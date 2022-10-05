package Items;

import Items.Equipment;

public class Weapon extends Equipment {
    private final int damage;

    public Weapon(String name, String description, double weight, int damage) {
        super(name, description, weight);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
