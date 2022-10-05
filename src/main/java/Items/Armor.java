package Items;

import Items.Equipment;

public class Armor extends Equipment {
    public int armorClass;

    public Armor(String name, String description, double weight, int armorClass) {
        super(name, description, weight);
        this.armorClass = armorClass;
    }

    public int getArmorClass() {
        return armorClass;
    }
}
