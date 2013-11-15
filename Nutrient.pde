class Nutrient {
    String name;
    String unit;
    Map<String, Float> values;

    Nutrient(String name, String unit) {
        this.name = name;
        this.unit = unit;
        values = new HashMap<String, Float>();
    }

    void add(String year, float value) {
        values.put(year, value);
    }
}
