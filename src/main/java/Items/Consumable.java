package Items;

import Items.Item;

public class Consumable extends Item {
    protected final int healthRecovery;

    public Consumable(String name, String description, double weight, int healthRecovery) {
        super(name, description, weight);
        this.healthRecovery = healthRecovery;
    }

    public int getHealthRecovery() {
        return healthRecovery;
    }
}
