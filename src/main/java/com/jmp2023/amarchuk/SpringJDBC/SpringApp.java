package com.jmp2023.amarchuk.SpringJDBC;

import com.jmp2023.amarchuk.SpringJDBC.DAO.SocialDAOImpl;
import com.jmp2023.amarchuk.SpringJDBC.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


public class SpringApp {

    private static final Logger logger =   LoggerFactory.getLogger(SpringApp.class);
    private static final String CREATE_USER_TABLE = "CREATE TABLE User (id int PRIMARY KEY, Name varchar(255), Surname varchar(255), Birthday Date)";
    private static final String CREATE_POSTS_TABLE = "CREATE TABLE Posts (id integer PRIMARY KEY, userId integer, text varchar(60),  timestampDate Date, FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";
    private static final String CREATE_LIKES_TABLE = "CREATE TABLE Likes (postId int, userId int,  timestampDate Date,FOREIGN KEY (postId) REFERENCES Posts(id) ON DELETE CASCADE, FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";
    private static final String CREATE_FRIENDSHIP_TABLE = "CREATE TABLE Friendship (userid1 int, userid2 int, timestampDate Date,FOREIGN KEY (userid1) REFERENCES User(id) ON DELETE CASCADE)";
    private static final String SELECT_POPULAR_PEOPLE = "Select  u.name,u.surname from User u\n" +
            "left join Posts p on u.id=p.userId\n" +
            "left join Likes l on l.userId=p.userId\n" +
            "left join Friendship f on f.userId1=u.id\n" +
            "GROUP BY u.name,u.surname\n" +
            "HAVING count(l.postId)>100 and count(l.postId)>100;";

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("configuration.xml");


        /**
         * Get the SocialDAOImpl Bean
         */
        SocialDAOImpl socialDAO = applicationContext.getBean("socialDAO", SocialDAOImpl.class);
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        JdbcTemplate template = new JdbcTemplate(dataSource);

        /**
         * Drop all tables.
         */
        logger.info("Drop al existed tables to start from the beggining...");
        dropAllTables(template);

        /**
         * Create tables.
         */
        logger.info("Creating tables...");
        createUserTable(template);
        createPostsTable(template);
        createLikesTable(template);
        createFriendshipTable(template);

        /**
         * Insert data into tables.
         */
        logger.info("Inserting data into tables...");
        insertDataUsers(1000);
        insertDataPosts(3000,socialDAO);
        insertDataLikes(20000,socialDAO);
        insertDataFriendship(70000,socialDAO);

