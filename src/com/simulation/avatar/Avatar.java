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
 * @param isDancing        State of dancing avatar
 * @param timeOut          The time it should remain outside
 */
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import java.awt.Color;
import java.util.HashMap;

import java.util.Random;

import com.simulation.enums.Direction;
import com.simulation.enums.Shape;


public abstract class Avatar {
	
	private Shape shape;
	private Color color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private int drinksConsumed = 0;
	private boolean isDancing = false;
	private boolean isHit = false;
	private int timeoutTimeRemaining = 0;
	private boolean isInTheParty;
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
		this.isHit = isHit;
		this.timeoutTimeRemaining = timeoutTimeRemaining;
		this.isInTheParty = isInTheParty;
	}
	
	// ************** Constructor for workers (DJ, bouncer & bartender) **************
	public Avatar(Shape shape, Color color, int borderWidth, int avatarId) { 
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = avatarId;
	}

    public Avatar(Shape shape, Color color, int borderWidth, int avatarId, int avatarAge, int drinksConsumed, boolean isHit, int timeoutTimeRemaining, boolean isInTheParty) {
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = avatarId;
		this.avatarAge = avatarAge;
		this.drinksConsumed = drinksConsumed;
		this.isHit = isHit;
		this.timeoutTimeRemaining = timeoutTimeRemaining;
		this.isInTheParty = isInTheParty;
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
	
	public int getTimeoutTimeRemaining() {
		return this.timeoutTimeRemaining;
	}

	public boolean getIsInThePartyState() {
		return this.isInTheParty;
	}
	
	public boolean getDancing() {
		return this.isDancing;
	}

	public boolean getIsHitState() {
		return this.isHit;
	}
	
	// ************** set functions **************
	
	public void setDancing(boolean newDancingState) {
		this.isDancing = newDancingState;
	}
	
	public void setDrinksConsumed(int newDrinksConsumed) {
		this.drinksConsumed = newDrinksConsumed;
	}
	
	public void setTimeoutTimeRemaining(int timeout) {
		this.timeoutTimeRemaining = timeout;
	}

	public void setIsInThePartyState(boolean newInThePartyState) {
		this.isInTheParty = newInThePartyState;
	}

	public void setIsHit(boolean newIsHitState) {
		this.isHit = newIsHitState;
	}
	
	// ************** move functions **************
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
