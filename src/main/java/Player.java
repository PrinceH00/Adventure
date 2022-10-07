import Enums.Direction;
import Enums.ReturnMessage;
import Items.*;

import java.util.ArrayList;

public class Player {
    private final int MAX_HEALTH = 20;
    private final double MAX_WEIGHT = 30.0;
    private final ArrayList<Item> inventory;
    private int health;
    private double weight;
    private Room currentRoom;
    private Weapon mainHandWeapon;
    private Weapon offHandWeapon;
    private BodyArmor equippedArmor;
    private Shield equippedShield;
    private String healthDescription;


    //Constructor with lives as parameters.
    public Player() {
        health = MAX_HEALTH;
        inventory = new ArrayList<Item>();
        inventory.add(new Food("Apple", "tastes nice", 1, 2));
        inventory.add(new HeavyWeapon("Battle Axe", "Smacks hard", 5, 8));
        inventory.add(new LightWeapon("Sword", "Slashes through skin and bone", 5, 12));
        inventory.add(new LightWeapon("Club", "Bashes the with heavy weight", 5, 6));
        inventory.add(new LightWeapon("Hatchet", "it is rusty and used, but still sharp to the edge", 5, 9));
        inventory.add(new Bow("Recurve bow", "Shoots arrows", 5, 15, 5));
        inventory.add(new Armor("Leather Tunic", "Protects a little", 5, 5));
        inventory.add(new Food("Pill", "Glowing pill that whispers something", 0.1, -1000));
        inventory.add(new Arrow("Arrows", "Sharp flint tips that could pierce skin easily", 2, 10));
        inventory.add(new Gun("P911", "a modern type handgun", 3, 25, 7));
        inventory.add(new Bullet("5mm Bullet", "Saml 5mm bullet for p911", 2, 20));
        inventory.add(new Shield("Tower Shield", "Big shield covering from head to toe", 5, 10));
        checkInventory();
        setHealthDescription();
    }

    //Get methods.
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Weapon getMainHandWeapon() {
        return mainHandWeapon;
    }

    public Weapon getOffHandWeapon() {
        return offHandWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public int getHealth() {
        return health;
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }


    //Set methods.
    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }

