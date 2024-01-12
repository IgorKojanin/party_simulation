package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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
    boolean foundTopLeftCorner = false;
    Places[] lastGWIS = new Places[2];
    Direction secondToLastMove;
    Direction lastMove;
    boolean isTurning = false;
    Integer horizontalDimension = 0;
    Integer verticalDimension = 0;
    Integer movementIndex = 0;
    HashMap<Integer, Direction> wallSearch = new HashMap<>();
    Places[] useableSpots = new Places[]{
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

    private void findLeftWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundLeftWall = true;
            dir = Direction.TURN_LEFT_ON_SPOT;
            secondToLastMove = lastMove;
            lastMove = Direction.TURN_LEFT_ON_SPOT;
            int countLeft = Collections.frequency(wallSearch.values(), Direction.TURN_LEFT_ON_SPOT);
            int countRight = Collections.frequency(wallSearch.values(), Direction.TURN_RIGHT_ON_SPOT);
            horizontalDimension = wallSearch.size() - (countLeft + countRight) / 2;
            System.out.println(horizontalDimension);
            wallSearch.clear();
            System.out.println("found left wall");
        } else {
            movementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
            wallSearch.put(movementIndex, lastMove);
            movementIndex++;
        }

        lastGWIS = getWhatISee();
    }

    private void findBottomWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundBottomWall = true;
            dir = Direction.BACK;
            secondToLastMove = lastMove;
            lastMove = Direction.BACK;
            System.out.println("found bottom wall");
        } else {
            movementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
        }
        lastGWIS = getWhatISee();
    }

    private void findTopWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundTopWall = true;
            dir = Direction.TURN_LEFT_ON_SPOT;
            secondToLastMove = lastMove;
            lastMove = Direction.TURN_LEFT_ON_SPOT;
            System.out.println("found top wall");
        } else {
            movementLogic(Direction.TURN_RIGHT_ON_SPOT, Direction.TURN_LEFT_ON_SPOT);
        }
        lastGWIS = getWhatISee();
    }

    private void findTopLeftCorner() {
        if (getWhatISee()[1] == Places.WALL) {
            if (lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                dir = Direction.IDLE;
                secondToLastMove = lastMove;
                lastMove = Direction.IDLE;
                foundTopLeftCorner = true;
                System.out.println("found top left corner");
            } else {
                dir = Direction.TURN_RIGHT_ON_SPOT;
                secondToLastMove = lastMove;
                lastMove = Direction.TURN_RIGHT_ON_SPOT;
            }
        } else {
            movementLogic(Direction.TURN_LEFT_ON_SPOT, Direction.TURN_RIGHT_ON_SPOT);
        }

        lastGWIS = getWhatISee();
    }

    private void movementLogic(Direction firstTurn, Direction secondTurn) {
        if (lastGWIS[0] != lastGWIS[1]
                && getWhatISee()[0] != getWhatISee()[1]
                && lastGWIS != getWhatISee()
                && !Arrays.asList(useableSpots).contains(getWhatISee()[1])) {
            isTurning = true;
            dir = firstTurn;
            secondToLastMove = lastMove;
            lastMove = firstTurn;
        } else if (isTurning) {
            if (lastMove == Direction.TURN_LEFT_ON_SPOT || lastMove == Direction.TURN_RIGHT_ON_SPOT) {
                if (lastMove == secondTurn) {
                    isTurning = false;
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
        }
    }


    public Direction moveAvatar() {
        if (!foundLeftWall) {
            findLeftWall();
        } else if (!foundBottomWall) {
            findBottomWall();
        } else if (!foundTopWall) {
            findTopWall();
        } else if (!foundTopLeftCorner) {
            findTopLeftCorner();
        } else {
            dir = Direction.IDLE;
        }
        return dir;
    }
}
