package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;
import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Eliyas extends Avatar{

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
		Random rand = new Random();
		int number = rand.nextInt(4);
		Direction dir = Direction.FORWARD;
		if (number == 0) {
			dir = Direction.FORWARD;
		} else if (number == 1) {
			dir = Direction.RIGHT;
		} else if (number == 2) {
			dir = Direction.BACK;
		} else if (number == 3) {
			dir = Direction.LEFT;
		}
		return dir;
	}

}
