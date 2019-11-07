import java.util.*;
public class VierGewinnt extends Spiel implements Protokollierbar {

    Scanner scan = new Scanner(System.in);
    boolean gamerunning;
    //Spiel spiel;
    //VierGewinntSpielfeld feld = new VierGewinntSpielfeld(6, 7);




    public void draw() {
        // for (String[] strings : spielfeld) {
        //    System.out.println(Arrays.toString(strings));
        //}
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < length; j++) {
                System.out.print(" | ");
                System.out.print(spielfeld[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println(" -----------------------------");
    }



    //Gewinnbedingungen
    private boolean won;
    private int counterH, counterV, counterS1, counterS2;
    private void gewonnen(int i, int j){
        won = horizontal(i, j) || vertikal(i, j) || schraeg1(i, j) || schraeg2(i, j);
    }


    //Test ob horizontal gewonnen
    private boolean horizontal(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if (height > zeile+i && zeile+i >= 0 && length > spalte+i && spalte+i >= 0) {
                if (spielfeld[zeile][spalte].equals(spielfeld[zeile][spalte + i]) && !spielfeld[zeile][spalte + i].equals(" ")) {

                    counterH++;

                    if (counterH == 4) {
                        return true;
                    }

                }
            }

            else{
                counterH=0;
            }

        }
        counterH=0;
        return false;
    }

    //Test ob vertikal gewonnen
    private boolean vertikal(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(height > zeile+i && zeile+i >= 0 && length > spalte+i && spalte+i >= 0) {
                if(spielfeld[zeile][spalte].equals(spielfeld[zeile + i][spalte]) && !spielfeld[zeile + i][spalte].equals(" ")){

                    counterV++;

                    if(counterV==4){
                       return true;
                    }
                }
            }

            else{
                counterV=0;
            }

        }
        counterV=0;
        return false;
    }

    //Test ob schraeg gewonnen
    //unten links nach oben rechts
    private boolean schraeg1(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(height > zeile-i & zeile-i >= 0 & length > spalte+i & spalte+i >= 0) {
                if (spielfeld[zeile][spalte].equals(spielfeld[zeile - i][spalte + i])) {
                    //  if(height > zeile-i && zeile-i >= 0 && spielfeld[zeile][spalte].equals(spielfeld[zeile - i][spalte + i]) && !spielfeld[zeile - i][spalte + i].equals(" ")){

                    counterS1++;

                    if (counterS1 == 4) {
                        return true;
                    }
                }
            } else {
                counterS1=0;
            }

        }
        counterS1=0;
        return false;
    }
    //oben links nach unten rechts
    private boolean schraeg2(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(height > zeile+i & zeile+i >= 0 & length > spalte+i & spalte+i >= 0){
                 if(spielfeld[zeile][spalte].equals(spielfeld[zeile + i][spalte + i])){
                    //if (!spielfeld[zeile + i][spalte + i].equals(" ")){
                    counterS2++;
                    if(counterS2==4){
                        return true;
                    }
                    //}
                }
            }
            else{
                counterS2=0;
            }

        }
        counterS2=0;
        return false;
    }


    private int height = 6, length = 7;
    //Spielstart
    void start() {
        System.out.println("Willkommen zu VierGewinnt");

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
                again = false;
            } else if (spielertyp == 1) {
                System.out.println("Wie heißt du, Spieler 2?");
                String spielername2 = scan.nextLine();
                spieler2 = new Spieler(spielername2, true);
                again = false;
            } else {
                System.out.println("Inkorrekte Eingabe, bitte erneut versuchen."); //bei nicht 0 oder 1 wird die Schleife erneut durchwandert
            }
            gamerunning = true;
            spielfeld = new String[height][length];
            for (int i = 0; i < spielfeld.length; i++) {
                for(int j = 0; j < spielfeld[i].length; j++){
                    spielfeld[i][j] = " ";
                }
            }
            draw();
            spiellaeuft(spieler1);
        }

    }

    public void spiellaeuft(Spieler aktuellerSpieler){
        while(gamerunning){
            System.out.println("Spieler " + aktuellerSpieler.getName() + " ist dran:");
            if(aktuellerSpieler.equals(spieler1)){
                spielzug(spieler1);
                if(won){
                    gamerunning=false;
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast gewonnen!");
                } else {
                    aktuellerSpieler=spieler2;
                }
            }
            else {
                spielzug(spieler2);
                if(won){
                    gamerunning=false;
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast gewonnen!");
                } else {
                    aktuellerSpieler = spieler1;
                }
            }
        }
    }

    @Override
    public void spielstein(Spieler aktuellerSpieler, int spalte) {
        int x = spielfeld.length-1; //letzte Zeile wird zuerst betrachtet

        while(x>=0){
            if (spielfeld[x][spalte].equals(" ")){  //wenn das Feld leer ist, kann man Spielstein setzen
                if (aktuellerSpieler.equals(spieler1)) {
                    spielfeld[x][spalte] = "X";
                    break;
                }
                else{
                    spielfeld[x][spalte] = "O";
                    break;
                }
            }
            x--;
        }

        if (x < 0) {
            System.out.println("Diese Spalte ist schon voll."); //andere "falsche" Eingaben auch noch abfangen
        } else {
            draw();
            gewonnen(x, spalte);
        }
        //gewonnen(aktuellerSpieler);
    }



    @Override
    public void spielzug(Spieler aktuellerSpieler) {
        System.out.println("In welche Spalte willst du den Stein setzen?");
        int spalte = scan.nextInt();
        spielstein(aktuellerSpieler, spalte - 1);
    }






    //Zustandsvariable zum Protokollieren der Zuege
    Stack zuege = new Stack();

    @Override
    public void addSpielzug() {
        zuege.push(spieler1);       //falsch, nur damit er nicht meckert
    }

    @Override
    public void removeSpielzug() {
        zuege.pop();
    }

    @Override
    public int durchgang() {
        return 0;
    }





























