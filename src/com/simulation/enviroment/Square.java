package com.simulation.enviroment;

import java.awt.*;

public class Square extends java.awt.Rectangle {
	private Color color;
	private String area;

 

    public Square(int x, int y, int width, int height, Color color, String area) {
        super(x, y, width, height);
        this.color = color;
        this.area = area;
    }

 

    public Color getColor() {
        return color;
    }

 

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public String getArea() {
        return area;
    }
}