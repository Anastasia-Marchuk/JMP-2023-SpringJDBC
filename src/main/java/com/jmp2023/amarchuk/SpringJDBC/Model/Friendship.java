package com.jmp2023.amarchuk.SpringJDBC.Model;

import java.util.Date;

public class Friendship {

    private int userid1;
    private int userid2;
    private Date timestampDate;

    public Friendship(int userid1, int userid2, Date timestampDate) {
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.timestampDate = timestampDate;
    }

    public int getUserid1() {
        return userid1;
    }

    public void setUserid1(int userid1) {
        this.userid1 = userid1;
    }

    public int getUserid2() {
        return userid2;
    }

    public void setUserid2(int userid2) {
        this.userid2 = userid2;
    }

    public Date getTimestamp() {
        return timestampDate;
    }

    public void setTimestamp(Date timestampDate) {
        this.timestampDate = timestampDate;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userid1=" + userid1 +
                ", userid2=" + userid2 +
                ", timestampDate=" + timestampDate +
                '}';
    }

}
