package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Alisa2 extends Avatar {
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
    Direction dir;
    boolean foundLeftWall = false;
    boolean foundBottomWall = false;
    boolean foundTopWall = false;
    Places[] lastGWIS = new Places[2];
    Direction secondToLastMove;
    Direction lastMove;
    boolean isTurning = false;
    Integer horizontalDimension = 0;
    Integer verticalDimension = 0;
    Integer movementIndex = 0;
    Integer distanceFromLeftWall = 0;
    int stepsMade = 0;
    int testStepsMade = 0;
    boolean isMindMapPrinted = false;
    HashMap<Integer, Direction> wallSearch = new HashMap<>();
    Places[][] mindMap;
    Integer[] lastPosition = new Integer[2];
    Integer[] currentPosition = new Integer[2];
    Places[] usableSpots = new Places[]{
            Places.PATH,
            Places.BAR_CHAIR,
            Places.FUSSBALL_CHAIR,
            Places.POOL_CHAIR,
            Places.TOILET_CHAIR,
            Places.LOUNGE_SMOKING,
            Places.LOUNGE_BIG,
            Places.LOUNGE_SMALL,
            Places.DANCEFLOOR
    };

    // ************** Constructor **************
    public Alisa2(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
        // TODO
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

    private boolean isNextSquareUsable() {
        return Arrays.asList(usableSpots).contains(getWhatISee()[1]);
    }

    private void findLeftWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundLeftWall = true;
            dir = Direction.TURN_LEFT_ON_SPOT;
            secondToLastMove = lastMove;
            lastMove = dir;
            int countLeft = Collections.frequency(wallSearch.values(), Direction.TURN_LEFT_ON_SPOT);
            int countRight = Collections.frequency(wallSearch.values(), Direction.TURN_RIGHT_ON_SPOT);
            horizontalDimension = wallSearch.size() - (countLeft + countRight) / 2;
//            System.out.println(horizontalDimension);
            wallSearch.clear();
            movementIndex = 1;
            distanceFromLeftWall = 0;
//            System.out.println("found left wall");
        } else {
            findingWallsMovementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
            wallSearch.put(movementIndex, lastMove);
        }
        lastGWIS = getWhatISee();
    }

    private void findBottomWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundBottomWall = true;
            dir = Direction.BACK;
            secondToLastMove = lastMove;
            lastMove = dir;
//            System.out.println("found bottom wall");
            movementIndex = 2;
        } else {
            findingWallsMovementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
        }
        lastGWIS = getWhatISee();
    }

    private void findTopWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundTopWall = true;
            dir = Direction.BACK;
            secondToLastMove = lastMove;
            lastMove = dir;
            int countLeft = Collections.frequency(wallSearch.values(), Direction.TURN_LEFT_ON_SPOT);
            int countRight = Collections.frequency(wallSearch.values(), Direction.TURN_RIGHT_ON_SPOT);
            verticalDimension = wallSearch.size() - (countLeft + countRight) / 2;
//            System.out.println(verticalDimension);
            wallSearch.clear();
            movementIndex = 0;
//            System.out.println("found top wall");
            mindMap = new Places[horizontalDimension][verticalDimension];
            currentPosition[0] = 0;
            currentPosition[1] = distanceFromLeftWall;
            lastPosition[0] = currentPosition[0];
            lastPosition[1] = currentPosition[1];
            mindMap[currentPosition[0]][currentPosition[1]] = getWhatISee()[0];
            currentPosition[0]++;
        } else {
            findingWallsMovementLogic(Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT);
            wallSearch.put(movementIndex, lastMove);

        }
        lastGWIS = getWhatISee();
    }

