public class Item {
    private String name;
    private String description;
    private double weight;
    private boolean useAble;

    public Item(String name, String description, boolean useAble) {
        this.name = name;
        this.description = description;
        this.useAble = useAble;
    }

    public String getName() {
        return name;
    }
    public boolean getUseAble() { return useAble;}
    public double getWeight() { return weight; }

    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
