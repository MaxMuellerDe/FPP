package server;

import java.io.IOException;

import java.net.ServerSocket;

import server.visual.ServerLog;

public class Server {

	public Server serverobj;
	private ServerSocket server;
	private ServerLog serverlog;
	private SocketListener socketListener;

	public Server() {
		serverobj = this;
		serverlog = new ServerLog(this);
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() throws IOException {
		server = new ServerSocket(9876);
		socketListener = new SocketListener(server, this);
		socketListener.start();
	}

	public void shutdown() {
		serverlog.printOutput("Der Server wird heruntergefahren!");
		try {
			socketListener.shutdown();
			server.close();
			serverlog.dispose();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerLog getServerLog() {
		return serverlog;
	}

	public static void main(String[] args) {
		try {
			new Server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}