package Items;

public class MeleeWeapon extends Weapon {
    public MeleeWeapon(String name, String description, double weight, int damage) {
        super(name, description, weight, damage);
    }

    @Override
    public int remainingUses() {
        return 9999;
    }

    @Override
    public void used() {}
}
