import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/*import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;*/

public class Server {
  final JTextPane jtextFilDiscu = new JTextPane();
  final JTextPane jtextListUsers = new JTextPane();
  private int port;
  private List<User> clients;
  private ServerSocket server;
  private static Server test;
  private boolean xit = false, running = true, serveropen=true;

  public static void main(String[] args) throws IOException, InterruptedException {
    test = new Server(12345);
    test.run();
  }

  public Server(int port) {
    this.port = port;
    this.clients = new ArrayList<User>();
    String fontfamily = "Arial, sans-serif";
    Font font = new Font(fontfamily, Font.PLAIN, 15);

    final JFrame jfr = new JFrame("Server");
    jfr.getContentPane().setLayout(null);
    jfr.setSize(700, 500);
    jfr.setResizable(false);
    jfr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    jfr.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        serverMessage("cls");
        System.exit(0);
      }
    });

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

    // button Disconnect
    final JButton jsbtndeco = new JButton("Server Schließen");
    jsbtndeco.setFont(font);
    jsbtndeco.setBounds(520, 380, 156, 35);

    // Button Connect
    final JButton jcbtn = new JButton("Start");
    jcbtn.setFont(font);
    jcbtn.setBounds(25, 380, 100, 40);

    // Standardfarbe der Module Diskussionsthread und Benutzerliste
    jtextFilDiscu.setBackground(Color.WHITE);
    jtextListUsers.setBackground(Color.WHITE);

    // Elemente hinzufügen
    jfr.add(jtextFilDiscuSP);
    jfr.add(jsplistuser);
    jfr.add(jsbtndeco);
    jfr.add(jcbtn);
    jfr.setVisible(true);

    jsbtndeco.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        //closeServer();
        serveropen=false;
        xit = true;
        serverMessage("xit");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
            jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
            jtextFilDiscu.setText("");
            jtextListUsers.setBackground(Color.LIGHT_GRAY);
            jtextListUsers.setText("");
            closeServer();
          }
        });

        jcbtn.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ae){
            serveropen=true;
            jtextFilDiscu.setBackground(Color.WHITE);
            jtextListUsers.setBackground(Color.WHITE);
            //openServer();
        appendToPane(jtextFilDiscu, "Port 12345 is now open.");
          }
        } );
  }

  public void closeServer(){
    for (User client : this.clients) {
      removeUser(client);
    }
    }
  
    public void openServer() throws IOException, InterruptedException {
      test.run();
    }

  public boolean isRunning(){
    return running;
  }


  public void run() throws IOException, InterruptedException {
    server = new ServerSocket(port) {
      protected void finalize() throws IOException {
        this.close();
      }
    };
    System.out.println("Port 12345 is now open.");
    appendToPane(jtextFilDiscu, "Port 12345 is now open.");

    while (isRunning()) {
      // accepts a new client
      Socket client = server.accept();

      // get nickname of newUser
      String nickname = (new Scanner(client.getInputStream() )).nextLine();
      nickname = nickname.replace(",", ""); //  ',' use for serialisation
      nickname = nickname.replace(" ", "_");

      // create new User
      User newUser = new User(client, nickname);

      // add newUser message to list
      this.clients.add(newUser);
      if(serveropen){
        // Inform about status
        newUser.getOutStream().println("go" );
        Thread.sleep(500);
      // Welcome msg
      newUser.getOutStream().println("<b>Welcome</b> " + newUser.toString() );
      // create a new thread for newUser incoming messages handling
      new Thread(new UserHandler(this, newUser)).start();
      }else{
        newUser.getOutStream().println("no" );
        removeUser(newUser);
      }
    }
  }

  // delete a user from the list
  public void removeUser(User user){
    this.clients.remove(user);
  }

  // send incoming msg to all Users
  public void broadcastMessages(String msg, User userSender) {
    for (User client : this.clients) {
      client.getOutStream().println(
          userSender.toString() + "<span>: " + msg+"</span>");
    }
    appendToPane(jtextFilDiscu, userSender.toString() + "<span>: " + msg+"</span>");
  }

 public void serverMessage(String msg){
   for(User client:this.clients){
     client.getOutStream().println(msg);
   }
 }

  //HIER NICHTS Ändern
  // send list of clients to all Users
  public void broadcastAllUsers(){
    jtextListUsers.setText(null);
    for (User client : this.clients) {
      client.getOutStream().println(this.clients);

      appendToPane(jtextListUsers, "@" + client);
    }
  }

  // send message to a User (String)
  public void sendMessageToUser(String msg, User userSender, String user){
    boolean find = false;
    for (User client : this.clients) {
      if (client.getNickname().equals(user) && client != userSender) {
        find = true;
        userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() +": " + msg);
        appendToPane(jtextFilDiscu, userSender.toString() + " -> " + client.toString() +": " + msg);
        client.getOutStream().println(
            "(<b>Private</b>)" + userSender.toString() + "<span>: " + msg+"</span>");
      }
    }
    if (!find) {
      userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
    }
  }
  public void print(String message){
    appendToPane(jtextFilDiscu, message);
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

class UserHandler implements Runnable {

  private Server server;
  private User user;

  public UserHandler(Server server, User user) {
    this.server = server;
    this.user = user;
    this.server.broadcastAllUsers();
  }

  public void run() {
    String message;
    server.print(user+" hat sich eingeloggt.");

    // when there is a new message, broadcast to all
    Scanner sc = new Scanner(this.user.getInputStream());
    while (sc.hasNextLine()) {
      message = sc.nextLine();

      // smiley
     /* message = message.replace(":)", "<img src='http://4.bp.blogspot.com/-ZgtYQpXq0Yo/UZEDl_PJLhI/AAAAAAAADnk/2pgkDG-nlGs/s1600/facebook-smiley-face-for-comments.png'>");
      message = message.replace(":D", "<img src='http://2.bp.blogspot.com/-OsnLCK0vg6Y/UZD8pZha0NI/AAAAAAAADnY/sViYKsYof-w/s1600/big-smile-emoticon-for-facebook.png'>");
      message = message.replace(":d", "<img src='http://2.bp.blogspot.com/-OsnLCK0vg6Y/UZD8pZha0NI/AAAAAAAADnY/sViYKsYof-w/s1600/big-smile-emoticon-for-facebook.png'>");
      message = message.replace(":(", "<img src='http://2.bp.blogspot.com/-rnfZUujszZI/UZEFYJ269-I/AAAAAAAADnw/BbB-v_QWo1w/s1600/facebook-frown-emoticon.png'>");
      message = message.replace("-_-", "<img src='http://3.bp.blogspot.com/-wn2wPLAukW8/U1vy7Ol5aEI/AAAAAAAAGq0/f7C6-otIDY0/s1600/squinting-emoticon.png'>");
      message = message.replace(";)", "<img src='http://1.bp.blogspot.com/-lX5leyrnSb4/Tv5TjIVEKfI/AAAAAAAAAi0/GR6QxObL5kM/s400/wink%2Bemoticon.png'>");
      message = message.replace(":P", "<img src='http://4.bp.blogspot.com/-bTF2qiAqvi0/UZCuIO7xbOI/AAAAAAAADnI/GVx0hhhmM40/s1600/facebook-tongue-out-emoticon.png'>");
      message = message.replace(":p", "<img src='http://4.bp.blogspot.com/-bTF2qiAqvi0/UZCuIO7xbOI/AAAAAAAADnI/GVx0hhhmM40/s1600/facebook-tongue-out-emoticon.png'>");
      message = message.replace(":o", "<img src='http://1.bp.blogspot.com/-MB8OSM9zcmM/TvitChHcRRI/AAAAAAAAAiE/kdA6RbnbzFU/s400/surprised%2Bemoticon.png'>");
      message = message.replace(":O", "<img src='http://1.bp.blogspot.com/-MB8OSM9zcmM/TvitChHcRRI/AAAAAAAAAiE/kdA6RbnbzFU/s400/surprised%2Bemoticon.png'>");*/

      // private message
      if (message.charAt(0) == '@'){
        if(message.contains(" ")){
          System.out.println("private msg : " + message);
          int firstSpace = message.indexOf(" ");
          String userPrivate= message.substring(1, firstSpace);
          server.sendMessageToUser(
              message.substring(
                firstSpace+1, message.length()
                ), user, userPrivate
              );
        }

      // Farbänderung
     /* }else if (message.charAt(0) == '#'){
        user.changeColor(message);
        // update color for all other users
        this.server.broadcastAllUsers();*/
     /*}else if(message.charAt(0)=='#'){
       Server.appendToPane(jtextFileDiscu, d)*/
      }else{
        // update user list
        server.broadcastMessages(message, user);
      }
    }
    // end of Thread
    server.print(user+" hat sich ausgeloggt");
    server.removeUser(user);
    this.server.broadcastAllUsers();
    sc.close();
    Thread.currentThread().interrupt();
  }
}

class User {
  private static int nbUser = 0;
  private int userId;
  private PrintStream streamOut;
  private InputStream streamIn;
  private String nickname;
  private Socket client;
  private String color;

  // constructor
  public User(Socket client, String name) throws IOException {
    this.streamOut = new PrintStream(client.getOutputStream());
    this.streamIn = client.getInputStream();
    this.client = client;
    this.nickname = name;
    this.userId = nbUser;
    this.color = ColorInt.getColor(this.userId);
    nbUser += 1;
  }

  // change color user
 /* public void changeColor(String hexColor){
    // check if it's a valid hexColor
    Pattern colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
    Matcher m = colorPattern.matcher(hexColor);
    if (m.matches()){
      Color c = Color.decode(hexColor);
      // if the Color is too Bright don't change
      double luma = 0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue(); // per ITU-R BT.709
      if (luma > 160) {
        this.getOutStream().println("<b>Color Too Bright</b>");
        return;
      }
      this.color = hexColor;
      this.getOutStream().println("<b>Color changed successfully</b> " + this.toString());
      return;
    }
    this.getOutStream().println("<b>Failed to change color</b>");
  }*/

  // getteur
  public PrintStream getOutStream(){
    return this.streamOut;
  }

  public InputStream getInputStream(){
    return this.streamIn;
  }

  public String getNickname(){
    return this.nickname;
  }

  // print user with his color
  public String toString(){

    return "<u><span style='color:"+ this.color
      +"'>" + this.getNickname() + "</span></u>";

  }
}

class ColorInt {
    public static String[] mColors = {
            "#3079ab", // dark blue
            "#e15258", // red
            "#f9845b", // orange
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#f092b0", // pink
            "#e8d174", // yellow
            "#e39e54", // orange
            "#d64d4d", // red
            "#4d7358", // green
    };

    public static String getColor(int i) {
        return mColors[i % mColors.length];
    }
}
