package chat;

import chat.frag.DataFrag;
import chat.task.Task;
import chat.task.Session;

public interface RequestHandler {
    Task HandleRequest(Session session, DataFrag dataFrag);
}
