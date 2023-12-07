/**
Party Simulation: Kieran Avatar
Date: 01.12.2023
Class: Kieran.java extending the Avatar.java class
Description: Creating a unique avatar for Kieran
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
	private Map <String, Places[]> coordinateMap;  // Creating a variable to store discovered map coordinates
	BeverageType cool_beverage;
	// ************** Constructor **************
	public Kieran(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO
		coordinateMap = new HashMap <>(); // Instantiating an instance of a Hashmap to store coordinates
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

	public Direction moveAvatar() {
       // TODO
        // create an algorithm that determines the next step of your movement pattern
        // based on a set of priorities.

		// Algorithm to move randomly and create a mind map, eventually trying to find the bar
		Places[] currentPlace = getWhatISee();
		// storeCoordinate(avatar.getX(), avatar.getY(), currentPlace); // Need to get x and y coordinates to be stored, but how it isn't available to Avatar



        Random rand = new Random();
        int number = rand.nextInt(4);
        // direction is set externally --> check with the simulation environment
        Direction dir = Direction.FORWARD;
        if (number == 0) {
            dir = Direction.FORWARD;
			System.out.println(getWhatISee());
        }
        else if (number == 1) {
            dir = Direction.RIGHT;
			System.out.println(getWhatISee());
        }
        else if (number == 2) {
            dir = Direction.BACK;
			System.out.println(getWhatISee());
        }
        else if (number == 3) {
            dir = Direction.LEFT;
			System.out.println(getWhatISee());
        }

		if(getWhatISee()[0] == Places.BAR) {
            dir = Direction.IDLE;
			drink(cool_beverage);
			setAlcoholPercentage(10);
			System.out.println("I am drinking a cool beverage!");
		}
        return dir;
	}

	public Places[] getPlaceAtCoordinate(int x, int y) {
		String coordinates = x + "," + y;
		return coordinateMap.getOrDefault(coordinates, new Places[0]);
	}

	private void storeCoordinate(int x, int y, Places place) {
        String coordinates = x + "," + y;
        
        // Check if coordinates exist in the map
        if (coordinateMap.containsKey(coordinates)) {
            // If coordinates exist, append the new place to the existing array
            Places[] placesArray = coordinateMap.get(coordinates);
            Places[] updatedPlacesArray = new Places[placesArray.length + 1];
            System.arraycopy(placesArray, 0, updatedPlacesArray, 0, placesArray.length);
            updatedPlacesArray[placesArray.length] = place;
            coordinateMap.put(coordinates, updatedPlacesArray);
        } else {
            // If coordinates don't exist, create a new array and store the place
            Places[] newPlacesArray = { place };
            coordinateMap.put(coordinates, newPlacesArray);
        }
    }
}
