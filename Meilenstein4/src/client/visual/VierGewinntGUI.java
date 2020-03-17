package client.visual;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

@SuppressWarnings("serial")
public class VierGewinntGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int size_x = 10, size_y = 10;
	private final int width = 40, height = 40;
	private Client clientobj;

	JButton[][] buttons = new JButton[size_x][size_y];

	public VierGewinntGUI(int size_x, int size_y, Client clientobj, String opponent) {
		this.size_x = size_x;
		this.size_y = size_y;
		this.clientobj=clientobj;
		setTitle("Vier Gewinnt: "+clientobj.getUsername()+" vs. "+opponent);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(9, 9, 9, 9));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		int[] gbl_x_array = new int[size_x + 1];
		double[] gbl_x_array_weight = new double[size_x + 1];
		for (int x = 0; x < size_x; x++) {
			gbl_x_array[x] = width;
			gbl_x_array_weight[x] = 0.0;
		}
		gbl_x_array[size_x] = 0;
		gbl_x_array_weight[size_x] = Double.MIN_VALUE;
		gbl_contentPane.columnWidths = gbl_x_array;
		int[] gbl_y_array = new int[size_y + 1];
		double[] gbl_y_array_weight = new double[size_y + 1];
		for (int y = 0; y < size_y; y++) {
			gbl_y_array[y] = height;
			gbl_y_array_weight[y] = 0.0;
		}
		gbl_y_array[size_y] = 0;
		gbl_y_array_weight[size_y] = Double.MIN_VALUE;
		gbl_contentPane.rowHeights = gbl_y_array;
		gbl_contentPane.columnWeights = gbl_x_array_weight;
		gbl_contentPane.rowWeights = gbl_y_array_weight;
		contentPane.setLayout(gbl_contentPane);

		for (int x = 0; x < size_x; x++) {
			for (int y = 0; y < size_y; y++) {
				JButton button = new JButton("");
				button.addActionListener(this);
				button.setOpaque(true);
				button.setActionCommand(x + "," + y);
				button.setFocusPainted(false);
				buttons[x][y] = button;
				GridBagConstraints gbc_btn = new GridBagConstraints();
				gbc_btn.fill = GridBagConstraints.BOTH;
				gbc_btn.insets = new Insets(0, 0, 0, 0);
				gbc_btn.gridx = x;
				gbc_btn.gridy = y;
				contentPane.add(button, gbc_btn);
			}
		}
		pack();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		int x = Integer.valueOf(cmd.split(",")[0]);
		int y = Integer.valueOf(cmd.split(",")[1]);
		buttons[x][y].setBackground(Color.red);
		
//		int answer = JOptionPane.showConfirmDialog(null, "NAME hat dich herausgefordert f�r Snake! Annehmen?", "Herausforderung", JOptionPane.YES_NO_OPTION);
//		if (answer == JOptionPane.YES_OPTION) {
//			
//		} else {
//			
//		}
	}
}
