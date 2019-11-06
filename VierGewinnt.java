import java.util.*;
public class VierGewinnt extends Spiel implements Protokollierbar{

    Scanner scan =  new Scanner(System.in);

    VierGewinntSpielfeld feld = new VierGewinntSpielfeld(6, 7);


    //Spielstart

    public void start(){
        System.out.println("Willkommen zu VierGewinnt");
    }

    //System.out.println("Wie ist dein Name?");
//    //name annehmen
//    System.out.println("Multiplayer oder gegen CPU? (1 für Multiplayer, 0 für CPU)");
//    Spieler spieler1 = new Spieler(scan.nextLine(), scan.nextInt());
//
//
//    //1 oder 0
//    System.out.println("Spieler 2, wie ist dein Name?");
//    Spieler spieler2 = new Spieler(scan.nextLine(), scan.nextInt());




    String signatur;
    @Override
    public void spielzug() {
        signatur = Spieler.name; //plus feld in das stein gesetzt wird
        feld.draw();
    }

    public String getSignatur() {
        return signatur;
    }

    public void setSignatur(String signatur) {
        this.signatur = signatur;
    }


    //Zustandsvariable zum Protokollieren der Zuege
    Stack zuege = new Stack();

    @Override
    public void addSpielzug() {
        zuege.push(signatur);
    }

    @Override
    public void removeSpielzug() {

    }


    @Override
    public int durchgang() {
        return 0;
    }
}