import java.io.IOException;
import java.util.Scanner;
import java.nio.file.*;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException{
        Command command;
        if (args.length == 1) { //File provided
            try {
                List<String> lines = Files.readAllLines(Paths.get(args[0])); 
                command = createCommand(lines.get(0)); //Create Plateau
                int index = 1;
                
                while (index < lines.size()-1) { //Alternate between rover initialisation and movement commands
                    if (!initialiseRover(lines.get(index), command)) {index++; continue;};
                    index++;

                    commandRover(lines.get(index), command);
                    index++;
                }
            }
            catch(Exception e) {
                System.out.println("Invalid format");
            }
            System.exit(0);
        }
        

        @SuppressWarnings("resource") 
        Scanner scanner = new Scanner(System.in);

        while (true) { //Console input
            System.out.println("Enter the plateau's upper coordinates in the format 'x y'. e.g. '3 5'. Type 'exit' to exit");
            String input = scanner.nextLine();
            if (input.equals("exit") || input.equals("close")) {System.exit(0);}
            
            

            try {
                command = createCommand(input);
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Format, Try Again");
            }     
        }

        
        while (true) { //Alternate between rover initialisation and movement commands

            while (true) {
                System.out.println("Enter the Rover's current coordinates and orientation 'x y orientation'. e.g. '2 3 N'. Type 'exit' to exit");
                String input = scanner.nextLine();
                if (input.equals("exit") || input.equals("close")) {System.exit(0);}
                
                
                try {
                    if (!initialiseRover(input, command)) { continue; } //Rover is out of bounds or not facing a valid direction
                    break;
                }
                catch (Exception e) {
                    System.out.println("Invalid Format, Try Again");
                }
            }

            while (true) {
                System.out.println("Enter the Rover's commands consisting of 'M', 'R', 'L' characters. e.g. 'MRMMMLM'. Type 'exit' to exit");
                String input = scanner.nextLine();
                if (input.equals("exit") || input.equals("close")) {System.exit(0);}
                
                try {
                    commandRover(input, command);
                    break;
                }
                catch (Exception e) {
                    System.out.println("Invalid Format, Try Again");
                }
            }
        }


        
    }
    private static Command createCommand(String parameters) { //Instantiate command object with plateau parameters
            
        String[] grid = parameters.split(" ");
        int length = Integer.parseInt(grid[0]);
        int height = Integer.parseInt(grid[1]);

        if (height < 0 || length < 0) { return null; }

        Command command = new Command(new Plateau(length, height));
        System.out.println("Initialised plateau with a length of " + grid[0] + " and a height of " + grid[1] + "\n");
        return command;

    }

    private static boolean initialiseRover(String parameters, Command command) { //Instantiate Rover
        String[] roverState = parameters.split(" ");
        int x = Integer.parseInt(roverState[0]);
        int y = Integer.parseInt(roverState[1]);
        String direction = roverState[2];

        return command.createRover(x, y, direction.charAt(0)); 
    
    }

    private static void commandRover(String parameters, Command command) { //Move most recently added rover in command
        String[] location = command.roverCommands(parameters);
        System.out.println("Rover has finished traversal and is located at x: " + location[0] + ", y: " + location[1] + " Direction: " + location[2] + "\n");
    }
}
