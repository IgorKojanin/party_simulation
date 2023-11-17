///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.avatar.PartyGoer;
import com.simulation.enums.BeverageType;
import java.awt.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class JoeMama extends Avatar implements PartyGoer {

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
	public JoeMama(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName) {
		super(shape, color, borderWidth, avatarAge, avatarName);
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
		Direction dir = Direction.IDLE;
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}
}
