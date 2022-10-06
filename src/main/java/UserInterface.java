import Items.Weapon;
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

    public void userCommand() {
        while (!adventure.hasWon() && !adventure.hasLost()) {
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
                    } else if (!adventure.getPlayer().getCurrentRoom().getCanBeDark()) {
                        System.out.println("This room can't be turned on/off.");
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
                case "drink" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.playerDrink(commands[1]));
                    } else {
                        System.out.println("You did not enter a valid action, please try again.");
                    }
                }
                case "look" -> {
                    System.out.println(adventure.playerLook());
                }
                case "help" -> {
                    System.out.println(help());
                }
                case "inventory", "bag", "invent", "inv" -> {

                    System.out.println(adventure.getInventory());
                }
                case "equip" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.equipWeapon(commands[2], commands[1]));
                    }
                }
                case "stash" -> {
                    if (commands.length > 1) {
                        System.out.println(adventure.stashWeapon(Weapon.class));
                    }
                }
                case "attack", "att" -> {
                    if (!adventure.attack().equals("You have died. Restarting....")) {
                        System.out.println(adventure.attack());
                    } else {
                        adventure.lost();
                    }
                }
                case "exit", "quit" -> {
                    if (adventure.hasWon()) {
                        System.out.print("Congratulations you have beaten the game and may leave with ");
                        System.out.print("what most likely is your first archivement");
                        System.exit(0);
                    } else {
                        System.out.println("You have given up!");
                        System.exit(0);
                    }
                }
                case "victory", "v", "end" -> {
                    System.out.print("Congratulations you have beaten the game and may leave with a penis");
                    System.out.print("what most likely is your first archivement");
                    adventure.victory();
                }
                case "status", "stat", "health" -> {
                    System.out.println(adventure.stats());
                }
                default -> {
                    System.out.println("This was not a valid command, enter a new one.");
                }
            }
        }
    }

    public void printIntro() {
        StringBuilder intro = new StringBuilder();
        intro.append("\nWelcome to the Escape Room KIDS!! ");
        intro.append("\nYou are in ");
        intro.append(adventure.currentRoom()).append("\n");
        intro.append("Find your way out before... its..t o tt tooo lateeeeeeeeeeeee.").append("\n");
        intro.append("You are lucky enough to be assisted by typing \"Help\"").append("\n");
        intro.append("Good luck u may indeed need it.").append("\n");
        System.out.println(intro.toString());
    }

    public String help() {
        StringBuilder helpInfo = new StringBuilder();
        helpInfo.append("Go + a cardinal direction: attempts to go in the designated direction. \n");
        helpInfo.append("Look: Gets the description on the room u are in. \n");
        helpInfo.append("Pickup + item name: attempts to pick up the designated item. \n");
        helpInfo.append("Turn + on/off: turns off the light if it is dark. \n");
        helpInfo.append("Take + item name: picks up the item and adds it to the player's inventory, but only works when the light is on.\n");
        helpInfo.append("Drop + item name: Drops the item and removes it from the player's inventory and adds it to the room. \n");
        helpInfo.append("Inventory/Inv/Bag: Shows the content of the player's inventory. \n");
        helpInfo.append("Eat + item name: Eats the item, if it is eatable and if you are not on full health. \n");
        helpInfo.append("Equip weapon/armor  + item name: Equips the item if it is possible. \n");
        helpInfo.append("Stash + item name: Stashes your weapon into your inventory. \n");
        helpInfo.append("Attack: attacks the enemy in the room, if there is any. \n");
        helpInfo.append("Exit: exits the labyrinth. LIKE A COWARD!!! \n");
        return helpInfo.toString();
    }
}