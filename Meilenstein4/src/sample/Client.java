package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Client extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loginWindow();
    }

    private void loginWindow(){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ClientLogin.fxml"));

            //Pane erstellen
            AnchorPane rootLogin = loader.load();

            //Szene erstellen
            Scene loginScene = new Scene(rootLogin);

            stage.setScene(loginScene);

            stage.setTitle("Login");

            stage.show();

            //minimale Größe - optional?
            stage.setMinWidth(500);
            stage.setMinHeight(400);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }//end loginWindow
}//end Client