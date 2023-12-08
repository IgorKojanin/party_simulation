package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;
import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Eliyas extends Avatar {

	public Eliyas(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

	}

	public void dancingAlgo() {
		// TODO
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
		//Places frontPlace = getWhatISee()[0];
		Direction dir = Direction.IDLE;
		if (getWhatISee()[0] == Places.PATH) {
			dir = Direction.FORWARD;
		} else if (getWhatISee()[0] == Places.WALL) {
			dir = Direction.LEFT;
		}else if (getWhatISee()[0] == Places.BAR) {
			dir = Direction.LEFT;
		}else if (getWhatISee()[0] == Places.DANCEFLOOR) {
			dir = Direction.RIGHT;	
		}else if (getWhatISee()[0] == Places.TOILET) {
			dir = Direction.RIGHT;
		}

		return dir;
	}

}
