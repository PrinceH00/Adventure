public class Armor extends Item {
    private int armorRating;

    public Armor(String name, String description, double weight, int armorRating) {
        super(name, description, weight);
    }

    public int getArmorRating() {
        return armorRating;
    }
}
