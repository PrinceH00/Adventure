public class Adventure {
    //Private objects of the player og item class
    private Player player;
    private Map map;

    //Constructor without parameters
    public Adventure() {
        player = new Player(10);
        map = new Map();
        player.setCurrentRoom(map.getEmptyRoom());
        player.getCurrentRoom().setVisited(true);
    }

    //Get methode.
    public Player getPlayer() {
        return player;
    }

    public Map getMap() { return map; }
}
