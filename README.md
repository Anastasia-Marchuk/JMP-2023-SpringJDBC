##To start using app lets start local service with command:
-sudo service mysql start

##Log in as user Anastasia to database jmp2023_SpringJDBC:
mysql -u anastasia -p jmp2023_SpringJDBC

##Now you can use SQL commands:
-SHOW DATABASES;

-SELECT * FROM user;

-SELECT * FROM posts;

-SELECT * FROM user u
 INNER JOIN Posts p ON u.id=p.userId
 INNER JOIN Likes l ON l.userId=p.userId
 INNER JOIN Friendship f ON f.userId1=u.id
 ORDER BY 1;
 
 -
 
 and others..

