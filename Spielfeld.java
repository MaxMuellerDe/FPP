public abstract class Spielfeld {


    public int height;
    public int length;
    public int feld[][];

    public abstract void draw();

    public Spielfeld() {
    }

    public Spielfeld(int height, int length){
        feld = new int[height][length];
        for(int i=0; i < length; i++){ //feld.length?
            for (int j = 0; j < feld[i].length; j++){
                feld[i][j]=0;
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


