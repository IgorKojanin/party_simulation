package com.simulation.extra;

import com.simulation.enums.Direction;
import com.simulation.enums.Places;

public class Point {
    private int x;
    private int y;
    private boolean visited;
    private Places place;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }

    public Places getPlace(){
        return place;
    }

    public void setPlace(Places place){
        this.place = place;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Point getAdjacentPoint(Direction direction) {
    int newX = this.getX();
    int newY = this.getY();

    switch (direction) {
        case FORWARD:
            newX++;
            break;
        case BACK:
            newX--;
            break;
        case RIGHT:
            newY++;
            break;
        case LEFT:
            newY--;
            break;
        default:
            break;
    }

        return new Point(newX, newY);

}

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", visited=" + visited + ")";
    }
}
