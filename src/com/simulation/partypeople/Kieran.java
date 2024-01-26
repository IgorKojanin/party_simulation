/*
Party Simulation: Kieran Avatar
Date: 25.01.2024
Class: Kieran.java extending the Avatar.java class
Description: Creating a unique avatar for Kieran
Status: WIP

Implementation Details: Key idea is to use the avatar's perception via getWhatISee to guide its movement 
						via BFS, remembering visited places and directions 
						to avoid loops.
*/

/* Packages */
package com.simulation.partypeople;

/* Imports */
import com.simulation.avatar.Avatar;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import com.simulation.kieranSup.KieranGP;
import com.simulation.kieranSup.KieranPt;
import com.simulation.enums.Places;

import com.simulation.enums.Heading;

public class Kieran extends Avatar {

	/* Variables */
	private Queue<Direction> movementQueue = new LinkedList<>(); // Used to store directions, FIFO principled
	private Queue<Places> placesQueue = new LinkedList<>();
	private Direction lastDirection = Direction.IDLE; // Keeps track of the last direction moved to avoid backtracking
	private int danceStepCounter = 0; // Int variable to control the dancing actions in a switch case
	private int visitingRestRoom = 0;
	private int playingPool = 0;
	private int playingFussball = 0;
	private boolean hasDanced = false; // Flag to indicate if the avatar has previously danced
	private boolean barFound = false;
	private boolean hasOrderedDrink = false;
	private BeverageType water;
	private boolean loungeFound = false;
	private boolean drinkStatement = false;
	private boolean nextLocationStatement = false;
	private Places[] canIOccupyPlace = { Places.DANCEFLOOR, Places.BAR_CHAIR, Places.POOL_CHAIR, Places.TOILET,
			Places.FUSSBALL_CHAIR, Places.LOUNGE_BIG, Places.LOUNGE_SMALL, Places.LOUNGE_SMOKING,
			Places.PATH
	};
	private Places[] canIOccupyPlaceMap = { Places.BAR_CHAIR, Places.POOL_CHAIR, Places.TOILET,
			Places.FUSSBALL_CHAIR, Places.LOUNGE_BIG, Places.LOUNGE_SMALL, Places.LOUNGE_SMOKING,
	};
	private Heading currentHeading = Heading.NORTH;
	private Heading nextHeading = Heading.NORTH;
	private int relativePositionX = 0;
	private int relativePositionY = 0;
	private KieranMap kieranFrame;
	private KieranGP kieranGraph;

	private boolean graphicalRep = true; // Graphical representation of avatars movement
	private boolean printStatements = true; // Flag to be set false to remove print statements for testing
	private boolean challengeMode = true; // Flag to set if challenge mode, if false contains print statements

	/* Constructor */
	public Kieran(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		movementQueue.offer(Direction.FORWARD);
		placesQueue.offer(Places.DANCEFLOOR);
		if (graphicalRep) {
			kieranGraph = new KieranGP();
			kieranFrame = new KieranMap(kieranGraph);
			kieranFrame.setVisible(true);
		}
	}

	/* Methods */

	// Implementing a simple state machine to determine the dancing movement
	public Direction dancingAlgo() {

		Direction dancingMovement = Direction.IDLE;

		if (hasDanced) {
			return moveAvatar();
		}
		if (danceStepCounter == 0) {
			dancingMovement = Direction.FORWARD;
		} else if (danceStepCounter >= 1 && danceStepCounter <= 4) {
			dancingMovement = Direction.RIGHT;
		} else {
			hasDanced = true;
			addNextPlaceToVisit(Places.BAR);
			if (printStatements) {
				System.out.println("Dancing was fun but time to move on!");
			}
		}
		danceStepCounter++;
		placesQueue.poll();

		return dancingMovement;
	}

