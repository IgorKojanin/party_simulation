///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Avatar.java
// Description: Abstract class with basic functions for all the people
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;
import java.util.HashMap;
import java.util.Random;

import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enums.Color;


public abstract class Avatar {
	
	private Shape shape;
	private Color color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private int drinksConsumed;
	private HashMap<String, Integer> placePriorities = new HashMap<String, Integer>();
	private HashMap<String[], String[]> questionsAnswersList = new HashMap<String[], String[]>();
	private String[] hobbiesList = new String[3];
	
	
	// ************** Main constructor for PartyGoer **************
	public Avatar(Shape shape, Color color, int borderWidth, int avatarId, int avatarAge, 
			int drinksConsumed, HashMap<String, Integer> placePriorities, 
			HashMap<String[], String[]> questionsAnswersList) {
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = avatarId;
		this.avatarAge = avatarAge;
		this.drinksConsumed = drinksConsumed;
		this.placePriorities = placePriorities;
		this.questionsAnswersList = questionsAnswersList;
	}
	// ************** Constructor for workers (DJ, bouncer & bartender) **************
	public Avatar(Shape shape, Color color, int borderWidth, int avatarId) { 
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = avatarId;
	}	
	
	// ************** get functions **************
	public Shape getShape() {
		return this.shape;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getBorderWidth() {
		return this.borderWidth;
	}
	
	public int getId() {
		return this.avatarId;
	}
	
	public int getAge() {
		return this.avatarAge;
	}
	
	public int getDrinksConsumed() {
		return this.drinksConsumed;
	}
	
	public HashMap<String, Integer> getPlacePriorities() {
		return this.placePriorities;
	}

	public HashMap<String[], String[]> getQuestionsAnswersList() {
		return this.questionsAnswersList;
	}
	
	// ************** move function **************
	public Direction moveAvatar() {
		Random rand = new Random();
		int number = rand.nextInt(4);
		// direction is set externally --> check with the simulation environment
		Direction dir = Direction.FORWARD;
		if (number == 0) {
			dir = Direction.FORWARD;
		}
		else if (number == 1) {
			dir = Direction.RIGHT;
		}
		else if (number == 2) {
			dir = Direction.BACK;
		}
		else if (number == 3) {
			dir = Direction.LEFT;
		}
		return dir;
	} 
	
}
