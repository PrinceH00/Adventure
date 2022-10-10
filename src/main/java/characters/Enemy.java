package characters;

import items.Item;

import java.util.ArrayList;
import room.Room;

public class Enemy extends Character {
    private String name;
    private int MAX_HEALTH;
    private int health;
    private int damage;

    public Enemy(String name, int MAX_HEALTH, int damage, Room currentRoom) {
        super(MAX_HEALTH, currentRoom);
        this.name = name;
        this.health = MAX_HEALTH;
        this.MAX_HEALTH = MAX_HEALTH;
        this.damage = damage;
        currentRoom.addEnemy(this);
    }
    public void setHealth(int health) { this.health = health; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public String getName() { return name; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }
    public ArrayList<Item> getLoot() { return inventory; }
    public void addLoot(Item itemToAdd) { inventory.add(itemToAdd); }
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
