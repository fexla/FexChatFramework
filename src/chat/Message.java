package chat;

public class Message {
    public String head;
    public String content;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(String head, String content) {
        this.head = head;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "head='" + head + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
