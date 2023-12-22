///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Catherine2.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Catherine2 extends Avatar{

	// store locally where u are
	// check with that exactly what can u do?
	//Places whereIAm = getWhatISee()[0];
	//Places whatISee = getWhatISee()[1];
	private boolean amOnDanceFloor = false;
	private int durationStayingStill = 0;

	// ************** Constructor **************
	public Catherine2(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO Auto-generated constructor stub
	}

	public void dancingAlgo() {
		// System.out.println("dance");
		// replaced with private method moveOnDanceFloor(whatISee)

	}

	public void talk() {
		// System.out.println("Talking to myself...");
	}

	public void talkToPerson(Avatar person) {
		// System.out.println("Hi " + person.getName());
	}

	public Direction moveAvatar() {
		Direction dir = Direction.IDLE;
		if (amOnDanceFloor) {
			moveOnDanceFloor();
		}
		else {
			// Don't go into any place that isn't the dancefloor
			if (getWhatISee()[1] == Places.PATH) {
				dir = getRandomMovement();
			}
			else if (getWhatISee()[1] != Places.DANCEFLOOR && getWhatISee()[1] != Places.PATH) {
				dir = Direction.BACK;
			}
			else if (getWhatISee()[1] == Places.DANCEFLOOR) {
				dir = Direction.FORWARD;
				amOnDanceFloor = true;
			}
		}
		return dir;
	}

	private Direction moveOnDanceFloor() {
		amOnDanceFloor = true;
		Direction dir = Direction.IDLE;

		while (durationStayingStill < 100) {	
			System.out.println(durationStayingStill);
			System.out.println(dir);

			if (durationStayingStill % 2 == 0) {
				dir = Direction.FORWARD;
			}	
			else {
				dir = Direction.BACK;
			}
			durationStayingStill = durationStayingStill + 1;
		}

		return dir;
	}
	
	private Direction getRandomMovement() {
		Random randomNumber = new Random();
		int number = randomNumber.nextInt(4);
		Direction currentRandomDirection = Direction.FORWARD;
		if (number == 0) {
			currentRandomDirection = Direction.FORWARD;
		}
		else if (number == 1) {
			currentRandomDirection = Direction.BACK;
		}
		else if (number == 2) {
			currentRandomDirection = Direction.RIGHT;
		}
		else if (number == 3) {
			currentRandomDirection = Direction.LEFT;
		}
		return currentRandomDirection;
	}
}