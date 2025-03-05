
public class Plateau {
    private int height;
    private int length;
    
    public Plateau (int length, int height) {
        this.length = Math.max(0, length);
        this.height = Math.max(0, height);
    }

    public boolean withinBounds(int x, int y) {
        return x >= 0 && x <= length && y >= 0 && y <= height;
    }
}
