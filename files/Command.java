
import java.util.ArrayList;
import java.util.List;

public class Command {
    Plateau plateau;
    List<Rover> rovers = new ArrayList<Rover>();
    
    public Command(Plateau plateau) {
        this.plateau = plateau;
        
    }

    public Rover getRover(int index) {
        return (index < rovers.size() && index >= 0) ? rovers.get(index) : null; //If rover exists
    } 

    public boolean createRover(int x, int y, char direction) { //Returns true if Rover has landed successfully
        if (plateau.withinBounds(x, y) && validDirection(direction) != '/') {
            rovers.add(new Rover(x, y, direction, plateau));
            return true;
        }
        return false;
    }

    private char validDirection(char c) {
        c = Character.toUpperCase(c);
        return (c == 'N' || c == 'E' || c == 'S' || c == 'W') ? c : '/'; //Return '/' if not a valid direction
    }

    public String[] roverCommands(String commands) { //Performs rovers commands. returns location and direction
        int size = rovers.size()-1;
        Rover rover = rovers.get(size);
        rover.move(commands);
        
        String[] location = new String[3];
        location[0] = String.valueOf(rover.getLocation()[0]);
        location[1] = String.valueOf(rover.getLocation()[1]);
        location[2] = String.valueOf(rover.getDirection());
        
        return location;
    }
    

}
