package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Alisa extends Avatar {
    Direction dir;
    boolean foundLeftWall = false;
    boolean foundBottomWall = false;
    boolean foundTopWall = false;
    boolean mindmapCreated = false;
    boolean isTurning = false;
    int findWallStep = 0;
    int distanceFromLeftWall = 0;
    int cols = 0;
    int rows = 0;
    int[] currentPosition = new int[2];
    int[] lastPosition = new int[2];
    int stepsMade = 0;
    int testSteps = 0;
    boolean printed = false;
    String currentHeading = "DOWN";
    Places[][] mindmap;
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
    Direction[] lastTwoMoves = new Direction[2];

    public Alisa(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
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

    private void printMindmap() {
        System.out.println("Alisa's mindmap: ");
        int[] columnWidths = new int[mindmap[0].length];
        for (int i = 0; i < mindmap.length; i++) {
            for (int j = 0; j < mindmap[i].length; j++) {
                int length = 0;
                if (mindmap[i][j] != null) {
                    length = mindmap[i][j].toString().length();
                } else {
                    length = "null".length();
                }
                if (length > columnWidths[j]) {
                    columnWidths[j] = length;
                }
            }
        }

        for (int i = 0; i < mindmap.length; i++) {
            for (int j = 0; j < mindmap[i].length; j++) {
                if (mindmap[i][j] != null) {
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", mindmap[i][j]);
                } else {
                    String bla = "null";
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", bla);
                }
            }
            System.out.println();
        }
    }


    private boolean isNextSquareUsable() {
        return Arrays.asList(usableSpots).contains(getWhatISee()[1]);
    }

    private void updateLastTwoMoves() {
        lastTwoMoves[1] = lastTwoMoves[0];
        lastTwoMoves[0] = dir;
    }

    private void turn(Direction firstTurn, Direction secondTurn) {
        if (!isTurning) {
            dir = firstTurn;
            isTurning = true;
        } else {
            if (lastTwoMoves[0] == Direction.FORWARD) {
                dir = secondTurn;
            } else if (lastTwoMoves[0] == firstTurn || lastTwoMoves[0] == Direction.IDLE) {
                if (getWhatISee()[1] == Places.PERSON) {
                    if (lastTwoMoves[0] == Direction.IDLE && lastTwoMoves[1] == Direction.IDLE) {
                        dir = Direction.BACK;
                    } else {
                        dir = Direction.IDLE;
                    }
                } else {
                    dir = Direction.FORWARD;
                    distanceFromLeftWall++;
                }
            } else if (lastTwoMoves[0] == secondTurn) {
                if (isNextSquareUsable()) {
                    dir = Direction.FORWARD;
                    findWallStep++;
                    isTurning = false;
                } else {
                    dir = firstTurn;
                }

            } else if (lastTwoMoves[0] == Direction.BACK) {
                dir = firstTurn;
                isTurning = false;
            }
        }
        updateLastTwoMoves();
    }

    private void findWall(int wall) {
        if (getWhatISee()[1] == Places.WALL && !isTurning) {
            switch (wall) {
                case 0:
                    foundLeftWall = true;
                    cols = findWallStep;
                    findWallStep = 0;
                    dir = Direction.TURN_LEFT_ON_SPOT;
                    distanceFromLeftWall = 0;
                    break;
                case 1:
                    foundBottomWall = true;
                    dir = Direction.BACK;
                    findWallStep = 2;
                    break;
                case 2:
                    foundTopWall = true;
                    rows = findWallStep;
                    dir = Direction.BACK;
                    mindmap = new Places[rows][cols];
                    mindmap[0][distanceFromLeftWall] = getWhatISee()[0];
                    lastPosition[0] = 0;
                    lastPosition[1] = distanceFromLeftWall;
                    currentPosition[0] = 1;
                    currentPosition[1] = distanceFromLeftWall;
                    mindmapCreated = true;
                    break;
            }
        } else {
            if (!isNextSquareUsable() || isTurning) {
                if (lastTwoMoves[0] == null && dir != Direction.FORWARD) {
                    findWallStep++;
                }
                if (wall == 2) {
                    turn(Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT);
                } else {
                    turn(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
                }
            } else if (isNextSquareUsable()) {
                dir = Direction.FORWARD;
                findWallStep++;
            }
        }
        updateLastTwoMoves();
    }

    private void findWalls() {
        if (!foundLeftWall) {
            findWall(0);
        } else if (!foundBottomWall) {
            findWall(1);
        } else if (!foundTopWall) {
            findWall(2);
        }
    }

    private void pickNewDirection() {
        Random random = new Random();
        int randomInt = random.nextInt(2);
        int result = (randomInt == 0) ? 0 : 1;
        if (result == 0) {
            dir = Direction.TURN_LEFT_ON_SPOT;
            switch (currentHeading) {
                case "DOWN":
                    currentHeading = "RIGHT";
                    break;
                case "UP":
                    currentHeading = "LEFT";
                    break;
                case "LEFT":
                    currentHeading = "DOWN";
                    break;
                case "RIGHT":
                    currentHeading = "UP";
                    break;
            }
        } else {
            dir = Direction.TURN_RIGHT_ON_SPOT;
            switch (currentHeading) {
                case "DOWN":
                    currentHeading = "LEFT";
                    break;
                case "UP":
                    currentHeading = "RIGHT";
                    break;
                case "LEFT":
                    currentHeading = "UP";
                    break;
                case "RIGHT":
                    currentHeading = "DOWN";
                    break;
            }
        }
    }

    private void recordPlace() {
//        System.out.println("entered function");
        if (mindmap[currentPosition[0]][currentPosition[1]] == null
                || mindmap[currentPosition[0]][currentPosition[1]] == Places.PERSON) {
            mindmap[currentPosition[0]][currentPosition[1]] = getWhatISee()[0];
//            System.out.println("assigned current: " + mindmap[currentPosition[0]][currentPosition[1]]);
        }

        int rowIncrease = currentPosition[0] - lastPosition[0];
        int colIncrease = currentPosition[1] - lastPosition[1];
//        System.out.println(dir);
//        System.out.println(getWhatISee()[1]);
//        System.out.println("rowinc: " + rowIncrease + " colInc: " + colIncrease);
        if (getWhatISee()[1] != Places.WALL) {
            if (rowIncrease > 0) {
                // moved down in map
                if (mindmap[currentPosition[0] + 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] + 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] + 1][currentPosition[1]] = getWhatISee()[1];
//                    System.out.println("assigned next: " + mindmap[currentPosition[0] + 1][currentPosition[1]]);
                }
            } else if (rowIncrease < 0) {
                // moved up in map
                if (mindmap[currentPosition[0] - 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] - 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] - 1][currentPosition[1]] = getWhatISee()[1];
//                    System.out.println("assigned next: " + mindmap[currentPosition[0] - 1][currentPosition[1]]);
                }
            } else if (colIncrease > 0) {
                // moved right in map
                if (mindmap[currentPosition[0]][currentPosition[1] + 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] + 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] + 1] = getWhatISee()[1];
//                    System.out.println("assigned next: " + mindmap[currentPosition[0]][currentPosition[1] + 1]);
                }
            } else if (colIncrease < 0) {
                // moved left in map
                if (mindmap[currentPosition[0]][currentPosition[1] - 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] - 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] - 1] = getWhatISee()[1];
//                    System.out.println("assigned next: " + mindmap[currentPosition[0]][currentPosition[1] - 1]);
                }
            }
        }

    }

    private void setMovement() {
        recordPlace();
        if (stepsMade == 5 || !isNextSquareUsable()) {
            pickNewDirection();
            stepsMade = 0;
            lastPosition[0] = currentPosition[0];
            lastPosition[1] = currentPosition[1];
        } else {
            dir = Direction.FORWARD;
            lastPosition[0] = currentPosition[0];
            lastPosition[1] = currentPosition[1];
            switch (currentHeading) {
                case "DOWN":
                    currentPosition[0]++;
                    break;
                case "UP":
                    currentPosition[0]--;
                    break;
                case "LEFT":
                    currentPosition[1]--;
                    break;
                case "RIGHT":
                    currentPosition[1]++;
                    break;
            }
            stepsMade++;
        }
        updateLastTwoMoves();
    }

    public Direction moveAvatar() {
        if (!mindmapCreated) {
            findWalls();
        } else {
            if (testSteps == 200) {
                if (!printed) {
                    printMindmap();
                    printed = true;
                }
                dir = Direction.IDLE;
            } else {
                setMovement();
                testSteps++;
            }

        }

        return dir;
    }
}
