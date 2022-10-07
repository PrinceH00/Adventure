package Items;

public class HeavyWeapon extends MeleeWeapon {
    public HeavyWeapon(String name, String description, double weight, int damage) {
        super(name, description, weight, damage);
        dualWieldAble = false;
    }
}
