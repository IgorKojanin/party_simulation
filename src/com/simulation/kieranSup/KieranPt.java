/*
Party Simulation: KieranPT
Date: 25.01.2024
Class: Kieran.java extending the Avatar.java class
Description: Represent a point on a graph or map, with x and y coordinates, a visited status, and a place
Status: DONE
*/

package com.simulation.kieranSup;

import com.simulation.enums.Direction;
import com.simulation.enums.Places;

public class KieranPt {
    // X coordinate of the point
    private int x;

    // Y coordinate of the point
    private int y;

    // Flag indicating whether this point has been visited
    private boolean visited;

    // The place associated with this point, as defined in the Places enum
    private Places place;

    // Constructor to initialize a point with x and y coordinates
    public KieranPt(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false; // All new points are initially set to not visited
    }

    // Getter for the x coordinate
    public int getX() {
        return x;
    }

    // Getter for the y coordinate
    public int getY() {
        return y;
    }

    // Getter to check if the point has been visited
    public boolean isVisited() {
        return visited;
    }

    // Getter for the place
    public Places getPlace() {
        return place;
    }

    // Setter to assign a place
    public void setPlace(Places place) {
        this.place = place;
    }

    // Setter to update the visited status
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Method to get a new point adjacent
    public KieranPt getAdjacentPoint(Direction direction) {
        int updatePositionX = this.getX(); // Current x
        int updatePositionY = this.getY(); // Current y

        // Adjusting newX and newY based on the specified direction
        switch (direction) {
            case FORWARD:
                updatePositionX++;
                break;
            case BACK:
                updatePositionX--;
                break;
            case RIGHT:
                updatePositionY++;
                break;
            case LEFT:
                updatePositionY--;
                break;
            default:
                break;
        }

        // Returning a new point with the adjusted coordinates
        return new KieranPt(updatePositionX, updatePositionY);
    }
}