    public String playerStats() {
        setHealthDescription();
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("The player has %s/%s health left.\n%s ",
                health, MAX_HEALTH, healthDescription)).append("\n");
        stats.append(String.format("The player is carrying %s/%s kg.",
                weight, MAX_WEIGHT)).append("\n");
        if (mainHandWeapon != null && mainHandWeapon.getDualWieldAble() && offHandWeapon == null) {
            stats.append(String.format("Main-hand weapon: %s.", mainHandWeapon)).append("\n");
            if (equippedShield != null) {
                stats.append(String.format("Shield: %s.", equippedShield)).append("\n");
            }
        } else if (mainHandWeapon != null && mainHandWeapon.getDualWieldAble()) {
            stats.append(String.format("Main-hand weapon: %s.", mainHandWeapon)).append("\n");
            if (offHandWeapon != null) {
                stats.append(String.format("Off-hand weapon: %s.", offHandWeapon)).append("\n");
            } else {
                stats.append("Off-hand weapon: none").append("\n");
            }
        } else if (mainHandWeapon != null && !mainHandWeapon.getDualWieldAble()) {
            stats.append(String.format("Two-handed weapon: %s.", mainHandWeapon)).append("\n");
        } else {
            stats.append("Main-hand weapon: none").append("\n");
            stats.append("Off-hand: none").append("\n");
        }
        if (equippedArmor != null) {
            stats.append(String.format("Armor: %s.", equippedArmor)).append("\n");
        } else {
            stats.append("Armor: none").append("\n");
        }
        return stats.toString();
    }

    public void setHealthDescription() {
        if (health == 20) {
            healthDescription = "You are perfectly healthy go slay those fakkers.";
        } else if (health >= 15) {
            healthDescription = "You are a little hurt, but tis just a flesh wound.";
        } else if (health >= 10) {
            healthDescription = "It hurts but you can still walk.";
        } else if (health >= 5) {
            healthDescription = "Okay, maybe you should be a little cautious.";
        } else {
            healthDescription = "You are basically dead be careful.";
        }
    }

    public boolean isDark() {
        return currentRoom.getDark();
    }

    public Enemy currentEnemy() {
        return currentRoom.getEnemy();
    }

    public Item checkInventoryForItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().toLowerCase().contains(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void checkInventory() {
        checkInventoryWeight();
        checkInventoryForAmmo();
    }

    public ReturnMessage checkInventoryForAmmo() {
        if (mainHandWeapon != null) {
            if (mainHandWeapon instanceof RangedWeapon) {
                for (Item item : inventory) {
                    if (item instanceof Ammunition) {
                        if (((Ammunition) item).getWeaponType() == mainHandWeapon.getClass()) {
                            mainHandWeapon.addRemainingUses(((Ammunition) item).getAmount());
                            inventory.remove(item);
                            return ReturnMessage.CAN;
                        }
                    }
                }
            } else {
                return ReturnMessage.CANT;
            }
        } else {
            return ReturnMessage.NOT_FOUND;
        }
        return null;
    }

    public void checkInventoryWeight() {
        weight = 0;
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
        if (itemToUse != null && itemToUse instanceof Food) {
            Food food = (Food) itemToUse;
            return canEat(food);
        } else if (itemToUse.getClass() != Food.class) {
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
        if (itemToUse != null && itemToUse instanceof Liquid) {
            Liquid liquid = (Liquid) itemToUse;
            return canDrink(liquid);
        } else if (itemToUse.getClass() != Liquid.class) {
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

    public ReturnMessage equipItem(String itemName, String weaponOrArmor, String mainOrOffHand) {
        Item itemToEquip = checkInventoryForItem(itemName);
        if (itemToEquip != null) {
            boolean equipment = checkItemIsEquipment(itemToEquip);
            if (equipment) {
                if ((weaponOrArmor.equals("w") || weaponOrArmor.equals("weapon"))
                        && itemToEquip instanceof Weapon) {
                    return equipWeapon((Weapon) itemToEquip, mainOrOffHand);
                } else if ((weaponOrArmor.equals("a") || weaponOrArmor.equals("armor"))
                        && itemToEquip instanceof Armor) {
                    return equipArmor((BodyArmor) itemToEquip);
                } else if ((weaponOrArmor.equals("s") || weaponOrArmor.equals("Shield"))
                        && itemToEquip instanceof Armor) {
                    return equipShield((Shield) itemToEquip);
                } else {
                    return ReturnMessage.CANT;
                }
            } else {
                return ReturnMessage.CANT;
            }
        } else {
            return ReturnMessage.NOT_FOUND;
        }
    }

    public boolean checkItemIsEquipment(Item itemToEquip) {
        if (itemToEquip instanceof Equipment) {
            return true;
        } else {
            return false;
        }
    }

    public ReturnMessage equipWeapon(Weapon weapon, String mainOrOff) {
        if (mainHandWeapon == null) {
            inventory.remove(weapon);
            mainHandWeapon = weapon;
        } else {
            if (offHandWeapon == null && mainHandWeapon.getDualWieldAble() && equippedShield == null) {
                if (weapon.getDualWieldAble()) {
                    inventory.remove(weapon);
                    offHandWeapon = weapon;
                } else {
                    return ReturnMessage.CAN_LITTLE;
                }
            } else if (mainHandWeapon != null && mainHandWeapon.getDualWieldAble() &&
                    offHandWeapon != null && equippedShield == null) {
                switch (mainOrOff) {
                    case "main", "m" -> {
                        stashItem(Weapon.class, mainOrOff);
                        inventory.remove(weapon);
                        mainHandWeapon = weapon;
                        return ReturnMessage.CAN;
                    }
                    case "off", "o" -> {
                        inventory.remove(weapon);
                        offHandWeapon = weapon;
                        return ReturnMessage.CAN;
                    }
                }
            } else if (!mainHandWeapon.getDualWieldAble()) {
                stashItem(Weapon.class, mainOrOff);
                inventory.remove(weapon);
                mainHandWeapon = weapon;
            }
        }
        return ReturnMessage.CAN;
    }

    private ReturnMessage equipArmor(BodyArmor armor) {
        if (equippedArmor != null) {
            stashItem(BodyArmor.class, "");
        }
        inventory.remove(armor);
        equippedArmor = armor;
        return ReturnMessage.CAN;
    }

    public ReturnMessage equipShield(Shield shield) {
        if (offHandWeapon == null) {
            if ((mainHandWeapon != null && mainHandWeapon.getDualWieldAble()) || mainHandWeapon == null) {
                if (equippedShield != null) {
                    stashItem(Shield.class, "");
                }
                inventory.remove(shield);
                equippedShield = shield;
                return ReturnMessage.CAN;
            } else {
                return ReturnMessage.CANT;
            }
        } else {
            return ReturnMessage.CANT;
        }
    }

    public ReturnMessage stashItem(Class armorOrWeapon, String mainOrOffHand) {
        if (armorOrWeapon.getClass().equals(Weapon.class)) {
            if (mainHandWeapon != null && mainOrOffHand.equals("m") || mainOrOffHand.equals("main")) {
                inventory.add(mainHandWeapon);
                mainHandWeapon = null;
            } else if (offHandWeapon != null && mainOrOffHand.equals("o") || mainOrOffHand.equals("off")) {
                inventory.add(offHandWeapon);
                offHandWeapon = null;
            } else {
                return ReturnMessage.CANT;
            }
        } else if (armorOrWeapon.getClass().equals(BodyArmor.class)) {
            if (equippedArmor != null) {
                inventory.add(equippedArmor);
                equippedArmor = null;
            } else {
                return ReturnMessage.CANT;
            }
        } else if (armorOrWeapon.getClass().equals(Shield.class)) {
            if (equippedShield != null) {
                inventory.add(equippedShield);
                equippedShield = null;
            } else {
                return ReturnMessage.CANT;
            }
        } else {
            return ReturnMessage.NOT_FOUND;
        }
        checkInventory();
        return ReturnMessage.CAN;
    }

    public String inventoryToString() {
        if (!inventory.isEmpty()) {
            checkInventory();
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
    public int attackPlayer() {
        health = (health - (currentEnemy().getDamage() - totalArmorClass()));
        return currentEnemy().getDamage();
    }

    public int attackEnemy() {
        mainHandWeapon.setCanUse();
        mainHandWeapon.used();
        currentEnemy().setHealth(currentEnemy().getHealth() - totalWeaponDamage());
        return mainHandWeapon.getDamage();
    }

    public int totalWeaponDamage() {
        if (mainHandWeapon != null && offHandWeapon != null) {
            return mainHandWeapon.getDamage() + offHandWeapon.getDamage();
        } else {
            return mainHandWeapon.getDamage();
        }
    }

    public int totalArmorClass() {
        int totalArmor = 0;
        if (equippedArmor != null) {
            totalArmor += equippedArmor.armorClass;
        }
        if (equippedShield != null) {
            totalArmor += equippedShield.armorClass;
        }
        return totalArmor;
    }

    public ReturnMessage attack() {
        if (currentRoom.getHasEnemy() && mainHandWeapon != null) {
            if (mainHandWeapon.getCanUse()) {
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
            } else {
                return ReturnMessage.NO_USES;
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