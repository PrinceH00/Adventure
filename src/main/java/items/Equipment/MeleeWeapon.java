package items.Equipment;

public class MeleeWeapon extends Weapon {
    public MeleeWeapon(String name, String description, double weight, int damage) {
        super(name, description, weight, damage);
    }

    @Override
    public int remainingUses() {
        return 99;
    }

    @Override
    public void used() {}

    @Override
    public void addRemainingUses(int ammo) {}

    public void setCanUse() {
        canUse = true;
    }
}
