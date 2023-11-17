package com.simulation.avatar;

import java.awt.Color;
import java.util.Random;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Thorvin extends Avatar {

	public Thorvin(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Direction moveAvatar() {
		Random rand = new Random();
		int number = rand.nextInt(4);
		// direction is set externally --> check with the simulation environment
		Direction dir = Direction.FORWARD;
		if (number == 0) {
			dir = Direction.FORWARD;
		}
		else if (number == 1) {
			dir = Direction.RIGHT;
		}
		else if (number == 2) {
			dir = Direction.BACK;
		}
		else if (number == 3) {
			dir = Direction.LEFT;
		}



		return dir;
	}

}
