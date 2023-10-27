package com.simulation.enviroment;

import java.awt.*;

import com.simulation.enums.Places;

public class Square extends java.awt.Rectangle {
	private Color color;
	private Places place;

    public Square(int x, int y, int width, int height, Color color, Places place) {
        super(x, y, width, height);
        this.color = color;
        this.place = place;
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

    public Places getPlace() {
        return place;
    }

    // This is a test comment!
}