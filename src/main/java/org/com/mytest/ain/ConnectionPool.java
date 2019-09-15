package org.com.mytest.ain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

// an ConnectionPool interface with some methods
public interface ConnectionPool {
    Connection getConnection() throws SQLException, IOException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
}
