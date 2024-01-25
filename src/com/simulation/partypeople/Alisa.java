package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
    static int maxSteps = 4;
    int countPickingDirection = 0;
    boolean printed = false;


    int placesDiscovered = 0;
    int[] targetPosition = {0, 0}; // UP, DOWN, LEFT, RIGHT

    boolean danced = false;
    int danceStep = 0;
    boolean orderedDrink = false;
    boolean visitedLounge = false;
    boolean foundDancefloor = false;
    boolean foundLounge = false;
    boolean foundBar = false;

    Direction[] turningOrder = new Direction[2];

    String currentHeading = "DOWN";
    static Places[][] mindmap;
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

    private void turnForWalls(Direction firstTurn, Direction secondTurn) {
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
                    turnForWalls(Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT);
                } else {
                    turnForWalls(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
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

    private void setDirectionTurnLeftOnSpot() {
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
    }

    private void setDirectionTurnRightOnSpot() {
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

    private void setDirectionForward() {
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

    private void setDirectionRight() {
        dir = Direction.RIGHT;
        lastPosition[0] = currentPosition[0];
        lastPosition[1] = currentPosition[1];
        switch (currentHeading) {
            case "DOWN":
                currentPosition[1]--;
                currentHeading = "LEFT";
                break;
            case "UP":
                currentPosition[1]++;
                currentHeading = "RIGHT";
                break;
            case "LEFT":
                currentPosition[0]--;
                currentHeading = "UP";
                break;
            case "RIGHT":
                currentPosition[0]++;
                currentHeading = "DOWN";
                break;
        }
    }

    private void setDirectionLeft() {
        dir = Direction.LEFT;
        lastPosition[0] = currentPosition[0];
        lastPosition[1] = currentPosition[1];
        switch (currentHeading) {
            case "DOWN":
                currentPosition[1]++;
                currentHeading = "RIGHT";
                break;
            case "UP":
                currentPosition[1]--;
                currentHeading = "LEFT";
                break;
            case "LEFT":
                currentPosition[0]++;
                currentHeading = "DOWN";
                break;
            case "RIGHT":
                currentPosition[0]--;
                currentHeading = "UP";
                break;
        }
    }

    private void setDirectionBack() {
        dir = Direction.BACK;
        lastPosition[0] = currentPosition[0];
        lastPosition[1] = currentPosition[1];
        switch (currentHeading) {
            case "DOWN":
                currentPosition[0]--;
                currentHeading = "UP";
                break;
            case "UP":
                currentPosition[0]++;
                currentHeading = "DOWN";
                break;
            case "LEFT":
                currentPosition[1]++;
                currentHeading = "RIGHT";
                break;
            case "RIGHT":
                currentPosition[1]--;
                currentHeading = "LEFT";
                break;
        }
    }

    private void pickNewDirection() {
        Random random = new Random();
        int randomInt = random.nextInt(3);
        switch (randomInt) {
            case 0:
                setDirectionTurnLeftOnSpot();
                break;
            case 1:
                setDirectionTurnRightOnSpot();
                break;
            default:
                if (isNextSquareUsable()) {
                    setDirectionForward();
                } else {
                    setDirectionTurnLeftOnSpot();
                }
                break;
        }
    }

    private float shareOfUndiscoveredPlaces(int startRow, int endRow, int startCol, int endCol) {
        float share = 0;
        int subArraySize = (endRow - startRow + 1) * (endCol - startCol + 1);
        int countNulls = 0;
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (mindmap[i][j] == null) {
                    countNulls++;
                }
            }
        }
        share = (float) countNulls / subArraySize;

        return share;
    }

    private void analyzeDirection() {
        int startRow = currentPosition[0];
        int endRow = currentPosition[0];
        int startCol = currentPosition[1];
        int endCol = currentPosition[1];

        switch (currentHeading) {
            case "DOWN":
                startRow++;
                endRow += maxSteps;
                break;
            case "UP":
                endRow--;
                startRow -= maxSteps;
                break;
            case "LEFT":
                endCol--;
                startCol -= maxSteps;
                break;
            case "RIGHT":
                startCol++;
                endCol += maxSteps;
        }

        if (startRow > 0 && startCol > 0 && endRow < rows && endCol < cols) {
            float shareOfUndiscovered = shareOfUndiscoveredPlaces(startRow, endRow, startCol, endCol);
            if (shareOfUndiscovered >= 0.5) {
                setDirectionForward();
                countPickingDirection = 0;
            } else {
                if (countPickingDirection <= 3) {
                    if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                        setDirectionTurnLeftOnSpot();
                    } else {
                        setDirectionTurnRightOnSpot();
                    }
                    countPickingDirection++;
                } else {
                    setDirectionForward();
                }

            }
        } else {
            if (countPickingDirection <= 3) {
                if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                    setDirectionTurnLeftOnSpot();
                } else {
                    setDirectionTurnRightOnSpot();
                }
                countPickingDirection++;
            } else {
                setDirectionForward();
            }
        }
    }

    private void recordPlace() {
        if (mindmap[currentPosition[0]][currentPosition[1]] == null
                || mindmap[currentPosition[0]][currentPosition[1]] == Places.PERSON) {
            mindmap[currentPosition[0]][currentPosition[1]] = getWhatISee()[0];
            placesDiscovered++;
        }

        int rowIncrease = currentPosition[0] - lastPosition[0];
        int colIncrease = currentPosition[1] - lastPosition[1];

        if (getWhatISee()[1] != Places.WALL && getWhatISee()[1] != Places.PERSON) {
            if (rowIncrease > 0) {
                // moved down in map
                if (mindmap[currentPosition[0] + 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] + 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] + 1][currentPosition[1]] = getWhatISee()[1];
                    placesDiscovered++;
                }
            } else if (rowIncrease < 0) {
                // moved up in map
                if (mindmap[currentPosition[0] - 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] - 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] - 1][currentPosition[1]] = getWhatISee()[1];
                    placesDiscovered++;
                }
            } else if (colIncrease > 0) {
                // moved right in map
                if (mindmap[currentPosition[0]][currentPosition[1] + 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] + 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] + 1] = getWhatISee()[1];
                    placesDiscovered++;
                }
            } else if (colIncrease < 0) {
                // moved left in map
                if (mindmap[currentPosition[0]][currentPosition[1] - 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] - 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] - 1] = getWhatISee()[1];
                    placesDiscovered++;
                }
            }
        }

    }

    private void setMovement() {
        recordPlace();
        if (stepsMade == maxSteps || !isNextSquareUsable()) {
            pickNewDirection();
            stepsMade = 0;
            lastPosition[0] = currentPosition[0];
            lastPosition[1] = currentPosition[1];
        } else {
            if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT || lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                analyzeDirection();
            } else {
                setDirectionForward();
            }
        }
        updateLastTwoMoves();
    }

    private void dance() {
        if (getWhatISee()[1] == Places.PERSON) {
            dir = Direction.IDLE;
            danceStep = 1;
        } else {
            switch (danceStep) {
                case 0, 1:
                    setDirectionForward();
                    stepsMade = 0;
                    danceStep++;
                    break;
                case 2:
                    setDirectionRight();
                    danceStep++;
                    break;
                case 3:
                    if (getWhatISee()[0] == Places.DANCEFLOOR) {
                        setDirectionRight();
                        danceStep++;
                    } else {
                        setDirectionBack();
                        danceStep = 1;
                    }
                    break;
                case 4:
                    setDirectionRight();
                    danced = true;
                    danceStep = 0;
                    break;
            }
        }
    }

    static List<Coordinates2> findCoordinates(Places[] targetPlaces) {
        List<Coordinates2> coordinates2List = new ArrayList<>();
        for (int i = 0; i < mindmap.length; i++) {
            for (int j = 0; j < mindmap[i].length; j++) {
                if (Arrays.asList(targetPlaces).contains(mindmap[i][j])) {
                    coordinates2List.add(new Coordinates2(i, j));
                }
            }
        }
        return coordinates2List;
    }

    private void findClosestCoordinate(List<Coordinates2> coordinates2List, int myRow, int myColumn) {
        double minDistance = Double.MAX_VALUE;
        Coordinates2 closestCoordinate = null;

        for (Coordinates2 coordinates2 : coordinates2List) {
            double distance = calculateDistance(myRow, myColumn, coordinates2.row, coordinates2.col);

            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = coordinates2;
            }
        }

        assert closestCoordinate != null;
        targetPosition[0] = closestCoordinate.row;
        targetPosition[1] = closestCoordinate.col;
    }

    static double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