//    private void findTopLeftCorner() {
//        if (getWhatISee()[1] == Places.WALL) {
//            if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
//                dir = Direction.IDLE;
//                secondToLastMove = lastMove;
//                lastMove = Direction.IDLE;
//                foundTopLeftCorner = true;
//                System.out.println("found top left corner");
//            } else {
//                dir = Direction.TURN_RIGHT_ON_SPOT;
//                secondToLastMove = lastMove;
//                lastMove = Direction.TURN_RIGHT_ON_SPOT;
//            }
//        } else {
//            movementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
//        }
//
//        lastGWIS = getWhatISee();
//    }

    private void findingWallsMovementLogic(Direction firstTurn, Direction secondTurn) {
        if (lastGWIS[0] != lastGWIS[1]
                && lastGWIS != getWhatISee()
                && !isNextSquareUsable()) {
            isTurning = true;
            dir = firstTurn;
            secondToLastMove = lastMove;
            lastMove = firstTurn;
        } else if (isTurning) {
            if (lastMove == Direction.TURN_LEFT_ON_SPOT || lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                if (lastMove == secondTurn) {
                    isTurning = false;
                    movementIndex++;
                    distanceFromLeftWall++;
                }
                dir = Direction.FORWARD;
                secondToLastMove = lastMove;
                lastMove = Direction.FORWARD;
            } else if (lastMove == Direction.FORWARD) {
                dir = secondTurn;
                secondToLastMove = lastMove;
                lastMove = secondTurn;
            }
        } else {
            dir = Direction.FORWARD;
            secondToLastMove = lastMove;
            lastMove = Direction.FORWARD;
            movementIndex++;
        }
    }

    private void assignMindMap() {
        mindMap[currentPosition[0]][currentPosition[1]] = getWhatISee()[0];
        int columnIncrease = currentPosition[1] - lastPosition[1];
        int rowIncrease = currentPosition[0] - lastPosition[0];
        if (getWhatISee()[1] != Places.WALL) {
            if (rowIncrease > 0) {
                // stepped in y direction by 1
                if (lastMove == Direction.TURN_LEFT_ON_SPOT) {
                    mindMap[currentPosition[0]][currentPosition[1]+1] = getWhatISee()[1];
                } else if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                    mindMap[currentPosition[0]][currentPosition[1]-1] = getWhatISee()[1];
                } else {
                    mindMap[currentPosition[0] + 1][currentPosition[1]] = getWhatISee()[1];
                }
            } else if (rowIncrease < 0) {
                // stepped in y direction by -1
                if (lastMove == Direction.TURN_LEFT_ON_SPOT) {
                    mindMap[currentPosition[0]][currentPosition[1]-1] = getWhatISee()[1];
                } else if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                    mindMap[currentPosition[0]][currentPosition[1]+1] = getWhatISee()[1];
                } else {
                    mindMap[currentPosition[0] - 1][currentPosition[1] - 1] = getWhatISee()[1];
                }

            } else if (columnIncrease > 0) {
                // stepped in x direction by 1
                if (lastMove == Direction.TURN_LEFT_ON_SPOT) {
                    mindMap[currentPosition[0]-1][currentPosition[1]] = getWhatISee()[1];
                } else if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                    mindMap[currentPosition[0]+1][currentPosition[1]] = getWhatISee()[1];
                } else {
                    mindMap[currentPosition[0]][currentPosition[1] + 1] = getWhatISee()[1];
                }
            } else if (columnIncrease < 0) {
                if (lastMove == Direction.TURN_LEFT_ON_SPOT) {
                    mindMap[currentPosition[0]+1][currentPosition[1]] = getWhatISee()[1];
                } else if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                    mindMap[currentPosition[0]-1][currentPosition[1]] = getWhatISee()[1];
                } else {
                    mindMap[currentPosition[0]][currentPosition[1] - 1] = getWhatISee()[1];
                }
            }
        }
    }

    private void pickNewDirection() {
        Random random = new Random();
        int randomInt = random.nextInt(2);
        int result = (randomInt == 0) ? 0 : 1;
        if (result == 0) {
            dir = Direction.TURN_LEFT_ON_SPOT;
        } else {
            dir = Direction.TURN_RIGHT_ON_SPOT;
        }
        secondToLastMove = lastMove;
        lastMove = dir;
        stepsMade = 0;
    }

    private void movementLogic() {
        if (stepsMade == 5 || (!isNextSquareUsable() && getWhatISee()[1] != Places.WALL)) {
            pickNewDirection();
            assignMindMap();
        } else {
            int rowIncrease = currentPosition[0] - lastPosition[0];
            int columnIncrease = currentPosition[1] - lastPosition[1];
            lastPosition[0] = currentPosition[0];
            lastPosition[1] = currentPosition[1];
            if (getWhatISee()[1] == Places.WALL) {
                dir = Direction.BACK;
                secondToLastMove = lastMove;
                lastMove = dir;
                if (currentPosition[0] == 0) {
                    currentPosition[0]++;
                } else if (currentPosition[0].equals(verticalDimension)) {
                    currentPosition[0]--;
                } else if (currentPosition[1] == 0) {
                    currentPosition[1]++;
                } else if (currentPosition[1].equals(horizontalDimension)) {
                    currentPosition[1]--;
                }
            } else {
                dir = Direction.FORWARD;

                if (columnIncrease > 0) {
                    currentPosition[1]++;
                } else if (columnIncrease < 0) {
                    currentPosition[1]--;
                } else if (rowIncrease > 0) {
                    currentPosition[0]++;
                } else if (rowIncrease < 0) {
                    currentPosition[0]--;
                }

                secondToLastMove = lastMove;
                lastMove = dir;
                stepsMade++;
            }
        }

    }

    private void printMindMap() {
        System.out.println("Alisa's mindmap: ");
        int[] columnWidths = new int[mindMap[0].length];
        for (int i = 0; i < mindMap.length; i++) {
            for (int j = 0; j < mindMap[i].length; j++) {
                int length = 0;
                if (mindMap[i][j] != null) {
                    length = mindMap[i][j].toString().length();
                } else {
                    length = "null".length();
                }
                if (length > columnWidths[j]) {
                    columnWidths[j] = length;
                }
            }
        }

        // Display the 2D array with even columns
        // Display the 2D array with even columns
        for (int i = 0; i < mindMap.length; i++) {
            for (int j = 0; j < mindMap[i].length; j++) {
                // Pad the string with spaces to match the maximum width
                if (mindMap[i][j] != null) {
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", mindMap[i][j]);
                } else {
                    String bla = "null";
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", bla);
                }

            }
            System.out.println(); // Move to the next line for the next row
        }
    }

    public Direction moveAvatar() {
        if (!foundLeftWall) {
            findLeftWall();
        } else if (!foundBottomWall) {
            findBottomWall();
        } else if (!foundTopWall) {
            findTopWall();
        } else {
            if (testStepsMade >= 50) {

                if (!isMindMapPrinted) {
                    System.out.println(mindMap[5][0]);
                    printMindMap();
                    isMindMapPrinted = true;
                }
                dir = Direction.IDLE;

            } else {
                movementLogic();
                testStepsMade++;
            }
        }
        return dir;
    }
}
