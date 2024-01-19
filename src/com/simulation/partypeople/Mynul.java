///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         16/12/2023
//
// Class: Mynul.java
// Description: Avatar class for Mynul
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import com.simulation.matrix.LocatedAvatar;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.simulation.enums.*;
import com.simulation.enums.Direction;

public class Mynul extends Avatar {
    
    private boolean isOnDanceFloor = false;
    private boolean goToBar = false;
    private boolean drinkOrdered = false;
    private boolean goToLoungeArea = false;
    private boolean goToDancefloor = true;
    private int dancingCounter = 0;
    private int moonwalkingTopCounter = 0;
    private int moonwalkingBottomCounter = 0;
    private int moonwalkingLeftCounter = 0;
    private int moonwalkingRightCounter = 0;
    private int moonwalkingLoop = 2;
    private int centerAlignMovements =0;
    private int centerAlignXY=0;
    private boolean isReadyForDancing = false;
    private boolean preparingToCenterAlign = false;
    private Heading currentHeading = Heading.NORTH;
    private HashMap<String, Places> surroundingsMap = new HashMap<>();
    private String[][] mentalMap = new String[50][50];;
//    private int xPos = 50;
//    private int yPos = 50;
    private LocatedAvatar myLocation = null;

    public Mynul(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
        for (int i = 0; i < 50; i++) { // Populating array with empty values rather than null for better visibility
        	Arrays.fill(mentalMap[i], "");
    	}
    }

    @Override
    public Direction moveAvatar() {
    	Direction dir = Direction.IDLE;
    	updateSurroundingsMap();
        
        if (isOnDanceFloor) {
            dir = dancingAlgo(); // Continue dancing
        }
        else if (goToDancefloor && surroundingsMap.getOrDefault("front", Places.PATH) == Places.DANCEFLOOR) {
            isOnDanceFloor = true; // Mark that Mynul is now on the dance floor
            dir = dancingAlgo(); // Start dancing
        }
        else if (goToBar && surroundingsMap.getOrDefault("front", Places.PATH) == Places.BAR) {
        	System.out.println("Reached at bar");
        	if(!drinkOrdered) {
        		this.drink(BeverageType.BEER);		// Order Drink
        		drinkOrdered = true;
        	}
        	else {
        		if(this.getAlcoholPercentage() > 0) {
        			// Drink allotted successfully
        			goToBar = false;
        			goToLoungeArea = true;
        		}
        	}
        	return Direction.IDLE;
        }
        else if (goToLoungeArea && (surroundingsMap.getOrDefault("front", Places.PATH) == Places.LOUNGE_BIG || surroundingsMap.getOrDefault("front", Places.PATH) == Places.LOUNGE_SMALL)) {
        	System.out.println("Reached at Lounge Area");
        	System.out.println("Mynul is so tired now. Sitting in Lounge having his favourite drink.");
        	return Direction.IDLE;
        }
        else {
        	dir =  navigateToDesiredLocation();
        }
        updateMindMap();
//        displayMap();
        return dir;
    }
    public void setLocatedAvatar(LocatedAvatar _myLocation) {
    	this.myLocation = _myLocation;
    }
    private void updateMindMap() {
//    	updateHeading(dir);
    	updateMentalMap(this.getWhatISee(), this.myLocation.getHeading(), this.myLocation.getX(), this.myLocation.getY());
    }
    
    private void updateSurroundingsMap() {
        Places[] surroundings = getWhatISee();
        surroundingsMap.put("front", surroundings[0]);
        surroundingsMap.put("next", surroundings[1]);
    }

    private Direction navigateToDesiredLocation() {
		Places inFrontOfMe = getWhatISee()[1];
		switch (inFrontOfMe) {
			case WALL:
				return Direction.BACK;
			case BAR:
				return (goToBar ? Direction.FORWARD : Direction.BACK);
			case POOL:
				return Direction.TURN_LEFT_ON_SPOT;
			case FUSSBALL:
				return Direction.TURN_RIGHT_ON_SPOT;
			case PERSON:
				return Direction.TURN_RIGHT_ON_SPOT;
	//				return Direction.BACK;
			case TOILET:
				return Direction.BACK;
			case DANCEFLOOR:
				return (goToDancefloor ? Direction.FORWARD : Direction.FORWARD);
			case LOUNGE_BIG:
			case LOUNGE_SMALL:
				return (goToLoungeArea ? Direction.FORWARD : Direction.BACK);
			case BOUNCER:
				return Direction.BACK;
			case DJ:
				return Direction.BACK;
			case OUTSIDE:
				return Direction.BACK;
			default:
				return randomMovement();
		}
    }

    private Direction randomMovement() {
    	Random rand = new Random();
		int number = rand.nextInt(100);
		Direction dir = Direction.IDLE;
		if (number <= 45) {
			dir = Direction.FORWARD;
		} else if (number < 65) {
			dir = Direction.RIGHT;
		} else if (number < 75) {
			dir = Direction.BACK;
		} else {
			dir = Direction.LEFT;
		}
		return dir;
    }

