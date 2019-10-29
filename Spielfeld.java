public abstract class Spielfeld {


    public int height;
    public int length;

    public abstract void draw();


    //Konstruktor fuer Spielfeld
    public Spielfeld(int height, int length) {
        this.height=height;
        this.length=length;
    }

    //getter und setter fuer length und height
    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

//Klasse fertig