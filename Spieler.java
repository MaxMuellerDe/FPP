import java.util.*;
public class Spieler{

    Scanner scan = new Scanner(System.in);

    String name;
    boolean istMensch;



    //Konstruktor Spieler
    public Spieler(String name, boolean istMensch) {
        this.name = name;
        this.istMensch = istMensch;
    }

    //getter und setter fuer name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //getter und setter fuer istMensch
    public boolean getIstMensch() {
        return istMensch;
    }

    public void setIstMensch(boolean istMensch) {
        this.istMensch = istMensch;
    }

//    System.out.println("Wie ist dein Name?");
//    //name annehmen
//    System.out.println("Multiplayer oder gegen CPU? (1 für Multiplayer, 0 für CPU)");
//    Spieler spieler1 = new Spieler(scan.nextLine(), scan.nextInt());
//
//
//    //1 oder 0
//    System.out.println("Spieler 2, wie ist dein Name?");
//    Spieler spieler2 = new Spieler(scan.nextLine(), scan.nextInt());
}

