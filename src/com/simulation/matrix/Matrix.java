package com.simulation.matrix;

import java.awt.Color;
import java.util.ArrayList;

import com.simulation.avatar.Bouncer;
import com.simulation.enums.Colors;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enviroment.MyFrame;

public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;

	public Matrix() {
		env = new MyFrame();
		env.setVisible(true);
		avatars = new ArrayList<LocatedAvatar>();
		Bouncer bouncer = new Bouncer(Shape.CIRCLE, Colors.RED, 0);
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
						switch (locAvatar.getHeading()) {
							case WEST:
								changeXY(locAvatar, ChangeInXY.DECX);
								break;
							case EAST:
								changeXY(locAvatar, ChangeInXY.INCX);
								break;
							case NORTH:
								changeXY(locAvatar, ChangeInXY.DECY);
								break;
							case SOUTH:
								changeXY(locAvatar, ChangeInXY.INCY);
								break;
						}
						break;
					case BACK:
						switch (locAvatar.getHeading()) {
							case WEST:
								changeXY(locAvatar, ChangeInXY.INCX);
								break;
							case EAST:
								changeXY(locAvatar, ChangeInXY.DECX);
								break;
							case NORTH:
								changeXY(locAvatar, ChangeInXY.INCY);
								break;
							case SOUTH:
								changeXY(locAvatar, ChangeInXY.DECY);
								break;
						}
						break;
					case RIGHT:
						switch (locAvatar.getHeading()) {
							case WEST:
								changeXY(locAvatar, ChangeInXY.DECY);
								break;
							case EAST:
								changeXY(locAvatar, ChangeInXY.INCY);
								break;
							case NORTH:
								changeXY(locAvatar, ChangeInXY.INCX);
								break;
							case SOUTH:
								changeXY(locAvatar, ChangeInXY.DECX);
								break;
						}
						break;
					case LEFT:
						switch (locAvatar.getHeading()) {
							case WEST:
								changeXY(locAvatar, ChangeInXY.INCY);
								break;
							case EAST:
								changeXY(locAvatar, ChangeInXY.DECY);
								break;
							case NORTH:
								changeXY(locAvatar, ChangeInXY.DECX);
								break;
							case SOUTH:
								changeXY(locAvatar, ChangeInXY.INCX);
								break;
						}
						break;
				}
				env.moveTo(oldX, oldY, locAvatar.getX(), locAvatar.getY(), Color.BLUE);
			}
		}
	}

	private void changeXY(LocatedAvatar locAvatar, ChangeInXY c) {
		switch (c) {
			case INCX:
				if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY())
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX() + 1, locAvatar.getY())) {
					locAvatar.incX();
					locAvatar.setHeading(Heading.EAST);
				}
				break;

			case DECX:
				if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY())
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX() - 1, locAvatar.getY())) {
					locAvatar.decX();
					locAvatar.setHeading(Heading.WEST);
				}
				break;

			case INCY:
				if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1)
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX(), locAvatar.getY() + 1)) {
					locAvatar.incY();
					locAvatar.setHeading(Heading.SOUTH);
				}
				break;

			case DECY:
				if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1)
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX(), locAvatar.getY() - 1)) {
					locAvatar.decY();
					locAvatar.setHeading(Heading.NORTH);
				}
				break;

		}
	}
}
