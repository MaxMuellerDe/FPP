package server;

import java.net.*;

//Klasse als Datenstruktur zur Speicherung angemeldeter Clients
public class ClientNode{

    private Socket client;
    private String name;
    private String password;
    private boolean loggedin;

    ClientNode(Socket client, String name, String password, boolean loggedin){
        this.client = client;
        this.name = name;
        this.password = password;
        this.loggedin = loggedin;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Socket getClient() {
        return client;
    }

    void setClient(Socket client) {
        this.client = client;
    }

    boolean isLoggedin() {
        return loggedin;
    }

    void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }


}

