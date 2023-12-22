package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Alisa extends Avatar {
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
    Map<Integer, Movement> movementMap = new HashMap<>();
    Map<Integer, Direction> matrixMovement = new HashMap<>();
    HashMap<Integer, Direction> wallSearch = new HashMap<>();
    Integer matrixSearchIndex = 0;
    Integer movementIndex = 0;
    Direction nextDir = Direction.FORWARD;
    Direction lastDir = Direction.FORWARD;
    Direction secondLastDir = Direction.FORWARD;
    Integer dancingCounter = 0;
    boolean foundLeftWall = false;
    boolean foundTopWall = false;
    boolean foundBottomWall = false;
    Integer horizontalDimension = 0;
    Integer verticalDimension = 0;
    Places[] lastGWIS = new Places[2];

    // ************** Constructor **************
    public Alisa(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
        // TODO
    }

    private static class Movement {
        private Direction direction;
        private Places place;

        public Movement(Direction direction, Places place) {
            this.direction = direction;
            this.place = place;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public Places getPlace() {
            return place;
        }

        public void setPlace(Places place) {
            this.place = place;
        }
    }

    // ************** Methods **************
    public Direction dancingAlgo() {
        Direction direction = nextDir;

        if (dancingCounter < 3) {
            direction = Direction.FORWARD;
        } else {
            if (getWhatISee()[0] != Places.DANCEFLOOR) {
                direction = pickOppositeDirection(direction);
            } else {
                direction = Direction.LEFT;
            }
        }

//        System.out.println(this.getName() + " is dancing");

        return direction;
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

    private Direction findLeftWall() {
        if (getWhatISee()[1] == Places.WALL) {
            foundLeftWall = true;
//            nextDir = Direction.IDLE;
//            System.out.println("found left wall");
            int countLeft = Collections.frequency(wallSearch.values(), Direction.LEFT);
            int countRight = Collections.frequency(wallSearch.values(), Direction.RIGHT);
            int countIdle = Collections.frequency(wallSearch.values(), Direction.IDLE);
            horizontalDimension = wallSearch.size() - (countLeft + countRight) / 2 - countIdle;
//            System.out.println(horizontalDimension);
            nextDir = Direction.LEFT;
            wallSearch.clear();
        } else {
            if (lastGWIS[0] == getWhatISee()[0] && lastGWIS[1] == getWhatISee()[1] && getWhatISee()[1] != Places.PATH) {
                // means we're stuck
                nextDir = Direction.LEFT;
            } else {
                if (lastDir == Direction.FORWARD
                        || (lastDir == Direction.LEFT && secondLastDir == Direction.RIGHT)
                        || (lastDir == Direction.RIGHT && secondLastDir == Direction.LEFT))
                {
                    nextDir = Direction.FORWARD;
                } else {
                    if (lastDir == Direction.RIGHT) {
                        nextDir = Direction.LEFT;
                    } else if (lastDir == Direction.LEFT) {
                        nextDir = Direction.RIGHT;
                    }
                }
            }
        }
        wallSearch.put(movementIndex, nextDir);
        movementIndex++;
        return nextDir;
    }

    public Direction moveAvatar() {
        secondLastDir = lastDir;
        lastDir = nextDir;
        if(!foundLeftWall) {
            nextDir = findLeftWall();
        } else if (!foundBottomWall) {
            if (getWhatISee()[1] == Places.WALL) {
                foundBottomWall = true;
                nextDir = Direction.IDLE;
//                System.out.println("found left wall");
                int countLeft = Collections.frequency(wallSearch.values(), Direction.LEFT);
                int countRight = Collections.frequency(wallSearch.values(), Direction.RIGHT);
                int countIdle = Collections.frequency(wallSearch.values(), Direction.IDLE);
                horizontalDimension = wallSearch.size() - (countLeft + countRight) / 2 - countIdle;
//                System.out.println(horizontalDimension);
                nextDir = Direction.LEFT;
                wallSearch.clear();
            }
        }
        lastGWIS[0] = getWhatISee()[0];
        lastGWIS[1] = getWhatISee()[1];
//        System.out.println("second last dir " + secondLastDir);
//        System.out.println("last dir " + lastDir);
//        System.out.println("next dir " + nextDir);
//        System.out.println(getWhatISee()[0] + " " + getWhatISee()[1]);
        return nextDir;
    }

    public Direction moveAvatarWIP() {
        if (nextDir == Direction.LEFT || nextDir == Direction.RIGHT) {
            nextDir = Direction.FORWARD;
        }




        if (getWhatISee()[1] == Places.OUTSIDE || getWhatISee()[1] == Places.WALL) {
            nextDir = pickOppositeDirection(nextDir);
            movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
        } else {
            if (movementMap.isEmpty()) {
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
            } else if (movementIndex % 5 == 0 && movementMap.get(movementIndex-1).getPlace() != Places.DANCEFLOOR) {
                nextDir = pickNewRandomDirection(nextDir);
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
            } else if (getWhatISee()[1] == Places.DANCEFLOOR && dancingCounter < 3) {
                nextDir = Direction.FORWARD;
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
                dancingCounter++;
//                System.out.println("Dancing " + dancingCounter);
            } else if (dancingCounter < 20 && dancingCounter >= 3) {
                nextDir = dancingAlgo();
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
                dancingCounter++;
//                System.out.println("Dancing " + dancingCounter);
            } else if (dancingCounter >= 20) {
                nextDir = Direction.FORWARD;
                if (getWhatISee()[0] != Places.DANCEFLOOR) {
                    dancingCounter = 0;
                }
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
            } else {
                movementMap.put(movementIndex, new Movement(nextDir, getWhatISee()[0]));
            }
        }

//        System.out.println(movementMap.get(movementIndex).getDirection() + " " + movementMap.get(movementIndex).getPlace());
        movementIndex++;
        return nextDir;
    }

    private Direction pickOppositeDirection(Direction direction) {
        switch (direction) {
            case BACK -> direction = Direction.FORWARD;
            case FORWARD -> direction = Direction.BACK;
            case LEFT -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.LEFT;
        }
        return direction;
    }

    private Direction pickNewRandomDirection(Direction oldDirection) {
        Random rand = new Random();
        int number = rand.nextInt(4);
        Direction dir = null;
        switch (number) {
            case 0:
                dir = Direction.FORWARD;
                break;
            case 1:
                dir = Direction.RIGHT;
                break;
            case 2:
                dir = Direction.BACK;
                break;
            case 3:
                dir = Direction.LEFT;
                break;
            default:
                break;
        }
        if (oldDirection == dir) {
            pickNewRandomDirection(oldDirection);
        }
        return dir;
    }
}
