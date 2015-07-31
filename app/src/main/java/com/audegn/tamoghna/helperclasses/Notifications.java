package com.audegn.tamoghna.helperclasses;

public class Notifications {
    private boolean isheader;
    private String header = null;
    private String time = null;
    private String notification = null;

    public Notifications(String header) {
        this.header = header;
        isheader = true;
    }

    public Notifications(String time, String notification) {
        this.time = time;
        this.notification = notification;
        isheader = false;
    }

    public String getHeader() {
        return header;
    }

    public Notifications setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Notifications setTime(String time) {
        this.time = time;
        return this;
    }

    public String getNotification() {
        return notification;
    }

    public Notifications setNotification(String notification) {
        this.notification = notification;
        return this;
    }

    public boolean isheader() {
        return isheader;
    }
}
