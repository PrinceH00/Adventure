import java.util.ArrayList;

public class Room {
    //Private Objects
    private ArrayList<Item> itemsInRoom;
    private Room north;
    private Room east;
    private Room south;
    private Room west;

    //Private attributes.
    private String roomName;
    private String description;
    private boolean visited;
    private boolean canBeDark;
    private boolean dark;

    //Constructor without parameters
    public Room() {
        itemsInRoom = new ArrayList<>();
    }

    //Constructor with 2 parameters
    public Room(String roomName, String description, boolean canBeDark) {
        this.roomName = roomName;
        this.description = description;
        this.canBeDark = canBeDark;
        dark = canBeDark;
    }

    //Get methods
    public Room getNorth() { return north; }
    public Room getEast() { return east; }
    public Room getSouth() { return south; }
    public Room getWest() { return west; }
    public String getDescription() { return description; }
    public String getRoomName() { return roomName; }

    //Set methods
    public void setNorth(Room northRoom) {
        north = northRoom;
        if (northRoom.getSouth() != this) {
            northRoom.setSouth(this);
        }
    }
    public void setEast(Room eastRoom) {
        east = eastRoom;
        if (eastRoom.getWest() != this) {
            eastRoom.setWest(this);
        }
    }
    public void setSouth(Room southRoom) {
        south = southRoom;
        if (southRoom.getNorth() != this) {
            southRoom.setNorth(this);
        }
    }
    public void setWest(Room westRoom) {
        west = westRoom;
        if (westRoom.getEast() != this) {
            westRoom.setEast(this);
        }
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setDark(boolean dark) {
        if (canBeDark) {
            this.dark = dark;
        }
    }

    public boolean getDark() {
        return dark;
    }

    public boolean getCanBeDark() {
        return canBeDark;
    }

    public void addItem(Item item) {
        itemsInRoom.add(item);
    }

    public void removeItem(Item item) {
        itemsInRoom.remove(item);
    }

    public String listItemsInRoom() {
        String roomItems = "";
        for (Item item:itemsInRoom) {
            roomItems += item;
        }
        return roomItems;
    }

    public String toString() { return String.format("%s: %s", roomName, description); }

    /*
    public String getItemsInRoom() {
        String items = "";
        for (Item item : itemsInRoom) {
            items += item.getName();
        }
        return items;
    }

    public void addItemsInRoom(Item item) {
        itemsInRoom.add(item);
    }
     */
}
