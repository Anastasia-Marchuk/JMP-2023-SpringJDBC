package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.Likes;
import com.jmp2023.amarchuk.SpringJDBC.Model.Posts;

import java.util.List;

public interface LikesDAO {

    //Create
    void addLike(Likes like);
    //Read
    Likes getById(int id);

    //Get All
    List<Likes> getAll();

    int getRandomPostId();

}
