package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.Posts;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostsDAOImpl implements PostsDAO {

    private DataSource dataSource;

    public PostsDAOImpl(DataSource driverManagerDataSource) {
        this.dataSource=driverManagerDataSource;
    }

    public PostsDAOImpl() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Posts posts) {

        String query = "insert into Posts (id, userId, text, timestampDate) values (?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, posts.getId());
            ps.setInt(2, posts.getUserId());
            ps.setString(3, posts.getText());
            ps.setDate(3, (Date)posts.getTimestamp());

            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Post saved with id="+posts.getId());
            }else System.out.println("Post save failed with id="+posts.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Posts getById(int id) {
        return null;
    }

    @Override
    public List<Posts> getAll() {
        String query = "select id, Name, Surname, Birthday from User";
        List <Posts> posts=new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                Posts post=new Posts();
                post.setId(resultSet.getInt("id"));
                post.setUserId(resultSet.getInt("userId"));
                post.setText(resultSet.getString("Text"));
                post.setTimestamp(resultSet.getDate("Birthday"));
                posts.add(post);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                resultSet.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    @Override
    public int getRandomPostId() {
        int randomId = 0;
        String query = "Select id from Posts order by rand() limit 1; ";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                randomId=resultSet.getInt("id");
            }

            return randomId;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                resultSet.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return randomId;
    }
}
