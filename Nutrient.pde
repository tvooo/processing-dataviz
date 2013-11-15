class Nutrient {
    String name;
    String unit;
    float min, max;
    Map<String, Float> values;

    Nutrient(String name, String unit, float min, float max) {
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
        values = new HashMap<String, Float>();
    }

    void add(String year, float value) {
        values.put(year, value);
    }
}
