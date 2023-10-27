package partySimulation;

import java.util.ArrayList;

import com.simulation.avatar.Avatar;
import com.simulation.avatar.Bouncer;
import com.simulation.enums.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enviroment.MyFrame;

public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;

	public Matrix() {
		env = new MyFrame();
		avatars = new ArrayList<LocatedAvatar>();
		Bouncer bouncer = new Bouncer(Shape.CIRCLE, Color.RED, 0, 0, 0, 0, null, null);
		LocatedAvatar locBouuncer = new LocatedAvatar(bouncer, 0, 0);
		avatars.add(locBouuncer);

	}

	public void run() {
		while (true) {
			for (LocatedAvatar locAvatar : avatars) {

				int oldX = locAvatar.getX();
				int oldY = locAvatar.getY();
				Direction dir = locAvatar.getAvatar().moveAvatar();
				switch (dir) {
				case FORWARD:
					if (locAvatar.getHeading() == Heading.WEST) {
						if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY()))
							locAvatar.incX();
					} else if (locAvatar.getHeading() == Heading.EAST) {
						if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY()))
							locAvatar.decX();
					} else if (locAvatar.getHeading() == Heading.NORTH) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1))
							locAvatar.decY();
					} else if (locAvatar.getHeading() == Heading.SOUTH) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1))
							locAvatar.incY();
					}
				case BACK:
					if (locAvatar.getHeading() == Heading.WEST) {
						if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY()))
							locAvatar.decX();
						locAvatar.setHeading(Heading.EAST);
					} else if (locAvatar.getHeading() == Heading.EAST) {
						if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY()))
							locAvatar.incX();
						locAvatar.setHeading(Heading.WEST);
					} else if (locAvatar.getHeading() == Heading.NORTH) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1))
							locAvatar.incY();
						locAvatar.setHeading(Heading.SOUTH);
					} else if (locAvatar.getHeading() == Heading.SOUTH) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1))
							locAvatar.decY();
						locAvatar.setHeading(Heading.NORTH);
					}
				case RIGHT:
					if (locAvatar.getHeading() == Heading.WEST) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1))
							locAvatar.decY();
						locAvatar.setHeading(Heading.NORTH);
					} else if (locAvatar.getHeading() == Heading.EAST) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1))
							locAvatar.incY();
						locAvatar.setHeading(Heading.SOUTH);
					} else if (locAvatar.getHeading() == Heading.NORTH) {
						if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY()))
							locAvatar.decX();
						locAvatar.setHeading(Heading.EAST);
					} else if (locAvatar.getHeading() == Heading.SOUTH) {
						if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY()))
							locAvatar.incX();
						locAvatar.setHeading(Heading.WEST);
					}
				case LEFT:
					if (locAvatar.getHeading() == Heading.WEST) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1))
							locAvatar.incY();
						locAvatar.setHeading(Heading.SOUTH);
					} else if (locAvatar.getHeading() == Heading.EAST) {
						if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1))
							locAvatar.decY();
						locAvatar.setHeading(Heading.NORTH);
					} else if (locAvatar.getHeading() == Heading.NORTH) {
						if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY()))
							locAvatar.incX();
						locAvatar.setHeading(Heading.WEST);
					} else if (locAvatar.getHeading() == Heading.SOUTH) {
						if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY()))
							locAvatar.decX();
						locAvatar.setHeading(Heading.EAST);
					}
				}
				env.moveTo(oldX, oldY, locAvatar.getX(), locAvatar.getY());
			}
		}

	}
}
