/*
Party Simulation: KieranMap
Date: 25.01.2024
Class: Kieran.java extending the Avatar.java class
Description: Gaphical representation of a graph using Java's Swing framework
Status: DONE
*/

package com.simulation.partypeople;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import com.simulation.enums.Places;
import com.simulation.kieranSup.KieranGP;
import com.simulation.kieranSup.KieranPt;

class KieranMap extends JFrame {
    // The graph structure containing the points to be displayed.
    private KieranGP graph;

    // Constants for the cell size and dimensions of the grid
    private static final int CELL_SIZE = 10; // Adjust size as needed
    private static final int ROWS = 50;
    private static final int COLS = 50;

    // Constructor to initialize the map with a given graph
    public KieranMap(KieranGP graph) {
        this.graph = graph;
        setTitle("Party Simulation: KieranMap"); // Set the title
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE)); // Set the size of the window
        pack(); // Pack the frame to the preferred size
        setLocationRelativeTo(null); // Center the frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
    }

    // Overriding the paint method to draw the map
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Call the super class's paint method
        drawGrid(g); // Draw the grid representing the graph
    }

    // Method to draw the grid based on the graph points
    private void drawGrid(Graphics g) {
        // Get the min and max extents of the graph to calculate the grid dimensions
        int minX = graph.getMinX();
        int maxX = graph.getMaxX();
        int minY = graph.getMinY();
        int maxY = graph.getMaxY();

        // Calculate the width and height of the graph
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;

        // Calculate the offsets to center the graph in the panel
        int offsetX = (COLS - graphWidth) / 2;
        int offsetY = (ROWS - graphHeight) / 2;

        // Loop through each point in the graph and draw it
        for (KieranPt point : graph.getPoints()) {
            g.setColor(placeColour(point.getPlace())); // Set the color based on the place type

            // Translate graph coordinates to panel coordinates
            int translatedY = (point.getX() - minX + offsetX) * CELL_SIZE;
            int translatedX = (point.getY() - minY + offsetY) * CELL_SIZE;

            // Invert y to match the Swing coordinate system
            int screenY = (ROWS * CELL_SIZE) - translatedY - CELL_SIZE;

            // Draw the cell for the point
            g.fillRect(translatedX, screenY, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK); // Set color for the cell border
            g.drawRect(translatedX, screenY, CELL_SIZE, CELL_SIZE); // Draw the border
        }
    }

    // Method to get the color associated with a specific place
    private Color placeColour(Places place) {
        // Switch statement to determine the color based on the place type
        switch (place) {
            case BAR:
                return Color.YELLOW;
            case DANCEFLOOR:
                return Color.BLUE;
            case BAR_CHAIR:
                return Color.GREEN;
            case TOILET_CHAIR:
                return Color.GREEN;
            case FUSSBALL_CHAIR:
                return Color.GREEN;
            case LOUNGE_BIG:
                return Color.GREEN;
            case LOUNGE_SMALL:
                return Color.GREEN;
            case LOUNGE_SMOKING:
                return Color.GREEN;
            default:
                return Color.GRAY; // Default colour
        }
    }

    // Method to update the panel with a new graph
    public void updatePanel(KieranGP graph) {
        this.graph = graph; // Update the graph
        repaint(); // Repaint the panel
    }
}
