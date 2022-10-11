package characters;

import items.Item;

import java.util.ArrayList;
import room.Room;

public class Enemy extends Character {
    private final String name;
    private final int DAMAGE;
    private final boolean AGGRESSIVE;


    public Enemy(String name, int MAX_HEALTH, int DAMAGE, Room currentRoom, boolean AGGRESSIVE) {
        super(MAX_HEALTH, currentRoom);
        this.name = name;
        this.DAMAGE = DAMAGE;
        currentRoom.addEnemy(this);
        this.AGGRESSIVE = AGGRESSIVE;
    }
    public void setHealth(int health) { this.health = health; }
    public int getHealth() { return health; }
    public int getDAMAGE() { return DAMAGE; }
    public String getName() { return name; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }
    public ArrayList<Item> getLoot() { return inventory; }
    public void addLoot(Item itemToAdd) { inventory.add(itemToAdd); }
    public boolean getAGGRESSIVE() { return AGGRESSIVE; }

    public boolean isAlive() {
        if (health > 0) {
            return true;
        } else {
            for (Item item: inventory) {
                currentRoom.addItem(item);
            }
            return false;
        }
    }
}
