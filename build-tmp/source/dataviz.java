import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Map; 
import java.util.Arrays; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class dataviz extends PApplet {





String[] lines;
int index = 0;
ArrayList<Nutrient> nutrients;
String[] years;
float zoomFactor = 300;
boolean saveOneFrame = true;

public void setup() {
    size(800, 800);

    stroke(255);
    //frameRate(12);
    csvInit();
}

public void csvInit() {
    nutrients = new ArrayList<Nutrient>();
    String[] lines = loadStrings("recipes2.csv");
    println(lines[0]);
    //years = split(lines[0], ';');
    //years = Arrays.copyOfRange(years, 2, years.length);
    //lines = Arrays.copyOfRange(lines, 2, lines.length);
    for(String line : lines) {
        String[] pieces = split(line, ';');
        Nutrient nutrient = new Nutrient(pieces[0], pieces[1], PApplet.parseFloat(pieces[2]), PApplet.parseFloat(pieces[3]));
        nutrient.add("Vegan Christmas Cookies", PApplet.parseFloat(pieces[4]));
        nutrient.add("Regular Christmas Cookies", PApplet.parseFloat(pieces[5]));

        nutrients.add(nutrient);
    }
}

public void drawMask(float x, float y) {
    int length = nutrients.size();
    float index = 0;

    for(Nutrient nutrient : nutrients) {
        float tx = x + (cos(-PI*0.4f + (PI*2) * (index / length)) * zoomFactor);
        float ty = y + (sin(-PI*0.4f + (PI*2) * (index / length)) * zoomFactor);

        fill(0);
        line(x, y, tx, ty);
        text(nutrient.name, tx, ty);

        index++;
    }
}

public void drawStar(float x, float y, String recipe) {
    int index = 0;
    int length = nutrients.size();
    float[] pointsx = new float[length];
    float[] pointsy = new float[length];

    for(Nutrient nutrient : nutrients) {

        float value = nutrient.values.get(recipe);
        float transValue = map(value, nutrient.min, nutrient.max, 0, zoomFactor);

        float tx = x + (cos(-PI*0.4f + (PI*2) * (PApplet.parseFloat(index) / 7)) * transValue);
        float ty = y + (sin(-PI*0.4f + (PI*2) * (PApplet.parseFloat(index) / 7)) * transValue);

        pointsx[index] = tx;
        pointsy[index] = ty;

        index++;
    }

    for( int i = 0; i < length; i++) {
        line(pointsx[i], pointsy[i], pointsx[(i+1) % length], pointsy[(i+1) % length]);
    }
}

public void mousePressed() {
  saveOneFrame = true;
}

public void draw() {
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

    public void add(String year, float value) {
        values.put(year, value);
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "dataviz" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
