import java.util.*;
public class Chomp extends Spiel implements Protokollierbar {
    
    private Scanner scan = new Scanner(System.in);
    private boolean gamerunning;
    private int anzahlDurchgange;
    private boolean computerSpielt;
    private Spielfeld feld;


    //Gewinnbedingungen
    private boolean won;
    private void gewonnen(){
        if(feld.get(0,0).equals("O")){
            won=true;
        }
    }
    
    //Spielstart
    void start() {
        //Scanner scan = new Scanner(System.in);
        System.out.println("Willkommen zu Chomp");
        System.out.println("Wie groß soll das Spielfeld sein?");
        System.out.println("Breite des Feldes:");
        int newLength = Integer.parseInt(scan.nextLine());
        System.out.println("Höhe des Feldes:");
        int newHeight = Integer.parseInt(scan.nextLine());
        feld = new Spielfeld(newHeight, newLength);

        //Spielernamen und -menschlichkeit abfragen
        System.out.println("Wie ist dein Name?");
        String spielername1 = scan.nextLine();
        spieler1 = new Spieler(spielername1, true); //warum schreibt er da nicht name davor?

        boolean again = true;
        while (again) {
            System.out.println("Möchtest du gegen einen Computer oder gegen einen anderen Spieler spielen? 0 für COMP, 1 für Spieler");
            int spielertyp = Integer.parseInt(scan.nextLine());
            if (spielertyp == 0) {
                spieler2 = new Spieler("COMPUTER", false);
                computerSpielt = true;
                again = false;
            } else if (spielertyp == 1) {
                System.out.println("Wie heißt du, Spieler 2?");
                String spielername2 = scan.nextLine();
                spieler2 = new Spieler(spielername2, true);
                computerSpielt=false;
                again = false;
            } else {
                System.out.println("Inkorrekte Eingabe, bitte erneut versuchen."); //bei nicht 0 oder 1 wird die Schleife erneut durchwandert
            }
        }
            gamerunning = true;
            feld.draw();
            spiellaeuft(spieler1);
    }

    private void spiellaeuft(Spieler aktuellerSpieler){
        while(gamerunning){
            System.out.println();
            System.out.println("Spieler " + aktuellerSpieler.getName() + " ist dran:");
            if(aktuellerSpieler.equals(spieler1)){
                spielzug(spieler1);
                durchgang();
                if(won){
                    gamerunning=false;
                    System.out.println();
                    aktuellerSpieler=spieler2;
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast im " + anzahlDurchgange +". Durchgang gewonnen!");
                } else {
                    aktuellerSpieler=spieler2;
                }
            }else {
                spielzug(spieler2);
                if(won){
                    gamerunning=false;
                    System.out.println();
                    aktuellerSpieler=spieler1;
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast im " + anzahlDurchgange +". Durchgang gewonnen!");
                } else {
                    aktuellerSpieler = spieler1;
                }
            }
        }
    }

    private String signatur;
    @Override
    public void spielstein(Spieler aktuellerSpieler, int spalte, int zeile) {
        boolean drawgo=true;
        signatur = "Spieler: " + aktuellerSpieler.getName() + "; " + "Spalte: " + (spalte+1) + "Zeile: " + (zeile+1) + "\n";

         if (feld.get(zeile, spalte).equals(" ")){  //wenn das Feld leer ist, kann man Spielstein setzen
              for(int i=feld.height-1;i>=zeile;i--){
                  for(int j=feld.length-1;j>=spalte;j--){
                            if(feld.get(i, j).equals(" ")){
                                feld.set(i, j, "O");
                        }
                }
            }
        }else{
            drawgo=false;
            System.out.println("Dieses Feld  ist schon voll."); //andere "falsche" Eingaben auch noch abfangen
            spielzug(aktuellerSpieler);
        }
        if(drawgo){
            feld.draw();
            gewonnen();
        }
        }


    @Override
    public void spielzug(Spieler aktuellerSpieler) {
        if (computerSpielt&&aktuellerSpieler.equals(spieler2)) {
            int spalte = (int) (Math.random()*feld.length)%feld.length;
            int zeile = (int)(Math.random()*feld.height)%feld.height;
            System.out.println("COMPUTER setzt den Stein ins Feld " + (spalte+1)+ "," + (zeile+1) + ".");
            spielstein(aktuellerSpieler, spalte, zeile);
            addSpielzug();
        } else {
            System.out.println("In welche Spalte willst du den Stein setzen? (Von links nach rechts)");
            int spalte = scan.nextInt();
            System.out.println("In welche Zeile willst du den Stein setzen? (Von oben nach unten)");
            int zeile = scan.nextInt();
            spielstein(aktuellerSpieler, spalte - 1, zeile-1);
            addSpielzug();
        }
    }

    @Override
    public void addSpielzug() {
        Protokollierbar.protokoll.push(signatur);
    }

    @Override
    public void removeSpielzug() {
        Protokollierbar.protokoll.pop();
    }

    @Override
    public void durchgang() {
        anzahlDurchgange++;
    }


    public void spielstein(Spieler x, int y){}
}