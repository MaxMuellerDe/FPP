package Chatserver;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException{ //throws IOException statt Try-Catch-Block

        try {
            System.out.println("Client started.");
            Socket socket = new Socket("localhost", 9999); //IP-Adresse des Servers - localhost, da Client und Server auf derselben Maschine sind; Portnummer
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            //System.in liest 1 Byte at a time; wir wollen keine Bytes -> InputStreamReader - nimmt Bytestream und gibt einen Characterstream zurueck
            //wir wollen nicht 1 Character at time, sondern einen gesamten String -> BufferedReader - liest gesamten String at a time
            System.out.println("Bitte geben Sie etwas ein!");
            String str = userInput.readLine(); //liest den eingegeben String; wartet, bis der User etwas eingibt
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //autoflush - sonst werden Daten nicht gesendet
            //Daten werden an Server gesendet
            // out.println(str);
        }
        catch (Exception e){
            e.printStackTrace();
        }



//        Socket socket = new Socket("localhost", 4999);
//
//        PrintWriter prWr = new PrintWriter(socket.getOutputStream()); //Statt PrintWriter DataInputStream?
//        prWr.println("Heyho");
//        prWr.flush();
//
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader bufferedReader = new BufferedReader(in);
//
//        String string = bufferedReader.readLine();
//        System.out.println("Server sagt: " + string);

    }//end main
}//end client
