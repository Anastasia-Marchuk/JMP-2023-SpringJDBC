package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.Posts;
import com.jmp2023.amarchuk.SpringJDBC.Model.User;

import java.util.List;

public interface PostsDAO {
    //Create
    void save(Posts posts);
    //Read
    Posts getById(int id);

    //Get All
    List<Posts> getAll();

    int getRandomPostId();

}
