import java.util.*;
public class VierGewinnt extends Spiel implements Protokollierbar {

    private Scanner scan = new Scanner(System.in);
    private boolean gamerunning;
    private int anzahlDurchgange;
    private boolean computerSpielt = false;
    //Spiel spiel;
    //VierGewinntSpielfeld feld = new VierGewinntSpielfeld(6, 7);
    private Spielfeld feld;



    //Spielstart
    void start() {
        //Scanner scan = new Scanner(System.in);
        System.out.println("Willkommen zu VierGewinnt");

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
                again = false;
            } else {
                System.out.println("Inkorrekte Eingabe, bitte erneut versuchen."); //bei nicht 0 oder 1 wird die Schleife erneut durchwandert
            }
        }
        gamerunning = true;
//            spielfeld = new String[feld.height][feld.length];
//            for (int i = 0; i < feld.length; i++) {
//                for(int j = 0; j < feld[i].length; j++){
//                    feld[i][j] = " ";
//                }
//            }
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
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast im " + anzahlDurchgange +". Durchgang gewonnen!");
                } else {
                    aktuellerSpieler=spieler2;
                }
            }
            else {
                spielzug(spieler2);
                if(won){
                    gamerunning=false;
                    System.out.println();
                    System.out.println("Glückwunsch " + aktuellerSpieler.getName() + ", du hast im " + anzahlDurchgange +". Durchgang gewonnen!");
                } else {
                    aktuellerSpieler = spieler1;
                }
            }
        }
    }

    @Override
    public void spielzug(Spieler aktuellerSpieler) {
//        if (computerSpielt) {
//            int spalte = (int) (Math.random()%feld.length);
//            spielsteinCOMP(aktuellerSpieler, spalte);
//            addSpielzug();
//        } else {
        if (computerSpielt && aktuellerSpieler.equals(spieler2)){
            int spalte = (int) (Math.random()*feld.length)%feld.length;
            System.out.println("COMPUTER setzt den Stein in Spalte " + (spalte+1) + ".");
            spielstein(aktuellerSpieler, spalte);
            addSpielzug();
        }
        else {
            System.out.println("In welche Spalte willst du den Stein setzen?");
            int spalte = scan.nextInt();
            spielstein(aktuellerSpieler, spalte - 1);
            addSpielzug();
        }
//        }
    }




    private String signatur;
    @Override
    public void spielstein(Spieler aktuellerSpieler, int spalte) {
       // boolean done = true;
        signatur = "Spieler: " + aktuellerSpieler.getName() + "; z" + "Spalte: " + (spalte+1) + "\n";
        int x = feld.height-1; //letzte Zeile wird zuerst betrachtet

        while(x>=0){ //&&done
            if (feld.get(x, spalte).equals(" ")){  //wenn das Feld leer ist, kann man Spielstein setzen
                if (aktuellerSpieler.equals(spieler1)) {
                    feld.set(x, spalte, "X");
                    break;
                }
                else{
                    feld.set(x, spalte, "O");
                    break;
                }
            }
            x--;
        }
        unentschieden();
        if (x < 0) {
            System.out.println("Diese Spalte ist schon voll.");
            spielzug(aktuellerSpieler);
        } else {
            feld.draw();
            gewonnen(x, spalte);
        }
    }

    //spielzug fuer computer
