package Items;

import Items.Equipment.RangedWeapon;

public class Ammunition extends Item {
    protected int amount;
    protected Class<? extends RangedWeapon> weaponType;

    public Ammunition(String name, String description, double weight, int amount) {
        super(name, description, weight);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Class<? extends RangedWeapon> getWeaponType() {
        return weaponType;
    }
}
