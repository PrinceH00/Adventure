import java.lang.reflect.Array;
import java.util.ArrayList;

public class Room {
    private ArrayList<Item> itemsInRoom = new ArrayList<>();
    private String roomName;
    private String description;
    private Room north;
    private Room east;
    private Room south;
    private Room west;

    public Room(){}

    public Room(String roomName,String description) {
        this.roomName = roomName;
        this.description = description;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room northRoom){
        north = northRoom;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room eastRoom){
        east = eastRoom;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room southRoom){
        south = southRoom;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room westRoom){
        west = westRoom;
    }

    public String getDescription(){
        return description;
    }
    public String getRoomName() {
        return roomName;
    }

    public String getItemsInRoom() {
        String items = "";
        for (Item item: itemsInRoom) {
            items += item.getName();
        }
        return items;
    }

    public void addItemsInRoom(Item item) {
        itemsInRoom.add(item);
    }

    public String toString() {
        return String.format("%s: %s", roomName,description);
    }
}
