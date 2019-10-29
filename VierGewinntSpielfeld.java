//gesamte Klasse loeschen?


public class VierGewinntSpielfeld extends Spielfeld implements Protokollierbar{



    public VierGewinntSpielfeld(int height, int length) {
        super(height, length);
    }

    //Spielstein
    public void spielstein(String name, int spalte){
        int x = spielfeld.length-1; //letzte Zeile wird zuerst betrachtet

        //boolean fortsetzen = true;

        //noch nicht ganz ausgereift
        while(x>=0){
            if (spielfeld[x][spalte] == ""){//wenn das Feld leer ist, kann man spielstein setzen
                spielfeld[x][spalte] = "x"; //hier noch unterscheiden zwischen x und o fuer verschiedene Spieler
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
                spielfeld[length][height] = "|"; //strich, leerzeichen, inhalt des feldes, strich
            }
        }

    }
}