//    @Override
//    public void spielsteinCOMP(Spieler aktuellerSpieler, int spalte) {
//        signatur = "Spieler: " + spieler2.getName() + "; " + "Spalte: " + (spalte+1) + "\n";
//        int x = feld.height-1; //letzte Zeile wird zuerst betrachtet
//
//        while(x>=0){
//            if (feld.get(x, spalte).equals(" ")){//wenn das Feld leer ist, kann man Spielstein setzen
//                if(aktuellerSpieler.equals(spieler1)) {
//                    feld.set(x, spalte, "X");
//                    break;
//                }
//                else{
//                    feld.set(x, spalte, "O");
//                    break;
//                }
//            }
//            x--;
//        }
//
//        if (x < 0) {
//            System.out.println("Diese Spalte ist schon voll.");
//        } else {
//            feld.draw();
//            gewonnen(x, spalte);
//        }
//    }






    //Gewinnbedingungen
    private boolean won;
    private int counterH, counterV, counterS1, counterS2;
    private void gewonnen(int i, int j){
        won = horizontal(i,j) || vertikal(i,j) || schraeg1(i,j) || schraeg2(i,j);
    }

    private void unentschieden(){
        boolean unentschieden = false;
        int counter = 0;
        for(int i=0; i < feld.height; i++){
            for (int j = 0; j < feld.length; j++){
                if(!feld.get(i, j).equals(" ")){
                    counter++;
                    if(counter == (feld.length*feld.height)){
                        unentschieden = true;
                    }
                }
                else{
                    unentschieden = false;
                }
            }
        }
        if(unentschieden){
            feld.draw();
            System.out.println();
            System.out.println("Es ist unentschieden!");
            System.exit(0);
        }
    }


    //Test ob horizontal gewonnen
    private boolean horizontal(int zeile, int spalte){
        for (int i = -3; i<4; i++){
            if (feld.length > spalte+i && spalte+i >= 0){ //&& ) {height > zeile+i && zeile+i >= 0
                if (feld.get(zeile,spalte).equals(feld.get(zeile,spalte + i)) && !feld.get(zeile, spalte+i).equals(" ")) {

                    counterH++;

                    if (counterH == 4) {
                        return true;
                    }

                }else{
                    counterH=0;
                }
            }
        }
        counterH=0;
        return false;
    }



//    private boolean horizontal(int x, int y){
//
//        boolean returnwert = false;
//        if(y + 3 < spielfeld[x].length) {
//            if(spielfeld[x][y].equals(spielfeld[x][y+1]) && spielfeld[x][y+1].equals(spielfeld[x][y+2]) && spielfeld[x][y+2].equals(spielfeld[x][y+3])){
//                returnwert = true;
//            }
//        }
//        return returnwert;
//    }








    //Test ob vertikal gewonnen
    private boolean vertikal(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(feld.height > zeile+i && zeile+i >= 0){ // && length > spalte+i && spalte+i >= 0) {
                if(feld.get(zeile, spalte).equals(feld.get(zeile+i, spalte)) && !feld.get(zeile+i, spalte).equals(" ")){

                    counterV++;
                    if(counterV==4){
                        return true;
                    }
                }else{
                    counterV=0;
                }
            }
        }
        counterV=0;
        return false;
    }





    //Test ob schraeg gewonnen
    //unten links nach oben rechts
    private boolean schraeg1(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(feld.height > zeile-i & zeile-i >= 0 & feld.length > spalte+i & spalte+i >= 0) {
                if (feld.get(zeile, spalte).equals(feld.get(zeile-i, spalte+i))) {
                    //  if(height > zeile-i && zeile-i >= 0 && spielfeld[zeile][spalte].equals(spielfeld[zeile - i][spalte + i]) && !spielfeld[zeile - i][spalte + i].equals(" ")){

                    counterS1++;

                    if (counterS1 == 4) {
                        return true;
                    }
                } else {
                    counterS1=0;
                }
            }

        }
        counterS1=0;
        return false;
    }
    //oben links nach unten rechts
    private boolean schraeg2(int zeile, int spalte){
        for (int i = -3; i<=3; i++){
            if(feld.height > zeile+i & zeile+i >= 0 & feld.length > spalte+i & spalte+i >= 0){
                if(feld.get(zeile, spalte).equals(feld.get(zeile+i, spalte+i))){
                    //if (!spielfeld[zeile + i][spalte + i].equals(" ")){
                    counterS2++;
                    if(counterS2==4){
                        return true;
                    }
                    //}
                }else{
                    counterS2=0;
                }

            }

        }
        counterS2=0;
        return false;
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




}//end class