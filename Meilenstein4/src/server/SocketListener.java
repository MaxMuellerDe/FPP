package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SocketListener extends Thread {
	private ServerSocket server;
	private Server serverobj;
	ArrayList<ServerThread> currentThreads = new ArrayList<>();

	SocketListener(ServerSocket server, Server serverobj) {
		this.server = server;
		this.serverobj = serverobj;
	}

	// Thread fuer multiple Clients
	public void run() {
		serverobj.getServerLog().printOutput("Der Server wurde gestartet!");
		try {
			while (true) {
				ServerThread serverThread = new ServerThread(server.accept(), serverobj);
				currentThreads.add(serverThread);
				serverThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Schlieﬂen des Servers
	void shutdown() throws IOException {
		int size = currentThreads.size();
		for (int i = 0; i < size; i++) {
			ServerThread thread = currentThreads.get(i);
			if (thread.getState() != State.TERMINATED) {
				thread.shutdown();
			}
		}
	}
}
