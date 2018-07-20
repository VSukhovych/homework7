package com.epam.lab.homework7;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class Task4 {
    private static final org.apache.log4j.Logger log = Logger.getLogger(Task4.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static DatabaseMetaData metadata = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Class not found");
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            System.out.println("Database 'studentdb' contains the following tables:\n");
            metadata = connection.getMetaData();
            getColumnsMetadata(getTablesMetadata(connection.getCatalog()));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Connection failed");
        }
    }

    private static ArrayList<String> getTablesMetadata(String catalog) throws SQLException {
        String table[] = {"TABLE"};
        ResultSet rs = null;
        ArrayList<String> tables = null;
        rs = metadata.getTables(catalog, "studentdb2", "%", table);
        tables = new ArrayList<String>();
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    private static void getColumnsMetadata(ArrayList<String> tables) throws SQLException {
        ResultSet rs = null;
        for (String actualTable : tables) {
            rs = metadata.getColumns(null, null, actualTable, null);
            System.out.println(actualTable.toUpperCase());
            while (rs.next()) {
                System.out.println(rs.getString("COLUMN_NAME") + " "
                        + rs.getString("TYPE_NAME") + " "
                        + rs.getString("COLUMN_SIZE"));
            }
            System.out.println("\n");
        }
    }
}
