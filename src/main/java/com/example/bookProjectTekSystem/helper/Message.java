package com.example.bookProjectTekSystem.helper;


// to manage messages when user get an error to sign up
public class Message {

    private String content;

    //    which is success or failed
    private String type;

    public Message(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
