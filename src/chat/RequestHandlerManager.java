package chat;

import chat.frag.DataFrag;
import chat.task.Task;
import chat.task.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestHandlerManager {
    public Map<String, RequestHandler> requestHandlers;

    public RequestHandlerManager() {
        requestHandlers = new ConcurrentHashMap<>();
    }

    public void AddRequestHandler(String key, RequestHandler handler) {
        if (handler == null) return;
        synchronized (requestHandlers) {
            requestHandlers.put(key, handler);
        }
    }

    public boolean ContainsKey(String key) {
        return requestHandlers.containsKey(key);
    }

    public Task HandleTask(String key, Session session, DataFrag dataFrag) {
        return requestHandlers.get(key).HandleRequest(session, dataFrag);
    }
}
