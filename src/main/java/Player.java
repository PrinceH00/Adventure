import Enums.Direction;
import Enums.ReturnMessage;
import Items.*;
import java.util.ArrayList;

public class Player {
    private final int MAX_HEALTH = 20;
    private final double MAX_WEIGHT = 30.0;
    private int health;
    private double weight;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private String healthDescription;


    //Constructor with lives as parameters.
    public Player() {
        health = MAX_HEALTH;
        inventory = new ArrayList<Item>();
        inventory.add(new Food("apple", "tastes nice", 1, 2));
        inventory.add(new Weapon("Battle Axe", "Smacks hard", 5, 10));
        inventory.add(new Armor("Leather Tunic", "Protects a little", 5, 5));
        inventory.add(new Food("pill","Glowing pill that whispers something",0.1,-1000));
        checkInventoryWeight();
        setHealthDescription();
    }

    //Get methods.
    public Room getCurrentRoom() { return currentRoom; }
    public Weapon getEquippedWeapon() { return equippedWeapon; }
    public Armor getEquippedArmor() { return equippedArmor; }
    public int getHealth() { return health; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }


    //Set methods.
    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }

    public String playerStats() {
        setHealthDescription();
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("The player has %s/%s health left.\n %s \n",
                health, MAX_HEALTH, healthDescription));
        stats.append(String.format("The player is carrying %s/%s kg.\n",
                weight, MAX_WEIGHT));
        stats.append(String.format("Items.Weapon: %s. %s damage\n", equippedWeapon, equippedWeapon.getDamage()));
        stats.append(String.format("Items.Armor: %s. %s armor\n", equippedArmor, equippedArmor.getArmorClass()));
        return stats.toString();
    }

    public void setHealthDescription() {
        if (health == 20) {
            healthDescription = "You are perfectly healthy go slay those fakkers.";
        } else if (health >= 15) {
            healthDescription = "You are a little hurt, but tis just a flesh wound.";
        } else if (health >= 10) {
            healthDescription = "It hurts but you can still walk.";
        }else if (health >= 5 ) {
            healthDescription = "Okay, maybe you should be a little cautious.";
        } else {
            healthDescription = "You are basically dead be careful.";
        }
    }

    public boolean isDark() { return currentRoom.getDark(); }

    public Enemy currentEnemy() { return currentRoom.getEnemy(); }

    public Item checkInventoryForItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().toLowerCase().contains(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void checkInventoryWeight() {
        for (Item item : inventory) {
            weight += item.getWeight();
        }
    }

    public ReturnMessage takeItem(String itemName) {
        Item itemToTake = currentRoom.checkItems(itemName);
        if (itemToTake != null && (weight + itemToTake.getWeight()) <= MAX_WEIGHT) {
            currentRoom.removeItem(itemToTake);
            inventory.add(itemToTake);
            weight += itemToTake.getWeight();
            return ReturnMessage.CAN;
        } else if (itemToTake == null) {
            return ReturnMessage.CANT;
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public ReturnMessage dropItem(String itemName) {
        Item itemToDrop = checkInventoryForItem(itemName);
        if (itemToDrop != null) {
            inventory.remove(itemToDrop);
            currentRoom.addItem(itemToDrop);
            return ReturnMessage.CAN;
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public ReturnMessage eat(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Food.class) {
            Food food = (Food) itemToUse;
            return canEat(food);
        } else if (itemToUse.getClass() != Food.class){
            return ReturnMessage.CANT;
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public ReturnMessage canEat(Food food) {
        if (health + food.getHealthRecovery() <= MAX_HEALTH && health + food.getHealthRecovery() >= 1) {
            inventory.remove(food);
            health += food.getHealthRecovery();
            return ReturnMessage.CAN;
        } else if (health + food.getHealthRecovery() >= MAX_HEALTH) {
            inventory.remove(food);
            health = MAX_HEALTH;
            return ReturnMessage.CAN_MUCH;
        } else {
            health = 0;
            isAlive();
            return ReturnMessage.CAN_LITTLE;
        }
    }

    public ReturnMessage drink(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Liquid.class) {
            Liquid liquid = (Liquid) itemToUse;
            return canDrink(liquid);
        } else if (itemToUse.getClass() != Liquid.class){
            return ReturnMessage.CANT;
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public ReturnMessage canDrink(Liquid liquid) {
        if (health + liquid.getHealthRecovery() <= MAX_HEALTH) {
            inventory.remove(liquid);
            health += liquid.getHealthRecovery();
            return ReturnMessage.CAN;
        } else if (health + liquid.getHealthRecovery() >= MAX_HEALTH) {
            inventory.remove(liquid);
            health = MAX_HEALTH;
            return ReturnMessage.CAN_MUCH;
        } else {
            health = 0;
            isAlive();
            return ReturnMessage.CAN_LITTLE;
        }
    }

    public ReturnMessage equipItem(String itemName, String weaponOrArmor) {
        Item itemToEquip = checkInventoryForItem(itemName);
        if (itemToEquip != null && itemToEquip.getClass().getSuperclass() == Equipment.class) {
            if ((weaponOrArmor.equals("w") || weaponOrArmor.equals("weapon"))
                    && itemToEquip.getClass() == Weapon.class) {
                return equipWeapon((Weapon) itemToEquip);
            } else if ((weaponOrArmor.equals("a") || weaponOrArmor.equals("armor"))
                    && itemToEquip.getClass() == Armor.class) {
                return equipArmor((Armor) itemToEquip);
            } else {
                return ReturnMessage.CANT;
            }
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public ReturnMessage equipWeapon(Weapon weapon) {
        if (equippedWeapon != null) {
            stashItem(Weapon.class);
        }
        inventory.remove(weapon);
        equippedWeapon = weapon;
        return ReturnMessage.CAN;
    }

    private ReturnMessage equipArmor(Armor armor) {
        if (equippedArmor != null) {
            stashItem(Armor.class);
        }
        inventory.remove(armor);
        equippedArmor = armor;
        return ReturnMessage.CAN;
    }

    public String stashItem(Class armorOrWeapon) {
        if (armorOrWeapon == Weapon.class) {
            if (equippedWeapon != null) {
                String formerWeapon = equippedWeapon.getName();
                inventory.add(equippedWeapon);
                equippedWeapon = null;
                return String.format("You have stashed %s", formerWeapon);
            } else {
                return "There was no weapon to stash.";
            }
        } else {
            return "Illegal statement";
        }
    }

    public String inventoryToString() {
        if (!inventory.isEmpty()) {
            StringBuilder inventoryString = new StringBuilder();
            for (Item item : inventory) {
                inventoryString.append(item).append("\n");
            }
            return String.format("%s\nThere is %s items in the inventory.", inventoryString, inventory.size());
        } else {
            return "The inventory is empty.";
        }
    }

    public ReturnMessage turnLight(String state) {
        switch (state) {
            case "on" -> {
                getCurrentRoom().setDark(false);
                getCurrentRoom().setVisited();
                return ReturnMessage.CAN;
            }
            case "off" -> {
                getCurrentRoom().setDark(true);
                return ReturnMessage.CANT;
            }
            default -> {
                return ReturnMessage.NOT_FOUND;
            }
        }
    }

    //Moves the player while checking the if it is a legal move.
    public Direction move(String direction) {
        switch (direction) {
            case "north", "n" -> {
                if (moveNorth()) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    return Direction.NORTH;
                } else {
                    return Direction.NULL;
                }
            }
            case "east", "e" -> {
                if (moveEast()) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    return Direction.EAST;
                } else {
                    return Direction.NULL;
                }
            }
            case "south", "s" -> {
                if (moveSouth()) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    return Direction.SOUTH;
                } else {
                    return Direction.NULL;
                }
            }
            case "west", "w" -> {
                if (moveWest()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    return Direction.WEST;
                } else {
                    return Direction.NULL;
                }
            }
            default -> {
                return Direction.NOT_FOUND;
            }
        }
    }

    //booleans moving the player N,S,E,W If it is not null.
    public boolean moveNorth() {
        if (currentRoom.getNorth() != null) {
            if (!currentRoom.getDark()) {
                currentRoom.setLastRoom(currentRoom);
                currentRoom.setVisited();
                return true;
            } else {
                if (currentRoom.getNorth() == currentRoom.getLastRoom()) {
                    currentRoom.setLastRoom(currentRoom);
                    currentRoom.setVisited();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null) {
            if (!currentRoom.getDark()) {
                currentRoom.setLastRoom(currentRoom);
                currentRoom.setVisited();
                return true;
            } else {
                if (currentRoom.getEast() == currentRoom.getLastRoom()) {
                    currentRoom.setLastRoom(currentRoom);
                    currentRoom.setVisited();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveSouth() {
        if (currentRoom.getSouth() != null) {
            if (!currentRoom.getDark()) {
                currentRoom.setLastRoom(currentRoom);
                currentRoom.setVisited();
                return true;
            } else {
                if (currentRoom.getSouth() == currentRoom.getLastRoom()) {
                    currentRoom.setLastRoom(currentRoom);
                    currentRoom.setVisited();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveWest() {
        if (currentRoom.getWest() != null) {
            if (!currentRoom.getDark()) {
                currentRoom.setLastRoom(currentRoom);
                currentRoom.setVisited();
                return true;
            } else {
                if (currentRoom.getWest() == currentRoom.getLastRoom()) {
                    currentRoom.setLastRoom(currentRoom);
                    currentRoom.setVisited();
                    return true;
                }
            }
        }
        return false;
    }

    //Attack methods.
    public double attackPlayer() {
        health = (health - (currentEnemy().getDamage() - equippedArmor.getArmorClass()));
        return currentEnemy().getDamage();
    }

    public double attackEnemy() {
        currentEnemy().setHealth(currentEnemy().getHealth() - equippedWeapon.getDamage());
        return equippedWeapon.getDamage();
    }

    public ReturnMessage attack() {
        if (currentRoom.getHasEnemy() && equippedWeapon != null) {
            attackEnemy();
            if (currentRoom.getHasEnemy()) {
                attackPlayer();
                if (isAlive()) {
                    return ReturnMessage.CAN;
                } else {
                    return ReturnMessage.CAN_LITTLE;
                }
            } else {
                return ReturnMessage.CAN_MUCH;
            }
        } else if (!currentRoom.getHasEnemy()) {
            return ReturnMessage.NOT_FOUND;
        } else {
            return ReturnMessage.CANT;
        }
    }

    public boolean isAlive() {
        if (health > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Giving a description of the room, depending on what the player "knows"
    public String look() {
        StringBuilder stringBuilder = new StringBuilder();
        if (currentRoom != null) {
            if (!currentRoom.getDark()) {
                if (currentRoom.getVisited()) {
                    stringBuilder.append(String.format("%s.\n%s \n", currentRoom.getDescription(), currentRoom.listItemsInRoom())).append("\n");
                } else {
                    stringBuilder.append(String.format("You are in %s %s", currentRoom.getRoomName(), currentRoom.getDescription())).append("\n");
                }
                stringBuilder.append(currentRoom.exits());
                return stringBuilder.toString();
            } else {
                return "The room is pitch black you see nothing but the entrance you came in from. \n";
            }
        } else {
            System.exit(0);
            return "";
        }
    }
}