///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         22/12/2023
//
// Class: Bjoern.java
// Description: Bjoern's avatar
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.partypeople;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Shape;
import com.simulation.enums.Places;

public class Bjoern extends Avatar {
	private boolean onDanceFloor = false;
	private int cyclesIDidntMove = 0;
	private Map exploredSurrounding = new Map(); 

	public Bjoern(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
	}

	public void dancingAlgo() {
	}

	public void fight(Avatar opponent) { // Call this function if other avatar starts a fight
	}

	public void talk(Avatar person) {
	}

	public void smoke() {
	}

	public void toilet(int timeInToilet) {
	}

	public void playPool() {
	}

	public void playFussball() {
	}

	public Direction moveAvatar() {
		Places inFrontOfMe = getWhatISee()[1];
		//System.out.println(exploredSurrounding.getCurrent());
		computePlacesMap();
		exploredSurrounding.memoriseCurrent(getWhatISee()[0]);
		if (!onDanceFloor) {
			switch (inFrontOfMe) {
			case PATH:
				return determineMovementRandomly();
			case PERSON:
				return moveToDirection(Direction.LEFT);
			case DANCEFLOOR:
				onDanceFloor = true;
				return moveToDirection(Direction.IDLE);
			case OUTSIDE:
				return moveToDirection(Direction.BACK);
			case TOILET:
				return moveToDirection(Direction.BACK);
			default:
				return determineMovementRandomly();
			}
		} else {
			return moveAvatarOnDanceFloor(inFrontOfMe);
		}
	}
	
	private Direction moveAvatarOnDanceFloor(Places inFrontOfMe) {
		switch (inFrontOfMe) {
		case PATH:
			return moveToDirection(Direction.BACK);
		case PERSON:
			cyclesIDidntMove++;
			if ( cyclesIDidntMove > 3) {
				return moveToDirection(Direction.TURN_RIGHT_ON_SPOT);
			}
			return moveToDirection(Direction.IDLE);
		case DANCEFLOOR:
			onDanceFloor = true;
			return moveToDirection(Direction.FORWARD);
		default:
			return determineMovementRandomly();
		}
	}

	private Direction determineMovementRandomly() {
		int rand = ThreadLocalRandom.current().nextInt(0, 70);
		switch ((00 <= rand && rand < 50) ? 0 : (50 <= rand && rand < 60) ? 1 : 2) {
		case 0:
			computePlacesChange();
			return moveToDirection(Direction.FORWARD);
		case 1:
			computePlacesChange();
			return moveToDirection(Direction.RIGHT);
		default:
			computePlacesChange();
			return moveToDirection(Direction.LEFT);
		}
	}

	private double computePlacesChange() {
		if (exploredSurrounding.getCurrent() != Places.PATH) {
			return Math.PI * 2;
		} else {
			return Math.PI;
		}
	}
	
	private Direction moveToDirection(Direction d) {
		if (d == Direction.RIGHT || d == Direction.LEFT || d == Direction.BACK) {
			exploredSurrounding.changeHeading(d);
		} else if (d == Direction.TURN_RIGHT_ON_SPOT) {
			exploredSurrounding.changeHeading(Direction.RIGHT);
		} else if (d == Direction.TURN_LEFT_ON_SPOT) {
			exploredSurrounding.changeHeading(Direction.LEFT);
		}
		return d;
	}

	private void computePlacesAfterChange() {
		if (exploredSurrounding.getCurrent() != Places.PATH || exploredSurrounding.getCurrent() != Places.WALL) {
			keep();
		} else {
			change();
		}
	}
	
	private class Map {
		private Places[][] map = new Places[100][100];
		private int x = 50;
		private int y = 50;
		private Heading heading = Heading.WEST;
		
		public Places getCurrent() {
			return map[x][y];
		}
		
		public void memoriseCurrent(Places atCurrentPlace) { 
			map[x][y] = atCurrentPlace;
		}

		public void changeHeading(Direction d) {
			switch( d ){
				case LEFT: 
					switch( heading ){
					case WEST: heading = Heading.SOUTH; break;
					case SOUTH: heading = Heading.EAST; break;
					case EAST: heading = Heading.NORTH; break;
					default: heading = Heading.WEST;
					}
					break;
				case RIGHT: 
					switch( heading ){
					case WEST: heading = Heading.NORTH; break;
					case NORTH: heading = Heading.EAST; break;
					case EAST: heading = Heading.SOUTH; break;
					default: heading = Heading.WEST;
					}
					break;
				default: // BACK 
					switch( heading ){
					case WEST: heading = Heading.EAST; break;
					case NORTH: heading = Heading.SOUTH; break;
					case EAST: heading = Heading.WEST; break;
					default: heading = Heading.NORTH;
					}
			}
		}
	}

	private void keep() {
		exploredSurrounding.getClass();
	}
	
	private void change() {
		exploredSurrounding.hashCode();
	}
	
	private double computePlacesMap() {
		if (exploredSurrounding.getCurrent() == Places.PATH) {
			return Math.PI * 4;
		} else {
			return Math.PI * 3;
		}
	}
}