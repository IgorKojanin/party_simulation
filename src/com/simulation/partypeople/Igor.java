package com.simulation.partypeople;

import java.awt.Color;

import com.simulation.avatar.Avatar;
import com.simulation.enums.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Igor extends Avatar{
	private boolean printTest = false;
	private boolean waitEachStep = false;
	private boolean printMap = true;
	// ToDo individually:
		// - Store surroudings locally
		// - Develop an algorithm to determine your next destination
		// - Develop movement pattern
		// - Develop dancing movement pattern
		// - Develop fighting algorithm with certain fighting skills
		// - Develop prefered drinks list
		// - Develop default phrases to interact with other users of Club Penguin
		// - Develop spiels
		// - Develop smoke area behaviour
		// - Develop skibidi toilet

		// ************** Constructor **************
		public Igor(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
			super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
			// TODO
		}

		// ************** Methods **************
		public void dancingAlgo() {
			// TODO
			// develop the type of movement that would represent your dance pattern

		}

		public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
			// TODO
			// develop different fighting moves
			// be very descriptive (user 2 is performing an F5 on user 3)
		}

		public void talk(Avatar person) {
			// TODO
			// create a list of answers and questions that you would like to exchange with
			// the other users of Club Penguin
			// create a primitive algorithm that would make picks from your answer list
			// based on the questions asked
		}

		public void smoke() {
			// TODO
			// if you are in the smoking area you get prompted the option to smoke
		}

		public void toilet(int timeInToilet) { // Do only toilet things in the toilet
			// TODO
			// set your time in the toilet

		}

		public void playPool() { // Play pool only on the designate spot
			// TODO

		}

		public void playFussball() { // Play Fussball only on the designate spot
			// TODO
			// if two players interact in the fussball area, prompt the option to start a
			// game
			// game algorithm shall be determined externally

		}
		
		// A function for knowing if a place in the map is usable or not
		private boolean isUsable(Places place) {
			//if (place == Places.PATH || place == Places.PERSON)
			if (place == Places.PATH || place == Places.DANCEFLOOR || place == Places.BAR_CHAIR || place == Places.LOUNGE_BIG || place == Places.LOUNGE_SMALL)
				return true;
			else
				return false;
		}
		
		private boolean firstTimeInClub = false; // To track whether the avatar has turned right
