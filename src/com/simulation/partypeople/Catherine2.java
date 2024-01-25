///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Catherine2.java
// Description: Template for the people
//
// - my code
// - my strategy
// - my overall impressions of the simulation
// - my map
/* 
*/

///////////////////////////////////////////////////////////////////////////////


package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;
import java.util.HashMap;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import com.simulation.enums.Heading;

public class Catherine2 extends Avatar{

	public boolean amOnDanceFloor = false;
	public BeverageType beverageOfChoice = BeverageType.WATER;
	
	public int x;
	public int y;
	Coordinate currentCoordinate = new Coordinate(0, 0);

	public Heading heading = Heading.WEST;
	public Direction[] directions = new Direction[]{Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT, Direction.FORWARD};
	public int headingIndex = 3;

	private int turnAroundTimeUnit = 0;
	private int dancingTimeUnit = 0;
	// This is used to keep track of the previously moved direction. 
    private int directionIndex = 0; 

	// Three global boolean variables to represent if my goals: goToDancefloor, goToBar, goToLounge. Initially, only goToDancefloor is 
	// true. After I get to the dancefloor, goToDancefloor is false and goToBar is true, and if I see the Dancefloor, I turn around. 
	// After I get to the bar, goToBar is false and goToLounge is true. After I get to the lounge, goToLounge is false. If all three 
	// variables are false, I stay idle.

	private boolean goToDancefloor = true;
	private boolean goToBar = false;
	private boolean goToLounge = false;

	// Arrays of places. 
	private Places loungePlaces[] = {Places.LOUNGE_BIG, Places.LOUNGE_SMALL, Places.LOUNGE_SMOKING};
	private Places barPlaces[] = {Places.BAR, Places.BAR_CHAIR};
	private Places availablePlacesToMove[] = {Places.LOUNGE_BIG, Places.LOUNGE_SMALL, Places.LOUNGE_SMOKING, Places.PATH, Places.FUSSBALL_CHAIR, Places.BAR_CHAIR, Places.POOL_CHAIR, Places.DANCEFLOOR};
	
	// Map containing key-value pairs of coordinates and places. I made a Coordinate class to store the x and y coordinates. If my 
	// direction is Forward and the next place I see is available to move to (example: not a Person, not the DJ). For example, if the 
	// next place is a Path, Chair, or Dancefloor. Then I update the coordinate by incrementing or decrementing x or y.
	private HashMap<Coordinate, Places> mentalMapFuturePlaces = new HashMap<Coordinate, Places>(); 

	// Map of the Lounges and their coordinates
	private HashMap<Coordinate, Places> mentalMapLounges = new HashMap<Coordinate, Places>(); 

	// Map of the Bar and Bar Chairs and their coordinates
	private HashMap<Coordinate, Places> mentalMapBar = new HashMap<Coordinate, Places>(); 

	private static class Coordinate {
		private int xCoord;
		private int yCoord;

		public Coordinate(int xCoord, int yCoord) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }
		public int getXCoord() {
			return xCoord;
		}
		public int getYCoord() {
			return yCoord;
		}
		public int[] getCoords() {
			int[] coords = {xCoord, yCoord};
			return coords;
		}
		public void setCoords(int xCoord, int yCoord) {
			this.xCoord = xCoord;
			this.yCoord = yCoord;
		}
	}
	// Only place a new coordinate and place on the map if that coordinate isn't already in the map 
	private void updateMentalMap(String mapName, HashMap<Coordinate, Places> map, Coordinate coordinate, Places[] placeToLog) {
		if (placeToLog[1] != Places.PERSON) {
			// System.out.println(mapName + " -- Coordinate: (" + coordinate.getXCoord() + ", " + coordinate.getYCoord() + "), Current Place: " + placeToLog[0] + ", Next Place: " + placeToLog[1]);
		}
		if (!map.containsKey(coordinate) && placeToLog[1] != Places.PERSON) {
			map.put(coordinate, placeToLog[1]);
		} 
	}

	// In order to create coordinates, I started out wherever I begin (on the right-side wall) as being (0,0). The Heading started out being
	// East becaus the first thing I do is go Forward. Then I either incremented or decremented the x or y value of Coordinate depending on the 
	// Heading value.
	private Coordinate updateCurrentPosition(Heading heading, Coordinate currCoordinate) {
		int currX = currCoordinate.getXCoord();
		int currY = currCoordinate.getYCoord();
		if (heading == Heading.NORTH) {
			currCoordinate.setCoords(currX, currY + 1);
		}
		else if (heading == Heading.SOUTH) {
			currCoordinate.setCoords(currX, currY - 1);
		}
		else if (heading == Heading.EAST) {
			currCoordinate.setCoords(currX + 1, currY);
		}
		else if (heading == Heading.WEST) {
			currCoordinate.setCoords(currX - 1, currY);
		}
		// System.out.println(heading);
		return currCoordinate;
	}

	// ************** Constructor **************
	public Catherine2(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
	}

