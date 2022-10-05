import Items.Item;

public class Map {
    private Room emptyRoom;
    private Room redForest;
    private Room lab;
    private Room kitchen;
    private Room bedRoom;
    private Room cry;
    private Room happyHand;
    private Room secretPassage;
    private Room teleporter;


    public Map() {
        createMap();
    }

    //Methode creating and handling the map.
    public void createMap() {
        //Creating objects of the Class Room with parameters.
        emptyRoom = new Room("The Empty room", "You find yourself in an empty room..", true);
        redForest = new Room("Red Forest", "Vegetation that should smell plants, and a bad smell of iron.", false);
        lab = new Room("Lab", "Blood? and is that parts of a ...., guess someone is experimenting here", false);
        kitchen = new Room("Kitchen", "seems normal enough, except its not food that is being prepared here <3 ", true);
        bedRoom = new Room("Bedroom", "comfy room i may even take a nap", false);
        cry = new Room("Cry", "There is a Baby crying blood and staring at you when u walk around", false);
        happyHand = new Room("Happy hand", "Relieving hand sticking out of the wall for all your business", false);
        secretPassage = new Room("Secret passage", "What a strange passage. Where does it lead?", false);
        teleporter = new Room("Hidden teleporter", "What is this wired code, and is that a Red Button!!!", false);

        //Creating and adding items to rooms.
        emptyRoom.addItem(new Item("Fleshlight", "Can satisfy... something.", 2));
        kitchen.addItem(new Item("foot", "Someones foot may come in handy", 4));
        redForest.addItem(new Item("red leaves", "Mashed red leaves can be smoked", 0.5));


        //creating and adding enemy to rooms.
        emptyRoom.setEnemy(new Enemy("Ogre", 24, 3));

        //Adding exits to the Rooms.
        createRoomLink(emptyRoom, redForest, "east");
        createRoomLink(emptyRoom, kitchen, "south");
        createRoomLink(redForest, lab, "east");
        createRoomLink(lab, cry, "south");
        createRoomLink(kitchen, happyHand, "south");
        createRoomLink(bedRoom, secretPassage, "south");
        createRoomLink(cry, teleporter, "south");
        createRoomLink(happyHand, secretPassage, "east");
        createRoomLink(secretPassage, teleporter, "east");
    }

    //Connecting two Rooms to each other with connecting exits.
    public void createRoomLink(Room firstRoom, Room secondRoom, String cardinalDirection) {
        switch (cardinalDirection.toLowerCase()) {
            case "north":
                firstRoom.setNorth(secondRoom);
                break;
            case "east":
                firstRoom.setEast(secondRoom);
                break;
            case "south":
                firstRoom.setSouth(secondRoom);
                break;
            case "west":
                firstRoom.setWest(secondRoom);
                break;
        }
    }

    public Room getBedRoom() {
        return bedRoom;
    }

    public Room getEmptyRoom() {
        return emptyRoom;
    }

    public Room getCry() {
        return cry;
    }
}
