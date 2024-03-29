package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Alisa extends Avatar {
    Direction dir;
    boolean foundLeftWall = false;
    boolean foundBottomWall = false;
    boolean foundTopWall = false;
    boolean mindmapCreated = false;
    boolean investigating = false;
    boolean isTurning = false;
    int rows = 0;
    int cols = 0;
    int findWallStep = 1;
    int distanceFromLeftWall = 0;
    Places[] lastSeenSquares = new Places[2];
    int[] currentPosition = new int[2];
    int[] lastPosition = new int[2];
    int[] targetPosition = new int[2];
    String currentHeading = "";
    Places[][] mindmap;
    HashSet<Places> usableSpots = new HashSet<>();
    HashSet<Places> unusableSpots = new HashSet<>();
    Direction[] lastTwoMoves = new Direction[2];
    int stepsMade = 0;
    int maxSteps = 4;
    int countPickingDirection = 0;
    boolean danced = false;
    boolean foundDancefloor = false;
    int danceStep = 0;
    boolean orderedDrink = false;
    boolean foundBar = false;
    boolean visitedLounge = false;
    boolean foundLounge = false;
    boolean printed = false;
    boolean failed = true;

    public Alisa(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
    }

    // the few methods below are for updating the avatar's position and heading according to the chosen direction
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
            default:
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
            default:
                break;
        }
    }

    private void setDirectionForward() {
        dir = Direction.FORWARD;
        updateLastPosition();
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
            default:
                break;
        }
        stepsMade++;
    }

    private void setDirectionRight() {
        dir = Direction.RIGHT;
        updateLastPosition();
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
            default:
                break;
        }
    }

    private void setDirectionLeft() {
        dir = Direction.LEFT;
        updateLastPosition();
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
            default:
                break;
        }
    }

    private void setDirectionBack() {
        dir = Direction.BACK;
        updateLastPosition();
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
            default:
                break;
        }
    }

    private void undoSettingCurrentPosition() {
        switch (currentHeading) {
            case "DOWN":
                currentPosition[0]--;
                break;
            case "UP":
                currentPosition[0]++;
                break;
            case "LEFT":
                currentPosition[1]++;
                break;
            case "RIGHT":
                currentPosition[1]--;
                break;
            default:
                break;
        }
        updateLastPosition();
        stepsMade--;
    }

    // memorize last two moves
    private void updateLastTwoMoves() {
        lastTwoMoves[1] = lastTwoMoves[0];
        lastTwoMoves[0] = dir;
    }

    // memorize last position
    private void updateLastPosition() {
        lastPosition[0] = currentPosition[0];
        lastPosition[1] = currentPosition[1];
    }

    // check if next square is usable
    private boolean isNextSquareUsable() {
        return usableSpots.contains(getWhatISee()[1]);
    }

    // check if next square is unusable
    private boolean isNextSquareUnusable() {
        return unusableSpots.contains(getWhatISee()[1]);
    }

    // memorize last seen squares
    private void recordLastSeenSquares() {
        lastSeenSquares[0] = getWhatISee()[0];
        lastSeenSquares[1] = getWhatISee()[1];
    }

    // check if the square in front is usable or not
    private void investigateSquare() {
        if (!investigating) {
            investigating = true;
            recordLastSeenSquares();
            setDirectionForward();
            findWallStep++;
        } else {
            if (lastSeenSquares[0] == getWhatISee()[0] && lastSeenSquares[1] == getWhatISee()[1]) {
                unusableSpots.add(getWhatISee()[1]);
                undoSettingCurrentPosition();
                findWallStep--;
            } else {
                setDirectionForward();
                findWallStep++;
            }
            investigating = false;
        }
    }

    // algorithm for performing turns during wall search
    private void turnDuringWallSearch(Direction firstTurn, Direction secondTurn) {
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
    }

    // algorithm for finding walls
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
                    currentHeading = "DOWN";
                    stepsMade = 0;
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
            if (isNextSquareUnusable() || isTurning) {
                if (wall == 2) {
                    turnDuringWallSearch(Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT);
                } else {
                    turnDuringWallSearch(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
                }
            } else if (isNextSquareUsable()) {
                dir = Direction.FORWARD;
                findWallStep++;
            }
        }
    }


    // wrapper method to find walls
    private void findWalls() {
        if (!foundLeftWall) {
            findWall(0);
        } else if (!foundBottomWall) {
            findWall(1);
        } else if (!foundTopWall) {
            findWall(2);
        }
    }

    // recording spot avatar is standing on as usable
    private void addCurrentSpotToUsable() {
        usableSpots.add(getWhatISee()[0]);
    }

    // introduce yourself when there is a person in front of you
    private void bePolite() {
        if (getWhatISee()[1] == Places.PERSON) {
            unusableSpots.add(getWhatISee()[1]);
            System.out.println("Hi! I'm Alisa!");
        }
    }

    // recording the new place in the mindmap
    private void addPlaceToMindmap() {
        if (mindmap[currentPosition[0]][currentPosition[1]] == null) {
            mindmap[currentPosition[0]][currentPosition[1]] = getWhatISee()[0];
        }

        int rowIncrease = currentPosition[0] - lastPosition[0];
        int colIncrease = currentPosition[1] - lastPosition[1];

        if (getWhatISee()[1] != Places.WALL) {
            if (rowIncrease > 0) {
                // moved down in map
                if (mindmap[currentPosition[0] + 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] + 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] + 1][currentPosition[1]] = getWhatISee()[1];
                }
            } else if (rowIncrease < 0) {
                // moved up in map
                if (mindmap[currentPosition[0] - 1][currentPosition[1]] == null
                        || mindmap[currentPosition[0] - 1][currentPosition[1]] == Places.PERSON) {
                    mindmap[currentPosition[0] - 1][currentPosition[1]] = getWhatISee()[1];
                }
            } else if (colIncrease > 0) {
                // moved right in map
                if (mindmap[currentPosition[0]][currentPosition[1] + 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] + 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] + 1] = getWhatISee()[1];
                }
            } else if (colIncrease < 0) {
                // moved left in map
                if (mindmap[currentPosition[0]][currentPosition[1] - 1] == null
                        || mindmap[currentPosition[0]][currentPosition[1] - 1] == Places.PERSON) {
                    mindmap[currentPosition[0]][currentPosition[1] - 1] = getWhatISee()[1];
                }
            }
        }
    }

    // find all existing coordinates for a target place
    private List<Coordinates> findExistingCoordinates(Places[] targetPlaces) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < mindmap.length; i++) {
            for (int j = 0; j < mindmap[i].length; j++) {
                if (Arrays.asList(targetPlaces).contains(mindmap[i][j])) {
                    coordinates.add(new Coordinates(i, j));
                }
            }
        }
        return coordinates;
    }

    // finding the closest target coordinate from the map
    private void findClosestCoordinate(List<Coordinates> coordinatesList, int myRow, int myColumn) {
        float minDistance = rows * cols;
        Coordinates closestCoordinate = null;

        for (Coordinates coordinates : coordinatesList) {
            float distance = calculateDistance(myRow, myColumn, coordinates.row, coordinates.col);

            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = coordinates;
            }
        }

        assert closestCoordinate != null;
        targetPosition[0] = closestCoordinate.row;
        targetPosition[1] = closestCoordinate.col;
    }

    // helper method to calculate distance to target square
    static float calculateDistance(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // check if the seat the avatar picked as target is occupied
    private void checkIfSeatOccupied() {
//        System.out.println("checking occupants");
        if (getWhatISee()[1] == Places.PERSON) {
            int rowIncrease = currentPosition[0] - lastPosition[0];
            int colIncrease = currentPosition[1] - lastPosition[1];

            if (rowIncrease > 0) {
                mindmap[currentPosition[0] + 1][currentPosition[1]] = getWhatISee()[1];
            } else if (rowIncrease < 0) {
                mindmap[currentPosition[0] - 1][currentPosition[1]] = getWhatISee()[1];
            } else if (colIncrease > 0) {
                mindmap[currentPosition[0]][currentPosition[1] + 1] = getWhatISee()[1];
            } else if (colIncrease < 0) {
                mindmap[currentPosition[0]][currentPosition[1] - 1] = getWhatISee()[1];
            }
//            System.out.println("Gotta find a new spot");
            setDirectionTurnLeftOnSpot();
        } else {
//            System.out.println("Occupying");
            setDirectionForward();
        }
    }

    // algorithm for heading to target square
    private void headToTarget(Places target) {
        if (currentPosition[0] == targetPosition[0] && currentPosition[1] == targetPosition[1]) {
            targetPosition[0] = 0;
            targetPosition[1] = 0;
            switch (target) {
                case DANCEFLOOR:
                    foundDancefloor = true;
                    dir = Direction.IDLE;
                    break;
                case BAR, BAR_CHAIR:
                    foundBar = true;
                    dir = Direction.IDLE;
                    break;
                case LOUNGE_BIG, LOUNGE_SMALL, LOUNGE_SMOKING:
                    foundLounge = true;
                    dir = Direction.IDLE;
                    break;
            }
        } else {
            int rowIncrease = targetPosition[0] - currentPosition[0];
            int colIncrease = targetPosition[1] - currentPosition[1];
            if (rowIncrease == 0 && (colIncrease == 1 || colIncrease == -1)
                    || colIncrease == 0 && (rowIncrease == 1 || rowIncrease == -1)) {
                if (colIncrease == 1 && Objects.equals(currentHeading, "RIGHT")
                        || colIncrease == -1 && Objects.equals(currentHeading, "LEFT")
                        || rowIncrease == 1 && Objects.equals(currentHeading, "DOWN")
                        || rowIncrease == -1 && Objects.equals(currentHeading, "UP")) {
                    checkIfSeatOccupied();
                }
            } else {
                if (rowIncrease < 0) {
                    switch (currentHeading) {
                        case "UP":
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                if (currentPosition[1] <= cols / 2) {
                                    setDirectionTurnRightOnSpot();
                                } else {
                                    setDirectionTurnLeftOnSpot();
                                }
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
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                if (currentPosition[1] <= cols / 2) {
                                    setDirectionTurnLeftOnSpot();
                                } else {
                                    setDirectionTurnRightOnSpot();
                                }
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
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                if (currentPosition[0] <= rows / 2) {
                                    setDirectionTurnLeftOnSpot();
                                } else {
                                    setDirectionTurnRightOnSpot();
                                }
                            }
                            break;
                        case "RIGHT":
                            setDirectionBack();
                            break;
                    }
                } else {
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
                            if (isNextSquareUsable()) {
                                setDirectionForward();
                            } else {
                                if (currentPosition[0] <= rows / 2) {
                                    setDirectionTurnRightOnSpot();
                                } else {
                                    setDirectionTurnLeftOnSpot();
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    // randomizer for picking next direction
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

    // calculating share of undiscovered places in the picked direction
    private float shareOfUndiscoveredPlaces(int startRow, int endRow, int startCol, int endCol) {
        float share;
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

    // analysis of the picked direction
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

        if ((endRow < rows || endRow >= 0) && (endCol < cols || endCol >= 0)) {
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

    // assign next move when moving randomly
    private void setMovement() {
        if (stepsMade == maxSteps || isNextSquareUnusable()) {
            pickNewDirection();
            stepsMade = 0;
            updateLastPosition();
        } else {
            if (lastTwoMoves[0] == Direction.TURN_LEFT_ON_SPOT || lastTwoMoves[0] == Direction.TURN_RIGHT_ON_SPOT) {
                analyzeDirection();
            } else {
                setDirectionForward();
            }
        }
    }

    // dancing algorithm
    private void dance() {
        if (getWhatISee()[1] == Places.PERSON) {
            dir = Direction.IDLE;
//            System.out.println("Alisa is waiting to bust a move");
//            danceStep = 0;
        } else {
            System.out.println("Alisa is dancing");
            switch (danceStep) {
                case 0, 1, 3, 5:
                    setDirectionForward();
                    stepsMade = 0;
                    danceStep++;
                    break;
                case 2, 6:
                    setDirectionTurnRightOnSpot();
                    danceStep++;
                    break;
                case 4:
                    if (getWhatISee()[0] == Places.DANCEFLOOR) {
                        setDirectionTurnRightOnSpot();
                        danceStep++;
                    } else {
                        setDirectionBack();
                        danceStep = 1;
                    }
                    break;
                case 7:
                    setDirectionForward();
                    danceStep = 0;
                    danced = true;
            }
        }
    }

    // logic to find dancefloor
    private void findDancefloor() {
        if (foundDancefloor || getWhatISee()[0] == Places.DANCEFLOOR) {
            foundDancefloor = true;
            dance();
        } else {
            List<Coordinates> existingCoordinates = findExistingCoordinates(new Places[]{Places.DANCEFLOOR});
            if (!existingCoordinates.isEmpty()) {
                findClosestCoordinate(existingCoordinates, currentPosition[0], currentPosition[1]);
                headToTarget(Places.DANCEFLOOR);
            } else {
                if (getWhatISee()[1] != Places.DANCEFLOOR && danceStep == 0) {
                    setMovement();
                } else {
                    dance();
                }
            }
        }
    }


    // logic to find bar
    private void findBar() {
        if (foundBar || getWhatISee()[0] == Places.BAR_CHAIR || getWhatISee()[1] == Places.BAR) {
            System.out.println("Alisa is ordering a drink");
            drink(BeverageType.APEROL_SPRITZ);
            orderedDrink = true;
            foundBar = true;
        } else {
            Places[] bar = new Places[]{Places.BAR, Places.BAR_CHAIR};
            List<Coordinates> existingCoordinates = findExistingCoordinates(bar);
            if (!existingCoordinates.isEmpty()) {
                findClosestCoordinate(existingCoordinates, currentPosition[0], currentPosition[1]);
                Places target = mindmap[targetPosition[0]][targetPosition[1]];
                headToTarget(target);
            } else {
                if (getWhatISee()[1] == Places.BAR_CHAIR) {
                    setDirectionForward();
                } else {
                    setMovement();
                }
            }
        }
    }

    // logic to find lounge
    private void findLounge() {
        Places[] lounge = new Places[]{Places.LOUNGE_SMALL, Places.LOUNGE_BIG, Places.LOUNGE_SMOKING};
        if (foundLounge || Arrays.asList(lounge).contains(getWhatISee()[0])) {
            System.out.println("Alisa is chilling in the lounge");
            dir = Direction.IDLE;
            visitedLounge = true;
        } else {
            List<Coordinates> existingCoordinates = findExistingCoordinates(lounge);
            if (!existingCoordinates.isEmpty()) {
                findClosestCoordinate(existingCoordinates, currentPosition[0], currentPosition[1]);
                Places target = mindmap[targetPosition[0]][targetPosition[1]];
                headToTarget(target);
            } else {
                if (Arrays.asList(lounge).contains(getWhatISee()[1])) {
                    setDirectionForward();
                } else {
                    setMovement();
                }
            }
        }
    }

    // main class for moving avatar
    public Direction moveAvatar() {
        try {
            addCurrentSpotToUsable();
            bePolite();
            if (!isNextSquareUsable() && !isNextSquareUnusable() || investigating) {
                investigateSquare();
            } else {
                if (!mindmapCreated) {
                    findWalls();
                } else {
                    addPlaceToMindmap();
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
                            printed = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            if(!failed) {
                System.out.println("Alisa failed T_T");
                failed = true;
            }
            dir = Direction.IDLE;
        }
        updateLastTwoMoves();
        return dir;
    }

    // Method to print the created map
    private void printMindmap() {
        System.out.println("Alisa's mindmap: ");
        int[] columnWidths = new int[mindmap[0].length];
        for (Places[] places : mindmap) {
            for (int j = 0; j < places.length; j++) {
                int length = 0;
                if (places[j] != null) {
                    length = places[j].toString().length();
                } else {
                    length = "null".length();
                }
                if (length > columnWidths[j]) {
                    columnWidths[j] = length;
                }
            }
        }

        for (Places[] places : mindmap) {
            for (int j = 0; j < places.length; j++) {
                if (places[j] != null) {
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", places[j]);
                } else {
                    String bla = "null";
                    System.out.printf("%-" + (columnWidths[j] + 2) + "s", bla);
                }
            }
            System.out.println();
        }
    }


    // helper class to store coordinates
    static class Coordinates {
        int row;
        int col;

        Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
