/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Task;
import model.User;
import todoapp.ToDoApp;

/**
 *
 * @author Ahmed Hammad
 */
public class DatabaseConnection {

    private static String url = "jdbc:mysql://127.0.0.1:3306/todo?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "123456";
    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public static void signUpUser(User user) {
        try {
            String sql = "insert into users(firstname,lastname,username,password,location,gender)"
                    + " values(?,?,?,?,?,?)";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            String encryptedPassword = hashing.HashFunction.getHash(user.getPassword().getBytes());
            statement.setString(4, encryptedPassword);
            statement.setString(5, user.getLocation());
            statement.setString(6, user.getGender());
            String sqlString = statement.toString();
            String getSql = sqlString.substring(43);
            FileStorage.writeLog(getSql);
            statement.executeUpdate();
            System.out.println("Successfully Addes new User");

        } catch (ClassCastException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static ResultSet getUser(User user) {
        ResultSet resultSet = null;
        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {
            String sql = "select * from users where username = ? and password = ?";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getPassword());
                String sqlString = statement.toString();
                String getSql = sqlString.substring(43);
                FileStorage.writeLog(getSql);
                resultSet = statement.executeQuery();

            } catch (SQLException ex) {
                System.out.println(ex);
            }

        } else {
            System.out.println("Please enter your credential");
        }
        return resultSet;
    }

    public static void addTask(Task task) {
        String addTask = "insert into tasks(userid,task,description,datecreated) values(?,?,?,?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(addTask);
            statement.setInt(1, ToDoApp.userLoginID);
            statement.setString(2, task.getTask());
            statement.setString(3, task.getDescription());
            statement.setTimestamp(4, task.getDatecreated());
            String sqlString = statement.toString();
            String getSql = sqlString.substring(43);
            FileStorage.writeLog(getSql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*
    public static int getTasksCount() throws SQLException, ClassNotFoundException {
        String query = "select count(*) from tasks where userid = ?";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1,ToDoApp.userLoginID);
        ResultSet resultSet = statement.executeQuery();
        System.out.println("Count == " + resultSet.getInt(1));

        int theCount = 0;
        while (resultSet.next()) {
            theCount = resultSet.getInt(1);
            System.out.println(theCount);
        }
        return theCount;

    }
     */
    public static ObservableList<Task> getAllTasks() {
        if (conn == null) {
            getConnection();
        }
        ObservableList<Task> tasksList = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement("select * from tasks where userid = ?");
            statement.setInt(1, ToDoApp.userLoginID);
            String sqlString = statement.toString();
            String getSql = sqlString.substring(43);
            FileStorage.writeLog(getSql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("datecreated");
                String tName = resultSet.getString("task");
                String tdescription = resultSet.getString("description");
                tasksList.add(new Task(ts, tdescription, tName));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tasksList;
    }
//    private boolean isUserLoggedIn(User user) {
//        if(!user.getUserName().equals("") || !user.getPassword().equals("")) {
//            String sql = "select * from users where username = ? and password = ?";
//            try {
//                PreparedStatement statement = getConnection().prepareStatement(sql);
//                statement.setString(1, user.getUserName());
//                statement.setString(2, user.getPassword());
//                statement.executeQuery();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//            
//        }
//        return false;
//        
//    }

}
