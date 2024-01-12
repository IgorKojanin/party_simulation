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
//import com.simulation.avatar.PartyGoer;
import com.simulation.enums.BeverageType;
import java.awt.Color;
import java.util.Random;
import com.simulation.enums.Places;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Shape;
import com.simulation.enums.Places;
import java.io.Console;

public class Thorvin extends Avatar {

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
	private int timeToLeave; 
	private Places [] PlacesArroundMe;
	private boolean isEntered = false;
	private Places[][] myMap;
	private int myY;
	private int myX;
	private Heading myHeading;
	private int countTurn; 
	private int danceCount;
	private int stand;
	private int inFront;
	

	// ************** Constructor **************
	public Thorvin(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

		goal = getAction(); //ersten Plan schmieden
		myMap = new Places[85][65];
		myY =40;
		myX=30;
		myHeading = Heading.WEST;
		PlacesArroundMe = new Places[1];
		countTurn =0;
		danceCount =0;
		 stand = 0;
		inFront =1;
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
		Random rand = new Random();
		int number = rand.nextInt(4);
		Direction dir= Direction.FORWARD;

	//if(this.getWhatISee()[stand] == Places.DANCEFLOOR){
		//dir = doDance(dir);
		
	//}
	/* else {
		
		if (number == 0) {
			dir = Direction.FORWARD;
		} else if (number == 1) {
			dir = Direction.RIGHT;
		} else if (number == 2) {
			dir = Direction.BACK;
		} else if (number == 3) {
			dir = Direction.LEFT;
		}
	} */
	dir = doScout();
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}

	private Places getAction(){ //what i want to do next 
		Random rand = new Random();
		int number = rand.nextInt(100);
		timeToLeave = number;

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
private boolean doLeave(){ // decides if the avatar wants to stay or leafe
timeToLeave = timeToLeave - 10;
if (timeToLeave <=0) {
	return true;
}
else{
return false;
}
}

private Direction doTurn(){ //erkunden der Karte	
Direction dir = Direction.IDLE;
Places[] placesArroundMe =this.getWhatISee();

if(myHeading == Heading.WEST){
myMap[myX+1][myY] = placesArroundMe[inFront];
dir = Direction.TURN_LEFT_ON_SPOT;
myHeading = Heading.SOUTH;
//System.out.println("West"myMap[myX+1][myY]);
	return dir;
}
else if (myHeading == Heading.SOUTH) {
myMap[myX][myY-1] = placesArroundMe[inFront];
myHeading = Heading.EAST;
//System.out.println("South"myMap[myX][myY-1]);
	return Direction.TURN_LEFT_ON_SPOT;
}
else if (myHeading == Heading.NORTH) {
myMap[myX][myY+1] = placesArroundMe[inFront];
myHeading = Heading.WEST;
//System.out.println("North"myMap[myX][myY+1]);
	return Direction.TURN_LEFT_ON_SPOT;
}
else if (myHeading == Heading.EAST) {
myMap[myX-1][myY] = placesArroundMe[inFront];
myHeading = Heading.NORTH;
//System.out.println("East"myMap[myX-1][myY]);
	return Direction.TURN_LEFT_ON_SPOT;
}
return dir;
}

private Direction doMove(){
Direction dir = Direction.IDLE;
Places[] placesArroundMe =this.getWhatISee();

if(myMap[myX+1][myY] == Places.PATH){
	myX = myX+1;
	myHeading = Heading.WEST;
return Direction.FORWARD;
}
else if (myMap[myX][myY-1]==Places.PATH) {
	myY = myY-1;
	myHeading = Heading.SOUTH;
	return Direction.LEFT;
}
else if (myMap[myX][myY+1] == Places.PATH) {
	myY = myY+1;
	myHeading = Heading.NORTH;
	return Direction.RIGHT;
}
else if(myMap[myX-1][myY]==Places.PATH){
	myX=myX-1;
	myHeading = Heading.EAST;
	return Direction.BACK;
}
return dir;
}

//Turn in spot until the heading ist west
private Direction findWest(){
	Direction dir = Direction.IDLE;
	do {
	if(myHeading == Heading.SOUTH){
		dir = Direction.TURN_RIGHT_ON_SPOT;
		myHeading = Heading.WEST;
	}
	else if(myHeading == Heading.NORTH){
		dir = Direction.TURN_LEFT_ON_SPOT;
		myHeading = Heading.WEST;
	}
	else if(myHeading == Heading.EAST){
	dir = Direction.TURN_LEFT_ON_SPOT;
	myHeading=Heading.NORTH;
	}

	return dir;
	}while(myHeading != Heading.WEST);
}

private Direction doScout(){
Direction dir = Direction.IDLE;
Places[] placesArroundMe =this.getWhatISee();

if(countTurn < 4 ){
dir = doTurn();
countTurn++;
return dir;
}
else if(countTurn == 4 && myHeading == Heading.WEST){
countTurn =5;
dir = doMove();
}
else if (countTurn > 4){
	countTurn ++;
	dir = findWest();
	if(myHeading == Heading.WEST){
		countTurn=0;
	}
}

return dir;
}

// Function for doing a circle when on Danecfloor
private Direction doDance (Direction current_dir){ 
	Direction dir = Direction.IDLE;
	
	if(danceCount == 0){
		Direction start_dir = current_dir;
		dir = current_dir;
		danceCount++;
	}
	else if(danceCount == 1){
		dir = Direction.LEFT;
			  danceCount++;	  
	}
	else if(danceCount == 2){
		dir = Direction.LEFT;
		danceCount++;
	}
	else if(danceCount==3){
		dir = Direction.LEFT;
		danceCount++;
	}
	else if(danceCount==4){ //ausgangsposition nun genau gleich eingangsposition
		dir = Direction.TURN_LEFT_ON_SPOT;
		danceCount++;
		}

	return dir;
}

//Function for finding the Dancefloor
private Direction findDancefloor(){
		Random rand = new Random();
		int number = rand.nextInt(4);
		Direction dir= Direction.IDLE;
		
		if (number == 0) {
			dir = Direction.FORWARD;
		} else if (number == 1) {
			dir = Direction.RIGHT;
		} else if (number == 2) {
			dir = Direction.BACK;
		} else if (number == 3) {
			dir = Direction.LEFT;
		}

	return dir;
}

//Function for finding the Bar
private Direction findBar(){
Direction dir = Direction.IDLE;

	return dir;
}

//Function for finding the Lounge
private Direction findLounge(){
	Direction dir = Direction.IDLE;

	return dir;
}


}

