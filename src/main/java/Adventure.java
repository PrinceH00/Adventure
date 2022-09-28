public class Adventure {
    //Private objeter af klassen player og item
    private Player player;
    private Item item;

    //Constructor without parameters
    public Adventure() {
        player = new Player(10);
        item = new Item();
    }

    //Metode som opretter og håndtere kortet(Spillebrædtet)
    public void createMap(){
        //Creating objects of the Class Room with parameters.
        Room emptyRoom = new Room("The Empty room", "The room is dark with no lights in sight, but u are not alone.", true);
        Room redForest = new Room("Red Forest", "vegetation that should green, and a bad smell of iron.", false);
        Room lab = new Room("Lab", "Blood? and is that parts of a ...., guess someone is experimenting here", false);
        Room kitchen = new Room("kitchen", "seems normal enough, except its not food that is being prepared here <3 ", true);
        Room bedroom = new Room("Bedroom", "comfy room i may even take a nap", false);
        Room cry = new Room("Cry", "There is a Baby crying blood and staring at you when u walk around", false);
        Room happyHand = new Room("Happy hand", "Relieving hand sticking out of the wall for all your business", false);
        Room secretPassage = new Room("Secret passage", "What a strange passage. Where does it lead?", false);
        Room teleporter = new Room("Hidden teleporter", "What is this wired code, and is that a Red Button!!!", false);

        //Adding exits to the Rooms.
        createRoomLink(emptyRoom, redForest, "east");
        createRoomLink(emptyRoom, kitchen, "south");
        createRoomLink(redForest, lab, "east");
        createRoomLink(lab, cry, "south");
        createRoomLink(kitchen, happyHand, "south");
        createRoomLink(bedroom, secretPassage, "south");
        createRoomLink(cry, teleporter, "south");
        createRoomLink(happyHand, secretPassage, "east");
        createRoomLink(secretPassage, teleporter, "east");

        //Sætter spillerens start rum til EmptyRoom
        player.setCurrentRoom(emptyRoom);
    }

    //Metode som tag E,N,S,Ø,W som parametre med datatypen Room
    public void setExit(Room exit, Room north, Room east, Room south, Room west){
        exit.setNorth(north);
        exit.setEast(east);
        exit.setSouth(south);
        exit.setWest(west);
    }

    //Get metode.
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
