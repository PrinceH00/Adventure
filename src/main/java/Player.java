import java.util.ArrayList;

public class Player {
    private int health;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    //Constructor with lives as parameters.
    public Player(int health) {
        this.health = health;
        inventory = new ArrayList<Item>();
    }

    //Get methods.
    public ArrayList<Item> getInventory() { return inventory; }
    public Room getCurrentRoom() { return currentRoom; }
    public int getHealth() { return health; }

    //Set methods.
    public void setCurrentRoom(Room selectedRoom) { currentRoom = selectedRoom; }
    public void setHealth(int health) { this.health = health; }

    public Item checkInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public String takeItem(String itemName) {
        Item itemToTake = currentRoom.checkItems(itemName);
        if (itemToTake != null) {
            currentRoom.removeItem(itemToTake);
            inventory.add(itemToTake);
            return String.format("%s has been picked up successfully.", itemName);
        } else {
            return String.format("There is no such item in the %s", currentRoom.getRoomName());
        }
    }

    public String dropItem(String itemName) {
        Item itemToDrop = checkInventory(itemName);
        if (itemToDrop != null) {
            inventory.remove(itemToDrop);
            currentRoom.addItem(itemToDrop);
            return String.format("%s has been dropped successfully.", itemName);
        } else {
            return String.format("There is no such item in the %s", currentRoom.getRoomName());
        }
    }

    public String useItem(String itemName) {
        Item itemToUse = checkInventory(itemName);
        if (itemToUse != null && itemToUse.getUseAble()) {
            inventory.remove(itemToUse);
            return String.format("%s has been used and disappeared.", itemName);
        } else if (itemToUse == null) {
            return String.format("There is no such item in the %s", currentRoom.getRoomName());
        } else {
            return String.format("%s ", currentRoom.getRoomName());
        }
    }

    public String inventoryToString() {
        if (!inventory.isEmpty()) {
            String inventoryString = "";
            for (Item item : inventory) {
                inventoryString += item + "\n";
            }
            return String.format("%s\nThere is %s items in the inventory.", inventoryString,inventory.size());
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
    public String look(){
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
