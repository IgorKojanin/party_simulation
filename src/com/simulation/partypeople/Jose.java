///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Avatar class for Jose Luis Roldan Rodriguez
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.avatar.PartyGoer;
import java.awt.Color;
import java.io.*;
import java.util.Random;

import com.simulation.enums.*;

public class Jose extends Avatar implements PartyGoer {

	File file = new File("misc\\Shrek-Script_Jose.txt");
	BufferedReader br = new BufferedReader(new FileReader(file));

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
	public Jose(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO
	}

	// ************** Methods **************
	public void dancingAlgo() {
		// TODO
		// develop the type of movement that would represent your dance pattern

	}

	public void fight(PartyGoer opponent) { // Call this function if other avatar starts a fight
		// TODO
		// develop different fighting moves
		// be very descriptive (user 2 is performing an F5 on user 3)
	}

	public void talk(PartyGoer person) {
		// TODO
		// create a list of answers and questions that you would like to exchange with
		// the other users of Club Penguin
		// create a primitive algorithm that would make picks from your answer list
		// based on the questions asked
		System.out.println("Finished playing all tracks. Restarting playlist.");
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

	public Direction moveAvatar() { 	// First implementation random movement
		// TODO
		// create an algorithm that determines the next step of your movement pattern
		// based on a set of priorities.
		Random rand = new Random();
		int number = rand.nextInt(4);

		Direction dir = Direction.FORWARD;
		if (number == 0) {
			dir = Direction.FORWARD;
		}
		else if (number == 1) {
			dir = Direction.RIGHT;
		}
		else if (number == 2) {
			dir = Direction.BACK;
		}
		else if (number == 3) {
			dir = Direction.LEFT;
		}
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}
}
