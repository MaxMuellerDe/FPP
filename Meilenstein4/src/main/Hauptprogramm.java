package main;
import client.Client;
import server.Server;

public class Hauptprogramm {
	public static void main(String[] args) throws InterruptedException {
		Server.main(null);
		Thread.sleep(1000);
		Client.main(null);
		Client.main(null);
		//Client.main(null);
		//Client.main(null);
	}
}
