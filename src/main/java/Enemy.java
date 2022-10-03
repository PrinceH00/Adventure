import java.util.ArrayList;

public class Enemy {
    private String name;
    private int MAX_HEALTH;
    private int health;
    private ArrayList<Item> loot;
    private int damage;

    public Enemy(String name, int health, int MAX_HEALTH, int damage) {
        this.name = name;
        this.health = health;
        this.MAX_HEALTH = MAX_HEALTH;
        this.damage = damage;
    }

    public void setHealth(int health) { this.health = health; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public String getName() { return name; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }

    public ArrayList<Item> getLoot() { return loot; }
    public void addLoot(Item itemToAdd) {
        loot.add(itemToAdd);
    }
}