//    private void turn(Direction firstTurn, Direction secondTurn) {
//        if (!isTurning) {
//            if (firstTurn == Direction.TURN_LEFT_ON_SPOT) {
//                setDirectionTurnLeftOnSpot();
//            } else {
//                setDirectionTurnRightOnSpot();
//            }
//            isTurning = true;
//        } else {
//            if (lastTwoMoves[0] == Direction.FORWARD) {
//                if (firstTurn == Direction.TURN_LEFT_ON_SPOT) {
//                    setDirectionTurnLeftOnSpot();
//                } else {
//                    setDirectionTurnRightOnSpot();
//                }
//            } else if (lastTwoMoves[0] == firstTurn || lastTwoMoves[0] == Direction.IDLE) {
//                if (getWhatISee()[1] == Places.PERSON) {
//                    if (lastTwoMoves[0] == Direction.IDLE && lastTwoMoves[1] == Direction.IDLE) {
//                        setDirectionBack();
//                    } else {
//                        dir = Direction.IDLE;
//                    }
//                } else {
//                    setDirectionForward();
//                }
//            } else if (lastTwoMoves[0] == secondTurn) {
//                if (isNextSquareUsable()) {
//                    setDirectionForward();
//                    isTurning = false;
//                } else {
//                    if (firstTurn == Direction.TURN_LEFT_ON_SPOT) {
//                        setDirectionTurnLeftOnSpot();
//                    } else {
//                        setDirectionTurnRightOnSpot();
//                    }
//                }
//            } else if (lastTwoMoves[0] == Direction.BACK) {
//                if (firstTurn == Direction.TURN_LEFT_ON_SPOT) {
//                    setDirectionTurnLeftOnSpot();
//                } else {
//                    setDirectionTurnRightOnSpot();
//                }
//                isTurning = false;
//            }
//        }
//        updateLastTwoMoves();
//    }


    private void headToTarget(Places target) {
        if (currentPosition[0] == targetPosition[0] && currentPosition[1] == targetPosition[1]) {
            targetPosition[0] = 0;
            targetPosition[1] = 0;
            switch (target) {
                case DANCEFLOOR:
                    if (getWhatISee()[1] == target) {
                        foundDancefloor = true;
                    } else {
                        findDancefloor();
                    }
                    break;
                case BAR:
                    if (getWhatISee()[1] == target) {
                        foundBar = true;
                    } else {
                        findBar();
                    }
                    break;
                case BAR_CHAIR:
                    if (getWhatISee()[0] == target) {
                        foundBar = true;
                    } else {
                        findBar();
                    }
                    break;
                case LOUNGE_BIG, LOUNGE_SMALL, LOUNGE_SMOKING:
                    if (getWhatISee()[0] == target) {
                        foundLounge = true;
                    } else {
                        findLounge();
                    }

            }
        } else {
            int rowIncrease = targetPosition[0] - currentPosition[0];
            int colIncrease = targetPosition[1] - currentPosition[1];


            if (rowIncrease < 0) {
                switch (currentHeading) {
                    case "UP":
                        if (!isNextSquareUsable()) {
                            if (currentPosition[1] <= cols / 2) {
                                setDirectionTurnRightOnSpot();
                            } else {
                                setDirectionTurnLeftOnSpot();
                            }
                        } else {
                            setDirectionForward();
                        }
                        break;
                    case "DOWN":
                        setDirectionBack();
                        break;
                    case "LEFT":
                        if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnRightOnSpot();
                        }
                        break;
                    case "RIGHT":
                        if (lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnLeftOnSpot();
                        }
                        break;
                }
            } else if (rowIncrease > 0) {
                switch (currentHeading) {
                    case "UP":
                        setDirectionBack();
                        break;
                    case "DOWN":
                        if (!isNextSquareUsable()) {
                            if (currentPosition[1] <= cols / 2) {
                                setDirectionTurnLeftOnSpot();
                            } else {
                                setDirectionTurnRightOnSpot();
                            }
                        } else {
                            setDirectionForward();
                        }
                        break;
                    case "LEFT":
                        if (lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnLeftOnSpot();
                        }
                        break;
                    case "RIGHT":
                        if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnRightOnSpot();
                        }
                        break;
                }
            } else if (colIncrease < 0) {
                switch (currentHeading) {
                    case "UP":
                        if (lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnLeftOnSpot();
                        }
                        break;
                    case "DOWN":
                        if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnRightOnSpot();
                        }
                        break;
                    case "LEFT":
                        if (!isNextSquareUsable()) {
                            if (currentPosition[0] <= rows / 2) {
                                setDirectionTurnLeftOnSpot();
                            } else {
                                setDirectionTurnRightOnSpot();
                            }
                        } else {
                            setDirectionForward();
                        }
                        break;
                    case "RIGHT":
                        setDirectionBack();
                        break;
                }
            } else if (colIncrease > 0) {
                switch (currentHeading) {
                    case "UP":
                        if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnRightOnSpot();
                        }
                        break;
                    case "DOWN":
                        if (lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                setDirectionBack();
                            }
                        } else {
                            setDirectionTurnLeftOnSpot();
                        }
                        break;
                    case "LEFT":
                        setDirectionBack();
                        break;
                    case "RIGHT":
                        if (!isNextSquareUsable()) {
                            if (currentPosition[0] <= rows / 2) {
                                setDirectionTurnRightOnSpot();
                            } else {
                                setDirectionTurnLeftOnSpot();
                            }
                        } else {
                            setDirectionForward();
                        }
                        break;
                }
            }

            updateLastTwoMoves();
        }
    }

    private void findDancefloor() {
        if (foundDancefloor) {
            recordPlace();
            dance();
            updateLastTwoMoves();
        } else {
            List<Coordinates2> coordinates2List = findCoordinates(new Places[]{Places.DANCEFLOOR});
            if (!coordinates2List.isEmpty()) {
                findClosestCoordinate(coordinates2List, currentPosition[0], currentPosition[1]);
                headToTarget(Places.DANCEFLOOR);
            } else {
                if (getWhatISee()[1] != Places.DANCEFLOOR && danceStep == 0) {
                    setMovement();
                } else {
                    recordPlace();
                    dance();
                    updateLastTwoMoves();
                }
            }
        }
    }

    private void findBar() {
        if (getWhatISee()[1] == Places.BAR || getWhatISee()[0] == Places.BAR_CHAIR) {
            foundBar = true;
        }
        if (foundBar) {
            System.out.println("Alisa is ordering a drink");
            drink(BeverageType.APEROL_SPRITZ);
            orderedDrink = true;
        } else {
            Places[] bar = new Places[]{Places.BAR, Places.BAR_CHAIR};
            List<Coordinates2> coordinates2List = findCoordinates(bar);
            if (!coordinates2List.isEmpty()) {
//                System.out.println("Alisa already knows where bar is");
                findClosestCoordinate(coordinates2List, currentPosition[0], currentPosition[1]);
                Places target = mindmap[targetPosition[0]][targetPosition[1]];
                headToTarget(target);
            } else {
//                System.out.println("Alisa looking for the bar");
                if (getWhatISee()[1] != Places.BAR) {
                    setMovement();
                } else {
                    System.out.println("Alisa is ordering a drink");
                    recordPlace();
                    drink(BeverageType.APEROL_SPRITZ);
                    orderedDrink = true;
                }
            }
        }
    }

    private void findLounge() {
        Places[] lounge = new Places[]{Places.LOUNGE_SMALL, Places.LOUNGE_BIG, Places.LOUNGE_SMOKING};
        if (Arrays.asList(lounge).contains(getWhatISee()[0])) {
            System.out.println("Alisa is chilling in the lounge");
            dir = Direction.IDLE;
            visitedLounge = true;
        } else {
            if (foundLounge) {
                System.out.println("Alisa is chilling in the lounge");
                dir = Direction.IDLE;
                visitedLounge = true;
            } else {
                List<Coordinates2> coordinates2List = findCoordinates(lounge);
                if (!coordinates2List.isEmpty()) {
                    findClosestCoordinate(coordinates2List, currentPosition[0], currentPosition[1]);
                    Places target = mindmap[targetPosition[0]][targetPosition[1]];
                    headToTarget(target);
                } else {
                    if (!Arrays.asList(lounge).contains(getWhatISee()[1])) {
                        setMovement();
                    } else {
                        recordPlace();
                        System.out.println("Alisa is chilling in the lounge");
                        visitedLounge = true;
                    }
                }
            }
        }
    }

    public Direction moveAvatar() {
        if (getWhatISee()[1] == Places.PERSON) {
            System.out.println("Hi! I'm Alisa!");
        }
        try {
            if (!mindmapCreated) {
                findWalls();
            } else {
                if (!danced) {
                    findDancefloor();
                } else if (!orderedDrink) {
                    findBar();
                } else if (!visitedLounge) {
                    findLounge();
                } else {
                    dir = Direction.IDLE;
                    if (!printed) {
                        printMindmap();
                        System.out.println("Total squares: " + rows * cols);
                        System.out.println("Found places: " + placesDiscovered);
                        printed = true;
                    }
                }
            }
        } catch (Exception e) {
            dir = Direction.IDLE;
            System.out.println("Alisa failed T_T");
            System.out.println("rows: " + rows + " cols: " + cols);
            throw e;
        }

        return dir;
    }

    static class Coordinates2 {
        int row;
        int col;

        Coordinates2(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
