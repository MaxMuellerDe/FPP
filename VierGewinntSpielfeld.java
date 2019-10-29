public class VierGewinntSpielfeld extends Spielfeld implements Protokollierbar {

    //Spielfeld definieren
    String[][] board;

    //Spielstein
    public void spielstein(String name, int spalte){
        int x = board.length-1; //letzte Zeile wird zuerst betrachtet

        //boolean fortsetzen = true;

        //noch nicht ganz ausgereift
        while(x>=0){
            if (board[x][spalte] == ""){//wenn das Feld leer ist, kann man spielstein setzen
                board[x][spalte] = "x"; //hier noch unterscheiden zwischen x und o fuer verschiedene Spieler
            //fortsetzen == false;
            }
            x--;
        }

        if (x < 0) {

           System.out.println("Diese Spalte ist schon voll."); //andere "falsche" Eingaben auch noch abfangen
        }

    }

    @Override
    public void draw() {
        for (int i=0; i<length; i++){
            for(int j=0; j<height; j++){
                board[length][height] = "|"; //strich, leerzeichen, inhalt des feldes, strich
            }
        }

    }
}
