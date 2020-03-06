package client;

import java.io.BufferedReader;

public class MessageListener extends Thread {
	private BufferedReader in;
	private Client clientobj;
	private boolean loggedIn = true;

	MessageListener(BufferedReader in, Client clientobj) {
		this.in = in;
		this.clientobj = clientobj;
	}

	public boolean getLoggedIn() {
		return this.loggedIn;
	}

	public void run() {
		try {
			while (true) {
				String input = in.readLine();
				System.out.println(input);
				switch (input) {
				case "0":
					clientobj.getClientWindow().updateTextArea(in.readLine());
					break;
				case "4":
					System.exit(0);
				case "5":
					// Neuer Login wird in ClientListe aufgenommen
					System.out.println("New Login");
					clientobj.addClient(in.readLine());
					clientobj.getClientWindow().updateClientList();
					break;
				case "6":
					// Logout muss aus ClientListe entfernt werden
					String username = in.readLine();
					System.out.println(username);
					clientobj.deleteClient(username);
					clientobj.getClientWindow().updateClientList();
					break;
				case "7":
					// Fehlgeschlagener Login
					clientobj.getClientWindow()
							.updateTextArea("Der Login ist fehlgeschlagen! Das Fenster kann geschlossen werden!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}