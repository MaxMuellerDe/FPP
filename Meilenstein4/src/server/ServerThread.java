package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket client;
	Server serverobj;

	ServerThread(Socket client, Server serverobj) {
		this.client = client;
		this.serverobj = serverobj;
	}

	public void run() {
		serverobj.getServerLog().printOutput("Ein neuer Client hat sich verbunden! Login wird gestartet!");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

			Session session = new Session(client, serverobj);

			// Login
			String benutzername = in.readLine();
			String passwort = in.readLine();
			boolean success = session.login(benutzername, passwort);

			if (success) {
				session.send_message("", "8", client);
				serverobj.getServerLog().printOutput(benutzername + " hat sich angemeldet!");
				// Nachricht über Anmeldung an alle anderen Nutzer
				session.message_all_clients_except_self(benutzername + " hat sich angemeldet");
				// Update für GUI des eigenen Clients
				session.update_all_active_clients();
				// Nachricht an alle anderen Clients zum Update der GUI
				session.message_all_clients(benutzername, "5");
				// Update der ServerGUI
				serverobj.getServerLog().updateClientList(session.returnClientList());
			} else {
				session.send_message("", "7", client);
			}

			// Input vom Client empfangen (Message Listener)
			boolean logout = false;
			while (!logout) {
				String[] input = session.get_message();
				System.out.println(input[0]);
				System.out.println(input[1]);
				if (input[0].equals("3")) {
					session.client_logout(client);
					serverobj.getServerLog().printOutput(benutzername + " hat die Verbindung getrennt!");
					session.message_all_clients(benutzername + " hat sich ausgeloggt!", "0");
					// GUI Update an alle Nutzer schicken
					session.message_all_clients(benutzername, "6");
					// GUI Update des Servers
					serverobj.getServerLog().updateClientList(session.returnClientList());
					logout = true;
				} else if (input[0].equals("2")) {
					serverobj.getServerLog().printOutput(benutzername + ": " + input[1]);
					session.message_all_clients(benutzername + ": " + input[1], "0");
				}
			}
			client.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void shutdown() throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		out.write("0");
		out.newLine();
		out.write("Der Server wurde geschlossen!");
		out.newLine();
		out.flush();
		out.write("4");
		out.newLine();
		out.flush();
		client.close();
	}
}