    public Direction moonWalkAlgo(Boolean isInfiniteLoop) {
        Direction dancingDir = Direction.FORWARD;

        if (moonwalkingLeftCounter++ < 6) {
        	if(moonwalkingLeftCounter == 0) {
        		return Direction.LEFT;
        	}
        	else if(moonwalkingLeftCounter == 4) {
        		return Direction.BACK;
        	}
        	return Direction.FORWARD; 
        }
        else if (moonwalkingRightCounter++ < 6) {
        	if(moonwalkingRightCounter == 0) {
        		return Direction.RIGHT;
        	}
        	else if(moonwalkingRightCounter == 4) {
        		return Direction.BACK;
        	}
        	return Direction.FORWARD; 
        }
        else if (moonwalkingTopCounter++ < 7) {
        	if(moonwalkingTopCounter == 1) {
        		return Direction.TURN_LEFT_ON_SPOT;
        	}
        	else if(moonwalkingTopCounter == 5) {
        		return Direction.BACK;
        	}
        	return Direction.FORWARD; 
        }
        else if (moonwalkingBottomCounter++ < 6) {
        	if(moonwalkingBottomCounter == 4) {
        		return Direction.BACK;
        	}
        	return Direction.FORWARD; 
        }
        System.out.println("Dancing step completed");
        if(isInfiniteLoop || --moonwalkingLoop > 0) {
        	moonwalkingTopCounter =0;
        	moonwalkingBottomCounter =0;
        	moonwalkingLeftCounter =0;
        	moonwalkingRightCounter =0;
        }
        return Direction.IDLE;
    }
    
    public Direction dancingAlgo() {
    	if(isReadyForDancing){
    		System.out.println(this.getName() + " is dancing");    		
    	}
    	if(getWhatISee()[1] == Places.PERSON) {
    		return Direction.TURN_LEFT_ON_SPOT;
    	}
    	if(isOnDanceFloor && !preparingToCenterAlign) {
    		if(getWhatISee()[1] == Places.DANCEFLOOR) {
    			return Direction.FORWARD;    			
    		}
    		else if(dancingCounter++ < 2){
    			// Avatar reaches on either corner of square
    			return Direction.TURN_LEFT_ON_SPOT;
    		}
    		else {
    			preparingToCenterAlign = true;
    			return Direction.TURN_LEFT_ON_SPOT;
    		}
    	}
		else if(preparingToCenterAlign && !isReadyForDancing){
			System.out.println("Center aligning avatar in square");
			if(centerAlignMovements++ < 10) {
				if(centerAlignXY++ < 4) {
					return Direction.FORWARD;
				}
				else {
					centerAlignXY =0;
					return Direction.TURN_LEFT_ON_SPOT;
				}				
			}
			else {
				isReadyForDancing = true; // Center in square
				setDancing(true);
				return Direction.IDLE;
			}
		}
		else if (isReadyForDancing && moonwalkingLoop > 0) {
			return moonWalkAlgo(false); // pass true for infinite dance loop
		}
		else {
			setDancing(false);
			isReadyForDancing = false;
			preparingToCenterAlign = false;
			dancingCounter = 0;
			isOnDanceFloor = false;
			goToDancefloor = false;
			goToBar = true;
		}
    	
    	return Direction.IDLE;
		
	}

    public int getAge() {
        return 28;
    }

    public void fight(Avatar opponent) {
        
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
    public void updateMentalMap (Places[] WhatISee, Heading currentHeading, int x, int y) {
		if (WhatISee[1] == Places.PERSON){
			return;
		}
			String placeString = WhatISee[1].toString();
			mentalMap[x][y] = WhatISee[0].toString();
			if (y > 0 && currentHeading == Heading.NORTH) {
				mentalMap[x][y - 1] = placeString;
			}
			else if (currentHeading == Heading.EAST) {
				mentalMap[x + 1][y] = placeString;
			}
			else if (currentHeading == Heading.SOUTH) {
				mentalMap[x][y+1] = placeString;
			}
			else if (x > 0 && currentHeading == Heading.WEST) {
				mentalMap[x-1][y] = placeString;
			}
	}
    private void updateHeading(Direction nextdir) {
		if (currentHeading == Heading.NORTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT || nextdir == Direction.LEFT) {
				currentHeading = Heading.WEST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT || nextdir == Direction.RIGHT) {
				currentHeading = Heading.EAST;
			}
			else if(nextdir == Direction.BACK) {
				currentHeading = Heading.SOUTH;
			}
		}

		else if (currentHeading == Heading.EAST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT || nextdir == Direction.LEFT) {
				currentHeading = Heading.NORTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT || nextdir == Direction.RIGHT) {
				currentHeading = Heading.SOUTH;
			}
			else if(nextdir == Direction.BACK) {
				currentHeading = Heading.WEST;
			}
		}

		else if (currentHeading == Heading.SOUTH) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT || nextdir == Direction.LEFT) {
				currentHeading = Heading.EAST;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT || nextdir == Direction.RIGHT) {
				currentHeading = Heading.WEST;
			}
			else if(nextdir == Direction.BACK) {
				currentHeading = Heading.NORTH;
			}
		}

		else if (currentHeading == Heading.WEST) {
			if (nextdir == Direction.TURN_LEFT_ON_SPOT || nextdir == Direction.LEFT) {
				currentHeading = Heading.SOUTH;
			}
			else if (nextdir == Direction.TURN_RIGHT_ON_SPOT || nextdir == Direction.RIGHT) {
				currentHeading = Heading.NORTH;
			}
			else if(nextdir == Direction.BACK) {
				currentHeading = Heading.EAST;
			}
		}
	}
    private void displayMap() {
        for (String[] row : mentalMap) {
            System.out.println(Arrays.deepToString(row));
        }
    }
}