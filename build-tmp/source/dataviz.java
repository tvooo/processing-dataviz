import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Map; 
import java.util.Arrays; 

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

public void setup() {
    size(800, 800);
    background(0);
    stroke(255);
    //frameRate(12);
    csvInit();
}

public void csvInit() {
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
            nutrient.add(years[i], PApplet.parseFloat(pieces[i]));
        }
        nutrients.add(nutrient);
    }
}

public void draw() {
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
class Nutrient {
    String name;
    String unit;
    Map<String, Float> values;

    Nutrient(String name, String unit) {
        this.name = name;
        this.unit = unit;
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