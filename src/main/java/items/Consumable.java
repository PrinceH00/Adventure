package items;

public class Consumable extends Item {
    protected final int recovery;

    public Consumable(String name, String description, double weight, int healthRecovery) {
        super(name, description, weight);
        this.recovery = healthRecovery;
    }

    public int getRecovery() {
        return recovery;
    }
}
