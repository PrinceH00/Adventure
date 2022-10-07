package Items;

public class LightWeapon extends MeleeWeapon {
    public LightWeapon(String name, String description, double weight, int damage) {
        super(name, description, weight, damage);
        dualWieldAble = true;
    }
}
