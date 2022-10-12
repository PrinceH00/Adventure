package room;

import characters.Enemy;
import items.Item;

import java.util.ArrayList;

public class Room {
    //Private Objects
    private ArrayList<Item> itemsInRoom;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private ArrayList<Enemy> enemies;
    private Room lastRoom;

    //Private attributes.
    private final String roomName;
    private String description;
    private boolean visited;
    private boolean canBeDark;
    private boolean dark;
    private boolean hasEnemy;

    //Constructor with 2 parameters
    public Room(String roomName, String description, boolean canBeDark) {
        this.roomName = roomName;
        this.description = description;
        this.canBeDark = canBeDark;
        dark = canBeDark;
        itemsInRoom = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
    }

    //Get methods
    public Room getNorth() { return north; }
    public Room getEast() { return east; }
    public Room getSouth() { return south; }
    public Room getWest() { return west; }
    public Room getLastRoom() { return lastRoom; }
    public String getDescription() { return description; }
    public String getRoomName() { return roomName; }
    public boolean getDark() { return dark; }
    public boolean getCanBeDark() { return canBeDark; }
    public boolean getVisited() { return visited; }
    public boolean getHasEnemy() {
        if (!enemies.isEmpty()) {
            return hasEnemy;
        } else {
            return hasEnemy = false;
        }
    }

    public ArrayList<Enemy> getEnemies() {
        if (enemies != null) {
            return enemies;
        } else {
            return null;
        }
    }

    public void addEnemy(Enemy enemy) {
        hasEnemy = true;
        enemies.add(enemy);
    }

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

    public void setLastRoom(Room lastRoom) { this.lastRoom = lastRoom; }

    public void setVisited() { this.visited = true; }

    public void setDark(boolean dark) {
        if (canBeDark) {
            this.dark = dark;
        }
    }

    public String exits() {
        StringBuilder stringBuilder = new StringBuilder();
        int exits = 0;
        if (north != null && north.getVisited()) {
            exits++;
            stringBuilder.append("North: " + north.getRoomName()).append("\n");
        } else {
            stringBuilder.append("North: null").append("\n");
        }
        if (east != null && east.getVisited()) {
            exits++;
            stringBuilder.append("East: " + east.getRoomName()).append("\n");
        } else {
            stringBuilder.append("East: null").append("\n");
        }
        if (south != null && south.getVisited()) {
            exits++;
            stringBuilder.append("South: " + south.getRoomName()).append("\n");
        } else {
            stringBuilder.append("South: null").append("\n");
        }
        if (west != null && west.getVisited()) {
            exits++;
            stringBuilder.append("West: " + west.getRoomName()).append("\n");
        } else {
            stringBuilder.append("West: null").append("\n");
        }
        stringBuilder.append(String.format("There are %s exits. \n", exits));
        return stringBuilder.toString();
    }

    public void addItem(Item item) {
        itemsInRoom.add(item);
    }

    public void removeItem(Item item) {
        itemsInRoom.remove(item);
    }

    public String listItemsInRoom() {
        return itemsInRoom.toString();
    }

    public ArrayList<Item> getItemsInRoom() { return itemsInRoom; }

    public Item checkItems(String itemName) {
        for (Item item : itemsInRoom) {
            if (item.getName().toLowerCase().contains(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public Enemy checkForEnemy(String enemyName) {
        for (Enemy enemy : enemies) {
            if (enemy.getName().toLowerCase().contains(enemyName)) {
                return enemy;
            }
        }
        return null;
    }

    public String enemiesToString() {
        if (!enemies.isEmpty()) {
            StringBuilder enemiesInRoom = new StringBuilder();
            enemiesInRoom.append("Enemies in the room: ");
            for (Enemy enemy : enemies) {
                if (enemy == enemies.get(0)) {
                    enemiesInRoom.append(String.format("%s",enemy.getName()));
                } else {
                    enemiesInRoom.append(String.format(", %s", enemy.getName()));
                }
            }
            return enemiesInRoom.toString();
        } else {
            return "No enemies are visible.";
        }
    }

    public ArrayList<Enemy> getAgressiveEnemies() {
        ArrayList<Enemy> aggressiveEnemies = new ArrayList<>();
        if (!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                if (enemy.getAGGRESSIVE()) {
                    aggressiveEnemies.add(enemy);
                }
            }
        }
        return aggressiveEnemies;
    }

    public String toString() { return String.format("%s: %s", roomName, description); }
}
