public class Spieler{


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

}

