/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Task;
import todoapp.ToDoApp;

/**
 * FXML Controller class
 *
 * @author Ahmed Hammad
 */
public class AddItemFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button saveTaskBtn;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtTask;

    @FXML
    private Button backBtn;

    @FXML
    void back(ActionEvent event) {
        ToDoApp.addTaskPage.hide();
    }

    @FXML
    void SaveTask(ActionEvent event) {
        String task = txtTask.getText().trim();
        String desc = txtDescription.getText().trim();
        if (!task.equals("") && !desc.equals("")) {
            ToDoApp.addTaskPage.setAlwaysOnTop(false);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
            Task newTask = new Task(timestamp, desc, task);   
            Database.DatabaseConnection.addTask(newTask);
            JOptionPane.showMessageDialog(null, "Added New Task Success", "Successfull Insert", JOptionPane.INFORMATION_MESSAGE);
            txtTask.setText("");
            txtDescription.setText("");

        } else {
            ToDoApp.addTaskPage.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "All Feilds are Require", "Miss Input", JOptionPane.ERROR_MESSAGE); 
            System.out.println("Invalid Username or Password");
        }
        ToDoApp.addTaskPage.setAlwaysOnTop(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
