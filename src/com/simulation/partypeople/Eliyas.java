package com.simulation.partypeople;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Eliyas extends Avatar {

	private int rows = 100;
	private int columns = 2;
	// Create a 2D array to store x, y coordinates
	private int[][] xyPosition = new int[rows][columns];
	private HashMap<Places, int[][]> mindMap = new HashMap<>();
	private int x = 40;
	private int y = 30;
	private Places[][] mindmap = new Places[45][50];
	private boolean headingWest = false;
	private boolean headingEast = false;
	private boolean headingNorth = false;
	private boolean headingSouth = false;
	private boolean printFlag = true;
	private boolean danceFloorFound = false;
	private boolean barFound = false;
	private boolean loungeFound = false;

	

	public Eliyas(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

	}

	public Direction dancingAlgo() {
//		if (!danceFloorFound) {
//			if (getWhatISee()[1] == Places.DANCEFLOOR) {
//				danceFloorFound = true;
//				return Direction.FORWARD;
//			} else {
//				return Direction.TURN_RIGHT_ON_SPOT;
//			}
//
//		} else {
//			return randomDirection();
//		}
		return Direction.IDLE;

	}

	public void drink(BeverageType type) {
//		if (getWhatISee()[1] == Places.BAR) {
//			System.out.println("Can I have a bottle of water");
//		}

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
//		if (getWhatISee()[1] == Places.POOL) {
//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

	}

	public void playFussball() {
//		if (getWhatISee()[1] == Places.FUSSBALL) {
//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	private int count = 0;
	private int countBarStay = 0;
	private int countLoungeStay = 0;

	private Direction findDanceFloor() {
		if (getWhatISee()[0] == Places.DANCEFLOOR && getWhatISee()[1] == Places.PATH && !danceFloorFound) {
			count += 1;
			if (count < 4) {
				return Direction.TURN_RIGHT_ON_SPOT;
			} else {
				danceFloorFound = true;
				return Direction.FORWARD;
			}
		} else {
			return moveRandomly();
		}
	}

	private Direction findBar() {
		if (getWhatISee()[1] == Places.BAR && !barFound) {
			countBarStay += 1;
			if (countBarStay < 4) {
				return Direction.IDLE;
			} else {
				barFound = true;
				return Direction.BACK;
			}
		} else {
			return moveRandomly();
		}
	}

	private Direction findLounge() {
		if ((getWhatISee()[0] == Places.LOUNGE_BIG || getWhatISee()[0] == Places.LOUNGE_SMALL) && !loungeFound) {
			countLoungeStay += 1;
			if (countLoungeStay < 4) {
				return Direction.IDLE;
			} else {
				loungeFound = true;
				return Direction.BACK;
			}
		} else {
			return moveRandomly();
		}
	}

	private Direction findPlacesRandomly() {
		if (!danceFloorFound) {
			System.out.println("I'm looking for dance floor");
			return findDanceFloor();
		} else if (danceFloorFound && !barFound) {
			System.out.println("I'm looking for bar");
			return findBar();
		} else if (danceFloorFound && barFound && !loungeFound) {
			System.out.println("I'm looking for Lounge");
			return findLounge();
		} else {
			System.out.println("All three places are found");
			return randomDirection();
		}
	}

	private Direction moveRandomly() {
		switch (getWhatISee()[1]) {
		case WALL:
			return randomDirection();
		case BAR:
			return randomDirection();
		case POOL:
			return Direction.TURN_RIGHT_ON_SPOT;
		case FUSSBALL:
			return Direction.TURN_RIGHT_ON_SPOT;
		case PERSON:
			return Direction.IDLE;
		case TOILET:
			return randomDirection();
		case DANCEFLOOR:
			return Direction.FORWARD;
		case BOUNCER:
			return randomDirection();
		case DJ:
			return randomDirection();
		case OUTSIDE:
			return Direction.BACK;
		case PATH:
			return randomDirection();
		default:
			return randomDirection();
		}
	}

	public Direction moveAvatar() {
//		Scanner scanner = new Scanner(System.in);
//		scanner.nextLine();
		// System.out.println(getWhatISee()[1]);
		// return findPlacesRandomly();
		return moveAvatarToDrawMap();

	}

	private Direction randomDirection() {
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

//	private void lookAround(Heading heading) {
//		if (heading == Heading.WEST) {
//
//		}
//	}

	private boolean isUsable(Places inFrontOfMe) {
		if (inFrontOfMe == Places.PATH || inFrontOfMe == Places.DANCEFLOOR || inFrontOfMe == Places.LOUNGE_BIG
				|| inFrontOfMe == Places.LOUNGE_SMALL || inFrontOfMe == Places.LOUNGE_SMOKING
				|| inFrontOfMe == Places.OUTSIDE) {
			return true;
		} else {
			return false;
		}

	}

	private Direction moveAvatarToDrawMap() {
		int i = 0;
		Heading heading = Heading.WEST;
		Places iStandOn = getWhatISee()[0];
		Places inFrontOfMe = getWhatISee()[1];
		if (printFlag) {
			for (Entry<Places, int[][]> entry : mindMap.entrySet()) {
				int[][] values = entry.getValue();
				System.out.println(entry.getKey() + ": " + values[i][0] + ", " + values[i][1]);
			}
		}
		if (!headingWest && heading == Heading.WEST) {
			headingWest = true;
			inFrontOfMe = getWhatISee()[1];

			if (heading == Heading.WEST && isUsable(inFrontOfMe)) {
				x--;
				xyPosition[i][0] = x;
				xyPosition[i][1] = y;
				mindMap.put(inFrontOfMe, xyPosition);
				i++;
				return Direction.FORWARD;
			}

			if (inFrontOfMe == Places.DANCEFLOOR) {
				danceFloorFound = true;
				return Direction.FORWARD;
			}
			heading = Heading.NORTH;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else if (!headingNorth && heading == Heading.NORTH) {
			headingNorth = true;
			inFrontOfMe = getWhatISee()[1];

			if (heading == Heading.NORTH && isUsable(inFrontOfMe)) {
				y--;
				xyPosition[i][0] = x;
				xyPosition[i][1] = y;
				mindMap.put(inFrontOfMe, xyPosition);
				i++;
				return Direction.FORWARD;
			}

			if (inFrontOfMe == Places.DANCEFLOOR) {
				danceFloorFound = true;
				return Direction.FORWARD;
			}
			heading = Heading.EAST;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else if (!headingEast && heading == Heading.EAST) {

			headingEast = true;
			inFrontOfMe = getWhatISee()[1];

			if (heading == Heading.EAST && isUsable(inFrontOfMe)) {
				x++;
				xyPosition[i][0] = x;
				xyPosition[i][1] = y;
				mindMap.put(inFrontOfMe, xyPosition);
				i++;
				return Direction.FORWARD;
			}

			if (inFrontOfMe == Places.DANCEFLOOR) {
				danceFloorFound = true;
				return Direction.FORWARD;
			}
			heading = Heading.SOUTH;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else if (!headingSouth && heading == Heading.SOUTH) {
			headingSouth = true;
			inFrontOfMe = getWhatISee()[1];

			if (heading == Heading.SOUTH && isUsable(inFrontOfMe)) {
				y++;
				xyPosition[i][0] = x;
				xyPosition[i][1] = y;
				mindMap.put(inFrontOfMe, xyPosition);
				i++;
				return Direction.FORWARD;
			}

			if (inFrontOfMe == Places.DANCEFLOOR) {
				danceFloorFound = true;
				return Direction.FORWARD;
			}
			heading = Heading.WEST;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else {
			inFrontOfMe = getWhatISee()[1];

			if (danceFloorFound) {
				return dancingAlgo();
			} else if (isUsable(inFrontOfMe)) {

				headingWest = false;
				headingSouth = false;
				headingEast = false;
				headingNorth = false;

				if (heading == Heading.WEST)
					x--;
				if (heading == Heading.SOUTH)
					y++;
				if (heading == Heading.EAST)
					x++;
				if (heading == Heading.NORTH)
					y--;
				return Direction.FORWARD;
			} else {
				return Direction.TURN_RIGHT_ON_SPOT;

			}

		}

	}
}
