public class Spielfeld {


    public int height;
    public int length;
    public String feld[][];

    //public abstract void draw();


    public Spielfeld(int height, int length){
        feld = new String[height][length];
        for(int i=0; i < length; i++){ //feld.length?
            for (int j = 0; j < feld[i].length; j++){
                feld[i][j]=" ";
            }
        }
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


