package org.com.mytest.ain;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// // initializes a database
public class Db {

    public static void main(String[] args) {
        Db db = new Db();
        db.initialize(); // initialize the database
    }

    public void initialize() {
        Connection conn = ConnectionFactory.getConnection();
        Statement s;
        try {

            s = conn.createStatement();
            s.executeUpdate("DROP DATABASE IF EXISTS somearticles;");
            System.out.println("Old database dropped.");
            System.out.println();

            s.executeUpdate("CREATE DATABASE IF NOT EXISTS somearticles;");
            s.executeUpdate("USE somearticles;");

            String create_art = "CREATE TABLE articles"
                    + "(top_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "some_text MEDIUMTEXT, "
                    + "fetched_date_time TIMESTAMP ,"
                    + "CONSTRAINT pk_emp_id PRIMARY KEY (top_id)"
                    + ");";
            s.executeUpdate(create_art);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
