public abstract class Spiel {

    //Zustandsvariable Spielfeld
    protected String[][] spielfeld; //spielfeld

    //Zustandsvariable Spieler
    Spieler spieler1;
    Spieler spieler2;

    //getter und setter fuer spielfeld
    public String[][] getSpielfeld() {
        return spielfeld;
    }

    public void setSpielfeld(String[][] spielfeld) {
        this.spielfeld = spielfeld;
    }


    //getter und setter fuer spieler
    public Spieler getSpieler1() {
        return spieler1;
    }

    public void setSpieler1(Spieler spieler1) {
        this.spieler1 = spieler1;
    }

    public Spieler getSpieler2() {
        return spieler2;
    }

    public void setSpieler2(Spieler spieler2) {
        this.spieler2 = spieler2;
    }

    public abstract void spielzug(Spieler aktuellerSpieler);
    public abstract void durchgang();
    public abstract void spielstein(Spieler aktuellerSpieler, int spalte);
    public abstract void spielstein(Spieler aktuellerSpieler, int spalte, int zeile);
    //public abstract void spielsteinCOMP(Spieler aktuellerSpieler, int spalte);

}






