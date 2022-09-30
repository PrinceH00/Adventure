public class Food extends Item {
    private final int healthRecovery;

    public Food(String name, String description, double weight, int healthRecovery) {
        super(name, description, weight);
        this.healthRecovery = healthRecovery;
    }

    public int getHealthRecovery() {
        return healthRecovery;
    }
}
