package items.Equipment;

public class Spell_Book extends Weapon {
    private int manaCost;

    public Spell_Book(String name, String description, double weight, int damage, int manaCost) {
        super(name, description, weight, damage);
        dualWieldAble = true;
        this.manaCost = manaCost;
    }

    @Override
    public int remainingUses() { return 99; }

    @Override
    public void used() {}

    @Override
    public void addRemainingUses(int ammo) {}

    @Override
    public void setCanUse() {}

    public int getManaCost() { return manaCost; }
}
