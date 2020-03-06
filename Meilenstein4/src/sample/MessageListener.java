package sample;

import java.io.BufferedReader;

public class MessageListener extends Thread{
    private BufferedReader in;
    private ClientController controller;
    private boolean loggedIn = true;

    MessageListener(BufferedReader in, ClientController controller) {
        this.in = in;
        this.controller = controller;
    }

    public boolean getLoggedIn(){
        return this.loggedIn;
    }
    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                System.out.println(input);
                switch (input) {
                    case "1-1":
                        controller.updateTextArea(in.readLine());
                        break;
                    case "222":
                        System.exit(0);
                    case "099":
                        //Neuer Login wird in ClientListe aufgenommen
                        System.out.println("New Login");
                        controller.addClient(in.readLine());
                        controller.updateClientList();
                        break;
                    case "098":
                        //Logout muss aus ClientListe entfernt werden
                        String username = in.readLine();
                        System.out.println(username);
                        controller.deleteClient(username);
                        controller.updateClientList();
                        break;
                    case "001":
                        //Fehlgeschlagener Login
                        controller.updateTextArea("Der Login ist fehlgeschlagen! Das Fenster kann geschlossen werden!");
                        break;
                }
            }
        }catch(Exception ignored){}
    }
}