// Turn left or right on the spot. I realized that the Heading enum lists the headings in a clockwise direction, 
// so I made a method that would turn left or right on the spot by incrementing or decrementing the index of the Heading enum. After
// incrementing or decrementing the index, I did "mod 4" on it so that it would be within the range of 0...3 which is the valid range of 
// indexes for the Heading enum. The directionIndex is also updated, so that I can still alternate between changing directions
// to forward or left/right.
	private Direction turnLeftInPlace() {
		Direction dir = Direction.TURN_LEFT_ON_SPOT;
		headingIndex = (headingIndex - 1 + Heading.values().length) % Heading.values().length;
		heading = Heading.values()[headingIndex];
		directionIndex = 1;
		return dir;
	}

	private Direction turnRightInPlace() {
		Direction dir = Direction.TURN_RIGHT_ON_SPOT;
		headingIndex = (headingIndex + 1) % Heading.values().length;
		heading = Heading.values()[headingIndex];
		directionIndex = 0;
		return dir;
	}

	// Turn around on the spot by turning left 3 times, then turning right once, then going Forward. The 
	// purpose of this is to turn around if the Avatar's "next place" is a place to avoid (Outside, Wall, Person, not the Dancefloor or 
	// Bar or Lounge at various times).This function has its own internal time counter, so that I can keep doing this sequence of actions
	// during the course of multiple moveAvatar() cycles.
	private Direction turnAroundOnSpot() {
		Direction dir;
		turnAroundTimeUnit++;
		if (turnAroundTimeUnit < 4) {
			dir = turnLeftInPlace();
		} else if (turnAroundTimeUnit == 4) {
			dir = turnRightInPlace();
		} else {
			dir = Direction.FORWARD;
			turnAroundTimeUnit = 0;
		}
		return dir;
	}

	public Direction dancingAlgorithm(Places[] places) {
		Direction dancingDirection = Direction.FORWARD; 
		dancingTimeUnit++;
		if (dancingTimeUnit == 1) {
			System.out.println("Catherine is at the " + places[0]);
			dancingDirection = turnAroundOnSpot();
			goToDancefloor = false;
			goToBar = true;
			dancingTimeUnit = 0;
		}	
		// If I run into a Person, I turn around. This was to be proactive and at least know that I was moving away, because I can't control how 
		// the other Person reacts.		
		if (places[1] == Places.PERSON) {
			dancingDirection = turnAroundOnSpot();
		}
		else if (places[0] != Places.DANCEFLOOR) {
			amOnDanceFloor = false;
		} 
		return dancingDirection;
	}

	
	private boolean isIncludedInArray(Places place, Places[] placesArray) {
		for (Places p: placesArray) {
			if (p == place) {
				return true;
			}
		}
		return false;
	}


	private int containsCoordinate(String typeOfCoord, int coord, HashMap<Coordinate, Places> coordinateMap) {
		for (Coordinate coordinate : coordinateMap.keySet()) {
			int x = coordinate.getXCoord();
			int y = coordinate.getYCoord();
			if (typeOfCoord == "x" && coord == x) {
				return (coord - x);
			}
			else if (typeOfCoord == "y" && coord == y) {
				return (coord - y);
			}
		}
		return -1;
	}

	
	public void talk() {
	}

	public void talkToPerson(Avatar person) {
	}

	public void playPool() { 
	}

	public void playFussball() { 
	}

	public void drink(BeverageType type) {	
	}

	public Direction moveAvatar() { 
		Direction direction = Direction.FORWARD; 
		Places[] mySurroundings = getWhatISee();
		// First, look for the dancefloor
		if (goToDancefloor && !goToBar && !goToLounge && mySurroundings[0] == Places.DANCEFLOOR) {
			amOnDanceFloor = true;			
			direction = this.dancingAlgorithm(mySurroundings);
		}
		// Then, look for bar to stand at the bar, or a bar chair
		else if (!goToDancefloor && goToBar && !goToLounge && isIncludedInArray(mySurroundings[1], barPlaces)) {
			direction = Direction.FORWARD;
			System.out.println("Catherine is at the " + mySurroundings[1]);
			goToBar = false;
			goToLounge = true;
		}
		// Then, look for a lounge
		else if (!goToDancefloor && !goToBar && goToLounge && isIncludedInArray(mySurroundings[1], loungePlaces)) {
			direction = Direction.FORWARD;
			System.out.println("Catherine is done and in the " + mySurroundings[1]);
			goToLounge = false;
		}
		// If I've already visited the dancefloor or the bar and I see the dancefloor or a bar chair, turn away from it 
		// (I'm already avoiding the Bar itself, so I don't need to avoid it again)
		else if ((!goToDancefloor && mySurroundings[1] == Places.DANCEFLOOR) || (goToLounge && mySurroundings[1] == Places.BAR_CHAIR)) {
			direction = turnRightInPlace();
		}	
		// If I'm facing an unavailable place, turn left. Don't turn around, because then I may get stuck in between two places, turning
		// around, facing another unavailable place, turning around, and so on
		else if ((goToDancefloor || goToBar || goToLounge) && !isIncludedInArray(mySurroundings[1], availablePlacesToMove)) {
			direction = turnLeftInPlace();
		}	
		// If all my goals are achieved, stay idle
		else if (!goToDancefloor && !goToBar && !goToLounge) {
			direction = Direction.IDLE;
		}
		// Otherwise, if I have a goal left and I don't see a place to avoid, randomely change direction
		else {
			amOnDanceFloor = false;				
			// Move randomly, but don't change direction very often. I don't want to bop around too much. I want to go in straight lines.
			Random randomNumber = new Random();
			// I used a random number generator to decide if I should change direction. I only want to change direction 10% of the 
			// time. Instead of using a loop to count, which would mean I'd change direction exactly every n cycles. I wanted it to 
			// be random since I don't start out knowing the map, so I don't know how far or near places are to each other.
			int randInt = randomNumber.nextInt(10);
			if (randInt == 1) {
				direction = moveRandomDirection(); 
			}
		} 			
		// Only update my coordinates and thus the mental map if I have actually moved forward to a new place.
		if (direction == Direction.FORWARD && isIncludedInArray(mySurroundings[1], availablePlacesToMove)) {
			currentCoordinate = updateCurrentPosition(heading, currentCoordinate);
			updateMentalMap("Map    ", mentalMapFuturePlaces, currentCoordinate, mySurroundings);
			if (isIncludedInArray(mySurroundings[1], loungePlaces)) {
				updateMentalMap("Lounges", mentalMapLounges, currentCoordinate, mySurroundings);
			}
			if (isIncludedInArray(mySurroundings[1], barPlaces)) {
				updateMentalMap("Bar    ", mentalMapBar, currentCoordinate, mySurroundings);
			}
		}
		// System.out.println(direction);
		return direction;
	}

	// I realized it was easier if the avatar always was going in the same direction, so I had my avatar only going in Direction.FORWARD, and 
	// just turned left or right on the spot in order to change direction. This simplified my Heading code because I only had to check for 
	// Heading with regards to one direction, "Forward". If Heading is North, then decrement y by 1 because I know I'm going forward. 
	private Direction moveRandomDirection() {
		Random randomNumber = new Random();
		int randInt;
		// Alternate between moving forward and turning left or right on the spot
		if (directionIndex == 2) {
            randInt = randomNumber.nextInt(2); 
        } else {
            randInt = 2;
        }			
		directionIndex = randInt;
		Direction dir = directions[randInt];
		if (directions[randInt] == Direction.TURN_LEFT_ON_SPOT) {
			dir = turnLeftInPlace();
		}
		else if (directions[randInt] == Direction.TURN_RIGHT_ON_SPOT) {
			dir = turnRightInPlace();
		}
		return dir;
	}
}