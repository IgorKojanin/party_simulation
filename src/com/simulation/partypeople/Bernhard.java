///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: AvatarTemplate.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;

import java.util.Random;

import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import javax.swing.*;
import java.awt.*;

public class Bernhard extends Avatar{
	// this int is just a flag to do the first moves and scout the bar.
	// When it is set to 1 it will never be reset to 0 and means that the avatar
	// starts moving according to its desires after scouting the area
	// int firstmovesfinished = 0;
	int j = 0;
	int i = 0;
	int l = 0;
	int k = 0;
	int m = 0;
	boolean initialscoutturndone = false;
	boolean arrayvisualised = false;
	boolean havemovedforward = false;
	boolean haveturnedright = false;
	int scoutturncounter = 0;
	boolean firstThingAfterEnteringBarDone = false;
	boolean turnrightdone = false;
	boolean movedlastturn = false;

	// to visualise the mental map
	public static JPanel[][] panels;

	// starting coordinates in own mental map
	// the current location in the mental map will be augmented with these
	int mentalmapxlocation = 50;
	int mentalmapylocation = 50;

	// save current heading locally, start off facing WEST after entering bar
	public Heading currentHeading = Heading.WEST;

	// save lastmove
	Direction lastmoveDirection;

	// this is the array in which the discovered surroundings are stored locally
	Places[][] mentalmap;

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
	public Bernhard(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		// TODO
		mentalmap = new Places[100][100];
	}

	// ************** Methods **************
	public Direction dancingAlgo() {
		// TODO
		// develop the type of movement that would represent your dance pattern
		Direction dir;
		if (this.getWhatISee()[0] == Places.DANCEFLOOR) {
			for (l++; l < 5;) {
				dir = Direction.FORWARD;
				return dir;
			}
			for (k++; k < 2;) {
				dir = Direction.BACK;
				l = 0;
				return dir;
			}
			// for (m++; m < 5;) {
			// 	dir = Direction.FORWARD;
			// 	return dir;
			// }
			dir = Direction.TURN_LEFT_ON_SPOT;
			l = 0;
			m = 0;
			k = 0;
		} else {
			dir = Direction.TURN_LEFT_ON_SPOT;
		}
		return dir;
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

	public static void visualizeArray(Places[][] array) {
        int rows = array.length;
        int cols = array[0].length;

        // Create a JFrame
        JFrame frame = new JFrame("Mental Map Visualisation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(rows, cols));

        panels = new JPanel[rows][cols];

        // Initialize panels and add them to the frame
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setBackground(getColorForValue(array[i][j]));
                panels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                frame.add(panels[i][j]);
            }
        }

        // Set the frame to be visible
        frame.setVisible(true);
    }

	private static Color getColorForValue(Places mentalmap) {
        // Customize the color mapping based on your specific numbers
    	// TODO write if to put a colour based on what type of place is stored there in mentalmap
		if (mentalmap == Places.BAR || mentalmap == Places.POOL || mentalmap == Places.TOILET || mentalmap == Places. FUSSBALL || mentalmap == Places.DJ || mentalmap == Places.BOUNCER || mentalmap == Places.WALL || mentalmap == Places.OUTSIDE) {
			return Color.RED;
		}
		else if (mentalmap == Places.BAR_CHAIR || mentalmap == Places.DANCEFLOOR || mentalmap == Places.FUSSBALL_CHAIR || mentalmap == Places.TOILET_CHAIR || mentalmap == Places.POOL_CHAIR || mentalmap == Places.LOUNGE_BIG || mentalmap == Places.LOUNGE_SMALL || mentalmap == Places.LOUNGE_SMOKING) {
			return Color.GREEN;
		}
        else {
			return Color.WHITE;
		}
    }

