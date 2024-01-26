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

	boolean initialscoutturndone = false;
	boolean walkedtodrink = false;
	boolean arrayvisualised = false;
	boolean havemovedforward = false;
	boolean haveturnedright = false;
	boolean mapscoutingdone = false;
	boolean gotobouncerdone = false;
	boolean gotobarstooldone = false;
	boolean onemoveforwarddone = false;
	boolean dancefinished = false;
	boolean dancedforwardonce = false;
	boolean drinkordered = false;
	boolean printedloungestatement = false;
	int dancecircles = 0;
	int scoutturncounter = 0;
	int mentalmapxlocationatstartofdance;
	int mentalmapylocationatstartofdance;
	boolean firstMoveAfterEnteringBarDone = false;
	boolean turnrightdone = false;
	boolean movedlastturn = false;
	boolean initialwestturndone = false;
	int[] barstoolsxlocations = new int[5];
	int[] barstoolsylocations = new int[5];
	int barstoolsfoundcounter = 0;
	int turnedtomove = 0;
	int walktobarstoolcounter = 0;
	int threemovesforward = 0;

	// to visualise the mental map
	public static JPanel[][] panels;

	// starting coordinates in own mental map
	// the current location in the mental map will be augmented with these
	int mentalmapxlocation = 50;
	int mentalmapylocation = 50;
	int mentalmapxlocationstart = 50;
	int mentalmapylocationstart = 50;

	// save current heading locally, start off facing WEST after entering bar
	Heading currentHeading = Heading.WEST;

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
	public Direction dancingAlgo(Heading heading) {
		// TODO
		// develop the type of movement that would represent your dance pattern
		Direction dir = Direction.IDLE;
		if (getWhatISee()[1] == Places.PERSON) {
			dir = Direction.IDLE;
			return dir;
		}
		if (dancecircles < 1) {
			if (dancedforwardonce == false) {
				dir = Direction.FORWARD;
				updateMentalMap(getWhatISee(), heading);
				updateMentalMapLocation(heading, dir);
				turnedtomove = 0;
				dancedforwardonce = true;
				return dir;
			}
			if (mentalmapxlocation != mentalmapxlocationatstartofdance || mentalmapylocation != mentalmapylocationatstartofdance) {
				if (getWhatISee()[1] == Places.PERSON) {
					dir = Direction.IDLE;
					return dir;
				}
				else if (turnedtomove > 0) {
					if (getWhatISee()[1] == Places.PERSON) {
						dir = Direction.IDLE;
						return dir;
					}
					dir = Direction.FORWARD;
					updateMentalMap(getWhatISee(), heading);
					updateMentalMapLocation(heading, dir);
					turnedtomove = 0;
					return dir;
				}
				else if (turnedtomove == 0) {
					dir = Direction.TURN_RIGHT_ON_SPOT;
					updateHeading(heading, dir);
					turnedtomove++;
					return dir;
				}
			}
			dancecircles++;
		}
		dancefinished = true;
		initialwestturndone = false;
		movedlastturn = false;
		return dir;
	}

	public Direction walktodrink(Heading heading) {
		Direction dir = Direction.IDLE;
		// turn west
		if (initialwestturndone == false) {
			if (heading == Heading.NORTH) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.EAST) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.SOUTH) {
				dir = Direction.TURN_RIGHT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.WEST) {
				initialwestturndone = true;
				movedlastturn = false;
				scoutturncounter = 0;
			}
		}
		if (getWhatISee()[1] != Places.PERSON) {
			if (movedlastturn == false) {
				if (checksquareonright(heading) != Places.BAR_CHAIR && turnrightdone == false) {
					if (getWhatISee()[1] == Places.PERSON) {
						dir = Direction.IDLE;
						return dir;
					}
					dir = Direction.FORWARD;
					// updateMentalMap(getWhatISee(), heading);
					updateMentalMapLocation(heading, dir);
					movedlastturn = true;
					scoutturncounter = 0;
					return dir;
				}
				else {
					if (turnrightdone == false) {
						dir = Direction.TURN_RIGHT_ON_SPOT;
						updateHeading(heading, dir);
						turnrightdone = true;
						return dir;
					}
					else {
						if (getWhatISee()[1] == Places.PERSON) {
							dir = Direction.IDLE;
							return dir;
						}
						dir = Direction.FORWARD;
						walkedtodrink = true;
						updateMentalMapLocation(heading, dir);
						return dir;
					}
				}
			}
			else if (movedlastturn == true) {
				while (scoutturncounter < 4) {
					scoutturncounter++;
					Direction nextmove = doscoutturn(heading);
					return nextmove;
				}
				movedlastturn = false;
			}
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
        int cols = array.length;
        int rows = array[0].length;

        // Create a JFrame
        JFrame frame = new JFrame("Mental Map Visualisation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new GridLayout(cols, rows));

        panels = new JPanel[cols][rows];

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
        else if (mentalmap == Places.PATH) {
			return Color.WHITE;
		}
		else {
			return Color.GRAY;
		}
    }

	public static void updateArray(Places[][] array) {
        int cols = array.length;
        int rows = array[0].length;

        // Update the panel backgrounds with the array values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                panels[i][j].setBackground(getColorForValue(array[i][j]));
            }
        }
    }

	public void updateHeading(Heading heading, Direction nextdir) {
		if (heading == Heading.NORTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.WEST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.EAST;
			}
		}

		else if (heading == Heading.EAST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.NORTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.SOUTH;
			}
		}

		else if (heading == Heading.SOUTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.EAST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.WEST;
			}
		}

		else if (heading == Heading.WEST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT) {
				currentHeading = Heading.SOUTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT) {
				currentHeading = Heading.NORTH;
			}
		}
	}

	// this method adds the Place in front of the avatar to the mental map
	public void updateMentalMap (Places[] WhatISee, Heading heading) {
		// if (WhatISee[1] != Places.PERSON) {
			if (heading == Heading.NORTH) {
				mentalmap[mentalmapylocation - 1][mentalmapxlocation] = WhatISee[1];
			}
			else if (heading == Heading.EAST) {
				mentalmap[mentalmapylocation][mentalmapxlocation + 1] = WhatISee[1];
			}
			else if (heading == Heading.SOUTH) {
				mentalmap[mentalmapylocation + 1][mentalmapxlocation] = WhatISee[1];
			}
			else if (heading == Heading.WEST) {
				mentalmap[mentalmapylocation][mentalmapxlocation - 1] = WhatISee[1];
			}
		// }
	}

	public Direction doscoutturn(Heading heading) {
		// set Direction to idle in case no other decision can be made here
		// Direction dir = Direction.IDLE;
		// while (scoutturn < 4) {
		Direction dir = Direction.TURN_LEFT_ON_SPOT;
		if (this.getWhatISee()[1] == Places.PERSON) {
			scoutturncounter--;
			return dir = Direction.IDLE;
		}
		updateMentalMap(this.getWhatISee(), heading);
		updateHeading(heading, dir);
		//scoutturn++;
		return dir;
		// }
		// scoutturn = 0;
		// return dir;
	}

	public Places checksquareonright(Heading heading) {
		// Failsafe: set squareonright to WALL in case the function fails so it at least returns something. 
		// WALL is the least critical thing to put in mental map as it only results in avoiding 
		// some squares in the worst case instead of trying to use invalid squares if e.g. PATH
		// had been saved as the failsafe option
		Places squareonright = Places.WALL;
		if (heading == Heading.NORTH) {
			squareonright = mentalmap[this.mentalmapylocation][this.mentalmapxlocation + 1];
		}
		else if (heading == Heading.EAST) {
			squareonright = mentalmap[this.mentalmapylocation + 1][this.mentalmapxlocation];
		}
		else if (heading == Heading.SOUTH) {
			squareonright = mentalmap[this.mentalmapylocation][this.mentalmapxlocation - 1];
		}
		else if (heading == Heading.WEST) {
			squareonright = mentalmap[this.mentalmapylocation - 1][this.mentalmapxlocation];
		}
		return squareonright;
	}

	public void updateMentalMapLocation (Heading heading, Direction nextDirection) {
		if (heading == Heading.NORTH) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapylocation--;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapxlocation++;
				heading = Heading.EAST;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapylocation++;
				heading = Heading.SOUTH;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapxlocation--;
				heading = Heading.WEST;
			}
		}
		else if (heading == Heading.EAST) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapxlocation++;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapylocation++;
				heading = Heading.SOUTH;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapxlocation--;
				heading = Heading.WEST;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapylocation--;
				heading = Heading.NORTH;
			}
		}
		else if (currentHeading == Heading.SOUTH) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapylocation++;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapxlocation--;
				heading = Heading.WEST;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapylocation--;
				heading = Heading.NORTH;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapxlocation++;
				heading = Heading.EAST;
			}
		}
		else if (heading == Heading.WEST) {
			if (nextDirection == Direction.FORWARD) {
				mentalmapxlocation--;
			}
			else if (nextDirection == Direction.RIGHT) {
				mentalmapylocation--;
				heading = Heading.NORTH;
			}
			else if (nextDirection == Direction.BACK) {
				mentalmapxlocation++;
				heading = Heading.EAST;
			}
			else if (nextDirection == Direction.LEFT) {
				mentalmapylocation++;
				heading = Heading.SOUTH;
			}
		}
	}

	public Direction scoutmap(Heading heading) {
		Direction dir = Direction.IDLE;
		boolean squareinfrontusable = checksquareinfrontusable(heading);
		if (movedlastturn == true) {
			scoutturncounter = 0;
			movedlastturn = false;
			firstMoveAfterEnteringBarDone = true;
		}
		if (this.getWhatISee()[1] == Places.TOILET_CHAIR) {
			// turn back and move forward
			if (scoutturncounter > 3 && currentHeading != Heading.NORTH) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			if (getWhatISee()[1] == Places.PERSON) {
				dir = Direction.IDLE;
				return dir;
			}
			dir = Direction.FORWARD;
			while (scoutturncounter < 4) {
				scoutturncounter++;
				Direction nextmove = doscoutturn(heading);
				return nextmove;
			}
			turnedtomove = 0;
			movedlastturn = true;
			updateMentalMapLocation(heading, dir);
			turnrightdone = false;
			return dir;
		}
		if (squareinfrontusable == true) {
			if (checksquareonrightusable(heading) == true) {
				if (turnrightdone == true) {
					if (getWhatISee()[1] == Places.PERSON) {
						dir = Direction.IDLE;
						return dir;
					}
					dir = Direction.FORWARD;
					while (scoutturncounter < 4) {
						scoutturncounter++;
						Direction nextmove = doscoutturn(heading);
						return nextmove;
					}
					movedlastturn = true;
					updateMentalMapLocation(heading, dir);
				}
				else {
					// turn right and move forward
					if (turnedtomove == 0 && scoutturncounter > 3) {
						turnedtomove++;
						dir = Direction.TURN_RIGHT_ON_SPOT;
						updateHeading(heading, dir);
					}
					else if (turnedtomove == 1 && scoutturncounter > 3) {
						turnedtomove = 0;
						if (getWhatISee()[1] == Places.PERSON) {
							dir = Direction.IDLE;
							return dir;
						}
						dir = Direction.FORWARD;
						turnrightdone = true;
						movedlastturn = true;
						updateMentalMapLocation(heading, dir);
					}
					while (scoutturncounter < 4) {
						scoutturncounter++;
						Direction nextmove = doscoutturn(heading);
						return nextmove;
					}
				}
			}
			else {
				if (getWhatISee()[1] == Places.PERSON) {
					dir = Direction.IDLE;
					return dir;
				}
				dir = Direction.FORWARD;
				while (scoutturncounter < 4) {
					scoutturncounter++;
					Direction nextmove = doscoutturn(heading);
					return nextmove;
				}
				turnrightdone = false;
				movedlastturn = true;
				turnedtomove = 0;
				updateMentalMapLocation(heading, dir);
			}
		}
		else {
			if (turnedtomove == 0 && scoutturncounter > 3) {
				turnedtomove++;
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
			}
			else if (turnedtomove == 1 && scoutturncounter > 3) {
				turnedtomove = 0;
				if (getWhatISee()[1] == Places.PERSON) {
					dir = Direction.IDLE;
					return dir;
				}
				dir = Direction.FORWARD;
				turnrightdone = false;
				movedlastturn = true;
				updateMentalMapLocation(heading, dir);
			}
			while (scoutturncounter < 4) {
				scoutturncounter++;
				Direction nextmove = doscoutturn(heading);
				return nextmove;
			}
		}
		return dir;
	}

	public boolean checksquareonrightusable (Heading heading) {
		Places temp = checksquareonright(heading);
		if (temp == Places.PATH || temp == null) {
			return true;
		}
		else {
			return false;
		}
	}


	// this will check if the square in front is usable but will NOT check if there is a person there
	public boolean checksquareinfrontusable(Heading heading) {

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

	public void findBarStools () {
		//we find the barstool first because the dancefloor location is not known yet. if we go to the barstool we will find the dancefloor
		for (int z = 0; z < mentalmap.length; z++) {
			for (int y = 0; y < mentalmap[0].length; y++) {
				if (mentalmap[z][y] == Places.BAR_CHAIR && barstoolsfoundcounter < barstoolsxlocations.length) {
					barstoolsylocations[barstoolsfoundcounter] = z;
					barstoolsxlocations[barstoolsfoundcounter] = y;
					barstoolsfoundcounter++;
				}
			}
		}
	}

	public Direction movetobouncer(Heading heading) {
		Direction dir = Direction.IDLE;
		if (this.getWhatISee()[1] == Places.BOUNCER) {
			gotobouncerdone = true;
			return dir;
		}
		if (getWhatISee()[1] == Places.PERSON) {
			dir = Direction.IDLE;
			return dir;
		}
		if (heading == Heading.NORTH) {
			dir = Direction.FORWARD;
			updateMentalMapLocation(heading, dir);
		}
		else if (heading == Heading.EAST) {
			dir = Direction.LEFT;
			updateMentalMapLocation(heading, dir);
		}
		else if (heading == Heading.SOUTH) {
			dir = Direction.BACK;
			updateMentalMapLocation(heading, dir);
		}
		else if (heading == Heading.WEST) {
			dir = Direction.RIGHT;
			updateMentalMapLocation(heading, dir);
		}
		return dir;
	}

	public Direction gotobarstool(Heading heading) {
		Direction dir = Direction.IDLE;
		// turn west at beginning
		if (initialwestturndone == false) {
			if (heading == Heading.NORTH) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.EAST) {
				dir = Direction.TURN_LEFT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.SOUTH) {
				dir = Direction.TURN_RIGHT_ON_SPOT;
				updateHeading(heading, dir);
				return dir;
			}
			else if (heading == Heading.WEST) {
				initialwestturndone = true;
			}
		}
		// begin moving diagonally towards the bar stool in the middle
		if (threemovesforward < 3) {
			if (getWhatISee()[1] == Places.PERSON) {
				dir = Direction.IDLE;
				return dir;
			}
			dir = Direction.FORWARD;
			threemovesforward++;
			updateMentalMap(getWhatISee(), heading);
			updateMentalMapLocation(heading, dir);
			return dir;
		}
		else if (threemovesforward == 3) {
			turnedtomove++;
			threemovesforward++;
			dir = Direction.TURN_LEFT_ON_SPOT;
			updateHeading(heading, dir);
			return dir;
		}
		if (turnedtomove > 0) {
			if (getWhatISee()[1] == Places.PERSON) {
				dir = Direction.IDLE;
				return dir;
			}
			dir = Direction.FORWARD;
			updateMentalMap(getWhatISee(), heading);
			updateMentalMapLocation(heading, dir);
			turnedtomove = 0;
		}
		else if (turnedtomove == 0) {
			dir = Direction.TURN_RIGHT_ON_SPOT;
			updateHeading(heading, dir);
			threemovesforward = 0;
			return dir;
		}
		return dir;
	}

	public Direction moveAvatar() {
		// new movement with local storage of surroundings
		// at start of method set Direction to idle in case the other methods don't execute and update it
		if (arrayvisualised == false) {
			arrayvisualised = true;
			visualizeArray(mentalmap);
		}
		updateArray(mentalmap);
		Direction dir = Direction.IDLE;

		// only scout the map once at the start of the program
		if (mapscoutingdone == false) {
			dir = scoutmap(currentHeading);
			if (mentalmapxlocation == mentalmapxlocationstart && mentalmapylocation == mentalmapylocationstart && firstMoveAfterEnteringBarDone == true) {
				mapscoutingdone = true;
				turnedtomove = 0;
			}
			return dir;
		}
		// search scouted area for bar stools and save their locations into an array
		findBarStools();

		//head north to corner of BOUNCER and WALL area
		if (gotobouncerdone == false) {
			dir = movetobouncer(currentHeading);
			return dir;
		}

		else if (gotobarstooldone == false) {
			dir = gotobarstool(currentHeading);
			if (this.getWhatISee()[1] == Places.DANCEFLOOR) {
				gotobarstooldone = true;
				updateMentalMap(getWhatISee(), currentHeading);
				turnedtomove = 1;
				mentalmapxlocationatstartofdance = mentalmapxlocation;
				mentalmapylocationatstartofdance = mentalmapylocation;
			}
			return dir;
		}

		else if (dancefinished == false) {
			dir = dancingAlgo(currentHeading);
			return dir;
		}

		else if (walkedtodrink == false) {
			dir = walktodrink(currentHeading);
			return dir;
		}

		else if (walkedtodrink == true && drinkordered == false) {
			drink(BeverageType.BEER);
			drinkordered = true;
			scoutturncounter = 0;
			// System.out.println("Bernhard ordered a beer");
		}

		else if (drinkordered == true) {
			dir = Direction.IDLE;
			if (getWhatISee()[0] != Places.LOUNGE_BIG) {
				if (getWhatISee()[1] == Places.PERSON) {
					dir = Direction.IDLE;
					return dir;
				}
				while (scoutturncounter < 4) {
					scoutturncounter++;
					Direction nextmove = doscoutturn(currentHeading);
					return nextmove;
				}
				dir = Direction.FORWARD;
				scoutturncounter = 0;
				updateMentalMapLocation(currentHeading, dir);
				return dir;
			}
			System.out.println("Bernhard is sitting in the lounge");
		}
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		System.out.println("Bernhard ordered a " + type);
		switch (type) {
			case BEER:
				this.setAlcoholPercentage(5);
				break;
			case VODKA:
				this.setAlcoholPercentage(20);
			case MOJITO:
				this.setAlcoholPercentage(35);
			case RUM_AND_COKE:
				this.setAlcoholPercentage(30);
			case GIN_TONIC:
				this.setAlcoholPercentage(30);
			case APEROL_SPRITZ:
				this.setAlcoholPercentage(15);
			case WATER:
				this.setAlcoholPercentage(0);
			default:
				this.setAlcoholPercentage(0);
		}
	}
}