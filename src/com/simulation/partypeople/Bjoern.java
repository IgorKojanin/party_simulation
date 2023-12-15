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
import com.simulation.enums.Places;

public class Bjoern extends Avatar {

	// store locally where u are
	// check with that exactly what can u do?
	private boolean onDanceFloor = false;
	private int momentsIDidntMove = 0;

	// ************** Constructor **************
	public Bjoern(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
	}

	public void dancingAlgo() {
	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
	}

	public void talk(Avatar person) {
	}

	public void smoke() {
	}

	public void toilet(int timeInToilet) {
	}

	public void playPool() {
	}

	public void playFussball() {
	}

	public Direction moveAvatar() {
		Places inFrontOfMe = getWhatISee()[1];
		if (!onDanceFloor) {
			switch (inFrontOfMe) {
			case PATH:
				return determineMovementRandomly();
			case PERSON:
				return Direction.LEFT;
			case DANCEFLOOR:
				onDanceFloor = true;
				return Direction.IDLE;
			case OUTSIDE:
				return Direction.BACK;
			case TOILET:
				return Direction.BACK;
			default:
				return determineMovementRandomly();
			}
		} else {
			return moveAvatarOnDanceFloor(inFrontOfMe);
		}
	}
	
	private Direction moveAvatarOnDanceFloor(Places inFrontOfMe) {
		switch (inFrontOfMe) {
		case PATH:
			return Direction.BACK;
		case PERSON:
			momentsIDidntMove++;
			if ( momentsIDidntMove > 3) {
				return Direction.TURN_RIGHT_ON_SPOT;
			}
			return Direction.IDLE;
		case DANCEFLOOR:
			onDanceFloor = true;
			return Direction.FORWARD;
		default:
			return determineMovementRandomly();
		}
	}

	private Direction determineMovementRandomly() {
		int rand = ThreadLocalRandom.current().nextInt(0, 70);
		switch ((00 <= rand && rand < 50) ? 0 : (50 <= rand && rand < 60) ? 1 : 2) {
		case 0:
			return Direction.FORWARD;
		case 1:
			return Direction.RIGHT;
		default:
			return Direction.LEFT;
		}
	}
}