	public void drink(BeverageType type) {
		if (drinkStatement == false) {
			System.out.println("Enjoying a delicious beverage at the lounge!");
			drinkStatement = true;
		}
	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
		Places inFrontOfMe = getWhatISee()[1];
		movementQueue.poll();
		if (inFrontOfMe == Places.PERSON) {
			movementQueue.offer(Direction.BACK);
		}
		String nameOfOpponent = opponent.getName();
		if (printStatements) {
			System.out.println("I don't want to fight " + nameOfOpponent + "!");
		}
	}

	public void talk(Avatar person) {
		Places inFrontOfMe = getWhatISee()[1];
		boolean interactionMode = true;
		String personToSpeakTo = person.getName();
		if (inFrontOfMe == Places.PERSON && interactionMode) {
			movementQueue.poll();
			movementQueue.offer(Direction.IDLE);
			interactionMode = false;
			if (printStatements) {
				System.out.println("Hello there, " + personToSpeakTo + "!");
			}
		} else {
			movementQueue.offer(Direction.TURN_LEFT_ON_SPOT);
		}
	}

	public void smoke() {
		Places currentSpot = getWhatISee()[0];
		if (currentSpot == Places.LOUNGE_SMOKING) {
			if (printStatements) {
				System.out.println("Hanging out at the smoking lounge!");
			}
		}
	}

	public void toilet(int timeInToilet) { // Do only toilet things in the toilet
		Places inFrontOfMe = getWhatISee()[1];
		if (isPlaceOccupiable(inFrontOfMe) && visitingRestRoom <= 1) {
			movementQueue.poll();
			movementQueue.offer(Direction.IDLE);
			if (printStatements) {
				System.out.println("Visting the restroom!");
			}
			visitingRestRoom++;
		} else {
			movementQueue.poll();
			movementQueue.offer(Direction.BACK);
		}
	}

	public void playPool() { // Play pool only on the designate spot
		Places inFrontOfMe = getWhatISee()[1];
		if (isPlaceOccupiable(inFrontOfMe) && playingPool < 4) {
			movementQueue.poll();
			movementQueue.offer(Direction.IDLE);
			if (printStatements) {
				System.out.println("Playing Pool!");
			}
			playingPool++;
		} else {
			movementQueue.poll();
			movementQueue.offer(Direction.BACK);
		}
	}

	public void playFussball() { // Play Fussball only on the designate spot
		Places inFrontOfMe = getWhatISee()[1];
		if (isPlaceOccupiable(inFrontOfMe) && playingFussball < 2) {
			movementQueue.poll();
			movementQueue.offer(Direction.IDLE);
			if (printStatements) {
				System.out.println("Playing Fussball!");
			}
			playingFussball++;
		} else {
			movementQueue.poll();
			movementQueue.offer(Direction.BACK);
		}
	}

	public void addNextPlaceToVisit(Places place) {
		placesQueue.offer(place);
	}

	public Direction moveAboutAimlessly() {
		Random rand = new Random();
		int number = rand.nextInt(4);
		Direction randomMovementDir = Direction.IDLE;
		if (number == 0) {
			randomMovementDir = Direction.FORWARD;
			if (printStatements) {
				System.out.println("Forward movement");
			}
		} else if (number == 1) {
			randomMovementDir = Direction.RIGHT;
			if (printStatements) {
				System.out.println("Right movement");
			}
		} else if (number == 2) {
			randomMovementDir = Direction.BACK;
			if (printStatements) {
				System.out.println("Back movement");
			}
		} else if (number == 3) {
			randomMovementDir = Direction.LEFT;
			if (printStatements) {
				System.out.println("Left movement");
			}
		}
		return randomMovementDir;
	}

