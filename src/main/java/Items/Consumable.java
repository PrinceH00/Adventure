package Items;

import Items.Item;

public class Consumable extends Item {
    private final int healthRecovery;

    public Consumable(String name, String description, double weight, int healthRecovery) {
        super(name, description, weight);
        this.healthRecovery = healthRecovery;
    }

    public int getHealthRecovery() {
        return healthRecovery;
    }
}
