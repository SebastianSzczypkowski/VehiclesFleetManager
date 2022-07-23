package pl.szczypkowski.vehiclesfleetmanager.email.model;

import java.util.List;

public class EmailRequest {

    private String title;
    private String content;
    private List<String> recipient;

    public EmailRequest() {
    }

    public EmailRequest(String title, String content, List<String> recipient) {
        this.title = title;
        this.content = content;
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getRecipient() {
        return recipient;
    }

    public void setRecipient(List<String> recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", recipient=" + recipient +
                '}';
    }
}
