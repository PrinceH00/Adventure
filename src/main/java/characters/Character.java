package characters;

import items.Item;

import java.util.ArrayList;
import room.Room;

public class Characters {
    protected final int MAX_HEALTH;
    protected final ArrayList<Item> inventory;
    protected int health;
    protected Room currentRoom;

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
