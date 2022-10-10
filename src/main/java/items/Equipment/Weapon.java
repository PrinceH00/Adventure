package items.Equipment;

public abstract class Weapon extends Equipment {
    protected final int damage;
    protected boolean canUse;
    protected boolean dualWieldAble;

    public Weapon(String name, String description, double weight, int damage) {
        super(name, description, weight);
        this.damage = damage;
        canUse = true;
    }

    public int getDamage() {
        return damage;
    }

    public abstract int remainingUses();
    public abstract void used();
    public abstract void addRemainingUses(int ammo);

    public abstract void setCanUse();


    public boolean getCanUse() {
        return canUse;
    }

    public boolean getDualWieldAble() {
        return dualWieldAble;
    }


    @Override
    public String toString() {
        return String.format("%s: %s, %s damage", name, description, damage);
    }
}
