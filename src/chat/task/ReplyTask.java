package chat.task;

import chat.frag.DataFrag;

/**
 * 回应对方发来的请求的任务
 */
public class ReplyTask extends Task {
    public int id;
    private DataFrag sendPkg;

    public ReplyTask(DataFrag sendPkg) {
        this.sendPkg = sendPkg;
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
        return false;
    }

    @Override
    public void Start(Session session) {
        session.SendPkg(this, sendPkg);
    }

    @Override
    public void Handle(Session session, DataFrag data) {
    }

    @Override
    public void Interrupt() {
    }
}
