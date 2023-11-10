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
import java.util.Random;

import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enums.Colors;


public abstract class Avatar {

	private static int Id; 

	private Shape shape;
	private Colors color;
	private int borderWidth;
	private int avatarId;
	private int avatarAge;
	private String avatarName;
	private int drinksConsumed = 0;
	private boolean isDancing = false;
	private boolean isHit = false;
	private int timeoutTimeRemaining = 0;
	private boolean isInTheParty;
	// ************** Main constructor for PartyGoer **************
	public Avatar(Shape shape, Colors color, int borderWidth, int avatarAge, int drinksConsumed, boolean isHit, int timeoutTimeRemaining, boolean isInTheParty, String avatarName) {
		Id += Id;
		this.avatarName = avatarName;
		this.shape = shape;
		this.color = color;
		this.borderWidth = borderWidth;
		this.avatarId = Id;
		this.avatarAge = avatarAge;
		this.drinksConsumed = drinksConsumed;
		this.isHit = isHit;
		this.timeoutTimeRemaining = timeoutTimeRemaining;
		this.isInTheParty = isInTheParty;
	}
	
	// ************** Constructor for workers (DJ, bouncer & bartender) **************
	public Avatar(Shape shape, Colors color, int borderWidth) { 
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
	
	// ************** See function **************
	public void getWhatISee(){ 		// get function from simulation, returns array of Places enums. 2 places ahead

	}

	public abstract Direction moveAvatar();  // To be specified on each personal class

}
