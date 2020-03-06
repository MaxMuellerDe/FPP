package client.visual;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.Client;

public class ClientWindow extends JFrame implements ActionListener {

	private Client clientobj;
	private JPanel contentPane;
	private JTextArea tA_Clients;
	private JTextArea tA_Serverlog;
	private JTextField textField;
	private JButton button_Logout;
	private JButton btnChallengePlayer;
	private JButton button_Senden;

	public ClientWindow(Client clientobj) {
		this.clientobj = clientobj;
		setTitle("Chat");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(400, 300, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		tA_Serverlog = new JTextArea();
		tA_Serverlog.setEditable(false);
		scrollPane.setViewportView(tA_Serverlog);

		JLabel lblChat = new JLabel("Chat");
		lblChat.setBorder(new EmptyBorder(3, 3, 3, 3));
		lblChat.setFont(lblChat.getFont().deriveFont(lblChat.getFont().getSize() + 1f));
		scrollPane.setColumnHeaderView(lblChat);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		tA_Clients = new JTextArea();
		tA_Clients.setEditable(false);
		scrollPane_1.setViewportView(tA_Clients);

		JLabel label_Right = new JLabel("Gerade online");
		label_Right.setBorder(new EmptyBorder(3, 3, 3, 3));
		label_Right.setFont(label_Right.getFont().deriveFont(label_Right.getFont().getSize() + 1f));
		scrollPane_1.setColumnHeaderView(label_Right);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		button_Senden = new JButton("Senden");
		button_Senden.addActionListener(this);
		GridBagConstraints gbc_button_Senden = new GridBagConstraints();
		gbc_button_Senden.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_Senden.insets = new Insets(0, 0, 5, 0);
		gbc_button_Senden.gridx = 2;
		gbc_button_Senden.gridy = 0;
		panel.add(button_Senden, gbc_button_Senden);

		button_Logout = new JButton("Logout");
		button_Logout.addActionListener(this);
		GridBagConstraints gbc_button_Logout = new GridBagConstraints();
		gbc_button_Logout.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_Logout.insets = new Insets(0, 0, 0, 5);
		gbc_button_Logout.gridx = 0;
		gbc_button_Logout.gridy = 1;
		panel.add(button_Logout, gbc_button_Logout);

		btnChallengePlayer = new JButton("Spieler herausfordern");
		btnChallengePlayer.addActionListener(this);
		GridBagConstraints gbc_btnChallengePlayer = new GridBagConstraints();
		gbc_btnChallengePlayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChallengePlayer.gridx = 2;
		gbc_btnChallengePlayer.gridy = 1;
		panel.add(btnChallengePlayer, gbc_btnChallengePlayer);
	}

	public void updateTextArea(String append) {
		tA_Serverlog.append(append + "\n");
	}

	public void updateClientList() {
		tA_Clients.setText("");
		for (String s : clientobj.clients)
			tA_Clients.append(s + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnChallengePlayer)) {
			ClientChallenge cc = new ClientChallenge(clientobj);
			cc.setVisible(true);
		} else if (e.getSource().equals(button_Logout)) {
			clientobj.logout();
			dispose();
		} else if (e.getSource().equals(button_Senden)) {
			clientobj.sendMessage(textField.getText());
			textField.setText("");
		}
	}
}
