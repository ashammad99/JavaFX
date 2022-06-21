/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.User;
import todoapp.ToDoApp;

/**
 * FXML Controller class
 *
 * @author Ahmed Hammad
 */
public class SignupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtUserName;

    @FXML
    private CheckBox femaleCheck;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField txtLastName;

    @FXML
    private CheckBox maleCheck;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLocation;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button goToLoginBtn;

    @FXML
    void signUp(ActionEvent event) {
        createUser();
    }

    @FXML
    void goToLogin(ActionEvent event) {
        //goToLoginBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
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

    public void createUser() {
        Database.DatabaseConnection connection = new DatabaseConnection();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String location = txtLocation.getText();
        String gender = "";
        if (maleCheck.isSelected()) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        if (firstName.equals("") && lastName.equals("") && username.equals("") && password.equals("") && location.equals("")) {
            JOptionPane.showMessageDialog(null, "All Fields required", "Miss Input",JOptionPane.ERROR_MESSAGE);
        } else {
            model.User newUser = new User(firstName, lastName, username, password, location, gender);
            connection.signUpUser(newUser);
            JOptionPane.showMessageDialog(null, "Success added new user, you can now login and start tasks","Success", JOptionPane.DEFAULT_OPTION);
        }

    }

}
