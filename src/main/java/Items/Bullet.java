package Items;

public class Bullet extends Ammunition {
    public Bullet(String name, String description, double weight, int amount) {
        super(name, description, weight, amount);
        this.weaponType = Gun.class;
    }
}
