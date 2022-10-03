public class Adventure {
    //Private objects of the player og item class
    private final Player player;
    private final Map map;
    private final CombatController combat;

    //Constructor without parameters
    public Adventure() {
        player = new Player(10);
        map = new Map();
        combat = new CombatController(player);
        player.setCurrentRoom(map.getEmptyRoom());
        player.getCurrentRoom().setVisited();
    }

    //Get methode.
    public Player getPlayer() { return player; }
    public Map getMap() { return map; }
    public CombatController getCombat() { return combat; }

    public String movePlayer(String direction) { return player.move(direction); }
    public boolean isDark() { return player.isDark(); }
    public String playerTurnLight(String state) { return player.turnLight(state); }
    public String dropItem(String itemToDrop) { return player.dropItem(itemToDrop); }
    public String playerEat(String itemToEat) { return player.eatFood((itemToEat)); }
    public String  playerLook() { return player.look(); }
    public String equipWeapon(String itemToEquip) { return player.equipItem(itemToEquip); }
    public String stashWeapon(Class weapon) { return player.stashItem(weapon); }
    public String stats() { return player.playerStats(); }
    public String attack() { return combat.attack(); }
    public String takeItem(String itemToTake) { return player.takeItem(itemToTake); }
    public String getInventory() { return player.inventoryToString(); }

    public Room currentRoom() { return player.getCurrentRoom(); }
}
