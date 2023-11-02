///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         02/11/2023
//
// Class: MainEnv.java
// Description: Main class for testing the environment keep disabled outside of
//              environment development
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.enviroment;

public class MainEnv {
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

        }
    }

    public static void main(String[] args) {
        // DRAWING THE FLOORPLAN
        MyFrame frame = new MyFrame();
        frame.setVisible(true);

        // EXAMPLE MOVING AVATAR BY CHANGING SQUARE COLOR
        wait(1000);
        frame.moveObject(7,34, 6, 34);
        wait(1000);
        frame.moveObject(6,34, 5, 34);
        wait(1000);
        frame.moveObject(5,34, 5, 33);
        wait(1000);
        frame.moveObject(5,33, 5, 32);
        wait(1000);
        frame.moveObject(5,32, 5, 31);
        wait(1000);
        frame.moveObject(5,31, 5, 30);
    }
}
