package server.visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import server.Server;

public class ServerLog extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea tA_Clients;
	private JTextArea tA_Serverlog;
	private JButton button_shutdown;
	private Server server;

	public ServerLog(Server server) {
		this.server = server;
		setTitle("Serverlog");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(400, 300, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				server.shutdown();
		      }
		});
		
		button_shutdown = new JButton("Shutdown");
		button_shutdown.addActionListener(this);
		contentPane.add(button_shutdown, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tA_Serverlog = new JTextArea();
		tA_Serverlog.setEditable(false);
		scrollPane.setViewportView(tA_Serverlog);
		
		JLabel label_Left = new JLabel("Serveraktivit\u00E4ten");
		label_Left.setBorder(new EmptyBorder(3, 3, 3, 3));
		label_Left.setFont(label_Left.getFont().deriveFont(label_Left.getFont().getSize() + 1f));
		scrollPane.setColumnHeaderView(label_Left);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		tA_Clients = new JTextArea();
		tA_Clients.setEditable(false);
		scrollPane_1.setViewportView(tA_Clients);
		
		JLabel label_Right = new JLabel("Liste aktiver Clients");
		label_Right.setBorder(new EmptyBorder(3, 3, 3, 3));
		label_Right.setFont(label_Right.getFont().deriveFont(label_Right.getFont().getSize() + 1f));
		scrollPane_1.setColumnHeaderView(label_Right);
		
		setVisible(true);
	}
	
	public void printOutput(String s) {
		tA_Serverlog.append(s + "\n");
	}
	
	public void updateClientList(List<String> list) {
		tA_Clients.setText("");
		for (String s : list)
			tA_Clients.append(s + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		server.shutdown();
	}
}
