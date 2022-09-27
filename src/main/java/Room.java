import java.util.ArrayList;

public class Room {
    //Private objekter
    private ArrayList<Item> itemsInRoom;
    private Room north;
    private Room east;
    private Room south;
    private Room west;

    //Private atributter.
    private String roomName;
    private String description;

    //Konstroktør uden paraperte
    public Room() {
        itemsInRoom = new ArrayList<>();
    }

    //Kontroktør med 2 Parametrere.
    public Room(String roomName, String description) {
        this.roomName = roomName;
        this.description = description;
    }

    //Get metoder
    public Room getNorth() { return north; }
    public Room getEast() { return east; }
    public Room getSouth() { return south; }
    public Room getWest() { return west; }

    public String getDescription() { return description; }
    public String getRoomName() { return roomName; }

    //Set metoder
    public void setNorth(Room northRoom) { north = northRoom; }
    public void setEast(Room eastRoom) { east = eastRoom; }
    public void setSouth(Room southRoom) { south = southRoom; }
    public void setWest(Room westRoom) { west = westRoom; }

    // Tosting til print layoutet
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
