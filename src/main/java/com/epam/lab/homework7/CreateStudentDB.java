package com.epam.lab.homework7;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class CreateStudentDB {
    private static final org.apache.log4j.Logger log = Logger.getLogger(CreateStudentDB.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static void createDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Class not found");
        }
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD); Statement stm = connection.createStatement()) {
            stm.addBatch("CREATE DATABASE studentdb");
            stm.addBatch("USE studentdb");
            stm.addBatch("CREATE TABLE subject (subj_id INT(5) PRIMARY KEY, subj_name VARCHAR(20), subj_desc VARCHAR(200));");
            stm.addBatch("CREATE TABLE specialty (spec_id VARCHAR(10) PRIMARY KEY, spec_name VARCHAR(20), spec_desc VARCHAR(200));");
            stm.addBatch("CREATE TABLE exam (exam_id INT(5) PRIMARY KEY, exam_name VARCHAR(15), exam_date DATE);");
            stm.addBatch("CREATE TABLE student(stud_id INT(10) PRIMARY KEY, stud_lname VARCHAR(10), stud_fname VARCHAR(10), stud_sname VARCHAR(15), stud_sex VARCHAR(6), stud_birth DATE, father_lname VARCHAR(10), father_fname VARCHAR(10), father_sname VARCHAR(15), mother_lname VARCHAR(10), mother_fname VARCHAR(10), mother_sname VARCHAR(15), city VARCHAR(10), street VARCHAR(15), str_number INT(3), phone_number VARCHAR(15), pass_numb VARCHAR(10), gradebook_numb VARCHAR(10), enter_date DATE, stud_group VARCHAR(10), course INT(1), spec_code VARCHAR(10), study_form VARCHAR(10));");
            stm.addBatch("CREATE TABLE spec_subject (spec_id VARCHAR(10), subj_id INT(5), PRIMARY KEY (spec_id, subj_id));");
            stm.addBatch("CREATE TABLE student_exam (stud_id INT(10), exam_id INT(5), mark INT(3), PRIMARY KEY(stud_id, exam_id));");
            stm.addBatch("ALTER TABLE student ADD FOREIGN KEY (spec_code) REFERENCES specialty(spec_id);");
            stm.addBatch("ALTER TABLE student_exam ADD FOREIGN KEY (stud_id) REFERENCES student(stud_id);");
            stm.addBatch("ALTER TABLE student_exam ADD FOREIGN KEY (exam_id) REFERENCES exam(exam_id);");
            stm.addBatch("ALTER TABLE spec_subject ADD FOREIGN KEY (spec_id) REFERENCES specialty(spec_id);");
            stm.addBatch("ALTER TABLE spec_subject ADD FOREIGN KEY (subj_id) REFERENCES subject(subj_id);");
            stm.addBatch("INSERT into specialty VALUES ('010101', 'Geodesy', 'Geodesy description');");
            stm.addBatch("INSERT into specialty VALUES ('010102', 'Psychology', 'Psychology description');");
            stm.addBatch("INSERT into subject\tVALUES ('1', 'Higher math', 'Higher math description');");
            stm.addBatch("INSERT into subject\tVALUES ('2', 'Philosophy', 'Philosophy description');");
            stm.addBatch("INSERT into subject\tVALUES ('3', 'Topography', 'Topography description');");
            stm.addBatch("INSERT into subject\tVALUES ('4', 'Psychology', 'Psychology description');");
            stm.addBatch("INSERT into subject\tVALUES ('5', 'English', 'English description');");
            stm.addBatch("INSERT into exam VALUES ('1', 'Mathematics', '2017/07/15');");
            stm.addBatch("INSERT into exam VALUES ('2', 'Physics', '2017/07/18');");
            stm.addBatch("INSERT into exam VALUES ('3', 'History', '2017/07/21');");
            stm.addBatch("INSERT into exam VALUES ('4', 'English', '2017/07/25');");
            stm.addBatch("INSERT into spec_subject VALUES ('010101', '1');");
            stm.addBatch("INSERT into spec_subject VALUES ('010101', '3');");
            stm.addBatch("INSERT into spec_subject VALUES ('010101', '5');");
            stm.addBatch("INSERT into spec_subject VALUES ('010102', '2');");
            stm.addBatch("INSERT into spec_subject VALUES ('010102', '4');");
            stm.addBatch("INSERT into spec_subject VALUES ('010102', '5');");
            stm.addBatch("INSERT into student\tVALUES (1, 'Sukhovych', 'Viktor', 'Evgenovych', 'male', '1997/03/16', 'Sukhovych', 'Evgen', 'Vasylyovych', 'Sukhovych', 'Iryna', 'Andriivna', 'Lviv', 'Horodotska', '71', '063-39-30-576', 'KC 213548', '1001452417', '2017/09/01', 'G-11', '1', '010101', 'Full_time');");
            stm.addBatch("INSERT into student\tVALUES (2, 'Stepanchuk', 'Olena', 'Vasylivna', 'female', '1997/01/12', 'Stepanchuk', 'Vasyl', 'Ivanovych', null, null, null, 'Odesa', 'Sovetska', '25', '093-17-13-251', 'KC 321487', '1001452425', '2017/09/01', 'G-11', '1', '010101', 'Full_time');");
            stm.addBatch("INSERT into student\tVALUES (3, 'Halan', 'Oleg', 'Petrovych', 'male', '1996/04/03', 'Halan', 'Petro', 'Vasylyovych', 'Halan', 'Tetyana', 'Andriivna', 'Lutsk', 'Shevchenka', '21', '063-14-11-777', 'KC 213587', '1001452243', '2017/09/01', 'P-21z', '2', '010102', 'Part-time');");
            stm.addBatch("INSERT into student\tVALUES (4, 'Melnyk', 'Igor', 'Volodumyrovych', 'male', '1996/08/22', 'Melnyk', 'Volodymyr', 'Olegovych', 'Melnyk', 'Andriana', 'Olegivna', 'Lviv', 'Mazepy', '32', '063-33-44-555', 'KC 348428', '1001452741', '2017/09/01', 'P-21z', '2', '010102', 'Part-time');");
            stm.addBatch("INSERT into student_exam VALUES ('1', '1', '85');");
            stm.addBatch("INSERT into student_exam VALUES ('1', '2', '74');");
            stm.addBatch("INSERT into student_exam VALUES ('1', '4', '80');");
            stm.addBatch("INSERT into student_exam VALUES ('2', '1', '89');");
            stm.addBatch("INSERT into student_exam VALUES ('2', '2', '85');");
            stm.addBatch("INSERT into student_exam VALUES ('2', '4', '92');");
            stm.addBatch("INSERT into student_exam VALUES ('3', '1', '68');");
            stm.addBatch("INSERT into student_exam VALUES ('3', '3', '94');");
            stm.addBatch("INSERT into student_exam VALUES ('3', '4', '58');");
            stm.addBatch("INSERT into student_exam VALUES ('4', '1', '91');");
            stm.addBatch("INSERT into student_exam VALUES ('4', '3', '73');");
            stm.addBatch("INSERT into student_exam VALUES ('4', '4', '74');");
            stm.executeBatch();
            stm.clearBatch();
            log.info("Database 'studentdb' created\n");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Connection failed");
        }
    }
}
