package Chatserver;

import java.net.*;
import java.io.*;


public class Server{
    public static void main(String[] args){ //throws IOException (entweder das oder try-catch block)


        try{
            System.out.println("Server started.");
            System.out.println("Waiting for Clients...");
            ServerSocket serverSocket = new ServerSocket(9999); //Portnummer ab bestimmter Zahl (4000 oder so) frei wählbar
            Socket socket = serverSocket.accept(); //Server wartet, dass sich ein Client verbindet und "akzeptiert" ihn dann
            //wenn Connection bekommt, returned es ein Socket-Objekt
            System.out.println("Connection successfull!");

            //Streams

            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            InputStream in = socket.getInputStream();
            BufferedReader userinput = new BufferedReader(new InputStreamReader(in)); //identisch zu Client, aber dort etwas kuerzer


            String str;

            while((str = userinput.readLine()) != null){
                writer.write(str + "\n");
                writer.flush();
                System.out.println("Client: " + str); //Solang Nachrichten empfangen, bis Client nichts mehr sagt
            }

            writer.close();
            userinput.close(); //am Ende Reader und Writer schliessen

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
