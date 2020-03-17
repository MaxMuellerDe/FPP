package client.visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.Futtern;
import client.TicTacToe;
import client.VierGewinnt;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class ClientChallenge extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblWhleDasSpiel;
	private JButton btnBesttigen;
	private JLabel lblWhleDeinenGegner;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JLabel lblNewLabel;
	private JSpinner spinner_width;
	private JLabel lblNewLabel_1;
	private JSpinner spinner_height;
	private Client clientobj;
	private String opponent;

	public ClientChallenge(Client clientobj) {
		this.clientobj = clientobj;
		ArrayList<String> clientList = new ArrayList<String>(clientobj.clients);
		setTitle("Spieler herausfordern");
		setBounds(100, 100, 450, 258);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 87, 86, 0 };
		gbl_contentPanel.rowHeights = new int[] { 59, 60, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			lblWhleDeinenGegner = new JLabel("Wähle deinen Gegner:");
			lblWhleDeinenGegner
					.setFont(lblWhleDeinenGegner.getFont().deriveFont(lblWhleDeinenGegner.getFont().getSize() + 1f));
			lblWhleDeinenGegner.setHorizontalAlignment(SwingConstants.CENTER);
		}
		GridBagConstraints gbc_lblWhleDeinenGegner = new GridBagConstraints();
		gbc_lblWhleDeinenGegner.fill = GridBagConstraints.BOTH;
		gbc_lblWhleDeinenGegner.insets = new Insets(0, 0, 5, 5);
		gbc_lblWhleDeinenGegner.gridx = 0;
		gbc_lblWhleDeinenGegner.gridy = 0;
		contentPanel.add(lblWhleDeinenGegner, gbc_lblWhleDeinenGegner);
		{
			lblWhleDasSpiel = new JLabel("W\u00E4hle das Spiel:");
			lblWhleDasSpiel.setFont(lblWhleDasSpiel.getFont().deriveFont(lblWhleDasSpiel.getFont().getSize() + 1f));
			lblWhleDasSpiel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			comboBox = new JComboBox<String>();
			if (clientobj.clients.size() > 1) {
				clientList.remove(clientobj.getUsername());
				comboBox.setModel(new DefaultComboBoxModel<String>(new Vector<String>(clientList)));
			}
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		GridBagConstraints gbc_lblWhleDasSpiel = new GridBagConstraints();
		gbc_lblWhleDasSpiel.fill = GridBagConstraints.BOTH;
		gbc_lblWhleDasSpiel.insets = new Insets(0, 0, 5, 5);
		gbc_lblWhleDasSpiel.gridx = 0;
		gbc_lblWhleDasSpiel.gridy = 1;
		contentPanel.add(lblWhleDasSpiel, gbc_lblWhleDasSpiel);
		{
			comboBox_1 = new JComboBox();
			comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Vier gewinnt", "Futtern", "TicTacToe"}));
			GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
			gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_1.gridx = 1;
			gbc_comboBox_1.gridy = 1;
			contentPanel.add(comboBox_1, gbc_comboBox_1);
		}
		{
			lblNewLabel = new JLabel("Breite:");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getSize() + 1f));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 2;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			spinner_width = new JSpinner();
			spinner_width.setModel(new SpinnerNumberModel(5, 4, 9, 1));
			GridBagConstraints gbc_spinner_width = new GridBagConstraints();
			gbc_spinner_width.insets = new Insets(0, 0, 5, 0);
			gbc_spinner_width.gridx = 1;
			gbc_spinner_width.gridy = 2;
			contentPanel.add(spinner_width, gbc_spinner_width);
		}
		{
			lblNewLabel_1 = new JLabel("H\u00F6he:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(lblNewLabel_1.getFont().deriveFont(lblNewLabel_1.getFont().getSize() + 1f));
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 3;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			spinner_height = new JSpinner();
			spinner_height.setModel(new SpinnerNumberModel(5, 4, 9, 1));
			GridBagConstraints gbc_spinner_height = new GridBagConstraints();
			gbc_spinner_height.gridx = 1;
			gbc_spinner_height.gridy = 3;
			contentPanel.add(spinner_height, gbc_spinner_height);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnBesttigen = new JButton("Best\u00E4tigen");
				btnBesttigen.addActionListener(this);
				buttonPane.add(btnBesttigen);
				getRootPane().setDefaultButton(btnBesttigen);
			}
		}
	}
//TODO: Add Game Opener 
	@Override
	public void actionPerformed(ActionEvent e){
		int listsize = clientobj.clients.size();
		for (int i = 0; i < listsize; i++) {
			if(clientobj.clients.get(i).equals((String)comboBox.getSelectedItem())) {
				opponent=clientobj.clients.get(i);
			}
		}
		
		if (comboBox_1.getSelectedIndex() == 0) {
			// Vier gewinnt
			//VierGewinnt spiel=new VierGewinnt((String)spinner_width.getValue(),(String)spinner_height.getValue(),clientobj, opponent);
			//spiel.start();
			try {
				clientobj.send_private_message("c 0"+(String)spinner_width.getValue().toString()+(String)spinner_height.getValue().toString(), "4", opponent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();

		} else if (comboBox_1.getSelectedIndex()==1){
			// Futtern
			try {
				clientobj.send_private_message("c 1"+spinner_width.getValue().toString()+spinner_height.getValue().toString(), "4", opponent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();

		} else if(comboBox_1.getSelectedIndex()==2) {
			//Tic Tac Toe
			try {
				clientobj.send_private_message("c 2", "4", opponent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();

		}
	}
}
