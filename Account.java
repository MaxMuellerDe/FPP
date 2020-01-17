import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Account extends JFrame implements Runnable {
	String accountname = null;
	String passwort = null;
	File account;
	JFrame fenster;
	boolean erfolg = false;

	public Account() {
		super("Login");
	}

	@Override
	public void run() {
		JTextField user = new JTextField("Benutzername");
		JPasswordField pass = new JPasswordField("Passwort");
		JButton login = new JButton("Login");
		fenster = new JFrame();
		fenster.setSize(500, 120);
		fenster.setLayout(new BorderLayout());
		fenster.add(user, BorderLayout.NORTH);
		fenster.add(pass, BorderLayout.CENTER);
		fenster.add(login, BorderLayout.SOUTH);
		fenster.setVisible(true);
		login.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String pw = new String(pass.getPassword());
					einLoggen(user.getText(), pw);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	public File neuerAccount() throws IOException {
		File file;
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Geben Sie Ihren neuen Accountnamen an:");
			this.accountname = scan.nextLine();
			file = new File("C:\\Users\\maxmu\\Google Drive\\A1 - Uni\\Jena\\Doc\\19WS FPP\\Meilenstein 3\\Accounts"
					+ this.accountname + ".txt");
			try {

				boolean existiert = file.createNewFile();
				if (existiert) {
					System.out.println("Account Erfolgreich erstellt!");
					break;
				} else {
					System.out.println("Diesen Account gibt es bereits!");
				}
			} catch (IOException e) {
				System.out.println("Fehler:");
				e.printStackTrace();
			}
		}
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);

		System.out.println("Geben Sie Ihr neues Passwort an:");
		Scanner scan2 = new Scanner(System.in);
		this.passwort = scan2.nextLine();
		pw.print(this.passwort);
		pw.close();
		this.account = file;
		System.out.println("Willkommen " + this.accountname + ", Sie k�nnen sich nun einloggen.");
		scan.close();
		scan2.close();
		return file;
	}

	public boolean einLoggen(String loginname, String passwort) throws IOException {
		boolean loginerfolg = false;
		this.accountname = loginname;
		this.passwort = passwort;
		while (loginerfolg == false) {
			File folder = new File("C:\\Users\\maxmu\\Google Drive\\A1 - Uni\\Jena\\Doc\\19WS FPP\\Meilenstein 3\\Accounts");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].getName().equals(loginname + ".txt")) {
					this.accountname = listOfFiles[i].getName().replace(".txt", "");
					passwortPruefen(listOfFiles[i]);
					this.account = listOfFiles[i];
					loginerfolg = true;
					return loginerfolg;
				}
			}
			File file = new File("C:\\Users\\maxmu\\Google Drive\\A1 - Uni\\Jena\\Doc\\19WS FPP\\Meilenstein 3\\Accounts"
			+ this.accountname + ".txt");
			file.createNewFile();
		}
		return false;
	}

	public boolean passwortPruefen(File account) throws IOException {
		boolean passwortkorrekt = false;

		while (passwortkorrekt == false) {

			if (this.passwort.length() == pruefePWLaenge(account)) {
				if (pruefePW(account) == false) {
					JOptionPane.showMessageDialog(null, "Falsches Passwort", "Fehler", JOptionPane.WARNING_MESSAGE);
					break;
				} else {
					fenster.setVisible(false);
					this.erfolg = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Falsches Passwort", "Fehler", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}
		return true;
	}

	public int pruefePWLaenge(File account) throws IOException {
		int laenge = 0;
		FileReader fr = new FileReader(account);

		int i;
		while ((i = fr.read()) != -1) {
			laenge++;
		}
		return laenge;
	}

	public boolean pruefePW(File account) throws IOException {
		
		int i;
		try(FileReader fr = new FileReader(account);) {
			for (int j = 0; j < this.passwort.length() & (i = fr.read()) != 10 & i != -1; j++) {
				if ((int) this.passwort.charAt(j) != i | (int) this.passwort.charAt(j) == -1) {
					return false;
				}
			}
		} catch (StringIndexOutOfBoundsException exception) {
			System.out.println("Falsches Passwort");
		}
		return true;
	}

	public String gibName() {
		return this.accountname;
	}

}
