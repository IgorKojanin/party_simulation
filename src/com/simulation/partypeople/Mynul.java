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
import java.io.PrintStream;
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
    private int barDelayCounter = 0;
    private int moonwalkingLoop = 2;
    private int centerAlignMovements =0;
    private int centerAlignXY=0;
    private boolean isReadyForDancing = false;
    private boolean preparingToCenterAlign = false;
    private boolean isMoving = true;
    private boolean reachedAtLounge = false;
    private Heading currentHeading = Heading.NORTH;
    private HashMap<String, Places> surroundingsMap = new HashMap<>();
    private String[][] mentalMap = new String[50][50];
    private PrettyPrintMindMap printer = new PrettyPrintMindMap();
    private LocatedAvatar myLocation = null;

    public Mynul(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
        for (int i = 0; i < 24; i++) { // Populating array with empty values rather than null for better visibility
        	Arrays.fill(mentalMap[i], "    ");	
    	}
    }

    @Override
    public Direction moveAvatar() {
    	Direction dir = Direction.IDLE;
    	updateSurroundingsMap();
    	updateMindMap();
        
        if (isOnDanceFloor) {
            dir = dancingAlgo(); // Continue dancing
        }
        else if (goToDancefloor && surroundingsMap.getOrDefault("front", Places.PATH) == Places.DANCEFLOOR) {
            isOnDanceFloor = true; // Mark that Mynul is now on the dance floor
            dir = dancingAlgo(); // Start dancing
        }
        else if (goToBar && surroundingsMap.getOrDefault("next", Places.PATH) == Places.BAR) {
        	if(!drinkOrdered) {
        		System.out.println("Reached at bar");
        		this.drink(BeverageType.BEER);		// Order Drink
        		System.out.println("Drink ordered at bar.");
        		drinkOrdered = true;
        		dir = Direction.IDLE;
        		displayMap();
        	}
        	else if(barDelayCounter++ > 10){
        		goToBar = false;
        		goToLoungeArea = true;
        		dir = Direction.TURN_RIGHT_ON_SPOT;
        	}
        	dir = Direction.IDLE; 
        }
        else if (goToLoungeArea && (surroundingsMap.getOrDefault("next", Places.PATH) == Places.LOUNGE_BIG || surroundingsMap.getOrDefault("next", Places.PATH) == Places.LOUNGE_SMALL)) {
        	if(!reachedAtLounge) {
        		System.out.println("Reached at "+surroundingsMap.getOrDefault("next", Places.PATH)+" area");
        		System.out.println("Mynul is so tired now. Sitting in Lounge having his favourite drink.");
        		displayMap();
        		reachedAtLounge = true;
        	}
        	dir = Direction.IDLE;
        }
        else {
        	dir =  navigateToDesiredLocation();
        }
        
        if(dir == Direction.IDLE){
    		this.isMoving = false;
    		System.out.println(this.getName()+" is stopped.");
    	}
    	else {
    		this.isMoving = true;
    		System.out.println(this.getName()+" is moving.");
    	}
        
        return dir;
    }
    public void setLocatedAvatar(LocatedAvatar _myLocation) {
    	this.myLocation = _myLocation;
    }
    private void updateMindMap() {
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
				return Direction.TURN_LEFT_ON_SPOT;
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
		if (number <= 75) {
			dir = Direction.FORWARD;
		} else if (number < 85) {
			dir = Direction.BACK;
		} else if (number < 95) {
			dir = Direction.LEFT;
		} else {
			dir = Direction.RIGHT;
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
			displayMap();
			System.out.println("Desired location updated to BAR.");
		}
    	if(isReadyForDancing){
    		System.out.println(this.getName() + " is dancing");    		
    	}
    	return Direction.IDLE;
		
	}

    public int getAge() {
        return 28;
    }

    public void fight(Avatar opponent) {
        // TODO: Implement fighting behavior
    }

    public void talk(Avatar person) {
        // TODO: Implement talking behavior
    }

    public void smoke() {
        // TODO: Implement smoking behavior
    }

    public void toilet(int timeInToilet) {
        // TODO: Implement toilet behavior
    }

    public void playPool() {
        // TODO: Implement playing pool behavior
    }

    public void playFussball() {
        // TODO: Implement playing fussball behavior
    }
    public void updateMentalMap (Places[] WhatISee, Heading currentHeading, int x, int y) {
			if (WhatISee[1] == Places.PERSON){
				return;
			}
			String placeString = WhatISee[1].toString();
			if(mentalMap[y][x] == "    ") { // only update if its blank
				mentalMap[y][x] = WhatISee[0].toString();
			}
			int _x = x;
			int _y = y;
			
			if (_y > 0 && currentHeading == Heading.NORTH) {
				_y = _y -1;
			}
			else if (currentHeading == Heading.EAST) {
				_x = _x + 1;
			}
			else if (currentHeading == Heading.SOUTH) {
				_y = _y+1;
			}
			else if (_x > 0 && currentHeading == Heading.WEST) {
				_x = _x-1;
			}
			
			if(mentalMap[_y][_x] == "    ") {
				mentalMap[_y][_x] = placeString;
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
    	printer.print(mentalMap);
}

    // PrettyPrint 
private class PrettyPrintMindMap {

    private static final char BORDER_KNOT = '+';
    private static final char HORIZONTAL_BORDER = '-';
    private static final char VERTICAL_BORDER = '|';

    private static final String DEFAULT_AS_NULL = "(NULL)";

    private final PrintStream out = System.out;
    private final String asNull = DEFAULT_AS_NULL;

    public void print(String[][] table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.length == 0 ) {
            return;
        }
        final int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths));
    }

    private void printPreparedTable(String[][] table, int widths[], String horizontalBorder) {
        final int lineLength = horizontalBorder.length();
        out.println(horizontalBorder);
        for ( final String[] row : table ) {
            if ( row != null ) {
                out.println(getRow(row, widths, lineLength));
                out.println(horizontalBorder);
            }
        }
    }

    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        final int maxWidths = widths.length;
        for ( int i = 0; i < maxWidths; i++ ) {
            builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }

    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(BORDER_KNOT);
        for ( final int w : widths ) {
            for ( int i = 0; i < w; i++ ) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(BORDER_KNOT);
        }
        return builder.toString();
    }

    private int getMaxColumns(String[][] rows) {
        int max = 0;
        for ( final String[] row : rows ) {
            if ( row != null && row.length > max ) {
                max = row.length;
            }
        }
        return max;
    }

    private void adjustColumnWidths(String[][] rows, int[] widths) {
        for ( final String[] row : rows ) {
            if ( row != null ) {
                for ( int c = 0; c < widths.length; c++ ) {
                    final String cv = getCellValue(safeGet(row, c, asNull));
                    final int l = cv.length();
                    if ( widths[c] < l ) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    private static String safeGet(String[] array, int index, String defaultValue) {
        return index < array.length ? array[index] : defaultValue;
    }

    private String getCellValue(Object value) {
        return value == null ? asNull : value.toString();
    }

  }
}
