package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    public void mainWindow(){
            try{
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("sample.fxml"));
//                AnchorPane pane = loader.load();


                // Create the Pane and all Details
//                AnchorPane rootLogin = (AnchorPane) loader.load(fxmlLoginStream);
                AnchorPane rootLogin = loader.load();

                // Create the Scene
                Scene loginScene = new Scene(rootLogin);

                // Set the Scene to the Stage
                primaryStage.setScene(loginScene);

                // Set the Title to the Stage
                primaryStage.setTitle("Server Log");

                // Display the Stage
                primaryStage.show();


                primaryStage.setMinHeight(400);
                primaryStage.setMinWidth(500);

//                Controller mainWindowController = loader.getController();
//                mainWindowController.setMain(this);

//                Scene scene = new Scene(pane);
//
//                primaryStage.setScene(scene);
//                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Login Fenster");
//
//        StackPane layout = new StackPane();
//        layout.getChildren().add(button);
//
//        Scene scene = new Scene(layout, 500, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }//end mainWindow


}//end Application
