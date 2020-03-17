package client;

import client.visual.FutternGUI;

public class Futtern extends Thread{
	private int size_x, size_y;
	private Client clientobj;
	private String opponent;
	private FutternGUI spiel;
	
	public Futtern(int size_x, int size_y, Client clientobj, String opponent) {
		this.size_x=size_x;
		this.size_y=size_y;
		this.clientobj=clientobj;
		this.opponent=opponent;
				
	}
	
	public void run() {
		spiel=new FutternGUI(size_x, size_y, clientobj, opponent);
		spiel.setVisible(true);
	}
}