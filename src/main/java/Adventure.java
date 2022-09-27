public class Adventure {
    private Player player;

    public Adventure() {
        player = new Player(10);
        Room EmptyRoom = new Room("The Empty room", "The room is dark with no lights in sight, but u are not alone.\nTry looking around");
        Room room2 = new Room("rum 2", "rummet er rum 2");
        Room room3 = new Room("rum 3", "rummet er rum 3");
        Room room4 = new Room("rum 4", "rummet er rum 4");
        Room room5 = new Room("rum 5", "rummet er rum 5");
        Room room6 = new Room("rum 6", "rummet er rum 6");
        Room room7 = new Room("rum 7", "rummet er rum 7");
        Room room8 = new Room("rum 8", "rummet er rum 8");
        Room room9 = new Room("rum 9", "rummet er rum 9");

        createMap(EmptyRoom, null, room2, room4, null);
        createMap(room2, null, room3, null, EmptyRoom);
        createMap(room3, null, null, room6, room2);
        createMap(room4, EmptyRoom, null, room7, null);
        createMap(room5, null, null, room8, null);
        createMap(room6, room3, null, room9, null);
        createMap(room7, room4, room8, null, null);
        createMap(room8, room5, room9, null, room7);
        createMap(room9, room6, null, null, room8);
        player.setCurrentRoom(EmptyRoom);
    }

    public void createMap(Room room, Room north, Room east, Room south, Room west) {
        room.setNorth(north);
        room.setEast(east);
        room.setSouth(south);
        room.setWest(west);
    }

    public Player getPlayer() {
        return player;
    }
}
