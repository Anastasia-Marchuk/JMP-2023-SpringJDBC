package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.Likes;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LikesDAOImpl implements LikesDAO{

    private DataSource dataSource;

    public LikesDAOImpl(DataSource driverManagerDataSource) {
        this.dataSource=driverManagerDataSource;

    }

    public LikesDAOImpl() {
    }

    @Override
    public void addLike(Likes like) {
    }

    @Override
    public Likes getById(int id) {
        return null;
    }

    @Override
    public List<Likes> getAll() {
        return null;
    }

    @Override
    public int getRandomPostId() {
        return 0;
    }
}
