package Items;


public class Arrow extends Ammunition {
    public Arrow(String name, String description, double weight, int amount) {
        super(name, description, weight, amount);
        this.weaponType = Bow.class;
    }
}
