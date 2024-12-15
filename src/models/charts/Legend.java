package models.charts;

import java.awt.Color;

public class Legend {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Legend(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Legend() {
    }

    private String name;
    private Color color;
}
