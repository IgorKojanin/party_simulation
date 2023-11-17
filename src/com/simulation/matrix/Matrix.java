package com.simulation.matrix;


import java.awt.Color;
import java.util.ArrayList;

import com.simulation.avatar.Bouncer;
import java.awt.Color;

import com.simulation.enums.ChangeInXY;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Shape;
import com.simulation.enviroment.MyFrame;

public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;

	public Matrix() {
		env = new MyFrame();
		env.setVisible(true);
		avatars = new ArrayList<LocatedAvatar>();
		Bouncer bouncer = new Bouncer(Shape.CIRCLE, Color.RED, 0);
		LocatedAvatar locBouncer = new LocatedAvatar(bouncer, 0, 0);
		avatars.add(locBouncer);

	}

	public void run() {
		while (true) {
			for (LocatedAvatar locAvatar : avatars) {

				int oldX = locAvatar.getX();
				int oldY = locAvatar.getY();
				Direction dir = locAvatar.getAvatar().moveAvatar();
				switch (dir) {
				case FORWARD:
					switch(locAvatar.getHeading()) {
					case WEST:changeXY(locAvatar, ChangeInXY.DECX);
					case EAST: changeXY(locAvatar, ChangeInXY.INCX);
					case NORTH:changeXY(locAvatar, ChangeInXY.DECY);
					case SOUTH:changeXY(locAvatar, ChangeInXY.INCY);			
					}
				case BACK:
					switch(locAvatar.getHeading()) {
					case WEST:changeXY(locAvatar, ChangeInXY.INCX);
					case EAST: changeXY(locAvatar, ChangeInXY.DECX);
					case NORTH:changeXY(locAvatar, ChangeInXY.INCY);
					case SOUTH:changeXY(locAvatar, ChangeInXY.DECY);			
					}
				case RIGHT:
					switch(locAvatar.getHeading()) {
					case WEST:changeXY(locAvatar, ChangeInXY.DECY);
					case EAST: changeXY(locAvatar, ChangeInXY.INCY);
					case NORTH:changeXY(locAvatar, ChangeInXY.INCX);
					case SOUTH:changeXY(locAvatar, ChangeInXY.DECX);			
					}
				case LEFT:
					switch(locAvatar.getHeading()) {
					case WEST:changeXY(locAvatar, ChangeInXY.INCY);
					case EAST: changeXY(locAvatar, ChangeInXY.DECY);
					case NORTH:changeXY(locAvatar, ChangeInXY.DECX);
					case SOUTH:changeXY(locAvatar, ChangeInXY.INCX);			
					}
				}
				env.moveTo(oldX, oldY, locAvatar.getX(), locAvatar.getY(), Color.BLUE);
			}
		}
	}

	private void changeXY(LocatedAvatar locAvatar, ChangeInXY c) {
		switch (c) {
		case INCX:
			if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY())) {
				locAvatar.incX();
				locAvatar.setHeading(Heading.EAST);
			}

		case DECX:
			if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY())) {
				locAvatar.decX();
				locAvatar.setHeading(Heading.WEST);
			}

		case INCY:
			if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1)) {
				locAvatar.incY();
				locAvatar.setHeading(Heading.SOUTH);
			}

		case DECY:
			if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1)) {
				locAvatar.decY();
				locAvatar.setHeading(Heading.NORTH);
			}

		}
	}
}
