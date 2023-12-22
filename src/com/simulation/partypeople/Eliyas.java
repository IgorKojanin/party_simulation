package com.simulation.partypeople;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Eliyas extends Avatar {
	public Eliyas(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

	}

	public Direction dancingAlgo() {
		if (getWhatISee()[1] == Places.DANCEFLOOR) {
			return Direction.FORWARD;
		} else {
			return Direction.TURN_RIGHT_ON_SPOT;
		}
	}

	public void drink(BeverageType type) {
		if (getWhatISee()[1] == Places.BAR) {
			System.out.println("Can I have a bottle of water");
		}

	}

	public void fight(Avatar opponent) {
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
		if (getWhatISee()[1] == Places.POOL) {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void playFussball() {
		if (getWhatISee()[1] == Places.FUSSBALL) {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Direction moveAvatar() {
		Places iStandOn = getWhatISee()[0];
		Places inFrontOfMe = getWhatISee()[1];
		if (iStandOn != Places.DANCEFLOOR)
			switch (inFrontOfMe) {
			case WALL:
				return Direction.BACK;
			case BAR:
				return Direction.BACK;
			case POOL:
				return Direction.TURN_LEFT_ON_SPOT;
			case FUSSBALL:
				return Direction.TURN_RIGHT_ON_SPOT;
			case TOILET:
				return Direction.BACK;
			case DANCEFLOOR:
				return Direction.FORWARD;
			case BOUNCER:
				return Direction.BACK;
			case DJ:
				return Direction.BACK;
			case OUTSIDE:
				return Direction.BACK;
			default:
				return randomMovement();
			}
		else {
				return dancingAlgo();	
		}
	}

	private Direction randomMovement() {
		Random rand = new Random();
		int number = rand.nextInt(100);
		Direction dir = Direction.IDLE;
		if (number <= 45) {
			dir = Direction.FORWARD;
		} else if (number > 45 && number < 65) {
			dir = Direction.RIGHT;
		} else if (number > 65 && number < 75) {
			dir = Direction.BACK;
		} else if (number >= 75) {
			dir = Direction.LEFT;
		}
		return dir;
	}
}
