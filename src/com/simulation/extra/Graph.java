package com.simulation.extra;

import java.util.ArrayList;
import java.util.List;

import com.simulation.enums.Places;

public class Graph {
    private List<Point> points;

    public Graph() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean containsPoint(Point pointToCheck) {
        for (Point point : points) {
            if (point.getX() == pointToCheck.getX() && point.getY() == pointToCheck.getY()) {
                return true; // The point exists in the graph
            }
        }
        return false; // The point does not exist in the graph
    }

    public Point getPoint(Point point) {
        for (Point existingPoint : points) {
            if (existingPoint.getX() == point.getX() && existingPoint.getY() == point.getY()) {
                return existingPoint;
            }
        }
        return null;
    }

    public boolean isPlaceDiscovered(Places place) {
    for (Point point : points) {
        if (point.getPlace() == place && point.isVisited()) {
            return true;
        }
    }
    return false;
}

    public int getMinX() {
        return points.stream().min((p1, p2) -> Integer.compare(p1.getX(), p2.getX())).get().getX();
    }

    public int getMaxX() {
        return points.stream().max((p1, p2) -> Integer.compare(p1.getX(), p2.getX())).get().getX();
    }

    public int getMinY() {
        return points.stream().min((p1, p2) -> Integer.compare(p1.getY(), p2.getY())).get().getY();
    }

    public int getMaxY() {
        return points.stream().max((p1, p2) -> Integer.compare(p1.getY(), p2.getY())).get().getY();
    }

    public void resetVisited() {
        for (Point point : points) {
            point.setVisited(false);
        }
    }

    @Override
    public String toString() {
        return "Graph{" +
               "points=" + points +
               '}';
    }
}
