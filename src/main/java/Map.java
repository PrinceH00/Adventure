import characters.Enemy;
import items.Arrow;
import items.Bullet;
import items.Equipment.*;
import items.Food;
import items.Item;
import room.Room;

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

        emptyRoom.addItem(new HeavyWeapon("Battle Axe", "Smacks hard", 5, 8));
        kitchen.addItem(new LightWeapon("Sword", "Slashes through skin and bone", 5, 12));
        lab.addItem(new LightWeapon("Club", "Bashes the with heavy weight", 5, 6));
        redForest.addItem(new LightWeapon("Hatchet", "it is rusty and used, but still sharp to the edge", 5, 9));
        cry.addItem(new Bow("Recurve bow", "Shoots arrows", 5, 15, 5));
        happyHand.addItem(new Armor("Leather Tunic", "Protects a little", 5, 5));
        happyHand.addItem(new Food("Pill", "Glowing pill that whispers something", 0.1, -1000));
        cry.addItem(new Arrow("Arrows", "Sharp flint tips that could pierce skin easily", 2, 10));
        teleporter.addItem(new Gun("P911", "a modern type handgun", 3, 25, 7));
        teleporter.addItem(new Bullet("5mm Bullet", "Saml 5mm bullet for p911", 2, 20));
        cry.addItem(new Shield("Tower Shield", "Big shield covering from head to toe", 5, 10));
        secretPassage.addItem(new Spell_Book("Firebolt",
                "Shoots a firebolt in front of you setting whatever it hits ablaze", 1, 20, 5));


        //creating and adding enemy to rooms and adds loot.
        new Enemy("Ogre", 24, 3, emptyRoom, true).addLoot(new Food("Banana cake", "Delicious cake to eat.", 0.5, 10));
        new Enemy("Mimic", 24, 3, emptyRoom, false);

        new Enemy("Hydra", 24, 10, secretPassage, true);

        new Enemy("Mom", 40, 1, kitchen, false);

        new Enemy("Succubus", 15, 15, bedRoom, true);



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
