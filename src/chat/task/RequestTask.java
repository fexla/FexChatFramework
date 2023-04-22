package chat.task;

import chat.frag.DataFrag;

import java.util.function.Consumer;

/**
 * 发送建立任务请求的任务
 */
public class RequestTask extends Task {
    public int id;
    private boolean isAlreadyGetReply = false;
    private String requestKey;
    private DataFrag sendPkg;
    private Consumer<DataFrag> replyHandler;
    private Runnable interruptHandler;

    public RequestTask(String requestKey, DataFrag sendPkg, Consumer<DataFrag> replyHandler) {
        this.requestKey = requestKey;
        this.sendPkg = sendPkg;
        this.replyHandler = replyHandler;
    }

    public RequestTask(String requestKey, DataFrag sendPkg, Consumer<DataFrag> replyHandler, Runnable interruptHandler) {
        this.requestKey = requestKey;
        this.sendPkg = sendPkg;
        this.replyHandler = replyHandler;
        this.interruptHandler = interruptHandler;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean IsActive() {
        return replyHandler != null && !isAlreadyGetReply;
    }

    @Override
    public void Start(Session session) {
        session.SendRequestPkg(this, sendPkg, requestKey);
    }

    @Override
    public void Handle(Session session, DataFrag data) {
        replyHandler.accept(data);
        isAlreadyGetReply = true;
    }

    @Override
    public void Interrupt() {
        if (interruptHandler != null) interruptHandler.run();
    }
}
