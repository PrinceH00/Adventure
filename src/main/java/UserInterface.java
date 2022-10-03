import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final Adventure adventure;

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
            String[] commands = input.split(" ", 3);
            switch (commands[0]) {
                case "go" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.movePlayer(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "turn" -> {
                    if (commands.length > 1 && adventure.getPlayer().getCurrentRoom().getCanBeDark()) {
                        System.out.println(adventure.playerTurnLight(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "take" -> {
                    if (commands.length > 1 && !adventure.isDark()) {
                        System.out.println(adventure.takeItem(commands[1]));
                    } else if (adventure.isDark()) {
                        System.out.println("You can't see any items through the dark.");
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "drop" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.dropItem(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "eat" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.playerEat(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "look" -> {
                    adventure.playerLook();
                }
                case "help" -> {
                    System.out.println(help());
                }
                case "inventory", "bag", "invent", "inv" -> {
                    System.out.println(adventure.getInventory());
                }
                case "equip" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.equipWeapon(commands[1]));
                    }
                }
                case "stash" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.stashWeapon(Weapon.class));
                    }
                }
                case "attack", "att" -> {
                    System.out.println(adventure.attack());
                }
                case "exit", "quit" -> {
                    System.out.println("You have given up!");
                    System.exit(0);
                }
                case "status", "stat" -> {
                    System.out.println(adventure.stats());
                }
                default -> {
                    System.out.println("This was not a valid command, enter a new one.");
                }
            }
        }
    }

    public void printIntro(){
        System.out.println("\nWelcome to the Escape Room KIDS!! "
                + "\nYou are in " + adventure.currentRoom());
        System.out.println();
        System.out.println("""
                Find your way out before... its..t o tt tooo lateeeeeeeeeeeee.
                You are lucky enough to be assisted by typing "Help"
                Good luck u may indeed need it. 
                """);
    }

    public String help(){
        StringBuilder helpInfo = new StringBuilder();
        helpInfo.append("Go + a cardinal direction: attempts to go in the designated direction. \n");
        helpInfo.append("Look: Gets the description on the room u are in. \n");
        helpInfo.append("Pickup + item name: attempts to pick up the designated item. \n");
        helpInfo.append("Turn + on/off: turns off the light if it is dark. \n");
        helpInfo.append("Take + item name: picks up the item and adds it to the player's inventory, but only works when the light is on.\n");
        helpInfo.append("Drop + item name: Drops the item and removes it from the player's inventory and adds it to the room. \n");
        helpInfo.append("Inventory/Inv/Bag: Shows the content of the player's inventory. \n");
        helpInfo.append("Eat + item name: Eats the item, if it is eatable and if you are not on full health. \n");
        helpInfo.append("Equip Weapon + item name: Equips the item as a weapon if it is possible. \n");
        helpInfo.append("Stash + item name: Stashes your weapon into your inventory. \n");
        helpInfo.append("Attack: attacks the enemy in the room, if there is any. \n");
        helpInfo.append("Exit: exits the labyrinth. LIKE A COWARD!!! \n");
        return helpInfo.toString();
    }
}