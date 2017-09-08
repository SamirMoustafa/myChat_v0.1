package com.example.abdelrahmanhesham.mychat;

/**
 * Created by Abdelrahman Hesham on 5/15/2017.
 */

public class Messages {
    private boolean isMine;
    private String text;

    public Messages(boolean isMine, String text) {
        this.isMine = isMine;
        this.text = text;
    }

    public boolean isMine() {
        return isMine;
    }

    public String getText() {
        return text;
    }
}
