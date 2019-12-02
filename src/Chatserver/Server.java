package Chatserver;

import java.net.*;
import java.io.*;


public class Server{
    public static void main(String[] args) throws IOException{ //throws IOException (entweder das oder try-catch block)


        try{
            System.out.println("Waiting for Clients.");
            ServerSocket serverSocket = new ServerSocket(9999); //Portnummer ab bestimmter Zahl (4000 oder so) frei wählbar
            Socket socket = serverSocket.accept(); //Server wartet, dass sich ein Client verbindet und "akzeptiert" ihn dann
            //wenn Connection bekommt, returned es ein Socket-Objekt
            System.out.println("Connection successfull!");
        }
        catch (Exception e){ //um IO Exceptions zu haendeln
            e.printStackTrace();
        }




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

    }//end main
}//end server
