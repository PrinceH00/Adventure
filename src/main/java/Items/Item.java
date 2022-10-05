package Items;

public class Item {
    private String name;
    private String description;
    private double weight;

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