package com.simulation.partypeople;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import com.simulation.enums.Places;
import com.simulation.extra.Graph;
import com.simulation.extra.Point;

class MapFrame extends JFrame {
    private Graph graph;
    private static final int CELL_SIZE = 10; // Adjust size as needed
    private static final int ROWS = 50;
    private static final int COLS = 50;

    public MapFrame(Graph graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        // Assuming you have methods to get the min and max extents of your graph
        int minY = graph.getMinX();
        int maxY = graph.getMaxX();
        int minX = graph.getMinY();
        int maxX = graph.getMaxY();
    
        // Find the width and height of your graph
        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;
    
        // Calculate the offsets needed to center the graph in the panel
        int offsetX = (COLS - graphWidth) / 2;
        int offsetY = (ROWS - graphHeight) / 2;
    
        for (Point point : graph.getPoints()) {
            g.setColor(getColorForPlace(point.getPlace()));
    
            // Translate graph coordinates to panel coordinates
            int translatedY = (point.getX() - minX + offsetX) * CELL_SIZE;
            int translatedX = (point.getY() - minY + offsetY) * CELL_SIZE;
    
            // Invert y to match the Swing coordinate system
            int screenY = (ROWS * CELL_SIZE) - translatedY - CELL_SIZE;
    
            g.fillRect(translatedX, screenY, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(translatedX, screenY, CELL_SIZE, CELL_SIZE);
        }
    }

    private Color getColorForPlace(Places place) {
        switch (place) {
            case BAR: return Color.YELLOW;
            case DANCEFLOOR: return Color.GREEN;
            case LOUNGE_BIG: return Color.PINK;
            case LOUNGE_SMALL: return Color.PINK;
            case LOUNGE_SMOKING: return Color.PINK;
            case BAR_CHAIR: return Color.MAGENTA;
            case POOL: return Color.BLUE;
            case TOILET: return Color.GRAY;
            case PATH: return Color.WHITE;
            case WALL: return Color.BLACK;
            // ... other cases for each Places type ...
            default: return Color.GRAY;
        }
    }

    public void updatePanel(Graph graph) {
        this.graph = graph;
        repaint();
    }

}