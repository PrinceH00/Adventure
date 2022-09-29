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
        printIntro();
        userCommand();
    }

    public void userCommand(){
        // TODO: 27/09/2022 Find a way without while "true" Optional
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
                                adventure.getPlayer().getCurrentRoom().setVisited(true);
                                System.out.println("The light gives off a blinding light, but your eyes quickly adjusts.");
                            }
                            case "off" -> {
                                adventure.getPlayer().getCurrentRoom().setDark(true);
                                System.out.println("The darkness consumes you, but your eyes quickly adjusts.");
                            }
                        }
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "take" -> {
                    if (commands.length > 1 && !adventure.getPlayer().getCurrentRoom().getDark()) {
                        System.out.println(adventure.getPlayer().takeItem(commands[1]));
                    } else if (adventure.getPlayer().getCurrentRoom().getDark()) {
                        System.out.println("You can't see any items through the dark.");
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "drop" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.getPlayer().dropItem(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "use" -> {
                    if (commands.length > 1) {
                        System.out.println("This part has not yet been implemented");
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "look" -> {
                    System.out.println(adventure.getPlayer().look());
                    System.out.println(adventure.getPlayer().getCurrentRoom().getNorth());
                    System.out.println(adventure.getPlayer().getCurrentRoom().getEast());
                    System.out.println(adventure.getPlayer().getCurrentRoom().getSouth());
                    System.out.println(adventure.getPlayer().getCurrentRoom().getWest());
                }
                case "help" -> {
                    System.out.println(help());
                }
                case "inventory", "bag", "invent", "inv" -> {
                    System.out.println(adventure.getPlayer().inventoryToString());
                }
                case "exits" -> {
                    boolean north = false;
                    boolean east = false;
                    boolean south = false;
                    boolean west = false;
                    if (adventure.getPlayer().getCurrentRoom().getNorth() != null && adventure.getPlayer().getCurrentRoom().getNorth().getVisited()) {
                        north = true;
                    }
                    if (adventure.getPlayer().getCurrentRoom().getEast() != null && adventure.getPlayer().getCurrentRoom().getEast().getVisited()) {
                        east = true;
                    }
                    if (adventure.getPlayer().getCurrentRoom().getSouth() != null && adventure.getPlayer().getCurrentRoom().getSouth().getVisited()) {
                        south = true;
                    }
                    if (adventure.getPlayer().getCurrentRoom().getWest() != null && adventure.getPlayer().getCurrentRoom().getWest().getVisited()) {
                        west = true;
                    }
                    String northName = "null";
                    String eastName = "null";
                    String southName = "null";
                    String westName = "null";
                    int exits = 0;
                    if (north) {
                        exits++;
                        northName = "north: " + adventure.getPlayer().getCurrentRoom().getNorth().getRoomName();
                    }
                    if (east) {
                        exits ++;
                        eastName = "east: " + adventure.getPlayer().getCurrentRoom().getEast().getRoomName();
                    }
                    if (south) {
                        exits++;
                        southName = "south: " + adventure.getPlayer().getCurrentRoom().getSouth().getRoomName();
                    }
                    if (west) {
                        exits ++;
                        westName = "west: " + adventure.getPlayer().getCurrentRoom().getWest().getRoomName();
                    }
                    if (north || east || south || west) {
                        System.out.printf("There are %s exits.\n%s\n%s\n%s\n%s",exits,northName,eastName,southName,westName);
                    }
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
                You are lucky enough to be assisted by typing "Help"
                Good luck u may indeed need it. 
                """);
    }

    public String help(){
        return ("""
                    Go + a cardinal direction: attempts to go in the designated direction. \n
                    Look: Gets the description on the room u are in. \n
                    Pickup + item name: attempts to pick up the designated item. \n
                    Exit: exits the labyrinth. LIKE A COWARD!!! \n
                    Turn + on/off: turns off the light if it is dark. \n
                    Take + item name: picks up the item and adds it to the player's inventory, but only works when the light is on.\n
                    Drop + item name: Drops the item and removes it from the player's inventory and adds it to the room. \n
                    Inventory/Inv/Bag: Shows the content of the player's inventory. \n
                    Exits: Shows the adjacent rooms that the player has already explored. \n 
                    """);
    }
}