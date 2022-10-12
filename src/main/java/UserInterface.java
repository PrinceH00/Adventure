import items.Equipment.BodyArmor;
import items.Equipment.Shield;
import items.Equipment.Weapon;

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
            String[] commands = input.split(" ", 4);
            switch (commands[0]) {
                case "go", "move" -> {
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
                    if (commands.length == 3) {
                        System.out.println(adventure.equipWeapon(commands[2], commands[1], ""));
                    } else if (commands.length == 4) {
                        System.out.println(adventure.equipWeapon(commands[3], commands[1], commands[2]));
                    } else {
                        System.out.println("You can't do that, something was wrong.");
                    }
                }
                case "unequip", "stash" -> {
                    if (commands.length > 2) {
                        switch (commands[1]) {
                            case "w", "weapon" -> {
                                System.out.println(adventure.stashWeapon(Weapon.class, commands[2]));
                            }
                            case "a", "armor" -> {
                                System.out.println(adventure.stashWeapon(BodyArmor.class, ""));
                            }
                            case "s", "shield" -> {
                                System.out.println(adventure.stashWeapon(Shield.class, ""));
                            }
                        }
                    } else {
                        System.out.println("You can't do that, something was wrong.");
                    }
                }
                case "attack", "att" -> {
                    if (commands.length > 1) {
                        String attackResult = adventure.attack(commands[1]);
                        adventure.hasLost();
                        adventure.hasWon();
                        System.out.println(attackResult);
                    }
                }
                case "exit", "quit" -> {
                    System.out.println("You have given up!");
                    System.exit(0);
                }
                case "reload" -> {
                    System.out.println(adventure.reloadWeapon());
                }
                case "status", "stat", "health", "stats" -> {
                    System.out.print(adventure.stats());
                }
                default -> {
                    System.out.println("This was not a valid command, enter a new one.");
                }
            }
        }
        if (adventure.hasLost()) {
            System.out.println("You have died and the game will thus end.\nTerminating....\nGoodbye!");
        }
        if (adventure.hasWon()) {
            System.out.println("CONGRATULATIONS, YOU HAVE WON THE GAME!");
        }
    }

    public void printIntro() {
        StringBuilder intro = new StringBuilder();
        intro.append("\nWelcome to the Escape room.Room KIDS!! ");
        intro.append("\nYou are in ");
        intro.append(adventure.currentRoom()).append("\n");
        intro.append("Find your way out before... its..t o tt tooo lateeeeeeeeeeeee.").append("\n");
        intro.append("You are lucky enough to be assisted by typing \"Help\"").append("\n");
        intro.append("Good luck u may indeed need it.").append("\n");
        System.out.println(intro.toString());
    }

    public String help() {
        StringBuilder helpInfo = new StringBuilder();
        helpInfo.append("Go + a cardinal direction: attempts to go in the designated direction.").append("\n");
        helpInfo.append("Look: Gets the description on the room u are in.").append("\n");
        helpInfo.append("Pickup + item name: attempts to pick up the designated item.").append("\n");
        helpInfo.append("Turn + on/off: turns off the light if it is dark.").append("\n");
        helpInfo.append("Take + item name: picks up the item and adds it to the player's inventory, but only works when the light is on.").append("\n");
        helpInfo.append("Drop + item name: Drops the item and removes it from the player's inventory and adds it to the room.").append("\n");
        helpInfo.append("Inventory/Inv/Bag: Shows the content of the player's inventory.").append("\n");
        helpInfo.append("Eat + item name: Eats the item, if it is eatable and if you are not on full health.").append("\n");
        helpInfo.append("Equip armor + item name: Equips the item as armor if it is possible.").append("\n");
        helpInfo.append("Equip weapon + main/off + item name: Equips the item as a weapon if it is possible.").append("\n");
        helpInfo.append("Stash/unequip + armor: Stashes your armor into your inventory.").append("\n");
        helpInfo.append("Stash/unequip + weapon + main/off: Stashes the corresponding weapon into your inventory.").append("\n");
        helpInfo.append("Reload: attempts to reload your weapon if it uses ammo and you have the corresponding ammo in your inventory.").append("\n");
        helpInfo.append("Attack: attacks the enemy in the room, if there is any.").append("\n");
        helpInfo.append("Exit: exits the labyrinth. LIKE A COWARD!!!").append("\n");
        return helpInfo.toString();
    }
}