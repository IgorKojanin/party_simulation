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
import java.awt.Color;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import com.simulation.enums.*;
import com.simulation.enums.Direction;

public class Mynul extends Avatar {
    
    private boolean isOnDanceFloor = false;
    private int dancingCounter = 0;
    private Direction moonwalkingDirection = Direction.LEFT;
    private int moonwalkingCounter = 0;
    private HashMap<String, Places> surroundingsMap = new HashMap<>();

    public Mynul(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
        super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
    }

    @Override
    public Direction moveAvatar() {
        updateSurroundingsMap();
        
        if (isOnDanceFloor) {
            return dancingAlgo(); // Continue dancing
        }

        if (surroundingsMap.getOrDefault("front", Places.PATH) == Places.DANCEFLOOR) {
            isOnDanceFloor = true; // Mark that Mynul is now on the dance floor
            moonwalkingCounter = 0; // Reset moonwalk counter
            return dancingAlgo(); // Start dancing
        }

        return navigateToDanceFloor(); // Move towards the dance floor
    }

    private void updateSurroundingsMap() {
        Places[] surroundings = getWhatISee();
        surroundingsMap.put("front", surroundings[0]);
        surroundingsMap.put("next", surroundings[1]);
    }

    private Direction navigateToDanceFloor() {
        if (!surroundingsMap.get("front").equals(Places.PERSON)) {
            return Direction.FORWARD;
        } else {
            return randomMovement();
        }
    }

    private Direction randomMovement() {
        int r = ThreadLocalRandom.current().nextInt(0, 100);
        if (r < 20) {
            return Direction.FORWARD;
        } else if (r < 40) {
            return Direction.RIGHT;
        } else if (r < 60) {
            return Direction.LEFT;
        } else if (r < 80) {
            return Direction.TURN_LEFT_ON_SPOT;
        } else if (r < 95) {
            return Direction.TURN_RIGHT_ON_SPOT;
        } else {
            return Direction.IDLE;
        }
    }

    public Direction dancingAlgo() {
        Direction dancingDir;

        if (moonwalkingCounter < 5) {
            dancingDir = moonwalkingDirection;
            moonwalkingCounter++;
        } else {
            moonwalkingCounter = 0;
            moonwalkingDirection = (moonwalkingDirection == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            dancingDir = moonwalkingDirection;
        }

        dancingCounter++;
        System.out.println(this.getName() + " is dancing");
        return dancingDir;
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
}