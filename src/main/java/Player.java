import java.util.ArrayList;

public class Player {
    private int health;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(){}

    //Constructor with lives as parameters.
    public Player(int health) {
        inventory = new ArrayList<Item>();
        this.health = health;
    }

    //Get methods.
    public Room getCurrentRoom() { return currentRoom; }
    public int getHealth() { return health; }

    //Set methods.
    public void setCurrentRoom(Room selectedRoom) { currentRoom = selectedRoom; }
    public void setHealth(int health) { this.health = health; }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    public String inventoryToString() {
        String inventoryString = "";
        for (Item item:inventory) {
            inventoryString += item;
        }
        return inventoryString;
    }

    //booleans moving the player N,S,E,W If it is not null.
    public boolean moveNorth() {
        if (currentRoom.getNorth() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else
            return false;
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null && !currentRoom.getDark()) {
            currentRoom.setVisited(true);
            return true;
        } else
            return false;
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
    public String look(){
        if (currentRoom != null) {
            if (!currentRoom.getDark()) {
                if (currentRoom.getVisited()) {
                    return currentRoom.getDescription();
                } else {
                    return String.format("You are in %s %s", currentRoom.getRoomName(), currentRoom.getDescription());
                }
            } else {
                return "the room is pitch black you see nothing but the entrance you came in from. ";
            }
        } else {
            System.exit(0);
            return "";
        }
    }
}
