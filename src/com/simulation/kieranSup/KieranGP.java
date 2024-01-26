/*
Party Simulation: KieranGP
Date: 25.01.2024
Class: Kieran.java extending the Avatar.java class
Description: Represent a graph-like structure consisting of KieranPt points.
Status: DONE
*/

package com.simulation.kieranSup;

import java.util.ArrayList;
import java.util.List;
import com.simulation.enums.Places;

public class KieranGP {
    // List of points in the graph.
    private List<KieranPt> graphPoints;

    // Constructor initializing the list of points.
    public KieranGP() {
        graphPoints = new ArrayList<>();
    }

    // Method to add a point to the graph.
    public void addGraphPoint(KieranPt point) {
        graphPoints.add(point);
    }

    public List<KieranPt> getPoints() {
        return graphPoints;
    }

    public boolean containsPoint(KieranPt pointToCheck) {
        for (KieranPt point : graphPoints) {
            if (point.getX() == pointToCheck.getX() && point.getY() == pointToCheck.getY()) {
                return true; // The point exists in the graph
            }
        }
        return false; // The point does not exist in the graph
    }

    // Retrieves a point from the graph that matches the specified point's...
    // ...coordinates.
    public KieranPt getPoint(KieranPt point) {
        for (KieranPt pointExists : graphPoints) {
            // Check if the current point in the graph has the same X and Y coordinates
            // as...
            // ...the specified point
            if (pointExists.getX() == point.getX() && pointExists.getY() == point.getY()) {
                return pointExists; // Return the matching point
            }
        }
        return null; // Return null if no matching point is found
    }

    // Checks if a place has been discovered in the graph.
    public boolean isPlaceDiscovered(Places place) {
        for (KieranPt point : graphPoints) {
            // Check if the point's place matches the specified place and if it has been...
            // ...visited
            if (point.getPlace() == place && point.isVisited()) {
                return true; // Return true if a visited point with the specified place is found
            }
        }
        return false; // Return false if no such point is found
    }

    // Gets the minimum X value among all the points in the graph.
    public int getMinX() {
        return graphPoints.stream()
                .min((p1, p2) -> Integer.compare(p1.getX(), p2.getX())) // Compare points based on X coordinate
                .get().getX(); // Extract the X value of the point with the minimum X coordinate
    }

    // Gets the maximum X value among all the points in the graph.
    public int getMaxX() {
        return graphPoints.stream()
                .max((p1, p2) -> Integer.compare(p1.getX(), p2.getX())) // Compare points based on X coordinate
                .get().getX(); // Extract the X value of the point with the maximum X coordinate
    }

    // Gets the minimum Y value among all the points in the graph.
    public int getMinY() {
        return graphPoints.stream()
                .min((p1, p2) -> Integer.compare(p1.getY(), p2.getY())) // Compare points based on Y coordinate
                .get().getY(); // Extract the Y value of the point with the minimum Y coordinate
    }

    // Gets the maximum Y value among all the points in the graph.
    public int getMaxY() {
        return graphPoints.stream()
                .max((p1, p2) -> Integer.compare(p1.getY(), p2.getY())) // Compare points based on Y coordinate
                .get().getY(); // Extract the Y value of the point with the maximum Y coordinate
    }

    // Resets the visited status of all points in the graph.
    public void resetVisited() {
        for (KieranPt point : graphPoints) {
            point.setVisited(false); // Set the visited status of each point to false
        }
    }
}
