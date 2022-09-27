public class Player {
    private int health;
    private Room currentRoom;

    public Player(int health) {
        this.health = health;
    }

    public void setCurrentRoom(Room selectedRoom) {
        currentRoom = selectedRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public boolean moveNorth() {
        if (currentRoom.getNorth() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        if (currentRoom.getSouth() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moveWest() {
        if (currentRoom.getWest() != null) {
            return true;
        } else {
            return false;
        }
    }

}
