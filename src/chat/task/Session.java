package chat.task;

import chat.frag.DataFrag;
import chat.frag.pkg.TaskPkg;
import chat.RequestHandlerManager;
import chat.util.Log;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 表示一个会话
 * 在会话中会有多次通信任务，通过任务包解析出来的任务id来匹配对应的任务
 */
public class Session extends Thread {
    private int num;
    private static final int MAX_TASK_ID = 100000000;
    public InputStream input;
    public OutputStream output;
    public RequestHandlerManager requestHandlerManager;

    public Map<Integer, Task> tasks;

    public AtomicBoolean active;
    private List<Runnable> onClose;

    public Session(Socket socket, RequestHandlerManager requestHandlerManager) {
        try {
            this.requestHandlerManager = requestHandlerManager;
            input = socket.getInputStream();
            output = socket.getOutputStream();
            active = new AtomicBoolean(true);
            num = new Random().nextInt(MAX_TASK_ID);
            tasks = new HashMap<>();
            onClose = new ArrayList<>();
        } catch (IOException e) {
            active = new AtomicBoolean(false);
        }
    }

    public void PutTask(Task task) {
        PutTask(task, num);
        num = (num + 1) % MAX_TASK_ID;
    }

    private void PutTask(Task task, int id) {
        if (!active.get()) return;
        Log.Debug("Put task");
        task.setId(id);
        task.Start(this);
        if (task.IsActive()) {
            synchronized (tasks) {
                tasks.put(id, task);
            }
        }
    }


    public void SendPkg(Task task, DataFrag data) {
        if (!active.get()) return;
        TaskPkg pkg = new TaskPkg(data);
        pkg.setTaskId(task.getId());
        Log.Debug("Send package " + pkg);
        try {
            pkg.write(output);
        } catch (IOException e) {
            Close();
        }
    }

    public void SendRequestPkg(Task task, DataFrag data, String requestKey) {
        if (!active.get()) return;
        TaskPkg pkg = new TaskPkg(data);
        pkg.setTaskId(task.getId());
        pkg.SetRequest(requestKey);

        Log.Debug("SetRequest " + requestKey);
        Log.Debug("Send package " + pkg);
        try {
            pkg.write(output);
        } catch (IOException e) {
            Close();
        }
    }

    public void HandlePkg(TaskPkg taskPkg) {
        if (!active.get()) return;
        int targetId = taskPkg.getTaskId();
        if (taskPkg.IsRequest()) {
            Log.Debug("Handle request");
            String key = taskPkg.GetRequestKey();
            synchronized (requestHandlerManager) {
                if (requestHandlerManager.ContainsKey(key)) {
                    Task task;
                    try {
                        task = requestHandlerManager.HandleTask(key, this, taskPkg.getData());

                    } catch (Exception e) {
                        System.out.println("Request handle error");
                        Close();
                        throw e;
                    }
                    if (task != null) {
                        PutTask(task, targetId);
                    }
                }
            }
        } else if (tasks.containsKey(targetId)) {
            if (CheckActive(targetId)) {
                tasks.get(targetId).Handle(this, taskPkg.getData());
            }
        }
    }

    private boolean CheckActive(int id) {
        var task = tasks.get(id);
        if (task.IsActive()) {
            return true;
        } else {
            tasks.remove(id);
            return false;
        }
    }

    @Override
    public void run() {
        try {
            while (active.get()) {
                var pkg = StructuredContentIO.ReadTaskPkg(input);
                Log.Debug("Receive package " + pkg);
                HandlePkg(pkg);
            }
        } catch (IOException e) {
            Close();
        }
    }

    public void AddCloseListener(Runnable listenner) {
        onClose.add(listenner);
    }

    public void Close() {
        active.set(false);
        try {
            input.close();
            output.close();
            tasks.forEach((key, value) -> value.Interrupt());
            tasks.clear();
            onClose.forEach(action -> action.run());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
