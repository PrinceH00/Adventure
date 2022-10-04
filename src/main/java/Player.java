import java.util.ArrayList;

public class Player {
    private final int MAX_HEALTH = 20;
    private final double MAX_WEIGHT = 30.0;
    private double health;
    private double weight;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    //Constructor with lives as parameters.
    public Player(int health) {
        this.health = health;
        inventory = new ArrayList<Item>();
        inventory.add(new Food("apple", "tastes nice", 1, 2));
        inventory.add(new Weapon("Battle Axe", "Smacks hard", 5, 10));
        inventory.add(new Armor("Leather Tunic", "Protects a little", 5, 5));
        checkInventoryWeight();
    }

    //Get methods.
    public Room getCurrentRoom() { return currentRoom; }

    public Weapon getEquippedWeapon() { return equippedWeapon; }
    public Armor getEquippedArmor() { return equippedArmor; }

    //Set methods.
    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }

    public void setHealth(double health) { this.health = health; }

    public double getHealth() { return health; }

    public int getMAX_HEALTH() { return MAX_HEALTH; }

    public String playerStats() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("The player has %s/%s health left.\n",
                health, MAX_HEALTH));
        stats.append(String.format("The player is carrying %s/%s kg.\n",
                weight, MAX_WEIGHT));
        stats.append(String.format("Weapon: %s. %s damage\n", equippedWeapon, equippedWeapon.getDamage()));
        stats.append(String.format("Armor: %s. %s armor\n", equippedArmor, equippedArmor.getArmorClass()));
        return stats.toString();
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

    public String takeItem(String itemName) {
        Item itemToTake = currentRoom.checkItems(itemName);
        if (itemToTake != null && (weight + itemToTake.getWeight()) <= MAX_WEIGHT) {
            currentRoom.removeItem(itemToTake);
            inventory.add(itemToTake);
            weight += itemToTake.getWeight();
            return String.format("%s has been picked up successfully.", itemName);
        } else if (itemToTake == null) {
            return String.format("There is no such item in the %s", currentRoom.getRoomName());
        } else {
            return String.format("You can't carry that, it's too heavy.");
        }
    }

    public String dropItem(String itemName) {
        Item itemToDrop = checkInventoryForItem(itemName);
        if (itemToDrop != null) {
            inventory.remove(itemToDrop);
            currentRoom.addItem(itemToDrop);
            return String.format("%s has been dropped successfully.", itemName);
        } else {
            return String.format("There is no such item in the inventory");
        }
    }

    public String eat(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Food.class) {
            Food food = (Food) itemToUse;
            if (health + food.getHealthRecovery() <= MAX_HEALTH) {
                inventory.remove(food);
                health += food.getHealthRecovery();
                return String.format("%s has been eaten and you have regained %s health", food,
                        food.getHealthRecovery());
            } else if (health < MAX_HEALTH) {
                inventory.remove(food);
                health = MAX_HEALTH;
                double overHeal = (health + food.getHealthRecovery()) - MAX_HEALTH;
                return String.format("%s has been eaten, with %s over healing.", food, overHeal);
            } else {
                return "You can't eat anymore, your health is full and so is your stomach.";
            }
        } else if (itemToUse.getClass() != Food.class){
            return String.format("%s is not food, you can't eat it.", itemName);
        } else {
        return String.format("There is no such item in the inventory");
        }
    }

    public String drink(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Liquid.class) {
            Liquid liquid = (Liquid) itemToUse;
            if (health + liquid.getHealthRecovery() <= MAX_HEALTH) {
                inventory.remove(liquid);
                health += liquid.getHealthRecovery();
                return String.format("%s has been drunk and you have regained %s health", liquid,
                        liquid.getHealthRecovery());
            } else if (health < MAX_HEALTH) {
                inventory.remove(liquid);
                health = MAX_HEALTH;
                double overHeal = (health + liquid.getHealthRecovery()) - MAX_HEALTH;
                return String.format("%s has been drunk, with %s over healing.", liquid, overHeal);
            } else {
                return "You can't drink anymore, your health is full and so is your stomach.";
            }
        } else if (itemToUse.getClass() != Liquid.class){
            return String.format("%s is not a liquid, you can't drink it.", itemName);
        } else {
            return String.format("There is no such item in the inventory");
        }
    }

    public String equipItem(String itemName, String weaponOrArmor) {
        Item itemToEquip = checkInventoryForItem(itemName);
        if (itemToEquip != null) {
            if ((weaponOrArmor.equals("w") || weaponOrArmor.equals("weapon"))
                    && itemToEquip.getClass() == Weapon.class) {
                if (equippedWeapon != null) {
                    stashItem(Weapon.class);
                }
                inventory.remove(itemToEquip);
                equippedWeapon = (Weapon) itemToEquip;
                return String.format("%s has been equipped.", itemToEquip.getName());
            } else if ((weaponOrArmor.equals("w") || weaponOrArmor.equals("weapon"))
                    && itemToEquip.getClass() == Armor.class) {
                if (equippedArmor != null) {
                    stashItem(Armor.class);
                }
                inventory.remove(itemToEquip);
                equippedArmor = (Armor) itemToEquip;
                return String.format("%s has been equipped.", itemToEquip.getName());
            } else {
                return String.format("%s is not a weapon or armor piece.", itemName);
            }
        } else {
            return String.format("There is no such item in the inventory");
        }
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

    public String turnLight(String state) {
        switch (state) {
            case "on" -> {
                getCurrentRoom().setDark(false);
                getCurrentRoom().setVisited();
                return "The light gives off a blinding light, but your eyes quickly adjusts.";
            }
            case "off" -> {
                getCurrentRoom().setDark(true);
                return "The darkness consumes you, but your eyes quickly adjusts.";
            }
            default -> {
                return String.format("%s is not a valid state for the light.", state);
            }
        }
    }

    //Moves the player while checking the if it is a legal move.
    public String move(String direction) {
        switch (direction) {
            case "north", "n" -> {
                if (moveNorth()) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    return currentRoom.toString();
                } else {
                    return "There is no exit in this room to the north.";
                }
            }
            case "east", "e" -> {
                if (moveEast()) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    return getCurrentRoom().toString();
                } else {
                    return "There is no exit in this room to the east.";
                }
            }
            case "south", "s" -> {
                if (moveSouth()) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    return getCurrentRoom().toString();
                } else {
                    return "There is no exit in this room to the south.";
                }
            }
            case "west", "w" -> {
                if (moveWest()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    return getCurrentRoom().toString();
                } else {
                    return "There is no exit in this room to the west.";
                }
            }
            default -> {
                return "There is no way such as: " + direction;
            }
        }
    }

    //booleans moving the player N,S,E,W If it is not null.
    public boolean moveNorth() {
        if (currentRoom.getNorth() != null && !currentRoom.getDark()) {
            currentRoom.setVisited();
            return true;
        } else {
            return false;
        }
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null && !currentRoom.getDark()) {
            currentRoom.setVisited();
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        if (currentRoom.getSouth() != null && !currentRoom.getDark()) {
            currentRoom.setVisited();
            return true;
        } else
            return false;
    }

    public boolean moveWest() {
        if (currentRoom.getWest() != null && !currentRoom.getDark()) {
            currentRoom.setVisited();
            return true;
        } else
            return false;
    }

    //Giving a description of the room, depending on what the player "knows"
    public String look() {
        StringBuilder stringBuilder = new StringBuilder();
        if (currentRoom != null) {
            if (!currentRoom.getDark()) {
                if (currentRoom.getVisited()) {
                    stringBuilder.append(String.format("%s. %s", currentRoom.getDescription(), currentRoom.listItemsInRoom()));
                } else {
                    return String.format("You are in %s %s", currentRoom.getRoomName(), currentRoom.getDescription());
                }
                stringBuilder.append(currentRoom.exits());
                return stringBuilder.toString();
            } else {
                return "The room is pitch black you see nothing but the entrance you came in from. ";
            }
        } else {
            System.exit(0);
            return "";
        }
    }
}