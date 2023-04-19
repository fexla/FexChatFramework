package chat.frag.pkg;

import chat.frag.DataFrag;
import chat.frag.IntegerFrag;
import chat.frag.TextFrag;

public class TaskPkg extends MapPkg {
    public TaskPkg() {
        super();
    }

    public TaskPkg(DataFrag data) {
        super();
        content.put("data", data);
    }


    public void setTaskId(int id) {
        content.put("id", new IntegerFrag(id));
    }

    public int getTaskId() {
        return ((IntegerFrag) content.get("id")).value;
    }

    public void setData(DataFrag data) {
        content.put("data", data);
    }

    public DataFrag getData() {
        return content.get("data");
    }

    /**
     * 设置为一个请求
     *
     * @param key 请求名
     */
    public void SetRequest(String key) {
        content.put("request", new TextFrag(key));
    }

    /**
     * 返回是否是一个请求
     *
     * @return
     */
    public boolean IsRequest() {
        return content.containsKey("request");
    }

    public String GetRequestKey() {
        return ((TextFrag) content.get("request")).content;
    }
}
