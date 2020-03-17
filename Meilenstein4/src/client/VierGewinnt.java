package client;

import client.visual.VierGewinntGUI;

public class VierGewinnt extends Thread{
	private int size_x, size_y;
	private Client clientobj;
	private String opponent;
	private VierGewinntGUI spiel;
	
	public VierGewinnt(int size_x, int size_y, Client clientobj, String opponent) {
		this.size_x=size_x;
		this.size_y=size_y;
		this.clientobj=clientobj;
		this.opponent=opponent;
				
	}
	
	public void run() {
		spiel=new VierGewinntGUI(size_x, size_y, clientobj, opponent);
		spiel.setVisible(true);
	}
}
