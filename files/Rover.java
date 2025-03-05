import java.util.Map;
import java.util.HashMap;

class Rover {
    private int x; //Horizontal
    private int y; //Vertical
    private Plateau plateau;

    private DirectionNode direction;
    private Map<Character, int[]> directions = new HashMap<>();
    
    public Rover (int x, int y, char currentDirection, Plateau plateau) {
        this.x = Math.max(0, x);
        this.y = Math.max(0, y); //Current Location
        this.plateau = plateau;
        if (!plateau.withinBounds(x, y)) {x = -1; y = -1;} //Initalised out of bounds case

        directions.put('N', new int[]{0, 1});
        directions.put('E', new int[]{1, 0});
        directions.put('S', new int[]{0, -1});
        directions.put('W', new int[]{-1, 0}); //x and y change based on orientation when moving
        
        DirectionNode n = new DirectionNode('N');
        DirectionNode e = new DirectionNode('E');
        DirectionNode s = new DirectionNode('S');
        DirectionNode w = new DirectionNode('W'); //Circular doubly linked list containing current direction

        n.left = w;
        n.right = e;

        e.left = n;
        e.right = s;

        s.left = e;
        s.right = w;

        w.left = s;
        w.right = n; //Setup pointers

        direction = n;
        while (direction.value != currentDirection) { //Set current direction node to currentDirection argument 
            this.direction = this.direction.right; 
        }
    }

    public int[] getLocation() {
        return new int[] {x, y};
    }

    public char getDirection() {
        return direction.value;
    }


    public void move(String commands) {
        for (int i = 0; i < commands.length(); i++) {
            executeMove(commands.charAt(i));
        }
    }

    private boolean executeMove(char command) {
        if (command == 'L') {
            this.direction = this.direction.left; //Rotates left
            return true;
        }
        else if (command == 'R') {
            this.direction = this.direction.right; //Rotates right
            return true;
        }
        else if (command == 'M') {
            int[] move = directions.get(this.direction.value);
            if (plateau.withinBounds(this.x + move[0], this.y + move[1])) { //Remains in place if the next movement would be out of bounds
                this.x += move[0];
                this.y += move[1];
                return true;
            }
        }
        //Out of bounds or invalid command
        return false;
    }

}