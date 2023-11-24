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
import java.util.Random;
import com.simulation.enums.Places;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.enums.Places;


public class Thorvin extends Avatar implements PartyGoer {

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

	private Places goal;

	// ************** Constructor **************
	public Thorvin(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

		goal = getAction(); //ersten Plan schmieden
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
		
		Direction dir = Direction.IDLE;
		Random rand = new Random();
		int number = rand.nextInt(4);
		
	if(doLeaf())	{ 
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
	}
	else //Avatar bleibt an Ort stehen
		dir = Direction.IDLE;
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}

	private Places getAction(){
		Random rand = new Random();
		int number = rand.nextInt(100);

		if(number<40){ // 40% warscheinlichkeit für Alkohol 
			return Places.BAR;
		}
		else if (number < 60) {
			return Places.DANCEFLOOR; //Tanzen
		}
		else if (number < 80) {
			return Places.LOUNGE_SMOKING; //Rauchen
		}
		else return Places.TOILET; //Toilette
				
	}
private boolean doLeaf(){ // decides if the avatar wants to stay or leafe

	return true;
}

private Places doScout(){ //was ist um mich herrum
Places first =Avatar.getWhatISee();

}

}
	

