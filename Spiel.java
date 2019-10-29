import java.util.*;
public abstract class Spiel {

    Scanner scan = new Scanner(System.in);

    System.out.println("Wie ist dein Name?");
    //name annehmen
    System.out.println("Multiplayer oder gegen CPU? (1 für Multiplayer, 0 für CPU)");
    Spieler spieler1 = new Spieler(scan.nextLine(), scan.nextInt());


    //1 oder 0
    System.out.println("Spieler 2, wie ist dein Name?");
    Spieler spieler2 = new Spieler(scan.nextLine(), scan.nextInt());

    public abstract void spielzug();
    public abstract int durchgang();

    //spielfeld

}