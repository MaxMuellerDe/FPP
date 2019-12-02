package Spiele;
class Spielfeld {
    int height;
    int length;
    private String[][] feld; //Spielfeld statt String

    //draw
    void draw() {
        // for (String[] strings : spielfeld) {
        //    System.out.println(Arrays.toString(strings));
        //}

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < length; j++) {
                System.out.print(" | ");
                System.out.print(feld[i][j]);
            }
            System.out.println(" |");
        }
        for(int i = 0; i<length; i++){
            System.out.print(" ---");
        }
    }


    Spielfeld(int height, int length){
        feld = new String[height][length]; //Spielfeld statt String
        for(int i=0; i < height; i++){
            for (int j = 0; j < length; j++){
                feld[i][j]= " "; //null statt " "
            }
        }
        this.height = height;
        this.length = length;
    }



    //getter und setter fuer length und height
    int getHeight() {
        return height;
    }

    int getLength() {
        return length;
    }

    void setHeight(int height) {
        this.height = height;
    }

    void setLength(int length) {
        this.length = length;
    }

    void set(int x, int y, String value) {
        this.feld[x][y] = value;
    }

    String get(int x, int y) {
        return feld[x][y];
    }
}


