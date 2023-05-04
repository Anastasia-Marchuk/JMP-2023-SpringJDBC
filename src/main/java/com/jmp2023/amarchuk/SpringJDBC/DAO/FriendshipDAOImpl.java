package com.jmp2023.amarchuk.SpringJDBC.DAO;

import com.jmp2023.amarchuk.SpringJDBC.Model.Friendship;
import com.jmp2023.amarchuk.SpringJDBC.Model.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FriendshipDAOImpl implements FriendshipDAO{


    private DataSource dataSource;

    public FriendshipDAOImpl(DataSource driverManagerDataSource) {
        this.dataSource=driverManagerDataSource;

    }

    public FriendshipDAOImpl() {


    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createFriendship(Friendship friendship) {
        String query = "insert into Friendship (userId1, userId2, timestampDate) values (?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, friendship.getUserid1());
            ps.setInt(2, friendship.getUserid2());
            ps.setDate(3, (Date)friendship.getTimestamp());

            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Friendship is created betwen users");
            }else System.out.println("Friendship is NOT created betwen users");
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
}
