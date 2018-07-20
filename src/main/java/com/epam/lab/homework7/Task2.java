package com.epam.lab.homework7;

import org.apache.log4j.Logger;

import java.sql.*;

public class Task2 {
    private static final org.apache.log4j.Logger log = Logger.getLogger(Task2.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        CreateStudentDB.createDB();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Class not found");
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD); Statement stm = connection.createStatement();) {
            log.info("Connected to database 'studentdb'");
            stm.execute("INSERT into subject VALUES ('6', 'Biology', null);");
            log.info("Inserted 1 row in subject");
            stm.executeUpdate("UPDATE subject SET subj_desc = 'Biology description' WHERE subj_id = 6");
            log.info("Updated 1 row in subject");
            stm.executeUpdate("DELETE FROM subject WHERE subj_id = 6");
            log.info("Deleted 1 row from subject");
            ResultSet resultSet = stm.executeQuery("SELECT * FROM subject");
            System.out.println("\nTable 'subject'");
            while (resultSet.next()) {
                int subj_id = resultSet.getInt(1);
                String subj_name = resultSet.getString(2);
                String subj_desc = resultSet.getString(3);
                System.out.format("subj_id: %d, subj_name: %s, subj_desc: %s\n", subj_id, subj_name, subj_desc);
            }
            System.out.println("\nTable 'student' with stud_id = 2");
            resultSet = stm.executeQuery("SELECT * FROM student WHERE stud_id = 2");
            while (resultSet.next()) {
                int stud_id = resultSet.getInt(1);
                String stud_lname = resultSet.getString(2);
                String stud_fname = resultSet.getString(3);
                String stud_sname = resultSet.getString(4);
                String stud_sex = resultSet.getString(5);
                String stud_birth = resultSet.getString(6);
                String city = resultSet.getString(13);
                String phone_number = resultSet.getString(16);
                String pass_numb = resultSet.getString(17);
                String gradebook_numb = resultSet.getString(18);
                String enter_date = resultSet.getString(19);
                String stud_group = resultSet.getString(20);
                int course = resultSet.getInt(21);
                String spec_code = resultSet.getString(22);
                String study_form = resultSet.getString(23);
                System.out.format("stud_id: %d, stud_lname: %s, stud_fname: %s, stud_sname: %s, stud_sex: %s, stud_birth: %s, city: %s, phone_number: %s, pass_numb: %s, gradebook_numb: %s, enter_date: %s, stud_group: %s, course: %d, spec_code: %s, study_form: %s\n", stud_id, stud_lname, stud_fname, stud_sname, stud_sex, stud_birth, city, phone_number, pass_numb, gradebook_numb, enter_date, stud_group, course, spec_code, study_form);
            }
            log.info("\nDemonstration of work with Cyrillic:");
            stm.execute("INSERT into subject VALUES ('6', 'Біологія', 'Опис біології');");
            resultSet = stm.executeQuery("SELECT * FROM subject WHERE subj_id = 6");
            while (resultSet.next()) {
                int subj_id = resultSet.getInt(1);
                String subj_name = resultSet.getString(2);
                String subj_desc = resultSet.getString(3);
                System.out.format("subj_id: %d, subj_name: %s, subj_desc: %s\n", subj_id, subj_name, subj_desc);
            }
            stm.executeUpdate("DELETE FROM subject WHERE subj_id = 6");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Connection failed");
        }
    }
}
