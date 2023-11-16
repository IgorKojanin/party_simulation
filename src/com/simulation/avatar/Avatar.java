///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Avatar.java
// Description: Abstract class with basic functions for all the people
///////////////////////////////////////////////////////////////////////////////
// @param shape            The shape of the avatar.
// @param color            The color of the avatar.
// @param borderWidth      The border width of the avatar.
// @param avatarId         The unique ID of the avatar.
// @param avatarAge        The age of the avatar.
// @param drinksConsumed   The number of drinks consumed by the avatar.
// @param isDancing        State of dancing avatar
// @param timeOut          The time it should remain outside
// 
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import java.awt.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.Random;



public abstract class Avatar {

	private static int Id; 

	private Shape shape;
	private Color color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private int AlcoholPercentage = 0;
	private String avatarName;
	private boolean isDancing = false;
	private boolean isHit = false;
	private int timeoutTimeRemaining = 0;
	private boolean isInTheParty;
	// ************** Main constructor for PartyGoer **************

	public Avatar(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName) {
		Id += Id;
		this.avatarName = avatarName;
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = Id;
		this.avatarAge = avatarAge;
		this.AlcoholPercentage = 0;
		this.isHit = false;
		this.timeoutTimeRemaining = 0;
		this.isInTheParty = false;
	}
	
	// ************** Constructor for workers (DJ, bouncer & bartender) **************

	public Avatar(Shape shape, Color color, int borderWidth) {
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = Id;
	}	
	
	// ************** get functions **************

	public Shape getShape() {
		return this.shape;
	}

	public String getName() {
		return this.avatarName;
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
	
	public int getAlcoholPercentage() {
		return this.AlcoholPercentage;
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
	
	public void setAlcoholPercentage(int newAlcoholPercentage) {
		this.AlcoholPercentage = newAlcoholPercentage;
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
	
	// ************** See function **************
	public void getWhatISee(){ 		// get function from simulation, returns array of Places enums. 2 places ahead

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
