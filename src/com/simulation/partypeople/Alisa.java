package com.simulation.partypeople;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

import java.awt.*;
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

    // ************** Constructor **************
    public Alisa(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
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

    public Direction moveAvatarWip() {
        Direction dir = null;

        return dir;
    }

    public Direction moveAvatar() {
        // TODO
        // create an algorithm that determines the next step of your movement pattern
        // based on a set of priorities.
        Random rand = new Random();
        int number = rand.nextInt(4);
        // direction is set externally --> check with the simulation environment
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

        return dir;
    }
}
