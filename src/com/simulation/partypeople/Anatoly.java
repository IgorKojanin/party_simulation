///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import java.util.EnumMap;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

import com.simulation.extra.Point;
import com.simulation.extra.Graph;
import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import com.simulation.enums.Heading;
import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Anatoly extends Avatar {

	private Places[] priorityList;
	private EnumMap<Places, Integer> placeCounters = new EnumMap<>(Places.class); // Counter for each place
	private EnumMap<Places, Integer> maxTimeAtPlaces = new EnumMap<>(Places.class); // Max time to spend at each place

	private Heading headed = Heading.WEST;
	private Heading headedChange = Heading.WEST;

	// private MapFrame mapFrame;
	private MapFrame mapFrame;
	private Graph myMapGraph;
	private Direction resetDir = Direction.FORWARD;

	private int currentX = 0;
	private int currentY = 0;

	private boolean hasDanced = false; // Flag to indicate if the avatar has previously danced
	private int danceCoutner = 0;

	// ************** Constructor **************
	public Anatoly(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

		myMapGraph = new Graph();
		mapFrame = new MapFrame(myMapGraph);
		mapFrame.setVisible(true);

		this.priorityList = new Places[] { Places.LOUNGE_SMOKING, Places.LOUNGE_SMALL, Places.BAR, Places.LOUNGE_BIG,
				Places.TOILET, Places.FUSSBALL };

		for (Places place : Places.values()) {
			placeCounters.put(place, 0);
			maxTimeAtPlaces.put(place, 10);
		}
	}

	public void fight(Avatar opponent) {
		if (opponent.getName() == "JoseLu") {
			System.out.println("You're time is up, Rolda!");
		} else if (opponent.getName() == "Kieran") {
			System.out.println("No way you survive this one, Kieran!");
		}
	}

	public void talk(Avatar person) {
		if (person.getName() == "JoseLu") {
			System.out.println("That is gotta be the worst mustache I have seen in my life!");
		} else if (person.getName() == "Kieran") {
			System.out.println("Howdy-ho");
		}
	}

	public Direction findShortestPathToPlace(Point currentLocation, Places destinationPlace) {
		Queue<Point> queue = new LinkedList<>();
		Map<Point, Point> parentMap = new HashMap<>();
		myMapGraph.resetVisited();

		queue.add(currentLocation);
		currentLocation.setVisited(true);

		while (!queue.isEmpty()) {
			Point current = queue.poll();
			if (current.getPlace() == destinationPlace) {
				return reconstructPath(current, parentMap);
			}

			for (Direction direction : Direction.values()) {
				Point adjacent = current.getAdjacentPoint(direction);
				if (adjacent != null && !adjacent.isVisited() && adjacent.getPlace() != Places.WALL) {
					adjacent.setVisited(true);
					parentMap.put(adjacent, current);
					queue.add(adjacent);
				}
			}
		}

		return Direction.IDLE; // No path found
	}

	private Direction reconstructPath(Point destination, Map<Point, Point> parentMap) {
		Stack<Direction> path = new Stack<>();
		Point current = destination;

		while (parentMap.containsKey(current)) {
			Point parent = parentMap.get(current);
			path.push(getDirectionFromTo(parent, current));
			current = parent;
		}

		return path.isEmpty() ? Direction.IDLE : path.pop(); // Return the next direction to move
	}

	// Method to determine the direction from one point to another
	private Direction getDirectionFromTo(Point from, Point to) {
		if (from.getX() < to.getX()) {
			return Direction.RIGHT;
		} else if (from.getX() > to.getX()) {
			return Direction.LEFT;
		} else if (from.getY() < to.getY()) {
			return Direction.FORWARD;
		} else if (from.getY() > to.getY()) {
			return Direction.BACK;
		}
		return Direction.IDLE;
	}

	public Direction dancingAlgo(Places placeAhead) {

		Direction dancingMovement = Direction.IDLE;

		if (hasDanced) {
			return moveAvatar();
		}
		if (placeAhead != Places.PATH) {
			dancingMovement = Direction.FORWARD;
		} else if (danceCoutner >= 1 && danceCoutner <= 10) {
			dancingMovement = Direction.RIGHT;
		} else {
			hasDanced = true;
		}
		danceCoutner++;
		return dancingMovement;
	}

	public void toilet(int timeInToilet) {
	}

	public void playPool() {
	}

	public void playFussball() {
	}

	public void startDrinking() {
	}

	public Direction moveRand() {
		Direction dir = Direction.IDLE;
		switch (random()) {
			case 0:
				dir = Direction.FORWARD;
				break;
			case 1:
				dir = Direction.TURN_RIGHT_ON_SPOT;
				break;
			case 2:
				dir = Direction.BACK;
				break;
			case 3:
				dir = Direction.TURN_LEFT_ON_SPOT;
				break;
		}
		return dir;
	}

	public int random() {
		Random rand = new Random();
		return rand.nextInt(4);
	}

	private void changeHeading() {
		if (resetDir == Direction.TURN_RIGHT_ON_SPOT) {
			switch (headed) {
				case EAST:
					headed = Heading.SOUTH;
					break;
				case NORTH:
					headed = Heading.EAST;
					break;
				case SOUTH:
					headed = Heading.WEST;
					break;
				case WEST:
					headed = Heading.NORTH;
					break;
				default:
					break;

			}
		}

		if (resetDir == Direction.TURN_LEFT_ON_SPOT) {
			switch (headed) {
				case EAST:
					headed = Heading.NORTH;
					break;
				case NORTH:
					headed = Heading.WEST;
					break;
				case SOUTH:
					headed = Heading.EAST;
					break;
				case WEST:
					headed = Heading.SOUTH;
					break;
				default:
					break;

			}
		}

		if (resetDir == Direction.FORWARD) {
			switch (headed) {
				case EAST:
					headed = Heading.EAST;
					headedChange = Heading.EAST;
					currentY++;
					break;
				case NORTH:
					headed = Heading.NORTH;
					headedChange = Heading.NORTH;
					currentX++;
					break;
				case SOUTH:
					headed = Heading.SOUTH;
					headedChange = Heading.SOUTH;
					currentX--;
					break;
				case WEST:
					headed = Heading.WEST;
					headedChange = Heading.WEST;
					currentY--;
					break;
				default:
					break;

			}
		}

		if (resetDir == Direction.RIGHT) {
			switch (headed) {
				case EAST:
					headed = Heading.SOUTH;
					headedChange = Heading.SOUTH;
					currentX--;
					break;
				case NORTH:
					headed = Heading.EAST;
					headedChange = Heading.EAST;
					currentY++;
					break;
				case SOUTH:
					headed = Heading.WEST;
					headedChange = Heading.WEST;
					currentY--;
					break;
				case WEST:
					headed = Heading.NORTH;
					headedChange = Heading.NORTH;
					currentX++;
					break;
				default:
					break;

			}
		}

		if (resetDir == Direction.LEFT) {
			switch (headed) {
				case EAST:
					headed = Heading.NORTH;
					headedChange = Heading.NORTH;
					currentX++;
					break;
				case NORTH:
					headed = Heading.EAST;
					headedChange = Heading.EAST;
					currentY++;
					break;
				case SOUTH:
					headed = Heading.WEST;
					headedChange = Heading.WEST;
					currentY--;
					break;
				case WEST:
					headed = Heading.SOUTH;
					headedChange = Heading.SOUTH;
					currentX--;
					break;
				default:
					break;

			}
		}

		if (resetDir == Direction.BACK) {
			switch (headed) {
				case EAST:
					headed = Heading.WEST;
					headedChange = Heading.WEST;
					currentY--;
					break;
				case NORTH:
					headed = Heading.SOUTH;
					headedChange = Heading.SOUTH;
					currentX--;
					break;
				case SOUTH:
					headed = Heading.NORTH;
					headedChange = Heading.NORTH;
					currentX++;
					break;
				case WEST:
					headed = Heading.EAST;
					headedChange = Heading.EAST;
					currentY++;
					break;
				default:
					break;

			}
		}
	}

	public void saveGraphToCSV(String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Write header
			writer.write("X,Y,Place\n");

			// Iterate through each point in the graph
			for (Point point : myMapGraph.getPoints()) {
				String line = point.getX() + "," + point.getY() + "," + point.getPlace().name() + "\n";
				writer.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Direction moveAvatar() {
		changeHeading();

		Places place_ahead = getWhatISee()[1];
		Places place_standing = getWhatISee()[0];
		// Thread.sleep(500);

		if (resetDir != Direction.TURN_LEFT_ON_SPOT && resetDir != Direction.TURN_RIGHT_ON_SPOT
				&& (place_ahead == Places.PATH || place_ahead != Places.DANCEFLOOR || place_ahead != Places.LOUNGE_BIG
						|| place_ahead != Places.LOUNGE_SMALL
						|| place_ahead == Places.BAR_CHAIR || place_ahead == Places.FUSSBALL_CHAIR
						|| place_ahead == Places.TOILET_CHAIR || place_ahead == Places.DJ)) {
			Point newPoint = new Point(currentX, currentY);

			// Check if the point already exists in the graph
			Point existingPoint = myMapGraph.getPoint(newPoint);
			if (existingPoint != null && newPoint.isVisited() == false) {
				// If it exists, update the existing point
				existingPoint.setVisited(true);
				// Add any other updates here, if necessary
			} else {
				// If not, set the new point as visited and add it to the graph
				newPoint.setVisited(true);
				newPoint.setPlace(place_standing);
				myMapGraph.addPoint(newPoint);
			}

		}

		// System.out.println(myMapGraph);

		boolean dancefloorDiscovered = myMapGraph.isPlaceDiscovered(Places.DANCEFLOOR);

		if (place_standing == Places.LOUNGE_SMALL && dancefloorDiscovered && (currentY < 32 && currentY > -6)
				&& (currentX < 6 && currentX > -18)) {
			Direction dancefloorDirection = findShortestPathToPlace(new Point(currentX, currentY), Places.DANCEFLOOR);
			if (dancefloorDirection != Direction.IDLE) {
				return dancefloorDirection;
			}
		}

		for (Places priorityPlace : priorityList) {
			if (place_standing == priorityPlace) {
				int currentTime = placeCounters.get(place_standing);
				if (currentTime < maxTimeAtPlaces.get(place_standing)) {
					placeCounters.put(place_standing, currentTime + 50); // Increment counter
					return Direction.IDLE; // Stay in place
				} else {
					placeCounters.put(place_standing, 0); // Reset counter
					break; // Break to resume random movement
				}
			}
		}

		if (myMapGraph.getMaxY() == 31 && myMapGraph.getMaxY() == -5 && myMapGraph.getMaxX() == 5
				&& myMapGraph.getMinX() == -17) {
			resetDir = Direction.IDLE;
		} else {
			resetDir = moveRand();
		}

		mapFrame.updatePanel(myMapGraph);

		switch (place_ahead) {
			case BAR:
				if (myMapGraph.isPlaceDiscovered(Places.BAR)) {
					super.drink(BeverageType.BEER);
				} else {
					super.drink(BeverageType.VODKA);
				}
				break;
			case POOL:
				break;
			case TOILET:
				break;
			case TOILET_CHAIR:
				// if (dancefloorDiscovered) {
				// Direction directionToDancefloor = findShortestPathToPlace(new Point(currentX,
				// currentY),
				// Places.DANCEFLOOR);
				// if (directionToDancefloor != Direction.IDLE) {
				// resetDir = directionToDancefloor;
				// }
				// } else {
				// resetDir = moveRand(); // Existing random movement if dancefloor not
				// discovered
				// }
				break;
			case DANCEFLOOR:
				if (hasDanced == false) {
					return dancingAlgo(place_ahead);
				}
				break;
			case LOUNGE_BIG:
				// if (dancefloorDiscovered) {
				// Direction directionToDancefloor = findShortestPathToPlace(new Point(currentX,
				// currentY),
				// Places.DANCEFLOOR);
				// if (directionToDancefloor != Direction.IDLE) {
				// resetDir = directionToDancefloor;
				// }
				// } else {
				// resetDir = moveRand(); // Existing random movement if dancefloor not
				// discovered
				// }
				break;
			case LOUNGE_SMALL:
				// if (dancefloorDiscovered) {
				// Direction directionToDancefloor = findShortestPathToPlace(new Point(currentX,
				// currentY),
				// Places.DANCEFLOOR);
				// if (directionToDancefloor != Direction.IDLE) {
				// resetDir = directionToDancefloor;
				// }
				// } else {
				// resetDir = moveRand(); // Existing random movement if dancefloor not
				// discovered
				// }
				break;
			case DJ:

				break;
			case BOUNCER:
				break;
			case PATH:
				break;
			case WALL:
				break;
			case PERSON:
				break;
			case QUEUE:
				break;
			default:
				break;
		}

		saveGraphToCSV("./misc/Map.csv");

		return resetDir;
	}
}
