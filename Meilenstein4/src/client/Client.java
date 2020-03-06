package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import client.visual.ClientLogin;
import client.visual.ClientWindow;
import server.Session;

public class Client {

	private ClientWindow clientWindow;
	private Client clientobj;
	private Socket server;
	private ClientLogin login;
	private Session session;
	private String user;
	private MessageListener messages;
	public ArrayList<String> clients;

	public Client() {
		clientobj = this;
		loginWindow();
	}

	public void loginWindow() {
		login = new ClientLogin(this);
		login.setVisible(true);
	}

	public void login(String user, String pass) {
		this.user = user;
		login.dispose();
		try {
			connectToServer();
			session = new Session();
			createMessageListener();
			clients = new ArrayList<>();
			clientWindow = new ClientWindow(this);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			out.write(user);
			out.newLine();
			out.flush();
			out.write(pass);
			out.newLine();
			out.flush();
			clientWindow.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return user;
	}

	public void logout() {
		if (server != null) {
			try {
				send_server_message("3", "3");
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void send_server_message(String message, String code) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
		out.write(code);
		out.newLine();
		out.write(message);
		out.newLine();
		out.flush();
	}

	public void sendMessage(String s) {
		try {
			if (server != null) {
				send_server_message(s, "2");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fehler. Nicht mit dem Server verbunden.", "Fehler!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void connectToServer() {
		try {
			server = new Socket("localhost", 9876);
		} catch (UnknownHostException e) {
			System.out.println("Cannot find host.");
		} catch (IOException e) {
			System.out.println("Error connecting to host.");
		}
	}

	private void createMessageListener() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		messages = new MessageListener(in, this);
		messages.start();
	}

	void deleteClient(String newClient) {
		clients.remove(newClient);
		clientWindow.updateClientList();
	}

	void addClient(String newClient) {
		clients.add(newClient);
		clientWindow.updateClientList();
	}

	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientWindow getClientWindow() {
		return clientWindow;
	}
}
