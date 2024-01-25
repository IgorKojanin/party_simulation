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
import java.awt.Point;
import java.util.Random;

import javax.swing.SwingUtilities;

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
	private Places[] PlacesArroundMe;
	private boolean isEntered = false;
	public Places[][] myMap;
	private int myY;
	private int myX;
	private Heading myHeading;
	private int countTurn;
	private int danceCount;
	private int stand;
	private int inFront;
	private int wallPong; // 0 ausgehend von wand links, 1 ausgehend von wand rechts
	private int shift; // 0 nach norden suchen 1 nach süden suchen
	private boolean hinderniss;
	private int wallRight;
	private int wallLeft;
	private thorvinsFrame customJFrame;
	private int bucketCheck;
	private int waitNext;
	private boolean goBarDone;

	// ************** Constructor **************
	public Thorvin(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);

		goal = getAction(); // ersten Plan schmieden
		myMap = new Places[85][65];
		myY = 40;
		myX = 30;
		myHeading = Heading.WEST;
		PlacesArroundMe = new Places[1];
		countTurn = 0;
		danceCount = 1;
		stand = 0;
		inFront = 1;
		wallPong = 0;
		hinderniss = false;
		wallLeft = 100;
		wallRight = 100;
		shift = 0;
		bucketCheck = 0; // noch nix erreicht
		goBarDone = false;
		waitNext = 0;
		for (int i = 0; i < myMap.length; i++) {
			for (int j = 0; j < myMap[i].length; j++) {
				myMap[i][j] = null;
			}
		}

		SwingUtilities.invokeLater(() -> {
			// Hier können Sie Ihr Places-Array initialisieren und an die Klasse übergeben

			customJFrame = new thorvinsFrame(myMap);
			customJFrame.setVisible(true);
		});
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
		Direction dir = Direction.FORWARD;

		if (bucketCheck == 0) {
			dir = doScout();
		} else if (bucketCheck == 1) {
			waitNext++;
			dir = Direction.LEFT;
			if (waitNext == 4) {
				bucketCheck = 2;
			}
		} else if (bucketCheck == 2) {
			dir = doScout();
		} else if (bucketCheck == 3 || bucketCheck == 4) {
			dir = goBar(lookForBar());
			if (goBarDone && myHeading == Heading.WEST) {
				countTurn = 4;
				goBarDone = false;
				bucketCheck = 5;
				waitNext = 6;
			}
		} else if (bucketCheck == 5) {
			dir = doScout();
		} else if (bucketCheck == 6 || bucketCheck == 7) {
			dir = goLounge(lookForLounge());
			if (goBarDone && myHeading == Heading.WEST) {
				countTurn = 4;
				goBarDone = false;
				bucketCheck = 8;
				waitNext = 7;
			}
		} else if (bucketCheck == 6 || bucketCheck == 7) {
			dir = goLounge(lookForLounge());
			if (goBarDone && myHeading == Heading.WEST) {
				countTurn = 4;
				goBarDone = false;
				bucketCheck = 8;
				waitNext = 7;
			}
		} else if (bucketCheck == 8) {
			dir = doScout();
			System.out.println("Fertig");
		}
		// ------------------------------------------------------------------

		if (inTanzbereich() && waitNext < 4) { // erste aufgabe abarbeiten
			bucketCheck = 1;

		} else if (bucketCheck == 2) { // nun suche ich nach nem bar platz
			if (lookForBar() != 42) {
				bucketCheck = 3;
			}
		} else if (bucketCheck == 5) {
			if (lookForLounge() != 42) {
				bucketCheck = 6;
			}
		}

		customJFrame.updateMap(myMap); // für anzeige Map
		System.out.println(myHeading);
		System.out.println(dir);
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink. The update alcohol levels happens automatically!
		// TODO
		// increase the drunkness level and subsequently make it lose coordination
	}

	private Places getAction() { // what i want to do next
		Random rand = new Random();
		int number = rand.nextInt(100);
		timeToLeave = number;

		if (number < 40) { // 40% warscheinlichkeit für Alkohol
			return Places.BAR;
		} else if (number < 60) {
			return Places.DANCEFLOOR; // Tanzen
		} else if (number < 80) {
			return Places.LOUNGE_SMOKING; // Rauchen
		} else
			return Places.TOILET; // Toilette

	}

	private boolean doLeave() { // decides if the avatar wants to stay or leafe
		timeToLeave = timeToLeave - 10;
		if (timeToLeave <= 0) {
			return true;
		} else {
			return false;
		}
	}

	private Direction doTurn() { // erkunden der Karte
		Direction dir = Direction.IDLE;
		Places[] placesArroundMe = this.getWhatISee();

		if (myHeading == Heading.WEST) {
			myMap[myX + 1][myY] = placesArroundMe[inFront];
			dir = Direction.TURN_LEFT_ON_SPOT;
			myHeading = Heading.SOUTH;
			// System.out.println( myMap[myX+1][myY]);
			return dir;
		} else if (myHeading == Heading.SOUTH) {
			myMap[myX][myY - 1] = placesArroundMe[inFront];
			myHeading = Heading.EAST;
			// System.out.println( myMap[myX][myY-1]);
			if (placesArroundMe[inFront] == Places.WALL) {
				wallLeft = myY - 1;
			}
			return Direction.TURN_LEFT_ON_SPOT;
		} else if (myHeading == Heading.NORTH) {
			myMap[myX][myY + 1] = placesArroundMe[inFront];
			myHeading = Heading.WEST;
			// System.out.println(myMap[myX][myY+1]);
			if (placesArroundMe[inFront] == Places.WALL) {
				wallRight = myY + 1;
			}
			return Direction.TURN_LEFT_ON_SPOT;
		} else if (myHeading == Heading.EAST) {
			myMap[myX - 1][myY] = placesArroundMe[inFront];
			myHeading = Heading.NORTH;
			// System.out.println(myMap[myX-1][myY]);
			return Direction.TURN_LEFT_ON_SPOT;
		}
		return dir;
	}

	private Direction doMove() {
		Direction dir = Direction.IDLE;
		Places[] placesArroundMe = this.getWhatISee();
		if (shift < 2) {

			if (myMap[myX][myY + 1] != Places.WALL && shift == 0) {
				// suche nach rechts
				if ((myMap[myX + 1][myY] == Places.WALL || hinderniss == true) && wallPong == 0) {
					wallPong = 1; // wand links erreicht
					hinderniss = false;
					if (myMap[myX][myY + 1] == Places.PATH || myMap[myX][myY + 1] == Places.DANCEFLOOR
							|| myMap[myX][myY + 1] == Places.BAR_CHAIR || myMap[myX][myY + 1] == Places.FUSSBALL_CHAIR
							|| myMap[myX][myY + 1] == Places.LOUNGE_BIG || myMap[myX][myY + 1] == Places.LOUNGE_SMALL
							|| myMap[myX][myY + 1] == Places.LOUNGE_SMOKING
							|| myMap[myX][myY + 1] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myY = myY + 1;
						myHeading = Heading.NORTH;
						return Direction.RIGHT;
					}
				} else if ((myMap[myX - 1][myY] == Places.WALL || hinderniss == true) && wallPong == 1) {
					wallPong = 0; // wand rechts errweicht
					hinderniss = false;
					if (myMap[myX][myY + 1] == Places.PATH || myMap[myX][myY + 1] == Places.DANCEFLOOR
							|| myMap[myX][myY + 1] == Places.BAR_CHAIR || myMap[myX][myY + 1] == Places.FUSSBALL_CHAIR
							|| myMap[myX][myY + 1] == Places.LOUNGE_BIG || myMap[myX][myY + 1] == Places.LOUNGE_SMALL
							|| myMap[myX][myY + 1] == Places.LOUNGE_SMOKING
							|| myMap[myX][myY + 1] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myY = myY + 1;
						myHeading = Heading.NORTH;
						return Direction.RIGHT;
					}
				}
			} else if (shift < 2) {
				shift = 1;
				if ((myMap[myX + 1][myY] == Places.WALL || hinderniss == true) && wallPong == 0) {
					wallPong = 1; // wand links erreicht
					hinderniss = false;
					if (myMap[myX][myY - 1] == Places.PATH || myMap[myX][myY - 1] == Places.DANCEFLOOR
							|| myMap[myX][myY - 1] == Places.BAR_CHAIR || myMap[myX][myY - 1] == Places.FUSSBALL_CHAIR
							|| myMap[myX][myY - 1] == Places.LOUNGE_BIG || myMap[myX][myY - 1] == Places.LOUNGE_SMALL
							|| myMap[myX][myY - 1] == Places.LOUNGE_SMOKING
							|| myMap[myX][myY - 1] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myY = myY - 1;
						myHeading = Heading.SOUTH;
						return Direction.LEFT;
					}
				} else if ((myMap[myX - 1][myY] == Places.WALL || hinderniss == true) && wallPong == 1) {
					wallPong = 0; // wand rechts errweicht
					hinderniss = false;
					if (myMap[myX][myY - 1] == Places.PATH || myMap[myX][myY - 1] == Places.DANCEFLOOR
							|| myMap[myX][myY - 1] == Places.BAR_CHAIR || myMap[myX][myY - 1] == Places.FUSSBALL_CHAIR
							|| myMap[myX][myY - 1] == Places.LOUNGE_BIG || myMap[myX][myY - 1] == Places.LOUNGE_SMALL
							|| myMap[myX][myY - 1] == Places.LOUNGE_SMOKING
							|| myMap[myX][myY - 1] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myY = myY - 1;
						myHeading = Heading.SOUTH;
						return Direction.LEFT;
					}
				}
				if (myMap[myX][myY - 1] == Places.WALL) {// bin an der anderen Seite angekommen nun vertikal suchen
					shift = 2;
				}
			}

			if (wallPong == 0) { // von rechts nach links
				if (myMap[myX + 1][myY] == Places.PATH || myMap[myX + 1][myY] == Places.DANCEFLOOR
						|| myMap[myX + 1][myY] == Places.BAR_CHAIR || myMap[myX + 1][myY] == Places.FUSSBALL_CHAIR
						|| myMap[myX + 1][myY] == Places.LOUNGE_BIG || myMap[myX + 1][myY] == Places.LOUNGE_SMALL
						|| myMap[myX + 1][myY] == Places.LOUNGE_SMOKING || myMap[myX + 1][myY] == Places.POOL_CHAIR) {
					myX = myX + 1;
					myHeading = Heading.WEST;
					return Direction.FORWARD;
				} else {
					hinderniss = true;
				}
			} else if (wallPong == 1) { // von links nach rechts
				if (myMap[myX - 1][myY] == Places.PATH || myMap[myX - 1][myY] == Places.DANCEFLOOR
						|| myMap[myX - 1][myY] == Places.BAR_CHAIR || myMap[myX - 1][myY] == Places.FUSSBALL_CHAIR
						|| myMap[myX - 1][myY] == Places.LOUNGE_BIG || myMap[myX - 1][myY] == Places.LOUNGE_SMALL
						|| myMap[myX - 1][myY] == Places.LOUNGE_SMOKING || myMap[myX - 1][myY] == Places.POOL_CHAIR) {
					myX = myX - 1;
					myHeading = Heading.EAST;
					return Direction.BACK;
				}

				else {
					hinderniss = true;
				}
			}
			// ------------------------------------------------------------------------------------------------------
		} else if (shift > 1) {
			if (myMap[myX + 1][myY] != Places.WALL && shift == 2) {
				// suche nach rechts
				if ((myMap[myX][myY + 1] == Places.WALL || hinderniss == true) && wallPong == 0) {
					wallPong = 1; // wand links erreicht
					hinderniss = false;
					if (myMap[myX + 1][myY] == Places.PATH || myMap[myX + 1][myY] == Places.DANCEFLOOR
							|| myMap[myX + 1][myY] == Places.BAR_CHAIR || myMap[myX + 1][myY] == Places.FUSSBALL_CHAIR
							|| myMap[myX + 1][myY] == Places.LOUNGE_BIG || myMap[myX + 1][myY] == Places.LOUNGE_SMALL
							|| myMap[myX + 1][myY] == Places.LOUNGE_SMOKING
							|| myMap[myX + 1][myY] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myX = myX + 1;
						myHeading = Heading.WEST;
						return Direction.FORWARD;
					}
				} else if ((myMap[myX - 1][myY] == Places.WALL || hinderniss == true) && wallPong == 1) {
					wallPong = 0; // wand rechts errweicht
					hinderniss = false;
					if (myMap[myX + 1][myY] == Places.PATH || myMap[myX + 1][myY] == Places.DANCEFLOOR
							|| myMap[myX + 1][myY] == Places.BAR_CHAIR || myMap[myX + 1][myY] == Places.FUSSBALL_CHAIR
							|| myMap[myX + 1][myY] == Places.LOUNGE_BIG || myMap[myX + 1][myY] == Places.LOUNGE_SMALL
							|| myMap[myX + 1][myY] == Places.LOUNGE_SMOKING
							|| myMap[myX + 1][myY] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myX = myX + 1;
						myHeading = Heading.WEST;
						return Direction.FORWARD;
					}
				}
			} else if (shift < 4) {
				shift = 3;
				if ((myMap[myX + 1][myY] == Places.WALL || hinderniss == true) && wallPong == 0) {
					wallPong = 1; // wand links erreicht
					hinderniss = false;
					if (myMap[myX - 1][myY] == Places.PATH || myMap[myX - 1][myY] == Places.DANCEFLOOR
							|| myMap[myX - 1][myY] == Places.BAR_CHAIR || myMap[myX - 1][myY] == Places.FUSSBALL_CHAIR
							|| myMap[myX - 1][myY] == Places.LOUNGE_BIG || myMap[myX - 1][myY] == Places.LOUNGE_SMALL
							|| myMap[myX - 1][myY] == Places.LOUNGE_SMOKING
							|| myMap[myX - 1][myY] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myX = myX - 1;
						myHeading = Heading.EAST;
						return Direction.BACK;
					}
				} else if ((myMap[myX - 1][myY] == Places.WALL || hinderniss == true) && wallPong == 1) {
					wallPong = 0; // wand rechts errweicht
					hinderniss = false;
					if (myMap[myX - 1][myY] == Places.PATH || myMap[myX - 1][myY] == Places.DANCEFLOOR
							|| myMap[myX - 1][myY] == Places.BAR_CHAIR || myMap[myX - 1][myY] == Places.FUSSBALL_CHAIR
							|| myMap[myX - 1][myY] == Places.LOUNGE_BIG || myMap[myX - 1][myY] == Places.LOUNGE_SMALL
							|| myMap[myX - 1][myY] == Places.LOUNGE_SMOKING
							|| myMap[myX - 1][myY] == Places.POOL_CHAIR) {// hier will ich nach rechts versetzen
						myX = myX - 1;
						myHeading = Heading.EAST;
						return Direction.BACK;
					}
				}
				if (myMap[myX - 1][myY] == Places.WALL) {// bin an der anderen Seite angekommen nun vertikal suchen
					shift = 4;
				}
			}

			if (wallPong == 0) { // von rechts nach links
				if (myMap[myX][myY + 1] == Places.PATH || myMap[myX][myY + 1] == Places.DANCEFLOOR
						|| myMap[myX][myY + 1] == Places.BAR_CHAIR || myMap[myX][myY + 1] == Places.FUSSBALL_CHAIR
						|| myMap[myX][myY + 1] == Places.LOUNGE_BIG || myMap[myX][myY + 1] == Places.LOUNGE_SMALL
						|| myMap[myX][myY + 1] == Places.LOUNGE_SMOKING || myMap[myX][myY + 1] == Places.POOL_CHAIR) {
					myY = myY + 1;
					myHeading = Heading.NORTH;
					return Direction.RIGHT;
				} else {
					hinderniss = true;
				}
			} else if (wallPong == 1) { // von links nach rechts
				if (myMap[myX][myY - 1] == Places.PATH || myMap[myX][myY - 1] == Places.DANCEFLOOR
						|| myMap[myX][myY - 1] == Places.BAR_CHAIR || myMap[myX][myY - 1] == Places.FUSSBALL_CHAIR
						|| myMap[myX][myY - 1] == Places.LOUNGE_BIG || myMap[myX][myY - 1] == Places.LOUNGE_SMALL
						|| myMap[myX][myY - 1] == Places.LOUNGE_SMOKING || myMap[myX][myY - 1] == Places.POOL_CHAIR) {
					myY = myY - 1;
					myHeading = Heading.SOUTH;
					return Direction.LEFT;
				}

				else {
					hinderniss = true;
				}
			}

		}

		return dir;
	}

	// Turn in spot until the heading ist west
	private Direction findWest() {
		Direction dir = Direction.IDLE;

		if (myHeading == Heading.SOUTH) {
			dir = Direction.TURN_RIGHT_ON_SPOT;
			myHeading = Heading.WEST;
		} else if (myHeading == Heading.NORTH) {
			dir = Direction.TURN_LEFT_ON_SPOT;
			myHeading = Heading.WEST;
		} else if (myHeading == Heading.EAST) {
			dir = Direction.TURN_LEFT_ON_SPOT;
			myHeading = Heading.NORTH;
		}

		return dir;

	}

	private Direction doScout() {
		Direction dir = Direction.IDLE;
		Places[] placesArroundMe = this.getWhatISee();
		if (myHeading != Heading.WEST && countTurn == 0) { // bevor was passiert immer nach westen orientieren
			dir = findWest();
		} else if (countTurn < 4) { // einmal alles abscannen
			dir = doTurn();
			countTurn++;
		} else if (countTurn == 4 && myHeading == Heading.WEST) { // hier wird sich dann bewegt
			dir = doMove();
			countTurn = 0;
		}

		return dir;
	}

	// Function for doing a circle when on Danecfloor
	private Direction doDance() {
		Direction dir = Direction.IDLE;

		if (danceCount == 1) {
			dir = Direction.IDLE;
			danceCount++;
		} else if (danceCount == 2) {
			dir = Direction.IDLE;
			danceCount++;
		} else if (danceCount == 3) {
			dir = Direction.IDLE;
			danceCount++;
		} else if (danceCount == 4) { // ausgangsposition nun genau gleich eingangsposition
			dir = Direction.IDLE;
			danceCount = 1;
		}

		return dir;
	}

	private Direction goBar(int barPos) {
		Direction dir = Direction.IDLE;
		if (bucketCheck < 4) {
			if (myHeading != Heading.WEST) { // bevor was passiert immer nach westen orientieren
				return findWest();
			} else {
				if (barPos == 0) {
					return Direction.IDLE;
				} else if (barPos == 1) {
					myX = myX + 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.WEST;
					bucketCheck = 4;
					return Direction.FORWARD;
				} else if (barPos == 2) {
					myY = myY + 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.NORTH;
					bucketCheck = 4;
					return Direction.RIGHT;
				} else if (barPos == 3) {
					myX = myX - 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.EAST;
					bucketCheck = 4;
					return Direction.BACK;
				} else if (barPos == 4) {
					myY = myY - 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.SOUTH;
					bucketCheck = 4;
					return Direction.LEFT;

				}
			}
		} else if (myHeading == Heading.WEST) {
			return Direction.IDLE;
		} else if (myHeading == Heading.EAST) {
			myHeading = Heading.NORTH;
			return Direction.TURN_LEFT_ON_SPOT;
		} else if (myHeading == Heading.SOUTH) {
			myHeading = Heading.WEST;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else if (myHeading == Heading.NORTH) {
			myHeading = Heading.WEST;
			return Direction.TURN_LEFT_ON_SPOT;
		}

		return Direction.IDLE;

	}

	private Direction goLounge(int loungePos) {
		Direction dir = Direction.IDLE;
		if (bucketCheck < 7) {
			if (myHeading != Heading.WEST) { // bevor was passiert immer nach westen orientieren
				return findWest();
			} else {
				if (loungePos == 0) {
					return Direction.IDLE;
				} else if (loungePos == 1) {
					myX = myX + 1;
					System.out.println("lounge gefunden");
					goBarDone = true;
					myHeading = Heading.WEST;
					bucketCheck = 7;
					return Direction.FORWARD;
				} else if (loungePos == 2) {
					myY = myY + 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.NORTH;
					bucketCheck = 7;
					return Direction.RIGHT;
				} else if (loungePos == 3) {
					myX = myX - 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.EAST;
					bucketCheck = 7;
					return Direction.BACK;
				} else if (loungePos == 4) {
					myY = myY - 1;
					System.out.println("bar gefunden");
					goBarDone = true;
					myHeading = Heading.SOUTH;
					bucketCheck = 7;
					return Direction.LEFT;

				}
			}
		} else if (myHeading == Heading.WEST) {
			return Direction.IDLE;
		} else if (myHeading == Heading.EAST) {
			myHeading = Heading.NORTH;
			return Direction.TURN_LEFT_ON_SPOT;
		} else if (myHeading == Heading.SOUTH) {
			myHeading = Heading.WEST;
			return Direction.TURN_RIGHT_ON_SPOT;
		} else if (myHeading == Heading.NORTH) {
			myHeading = Heading.WEST;
			return Direction.TURN_LEFT_ON_SPOT;
		}

		return Direction.IDLE;

	}

	private int lookForBar() { // 0 bin drauf, 1 vormir, 2 rechts, 3 hinter mir, 4 links
		if (myMap[myX][myY] == Places.BAR_CHAIR) {
			return 0;
		} else if (myMap[myX + 1][myY] == Places.BAR_CHAIR) {
			return 1;
		} else if (myMap[myX - 1][myY] == Places.BAR_CHAIR) {
			return 3;
		} else if (myMap[myX][myY + 1] == Places.BAR_CHAIR) {
			return 2;
		} else if (myMap[myX][myY - 1] == Places.BAR_CHAIR) {
			return 4;
		} else
			return 42;
	}

	private int lookForLounge() { // 0 bin drauf, 1 vormir, 2 rechts, 3 hinter mir, 4 links
		if (myMap[myX][myY] == Places.LOUNGE_BIG || myMap[myX][myY] == Places.LOUNGE_SMOKING
				|| myMap[myX][myY] == Places.LOUNGE_SMALL) {
			return 0;
		} else if (myMap[myX + 1][myY] == Places.LOUNGE_BIG || myMap[myX + 1][myY] == Places.LOUNGE_SMOKING
				|| myMap[myX + 1][myY] == Places.LOUNGE_SMALL) {
			return 1;
		} else if (myMap[myX - 1][myY] == Places.LOUNGE_BIG || myMap[myX - 1][myY] == Places.LOUNGE_SMOKING
				|| myMap[myX - 1][myY] == Places.LOUNGE_SMALL) {
			return 3;
		} else if (myMap[myX][myY + 1] == Places.LOUNGE_BIG || myMap[myX][myY + 1] == Places.LOUNGE_SMOKING
				|| myMap[myX][myY + 1] == Places.LOUNGE_SMALL) {
			return 2;
		} else if (myMap[myX][myY - 1] == Places.LOUNGE_BIG || myMap[myX][myY - 1] == Places.LOUNGE_SMOKING
				|| myMap[myX][myY - 1] == Places.LOUNGE_SMALL) {
			return 4;
		} else
			return 42;
	}

	private Point myFind(Places suche) {

		for (int i = 0; i < 85; i++) {
			for (int j = 0; j < 65; j++) {
				if (myMap[i][j] == suche) {
					// gefunden, gib die Position zurück
					return new Point(i, j);
				}
			}
		}
		// nicht gefunden
		return null;
	}

	private boolean isFree(int myx, int myy) {
		if (myMap[myx][myy] == Places.PATH || myMap[myx][myy] == Places.DANCEFLOOR
				|| myMap[myx][myy] == Places.BAR_CHAIR || myMap[myx][myy] == Places.FUSSBALL_CHAIR
				|| myMap[myx][myy] == Places.LOUNGE_BIG || myMap[myx][myy] == Places.LOUNGE_SMALL
				|| myMap[myx][myy] == Places.LOUNGE_SMOKING || myMap[myx][myy] == Places.POOL_CHAIR) {
			return true;
		} else {
			return false;
		}
	}

	private Point deltaPos(Places suche) { // abstand zwischen mir und suche
		Point Punkt = myFind(suche);
		int x = (int) Punkt.getX();
		int y = (int) Punkt.getY();
		return new Point(x - myX, y - myY);
	}

	private Direction findMyWay(Places suche) {
		Point suchRadius = deltaPos(suche);
		int sucheX = (int) suchRadius.getX();
		int sucheY = (int) suchRadius.getY();
		boolean xF = true;
		boolean yF = true;
		Point gegenstandOrt = myFind(suche);
		int xOrt = (int) gegenstandOrt.getX();
		int yOrt = (int) gegenstandOrt.getY();

		// suche von mein Stndort x und dann y
		if (sucheX < 0) { // dann muss ich nach rechts, also x muss kleiner werden
			for (int i = sucheX; i <= myX + sucheX; i--) {
				if (isFree(i, myY) == false) {
					xF = false; // kein freier Weg
					break;
				}
			}
		} else if (sucheX > 0) {// ich muss nach links, also x muss groesser werden
			for (int i = sucheX; i <= myX + sucheX; i++) {
				if (isFree(i, myY) == false) {
					xF = false; // kein freier Weg
					break;
				}
			}
		} else { // sucheX gleich 0, also keie x verschiebung noetig
			xF = true;
		}
		// ----------------------------------------------------------
		if (sucheY < 0) {// ich muss nach unten, also weg von gegenstand hoch prüfen
			for (int i = sucheY; i <= yOrt - sucheX; i++) {
				if (isFree(xOrt, i) == false) {
					yF = false; // kein freier Weg
					break;
				}
			}
		} else if (sucheY > 0) {// ich muss nach oben also suche nach unten
			for (int i = sucheY; i <= yOrt - sucheX; i--) {
				if (isFree(xOrt, i) == false) {
					yF = false; // kein freier Weg
					break;
				}
			}
		} else { // kein y weg noetig aslo frei
			yF = true;
		}
		// suche von mein standort y und dann x

		return Direction.IDLE;
	}

	private boolean inTanzbereich() {
		if (myMap[myX + 1][myY] == Places.DANCEFLOOR && myMap[myX - 1][myY] == Places.DANCEFLOOR
				&& myMap[myX][myY + 1] == Places.DANCEFLOOR && myMap[myX][myY - 1] == Places.DANCEFLOOR) {
			return true;
		} else {
			return false;
		}
	}

}
