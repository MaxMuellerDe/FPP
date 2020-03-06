package sample;

import java.io.*;
import java.net.Socket;

class ServerThread extends Thread{
    Socket client;
    Controller controller;
    ServerThread(Socket client, Controller controller) {
        this.client = client;
        this.controller = controller;
    }

    public void run() {
        controller.printOutput("Ein neuer Client hat sich verbunden! Login wird gestartet!");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            Session session = new Session(client, controller);

            //Login
            String benutzername = in.readLine();
            String passwort = in.readLine();
            boolean success = session.login(benutzername, passwort);

            if(success){
                session.send_message("", "002", client);
                controller.printOutput(benutzername + " hat sich angemeldet!");
                //Nachricht über Anmeldung an alle anderen Nutzer
                session.message_all_clients_except_self(benutzername + " hat sich angemeldet");
                //Update für GUI des eigenen Clients
                session.update_all_active_clients();
                //Nachricht an alle anderen Clients zum Update der GUI
                session.message_all_clients(benutzername, "099");
                //Update der ServerGUI
                controller.updateClientList(session.returnClientList());
            }
            else{
                session.send_message("", "001", client);
            }

            //Input vom Client empfangen (Message Listener)
            boolean logout = false;
            while(!logout){
                String[] input = session.get_message();
                System.out.println(input[0]);
                System.out.println(input[1]);
                if(input[0].equals("101")){
                    session.client_logout(client);
                    controller.printOutput(benutzername + " hat die Verbindung getrennt!");
                    session.message_all_clients(benutzername + " hat sich ausgeloggt!", "1-1");
                    //GUI Update an alle Nutzer schicken
                    session.message_all_clients(benutzername, "098");
                    //GUI Update des Servers
                    controller.updateClientList(session.returnClientList());
                    logout = true;
                }
                else if(input[0].equals("100")){
                    controller.printOutput(benutzername + ": " + input[1]);
                    session.message_all_clients(benutzername + ": " + input[1], "1-1");
                }
            }
            client.close();

        } catch(IOException e){}
    }

    void shutdown() throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        out.write("1-1");
        out.newLine();
        out.write("Der Server wurde geschlossen!");
        out.newLine();
        out.flush();
        out.write("222");
        out.newLine();
        out.flush();
        client.close();
    }
}