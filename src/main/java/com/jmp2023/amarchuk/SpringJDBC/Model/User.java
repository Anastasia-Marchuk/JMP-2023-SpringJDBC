package com.jmp2023.amarchuk.SpringJDBC.Model;

import java.util.Date;
import java.util.Random;

public class User {


    private int id;
    private String name;
    private String Surname;
    private Date bithday;


    public User(int id, String name, String surname, Date bithday) {
        this.id = id;
        this.name = name;
        Surname = surname;
        this.bithday = bithday;
    }


    public User() {

    }

    public User(String name, String surname) {
        this.name = name;
        this.Surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Date getBithday() {
        return bithday;
    }

    public void setBithday(Date bithday) {
        this.bithday = bithday;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", bithday=" + bithday +
                '}';
    }

}
