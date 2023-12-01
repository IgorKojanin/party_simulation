package com.simulation.matrix;

import java.awt.Color;

import com.simulation.avatar.Avatar;

import com.simulation.enums.Heading;
import com.simulation.enums.Places;
import com.simulation.enviroment.MyFrame;

import java.util.Scanner;

public class LocatedAvatar {
	private Avatar avatar;
	private int x;
	private int y;
	private Heading heading;

	public LocatedAvatar(Avatar avatar, int x, int y) {
		this.avatar = avatar;
		this.x = x;
		this.y = y;
		this.heading = Heading.WEST;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public Heading getHeading() {
		return heading;
	}

	public void incX() {
		x++;
	}

	public void decX() {
		x--;
	}

	public void incY() {
		y++;
	}

	public void decY() {
		y--;
	}

	public void setHeading(Heading heading) {
		this.heading = heading;
	}

	public Color getColor() {
		return avatar.getColor();
	}

	public void setWhatIsee(MyFrame env) {
		Places[] p = new Places[4];
		p = getPositionPlace(env);
		avatar.setWhatISee(p);

		System.out.println(
				"\n" + avatar.getName() + "'s heading: " + heading + " " + p[0] + " " + p[1] + " " + p[2] + " " + p[3]);
		Scanner inp = new Scanner(System.in);
		inp.nextLine();
	}

	private Places[] getPositionPlace(MyFrame env) {
		Places[] places = new Places[4];
		Places front = null;
		Places back = null;
		Places left = null;
		Places right = null;
		if (x > MyFrame.getEntranceX()) {
			front = Places.OUTSIDE;
			back = Places.OUTSIDE;
			left = Places.OUTSIDE;
			right = Places.OUTSIDE;
		} else {
			switch (getHeading()) {
			case WEST:
				if (x == 0) {
					front = Places.WALL;
				} else {
					front = getPlace(env, x - 1, y);
				}
				if (y == 0) {
					right = Places.WALL;
				} else {
					right = getPlace(env, x, y - 1);
				}
				if (y == MyFrame.getMaxY() - 1) {
					left = Places.WALL;
				} else {
					left = getPlace(env, x, y + 1);
				}
				if (x == MyFrame.getEntranceX()) {
					back = Places.WALL;
				} else {
					back = getPlace(env, x + 1, y);
				}
			case EAST:
				if (x == MyFrame.getEntranceX()) {
					front = Places.WALL;
				} else {
					front = getPlace(env, x + 1, y);
				}
				if (y == 0) {
					left = Places.WALL;
				} else {
					left = getPlace(env, x, y - 1);
				}
				if (y == MyFrame.getMaxY() - 1) {
					right = Places.WALL;
				} else {
					right = getPlace(env, x, y + 1);
				}
				if (x == 0) {
					back = Places.WALL;
				} else {
					back = getPlace(env, x - 1, y);
				}
			case NORTH:
				if (y == 0) {
					front = Places.WALL;
				} else {
					front = getPlace(env, x, y - 1);
				}
				if (x == 0) {
					left = Places.WALL;
				} else {
					left = getPlace(env, x - 1, y);
				}
				if (x == MyFrame.getEntranceX()) {
					right = Places.WALL;
				} else {
					right = getPlace(env, x + 1, y);
				}
				if (y == MyFrame.getMaxY()) {
					back = Places.WALL;
				} else {
					back = getPlace(env, x, y + 1);
				}
			default: // SOUTH:
				if (y == MyFrame.getMaxY() - 1) {
					front = Places.WALL;
				} else {
					front = getPlace(env, x, y + 1);
				}
				if (x == 0) {
					right = Places.WALL;
				} else {
					right = getPlace(env, x - 1, y);
				}
				if (x == MyFrame.getEntranceX()) {
					left = Places.WALL;
				} else {
					left = getPlace(env, x + 1, y);
				}
				if (y == 0) {
					back = Places.WALL;
				} else {
					back = getPlace(env, x, y - 1);
				}
			}
		}
		places[0] = front;
		places[1] = back;
		places[2] = left;
		places[3] = right;
		return places;
	}

	private Places getPlace(MyFrame env, int x, int y) {
		if (env.getPlace(x, y) == Places.PATH && !env.isUsable(x, y)) {
			return Places.PERSON;
		} else {

			return env.getPlace(x, y);
		}
	}

}
