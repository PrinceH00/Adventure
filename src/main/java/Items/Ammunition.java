package Items;

import org.w3c.dom.ranges.Range;

public class Ammunition extends Item {
    protected int amount;
    protected Class<? extends Items.RangedWeapon> weaponType;

    public Ammunition(String name, String description, double weight, int amount) {
        super(name, description, weight);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Class<? extends Items.RangedWeapon> getWeaponType() {
        return weaponType;
    }
}
