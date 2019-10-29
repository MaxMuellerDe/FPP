public abstract class Spielfeld {

    int height;
    int length;
    public abstract void draw();

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