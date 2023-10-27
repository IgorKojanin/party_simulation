package com.simulation.enviroment;

import java.awt.*;

public class Square extends java.awt.Rectangle {
	private Color color;
	private String area; 
	private Boolean isUsable;

 

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
    
    public Boolean getIsUsable(int x, int y) {
    	if(isUsable == true) {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
    
    public void setIsUsable(Color color) { //squares which are red, must not be used
    	if(color == Color.red ) {
    		this.isUsable = false;
    	}
    	
    }
    
}