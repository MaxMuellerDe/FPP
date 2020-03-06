package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class ClientController{

    //Login Fenster
    public Label lblname;
    public Label lblpassword;

    public TextField txtfieldname;
    public TextField txtfieldpassword;

    public Label loginmessage;
    public Button register;


    //Client Fenster
    public TextArea outputField;
    public TextField inputField;

    public Button sendInput;
    public TextArea activeClients;


    private static Socket server;

    private Session session;

    private ArrayList<String> clients;

    private static boolean initialized = false;


    public ClientController() {
    }

    @FXML
    private void initialize() throws IOException {
        if (!initialized)
        {
            initialized = true;
            createSocket();
            session = new Session();
        }
        else{
            createMessageListener();
            clients = new ArrayList<>();
        }
    }

    @FXML
    private void sendMessage() throws IOException{
        if(server != null) {
            send_server_message(inputField.getText(), "100");
        }
        inputField.clear();
    }
    @FXML
    public void logout() throws IOException{
        Stage stage = (Stage) inputField.getScene().getWindow();
        if(server!= null){
            send_server_message("101", "101");
            server.close();
        }
        stage.close();

    }

    @FXML
    private void login() throws  Exception{
        System.out.println("Login aufgerufen.");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(txtfieldname.getText());
        out.newLine();
        out.flush();
        out.write(txtfieldpassword.getText());
        out.newLine();
        out.flush();

        Stage stage = (Stage) txtfieldname.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ClientWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat Fenster von " + txtfieldname.getText());
        stage.show();

    }
    private void createSocket(){
        try {
            server = new Socket("localhost", 9876);
        }catch(UnknownHostException e) {
            System.out.println("Cannot find host.");
        }catch (IOException e) {
            System.out.println("Error connecting to host.");
        }
    }

    private void createMessageListener() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        MessageListener messages = new MessageListener(in, this);
        messages.start();
    }

    private void send_server_message(String message, String code)throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }

    void updateTextArea(String message){
        outputField.appendText(message + "\n");
    }

    void updateClientList(){
        System.out.println("Updating client list...");
        activeClients.clear();
        for (String s : clients) {
            activeClients.appendText(s + "\n");
        }
        System.out.println("Done updating client list. ");
    }

    void deleteClient(String newClient){
        System.out.println("Deleting client...");
        int clients_size = clients.size();
        for(int i = 0; i < clients_size; i++){
            if(clients.get(i).equals(newClient)){
                clients.remove(i);
                break;
            }
        }
        System.out.println("Done deleting client.");
    }

    void addClient(String newClient){
        System.out.println("Adding client...");
        clients.add(newClient); //Programm bleibt stehen!
        System.out.println("Done adding client.");
    }
}

