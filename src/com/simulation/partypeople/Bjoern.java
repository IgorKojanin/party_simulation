///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         01/12/2023
//
// Class: Bjoern.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Bjoern extends Avatar{

	// store locally where u are
	// check with that exactly what can u do?

	// ************** Constructor **************
	public Bjoern(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO Auto-generated constructor stub
	}

	public void dancingAlgo() {
		// TODO

	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
		// TODO

	}

	public void talk(Avatar person) {
		// TODO
	}

	public void smoke() {
		// TODO
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
		int r = ThreadLocalRandom.current().nextInt(0, 100); 
		switch ((00 <= r && r < 40 ) ? 0 :
				(40 <= r && r < 65) ? 1 :
				(65 <= r && r < 90) ? 2 : 3){
		case 0: return Direction.FORWARD;
		case 1: return Direction.RIGHT;
		case 2: return Direction.LEFT;
		case 3: return Direction.BACK;
		default: return Direction.IDLE;
		}
	}
}
