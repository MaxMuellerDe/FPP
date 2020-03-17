package client;

import client.visual.TicTacToeGUI;

public class TicTacToe extends Thread{
	private Client clientobj;
	private String opponent;
	public TicTacToeGUI spiel2=null;
	private boolean turn;
	
	public TicTacToe(Client clientobj, String opponent, boolean turn) {
		this.clientobj=clientobj;
		this.opponent=opponent;
		this.turn=turn;
	}
	
	public void run() {
		spiel2=new TicTacToeGUI(3, 3, clientobj, opponent, turn);
		spiel2.setVisible(true);
	}
	


	public void update(int x, int y) {
		TicTacToeGUI spiel2=this.spiel2;
		// TODO Auto-generated method stub
		clientobj.getClientWindow().updateTextArea("Stufe 2");
		if(spiel2!=null) {
		spiel2.update(x, y);
		}
	}
}