package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Session {
	private Socket client;
	private boolean failed;
	private Server serverobj;
	private static ArrayList<ClientNode> clients = new ArrayList<>();

	Session(Socket client, Server serverobj) {
		this.client = client;
		this.serverobj = serverobj;
	}

	public Session() {
	}

	boolean login(String benutzername, String passwort) throws IOException {
		System.out.println("Login aufgerufen.");
		int listsize = clients.size();
//        boolean stop = false;
		int i = 0;
		while (i < listsize) { // !stop &&
			ClientNode current_client = clients.get(i);

			// Suche nach bekanntem Nutzernamen
			if (benutzername.equals(current_client.getName())) {
//                stop = true;
				// Richtiges Passwort und Nutzer war nicht eingeloggt
				if (passwort.equals(current_client.getPassword()) && !current_client.isLoggedin()) {
					send_message("Willkommen zurück " + benutzername, "111", this.client);

					// Socket aktualisieren
					current_client.setClient(this.client);
					current_client.setLoggedin(true);
					return true;
				}
				// richtiges passwort aber nutzer war schon eingeloggt
				else if (passwort.equals(current_client.getPassword()) && current_client.isLoggedin()) {
					serverobj.getServerLog().printOutput("VERBINDUNG\tAnmeldung fehlgeschlagen! Verbindung wird getrennt!");
					client.close();
					return false;
				}
				// falsches passwort
				else {
					serverobj.getServerLog().printOutput("VERBINDUNG\tAnmeldung fehlgeschlagen! Verbindung wird getrennt!");
					client.close();
					return false;
				}
			}
			i++;
		}
		// Schleife ist vollstÃ¤ndig durchgelaufen und es gab keinen Benutzernamenmatch
		// oder es ist noch niemand registriert--> Registrierung
		if ((i == listsize) || clients.isEmpty()) { // && !stop
			send_message("Willkommen auf dem Server!", "111", this.client);
			ClientNode newclient = new ClientNode(this.client, benutzername, passwort, true);
			clients.add(newclient);
			failed = false;
			serverobj.getServerLog().printOutput("USER\tNeue Registrierung von " + benutzername);
			return true;
		}
		return false;
	}

	void client_logout(Socket logout_client) {
		int listsize = clients.size();
		for (int i = 0; i < listsize; i++) {
			ClientNode current_client = clients.get(i);
			if (current_client.getClient() == logout_client) {
				current_client.setLoggedin(false);
			}
		}
	}

	void send_message(String message, String code, Socket curr_client) throws IOException {
		System.out.println(code + ' ' + message);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(curr_client.getOutputStream()));
		out.write(code);
		out.newLine();
		out.write(message);
		out.newLine();
		out.flush();
	}

	void message_all_clients(String message, String code) throws IOException {
		int listsize = clients.size();
		for (int i = 0; i < listsize; i++) {
			ClientNode current_client = clients.get(i);
			if (current_client.isLoggedin()) {
				send_message(message, code, current_client.getClient());
			}
		}
	}

	void message_all_clients_except_self(String message) throws IOException {
		int listsize = clients.size();
		for (int i = 0; i < listsize; i++) {
			ClientNode current_client = clients.get(i);
			if (current_client.getClient() != this.client && current_client.isLoggedin()) {
				send_message(message, "publicmsg", current_client.getClient());
			}
		}
	}

	/*
	 * public void send_client_list() throws IOException{ int listsize =
	 * clients.size(); for(int i = 0; i < listsize; i++){ ClientNode current_client
	 * = clients.get(i); if(current_client.isLoggedin() == true){ send_message("",
	 * "5", current_client.getClient()); } } }
	 */

	String[] get_message() throws IOException {
		if (client != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			return new String[] { in.readLine(), in.readLine() };
		} else {
			client_logout(client);
			return null;
		}
	}

	// neue Methode
	ArrayList<String> returnClientList() {
		int listsize = clients.size();
		ArrayList<String> clientsArray = new ArrayList<String>();
		int x = 0;
		for (int i = 0; i < listsize; i++) {
			ClientNode current_client = clients.get(i);
			if (current_client.isLoggedin()) {
				clientsArray.add(current_client.getName());
			}
		}
		return clientsArray;
	}

	// neue Methode
	void update_all_active_clients() throws IOException {
		int listsize = clients.size();
		for (int i = 0; i < listsize; i++) {
			ClientNode current_client = clients.get(i);
			if (current_client.getClient() != this.client && current_client.isLoggedin()) {
				send_message(current_client.getName(), "5", this.client);
			}
		}
	}

	public boolean getFailed() {
		return this.failed;
	}
}