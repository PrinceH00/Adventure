package items.Equipment;

public class Armor extends Equipment {
    public int armorClass;

    public Armor(String name, String description, double weight, int armorClass) {
        super(name, description, weight);
        this.armorClass = armorClass;
    }

    public int getArmorClass() {
        return armorClass;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s armor", name, description, armorClass);
    }
}
