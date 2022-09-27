public class Adventure {
    //Private objeter af klassen player og item
    private Player player;
    private Item item;

    //konstroktør uden parapenrete
    public Adventure() {
        player = new Player(10);
        item = new Item();
    }

    //Metode som opretter og håndtere kortet(Spillebrædtet)
    public void createMap(){
        // TODO: 27/09/2022 Ret følgende rum
        //Optetter objekter af klassen Room og tildeler parametrerne for kontroktøreren
        Room emptyRoom = new Room("The Empty room", "The room is dark with no lights in sight, but u are not alone.\nTry looking around");
        Room redForest = new Room("Red Forest", "Vegitations that should green, and a bad smell of iron.");
        Room room3 = new Room("rum 3", "rummet er rum 3");
        Room room4 = new Room("rum 4", "rummet er rum 4");
        Room room5 = new Room("rum 5", "rummet er rum 5");
        Room room6 = new Room("rum 6", "rummet er rum 6");
        Room room7 = new Room("rum 7", "rummet er rum 7");
        Room room8 = new Room("rum 8", "rummet er rum 8");
        Room room9 = new Room("rum 9", "rummet er rum 9");

        //Tilføjer udgange til de forskællige rum.
        setExit(emptyRoom, null, redForest, room4, null);
        setExit(redForest, null, room3, null, emptyRoom);
        setExit(room3, null, null, room6, redForest);
        setExit(room4, emptyRoom, null, room7, null);
        setExit(room5, null, null, room8, null);
        setExit(room6, room3, null, room9, null);
        setExit(room7, room4, room8, null, null);
        setExit(room8, room5, room9, null, room7);
        setExit(room9, room6, null, null, room8);

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
}
