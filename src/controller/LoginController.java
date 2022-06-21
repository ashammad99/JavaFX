/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseConnection;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.User;
import todoapp.ToDoApp;
import static todoapp.ToDoApp.homePage;
import static todoapp.ToDoApp.userLoginID;

/**
 * FXML Controller class
 *
 * @author Ahmed Hammad
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtUserName;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblLogin;

    @FXML
    void login(ActionEvent event) {
        System.out.println("Login button pressed");
        System.out.println("User Login ID: " + userLoginID);
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        User user = new User();
        user.setUserName(username);
        String encryptedPassword = hashing.HashFunction.getHash(txtPassword.getText().getBytes());
        user.setPassword(encryptedPassword);
        if (!username.equals("") || !txtPassword.equals("")) {
            //loginUser(username, password);
            ResultSet userRow = DatabaseConnection.getUser(user);
            int counter = 0;
            String name = "";
            try {
                while (userRow.next()) {
                    counter++;
                    name = userRow.getString("firstname");
                    userLoginID = userRow.getInt("userid");
                }
                if (counter == 1) {
                    System.out.println("Login Successfully");
                    lblLogin.setText("Welcome, " + name);
                    System.out.println("User Login ID: " + userLoginID);
                    showItemScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Wrong Credentials", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Invalid Username or Password");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Incorrect UserName or Password");
        }

    }

    @FXML
    void signUp(ActionEvent event) {
        System.out.println("Sign UP button pressed");
//        loginBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/signup.fxml"));
        try {
            loader.load();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent root = loader.getRoot();
        ToDoApp.homePage.setScene(new Scene(root));

//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    private void showItemScreen() {
        loginBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addItem.fxml"));
        try {
            loader.load();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Task Processing");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/assets/todo.png"));
        stage.showAndWait();
    }

}
