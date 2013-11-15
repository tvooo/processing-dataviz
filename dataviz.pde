import java.util.Map;
import java.util.Arrays;
import processing.pdf.*;

String[] lines;
int index = 0;
ArrayList<Nutrient> nutrients;
String[] years;
float zoomFactor = 300;
boolean saveOneFrame = true;

void setup() {
    size(800, 800);

    stroke(255);
    //frameRate(12);
    csvInit();
}

void csvInit() {
    nutrients = new ArrayList<Nutrient>();
    String[] lines = loadStrings("recipes2.csv");
    println(lines[0]);
    //years = split(lines[0], ';');
    //years = Arrays.copyOfRange(years, 2, years.length);
    //lines = Arrays.copyOfRange(lines, 2, lines.length);
    for(String line : lines) {
        String[] pieces = split(line, ';');
        Nutrient nutrient = new Nutrient(pieces[0], pieces[1], float(pieces[2]), float(pieces[3]));
        nutrient.add("Vegan Christmas Cookies", float(pieces[4]));
        nutrient.add("Regular Christmas Cookies", float(pieces[5]));

        nutrients.add(nutrient);
    }
}

void drawMask(float x, float y) {
    int length = nutrients.size();
    float index = 0;

    for(Nutrient nutrient : nutrients) {
        float tx = x + (cos(-PI*0.4 + (PI*2) * (index / length)) * zoomFactor);
        float ty = y + (sin(-PI*0.4 + (PI*2) * (index / length)) * zoomFactor);

        fill(0);
        line(x, y, tx, ty);
        text(nutrient.name, tx, ty);

        index++;
    }
}

void drawStar(float x, float y, String recipe) {
    int index = 0;
    int length = nutrients.size();
    float[] pointsx = new float[length];
    float[] pointsy = new float[length];

    for(Nutrient nutrient : nutrients) {

        float value = nutrient.values.get(recipe);
        float transValue = map(value, nutrient.min, nutrient.max, 0, zoomFactor);

        float tx = x + (cos(-PI*0.4 + (PI*2) * (float(index) / 7)) * transValue);
        float ty = y + (sin(-PI*0.4 + (PI*2) * (float(index) / 7)) * transValue);

        pointsx[index] = tx;
        pointsy[index] = ty;

        index++;
    }

    for( int i = 0; i < length; i++) {
        line(pointsx[i], pointsy[i], pointsx[(i+1) % length], pointsy[(i+1) % length]);
    }
}

void mousePressed() {
  saveOneFrame = true;
}

void draw() {
    if(saveOneFrame == true) {
        beginRecord(PDF, "Line.pdf");
    }

    background(255);

    stroke(200, 100, 100);
    drawMask(400, 400);
    stroke(100, 200, 100);
    drawStar(400, 400, "Vegan Christmas Cookies");
    stroke(100, 100, 200);
    drawStar(400, 400, "Regular Christmas Cookies");

    if(saveOneFrame == true) {
        endRecord();
        saveOneFrame = false;
    }
}
