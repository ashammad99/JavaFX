/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseConnection;
import Database.FileStorage;
import todoapp.ToDoApp;
import animations.Shaker;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.Task;
import static todoapp.ToDoApp.addTaskPage;
import static todoapp.ToDoApp.homePage;

/**
 * FXML Controller class
 *
 * @author Ahmed Hammad
 */
public class AddItemController implements Initializable {

    @FXML
    private TableColumn<Task, String> descCol;

    @FXML
    private TableColumn<Task, Date> dateCol;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> taskCol;

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView refreshButton;

    @FXML
    private Label lblNoTasks;

    int index = -1;

    @FXML
    private ImageView deleteButton;

    @FXML
    private ImageView sortBtn;

    @FXML
    private ImageView exit;

    @FXML
    void getSelected(javafx.scene.input.MouseEvent event) {
        index = taskTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateTable();

        //addButton.setOpacity(0);
        if (taskTable.getItems().size() > 0) {
            lblNoTasks.setText("Count of Current Tasks: " + taskTable.getItems().size());
        }
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            System.out.println("Added Button Pressed");
            Shaker buttonShaker = new Shaker(addButton);
            buttonShaker.shake();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Update UI here.
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/addItemForm.fxml"));
                        Scene scene = new Scene(root);
                        ToDoApp.addTaskPage.setScene(scene);
                        ToDoApp.addTaskPage.setTitle("Add Task");
                        addTaskPage.getIcons().add(new Image("/assets/add_task_32.png"));
                        ToDoApp.addTaskPage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });

        refreshButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            updateTable();
        });
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {//Delete Task using task name()
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "No Task Selected to delete it");
            } else {
                String sql = "delete from tasks where task = ? and userid = ?";
                try {
                    PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                    statement.setString(1, taskCol.getCellData(index));
                    statement.setInt(2, ToDoApp.userLoginID);
                    String sqlString = statement.toString();
                    String getSql = sqlString.substring(43);
                    FileStorage.writeLog(getSql);
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "Delete Task Successfully");
                    updateTable();
                } catch (SQLException ex) {
                    Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            System.exit(0);
        });
        sortBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            ObservableList<Task> tasksList = DatabaseConnection.getAllTasks();
            Collections.sort(tasksList, new Comparator<Task>() {
                @Override
                public int compare(Task t, Task t1) {
                    return t.getTask().compareTo(t1.getTask());
                }
            });
            taskTable.setItems(tasksList);
        });
    }

    public void updateTable() {
        ObservableList<Task> tasksList = DatabaseConnection.getAllTasks();

        taskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        descCol.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Task, Date>("datecreated"));
        taskTable.setItems(tasksList);

        if (taskTable.getItems().size() > 0) {
            lblNoTasks.setText("Count of Current Tasks: " + taskTable.getItems().size());
        } else {
            lblNoTasks.setText("Not Tasks for Today, yet !");
        }

    }
}
