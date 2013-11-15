import java.util.Map;
import java.util.Arrays;

String[] lines;
int index = 0;
ArrayList<Nutrient> nutrients;
String[] years;

void setup() {
    size(800, 800);
    background(0);
    stroke(255);
    //frameRate(12);
    csvInit();
}

void csvInit() {
    nutrients = new ArrayList<Nutrient>();
    String[] lines = loadStrings("nutrition.csv");
    println(lines[0]);
    years = split(lines[0], ';');
    //years = Arrays.copyOfRange(years, 2, years.length);
    lines = Arrays.copyOfRange(lines, 2, lines.length);
    for(String line : lines) {
        String[] pieces = split(line, ';');
        Nutrient nutrient = new Nutrient(pieces[0], pieces[1]);
        for(int i = 2; i < pieces.length; i++) {
            nutrient.add(years[i], float(pieces[i]));
        }
        nutrients.add(nutrient);
    }
}

void draw() {
    int index = 1;
    for(Nutrient n : nutrients) {

        float value = n.values.get(years[2]);
        float value2 = n.values.get(years[3]);
        float value3 = n.values.get(years[4]);
        println(value);
        stroke(200);
        text(n.name, 10, index*30);

        stroke(200, 100, 100);
        line(10, index * 30 + 10, 10 + value, index * 30 + 10);
        stroke(100, 200, 100);
        line(10, index * 30 + 12, 10 + value2, index * 30 + 12);
        stroke(100, 100, 200);
        line(10, index * 30 + 14, 10 + value3, index * 30 + 14);
        index++;
    }
}
