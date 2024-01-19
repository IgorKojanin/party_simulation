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
			if (place == Places.PATH || place == Places.DANCEFLOOR)
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
		
		
		private int xPos = 0;
		private int yPos = 0;
		private boolean lookNorth = false;
		private boolean lookEast = false;
		private boolean lookSouth = false;
		private boolean lookWest = false;
		private Heading lookingDirection = Heading.WEST; // 0 - north, 1 - east, 2 - south, 3 - west
		private boolean doneSettingRandomDir = false;
		private Places[][] map = { { Places.PATH } };
		
		// TODO: Instead of turning left/right on spot, Go Left and Right. I think that it will also make the code shorter
		// Is there a way not to check what I see at the place I came from? 
	    public Direction moveAvatar() {
	        System.out.println(isHasMoved());
	    	if (printTest)
	    		System.out.println("xPos: "+xPos+", yPos: "+yPos+", lookingDirection = "+lookingDirection);
	    	if (waitEachStep) {
		    	Scanner scanner = new Scanner(System.in);
		        scanner.nextLine();
	    	}
	        Places whatISee;
	        Direction dir;
	        if (!lookWest && lookingDirection == Heading.WEST) {
	        	lookWest = true;
    			whatISee = getWhatISee()[0];
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (lookingDirection == Heading.WEST && xPos == 0) {
    				if (printTest)
    					System.out.println("Adding new column to the left");
    				map = updateMapNewColumnLeft(map,whatISee,xPos,yPos);
    				xPos = xPos+1; // Added a new column left so xPos is updated +1
    			}
		        // Update the element that the avatar sees
		        map[yPos][xPos-1] = whatISee;
		        setHeadingTurnRight();
		        if (printTest)
		        	System.out.println("New Heading after west: "+lookingDirection);
    			return Direction.TURN_RIGHT_ON_SPOT;
    		}
    		else if (!lookNorth && lookingDirection == Heading.NORTH) {
    			lookNorth = true;
    			whatISee = getWhatISee()[0];
    			usableOnTheRight = isUsable(whatISee);
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (lookingDirection == Heading.NORTH && yPos == 0) {
    				if (printTest)
    					System.out.println("Adding new row up");
    				map = updateMapNewRowUp(map,whatISee,xPos,yPos);
    				yPos = yPos +1; // Added a new row up so yPox is updated +1
    			}
		        // Update the element that the avatar sees
		        map[yPos-1][xPos] = whatISee;
		        setHeadingTurnRight();
    			return Direction.TURN_RIGHT_ON_SPOT;
    		}
    		else if (!lookEast && lookingDirection == Heading.EAST) {
    			
    			lookEast = true;
    			whatISee = getWhatISee()[0];
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			
    			if (lookingDirection == Heading.EAST && xPos+1 == map[0].length) {
    				if (printTest)
    					System.out.println("Adding new column to the right");
    				map = updateMapNewColumnRight(map,whatISee,xPos,yPos);
    			}
		        // Update the element that the avatar sees
		        map[yPos][xPos+1] = whatISee;
		        setHeadingTurnRight();
		        if (printTest)
		        	System.out.println("returning");
    			return Direction.TURN_RIGHT_ON_SPOT;
    		}
    		else if (!lookSouth && lookingDirection == Heading.SOUTH) {
    			lookSouth = true;
    			whatISee = getWhatISee()[0];
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (lookingDirection == Heading.SOUTH && yPos+1 == map.length) {
    				if (printTest)
    					System.out.println("Adding new row down");
    				map = updateMapNewRowDown(map,whatISee,xPos,yPos);
    			}
    	        // Update the element that the avatar sees
    	        map[yPos+1][xPos] = whatISee;
    	        setHeadingTurnRight();
    			return Direction.TURN_RIGHT_ON_SPOT;
    		}
    		else {
    			whatISee = getWhatISee()[0];
    			if (printTest)
    				System.out.println(getWhatISee()[0]);
    			if (doneSettingRandomDir) {
    				if (isUsable(whatISee)) {
    					doneSettingRandomDir = false;
    					dir = Direction.FORWARD;
    					updateAvatarsPos();
    				}
    				else 
    					dir = turnToRandomDir(whatISee); // turn again
    			}
    			else {
    				//dir = turnToRandomDir(whatISee);
    				dir = turnToRandomDir(whatISee);
    				if (dir != Direction.FORWARD) // If we dont go forward we need to check if in front of us there is an obstacle
    					doneSettingRandomDir = true;
    				else { // else random dir is forward -> we go forward, and take care to set everything needed
    					updateAvatarsPos();
    			        if (printTest)
    			        	displayMap(map);
	    				return dir;
    				}
    			}
    		}
	        if (printTest)
	        	displayMap(map);
	        //return walkToCreateAMap();
	        if (printTest)
	        	System.out.println("GOING TO: " + dir);
	        return dir;
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
	    
	    private Direction turnToRandomDir(Places whatISee) {
	    	Direction dir;
	    	Random rand = new Random();
			int number = rand.nextInt(5);
			// direction is set externally --> check with the simulation environment
			if (number == 0) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				setHeadingTurnLeft();
			}
			else if (number == 1) {
				dir = Direction.TURN_RIGHT_ON_SPOT;
				setHeadingTurnRight();
			}
			else { // higher chance to go forward
				if (isUsable(whatISee))
					dir = Direction.FORWARD;
				else {
					dir = Direction.TURN_LEFT_ON_SPOT;
					setHeadingTurnLeft();
				}
			}
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
	        lookWest = false;
	        lookSouth = false;
	        lookEast = false;
	        lookNorth = false;

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
	        for (Places[] row : map) {
	            System.out.println(Arrays.toString(row));
	        }
	    }
}