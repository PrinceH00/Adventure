import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private Adventure adventure;

    public UserInterface() {
        adventure = new Adventure();
        scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public void startAdventure() {
        adventure.createMap();
        printIntro();
        usercomand();
    }
    public void usercomand(){
        // TODO: 27/09/2022 find en måde udenom while "true" Optinal
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            String[] commands = input.split(" ");
            switch (commands[0]) {
                case "go" -> {
                    if (commands.length > 1) {
                        switch (commands[1]) {
                            case "north", "n" -> {
                                if (adventure.getPlayer().moveNorth()) {
                                    adventure.getPlayer().setCurrentRoom(adventure.getPlayer().getCurrentRoom().getNorth());
                                    System.out.println(adventure.getPlayer().getCurrentRoom());
                                } else {
                                    System.out.println("There is no exit in this room to the north.");
                                }
                            }
                            case "east", "e" -> {
                                if (adventure.getPlayer().moveEast()) {
                                    adventure.getPlayer().setCurrentRoom(adventure.getPlayer().getCurrentRoom().getEast());
                                    System.out.println(adventure.getPlayer().getCurrentRoom());
                                } else {
                                    System.out.println("There is no exit in this room to the east.");
                                }
                            }
                            case "south", "s" -> {
                                if (adventure.getPlayer().moveSouth()) {
                                    adventure.getPlayer().setCurrentRoom(adventure.getPlayer().getCurrentRoom().getSouth());
                                    System.out.println(adventure.getPlayer().getCurrentRoom());
                                } else {
                                    System.out.println("There is no exit in this room to the south.");
                                }
                            }
                            case "west", "w" -> {
                                if (adventure.getPlayer().moveWest()) {
                                    adventure.getPlayer().setCurrentRoom(adventure.getPlayer().getCurrentRoom().getWest());
                                    System.out.println(adventure.getPlayer().getCurrentRoom());
                                } else {
                                    System.out.println("There is no exit in this room to the west.");
                                }
                            }
                            default -> {
                                System.out.println("There is no way such as: " + commands[1]);
                            }
                        }
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "turn" -> {
                    if (commands.length > 1 && adventure.getPlayer().getCurrentRoom().getCanBeDark()) {
                        switch (commands[1]) {
                            case "on" -> {
                                adventure.getPlayer().getCurrentRoom().setDark(false);
                            }
                            case "off" -> {
                                adventure.getPlayer().getCurrentRoom().setDark(true);
                            }
                        }
                    }
                }
                case "take" -> {
                    if (commands.length > 1) {
                        System.out.println("This has not yet been implemented.");
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "drop" -> {
                    if (commands.length > 1) {
                        System.out.println("This has not yet been implemented.1");
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "look" -> {
                    System.out.println(adventure.getPlayer().look());
                }
                case "help" -> {
                    System.out.println(help());
                }
                case "exit", "quit" -> {
                    System.out.println("You have given up!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("This was not a valid command, enter a new one.");
                }
            }
        }
    }
    public void printIntro(){
        System.out.println("\nWelcome to the Escape Room KIDS!! "
                + "\nYou are in " + adventure.getPlayer().getCurrentRoom());
        System.out.println();
        System.out.println("""
                Find your way out before... its..t o tt tooo lateeeeeeeeeeeee.
                You are lucky enough to be assistet by typing "Help"
                Good luck u may indeed need it. 
                """);
    }



    public String help(){
        return ("""
                    Type "go" + the cardinal direction: attempts to go in the designated direction. \n
                    Look: Gets the description on the room u are in. \n
                    pickup + item name: attempts to pick up the designated item. \n
                    exit: exits the labyrinth. LIKE A COWARD!!! \n
                    """);
    }
}