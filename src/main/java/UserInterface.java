import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Adventure adventure;
    private Room room;

    public UserInterface() {
        adventure = new Adventure();
        room = new Room();
        scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public void runAdventure() {
        System.out.println("\nWelcome to the Escape Room KIDS!! " + "\nYou are in " + adventure.getPlayer().getCurrentRoom());
        while (true) {
            String input = scanner.nextLine();
            input.toLowerCase();
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
                case "pickup" -> {
                    System.out.println("This has not yet been implemented.");
                }
                case "look" -> {
                    System.out.println(adventure.getPlayer().getCurrentRoom().getDescription());
                    if (adventure.getPlayer().getCurrentRoom().getRoomName().equals("The Empty room")) {
                        System.out.println("you see two paths where lights enter the room: d");
                    }
                }
                case "help" -> {
                    System.out.print("""
                            go + cardinal direction: attempts to go in the designated direction. \n
                            Look: Gets the description on the room u are in. \n
                            pickup + item name: attempts to pick up the designated item. \n
                            exit: exits the labyrinth. LIKE A COWARD!!! \n
                            """);
                }
                case "exit" -> {
                    System.out.println("You have given up!");
                    System.exit(0);
                    break;
                }
                default -> {
                    System.out.println("This was not a valid command, enter a new one.");
                }
            }
        }
    }
}