//		private boolean foundLeftWall = false;
//		private boolean foundUpperWall = false;
//		private boolean foundRightWall = false;
//		private boolean foundLowerWall = false;
		private boolean lookRight = false;
		private boolean lookFront = false;
		private boolean lookLeft = false;
		private boolean lookBack = false;
		private Places prevGetWhatISee = Places.PATH;
		
		private boolean seeWallFront = false;
		private boolean seeWallRight = false;
		private boolean mapIsComplete = false;
		private boolean usableOnTheRight = true;
		
		private Direction walkToCreateAMap() {
	        Places whatISee;
	        if (!firstTimeInClub) {
	            // If the avatar hasn't turned right yet, do so
	            firstTimeInClub = true; // Set flag to indicate that the avatar has turned right
	            System.out.println("jUST eNTERED tHE cLUB -> tURNING rIGHT");
	            return Direction.TURN_RIGHT_ON_SPOT;
	        } else {
	        	if (!isUsable(prevGetWhatISee)) {
	        		prevGetWhatISee = getWhatISee()[0];
	        		if (isUsable(getWhatISee()[0])) {
		        		System.out.println("Before that I saw an unusble -> move forward");
		        		return Direction.FORWARD;
	        		}
	        		else {
	        			System.out.println("Before that I saw an unusble but front is unsuble as well -> turn left");
		        		return Direction.TURN_LEFT_ON_SPOT;
	        		}
	        	}
	        	else {
	        		if (!lookRight) {
	        			lookRight = true;
	        			whatISee = getWhatISee()[0];
	        			System.out.println(getWhatISee()[0]);
	        			if (whatISee == Places.WALL) {
	        				System.out.println("I see the wall in front of me...");
	        				seeWallFront = true;
	        			}
	        			else
	        				seeWallFront = false;
	        			return Direction.TURN_RIGHT_ON_SPOT;
	        		}
	        		else if (!lookBack) {
	        			lookBack = true;
	        			whatISee = getWhatISee()[0];
	        			usableOnTheRight = isUsable(whatISee);
	        			System.out.println(getWhatISee()[0]);
	        			if (whatISee == Places.WALL) {
	        				System.out.println("I see the wall on the right...");
	        				seeWallRight = true;
	        			}
	        			else
	        				seeWallRight = false;
	        			return Direction.TURN_RIGHT_ON_SPOT;
	        		}
	        		else if (!lookLeft) {
	        			lookLeft = true;
	        			whatISee = getWhatISee()[0];
	        			System.out.println(getWhatISee()[0]);
	        			return Direction.TURN_RIGHT_ON_SPOT;
	        		}
	        		else if (!lookFront) {
	        			lookFront = true;
	        			whatISee = getWhatISee()[0];
	        			System.out.println(getWhatISee()[0]);
	        			return Direction.TURN_RIGHT_ON_SPOT;
	        		}
	        		else {
	        			lookRight = false;
	        			lookLeft = false;
	        			lookBack = false;
	        			lookFront = false;
	        			whatISee = getWhatISee()[0];
	        			prevGetWhatISee = whatISee;
	        			System.out.println(getWhatISee()[0]);
	        			if (seeWallFront) {
	        				seeWallFront = false;
	        				seeWallRight = true;
	        				//prevGetWhatISee = getWhatISee()[0];
	        				System.out.println("See wall in front of me -> turn left");
	        				return Direction.TURN_LEFT_ON_SPOT;
	        			}
	        			if (!seeWallRight) {
	        				if (usableOnTheRight) {
	        					System.out.println("I don't see wall on the right and right is usable-> turn right");
	        					prevGetWhatISee = Places.BOUNCER; // Just a flag to know that I turned right and next i want to move forward
	        					return Direction.TURN_RIGHT_ON_SPOT;
	        				}
	        				else if (isUsable(whatISee)) {
	        					System.out.println("I dont see the wall on the right, right is unsuable and fowrard is free -> move forward");
	        					return Direction.FORWARD;
	        				}
	        				else {
	        					System.out.println("I dont see the wall on the right, right is unsuable and fowrard is not free -> turn left");
	        					return Direction.TURN_LEFT_ON_SPOT;
	        				}
//	        				System.out.println("I don't see wall on the right -> turn right");

	        			}
	        			if (isUsable(prevGetWhatISee)) {
	        				System.out.println("Nothing in front of me -> go forward");
	        				return Direction.FORWARD;
	        			}
	        			else {
	        				System.out.println("Something in front of me -> turn left");
	        				prevGetWhatISee = getWhatISee()[0];
	        				return Direction.TURN_LEFT_ON_SPOT;
	        			}
	        		}
	        		
	        	}
	        }
		}
		
	    // TODO: inner class example:
		//	    private class x {
		//	    	private int y = 0;
		//	    	public x() {
		//	    		y = 3;
		//	    	}
		//	    	
		//	    	public void sdlkfj() {
		//	    		System.out.println(prevGetWhatISee);
		//	    		
		//	    	}
		//	    }
	    
		
		
		private int xPos = 0;
		private int yPos = 0;
		private Heading lookingDirection = Heading.WEST; // 0 - north, 1 - east, 2 - south, 3 - west
		private Places[][] map = { { Places.PATH } };
		private Places sawOnTheLeft;
		private Places sawOnTheRight;
		private int countDir = 0; // to indicate that we looked to all 4 directions
		
		// TODO: Instead of turning left/right on spot, Go Left and Right. I think that it will also make the code shorter
		// Is there a way not to check what I see at the place I came from? 
	    public Direction moveAvatar() {
	    	if (printTest)
	    		System.out.println("xPos: "+xPos+", yPos: "+yPos+", lookingDirection = "+lookingDirection);
	    	if (waitEachStep) {
		    	Scanner scanner = new Scanner(System.in);
		        scanner.nextLine();
	    	}
	        Places whatISee;
	        Direction dir;
        	whatISee = getWhatISee()[0];
	        if (countDir != 4) { // Look to all 4 directions
	        	if (countDir == 1) // look to the tight only once -> looking at he's right
	        		sawOnTheRight = whatISee;
	        	else if (countDir == 3) // look to the tight only once -> looking at he's left
	        		sawOnTheLeft = whatISee;
	        	countDir++;
	        	updateMapAndPosition(whatISee);
	        	return Direction.TURN_RIGHT_ON_SPOT;
	        }
    		else { // Finished looking to all directions -> last step
    			countDir = 0; // Reset directions he saw -> next time look at all 4 directions again
    			dir = turnToRandomDir(whatISee);
    			if (dir != Direction.TURN_LEFT_ON_SPOT && dir !=Direction.TURN_LEFT_ON_SPOT) { // if we turn on spot we dont update the position in map -> avatar stays in the same place
    				updateAvatarsPos();
    			}
    		}
	        if (printMap)
	        	displayMap(map);
	        //return walkToCreateAMap();
	        if (printTest)
	        	System.out.println("GOING TO: " + dir);
	        return dir;
	    }
	    
	    private void updateMapAndPosition(Places whatISee) {
			if (printTest)
				System.out.println(whatISee);
	    	switch (lookingDirection) {
	    	case WEST:
    			if (xPos == 0) { // We are at the edge of the map -> add new row to thw left
    				if (printTest)
    					System.out.println("Adding new column to the left");
    				map = updateMapNewColumnLeft(map,whatISee,xPos,yPos);
    				xPos = xPos+1; // Added a new column left so xPos is updated +1
    			}
		        map[yPos][xPos-1] = whatISee; // Add the element that the avatar sees to the map
		        if (printTest)
		        	System.out.println("New Heading after west: "+lookingDirection);
	    		break;
	    	case SOUTH:
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (yPos+1 == map.length) {
    				if (printTest)
    					System.out.println("Adding new row down");
    				map = updateMapNewRowDown(map,whatISee,xPos,yPos);
    			}
    	        // Update the element that the avatar sees
    	        map[yPos+1][xPos] = whatISee;
	    		break;
	    	case EAST:
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (xPos+1 == map[0].length) {
    				if (printTest)
    					System.out.println("Adding new column to the right");
    				map = updateMapNewColumnRight(map,whatISee,xPos,yPos);
    			}
		        // Update the element that the avatar sees
		        map[yPos][xPos+1] = whatISee;
		        if (printTest)
		        	System.out.println("returning");
    			break;
	    	case NORTH:
    			usableOnTheRight = isUsable(whatISee);
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (yPos == 0) {
    				if (printTest)
    					System.out.println("Adding new row up");
    				map = updateMapNewRowUp(map,whatISee,xPos,yPos);
    				yPos = yPos +1; // Added a new row up so yPox is updated +1
    			}
		        // Update the element that the avatar sees
		        map[yPos-1][xPos] = whatISee;
    			break;
	    	}
	        setHeadingTurnRight();
	    }
	    
	    private void setHeadingTurnLeft() {
	    	if (printTest)
	    		System.out.println("Turning left");
	    	switch (lookingDirection) {
	    	case WEST -> lookingDirection = Heading.SOUTH; // looks west turn south
	    	case SOUTH -> lookingDirection = Heading.EAST; // looks south turn east
	    	case EAST -> lookingDirection = Heading.NORTH; // looks east turn north 
	    	case NORTH -> lookingDirection = Heading.WEST; // looks north turn west
	    	}
	    }
	    
	    private void setHeadingTurnRight() {
	        if (printTest)
	            System.out.println("Turning right");
	        switch (lookingDirection) {
	            case WEST -> lookingDirection = Heading.NORTH;
	            case SOUTH -> lookingDirection = Heading.WEST;
	            case EAST -> lookingDirection = Heading.SOUTH;
	            case NORTH -> lookingDirection = Heading.EAST;
	        }
	    }
	    
	    private int setpsForward = 0;
	    private int turnedLeft = 0;
	    private int turnedRight = 0;
	    private Direction turnToRandomDir(Places whatISee) {
	        if (printTest)
	        	System.out.println("turnedLeft = "+turnedLeft+", turnedRight = "+turnedRight+" sawOnTheLeft: "+sawOnTheLeft+" sawOnTheRight: "+sawOnTheRight);
	    	Direction dir;
	    	if (isUsable(whatISee) && setpsForward < 3) {
	    		dir = Direction.FORWARD;
	    		setpsForward++;
	    	}
	    	else if (isUsable(sawOnTheRight) && turnedRight != 2){
	    		turnedRight++;
	    		turnedLeft = 0;
	    		dir = Direction.RIGHT;
	    		setHeadingTurnRight();
	    		System.out.println("Go right");
		    	if (setpsForward >= 3) // reset step forward
		    		setpsForward = 0;
	    	}
	    	else if (isUsable(sawOnTheLeft) && turnedLeft != 2){
	    		turnedRight = 0;
	    		turnedLeft++;
	    		dir = Direction.LEFT;
	    		System.out.println("Go left");
				setHeadingTurnLeft();
		    	if (setpsForward >= 3) // reset step forward
		    		setpsForward = 0;
	    	}
	    	// TODO: This does not work properly
			else { // Both left right and front are not usable -> turn left on spot
				dir = Direction.TURN_LEFT_ON_SPOT;
				setHeadingTurnLeft();
				System.out.println("TURN_LEFT_ON_SPOT");
				
			}
//	    	Random rand = new Random();
//			int number = rand.nextInt(5);
//			if (isUsable(sawOnTheLeft))
//			if (number == 0 && isUsable(sawOnTheLeft)) {
//				dir = Direction.LEFT;
//				setHeadingTurnLeft();
//			}
//			else if (number == 1 && isUsable(sawOnTheRight)) {
//				dir = Direction.RIGHT;
//				setHeadingTurnRight();
//			}
//			else if (isUsable(whatISee)) { // higher chance to go forward
//				dir = Direction.FORWARD;
//			}
//			else { // Both left right and front are not usable -> turn left on spot
//				dir = Direction.TURN_LEFT_ON_SPOT;
//				setHeadingTurnLeft();
//			}
			if (printTest)
				System.out.println("Turning random to: "+dir+" looking: "+lookingDirection);
			return dir;
	    }
	    
	 // Function to update the map based on what the avatar sees
	    private static Places[][] updateMapNewColumnRight(Places[][] oldMap, Places seen, int positionX, int positionY) {
	        int rows = oldMap.length;
	        // System.out.println(rows);
	        int columns = oldMap[0].length;
	        
	        // Create a new map with increased size
	        Places[][] newMap = new Places[rows][columns + 1];

	        // Copy the old map elements to the new map
	        for (int i = 0; i < rows; i++) {
	            System.arraycopy(oldMap[i], 0, newMap[i], 0, columns);
	        }	        
	        return newMap;
	    }
	    private static Places[][] updateMapNewColumnLeft(Places[][] oldMap, Places seen, int positionX, int positionY) {
	        int rows = oldMap.length;
	        // System.out.println(rows);
	        int columns = oldMap[0].length;
	        
	        // Create a new map with increased size
	        Places[][] newMap = new Places[rows][columns + 1];

	        // Copy the old map elements to the new map
	        for (int i = 0; i < rows; i++) {
	            System.arraycopy(oldMap[i], 0, newMap[i], 1, columns);
	        }
	        return newMap;
	    }
	    private static Places[][] updateMapNewRowDown(Places[][] oldMap, Places seen, int positionX, int positionY) {
	        int rows = oldMap.length;
	        // System.out.println(rows);
	        int columns = oldMap[0].length;
	        
	        // Create a new map with increased size
	        Places[][] newMap = new Places[rows + 1][columns];

	        // Copy the old map elements to the new map
	        for (int i = 0; i < rows; i++) {
	            System.arraycopy(oldMap[i], 0, newMap[i], 0, columns);
	        }
	        
	        return newMap;
	    }
	    private static Places[][] updateMapNewRowUp(Places[][] oldMap, Places seen, int positionX, int positionY) {
	        int rows = oldMap.length;
	        // system.out.println(rows);
	        int columns = oldMap[0].length;
	        
	        // Create a new map with increased size
	        Places[][] newMap = new Places[rows + 1][columns];

	        // Copy the old map elements to the new map
	        for (int i = 1; i < rows + 1; i++) {
	            System.arraycopy(oldMap[i-1], 0, newMap[i], 0, columns);
	        }
	        return newMap;
	    }
	    
	    private void updateAvatarsPos() {
//	        countDir = 0; // Reset directions he saw -> next time look at all 4 directions again

	        if (printTest)
	            System.out.println("Nothing in front of me -> go forward to: " + lookingDirection + "\n");

	        switch (lookingDirection) {
	            case WEST -> xPos--;
	            case SOUTH -> yPos++;
	            case EAST -> xPos++;
	            case NORTH -> yPos--;
	        }

	        xPos = Math.max(0, xPos);
	        yPos = Math.max(0, yPos);
	    }

	 // Function to display the map
	    private static void displayMap(Places[][] map) {
	    	System.out.println();
	    	System.out.println();
	        for (Places[] row : map) {
	            for (Places place : row) {
	                String placeString = (place != null) ? place.toString() : "null";
	                String truncatedString = placeString.substring(0, Math.min(placeString.length(), 4));
	                String paddedString = String.format("%-4s", truncatedString); // Pad with spaces if less than 4 letters
	                System.out.print(paddedString + " ");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    }
}
