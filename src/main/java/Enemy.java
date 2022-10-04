import java.util.ArrayList;

public class Enemy {
    private String name;
    private int MAX_HEALTH;
    private double health;
    private ArrayList<Item> loot;
    private int damage;

    public Enemy(String name, int MAX_HEALTH, int damage) {
        this.name = name;
        this.health = MAX_HEALTH;
        this.MAX_HEALTH = MAX_HEALTH;
        this.damage = damage;
    }

    public void setHealth(double health) { this.health = health; }
    public double getHealth() { return health; }
    public int getDamage() { return damage; }
    public String getName() { return name; }
    public int getMAX_HEALTH() { return MAX_HEALTH; }

    public ArrayList<Item> getLoot() { return loot; }
    public void addLoot(Item itemToAdd) { loot.add(itemToAdd); }
}
