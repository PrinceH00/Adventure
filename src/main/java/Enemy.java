import java.util.ArrayList;

public class Enemy {
    private String name;
    private int MAX_HEALTH;
    private int health;
    private Room currentRoom;
    private ArrayList<Item> loot;

    public Enemy(String name, int health, int MAX_HEALTH, Room currentRoom) {
        this.name = name;
        this.health = health;
        this.MAX_HEALTH = MAX_HEALTH;
        this.currentRoom = currentRoom;
    }

    //TODO make attack action.
    public void attack(Player player) {
    }
}
