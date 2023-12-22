/**
Party Simulation: Kieran Avatar
Date: 16.12.2023
Class: Kieran.java extending the Avatar.java class
Description: Creating a unique avatar for Kieran
Status: WIP
**/

//Packages
package com.simulation.partypeople;

// Imports
import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import java.awt.Color;
import java.util.Random;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enviroment.Square;

// Additional imports for avatar creating a "mind map"
import java.util.HashMap;
import java.util.Map;
import com.simulation.enums.Places;
import com.simulation.matrix.LocatedAvatar;
import com.simulation.matrix.Matrix;


/* To do list:
    Store surroudings locally
    Develop an algorithm to determine your next destination
    Develop movement pattern
    Develop dancing movement pattern
    Develop fighting algorithm with certain fighting skills
    Develop prefered drinks list
    Develop default phrases to interact with other users of Club Penguin
    Develop spiels
    Develop smoke area behaviour
    Develop skibidi toilet

    ! Develop algorithm to find the bar and have a drink, so not random movement completely but build an "internal" map
    of the environement and find the bar
*/

public class Kieran extends Avatar{

	// Variables

	private HashMap <String, Places[]> mindMap;  // Creating a variable to store discovered map coordinates
	private BeverageType cool_beverage;
	private Boolean danceFloorShenanigans;
	private Direction movementDirection;

	// Constructor

	public Kieran(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// WIP
		mindMap = new HashMap <>(); // Instantiating an instance of a Hashmap to store coordinates
		danceFloorShenanigans = false;
		movementDirection = Direction.FORWARD;
	}

	// Methods

	public Direction dancingAlgo() {
		// TODO
		// develop the type of movement that would represent your dance pattern
		// Task: develop moon walk dancing algorithm
		// Idea: move through club until encountering dancefloor, call dancingAlgo
		//...needs to move avatar back and forth and stay within dance floor area
		danceFloorShenanigans = true;
		Places futureSpot = getWhatISee()[1];
		Direction dancingMovement = Direction.FORWARD;
		// System.out.println("Let's DANCE!");  // Print statement for testing, comment out to merge with main

		// Switch case for dance floor scenarios
		// Need to keep avatar on dancefloor for specified time
		switch(futureSpot) {
			case DANCEFLOOR:
				dancingMovement = Direction.FORWARD;
				break;
			case PATH:
				dancingMovement = Direction.BACK;
				break;
			case PERSON:
				whenStuckMove(dancingMovement);
				System.out.println("Person in the way, dancing elsewhere");
				break;
			default:
				dancingMovement = Direction.FORWARD;
				break;
		}
		return dancingMovement;
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

	public Direction moveAboutAimlessly() {
		Random rand = new Random();
        int number = rand.nextInt(4);
		Direction randomMovementDir = Direction.IDLE;
		if (number == 0) {
			randomMovementDir = Direction.FORWARD;
			// System.out.println("Forward movement");
		}
		else if (number == 1) {
			randomMovementDir = Direction.RIGHT;
			// System.out.println("Right movement");
		}
		else if (number == 2) {
			randomMovementDir = Direction.BACK;
			// System.out.println("Back movement");
		}
		else if (number == 3) {
			randomMovementDir = Direction.LEFT;
			// System.out.println("Left movement");
		}
		return randomMovementDir;
	}
    
	@Override
	public Direction moveAvatar() {
        // TODO
        // create an algorithm that determines the next step of your movement pattern
        // based on a set of priorities.

		// Variables for movement
		Places currentSpot = getWhatISee()[0];
        Places futureSpot = getWhatISee()[1];
		int trappedCounter = 0;

		// Variable for storing mindmap locations
		Places[] surroundings = getWhatISee();

		if(danceFloorShenanigans == false) {

			// Switch case for movement dependent on environment location
			switch(futureSpot) {
				case OUTSIDE:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					break;
				case PATH:
					updateAvatarMindMap(surroundings);
					movementDirection = moveAboutAimlessly();
					break;
				case PERSON:
					if (getWhatISee()[1] == Places.PERSON) {
						trappedCounter ++;
						movementDirection = Direction.BACK;
						// System.out.println("Incrementing trapped counter: " + trappedCounter);
					}
					if (trappedCounter > 3) {
						// System.out.println("Trapped moving elsewhere!");
						whenStuckMove(movementDirection);
					}
					break;				
				case LOUNGE_BIG:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					//    talk();
					break;
				case LOUNGE_SMALL:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					//    talk();
					break;
				case LOUNGE_SMOKING:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					smoke();
					break;
				case DANCEFLOOR:
					updateAvatarMindMap(surroundings);
					// movementDirection = Direction.FORWARD;
					movementDirection = dancingAlgo();
				case BAR:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					drink(cool_beverage);
					setAlcoholPercentage(10);
					// System.out.println("I am drinking a cool beverage!");
					break;
				case DJ:
					updateAvatarMindMap(surroundings);
					// request music?
					movementDirection = Direction.BACK;
					break;
				case BOUNCER:
					updateAvatarMindMap(surroundings);
					// talk();
					movementDirection = Direction.BACK;
					break;
				case FUSSBALL:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					playFussball();
					break;
				case POOL:
					updateAvatarMindMap(surroundings);
			   		movementDirection = Direction.BACK;
					playPool();
					break;
				case TOILET:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					//    toilet(int timeInToilet)
					break;
				case WALL:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.BACK;
					if(getWhatISee()[1] == Places.WALL) {
						trappedCounter ++;
						// System.out.println("Incrementing trapped counter: " + trappedCounter);
					}
					if(trappedCounter > 3) {
						// System.out.println("Trapped moving elsewhere!");
						whenStuckMove(movementDirection);
					}
					break;
				default:
					updateAvatarMindMap(surroundings);
					moveAboutAimlessly();
					break;
			}
		} else if (danceFloorShenanigans = true) {
			movementDirection = dancingAlgo();
		}
		return movementDirection;
	}

	// WIP *****************************************************************************

	// Implementing a mental map for the avatar to use relative positions to locate places in the environment
	// Cannot access x and y coordinates, need to use relative positions!

	// Algorithm to move randomly and create a mind map, eventually trying to find the bar
	// Places currentPlace = getWhatISee()[0];

	public boolean canIUseIt(Boolean isUsable) {
		if(isUsable == true) {
			return true;
		} else {
			return false;
		}
	}

	private Direction whenStuckMove(Direction movementDirection) {
		switch(movementDirection) {
			case FORWARD:
				movementDirection = Direction.BACK;
				System.out.println("Can't move forward, going backward!");
			case BACK:
				movementDirection = Direction.FORWARD;
				System.out.println("Can't move backward, going forward!");
			case RIGHT:
				movementDirection = Direction.LEFT;
				System.out.println("Can't move left, going right!");
			case LEFT:
				movementDirection = Direction.RIGHT;
				System.out.println("Can't move right, going left!");
		}
		return movementDirection;
	} 

	private void updateAvatarMindMap(Places[] surroundings) {
		Places currentSpot = surroundings[0];
		mindMap.put(currentSpot.toString(), surroundings);
	}

	public void printMindMap() {
		System.out.println("Mind Map Contents: ");
		for(Map.Entry<String, Places[]> entry : mindMap.entrySet()) {
            String key = entry.getKey();
			Places[] value = entry.getValue();
			System.out.println(key + ": [");
			for(Places place : value) {
				System.out.println(place + ", ");
			}
			System.out.println("]");
		}
	}
}
