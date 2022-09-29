public class Adventure {
    //Private objects of the player og item class
    private Player player;
    private Item item;

    //Constructor without parameters
    public Adventure() {
        player = new Player(10);
        item = new Item();
    }

    //Get methode.
    public Player getPlayer() {
        return player;
    }

    //Connecting two Rooms to each other with connecting exits.
    public void createRoomLink(Room firstRoom, Room secondRoom, String cardinalDirection) {
        switch (cardinalDirection.toLowerCase()) {
            case "north": firstRoom.setNorth(secondRoom);
            case "east": firstRoom.setEast(secondRoom);
            case "south": firstRoom.setSouth(secondRoom);
            case "west": firstRoom.setWest(secondRoom);
        }
    }
}
