package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{


    private DataSource dataSource;

    public UserDAOImpl(DataSource driverManagerDataSource) {
        this.dataSource=driverManagerDataSource;
    }

    public UserDAOImpl() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void save(User user) {
        String query = "insert into User (id, name, surname, birthday) values (?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setDate(3, (Date)user.getBithday());

            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("User saved with id="+user.getId());
            }else System.out.println("User save failed with id="+user.getId());
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
    public List<User> getAll() {

        String query = "select id, Name, Surname, Birthday from User";
        List <User> users=new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user=new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("Name"));
                user.setSurname(resultSet.getString("Surname"));
                user.setBithday(resultSet.getDate("Birthday"));
                users.add(user);
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
        return users;
    }

    public int getRandomUserId(){

        int randomId = 0;
        String query = "Select id from User order by rand() limit 1; ";
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
