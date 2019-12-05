package Chatserver;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){ //throws IOException{ //throws IOException statt Try-Catch-Block

        try (Scanner scan = new Scanner(System.in)){
            System.out.println("Client started.");
            Socket socket = new Socket("localhost", 9999); //IP-Adresse des Servers - localhost, da Client und Server auf derselben Maschine sind; Portnummer

            System.out.println("What do you want to say?");

            PrintWriter writer = new PrintWriter(socket.getOutputStream()); //, true     autoflush - sonst werden Daten nicht gesendet
            //Daten werden an Server gesendet

            InputStream in =  socket.getInputStream();
            BufferedReader userInput = new BufferedReader(new InputStreamReader(in));
            //System.in liest 1 Byte at a time; wir wollen keine Bytes -> InputStreamReader - nimmt Bytestream und gibt einen Characterstream zurueck
            //wir wollen nicht 1 Character at time, sondern einen gesamten String -> BufferedReader - liest gesamten String at a time
            boolean eingeloggt=true;
            while(eingeloggt){
            System.out.print("Input: ");
            String toServer = scan.nextLine();
                if(toServer.equals("logout"))
                    eingeloggt=false;
            writer.write(toServer+ "\n");
            writer.flush();
            



            String str = userInput.readLine(); //userInput.readLine(); //liest den eingegeben String; wartet, bis der User etwas eingibt

            System.out.println("Server: " + str); //Solang Nachrichten empfangen, bis Client nichts mehr sagt
            
        }
            userInput.close();
            writer.close();
            socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }//end main
}//end client
