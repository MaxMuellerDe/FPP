package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

class SocketListener extends Thread {
    private ServerSocket server;
    private Controller controller;
    ArrayList<ServerThread> currentThreads = new ArrayList<>();

    SocketListener(ServerSocket server, Controller controller){
        this.server = server;
        this.controller = controller;
    }

    //Thread fuer multiple Clients
    public void run(){
        controller.printOutput("Der Server wurde gestartet!");
        try{
            while(true) {
                ServerThread serverThread = new ServerThread(server.accept(), controller);
                currentThreads.add(serverThread);
                serverThread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Schließen des Servers
    void shutdown() throws IOException{
        int size = currentThreads.size();
        for(int i = 0; i < size; i++){
            ServerThread thread = currentThreads.get(i);
            if (thread.getState() != State.TERMINATED){
                thread.shutdown();
            }
        }
    }
}//end SocketListener