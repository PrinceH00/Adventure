package Items;

public class RangedWeapon extends Weapon {
    private final int MAXIMUM_USES;
    private int remainingUses;

    public RangedWeapon(String name, String description, double weight, int damage, int MAXIMUM_USES) {
        super(name, description, weight, damage);
        this.MAXIMUM_USES = MAXIMUM_USES;
        this.remainingUses = this.MAXIMUM_USES;
    }

    @Override
    public int remainingUses() {
        return remainingUses;
    }

    @Override
    public void used() {
        remainingUses--;
    }

    @Override
    public void addRemainingUses(int ammo) {
        remainingUses += ammo;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s damage, %s ammunition", name, description, damage, remainingUses());
    }
}
