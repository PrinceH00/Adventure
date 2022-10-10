package items;

public class Item {
    protected String name;
    protected String description;
    protected double weight;

    public Item(String name, String description, double weight) {
            this.name = name;
            this.description = description;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }
        public double getWeight() { return weight; }

        public String toString() {
            return String.format("%s: %s", name, description);
        }
    }