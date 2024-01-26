package com.simulation.partypeople;

import java.awt.Color;
import java.util.ArrayList;
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

	
	private HashMap<Places, XYPosition> hashMindMap = new HashMap<>();
	private int x = 40;
	private int y = 30;
	private Places[][] mindmap = new Places[100][100];
	private boolean headingWest = false;
	private boolean headingEast = false;
	private boolean headingNorth = false;
	private boolean headingSouth = false;
	private boolean print = false;
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
			if (count < 5) {
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
			if (countBarStay < 10) {
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
//			countLoungeStay += 1;
//			if (countLoungeStay < 10) {
//				return Direction.IDLE;
//			} else {
//				loungeFound = true;
//				return Direction.BACK;
//			}
			return Direction.IDLE;
		} else {
			return moveRandomly();
		}
	}

	private Direction findPlacesRandomly() {
		if (!danceFloorFound) {
			if(print) {
				System.out.println("I'm looking for dance floor");
			}
			return findDanceFloor();
		} else if (danceFloorFound && !barFound) {
			if(print) {
				System.out.println("I'm looking for bar");
			}
			return findBar();
		} else if (danceFloorFound && barFound && !loungeFound) {
			if(print) {
				System.out.println("I'm looking for Lounge");
			}
			return findLounge();
		} else {
			if(print) {
				System.out.println("All three places are found");
			}
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
			return Direction.TURN_RIGHT_ON_SPOT;
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

	public Direction moveAvatar() {
		if(print) {
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		}
		 return findPlacesRandomly();

		
		//return drawMindMap();

	}

	private Direction moveAvatarToDrawMap() {
		Heading heading = Heading.WEST;
		Places iStandOn = getWhatISee()[0];
		Places inFrontOfMe = getWhatISee()[1];
		addPlacesToArray();
		printMindMap();
		if (!headingWest && heading == Heading.WEST) {
			headingWest = true;
			if (heading == Heading.WEST && isUsable(getWhatISee()[1])) {
				updateMindMap(getWhatISee()[1],heading);
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
				updateMindMap(getWhatISee()[1],heading);
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
				updateMindMap(getWhatISee()[1],heading);
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
				updateMindMap(getWhatISee()[1],heading);
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

	Heading heading = Heading.WEST;

	// Direction dir = Direction.FORWARD;
	private Direction drawMindMap() {
		addPlacesToArray();
		//printMindMap();

		if (isUsable(getWhatISee()[1])) {
			switch (heading) {
			case WEST:
				heading = Heading.WEST;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.FORWARD;
			case EAST:
				heading = Heading.EAST;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.FORWARD;
			case NORTH:
				heading = Heading.NORTH;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.FORWARD;
			default:// south
				heading = Heading.SOUTH;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.FORWARD;
			}
		} else {
			switch (heading) {
			case WEST:
				heading = Heading.NORTH;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.TURN_RIGHT_ON_SPOT;
			case EAST:
				heading = Heading.SOUTH;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.TURN_RIGHT_ON_SPOT;
			case NORTH:
				heading = Heading.EAST;
				updateMindMap(getWhatISee()[1], heading);
				return Direction.TURN_RIGHT_ON_SPOT;
			default:
				heading = Heading.WEST;
				dir = Direction.TURN_RIGHT_ON_SPOT;
				updateHeading(dir,heading);
				return Direction.TURN_RIGHT_ON_SPOT;
			}

		}

	}

	private void updateHeading(Direction dir, Heading heading) {
		if (dir == Direction.TURN_LEFT_ON_SPOT) {
			switch (heading) {
			case WEST:
				heading = Heading.SOUTH;
				break;
			case EAST:
				heading = Heading.NORTH;
				break;
			case NORTH:
				heading = Heading.WEST;
				break;
			case SOUTH:
				heading = Heading.EAST;
				break;
			}
		}
		if (dir == Direction.TURN_RIGHT_ON_SPOT) {
			switch (heading) {
			case WEST:
				heading = Heading.NORTH;
				break;
			case EAST:
				heading = Heading.SOUTH;
				break;
			case NORTH:
				heading = Heading.EAST;
				break;
			case SOUTH:
				heading = Heading.WEST;
				break;
			}
		}

	}
Direction dir = Direction.FORWARD;
	private void updateMindMap(Places inFrontOfMe, Heading heading) {
		if (getWhatISee()[1] != Places.PERSON) {
			switch (heading) {
			case WEST:
				if (dir == Direction.FORWARD) {
					x--;
					heading = Heading.WEST;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.RIGHT) {
					y++;
					heading = Heading.SOUTH;
					mindmap[x][y] = getWhatISee()[1];
				}else if (dir == Direction.LEFT) {
					y--;
					heading = Heading.NORTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.BACK) {
					x++;
					heading = Heading.EAST;
					mindmap[x][y] = getWhatISee()[1];
				}
				break;
			case EAST:
				if (dir == Direction.FORWARD) {
					x++;
					heading = Heading.EAST;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.RIGHT) {
					y--;
					heading = Heading.NORTH;
					mindmap[x][y] = getWhatISee()[1];
				}else if (dir == Direction.LEFT) {
					y++;
					heading = Heading.SOUTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.BACK) {
					x--;
					heading = Heading.WEST;
					mindmap[x][y] = getWhatISee()[1];
				}
				x++;
				mindmap[x][y] = getWhatISee()[1];
				break;
			case NORTH:
				if (dir == Direction.FORWARD) {
					y--;
					heading = Heading.NORTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.RIGHT) {
					x++;
					heading = Heading.EAST;
					mindmap[x][y] = getWhatISee()[1];
				}else if (dir == Direction.LEFT) {
					x--;
					heading = Heading.WEST;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.BACK) {
					y++;
					heading = Heading.SOUTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				break;
			case SOUTH:
				if (dir == Direction.FORWARD) {
					y++;
					heading = Heading.SOUTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.RIGHT) {
					x--;
					heading = Heading.WEST;
					mindmap[x][y] = getWhatISee()[1];
				}else if (dir == Direction.LEFT) {
					x++;
					heading = Heading.EAST;
				mindmap[x][y] = getWhatISee()[1];
				}
				else if (dir == Direction.BACK) {
					y--;
					heading = Heading.NORTH;
					mindmap[x][y] = getWhatISee()[1];
				}
				break;
			}
		}
	}
	
	
	 private XYPosition xyPosition = new XYPosition();
	private ArrayList<Places> places = new ArrayList<>();
	private void addPlacesToArray() {
		for (int i = 0; i < mindmap.length; i++) {
			for (int j = 0; j < mindmap.length; j++) {
				if (mindmap[i][j] != null) {
					if (!places.contains(mindmap[i][j])) {
						xyPosition.setXPosition(i);
						xyPosition.setYPosition(j);
						places.add(mindmap[i][j]);
						hashMindMap.put(mindmap[i][j], xyPosition);
					}
				}
			}
		}
		if (print) {
			for (Entry<Places, XYPosition> entry : hashMindMap.entrySet()) {
				XYPosition values = entry.getValue();
				System.out.println(entry.getKey() + ": " + values.getXPosition() + ", " + values.getYPosition());
			}

			 System.out.println(places);
		}
		
	}
	
	private void printMindMap() {
	
	}
	

	private class XYPosition {
		private int xPosition;
		private int yPosition;

		private XYPosition() {

		}

		public int getXPosition() {
			return xPosition;
		}

		public int getYPosition() {
			return yPosition;
		}

		public void setXPosition(int x) {
			this.xPosition = x;
		}

		public void setYPosition(int y) {
			this.yPosition = y;
		}
	}
}
