package com.epam.lab.homework7;

import org.apache.log4j.Logger;

import java.sql.*;

public class Task3 {
    private static final org.apache.log4j.Logger log = Logger.getLogger(Task3.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb2?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Class not found");
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); //disable auto-commit mode
            try (Statement stm = connection.createStatement()) {
                stm.executeUpdate("INSERT into subject VALUES ('6', 'Biology', null);");
                stm.executeUpdate("INSERT into subject VALUES ('7', 'Physics', 'Physics description');");
                stm.executeUpdate("UPDATE subject SET subj_desc = 'Biology description' WHERE subj_id = 6");
                connection.commit(); //execute transaction with few query
                log.info("Transaction executed with few query\n");
                System.out.println("SELECT * FROM subject WHERE subj_id=6 OR subj_id=7;");
                ResultSet resultSet = stm.executeQuery("SELECT * FROM subject WHERE subj_id=6 OR subj_id=7");
                while (resultSet.next()) {
                    int subj_id = resultSet.getInt(1);
                    String subj_name = resultSet.getString(2);
                    String subj_desc = resultSet.getString(3);
                    System.out.format("subj_id: %d, subj_name: %s, subj_desc: %s\n", subj_id, subj_name, subj_desc);
                }
                System.out.println();
                stm.executeUpdate("DELETE FROM subject WHERE subj_id = 6");
                stm.executeUpdate("DELETE FROM subject WHERE subj_id = 7");
                connection.commit(); //execute transaction with few query again
                log.info("Transaction executed with few query again");
                resultSet = stm.executeQuery("SELECT * FROM subject WHERE subj_id = 6 AND subj_id = 7");
                if (resultSet.next()) {
                    int subj_id = resultSet.getInt(1);
                    String subj_name = resultSet.getString(2);
                    String subj_desc = resultSet.getString(3);
                    System.out.format("subj_id: %d, subj_name: %s, subj_desc: %s\n", subj_id, subj_name, subj_desc);
                } else {
                    log.info("Row with subj_id = 6 and 7 is not exist");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Connection failed");
        }
    }
}
