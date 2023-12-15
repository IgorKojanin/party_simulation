package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;
import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Eliyas extends Avatar {
	Direction dir = Direction.IDLE;

	public Eliyas(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

	}

	public Direction dancingAlgo() {
		if (getWhatISee()[0] == Places.DANCEFLOOR) {
			return Direction.FORWARD;
		} else {
			return  Direction.BACK;
		}
	}

	public void drink(BeverageType type) {
		// TODO

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
		// TODO

	}

	public void playFussball() {
		// TODO

	}

	public Direction moveAvatar() {
				
		Random rand = new Random();
		int number = rand.nextInt(100);
		Places iSee = getWhatISee()[0];
		switch(iSee) {
		case WALL:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		case BAR:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		case POOL:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		case FUSSBALL:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		case TOILET:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;	
		case DANCEFLOOR:		
			return dancingAlgo();
		case BOUNCER:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		case DJ:
			dir = Direction.TURN_LEFT_ON_SPOT;
			return dir;
		default:
			if (number <= 45) {
				dir = Direction.FORWARD;
			} else if (number > 45 && number < 55) {
				dir = Direction.RIGHT;
			} else if (number > 55 && number < 85) {
				dir = Direction.BACK;
			} else if (number >= 85) {
				dir = Direction.LEFT;
			}
			return dir;
		}

	}
	
	

	
	
	
	

}
