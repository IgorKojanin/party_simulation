///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import java.awt.Color;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import java.awt.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Anatoly extends Avatar{

	// store locally where u are
	// check with that exactly what can u do?

	// ************** Constructor **************
	public Anatoly(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO Auto-generated constructor stub
	}

	public void dancingAlgo() {
		// if while moving the dance_floor was detected and the priority is right
        // start dancing animation 
        // motions within the square itself?
	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
		// if an interaction with another partyGoer has occured
        // start dirty-talking 
        // implement Wrestling moves: rko, f5, aa.
	}

	public void talk(Avatar person) {
		// TODO
	}

	public void smoke() {
		// TODO
        // shall stay empty
	}

	public void toilet(int timeInToilet) {
		// TODO

	}

	public void playPool() {
		// TODO

	}

	public void playFussball() {
		// TODO

	}

	public Direction moveAvatar() {
		// TODO
		Direction dir = Direction.FORWARD;
		return dir;

        // define a variable that would hold a list of priorities: sort places on a scale of 1-5
        // check the map of places discovered, if it's empty randomly head to places until everything is discovered and stored
        // if the whole map was discovered start applying the priority queue
        // head first towards the higher-rated places on the list, stay there longer
    }
}