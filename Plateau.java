
public class Plateau {
    private int height;
    private int length;
    
    public Plateau (int length, int height) {
        this.length = length;
        this.height = height;
    }

    public boolean withinBounds(int x, int y) {
        return x >= 0 && x <= length && y >= 0 && y <= height;
    }
}
