package client.visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;

@SuppressWarnings("serial")
public class ClientLogin extends JFrame implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField tF_user;
	private JLabel label_pass;
	private JPasswordField tF_pass;
	private JButton button_Login;
	private JLabel label_nutzer;
	private Client clientobj;

	public ClientLogin(Client clientobj) {
		this.clientobj = clientobj;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Anmelden");
		setBounds(100, 100, 450, 194);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 87, 86, 0 };
		gbl_contentPanel.rowHeights = new int[] { 59, 60, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			label_nutzer = new JLabel("Nutzername:");
			label_nutzer.setFont(label_nutzer.getFont().deriveFont(label_nutzer.getFont().getSize() + 1f));
			label_nutzer.setHorizontalAlignment(SwingConstants.CENTER);
		}
		GridBagConstraints gbc_label_nutzer = new GridBagConstraints();
		gbc_label_nutzer.fill = GridBagConstraints.BOTH;
		gbc_label_nutzer.insets = new Insets(0, 0, 5, 5);
		gbc_label_nutzer.gridx = 0;
		gbc_label_nutzer.gridy = 0;
		contentPanel.add(label_nutzer, gbc_label_nutzer);
		{
			tF_user = new JTextField();
			tF_user.setColumns(10);
		}
		GridBagConstraints gbc_tF_user = new GridBagConstraints();
		gbc_tF_user.fill = GridBagConstraints.HORIZONTAL;
		gbc_tF_user.insets = new Insets(0, 0, 5, 0);
		gbc_tF_user.gridx = 1;
		gbc_tF_user.gridy = 0;
		contentPanel.add(tF_user, gbc_tF_user);
		{
			label_pass = new JLabel("Passwort:");
			label_pass.setFont(label_pass.getFont().deriveFont(label_pass.getFont().getSize() + 1f));
			label_pass.setHorizontalAlignment(SwingConstants.CENTER);
		}
		GridBagConstraints gbc_label_pass = new GridBagConstraints();
		gbc_label_pass.fill = GridBagConstraints.BOTH;
		gbc_label_pass.insets = new Insets(0, 0, 0, 5);
		gbc_label_pass.gridx = 0;
		gbc_label_pass.gridy = 1;
		contentPanel.add(label_pass, gbc_label_pass);
		{
			tF_pass = new JPasswordField();
			GridBagConstraints gbc_tF_pass = new GridBagConstraints();
			gbc_tF_pass.fill = GridBagConstraints.HORIZONTAL;
			gbc_tF_pass.gridx = 1;
			gbc_tF_pass.gridy = 1;
			contentPanel.add(tF_pass, gbc_tF_pass);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				button_Login = new JButton("Login");
				button_Login.addActionListener(this);
				buttonPane.add(button_Login);
				getRootPane().setDefaultButton(button_Login);
			}
		}
	}
//TODO: Enter ?
	@Override
	public void actionPerformed(ActionEvent e) {
		if (tF_user.getText().equals("") || new String(tF_pass.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "Fehler. Benutzername und Passwort dürfen nicht leer sein.", "Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			clientobj.login(tF_user.getText(), new String(tF_pass.getPassword()));
		}
	}
}
