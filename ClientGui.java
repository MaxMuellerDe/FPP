import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.*;

import java.util.ArrayList;
import java.util.Arrays;


public class ClientGui extends Thread{

  final JTextPane jtextFilDiscu = new JTextPane();
  final JTextPane jtextListUsers = new JTextPane();
  final JTextField jtextInputChat = new JTextField();
  private String oldMsg = "", servermsg="";
  private Thread read;
  private String serverName;
  private int PORT;
  private String name;
  String accountname = null;
	String passwort = null;
	File account;
	boolean serveropen=true,erfolg = false;
  BufferedReader input;
  PrintWriter output;
  Socket server;

  
  public static void main(String[] args) throws Exception {
    ClientGui client = new ClientGui();
  }

  public ClientGui(){
    this.serverName = "localhost";
    this.PORT = 12345;
    this.name = "nickname";

    String fontfamily = "Arial, sans-serif";
    Font font = new Font(fontfamily, Font.PLAIN, 15);

    final JFrame jfr = new JFrame("Chat");
    jfr.getContentPane().setLayout(null);
    jfr.setSize(700, 500);
    jfr.setResizable(false);
    jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Thread-Modul
    jtextFilDiscu.setBounds(25, 25, 490, 320);
    jtextFilDiscu.setFont(font);
    jtextFilDiscu.setMargin(new Insets(6, 6, 6, 6));
    jtextFilDiscu.setEditable(false);
    JScrollPane jtextFilDiscuSP = new JScrollPane(jtextFilDiscu);
    jtextFilDiscuSP.setBounds(25, 25, 490, 320);

    jtextFilDiscu.setContentType("text/html");
    jtextFilDiscu.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    // User List Modul
    jtextListUsers.setBounds(520, 25, 156, 320);
    jtextListUsers.setEditable(true);
    jtextListUsers.setFont(font);
    jtextListUsers.setMargin(new Insets(6, 6, 6, 6));
    jtextListUsers.setEditable(false);
    JScrollPane jsplistuser = new JScrollPane(jtextListUsers);
    jsplistuser.setBounds(520, 25, 156, 320);

    jtextListUsers.setContentType("text/html");
    jtextListUsers.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    // Field message user input
    jtextInputChat.setBounds(0, 350, 400, 50);
    jtextInputChat.setFont(font);
    jtextInputChat.setMargin(new Insets(6, 6, 6, 6));
    final JScrollPane jtextInputChatSP = new JScrollPane(jtextInputChat);
    jtextInputChatSP.setBounds(25, 350, 650, 50);

    // button send
    final JButton jsbtn = new JButton("Send");
    jsbtn.setFont(font);
    jsbtn.setBounds(575, 410, 100, 35);

    // button Disconnect
    final JButton jsbtndeco = new JButton("Disconnect");
    jsbtndeco.setFont(font);
    jsbtndeco.setBounds(25, 410, 130, 35);

    jtextInputChat.addKeyListener(new KeyAdapter() {
      // send message on Enter
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          sendMessage();
        }

        // Get last message typed
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          String currentMessage = jtextInputChat.getText().trim();
          jtextInputChat.setText(oldMsg);
          oldMsg = currentMessage;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          String currentMessage = jtextInputChat.getText().trim();
          jtextInputChat.setText(oldMsg);
          oldMsg = currentMessage;
        }
      }
    });

    // Click on send button
    jsbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        sendMessage();
      }
    });

    // Connection view
    final JTextField jtfName = new JTextField(this.name);
    final JPasswordField jtfPassword = new JPasswordField("Passwort");
    final JTextField jtfport = new JTextField(Integer.toString(this.PORT));
    final JTextField jtfAddr = new JTextField(this.serverName);
    final JButton jcbtn = new JButton("Connect");

    // check if those field are not empty
    jtfName.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
    jtfport.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
    jtfAddr.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));

    // Modulpositionen
    jcbtn.setFont(font);
    jtfAddr.setBounds(25, 380, 100, 40);
    jtfport.setBounds(155, 380, 100, 40);
    jtfName.setBounds(285, 380, 100, 40);
    jtfPassword.setBounds(415, 380, 100, 40);
    jcbtn.setBounds(575, 380, 100, 40);

    // Standardfarbe der Module Diskussionsthread und Benutzerliste
    jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
    jtextListUsers.setBackground(Color.LIGHT_GRAY);

    // Elemente hinzufügen
    jfr.add(jcbtn);
    jfr.add(jtfPassword);
    jfr.add(jtextFilDiscuSP);
    jfr.add(jsplistuser);
    jfr.add(jtfName);
    jfr.add(jtfport);
    jfr.add(jtfAddr);
    jfr.setVisible(true);
    jtfport.setVisible(false);
    jtfAddr.setVisible(false);


    // Chatinformationen
    appendToPane(jtextFilDiscu, "<h4>Folgende Befehle sind möglich::</h4>"
        +"<ul>"
        +"<li><b>@nickname</b>Sende eine Privatnachricht an einen anderen Nutzer.</li>"
        /*+"<li><b>#d3961b</b> Änderung der Farbe</li>"
        +"<li><b>;)</b> Ersetzen von Smileys</li>"
        +"<li><b>Pfeil nach oben</b> Zuletzt eingegebene Nachricht fortsetzen</li>"*/
        +"</ul><br/>");

    // on disconnect
    ActionListener listen=new ActionListener()  {
      public void actionPerformed(ActionEvent ae) {
        jfr.setTitle("Chat");
        jfr.add(jtfName);
        jfr.add(jtfPassword);
        jfr.add(jtfport);
        jfr.add(jtfAddr);
        jfr.add(jcbtn);
        jfr.remove(jsbtn);
        jfr.remove(jtextInputChatSP);
        jfr.remove(jsbtndeco);
        jfr.revalidate();
        jfr.repaint();
        read.interrupt();
        jtextListUsers.setText(null);
        jtextFilDiscu.setBackground(Color.GRAY);
        jtextListUsers.setBackground(Color.GRAY);
        appendToPane(jtextFilDiscu, "<span>Connection closed.</span>");
        output.close();
      }
    };

    jsbtndeco.addActionListener(listen);

    // On connect
    jcbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        if(serveropen){
        try {
          name = jtfName.getText();
          //String password = new String( jtfPassword.getPassword());
          String port = jtfport.getText();
          serverName = jtfAddr.getText();
          PORT = Integer.parseInt(port);
          //Account konto=new Account();
          
          if(true){

          //appendToPane(jtextFilDiscu, "<span>Connecting to " + serverName + " on port " + PORT + "...</span>");
          server = new Socket(serverName, PORT);

          //appendToPane(jtextFilDiscu, "<span>Connected to " + server.getRemoteSocketAddress()+"</span>");

          input = new BufferedReader(new InputStreamReader(server.getInputStream()));
          output = new PrintWriter(server.getOutputStream(), true);

          // send nickname to server
          output.println(name);

          servermsg=input.readLine();
          if(servermsg!=null&&servermsg.equals("go")){
          // create new Read Thread
          read = new Read(listen);
          read.start();
          jfr.setTitle(name);
          jfr.remove(jtfName);
          jfr.remove(jtfPassword);
          jfr.remove(jtfport);
          jfr.remove(jtfAddr);
          jfr.remove(jcbtn);
          jfr.add(jsbtn);
          jfr.add(jtextInputChatSP);
          jfr.add(jsbtndeco);
          jfr.revalidate();
          jfr.repaint();
          jtextFilDiscu.setBackground(Color.WHITE);
          jtextListUsers.setBackground(Color.WHITE);
          }else{
            appendToPane(jtextFilDiscu, "Server ist geschlossen");
          }
        }
        } catch (Exception ex) {
          appendToPane(jtextFilDiscu, "<span>Der Server ist geschlossen</span>");
        }
      }else{
      appendToPane(jtextFilDiscu, "The Server is still closed.");
      }
      }
    });



  }

  // check if if all field are not empty
  public class TextListener implements DocumentListener{
    JTextField jtf1;
    JTextField jtf2;
    JTextField jtf3;
    JButton jcbtn;

    public TextListener(JTextField jtf1, JTextField jtf2, JTextField jtf3, JButton jcbtn){
      this.jtf1 = jtf1;
      this.jtf2 = jtf2;
      this.jtf3 = jtf3;
      this.jcbtn = jcbtn;
    }

    public void changedUpdate(DocumentEvent e) {}

    public void removeUpdate(DocumentEvent e) {
      if(jtf1.getText().trim().equals("") ||
          jtf2.getText().trim().equals("") ||
          jtf3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }
    public void insertUpdate(DocumentEvent e) {
      if(jtf1.getText().trim().equals("") ||
          jtf2.getText().trim().equals("") ||
          jtf3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }

  }

  // Nachrichten senden
  public void sendMessage() {
    try {
      String message = jtextInputChat.getText().trim();
      if (message.equals("")) {
        return;
      }
      this.oldMsg = message;
      output.println(message);
      jtextInputChat.requestFocus();
      jtextInputChat.setText(null);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
      System.exit(0);
    }
  }

  // read new incoming messages
  class Read extends Thread {
    private ActionListener close;
    public Read(ActionListener close){
      this.close=close;
    }
    public void run() {
      String message;
      while(!Thread.currentThread().isInterrupted()){
        try {
          message = input.readLine();
          if(message != null&&!message.equals("cls")&&!message.equals("op")&&!message.equals("xit")){
            if (message.charAt(0) == '[') {
              message = message.substring(1, message.length()-1);
              ArrayList<String> ListUser = new ArrayList<String>(
                  Arrays.asList(message.split(", "))
                  );
              jtextListUsers.setText(null);
              for (String user : ListUser) {
                appendToPane(jtextListUsers, "@" + user);
              }
            }else{
              appendToPane(jtextFilDiscu, message);
            }
          }else if(message.equals("xit")){
            appendToPane(jtextFilDiscu, "Der Server wurde geschlossen");
            Thread.currentThread().interrupt();
            //serveropen=false;
            this.close.actionPerformed(null);
          }else if(message.equals("op")){
            appendToPane(jtextFilDiscu, "Der Server ist offen");
            //serveropen=true;
          }else if(message.equals("cls")){
            System.exit(0);
          }
        }
        catch (IOException ex) {
          System.err.println("Failed to parse incoming message");
          appendToPane(jtextFilDiscu, "Der Server wurde geschlossen");
          this.close.actionPerformed(null);
        }
      }
    }
  }

  // send html to pane
  private void appendToPane(JTextPane tp, String msg){
    HTMLDocument doc = (HTMLDocument)tp.getDocument();
    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
    try {
      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
      tp.setCaretPosition(doc.getLength());
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
