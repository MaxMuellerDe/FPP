public class VierGewinntSpielfeld extends Spielfeld{


    @Override
    public void draw() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                System.out.println("| ");
            }
            System.out.println();

        }
    }

//HIER ZULETZT: DRAW DURCH AUSGEBEN ERSETZEN

    public static void spielfeldausgeben() {
        for(int i = 0; i < 6; i++) {

            for(int j = 0; j < 7; j++) {
                System.out.print(" | ");
                switch (spielfeld[i][j]) {
                    case "":
                        System.out.print(" ");
                        break;
                    case "1":
                        System.out.print("X");
                        break;
                    case "2":
                        System.out.print("O");
                        break;
                }
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println(" -----------------------------");
    }









//  @Override
//    public void draw() {
//        for (int i=0; i<length; i++){
//            for(int j=0; j<height; j++){
//                spielfeld[length][height] = "|"; //strich, leerzeichen, inhalt des feldes, strich
//            }
//        }



        //Spielstein
//    public void spielstein(String name, int spalte){
//        int x = spielfeld.length-1; //letzte Zeile wird zuerst betrachtet
//
//        //boolean fortsetzen = true;
//
//        //noch nicht ganz ausgereift
//        while(x>=0){
//            if (spielfeld[x][spalte] == ""){//wenn das Feld leer ist, kann man spielstein setzen
//                spielfeld[x][spalte] = "x"; //hier noch unterscheiden zwischen x und o fuer verschiedene Spieler
//                //fortsetzen == false;
//            }
//            x--;
//        }
//
//        if (x < 0) {
//
//            System.out.println("Diese Spalte ist schon voll."); //andere "falsche" Eingaben auch noch abfangen
//        }
//
//    }

        //chip setzen
        //gewinnbedingung



//Gewinnbedingungen
int counterH, counterV, counterS1, counterS2;
    public boolean gewonnen(String name){
        boolean wert = false;

            for(int i=0; i < feld.length; i++){
                for(int j=0; j < feld.length; j++){
                    if(horizontal(i,j)|| vertikal(i,j)|| schraeg1(i,j)|| schraeg2(i,j)){
                        wert = true;
                    }
                    else{
                        return wert;
                    }
                }
            }
        return wert;
    }


//Test ob horizontal gewonnen
public boolean horizontal(int zeile, int spalte){
        boolean wert = false;

        for (int i = -3; i<=3; i++){
          if(feld[spalte].length > spalte+i && spalte+i >= 0 && feld[zeile][spalte] == feld[zeile][spalte+i]){//&& feld[zeile][spalte+i] != null

              counterH++;

              if(counterH==4){
                  wert = true;
              }
          }

          else{
              counterH=0;
          }

        }
        return wert;
}

    //Test ob vertikal gewonnen
    public boolean vertikal(int zeile, int spalte){
        boolean wert = false;

        for (int i = -3; i<=3; i++){
            if(feld[zeile].length > zeile+i && zeile+i >= 0 && feld[spalte].length > spalte+i && spalte+i >= 0
                    && feld[zeile][spalte] == feld[zeile+i][spalte]){//&& feld[zeile][spalte+i] != null

                counterV++;

                if(counterV==4){
                    wert = true;
                }
            }

            else{
                counterV=0;
            }

        }
        return wert;
    }

    //Test ob schraeg gewonnen
    public boolean schraeg1(int zeile, int spalte){
        boolean wert = false;

        for (int i = -3; i<=3; i++){
            if(feld[zeile].length > zeile+i && zeile+i >= 0 && feld[zeile][spalte] == feld[zeile+i][spalte+i]){//&& feld[zeile][spalte+i] != null

                counterS1++;

                if(counterS1==4){
                    wert = true;
                }
            }
            else{
                counterS1=0;
            }

        }
        return wert;
    }

    public boolean schraeg2(int zeile, int spalte){
        boolean wert = false;

        for (int i = -3; i<=3; i++){
            if(feld[zeile].length > zeile-i && zeile-i >= 0 && feld[zeile][spalte] == feld[zeile-i][spalte+i]){//&& feld[zeile][spalte+i] != null

                counterS2++;

                if(counterS2==4){
                    wert = true;
                }
            }
            else{
                counterS2=0;
            }

        }
        return wert;
    }

}
