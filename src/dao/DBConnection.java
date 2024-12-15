package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   public static Connection getConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/java_user_database";
       String user = "root";
       String password = "";
       
       return DriverManager.getConnection(url, user, password);
       
       
   } 
   private static DBConnection instance = null;
   public static DBConnection getInstance() {
       if (instance == null) {
           instance = new DBConnection();
       }
       return instance;
   }

   
}
