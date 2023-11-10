package com.simulation.enviroment;

import java.awt.*;

import com.simulation.enums.Places;

public class Square extends java.awt.Rectangle {
    
	private Color color;
    private final Color baseColor;
	private Boolean isUsable;
	private final Places place;

 

    public Square(int x, int y, int width, int height, Color baseColor, boolean isUsable, Places place) {
        super(x, y, width, height);
        this.color = baseColor;
        this.place = place;
        this.isUsable = isUsable;
        this.baseColor = baseColor;
    }

    public Color getColor() {
        return color;
    }

   public Color getBaseColor(){
    return baseColor;
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


    public Boolean getIsUsable() {
    	return isUsable;
    }

    public void setIsUsable(boolean isUsable) { 
        this.isUsable = isUsable;
    }


}