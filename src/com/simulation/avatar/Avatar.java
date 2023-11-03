///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Avatar.java
// Description: Abstract class with basic functions for all the people

/* @param shape            The shape of the avatar.
 * @param color            The color of the avatar.
 * @param borderWidth      The border width of the avatar.
 * @param avatarId         The unique ID of the avatar.
 * @param avatarAge        The age of the avatar.
 * @param drinksConsumed   The number of drinks consumed by the avatar.
 * @param placePriorities  The priorities of places for the avatar.
 * @param questionsAnswersList  The list of questions and answers for the avatar.
 */
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;
import java.util.HashMap;
import java.util.Random;

import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enums.Colors;


public abstract class Avatar {
	
	private Shape shape;
	private Colors color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private int drinksConsumed;
	private HashMap<String, Integer> placePriorities = new HashMap<String, Integer>();
	private HashMap<String[], String[]> questionsAnswersList = new HashMap<String[], String[]>();
	private String[] hobbiesList = new String[3];
	
	
	// ************** Main constructor for PartyGoer **************
	public Avatar(Shape shape, Colors color, int borderWidth, int avatarId, int avatarAge, 
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
	public Avatar(Shape shape, Colors color, int borderWidth, int avatarId) { 
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = avatarId;
	}	
	
	// ************** get functions **************
	public Shape getShape() {
		return this.shape;
	}
	
	public Colors getColor() {
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
