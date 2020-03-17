package client;

import java.io.BufferedReader;

import javax.swing.JOptionPane;


public class MessageListener extends Thread {
	private int height=3, width=3, firstSpace, gameint, x, y;
	private String game, chal, opponent, zug;
	private BufferedReader in;
	private Client clientobj;
	private boolean loggedIn = true;
	public TicTacToe spiel2=null;

	MessageListener(BufferedReader in, Client clientobj) {
		this.in = in;
		this.clientobj = clientobj;
	}

	public boolean getLoggedIn() {
		return this.loggedIn;
	}
//TODO: Extra Cases
	public void run() {
		try {
			while (true) {
				String input = in.readLine();
				System.out.println(input);
				switch (input) {
				case "0":
					clientobj.getClientWindow().updateTextArea(in.readLine());
					break;
				case "1":
					chal=in.readLine();
					firstSpace=chal.indexOf(" ");
					opponent=chal.substring(0, firstSpace);
					gameint=Integer.parseInt(chal.substring(firstSpace+1, firstSpace+2));
					
					if(gameint==0) {
						game="VierGewinnt";
						width=Integer.parseInt(chal.substring(firstSpace+2, firstSpace+3));
						height=Integer.parseInt(chal.substring(firstSpace+3, firstSpace+4));
					}else if(gameint==1) {
						game="Futtern";
						width=Integer.parseInt(chal.substring(firstSpace+2, firstSpace+3));
						height=Integer.parseInt(chal.substring(firstSpace+3, firstSpace+4));
					}else {
						game="TicTacToe";
					}

					int answer = JOptionPane.showConfirmDialog(null, opponent+" hat dich herausgefordert für "+game+" - Annehmen?", "Herausforderung", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
						clientobj.send_private_message("g "+Integer.toString(gameint)+Integer.toString(width)+Integer.toString(height), "4", opponent);
						if(gameint==0) {
							VierGewinnt spiel0=new VierGewinnt(width, height,clientobj, opponent);
							spiel0.start();
						}else if(gameint==1) {
							Futtern spiel1=new Futtern(width, height,clientobj, opponent);
							spiel1.start();
						}else {
							clientobj.createTicTacToe(opponent, false);
						}
					} else {
						
					}
					break;
				case "2":
					chal=in.readLine();
					firstSpace=chal.indexOf(" ");
					opponent=chal.substring(0, firstSpace);
					gameint=Integer.parseInt(chal.substring(firstSpace+1, firstSpace+2));
					
					if(gameint==0) {
						VierGewinnt spiel0=new VierGewinnt(Integer.parseInt(chal.substring(firstSpace+2, firstSpace+3)), Integer.parseInt(chal.substring(firstSpace+3, firstSpace+4)),clientobj, opponent);
						spiel0.start();
					}else if(gameint==1) {
						Futtern spiel1=new Futtern(Integer.parseInt(chal.substring(firstSpace+2, firstSpace+3)), Integer.parseInt(chal.substring(firstSpace+3, firstSpace+4)),clientobj, opponent);
						spiel1.start();
					}else {
						clientobj.createTicTacToe(opponent, true);
					}
					break;
				case "3":
					zug=in.readLine();
					x=Integer.parseInt(zug.substring(0, 1));
					y=Integer.parseInt(zug.substring(1, 2));
					clientobj.getClientWindow().updateTextArea(zug+Integer.toString(x)+Integer.toString(y));
					clientobj.update(x, y);
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
				case "8":
					//Spieleinladung
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}

/*
stuff



			

*/