package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.User;
import java.util.List;

public class SocialDAOImpl {

    private UserDAOImpl userDAO ;
    private PostsDAOImpl postsDAO;
    private LikesDAOImpl likesDAO;
    private FriendshipDAOImpl friendshipDAO;


    public SocialDAOImpl(UserDAOImpl userDAO , PostsDAOImpl postsDAO, LikesDAOImpl likesDAO,FriendshipDAOImpl friendshipDAO) {
        this.userDAO = userDAO;
        this.postsDAO = postsDAO;
        this.likesDAO = likesDAO;
        this.friendshipDAO = friendshipDAO;
    }


    public List<User> getAll() {
       return userDAO.getAll();
    }

    public int getRandomUserId() {
        return userDAO.getRandomUserId();
    }

    public int getRandomPostId() {
        return postsDAO.getRandomPostId();
    }

}
