package Chatserver;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server{
    public static void main(String[] args){ //throws IOException (entweder das oder try-catch block)

        ExecutorService executor = Executors.newFixedThreadPool(20); //damit der Server nur 20 Verbindungen maximal annimmt

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(9999); //Portnummer ab bestimmter Zahl (4000 oder so) frei wählbar
            System.out.println("Server started.");
            System.out.println("Waiting for Clients...");

            while (true) {
                try {

                    Socket socket = serverSocket.accept(); //Server wartet, dass sich ein Client verbindet und "akzeptiert" ihn dann
                    //wenn Connection bekommt, returned es ein Socket-Objekt
                    System.out.println("Connection successfull!");

                    executor.execute(new MultipleClients(socket));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//end while
        } catch (IOException e1) { //um IO Exceptions zu haendeln
            e1.printStackTrace();
        }

    }//end main
}//end server



//        ServerSocket serverSocket = new ServerSocket(4999);
//        Socket socket = serverSocket.accept();
//
//        System.out.println("Client connected.");
//
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader bufferedReader = new BufferedReader(in);
//
//        String string = bufferedReader.readLine();
//        System.out.println("Client sagt: " + string);
//
//        PrintWriter prWr = new PrintWriter(socket.getOutputStream()); //Statt PrintWriter DataInputStream?
//        prWr.println("Hallo zurück!");
//        prWr.flush();

