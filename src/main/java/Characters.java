import Items.Equipment.BodyArmor;
import Items.Equipment.Shield;
import Items.Equipment.Weapon;
import Items.Item;

import java.util.ArrayList;

public class Characters {
    private final int MAX_HEALTH;
    private final ArrayList<Item> inventory;
    private int health;
    private Room currentRoom;
    private Weapon mainHandWeapon;
    private Weapon offHandWeapon;

    public Characters(int MAX_HEALTH, Room currentRoom) {
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.currentRoom = currentRoom;
        inventory = new ArrayList<Item>();
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
}
