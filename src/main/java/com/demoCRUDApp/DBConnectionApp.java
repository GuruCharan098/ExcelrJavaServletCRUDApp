package com.demoCRUDApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionApp {

    private static final String URL =
        "jdbc:mysql://localhost:3306/student_detailsdb";

    private static final String USER = "root";

    private static final String PASSWORD = "root123";

    public static Connection getConnection()
            throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL JDBC driver is not available to this web application.",
                e
            );
        }

        return DriverManager.getConnection(
        URL,USER,PASSWORD
        );
    }
}
