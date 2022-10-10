package Items.Equipment;

import Items.Equipment.RangedWeapon;

public class Gun extends RangedWeapon {
    public Gun(String name, String description, double weight, int damage, int MAXIMUM_USES) {
        super(name, description, weight, damage, MAXIMUM_USES);
        dualWieldAble = true;
    }
}