	public void saveGraphToCSV(String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Write header
			writer.write("x, y , Location: \n");

			// Iterate through each point in the graph
			for (KieranPt point : kieranGraph.getPoints()) {
				String line = point.getX() + "," + point.getY() + "," + point.getPlace().name() + "\n";
				writer.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Direction moveAvatar() {

		Places currentTargetPlace = placesQueue.peek();

		if (nextLocationStatement) {
			System.out.println("Current Target: " + currentTargetPlace);
		}
		if (loungeFound && hasOrderedDrink) {
			drink(water);
			return Direction.IDLE;
		}
		if (movementQueue.isEmpty()) { // No more directions to explore
			// No more moves left, stay still
			return Direction.IDLE;
		}

		Direction nextMove = movementQueue.poll(); // Retrieves lastest direction using poll() and removes it via FIFO
		lastDirection = nextMove;

		Places currentSpot = getWhatISee()[0];
		Places inFrontOfMe = getWhatISee()[1];

		if (inFrontOfMe == Places.QUEUE || inFrontOfMe == Places.OUTSIDE) {
			return stayIndoors();
		}
		if (!barFound && inFrontOfMe == Places.BAR) {
			barFound = true;
			if (printStatements) {
				System.out.println("I found the bar, ordering a drink!");
			}
			hasOrderedDrink = true;
			placesQueue.poll();
			addNextPlaceToVisit(Places.LOUNGE_SMOKING);
		}
		switch (currentSpot) {
			case PATH:
				exploreSurroundings();
				break;
			case DANCEFLOOR:
				if (hasDanced == false) {
					return dancingAlgo();
				}
				break;
			case BAR:
				break;
			case BAR_CHAIR:
				barFound = true;
				if (printStatements) {
					System.out.println("Sitting at the bar stool, ordering a drink!");
				}
				hasOrderedDrink = true;
				placesQueue.poll();
				addNextPlaceToVisit(Places.LOUNGE_SMOKING);
				break;
			case QUEUE:
				stayIndoors();
				break;
			case OUTSIDE:
				stayIndoors();
				break;
			case PERSON:
				if (challengeMode == false) {
				}
				break;
			case LOUNGE_BIG:
				loungeAction();
				if (hasDanced && barFound && hasOrderedDrink) {
					nextMove = Direction.IDLE;
				}
				break;
			case LOUNGE_SMALL:
				loungeAction();
				if (hasDanced && barFound && hasOrderedDrink) {
					nextMove = Direction.IDLE;
				}
				break;
			case LOUNGE_SMOKING:
				loungeAction();
				if (hasDanced && barFound && hasOrderedDrink) {
					nextMove = Direction.IDLE;
				}
				break;
			case DJ:
				if (challengeMode == false) {
				}
				break;
			case BOUNCER:
				if (challengeMode == false) {
				}
				break;
			case FUSSBALL_CHAIR:
				if (challengeMode == false) {
					playFussball();
				}
				break;
			case POOL_CHAIR:
				if (challengeMode == false) {
					playPool();
				}
				break;
			case TOILET:
				nextMove = Direction.BACK;
				exploreSurroundings();
				break;
			case TOILET_CHAIR:
				nextMove = Direction.BACK;
				exploreSurroundings();
				break;
		}

		if (graphicalRep) {
			swapHeading(nextMove);

			KieranPt currentPT = new KieranPt(relativePositionX, relativePositionY);
			KieranPt previousPT = kieranGraph.getPoint(currentPT);

			if (previousPT != null && currentPT.isVisited() == false) {
				previousPT.setVisited(true); // Set previous point to being visited if not
			} else {
				currentPT.setVisited(true); // Adding newly encountered point to graph
				currentPT.setPlace(currentSpot);
				kieranGraph.addGraphPoint(currentPT);
			}
			kieranFrame.updatePanel(kieranGraph);
		}
		saveGraphToCSV("src/com/simulation/kieranSup/KieranMap.csv"); // Save taken directions to a csv file
		return nextMove;
	}

	private void exploreSurroundings() { // Determines directions to explore via BFS
		// Adding new directions to explore based on last direction moved
		// Avoid backtracking with lastDirection
		if (lastDirection != Direction.BACK) {
			movementQueue.offer(Direction.FORWARD);
		}
		if (lastDirection != Direction.RIGHT) {
			movementQueue.offer(Direction.LEFT);
		}
		if (lastDirection != Direction.LEFT) {
			movementQueue.offer(Direction.RIGHT);
		}
	}

	private Direction stayIndoors() {

		Direction nextMove = Direction.IDLE;
		Places futureSpot = getWhatISee()[1];

		if (futureSpot == Places.OUTSIDE || futureSpot == Places.QUEUE) {
			nextMove = Direction.BACK;
			if (printStatements) {
				System.out.println("Returning Inside the club");
			}
		}
		return nextMove;
	}

	private void loungeAction() {

		if (hasDanced && barFound && hasOrderedDrink) {
			Places currentSpot = getWhatISee()[0];
			switch (currentSpot) {
				case LOUNGE_BIG:
					loungeFound = true;
					movementQueue.clear(); // Clearing the movement queue
					movementQueue.offer(Direction.IDLE); // Ensuring the next movement is to idle
					break;
				case LOUNGE_SMALL:
					loungeFound = true;
					movementQueue.clear(); // Clearing the movement queue
					movementQueue.offer(Direction.IDLE); // Ensuring the next movement is to idle
					break;
				case LOUNGE_SMOKING:
					loungeFound = true;
					movementQueue.clear(); // Clearing the movement queue
					movementQueue.offer(Direction.IDLE); // Ensuring the next movement is to idle
					break;
			}
			placesQueue.poll();
		}
	}

	private boolean isPlaceOccupiable(Places place) {
		return Arrays.asList(canIOccupyPlace).contains(place);
	}

	private void swapHeading(Direction nextMove) {

		if (nextMove == Direction.FORWARD) {
			switch (currentHeading) {
				case EAST:
					currentHeading = Heading.EAST;
					nextHeading = Heading.EAST;
					relativePositionY++;
					break;
				case NORTH:
					currentHeading = Heading.NORTH;
					nextHeading = Heading.NORTH;
					relativePositionX++;
					break;
				case SOUTH:
					currentHeading = Heading.SOUTH;
					nextHeading = Heading.SOUTH;
					relativePositionX--;
					break;
				case WEST:
					currentHeading = Heading.WEST;
					nextHeading = Heading.WEST;
					relativePositionY--;
					break;
				default:
					break;

			}
		}

		if (nextMove == Direction.RIGHT) {
			switch (currentHeading) {
				case EAST:
					currentHeading = Heading.SOUTH;
					nextHeading = Heading.SOUTH;
					relativePositionX--;
					break;
				case NORTH:
					currentHeading = Heading.EAST;
					nextHeading = Heading.EAST;
					relativePositionY++;
					break;
				case SOUTH:
					currentHeading = Heading.WEST;
					nextHeading = Heading.WEST;
					relativePositionY--;
					break;
				case WEST:
					currentHeading = Heading.NORTH;
					nextHeading = Heading.NORTH;
					relativePositionX++;
					break;
				default:
					break;

			}
		}

		if (nextMove == Direction.LEFT) {
			switch (currentHeading) {
				case EAST:
					currentHeading = Heading.NORTH;
					nextHeading = Heading.NORTH;
					relativePositionX++;
					break;
				case NORTH:
					currentHeading = Heading.EAST;
					nextHeading = Heading.EAST;
					relativePositionY++;
					break;
				case SOUTH:
					currentHeading = Heading.WEST;
					nextHeading = Heading.WEST;
					relativePositionY--;
					break;
				case WEST:
					currentHeading = Heading.SOUTH;
					nextHeading = Heading.SOUTH;
					relativePositionX--;
					break;
				default:
					break;

			}
		}

		if (nextMove == Direction.BACK) {
			switch (currentHeading) {
				case EAST:
					currentHeading = Heading.WEST;
					nextHeading = Heading.WEST;
					relativePositionY--;
					break;
				case NORTH:
					currentHeading = Heading.SOUTH;
					nextHeading = Heading.SOUTH;
					relativePositionX--;
					break;
				case SOUTH:
					currentHeading = Heading.NORTH;
					nextHeading = Heading.NORTH;
					relativePositionX++;
					break;
				case WEST:
					currentHeading = Heading.EAST;
					nextHeading = Heading.EAST;
					relativePositionY++;
					break;
				default:
					break;

			}
		}
	}
}