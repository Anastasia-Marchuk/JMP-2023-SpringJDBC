package com.jmp2023.amarchuk.SpringJDBC.Model;

import java.util.Date;

public class Likes {

    private int postId;
    private int userId;
    private Date timestampDate;

    public Likes(int postId, int userId, Date timestampDate) {
        this.postId = postId;
        this.userId = userId;
        this.timestampDate = timestampDate;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestampDate;
    }

    public void setTimestamp(Date timestampDate) {
        this.timestampDate = timestampDate;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", timestampDate=" + timestampDate +
                '}';
    }
}
