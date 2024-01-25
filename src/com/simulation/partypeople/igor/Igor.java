package com.simulation.partypeople.igor;

import java.awt.Color;

import com.simulation.avatar.Avatar;
import com.simulation.enums.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Igor extends Avatar {
	private boolean printMap = false;
	private boolean printVertices = false;

	// ************** Constructor **************
	public Igor(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO
	}

	// ************** Methods **************
	private boolean forward = false;
	private boolean forwardTwice = false;
	private boolean sideOnce = false;
	private boolean sideTwice = false;
	private boolean sideThrice = false;
	private boolean otherSide = false;
	private boolean finalForward = false;
	private boolean turnLeft = false;

	private int xPos = 0;
	private int yPos = 0;
	private Heading lookingDirection = Heading.WEST; // 0 - north, 1 - east, 2 - south, 3 - west
	private squareInfo[][] map = { { new squareInfo(0, Places.PATH) } };
	private int nextSquareIdInPath;
	private boolean goingToNewDestination = false; // A variable indicating weather we are going to a new destination or
													// continuing exploring the map
	private Vertex destinationVer;
	private Graph mapGraph = new Graph();
	private boolean firstStepInClub = true; // Helping graph first time
	private boolean dancing = false; // indicating that the avatar is dancing
	private int dancingSteps = 6;
	// Finding the path
	private boolean notDancedYet = true; // Avatar needs to dance only once
	private boolean doneBeingAtTheBar = false; // Avatar was at the bar
	private boolean doneBeingAtTheLounge = false; // avatar was at the lounge
	private boolean finishedGoingToNewPlace = false; // after going to a new place -> look to all 4 directions to create
														// a amp around you
	private int xPrevPos = 0; // in case Avatar did not move due to simulation but he thinks he moved -> undo
								// the position update
	private int yPrevPos = 0;
	private Heading prevLookingDirection = lookingDirection;
	private Direction lastMovingDirection; // Track of previous moving direction
	private boolean firstTimeInCl = true;
	private int countCouldntMove = 0; // count how many times the avatar could not move in a row-> if higher than 2
										// than we cannot go left or right (i.e toilet) -> turn left on spot
	private boolean movedRight = false; // to know if to move next to the left or to the right
	private int countMovedRight = 0; // change order of left and right once in a while

	public Direction dancingAlgo() {
		if (!forward) {
			movedRight = !movedRight;
			forward = true;
			updateAvatarsPos();
			return Direction.FORWARD;
		}
		if (!forwardTwice) {
			forwardTwice = true;
			updateAvatarsPos();
			return Direction.FORWARD;
		}
		if (!sideOnce) {
			sideOnce = true;
			setHeadingTurnLeft();
			updateAvatarsPos();
			return Direction.LEFT;
		}
		if (!sideTwice) {
			sideTwice = true;
			setHeadingTurnLeft();
			updateAvatarsPos();
			return Direction.LEFT;
		}
		if (!sideThrice) {
			sideThrice = true;
			setHeadingTurnLeft();
			updateAvatarsPos();
			return Direction.LEFT;
		}
		if (!otherSide) {
			otherSide = true;
			setHeadingTurnRight();
			updateAvatarsPos();
			return Direction.RIGHT;
		}
		if (!finalForward) {
			finalForward = true;
			updateAvatarsPos();
			return Direction.FORWARD;
		}
		if (!turnLeft) {
			turnLeft = true;
			setHeadingTurnLeft();
			return Direction.TURN_LEFT_ON_SPOT;
		}
		updateAvatarsPos();
		return Direction.FORWARD;
	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
	}

	public void talk(Avatar person) {
	}

	public void smoke() {
	}

	public void toilet(int timeInToilet) { // Do only toilet things in the toilet
	}

	public void playPool() { // Play pool only on the designate spot
	}

	public void playFussball() { // Play Fussball only on the designate spot
	}

	// A list to save all usable places that the avatar can use
	List<Places> usablePlaces = Arrays.asList(Places.PATH, Places.BAR_CHAIR, Places.LOUNGE_BIG, Places.LOUNGE_SMALL);

	// A function for knowing if a place in the map is usable or not
	private boolean isUsable(Places place) {
		if (usablePlaces.contains(place))
			return true;
		else
			return false;
	}

	// Info about each square of the map
	private static class squareInfo {
		private int sqaureId = -1; // default is -1 -> not seen this square yet
		private Places place;

		private squareInfo(int vertexId, Places place) {
			this.sqaureId = vertexId;
			this.place = place;
		}

		private int getSquareId() {
			return sqaureId;
		}

		private Places getPlace() {
			return place;
		}
	}

	public Direction moveAvatar() {
    	Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
		// Finished The Path
		if (doneBeingAtTheLounge) { // Reached Lounge -> chill here
			return Direction.TURN_RIGHT_ON_SPOT;
		}
		// in case Avatar did not move due to simulation but he thinks he moved -> undo
		// the position update
		if (!isHasMoved() && lastMovingDirection != Direction.TURN_LEFT_ON_SPOT
				&& lastMovingDirection != Direction.TURN_RIGHT_ON_SPOT && !firstTimeInCl) {
			undoPositionUpdate();
		} else { // moved successfully
			countCouldntMove = 0;
			if (getWhatISee()[1] == Places.OUTSIDE) // Avatar is outside of the club
				return handleOutsideOfClub();
			updateMapIfNeeded();
			if (getMapCurrentSquare().getSquareId() == -1 || firstTimeInCl) { // Add new vertex only if the square
																				// avatar stands on is new
				if (firstTimeInCl)
					firstTimeInCl = false;
				createNewVertexCurrLoc();
			}
		}

		if (printMap) {
			displayMap(map);
		}

		Places whatISee;
		Direction dir;
		whatISee = getWhatISee()[1];
		if (dancing && dancingSteps > 0) { // dancing
			dancingSteps--;
			return dancingAlgo();
		}

		if (!finishedGoingToNewPlace) {
			if (whatISee == Places.DANCEFLOOR && notDancedYet) {
				notDancedYet = false;
				dancing = true;
				return dancingAlgo();
			}
			findPlacesToWin();
		}

		if (goingToNewDestination) { // We are currently going to a new space and not exploring the map
			return handleGoingToNewDestination();
		}

		// Create vertex for square avatar is looking at
		handleCreatingVertexForSquare();

		finishedGoingToNewPlace = false;
		dir = goToNextPlace(whatISee);
		if (dir != Direction.TURN_LEFT_ON_SPOT && dir != Direction.TURN_RIGHT_ON_SPOT) { // if we turn on spot we don't
																							// update the position in
																							// map -> avatar stays in
																							// the same place
			updateAvatarsPos();
		}
		if (firstStepInClub)
			firstStepInClub = false;
		lastMovingDirection = dir;
		return dir;
	}
	
	
	
	// The path that we are looking for is DanceFloor -> Bar_Chair -> Lounge
	private void findPlacesToWin() {
		if ((findPlaceInMap(map, Places.BAR_CHAIR) != -1 || findPlaceInMap(map, Places.BAR) != -1) && !notDancedYet
				&& !doneBeingAtTheBar) { // done dancing -> did I see bar chair
			addNeighbors(xPos, yPos);
			if (getWhatISee()[0] == Places.BAR_CHAIR || getWhatISee()[1] == Places.BAR) { // currently at the bar -> no
																							// need to walk there
				doneBeingAtTheBar = true;
			} else {
				if (findPlaceInMap(map, Places.BAR_CHAIR) != -1)
					destinationVer = mapGraph.getVertex(findPlaceInMap(map, Places.BAR_CHAIR));
				else
					destinationVer = mapGraph.getVertex(findPlaceInMap(map, Places.BAR));
				goingToNewDestination = true;
			}
		} else if ((findPlaceInMap(map, Places.LOUNGE_SMALL) != -1 || findPlaceInMap(map, Places.LOUNGE_BIG) != -1)
				&& !notDancedYet && doneBeingAtTheBar && !doneBeingAtTheLounge) { // done dancing -> done being at the
																					// bar chair -> found lounge -> go
																					// to lounge
			addNeighbors(xPos, yPos);
			if (getWhatISee()[0] == Places.LOUNGE_BIG || getWhatISee()[0] == Places.LOUNGE_SMALL) { // currently at the
																									// bar -> no need to
																									// walk there
				doneBeingAtTheLounge = true;
			}
			if (findPlaceInMap(map, Places.LOUNGE_BIG) != -1)
				destinationVer = mapGraph.getVertex(findPlaceInMap(map, Places.LOUNGE_BIG));
			else
				destinationVer = mapGraph.getVertex(findPlaceInMap(map, Places.LOUNGE_SMALL));
			goingToNewDestination = true;
		}
	}

	private Direction handleGoingToNewDestination() {
		Vertex currentSquareVertex = mapGraph.getVertex(getMapCurrentSquare().getSquareId());
		nextSquareIdInPath = mapGraph.nextVertexIdInShortestPath(currentSquareVertex, destinationVer);
		squareInfo whatISeeSq = getMapWhatISee();

		if (whatISeeSq != null) {
			if (getMapWhatISee().getSquareId() == nextSquareIdInPath) {
				if (destinationVer.getKey() == getMapWhatISee().getSquareId()) {
					goingToNewDestination = false;
					handleNewDestinationReached();
				}
				updateAvatarsPos();
				lastMovingDirection = Direction.FORWARD;
				return Direction.FORWARD;
			} else {
				setHeadingTurnRight();
				lastMovingDirection = Direction.TURN_RIGHT_ON_SPOT;
				return Direction.TURN_RIGHT_ON_SPOT;
			}
		} else {
			setHeadingTurnRight();
			lastMovingDirection = Direction.TURN_RIGHT_ON_SPOT;
			return Direction.TURN_RIGHT_ON_SPOT;
		}
	}

	private void handleCreatingVertexForSquare() {
		checkAddMapRowsOrColumns(lookingDirection);
		if (getMapWhatISee().getSquareId() == -1) {
			Vertex v0 = new Vertex(getWhatISee()[1]);
			mapGraph.add(v0);
			setMapWhatISee(new squareInfo(v0.getKey(), getWhatISee()[1]));
			if (isUsable(getWhatISee()[1])) {
				mapGraph.getVertex(getMapCurrentSquare().getSquareId()).addNeighbor(v0);
			}
			addAllNeighborsToVertex(xPos, yPos);
			if (printVertices) {
				mapGraph.print();
			}
		}
	}

	private void handleNewDestinationReached() {
		if (getMapWhatISee().getPlace() == Places.BAR_CHAIR || getMapWhatISee().getPlace() == Places.BAR) {
			doneBeingAtTheBar = true;
		}
		if (getMapWhatISee().getPlace() == Places.LOUNGE_BIG || getMapWhatISee().getPlace() == Places.LOUNGE_SMALL) {
			doneBeingAtTheLounge = true;
		}
		finishedGoingToNewPlace = true;
	}

	private void undoPositionUpdate() {
		xPos = xPrevPos;
		yPos = yPrevPos;
		lookingDirection = prevLookingDirection;
		countCouldntMove++;
		if (countCouldntMove > 2) {
			setHeadingTurnLeft();
			lastMovingDirection = Direction.TURN_LEFT_ON_SPOT;
		}
	}

	// Create a new vertex for the place avatar currently stands on
	private void createNewVertexCurrLoc() {
		Vertex currentVertex = new Vertex(getWhatISee()[0]);
		mapGraph.add(currentVertex);
		currentVertex.setVisited();
		setMapCurrentSquare(new squareInfo(currentVertex.getKey(), getWhatISee()[0]));
	}

	private Direction handleOutsideOfClub() {
		if (getWhatISee()[1] == Places.WALL) {
			return Direction.FORWARD;
		}
		return Direction.TURN_LEFT_ON_SPOT;
	}

	private void addAllNeighborsToVertex(int xPos, int yPos) {
		addNeighbors(xPos, yPos);
		addNeighborsToVertexLookingAt(xPos, yPos);
	}

	private void addNeighborsToVertexLookingAt(int xPos, int yPos) {
		int vertexPosX = xPos;
		int vertexPosY = yPos;
		switch (lookingDirection) {
		case WEST:
			vertexPosX = xPos - 1;
			vertexPosY = yPos;
			break;
		case SOUTH:
			vertexPosX = xPos;
			vertexPosY = yPos + 1;
			break;
		case EAST:
			vertexPosX = xPos + 1;
			vertexPosY = yPos;
			break;
		case NORTH:
			vertexPosX = xPos;
			vertexPosY = yPos - 1;
			break;
		}
		addNeighbors(vertexPosX, vertexPosY);
	}

	// Add neighbors to current Vertex avatar is standing on
	private void addNeighbors(int vertexPosX, int vertexPosY) {
		if (vertexPosX - 1 >= 0) {
			if (map[vertexPosY][vertexPosX - 1].getSquareId() != -1 && isUsable(map[vertexPosY][vertexPosX].getPlace())
					&& isUsable(map[vertexPosY][vertexPosX - 1].getPlace())) {
				mapGraph.getVertex(map[vertexPosY][vertexPosX - 1].getSquareId())
						.addNeighbor(mapGraph.getVertex(map[vertexPosY][vertexPosX].getSquareId()));
			}
		}

		// Check bottom neighbor
		if (vertexPosY + 1 < map.length) {
			if (map[vertexPosY + 1][vertexPosX].getSquareId() != -1 && isUsable(map[vertexPosY][vertexPosX].getPlace())
					&& isUsable(map[vertexPosY + 1][vertexPosX].getPlace())) {
				mapGraph.getVertex(map[vertexPosY + 1][vertexPosX].getSquareId())
						.addNeighbor(mapGraph.getVertex(map[vertexPosY][vertexPosX].getSquareId()));
			}
		}

		// Check right neighbor
		if (vertexPosX + 1 < map[0].length) {
			if (map[vertexPosY][vertexPosX + 1].getSquareId() != -1 && isUsable(map[vertexPosY][vertexPosX].getPlace())
					&& isUsable(map[vertexPosY][vertexPosX + 1].getPlace())) {
				mapGraph.getVertex(map[vertexPosY][vertexPosX + 1].getSquareId())
						.addNeighbor(mapGraph.getVertex(map[vertexPosY][vertexPosX].getSquareId()));
			}
		}

		// Check top neighbor
		if (vertexPosY - 1 >= 0) {
			if (map[vertexPosY - 1][vertexPosX].getSquareId() != -1 && isUsable(map[vertexPosY][vertexPosX].getPlace())
					&& isUsable(map[vertexPosY - 1][vertexPosX].getPlace())) {
				mapGraph.getVertex(map[vertexPosY - 1][vertexPosX].getSquareId())
						.addNeighbor(mapGraph.getVertex(map[vertexPosY][vertexPosX].getSquareId()));
			}
		}
	}

	// Add new columns or rows depends on current location
	private void updateMapIfNeeded() {
		if (yPos == -1) {
			map = updateMapNewRowUp(map, xPos, yPos);
			yPos = yPos + 1;
		}
		if (xPos == -1) {
			map = updateMapNewColumnLeft(map, xPos, yPos);
			xPos = xPos + 1;
		}
		if (yPos == map.length) {
			map = updateMapNewRowDown(map, xPos, yPos);
		}
		if (xPos == map[0].length) {
			map = updateMapNewColumnRight(map, xPos, yPos);
		}
	}

	// Will add more rows or columns to the existing map if we see a new square
	// outside the current map boundaries
	private void checkAddMapRowsOrColumns(Heading lookingDirection) {
		switch (lookingDirection) {
		case WEST:
			if (xPos == 0) { // We are at the edge of the map -> add new row to thw left
				map = updateMapNewColumnLeft(map, xPos, yPos);
				xPos = xPos + 1; // Added a new column left so xPos is updated +1
			}
			break;
		case SOUTH:
			if (yPos + 1 == map.length) {
				map = updateMapNewRowDown(map, xPos, yPos);
			}
			break;
		case EAST:
			if (xPos + 1 == map[0].length) {
				map = updateMapNewColumnRight(map, xPos, yPos);
			}
			break;
		case NORTH:
			if (yPos == 0) {
				map = updateMapNewRowUp(map, xPos, yPos);
				yPos = yPos + 1; // Added a new row up so yPox is updated +1
			}
			break;
		}
	}

	private void setMapWhatISee(squareInfo whatISee) {
		switch (lookingDirection) {
		case WEST:
			map[yPos][xPos - 1] = whatISee; // Add the element that the avatar sees to the map
			break;
		case SOUTH:
			map[yPos + 1][xPos] = whatISee; // Update the element that the avatar sees
			break;
		case EAST:
			map[yPos][xPos + 1] = whatISee; // Update the element that the avatar sees
			break;
		case NORTH:
			map[yPos - 1][xPos] = whatISee;
			break;
		}
	}

	private void setMapCurrentSquare(squareInfo whatISee) {
		map[yPos][xPos] = whatISee;
	}

	// Get the square info that the avatar stands on
	private squareInfo getMapCurrentSquare() {
		return map[yPos][xPos];
	}

	// Get square info of what avatar sees in front of him
	private squareInfo getMapWhatISee() {
		switch (lookingDirection) {
		case WEST:
			if (xPos == 0)
				return null;
			return map[yPos][xPos - 1];
		case SOUTH:
			if (yPos == map.length - 1)
				return null;
			return map[yPos + 1][xPos];
		case EAST:
			if (xPos == map[0].length - 1)
				return null;
			return map[yPos][xPos + 1];
		case NORTH:
			if (yPos == 0)
				return null;
			return map[yPos - 1][xPos];
		}
		return null;
	}

	// Set heading (looking direction of the avatar to look left relative to its
	// prev heading
	private void setHeadingTurnLeft() {
		prevLookingDirection = lookingDirection;
		switch (lookingDirection) {
		case WEST -> lookingDirection = Heading.SOUTH; // looks west turn south
		case SOUTH -> lookingDirection = Heading.EAST; // looks south turn east
		case EAST -> lookingDirection = Heading.NORTH; // looks east turn north
		case NORTH -> lookingDirection = Heading.WEST; // looks north turn west
		}
	}

	// Set heading (looking direction of the avatar to look right relative to its
	// prev heading
	private void setHeadingTurnRight() {
		prevLookingDirection = lookingDirection;
		switch (lookingDirection) {
		case WEST -> lookingDirection = Heading.NORTH;
		case SOUTH -> lookingDirection = Heading.WEST;
		case EAST -> lookingDirection = Heading.SOUTH;
		case NORTH -> lookingDirection = Heading.EAST;
		}
	}

	private int returnBack = 0;

	private Direction goToNextPlace(Places whatISee) {
		countMovedRight++;
		if (countMovedRight > 160) { // change left right oder once in a while -> this is done to avoid falling in a
										// repetitive loop
			countMovedRight = 0;
			movedRight = !movedRight;
		}
		if (getMapCurrentSquare().getPlace() == Places.DANCEFLOOR || getMapCurrentSquare().getPlace() == Places.TOILET
				|| getMapCurrentSquare().getPlace() == Places.TOILET_CHAIR) {
			if (returnBack == 0) {
				returnBack++;
				setHeadingTurnLeft();
				return Direction.TURN_LEFT_ON_SPOT;
			}
			if (returnBack == 1) {
				returnBack++;
				setHeadingTurnLeft();
				return Direction.TURN_LEFT_ON_SPOT;
			}
			if (returnBack == 2) {
				returnBack = 0;
				movedRight = !movedRight;
				return Direction.FORWARD;
			}

		}
		if (movedRight) {
			movedRight = false;
			setHeadingTurnLeft();
			return Direction.LEFT;

		} else { // move Left
			movedRight = true;
			setHeadingTurnRight();
			return Direction.RIGHT;
		}
	}

	// Function to update the map based on what the avatar sees
	private static squareInfo[][] updateMapNewColumnRight(squareInfo[][] oldMap, int positionX, int positionY) {
		int rows = oldMap.length;
		int columns = oldMap[0].length;

		// Create a new map with increased size
		squareInfo[][] newMap = new squareInfo[rows][columns + 1];

		// Copy the old map elements to the new map
		for (int i = 0; i < rows; i++) {
			System.arraycopy(oldMap[i], 0, newMap[i], 0, columns);

			// Set the new column to squareInfo(-1, null)
			newMap[i][columns] = new squareInfo(-1, null);
		}

		return newMap;
	}

	private static squareInfo[][] updateMapNewColumnLeft(squareInfo[][] oldMap, int positionX, int positionY) {
		int rows = oldMap.length;
		int columns = oldMap[0].length;

		// Create a new map with increased size
		squareInfo[][] newMap = new squareInfo[rows][columns + 1];

		// Copy the old map elements to the new map, shifting one position to the right
		for (int i = 0; i < rows; i++) {
			System.arraycopy(oldMap[i], 0, newMap[i], 1, columns);

			// Set the new column to squareInfo(-1, null)
			newMap[i][0] = new squareInfo(-1, null);
		}

		return newMap;
	}

	private static squareInfo[][] updateMapNewRowDown(squareInfo[][] oldMap, int positionX, int positionY) {
		int rows = oldMap.length;
		int columns = oldMap[0].length;

		// Create a new map with increased size
		squareInfo[][] newMap = new squareInfo[rows + 1][columns];

		// Copy the old map elements to the new map
		for (int i = 0; i < rows; i++) {
			System.arraycopy(oldMap[i], 0, newMap[i], 0, columns);
		}

		// Set the new row to squareInfo(-1, null)
		for (int j = 0; j < columns; j++) {
			newMap[rows][j] = new squareInfo(-1, null);
		}

		return newMap;
	}

	private static squareInfo[][] updateMapNewRowUp(squareInfo[][] oldMap, int positionX, int positionY) {
		int rows = oldMap.length;
		int columns = oldMap[0].length;

		// Create a new map with increased size
		squareInfo[][] newMap = new squareInfo[rows + 1][columns];

		// Set the new row to squareInfo(-1, null)
		for (int j = 0; j < columns; j++) {
			newMap[0][j] = new squareInfo(-1, null);
		}

		// Copy the old map elements to the new map, starting from the second row
		for (int i = 1; i < rows + 1; i++) {
			System.arraycopy(oldMap[i - 1], 0, newMap[i], 0, columns);
		}

		return newMap;
	}

	private void updateAvatarsPos() {
		xPrevPos = xPos;
		yPrevPos = yPos;
		switch (lookingDirection) {
		case WEST -> xPos--;
		case SOUTH -> yPos++;
		case EAST -> xPos++;
		case NORTH -> yPos--;
		}
	}

	private static int findPlaceInMap(squareInfo[][] map, Places place) {
		for (squareInfo[] row : map) {
			for (squareInfo square : row) {
				if (square.getPlace() == place) {
					return square.getSquareId();
				}
			}
		}
		// Return -1 if no place is not found
		return -1;
	}

	// Function to display the map
	private static void displayMap(squareInfo[][] map) {
		System.out.println();
		System.out.println();
		for (squareInfo[] row : map) {
			for (squareInfo square : row) {
				String placeString = (square != null && square.getPlace() != null) ? square.getPlace().toString()
						: "null";
				String truncatedString = placeString.substring(0, Math.min(placeString.length(), 4));
				String formattedString = truncatedString + "[" + square.getSquareId() + "]";
				String paddedString = String.format("%-10s", formattedString); // Adjust the padding as needed
				System.out.print(paddedString + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
}