        /**
         * Get all names (only distinct) of users who has more than 100 friends and 100 likes in March 2025
         */
        List<User> users=getDistinctPopularUsers(template);
        logger.info("Number of popular users - {}",users.size());
        logger.info("List of popular users: {}",users.toString());

    }


    private static List<User> getDistinctPopularUsers(JdbcTemplate template){
        System.out.println("Getting popular users:");
        StringBuilder query = new StringBuilder();
        query.append(SELECT_POPULAR_PEOPLE);
        List<User> usersList =
                template.query(query.toString(), new BeanPropertyRowMapper<User>(User.class));
        return usersList;
    }

    private static int[]  insertDataFriendship(int records, SocialDAOImpl socialDAO) {
        String url = "jdbc:mysql://localhost:3306/jmp2023_SpringJDBC";
        String user = "anastasia";
        String password = "130352";

        PreparedStatement preparedStatement;

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(true);

            String compiledQuery = "INSERT INTO Friendship (userId1, userId2, timestampDate) VALUES" + "(?, ?, ?)";
            preparedStatement = connection.prepareStatement(compiledQuery);


            for(int index = 1; index <= records; index++) {

                int user1=socialDAO.getRandomUserId();
                int user2=socialDAO.getRandomUserId();

                while(user1==user2){
                    user2=socialDAO.getRandomUserId();
                }


                preparedStatement.setInt(1, user1);
                preparedStatement.setInt(2, user2);
                preparedStatement.setDate(3, getRandomDate());
                preparedStatement.addBatch();
            }

            long start = System.currentTimeMillis();
            int[] inserted = preparedStatement.executeBatch();
            long end = System.currentTimeMillis();

            System.out.println("total time taken to insert the batch = " + (end - start) + " ms");

            preparedStatement.close();
            connection.close();

            logger.info("Friendship data has been inserted successfully!");


            return inserted;

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            throw new RuntimeException("Error");
        }
    }

    private static int[] insertDataLikes(int records, SocialDAOImpl socialDAO) {
        String url = "jdbc:mysql://localhost:3306/jmp2023_SpringJDBC";
        String user = "anastasia";
        String password = "130352";

        PreparedStatement preparedStatement;

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(true);

            String compiledQuery = "INSERT INTO Likes(postId, userId, timestampDate)" +
                    " VALUES" + "(?, ?, ?)";
            preparedStatement = connection.prepareStatement(compiledQuery);


            for(int index = 1; index <= records; index++) {
                preparedStatement.setInt(1, socialDAO.getRandomPostId());
                preparedStatement.setInt(2, socialDAO.getRandomUserId());
                preparedStatement.setDate(3, getRandomDate());
                preparedStatement.addBatch();
            }

            long start = System.currentTimeMillis();
            int[] inserted = preparedStatement.executeBatch();
            long end = System.currentTimeMillis();

            System.out.println("total time taken to insert the batch = " + (end - start) + " ms");

            preparedStatement.close();
            connection.close();

            logger.info("Likes data has been inserted successfully!");

            return inserted;


        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            throw new RuntimeException("Error");
        }

    }

    public static int[] insertDataPosts(int records, SocialDAOImpl socialDAO)  {
    String url = "jdbc:mysql://localhost:3306/jmp2023_SpringJDBC";
    String user = "anastasia";
    String password = "130352";

    PreparedStatement preparedStatement;

    try {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(true);

        String compiledQuery = "INSERT INTO Posts(id, userId, text, timestampDate)" +
                " VALUES" + "(?, ?, ?,?)";
        preparedStatement = connection.prepareStatement(compiledQuery);


        for(int index = 1; index <= records; index++) {
            preparedStatement.setInt(1, index);
            preparedStatement.setInt(2, socialDAO.getRandomUserId());
            preparedStatement.setString(3, "Text" +index);
            preparedStatement.setDate(4, getRandomDate());
            preparedStatement.addBatch();
        }

        long start = System.currentTimeMillis();
        int[] inserted = preparedStatement.executeBatch();
        long end = System.currentTimeMillis();

        System.out.println("total time taken to insert the batch = " + (end - start) + " ms");

        preparedStatement.close();
        connection.close();

        logger.info("Posts data has been inserted successfully!");
        return inserted;

    } catch (SQLException ex) {
        System.err.println("SQLException information");
        while (ex != null) {
            System.err.println("Error msg: " + ex.getMessage());
            ex = ex.getNextException();
        }
        throw new RuntimeException("Error");
    }
}

    public static int[] insertDataUsers(int records){

        String url = "jdbc:mysql://localhost:3306/jmp2023_SpringJDBC";
        String user = "anastasia";
        String password = "130352";

        PreparedStatement preparedStatement;

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(true);

            String compiledQuery = "INSERT INTO User(id, name, Surname, birthday)" +
                    " VALUES" + "(?, ?, ?,?)";
            preparedStatement = connection.prepareStatement(compiledQuery);


            for(int index = 1; index <= records; index++) {
                preparedStatement.setInt(1, index);
                preparedStatement.setString(2, getRandomName());
                preparedStatement.setString(3, getRandomSurname());
                preparedStatement.setDate(4, getRandomDate());
                preparedStatement.addBatch();
            }

            long start = System.currentTimeMillis();
            int[] inserted = preparedStatement.executeBatch();
            long end = System.currentTimeMillis();

            System.out.println("total time taken to insert the batch = " + (end - start) + " ms");

            preparedStatement.close();
            connection.close();

            logger.info("User data has been inserted successfully!");
            return inserted;

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            throw new RuntimeException("Error");
        }
    }


    private static Date getRandomDate(){

        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return java.sql.Date.valueOf(randomBirthDate);

    }



    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private static String getRandomName () {
            String[] strArray = new String[]{"Anastasiya", "Volha", "Siri", "Jim", "Peter", "Tony", "Rosa", "Sam", "Don", "Ron",
                    "Fred", "Sasha", "Sara", "Ron", "Dima", "Pavel"};
            Random random = new Random();
            int index = random.nextInt(strArray.length);
            return strArray[index];
     }

        private static String getRandomSurname () {
            String[] strArray = new String[]{"Marchuk", "Petrov", "Solviev", "Pupkin", "Zajcev", "Shyshko", "Osipov",
                    "Bobrov", "Petrosiuk", "Sorokin", "S", "Michaluk", "M.", "Yard"};
            Random random = new Random();
            int index = random.nextInt(strArray.length);
            return strArray[index];
        }


        private static void createFriendshipTable (JdbcTemplate template){
            template.execute(CREATE_FRIENDSHIP_TABLE);
            System.out.println("Table Friendship created successfully");
        }

        private static void createLikesTable (JdbcTemplate template){
            template.execute(CREATE_LIKES_TABLE);
            System.out.println("Table Likes created successfully");
        }

        private static void createUserTable (JdbcTemplate template) {

            template.execute(CREATE_USER_TABLE);
            System.out.println("Table User created successfully");
        }

        private static void createPostsTable (JdbcTemplate template){
            template.execute(CREATE_POSTS_TABLE);
            System.out.println("Table Posts created successfully");
        }

        private static void dropAllTables (JdbcTemplate template){
            template.execute("DROP TABLE IF EXISTS `Friendship`");
            template.execute("DROP TABLE IF EXISTS `Likes`");
            template.execute("DROP TABLE IF EXISTS `Posts`");
            template.execute("DROP TABLE IF EXISTS `User`");

        }


}
