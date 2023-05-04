package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.User;

import java.util.List;

public interface UserDAO {

    //Create
    void save(User user);
    //Read
//    User getById(int id);
    //Update
//    void update(User user);
    //Delete
//    void deleteById(int id);
    //Get All
    List<User> getAll();

    int getRandomUserId();
}
