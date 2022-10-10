package items;


import items.Equipment.Bow;

public class Arrow extends Ammunition {
    public Arrow(String name, String description, double weight, int amount) {
        super(name, description, weight, amount);
        this.weaponType = Bow.class;
    }
}
