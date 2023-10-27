/**
 * 
 */
package com.simulation.avatar;

import java.util.HashMap;

import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

/**
 * 
 */
public abstract class Avatar {
	
	private Shape shape;
	private String color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private int drinksConsumed;
	private HashMap<String, Integer> placePriorities = new HashMap<String, Integer>();
	private HashMap<String[], String[]> questionsAnswersList = new HashMap<String[], String[]>();
	
	private String[] hobbiesList = new String[3];
	
	public Avatar(Shape shape, String color, int borderWidth, int avatarId, int avatarAge, 
			int drinksConsumed, HashMap<String, Integer> placePriorities, 
			HashMap<String[], String[]> questionsAnswersList) {
		this.shape = shape;
		this.borderWidth = borderWidth;
		this.shape = shape;
		this.shape = shape;
		this.shape = shape;
		this.shape = shape;
	}
	
	private void moveAvatar(Direction direction) {
		// direction is set externally --> check with the simulation environment 
	} 
	
}
