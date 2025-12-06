package com.mycompany.app.ui;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PinColours {
    Map<String, Color> pinColours = new HashMap<>();

    public PinColours() {
        pinColours.put("Acorn", Color.rgb(255, 255, 0));
        pinColours.put("Crabapple", Color.rgb(255, 155, 155));
        pinColours.put("RussianOlive", Color.rgb(75, 150, 0));
        pinColours.put("Cherry", Color.rgb(255, 0, 0));
        pinColours.put("Chokecherry", Color.rgb(255, 75, 125));
        pinColours.put("Hawthorn", Color.rgb(0, 255, 255));
        pinColours.put("Apple", Color.rgb(0, 255, 0));
        pinColours.put("Pear", Color.rgb(255, 0, 255));
        pinColours.put("Plum", Color.rgb(155, 55, 255));
        pinColours.put("Hackberry", Color.rgb(150, 0, 0));
        pinColours.put("Coffeetreepod", Color.rgb(110, 110, 10));
        pinColours.put("Caraganaflower/pod", Color.rgb(255, 155, 255));
        pinColours.put("Butternut", Color.rgb(25, 75, 255));
        pinColours.put("Juniper", Color.rgb(125, 25, 200));
        pinColours.put("Saskatoon", Color.rgb(75, 10, 110));
        pinColours.put("Walnut", Color.rgb(110, 55, 0));
    }

    public Color getColour(String fruit) {
        return pinColours.get(fruit);
    }

    public Map<String, Color> getPinColours() {
        return pinColours;
    }
}
