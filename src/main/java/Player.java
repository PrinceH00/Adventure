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
    private String healthDescription;


    //Constructor with lives as parameters.
    public Player(int health) {
        this.health = health;
        inventory = new ArrayList<Item>();
        inventory.add(new Food("apple", "tastes nice", 1, 2));
        inventory.add(new Weapon("Battle Axe", "Smacks hard", 5, 10));
        inventory.add(new Armor("Leather Tunic", "Protects a little", 5, 5));
        inventory.add(new Food("pill","Glowing pill that wispers someting",0.1,-1000));
        checkInventoryWeight();
        setHealthDescription();
    }

    //Get methods.
    public Room getCurrentRoom() { return currentRoom; }
    public Weapon getEquippedWeapon() { return equippedWeapon; }
    public Armor getEquippedArmor() { return equippedArmor; }
    public double getHealth() { return health; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }


    //Set methods.
    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }
    public void setHealth(double health) { this.health = health; }

    public String playerStats() {
        setHealthDescription();
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("The player has %s/%s health left.\n %s \n",
                health, MAX_HEALTH, healthDescription));
        stats.append(String.format("The player is carrying %s/%s kg.\n",
                weight, MAX_WEIGHT));
        stats.append(String.format("Weapon: %s. %s damage\n", equippedWeapon, equippedWeapon.getDamage()));
        stats.append(String.format("Armor: %s. %s armor\n", equippedArmor, equippedArmor.getArmorClass()));
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
    //@TODO Make ENUMS
    public String eat(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Food.class) {
            Food food = (Food) itemToUse;
            if (health + food.getHealthRecovery() <= MAX_HEALTH && health + food.getHealthRecovery() >= 1) {
                inventory.remove(food);
                health += food.getHealthRecovery();
                return String.format("%s has been eaten and you have regained %s health", food,
                        food.getHealthRecovery());
            } else if (health + food.getHealthRecovery() >= MAX_HEALTH) {
                inventory.remove(food);
                health = MAX_HEALTH;
                double overHeal = (health + food.getHealthRecovery()) - MAX_HEALTH;
                return String.format("%s has been eaten, with %s over healing.", food, overHeal);
            } else if (true) {
                return "";
            } else {
                return "You can't eat anymore, your health is full and so is your stomach.";
            }
        } else if (itemToUse.getClass() != Food.class){
            return String.format("%s is not food, you can't eat it.", itemName);
        } else {
            return String.format("There is no such item in the inventory");
        }
    }
    /*public ReturnMessage eat(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Food.class) {
            Food food = (Food) itemToUse;
            if (health + food.getHealthRecovery() <= MAX_HEALTH && health + food.getHealthRecovery() >= 1) {
                inventory.remove(food);
                health += food.getHealthRecovery();
                return ReturnMessage.CAN;
            } else if (health + food.getHealthRecovery() >= MAX_HEALTH) {
                inventory.remove(food);
                health = MAX_HEALTH;
                double overHeal = (health + food.getHealthRecovery()) - MAX_HEALTH;
                return ReturnMessage.CAN;
            } else {
                return ReturnMessage.CANT;
            }
        } else if (itemToUse.getClass() != Food.class){
            return ReturnMessage.CANT;
        } else {
            return String.format("There is no such item in the inventory");
        }
    }*/

    public String drink(String itemName) {
        Item itemToUse = checkInventoryForItem(itemName);
        if (itemToUse != null && itemToUse.getClass() == Liquid.class) {
            Liquid liquid = (Liquid) itemToUse;
            if (health + liquid.getHealthRecovery() <= MAX_HEALTH) {
                inventory.remove(liquid);
                health += liquid.getHealthRecovery();
                return String.format("%s has been drunk and you have regained %s health", liquid,
                        liquid.getHealthRecovery());
            } else if (health + liquid.getHealthRecovery() >= MAX_HEALTH) {
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
        if (itemToEquip != null && itemToEquip.getClass() == Equipment.class) {
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

    //Attack methods.
    public double attackPlayer() {
        health = (health - (currentEnemy().getDamage()*(
                1-(equippedArmor.getArmorClass()/10))));
        return currentEnemy().getDamage();
    }

    public double attackEnemy() {
        currentEnemy().setHealth(currentEnemy().getHealth() - equippedWeapon.getDamage());
        return equippedWeapon.getDamage();
    }

    public String attack() {
        StringBuilder combatInfo = new StringBuilder();
        if (currentRoom.getHasEnemy() && equippedWeapon != null) {
            attackEnemy();
            combatInfo.append(String.format("You have attacked %s and dealt %s damage. \n",
                        currentRoom.getEnemy().getName(),
                        equippedWeapon.getDamage()));
            if (currentRoom.getHasEnemy()) {
                attackPlayer();
                combatInfo.append(String.format("You have been attacked and have taken %s damage. \n",
                        currentEnemy().getDamage()));
                if (isAlive()) {
                combatInfo.append(String.format("Player: %s/%s health\n", health, MAX_HEALTH));
                combatInfo.append(String.format("%s: %s/%s health\n", currentRoom.getEnemy().getName(),
                        currentRoom.getEnemy().getHealth(),
                        currentRoom.getEnemy().getMAX_HEALTH()));
                } else {
                    return String.format("You have died. Restarting....");
                }
            } else {
                return String.format("%s, has been slain and you get %s", currentEnemy().getName(),
                        currentEnemy().getLoot());
            }
        } else if (!currentRoom.getHasEnemy()) {
            combatInfo.append("There is no enemy enemy in this room.");
        } else {
            combatInfo.append("You have no weapon equipped.");
        }
        return combatInfo.toString();
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