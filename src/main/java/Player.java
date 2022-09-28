public class Player {
    private int health;
    private Room currentRoom;

    public Player(){}

    //Constructor with lives as parameters.
    public Player(int health) {
        this.health = health;
    }

    //Get metoder.
    public Room getCurrentRoom() { return currentRoom; }
    public int getHealth() { return health; }

    //Sæt metoder.
    public void setCurrentRoom(Room selectedRoom) { currentRoom = selectedRoom; }
    public void setHealth(int health) { this.health = health; }


    //boolean som bevæger spilleren N,S,Ø,W hvis den er forskællig fra null
    public boolean moveNorth() {
        if (currentRoom.getNorth() != null) {
            return true;
        } else
            return false;
    }

    public boolean moveEast() {
        if (currentRoom.getEast() != null) {
            return true;
        } else
            return false;
    }

    public boolean moveSouth() {
        if (currentRoom.getSouth() != null) {
            return true;
        } else
            return false;
    }

    public boolean moveWest() {
        if (currentRoom.getWest() != null) {
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
