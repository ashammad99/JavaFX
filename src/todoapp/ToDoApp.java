package todoapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed Hammad
 */
public class ToDoApp extends Application {

    public static Stage homePage = new Stage();
    public static Stage addTaskPage = new Stage();
    public static int userLoginID = 0;



    @Override
    public void start(Stage stage) throws Exception {



        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/view/addItem.fxml"));
        Scene scene = new Scene(root, 700, 400);
        homePage.setTitle("TO Do App");
        homePage.setScene(scene);
        homePage.getIcons().add(new Image("/assets/ToDoIcon.png"));
        homePage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
    }

}
