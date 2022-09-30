import java.util.ArrayList;

public class Player {
    private final int MAX_HEALTH = 20;
    private final double MAX_WEIGHT = 15.0;
    private int health;
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
        inventory.add(new Weapon("Leather Tunic", "Protects a little", 5, 5));
        checkInventoryWeight();
    }

    //Get methods.
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    //Set methods.
    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String playerStats() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("The player has %s/%s health left.\n",
                health, MAX_HEALTH));
        stats.append(String.format("The player is carrying %s/%s kg.\n",
                weight, MAX_WEIGHT));
        stats.append(String.format("Weapon: %s.\n", equippedWeapon));
        return stats.toString();
    }

    public Item checkInventoryForItem(String itemName) {
        itemName.toLowerCase();
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

    public String eatFood(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Food.class) {
            if (health + ((Food) itemToUse).getHealthRecovery() <= MAX_HEALTH) {
                inventory.remove(itemToUse);
                health += ((Food) itemToUse).getHealthRecovery();
                return String.format("%s has been eaten.", itemName);
            } else if (health < MAX_HEALTH) {
                inventory.remove(itemToUse);
                health = MAX_HEALTH;
                int overHeal = (health + ((Food) itemToUse).getHealthRecovery()) - MAX_HEALTH;
                return String.format("%s has been eaten, with %s over healing.", itemName, overHeal);
            } else {
                return "You can't eat anymore, your health is full and so is your stomach.";
            }
        } else if (itemToUse == null) {
            return String.format("There is no such item in the inventory");
        } else {
            return String.format("%s is not food, you can't eat it.", itemName);
        }
    }

    public String equipItem(String itemName) {
        Item itemToEquip = checkInventoryForItem(itemName);
        if (itemToEquip != null && itemToEquip.getClass() == Weapon.class) {
            stashItem(Weapon.class);
            inventory.remove(itemToEquip);
            equippedWeapon = (Weapon) itemToEquip;
            return String.format("%s has been equipped.", itemToEquip.getName());
        } else if (itemToEquip != null && itemToEquip.getClass() == Armor.class) {
            stashItem(Armor.class);
            inventory.remove(itemToEquip);
            equippedArmor = (Armor) itemToEquip;
            return String.format("%s has been equipped.", itemToEquip.getName());
        } else if (itemToEquip == null) {
            return String.format("There is no such item in the inventory");
        } else {
            return String.format("%s is not a weapon or armor piece.", itemName);
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
        } else if (armorOrWeapon == Armor.class) {
            if (equippedArmor != null) {
                String formerArmor = equippedWeapon.getName();
                inventory.add(equippedArmor);
                equippedArmor = null;
                return String.format("You have stashed %s", formerArmor);
            } else {
                return "There was no armor to stash.";
            }
        } else {
            return "Illegal statement";
        }
    }

    public String swapItem(String itemName, String armorOrWeapon) {
        if (armorOrWeapon.toLowerCase().equals("weapon") || armorOrWeapon.equals("w")) {
            String formerWeapon = equippedWeapon.getName();
            stashItem(Weapon.class);
            equipItem(itemName);
            return String.format("You have swapped your weapon from %s, to %s",
                    formerWeapon, equippedWeapon.getName());
        } else if (armorOrWeapon.equals("armor") || armorOrWeapon.equals("a")) {
            String formerWeapon = equippedArmor.getName();
            stashItem(Armor.class);
            equipItem(itemName);
            return String.format("You have swapped your weapon from %s, to %s",
                    formerWeapon, equippedArmor.getName());
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

    //booleans moving the player N,S,E,W If it is not null.
    public boolean moveNorth() {
        if (currentRoom.getNorth() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        if (currentRoom.getSouth() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else
            return false;
    }

    public boolean moveWest() {
        if (currentRoom.getWest() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else
            return false;
    }

    //Giving a description of the room, depending on what the player "knows"
    public String look() {
        if (currentRoom != null) {
            if (!currentRoom.getDark()) {
                if (currentRoom.getVisited()) {
                    return String.format("%s. %s", currentRoom.getDescription(), currentRoom.listItemsInRoom());
                } else {
                    return String.format("You are in %s %s", currentRoom.getRoomName(), currentRoom.getDescription());
                }
            } else {
                return "The room is pitch black you see nothing but the entrance you came in from. ";
            }
        } else {
            System.exit(0);
            return "";
        }
    }
}