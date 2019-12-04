package Chatserver;

import java.io.*;
import java.net.*;

public class MultipleClients implements Runnable {

    private Socket socket;

    MultipleClients(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            InputStream in = socket.getInputStream();
            BufferedReader userinput = new BufferedReader(new InputStreamReader(in)); //identisch zu Client, aber dort etwas kuerzer


            String str;

            while ((str = userinput.readLine()) != null) {
                writer.write(str + "\n");
                writer.flush();
                System.out.println("Client: " + str); //Solang Nachrichten empfangen, bis Client nichts mehr sagt
            }

            writer.close();
            userinput.close(); //am Ende Reader und Writer schliessen

            socket.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
