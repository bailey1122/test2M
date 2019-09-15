package org.com.mytest.ain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// a central class for connecting to a database
public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/"; // JDBC URL
    public static final String USER = "user1"; // user name
    public static final String PASS = "1234"; // password

    public synchronized static Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(URL, USER, PASS); // return a Connection object
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}