/**
Party Simulation: Kieran Avatar
Date: 15.12.2023
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
	private HashMap <String, Places[]> perceivedMap;  // Creating a variable to store discovered map coordinates
	BeverageType cool_beverage;
	Boolean isMoonwalking;
	// ************** Constructor **************
	public Kieran(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO
		perceivedMap = new HashMap <>(); // Instantiating an instance of a Hashmap to store coordinates
		isMoonwalking = false;
	}

	// ************** Methods **************
	public void dancingAlgo() {
		// TODO
		// develop the type of movement that would represent your dance pattern
		// Task: develop moon walk dancing algorithm
		// Idea: move through club until encountering dancefloor, call dancingAlgo
		//...needs to move avatar back and forth and stay within dance floor area
		System.out.println("I want to dance!");
		Direction dancingDirection = Direction.IDLE;

		if (isMoonwalking == false) {
			dancingDirection = Direction.FORWARD;
			isMoonwalking = true;
			System.out.println("Going to do the moonwalk!");
		} else if (getWhatISee()[0] != Places.DANCEFLOOR) {
			dancingDirection = Direction.BACK;
			System.out.println("I'm like Michael!");
		} else {
			dancingDirection = Direction.FORWARD;
			System.out.println("Hehe!");
		}
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

	public Direction moveAvatar() {
       // TODO
        // create an algorithm that determines the next step of your movement pattern
        // based on a set of priorities.

		// Algorithm to move randomly and create a mind map, eventually trying to find the bar
		Places[] currentPlace = getWhatISee();
		// storeCoordinate(avatar.getX(), avatar.getY(), currentPlace); // Need to get x and y coordinates to be stored, but how it isn't available to Avata

        Random rand = new Random();
        int number = rand.nextInt(4);
        // direction is set externally --> check with the simulation environment
		// Manage direction with path and queue as well as other places below
        Direction dir = Direction.FORWARD;
		if (isMoonwalking == true) {
			dancingAlgo();
		} else {
			if (number == 0) {
				dir = Direction.FORWARD;
			//	System.out.println(getWhatISee());
			}
			else if (number == 1) {
				dir = Direction.RIGHT;
			//	System.out.println(getWhatISee());
			}
			else if (number == 2) {
				dir = Direction.BACK;
			//	System.out.println(getWhatISee());
			}
			else if (number == 3) {
				dir = Direction.LEFT;
			//	System.out.println(getWhatISee());
			}
			// WIP ******************************************************************************
			// Re-examine if an if else block is the best strat
			if(getWhatISee()[0] == Places.BAR) {
			//    dir = Direction.IDLE;
				drink(cool_beverage);
				setAlcoholPercentage(10);
				System.out.println("I am drinking a cool beverage!");
			} else if(getWhatISee()[0] == Places.DANCEFLOOR) {
				dancingAlgo();
				System.out.println("I am done dancing!");
			}
			else if(getWhatISee()[0] == Places.FUSSBALL) {
				playFussball();
			}
			else if(getWhatISee()[0] == Places.POOL) {
				playPool();
			}
			else if(getWhatISee()[0] == Places.LOUNGE_SMOKING) {
				smoke();
			}
			else if(getWhatISee()[0] == Places.LOUNGE_BIG) {
			//    talk();
			}
			else if(getWhatISee()[0] == Places.LOUNGE_SMALL) {
			//    talk();
			}		
			else if(getWhatISee()[0] == Places.TOILET) {  // Toilet time can be influenced by how many drinks consumed
			//    toilet(int timeInToilet)
			}
			else if(getWhatISee()[0] == Places.DJ) {
			// request music?
			}
			else if(getWhatISee()[0] == Places.OUTSIDE) {
				dir = Direction.BACK;
			}
		}
        return dir;
	}

	// WIP *****************************************************************************
	// Implementing a mental map for the avatar to use relative positions to locate places in the environment
	// Cannot access x and y coordinates, need to use relative positions

	public Places[] getPlaceAtCoordinate(int x, int y) {
		String coordinates = x + "," + y;
		return perceivedMap.getOrDefault(coordinates, new Places[0]);
	}

	private void storeCoordinate(int x, int y, Places place) {
        String coordinates = x + "," + y;
        
        // Check if coordinates exist in the map
        if (perceivedMap.containsKey(coordinates)) {
            // If coordinates exist, append the new place to the existing array
            Places[] placesArray = perceivedMap.get(coordinates);
            Places[] updatedPlacesArray = new Places[placesArray.length + 1];
            System.arraycopy(placesArray, 0, updatedPlacesArray, 0, placesArray.length);
            updatedPlacesArray[placesArray.length] = place;
            perceivedMap.put(coordinates, updatedPlacesArray);
        } else {
            // If coordinates don't exist, create a new array and store the place
            Places[] newPlacesArray = { place };
            perceivedMap.put(coordinates, newPlacesArray);
        }
    }

	public void updatePerceivedEnvironment(Places[] whatISee) {
        // Assuming 'whatISee' contains the places observed by the avatar

        // Clear the existing perceived environment before updating
        perceivedMap.clear();

        // Update the perceived environment using the information received
        for (int i = 0; i < whatISee.length; i++) {
            // Construct a unique key based on the relative position from the avatar
            String relativePositionKey = generateRelativePositionKey(i); // Define your logic

            // Update the perceived environment with the observed place at the relative position
    //        perceivedMap.put(relativePositionKey, whatISee[i]);
        }
    }

	private String generateRelativePositionKey(int index) {
        // Implement your logic to generate a unique key based on the index or other relative factors
        // This method should generate a key representing the position relative to the avatar
        // Example: return "relativeKey_" + index;
        return Integer.toString(index); // Placeholder; replace with your logic
    }
}
