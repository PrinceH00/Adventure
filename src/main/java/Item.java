public class Item {
    private String name;
    private String description;

    public Item(){}

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
