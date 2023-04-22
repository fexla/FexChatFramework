package chat.task;

import chat.frag.DataFrag;

/**
 * 通信任务
 * 通信任务可以是单次的任务，也可以发送完之后等待单次或多次回复的任务
 * 框架通过任务id来匹配收到的包和任务
 */
public abstract class Task {
    public abstract int getId();

    public abstract void setId(int id);

    public abstract boolean IsActive();

    public abstract void Start(Session session);

    /**
     * 处理这个Task收到的数据
     *
     * @param data 远程发送过来的数据
     */
    public abstract void Handle(Session session, DataFrag data);

    public abstract void Interrupt();
}
