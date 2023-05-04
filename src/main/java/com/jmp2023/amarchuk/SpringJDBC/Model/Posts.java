package com.jmp2023.amarchuk.SpringJDBC.Model;

import java.util.Date;

public class Posts {

    private int id;
    private int userId;
    private String text;
    private Date timestampDate;

    public Posts(int id, int userId, String text, Date timestampDate) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.timestampDate = timestampDate;
    }

    public Posts() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestampDate;
    }

    public void setTimestamp(Date timestampDate) {
        this.timestampDate = timestampDate;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", timestampDate=" + timestampDate +
                '}';
    }
}
