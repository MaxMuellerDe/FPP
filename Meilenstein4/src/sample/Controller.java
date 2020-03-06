package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    static private HashMap<String, String> users = new HashMap<>();


    public Main main;

    public TextArea activeClients;
    public TextArea logField;

    private ServerSocket server;
    private SocketListener socketListener;

    public void setMain(Main main){
        this.main = main;
    }


    public void initialize() throws IOException {
        server = new ServerSocket(9876);
        socketListener = new SocketListener(server, this);

        socketListener.start();

    }

    @FXML
    private void shutdown() throws Exception{
        Stage stage = (Stage) logField.getScene().getWindow();
        socketListener.shutdown();
        server.close();
        stage.close();
    }

    void printOutput(String output){
        logField.appendText(output + "\n");
    }


    void updateClientList(ArrayList<String> clients){
        System.out.println("Updating clients...");
        activeClients.clear();
        for (String s : clients) {
            activeClients.appendText(s + "\n");
        }
        System.out.println("Done updating clients.");
    }



//    public void login(ActionEvent actionEvent) {
//
//        boolean loginsuccess = true;
//
//        String benutzername = txtfieldname.textProperty().get();
//            String password = txtfieldpassword.textProperty().get();
//
//        //Liste leer
//        if(users.isEmpty()){
//            users.put(benutzername, password);
////            Socketlist socketlist = new Socketlist(benutzername, passwort, socket);
////            clients.add(socketlist);
//            System.out.println("Liste leer");
//            loginmessage.setText("Login erfolgreich!");
//
//        }
//        else{
//            //Benutzername noch nicht vergeben
//            if(!users.containsKey(benutzername)){
//                users.put(benutzername, password);
////                Socketlist socketlist = new Socketlist(benutzername, passwort, socket);
////                clients.add(socketlist);
//                System.out.println("Name noch nicht vergeben");
//                loginmessage.setText("Login erfolgreich!");
//
//
//            }
//            //Gueltiger Login
//            else{
//                if(users.get(benutzername).equals(password)){
//                    System.out.println("Login erfolgreich nach Passworteingabe!");
//                    loginmessage.setText("Login erfolgreich!");
//                }
//                //falscher Login
//                else{
//                    loginsuccess = false;
//                    System.out.println("Nutzer hat falsches Passwort eingegeben.");
////                    socket.close(); //noch mal Login
//                    loginmessage.setText("Passwort oder Benutzername sind falsch!");
//                }
//
//            }
//        }
////        send_message("Login erfolgreich!", this.socket);
//
//    }//end login


}//end Controller
