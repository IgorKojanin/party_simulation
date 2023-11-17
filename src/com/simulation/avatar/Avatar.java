///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Avatar.java
// Description: Abstract class with basic functions for all the people
///////////////////////////////////////////////////////////////////////////////
// @param shape             		The shape of the avatar.
// @param color             		The color of the avatar.
// @param borderWidth      			The border width of the avatar.
// @param avatarId          		The unique ID of the avatar.
// @param avatarAge         		The age of the avatar.
// @param AlcoholPercentage 		The alcohol percentage of the avatar.
// @param avatarName        		The name of the avatar.
// @param isDancing         		State of the avatar dancing. 
// @param isHit             		State of the avatar being hit. 
// @param timeoutTimeRemaining      Amount of timeout time the avatar has left. 
// @param isInTheParty      		If the avatar is in the party. 
// @param waitingTime      			Amount of waiting time the avatar has. 
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import java.awt.Color;

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

	// Addition of waiting time variable for queing, or ordering drinks, or waiting
	// to play a game etc
	private int waitingTime;

	// ************** Main constructor for PartyGoer **************
	public Avatar(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
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
		this.waitingTime = waitingTime;
	}

	// ************** Constructor for workers (DJ, bouncer & bartender)
	// **************
	public Avatar(Shape shape, Color color2, int borderWidth) {

		this.shape = shape;
		this.color = color2;
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

	public int getWaitingTime() { // Waiting time getter
		return waitingTime;
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

	public void setWaitngTime(int newWaitingTime) { // Setter for waiting time
		this.waitingTime = newWaitingTime;
	}

	// ************** See function **************
	public void setWhatISee(Places[] places) { // set function from simulation, returns array of Places enums. 2 places ahead

	}
	
	public void drink(BeverageType type, Bartender bartender) {
		//if (getWhatISee() == BAR AREA) { // can only call this function if you're at the area of the bar
            bartender.addOrderToQueue(this, type);
        //} else {
        //    System.out.println("You're not at the bar area.");
        //}
	}

	public abstract Direction moveAvatar(); // To be specified on each personal class
}
