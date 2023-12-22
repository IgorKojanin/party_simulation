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

	// Constructor

	public Kieran(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// WIP
		mindMap = new HashMap <>(); // Instantiating an instance of a Hashmap to store coordinates
		danceFloorShenanigans = false;
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
				Random rand = new Random();
                int number = rand.nextInt(2);
				if (number == 0) {
					dancingMovement = Direction.TURN_RIGHT_ON_SPOT; // Only turning right like Zoolander
				    dancingMovement = Direction.FORWARD;
					// System.out.println("Person in the way, variant 1");
				}
				else if (number == 1) {
					dancingMovement = Direction.TURN_LEFT_ON_SPOT; // Only turning left
				    dancingMovement = Direction.FORWARD;
					// System.out.println("Person in the way, variant 1");
				}
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
		Direction movementDirection = Direction.FORWARD;
		Places currentSpot = getWhatISee()[0];
        Places futureSpot = getWhatISee()[1];

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
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;	
					break;				
				case LOUNGE_BIG:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					//    talk();
					break;
				case LOUNGE_SMALL:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					//    talk();
					break;
				case LOUNGE_SMOKING:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					smoke();
					break;
				case DANCEFLOOR:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					movementDirection = dancingAlgo();
				case BAR:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					drink(cool_beverage);
					setAlcoholPercentage(10);
					// System.out.println("I am drinking a cool beverage!");
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case DJ:
					updateAvatarMindMap(surroundings);
					// request music?
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case BOUNCER:
					updateAvatarMindMap(surroundings);
					// talk();
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case FUSSBALL:
					updateAvatarMindMap(surroundings);
					movementDirection = Direction.FORWARD;
					playFussball();
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case POOL:
					updateAvatarMindMap(surroundings);
			   		movementDirection = Direction.FORWARD;
					playPool();
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case TOILET:
					updateAvatarMindMap(surroundings);
					//    toilet(int timeInToilet)
					movementDirection = Direction.RIGHT;
					movementDirection = Direction.BACK;
					break;
				case WALL:
					updateAvatarMindMap(surroundings);
				movementDirection = Direction.RIGHT;
			    	movementDirection = Direction.BACK;
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