//
//    do{
//        VierGewinntSpielfeld.draw();
//    }
//
//
//    public Spielfeld(int x, int y)
//    {
//
//        feld = new int[x][y];
//
//        for(int i=0; i < feld.length; i++)
//        {
//            for(int j=0; j < feld[i].length; j++)
//            {
//                feld[i][j] = 0;
//            }
//
//        }
//
//
//
//    }
//
//    public void setzeSpielstein(int spielerNr, int spalte)
//    {
//
//        int x = feld.length-1;
//
//        boolean fortsetzen = true;
//
//        while(x >= 0 && fortsetzen == true)
//        {
//
//            if(feld[x][spalte] == 0 )
//            {
//                feld[x][spalte] = spielerNr;
//                fortsetzen = false;
//            }
//
//            x--;
//
//        }
//
//        if(x < 0)
//        {
//            System.out.println("Der Stein konnte nicht gesetzt werden!");
//        }
//
//
//
//
//    }
//
//    public void ausgabeSpielfeld()
//    {
//
//        for(int i=0; i < feld.length; i++)
//        {
//            for(int j=0; j < feld[i].length; j++)
//            {
//
//                System.out.print("| " + feld[i][j] + " |");
//
//            }
//            System.out.println();
//        }
//
//
//    }
//
//    public boolean unentschieden()
//    {
//
//        boolean returnwert = false;
//
//        if(getAnzahlGesetzterSteine() == feld.length*feld[0].length)
//        {
//            returnwert = true;
//        }
//
//        return returnwert;
//    }
//
//    public int getAnzahlGesetzterSteine()
//    {
//        int anzSteine = 0;
//        for(int i=0; i < feld.length; i++)
//        {
//            for(int j=0; j < feld[i].length; j++)
//            {
//                if(feld[i][j] != 0)
//                {
//                    anzSteine++;
//                }
//            }
//        }
//
//        return anzSteine;
//    }
//

}//end class