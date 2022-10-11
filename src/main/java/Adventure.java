import characters.Enemy;
import characters.Player;
import enums.Direction;
import enums.ReturnMessage;
import items.*;
import items.Equipment.Armor;
import items.Equipment.Weapon;
import room.Room;

public class Adventure {
    //Private objects of the player og item class
    private final Player player;
    private final Map map;
    private boolean hasWon = false;
    private boolean hasLost = false;
    private ReturnMessage result;

    //Constructor without parameters
    public Adventure() {
        map = new Map();
        player = new Player(20, map.getEmptyRoom());
    }

    //Get methode.
    public Player getPlayer() {
        return player;
    }

    public String movePlayer(String direction) {
        Direction moveDirection = player.move(direction);
        hasLost();
        switch (moveDirection) {
            case NORTH, EAST, SOUTH, WEST -> {
                return player.getCurrentRoom().toString();
            }
            case NULL -> {
                return String.format("There is no exit in this room to the %s.", direction);
            }
            case NOT_FOUND -> {
                return "There is no way such as: " + direction;
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public boolean isDark() {
        return player.isDark();
    }

    public String playerTurnLight(String state) {
        result = player.turnLight(state);
        switch (result) {
            case CAN -> {
                return "The light gives off a blinding light, but your eyes quickly adjusts.";
            }
            case CANT -> {
                return "The darkness consumes you, but your eyes quickly adjusts.";
            }
            case NOT_FOUND -> {
                return String.format("%s is not a valid state for the light.", state);
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String playerEat(String itemToEat) {
        Item item = player.checkInventoryForItem(itemToEat);
        Food food = (Food) item;
        result = player.eat(itemToEat);
        switch (result) {
            case CAN -> {
                return String.format("%s has been eaten and you have regained %s health", food.getName(),
                        food.getRecovery());
            }
            case CAN_MUCH -> {
                double overHeal = (player.getHealth() + food.getRecovery()) - player.getMAX_HEALTH();
                return String.format("%s has been eaten, with %s over healing.", food.getName(), overHeal);
            }
            case CAN_LITTLE -> {
                return String.format("%s has been eaten, and you have fainted.", food.getName());
            }
            case CANT -> {
                return String.format("%s is not food, you can't eat it.", itemToEat);
            }
            case NOT_FOUND -> {
                return String.format("There is no such item in the inventory");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String playerDrink(String itemToDrink) {
        Item item = player.checkInventoryForItem(itemToDrink);
        Liquid liquid = (Liquid) item;
        result = player.drink(itemToDrink);
        switch (result) {
            case CAN -> {
                return String.format("%s has been drunk and you have regained %s mana", liquid.getName(),
                        liquid.getRecovery());
            }
            case CAN_MUCH -> {
                double overHeal = (player.getMana() + liquid.getRecovery()) - player.getMAX_MANA();
                return String.format("%s has been drunk, with %s mana to lost.", liquid.getName(), overHeal);
            }
            case CAN_LITTLE -> {
                return String.format("%s has been drunk, and you have lost all your mana.", liquid.getName());
            }
            case CANT -> {
                return String.format("%s is not liquid, you can't drink it.", itemToDrink);
            }
            case NOT_FOUND -> {
                return String.format("There is no such item in the inventory");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String playerLook() {
        return player.look();
    }

    public String equipWeapon(String itemToEquip, String weaponOrArmor, String mainOrOffHand) {
        Item itemInInventory = player.checkInventoryForItem(itemToEquip);
        result = player.equipItem(itemToEquip, weaponOrArmor, mainOrOffHand);
        switch (result) {
            case CAN -> {
                return String.format("%s has been equipped.", itemInInventory.getName());
            }
            case CANT -> {
                return String.format("%s is not a weapon or armor piece.", itemToEquip);
            }
            case CAN_LITTLE -> {
                return String.format("%s, could not be equipped since it is a two-handed weapon", itemInInventory.getName());
            }
            case NOT_FOUND -> {
                return String.format("There is no such item in the inventory");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String stashWeapon(Class equipment, String mainOrOffHand) {
        String formerEquipment = "";
        if (equipment == Weapon.class && player.getMainHandWeapon() != null) {
            formerEquipment = player.getMainHandWeapon().getName();
        } else if (equipment == Armor.class && player.getEquippedArmor() != null) {
            formerEquipment = player.getMainHandWeapon().getName();
        }
        result = player.stashItem(equipment, mainOrOffHand);
        switch (result) {
            case CAN -> {
                return String.format("You have stashed %s", formerEquipment);
            }
            case CANT -> {
                return "You don't have that item equipped.";
            }
            case NOT_FOUND -> {
                return "You cannot unequip that.";
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String stats() {
        return player.playerStats();
    }

    public String attack(String enemy) {
        StringBuilder combatInfo = new StringBuilder();
        Enemy currentEnemy = player.currentEnemy(enemy);
        result = player.attack(enemy);
        switch (result) {
            case NO_USES -> {
                combatInfo.append("Your Weapon is no longer usable in battle.");
            }
            case CANT -> {
                combatInfo.append("You have no weapon equipped.");
            }
            case NOT_FOUND -> {
                combatInfo.append("There is no enemy in this room.");
            }
            default -> {
                combatInfo.append(String.format("You have attacked %s and dealt %s damage. \n",
                        currentEnemy.getName(),
                        player.getMainHandWeapon().getDamage()));
                switch (result) {
                    case DODGE -> {
                        combatInfo.append(String.format("You dodged the attack and thus took no damage.")).append("\n");
                        combatInfo.append(String.format("Player: %s/%s health\n", player.getHealth(), player.getMAX_HEALTH()));
                        combatInfo.append(String.format("%s: %s/%s health\n", currentEnemy.getName(),
                                currentEnemy.getHealth(),
                                currentEnemy.getMAX_HEALTH()));
                    }
                    case CAN_MUCH -> {
                        combatInfo.append(String.format("Player: %s/%s health\n", player.getHealth(), player.getMAX_HEALTH()));
                        combatInfo.append(String.format("%s, has been slain and dropped %s", currentEnemy.getName(),
                                currentEnemy.getLoot()));
                    }
                    case CAN_LITTLE -> {
                        combatInfo.append(String.format("You have been attacked and have taken %s damage. \n",
                                currentEnemy.getDAMAGE()));
                        combatInfo.append("You have died. Restarting....");
                    }
                    case CAN -> {
                        combatInfo.append(String.format("You have been attacked and have taken %s damage. \n",
                                currentEnemy.getDAMAGE()));
                        combatInfo.append(String.format("Player: %s/%s health\n", player.getHealth(), player.getMAX_HEALTH()));
                        combatInfo.append(String.format("%s: %s/%s health\n", currentEnemy.getName(),
                                currentEnemy.getHealth(),
                                currentEnemy.getMAX_HEALTH()));
                    }
                    default -> {
                        combatInfo.append("Something went wrong.");
                    }
                }
            }
        }
        return combatInfo.toString();
    }

    public String takeItem(String itemToTake) {
        result = player.takeItem(itemToTake);
        switch (result) {
            case CAN -> {
                return String.format("%s has been picked up successfully.", itemToTake);
            }
            case CANT -> {
                return String.format("There is no such item in the %s", player.getCurrentRoom().getRoomName());
            }
            case NOT_FOUND -> {
                return String.format("You can't carry that, it's too heavy.");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String dropItem(String itemToDrop) {
        Item itemDropped = player.checkInventoryForItem(itemToDrop);
        result = player.dropItem(itemToDrop);
        switch (result) {
            case CAN -> {
                return String.format("%s has been dropped successfully.", itemDropped.getName());
            }
            case NOT_FOUND -> {
                return String.format("There is no such item in the inventory");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String getInventory() {
        return player.inventoryToString();
    }

    public String reloadWeapon() {
        result = player.checkInventoryForAmmo();
        switch (result) {
            case CAN -> {
                return "You have successfully reloaded your weapon.";
            }
            case CANT -> {
                return "You can't reload that weapon.";
            }
            case NOT_FOUND -> {
                return "You have no weapon to reload.";
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public Room currentRoom() {
        return player.getCurrentRoom();
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void victory() {
        hasWon = true;
    }

    public boolean hasLost() {
        if (player.isAlive()) {
            hasLost = false;
        } else {
            hasLost = true;
        }
        return hasLost;
    }

}
