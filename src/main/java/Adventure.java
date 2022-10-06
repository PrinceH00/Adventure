import Enums.Direction;
import Enums.ReturnMessage;
import Items.Food;
import Items.Item;
import Items.Liquid;

public class Adventure {
    //Private objects of the player og item class
    private final Player player;
    private final Map map;
    private boolean hasWon = false;
    private boolean hasLost = false;
    private ReturnMessage result;

    //Constructor without parameters
    public Adventure() {
        player = new Player();
        map = new Map();
        player.setCurrentRoom(map.getEmptyRoom());
        player.getCurrentRoom().setVisited();
    }

    //Get methode.
    public Player getPlayer() {
        return player;
    }

    public String movePlayer(String direction) {
        Direction moveDirection = player.move(direction);
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
                        food.getHealthRecovery());
            }
            case CAN_MUCH -> {
                double overHeal = (player.getHealth() + food.getHealthRecovery()) - player.getMAX_HEALTH();
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
                return String.format("%s has been drunk and you have regained %s health", liquid.getName(),
                        liquid.getHealthRecovery());
            }
            case CAN_MUCH -> {
                double overHeal = (player.getHealth() + liquid.getHealthRecovery()) - player.getMAX_HEALTH();
                return String.format("%s has been drunk, with %s over healing.", liquid.getName(), overHeal);
            }
            case CAN_LITTLE -> {
                return String.format("%s has been drunk, and you have fainted.", liquid.getName());
            }
            case CANT -> {
                return String.format("%s is not food, you can't eat it.", itemToDrink);
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

    public String equipWeapon(String itemToEquip, String weaponOrArmor) {
        StringBuilder combatInfo = new StringBuilder();
        Item equippedItem = player.checkInventoryForItem(itemToEquip);
        result = player.equipItem(itemToEquip, weaponOrArmor);
        switch (result) {
            case CAN -> {
                return String.format("%s has been equipped.", equippedItem.getName());
            }
            case CANT -> {
                return String.format("%s is not a weapon or armor piece.", itemToEquip);
            }
            case NOT_FOUND -> {
                return String.format("There is no such item in the inventory");
            }
            default -> {
                return "Something went wrong.";
            }
        }
    }

    public String stashWeapon(Class weapon) {
        return player.stashItem(weapon);
    }

    public String stats() {
        return player.playerStats();
    }

    public String attack() {
        StringBuilder combatInfo = new StringBuilder();
        result = player.attack();
        switch (result) {
            case NO_USES ->  {
                combatInfo.append("Your Weapon is broken and can no longer be used to attack.");
            }
            case CANT -> {
                combatInfo.append("You have no weapon equipped.");
            }
            case NOT_FOUND -> {
                combatInfo.append("There is no enemy enemy in this room.");
            }
            default -> {
                combatInfo.append(String.format("You have attacked %s and dealt %s damage. \n",
                        player.currentEnemy().getName(),
                        player.getEquippedWeapon().getDamage()));
                combatInfo.append(String.format("You have been attacked and have taken %s damage. \n",
                        player.currentEnemy().getDamage()));
                switch (result) {
                    case CAN -> {
                        combatInfo.append(String.format("Player: %s/%s health\n", player.getHealth(), player.getMAX_HEALTH()));
                        combatInfo.append(String.format("%s: %s/%s health\n", player.currentEnemy().getName(),
                                player.currentEnemy().getHealth(),
                                player.currentEnemy().getMAX_HEALTH()));
                    }
                    case CAN_MUCH -> {
                        combatInfo.append(String.format("%s, has been slain and you get %s", player.currentEnemy().getName(),
                                player.currentEnemy().getLoot()));
                    }
                    case CAN_LITTLE -> {
                        combatInfo.append("You have died. Restarting....");
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
        result = player.dropItem(itemToDrop);
        switch (result) {
            case CAN -> {
                return String.format("%s has been dropped successfully.", itemToDrop);
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
        return hasLost;
    }

    public void lost() {
        hasLost = true;
    }
}