	public static void updateArray(Places[][] array) {
        int rows = array.length;
        int cols = array[0].length;

        // Update the panel backgrounds with the array values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                panels[i][j].setBackground(getColorForValue(array[i][j]));
            }
        }
    }

	public void updateHeading(Heading currentHeading, Direction nextdir) {
		if (currentHeading == Heading.NORTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.WEST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.EAST;
			}
		}

		else if (currentHeading == Heading.EAST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.NORTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.SOUTH;
			}
		}

		else if (currentHeading == Heading.SOUTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.EAST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.WEST;
			}
		}

		else if (currentHeading == Heading.WEST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.SOUTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.NORTH;
			}
		}
	}

	// this method adds the Place in front of the avatar to the mental map
	public void updateMentalMap (Places[] WhatISee, Heading currentHeading) {
		if (WhatISee[1] != Places.PERSON) {
			if (currentHeading == Heading.NORTH) {
				mentalmap[mentalmapxlocation][mentalmapylocation - 1] = WhatISee[1];
			}
			else if (currentHeading == Heading.EAST) {
				mentalmap[mentalmapxlocation + 1][mentalmapylocation] = WhatISee[1];
			}
			else if (currentHeading == Heading.SOUTH) {
				mentalmap[mentalmapxlocation][mentalmapylocation + 1] = WhatISee[1];
			}
			else if (currentHeading == Heading.WEST) {
				mentalmap[mentalmapxlocation - 1][mentalmapylocation] = WhatISee[1];
			}
		}
	}

	public Direction doscoutturn(Heading currentHeading) {
		// set Direction to idle in case no other decision can be made here
		// Direction dir = Direction.IDLE;
		// while (scoutturn < 4) {
		updateMentalMap(this.getWhatISee(), currentHeading);
		Direction dir = Direction.TURN_LEFT_ON_SPOT;
		updateHeading(currentHeading, Direction.TURN_LEFT_ON_SPOT);
		//scoutturn++;
		return dir;
		// }
		// scoutturn = 0;
		// return dir;
	}

	public Places checksquareonright(Heading currentHeading) {
		// Failsafe: set squareonright to WALL in case the function fails so it at least returns something. 
		// WALL is the least critical thing to put in mental map as it only results in avoiding 
		// some squares in the worst case instead of trying to use invalid squares if e.g. PATH
		// had been saved as the failsafe option
		Places squareonright = Places.WALL;
		if (currentHeading == Heading.NORTH) {
			squareonright = mentalmap[this.mentalmapxlocation + 1][this.mentalmapylocation];
		}
		else if (currentHeading == Heading.EAST) {
			squareonright = mentalmap[this.mentalmapxlocation][this.mentalmapylocation + 1];
		}
		else if (currentHeading == Heading.SOUTH) {
			squareonright = mentalmap[this.mentalmapxlocation - 1][this.mentalmapylocation];
		}
		else if (currentHeading == Heading.WEST) {
			squareonright = mentalmap[this.mentalmapxlocation][this.mentalmapylocation - 1];
		}
		return squareonright;
	}

	public void updateMentalMapLocation (Heading currentHeading, Direction nextDirection) {
		if (currentHeading == Heading.NORTH) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapylocation--;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapxlocation++;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapylocation++;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapxlocation--;
			}
		}
		else if (currentHeading == Heading.EAST) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapxlocation++;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapylocation++;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapxlocation--;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapylocation--;
			}
		}
		else if (currentHeading == Heading.SOUTH) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapylocation++;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapxlocation--;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapylocation--;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapxlocation++;
			}
		}
		else if (currentHeading == Heading.WEST) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapxlocation--;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapylocation--;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapxlocation++;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapylocation++;
			}
		}
	}

	// public Direction scoutmap(Heading currentHeading) {
	// 	Direction dir = Direction.IDLE;
	// 	if (initialscoutturndone == false) {
	// 		while (scoutturncounter < 4) {
	// 			scoutturncounter++;
	// 			return dir = doscoutturn(currentHeading);
	// 		}
	// 		scoutturncounter = 0;
	// 		initialscoutturndone = true;
	// 	}
	// 	// if Place in front is usable, unfortunately there is no way to do this that is more elegant in my opinion
	// 	if (checksquareinfrontusable(currentHeading) == true) {
	// 		// check if Place on right side of avatar is unusable
	// 		if (checksquareonright(currentHeading) == Places.BAR || checksquareonright(currentHeading) == Places.POOL || checksquareonright(currentHeading) == Places.TOILET || checksquareonright(currentHeading) == Places.FUSSBALL || checksquareonright(currentHeading) == Places.DJ || checksquareonright(currentHeading) == Places.BOUNCER || checksquareonright(currentHeading) == Places.WALL || checksquareonright(currentHeading) == Places.OUTSIDE) {
	// 			// move forward if you have completed the scoutturn
	// 			if (havemovedforward == false) {
	// 				havemovedforward = true;
	// 				dir = Direction.FORWARD;
	// 				updateMentalMapLocation(currentHeading, dir);
	// 				return dir;
	// 			}
	// 			else {
	// 				while (scoutturncounter < 4) {
	// 					scoutturncounter++;
	// 					return dir = doscoutturn(currentHeading);
	// 				}
	// 				scoutturncounter = 0;
	// 				havemovedforward = false;
	// 			}
	// 		}
	// 		// if square on right is PATH, so usable
	// 		else if ((checksquareonright(currentHeading) == Places.PATH || checksquareonright(currentHeading) == null) && turnrightdone == false) {
	// 				dir = Direction.RIGHT;
	// 				updateMentalMapLocation(currentHeading, dir);
	// 				turnrightdone = true;
	// 				return dir;
	// 		}
	// 		// if there is a person in front, wait a turn for them to leave
	// 		else {
	// 			return dir = Direction.IDLE;
	// 		}
	// 	}
	// 	else if (this.getWhatISee()[1] == Places.PATH) {
	// 		while (scoutturncounter < 4) {
	// 			scoutturncounter++;
	// 			return dir = doscoutturn(currentHeading);
	// 		}
	// 		scoutturncounter = 0;

	// 	}
	// 	return dir;
	// }

	public Direction scoutmap(Heading currentHeading) {
		Direction dir = Direction.IDLE;
		boolean squareinfrontusable = checksquareinfrontusable(currentHeading);
		if (movedlastturn == true) {
			scoutturncounter = 0;
			movedlastturn = false;
		}
		if (squareinfrontusable == true) {
			if (checksquareonrightusable(currentHeading) == true) {
				if (turnrightdone == true) {
					dir = Direction.FORWARD;
					while (scoutturncounter < 4) {
						scoutturncounter++;
						Direction nextmove = doscoutturn(currentHeading);
						return nextmove;
					}
					movedlastturn = true;
				}
				else {
					dir = Direction.RIGHT;
					while (scoutturncounter < 4) {
						scoutturncounter++;
						Direction nextmove = doscoutturn(currentHeading);
						return nextmove;
					}
					turnrightdone = true;
					movedlastturn = true;
				}
			}
			else {
				dir = Direction.FORWARD;
				while (scoutturncounter < 4) {
					scoutturncounter++;
					Direction nextmove = doscoutturn(currentHeading);
					return nextmove;
				}
				movedlastturn = true;
			}
		}
		else {
			dir = Direction.LEFT;
			while (scoutturncounter < 4) {
				scoutturncounter++;
				Direction nextmove = doscoutturn(currentHeading);
				return nextmove;
			}
			turnrightdone = false;
			movedlastturn = true;
		}
		return dir;
	}

	public boolean checksquareonrightusable (Heading currentHeading) {
		Places temp = checksquareonright(currentHeading);
		if (temp == Places.PATH || temp == null) {
			return true;
		}
		else {
			return false;
		}
	}


	// this will check if the square in front is usable but will NOT check if there is a person there
	public boolean checksquareinfrontusable(Heading currentHeading) {

		if (this.getWhatISee()[1] == Places.BAR || this.getWhatISee()[1] == Places.POOL 
		|| this.getWhatISee()[1] == Places.TOILET || this.getWhatISee()[1] == Places.FUSSBALL 
		|| this.getWhatISee()[1] == Places.DJ || this.getWhatISee()[1] == Places.BOUNCER 
		|| this.getWhatISee()[1] == Places.WALL || this.getWhatISee()[1] == Places.OUTSIDE) {
			return false;
		}
		else {
			return true;
		}
	}

	public Direction moveAvatar() {
		// new movement with local storage of surroundings
		// at start of method set Direction to idle in case the other methods don't execute and update it
		if (arrayvisualised == false) {
			arrayvisualised = true;
			visualizeArray(mentalmap);
		}
		updateArray(mentalmap);
		Direction dir = Direction.FORWARD;

		// only scout the map once at the start of the program
		if (k == 0) {
			dir = scoutmap(currentHeading);
			return dir;
		}		
		
		// only do this once at the beginning to scout the area
		// squaresinvision = this.getWhatISee();
		// Direction dir = Direction.FORWARD;
		// if (this.firstmovesfinished == 0) {
			
		// }
		// setting desire to 0 means there is currently no desire
		// setting desire to 6 for now to achieve dance floor challenge
		// int desire = 0;
		// this.setAlcoholPercentage(0);


		// TODO
		// create an algorithm that determines the next step of your movement pattern
		// based on a set of priorities.
		// Direction dir = Direction.IDLE;
		// return dir;

		//The following lines make the avatar move randomly
		/* Random rand = new Random();
		int number = rand.nextInt(4);
		// direction is set externally --> check with the simulation environment
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
		} */
		// return dir;

		// if (this.getWhatISee()[1] == Places.DANCEFLOOR) {
		// 	Direction direction = dancingAlgo();
		// 	return direction;
		// }
		// Direction dir = Direction.FORWARD;
		// for (j++; j < 15;) {
		// 	dir = Direction.FORWARD;
		// 	return dir;
		// }
 		
		// if (i == 0) {
		// 	dir = Direction.LEFT;
		// 	i = 1;
		// 	return dir;
		// }
		
		// for (k++; k < 2;) {
		// 	dir = Direction.FORWARD;
		// 	return dir;
		// }
		// dir = Direction.TURN_LEFT_ON_SPOT;


		// End of code for random movement
		// First check if any immediate desires need to be fulfilled
		// Check if avatar needs toilet

		// decide what to do based on generated number
		// 1 - foosball
		// 2 - pool
		// 3 - bar
		// 4 - lounge
		// 5 - dj
		// 6 - dancefloor
		// 7 - toilet
		// 8 - talk
		// 9 - fight
		// 10 - go to bar and get water
		// if (this.getAlcoholPercentage() > 60) {
		// 	desire = 10;
		// }
		// // if alcoholpercentage is too high then start a fight
		// else if (this.getAlcoholPercentage() > 80) {
		// 	desire = 9;
		// 	// add code here to fight the next avatar that appears around you
		// }
		// check what is in front of the avatar and interact with the place if the current desire can be met there

		// The following lines make the avatar move randomly
		//Random rand = new Random();
		//int number = rand.nextInt(4);
		// direction is set externally --> check with the simulation environment

		return dir;
	}

	private int decideDesire() {
		int desire = 0;
		Random rand = new Random();
		// pick random number between 0 and 100
		int number = rand.nextInt(100);
		// decide what to do based on generated number
		// 1 - foosball
		// 2 - pool
		// 3 - bar
		// 4 - lounge
		// 5 - dj
		// 6 - dancefloor
		if (number >= 0 && number < 30) {
			desire = 1;
		}
		else if (number >= 30 && number < 60) {
			desire = 2;
		}
		else if (number >= 60 && number < 80) {
			desire = 3;
		}
		else if (number >= 80 && number < 90) {
			desire = 4;
		}
		else if (number >= 90 && number < 95) {
			desire = 5;
		}
		else if (number >= 95 && number < 100) {
			desire = 6;
		}
		return desire;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}
}