package com.simulation.matrix;

import java.awt.Color;
import java.util.ArrayList;

import com.simulation.enums.ChangeInXY;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Shape;
import com.simulation.enviroment.MyFrame;
import com.simulation.partypeople.*;


public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;       // Array list for all avatars
	private ArrayList<LocatedAvatar> queueAvatars;  // Array list for tracking avatars in queue
	private ArrayList<LocatedAvatar> clubAvatars;  // Array list for tracking avatars in club
	private ArrayList<LocatedAvatar> unrenderedAvatars;  // Array list for tracking all avatars

	public Matrix() {
		env = new MyFrame();
		env.setVisible(true);
		avatars = new ArrayList<>();
		queueAvatars = new ArrayList<>();
		clubAvatars = new ArrayList<>();
		unrenderedAvatars = new ArrayList<>();

		Emmanuel emmanuel = new Emmanuel(Shape.CIRCLE, Color.RED, 0, 0, "Emmanuel", 0);
		Eliyas eliyas = new Eliyas(Shape.SQUARE, Color.MAGENTA, 0, 0, "Eliyas", 0);
		Emmanuel celestine = new Emmanuel(Shape.CIRCLE, Color.BLUE, 0, 0, "Celestine", 0);
		LocatedAvatar locEmmanuel = new LocatedAvatar(emmanuel, 0, 0);
		LocatedAvatar locCelestine = new LocatedAvatar(celestine, 0, 0);
		LocatedAvatar locEliyas = new LocatedAvatar(eliyas, 0, 0);
		Bernhard bernhard = new Bernhard(Shape.CIRCLE, Color.YELLOW, 0, 0, "Bernhard",0);
		LocatedAvatar locBernhard = new LocatedAvatar(bernhard, 0, 0);
		avatars.add(locEmmanuel);
		avatars.add(locCelestine);
		avatars.add(locEliyas);
		avatars.add(locBernhard);
	}

	private void sortAvatar(LocatedAvatar avatar) {
		if (!clubAvatars.contains(avatar) && !queueAvatars.contains(avatar) && !unrenderedAvatars.contains(avatar)) {
			unrenderedAvatars.add(avatar);
		}

		if (queueAvatars.size() < 17 && unrenderedAvatars.contains(avatar)) {
			int lastInQueueY = 22;
			if (!queueAvatars.isEmpty()) {
				lastInQueueY = queueAvatars.get(queueAvatars.size() - 1).getY();
			}
			if (lastInQueueY <= 22) {
				if (queueAvatars.isEmpty()) {
					unrenderedAvatars.remove(avatar);
					avatar.setX(34);
					avatar.setY(lastInQueueY);
					queueAvatars.add(avatar);
				} else if (lastInQueueY < 22) {
					unrenderedAvatars.remove(avatar);
					avatar.setX(34);
					avatar.setY(lastInQueueY + 1);
					queueAvatars.add(avatar);
				}
			}
		}
	}

	private void moveAvatarInQueue(LocatedAvatar avatar) {
		wait(30);
		avatar.setWhatIsee(env);
		int x = avatar.getX();
		int y = avatar.getY();
		if (x == 34 && y > 5) {
			if (env.isUsable(avatar.getX(), avatar.getY() - 1)) {
				avatar.setX(x);
				avatar.setY(y - 1);
				env.moveInQueue(x, y, avatar.getColor());
			}
		} else if (x <= 34 && y == 5) {
			if (queueAvatars.get(0).equals(avatar)) {
				if (env.isUsable(avatar.getX() - 1, avatar.getY())) {
					env.moveInQueue(x, y, avatar.getColor());
					if (x - 1 == 32) {
						queueAvatars.remove(avatar);
						clubAvatars.add(avatar);
					}
					avatar.setX(x - 1);
					avatar.setY(y);
				}
			}
		}
	}

	private void moveAvatarInClub(LocatedAvatar avatar) {
		wait(30);
		avatar.setWhatIsee(env);
		int oldX = avatar.getX();
		int oldY = avatar.getY();
		Direction dir = avatar.getAvatar().moveAvatar();
		switch (dir) {
			case FORWARD:
				switch (avatar.getHeading()) {
					case WEST -> changeXY(avatar, ChangeInXY.DECX);
					case EAST -> changeXY(avatar, ChangeInXY.INCX);
					case NORTH -> changeXY(avatar, ChangeInXY.DECY);
					case SOUTH -> changeXY(avatar, ChangeInXY.INCY);
				}
				break;
			case BACK:
				switch (avatar.getHeading()) {
					case WEST -> changeXY(avatar, ChangeInXY.INCX);
					case EAST -> changeXY(avatar, ChangeInXY.DECX);
					case NORTH -> changeXY(avatar, ChangeInXY.INCY);
					case SOUTH -> changeXY(avatar, ChangeInXY.DECY);
				}
				break;
			case RIGHT:
				switch (avatar.getHeading()) {
					case WEST -> changeXY(avatar, ChangeInXY.DECY);
					case EAST -> changeXY(avatar, ChangeInXY.INCY);
					case NORTH -> changeXY(avatar, ChangeInXY.INCX);
					case SOUTH -> changeXY(avatar, ChangeInXY.DECX);
				}
				break;
			case LEFT:
				switch (avatar.getHeading()) {
					case WEST -> changeXY(avatar, ChangeInXY.INCY);
					case EAST -> changeXY(avatar, ChangeInXY.DECY);
					case NORTH -> changeXY(avatar, ChangeInXY.DECX);
					case SOUTH -> changeXY(avatar, ChangeInXY.INCX);
				}
				break;

		}
		env.setPlaceFree(oldX, oldY);
		env.moveTo(oldX, oldY, avatar.getX(), avatar.getY(),avatar.getColor());
		env.setPlaceOccupied(avatar.getX(), avatar.getY());
	}

	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}


	public void run() {
		while (true) {
			for (LocatedAvatar avatar : avatars) {
				sortAvatar(avatar);

				if (queueAvatars.contains(avatar)) {
					moveAvatarInQueue(avatar);
				}

				if (clubAvatars.contains(avatar)) {
					moveAvatarInClub(avatar);
				}
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
