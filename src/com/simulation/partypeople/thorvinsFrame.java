package com.simulation.partypeople;

import javax.swing.*;
import java.awt.*;
import com.simulation.enums.Places;

public class thorvinsFrame extends JFrame {

    private static final int CELL_SIZE = 10;
    private static final int ROWS = 85;
    private static final int COLS = 65;

    private Places[][] placesArray; // Das Array vom Typ Places

    public thorvinsFrame(Places[][] placesArray) {
        this.placesArray = placesArray; // Das übergebene Array initialisieren

        setTitle("Grid JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(CELL_SIZE * COLS, CELL_SIZE * ROWS);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };

        getContentPane().add(panel);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (placesArray[i][j] == null) {
                    g.setColor(Color.GRAY); // Graue Hintergrundfarbe für Null-Elemente
                } else {
                    // Zuordnung von Places zu Farben
                    switch (placesArray[i][j]) {
                        case WALL:
                            g.setColor(Color.BLACK);
                            break;
                        case PATH:
                            g.setColor(Color.WHITE);
                            break;
                        case DANCEFLOOR:
                            g.setColor(Color.GREEN);
                            break;
                        case BAR_CHAIR:
                            g.setColor(Color.MAGENTA);
                            break;
                        case BAR:
                            g.setColor(Color.RED);
                            break;
                        case DJ:
                            g.setColor(Color.RED);
                            break;
                        case BOUNCER:
                            g.setColor(Color.RED);
                            break;
                        case POOL:
                            g.setColor(Color.RED);
                            break;
                        case FUSSBALL:
                            g.setColor(Color.RED);
                            break;
                        case TOILET:
                            g.setColor(Color.RED);
                            break;
                        case LOUNGE_BIG:
                            g.setColor(Color.PINK);
                            break;
                        case LOUNGE_SMALL:
                            g.setColor(Color.PINK);
                            break;
                        case LOUNGE_SMOKING:
                            g.setColor(Color.PINK);
                            break;
                        case POOL_CHAIR:
                            g.setColor(Color.PINK);
                            break;
                        case TOILET_CHAIR:
                            g.setColor(Color.PINK);
                            break;

                        default:
                            g.setColor(Color.GRAY);
                            break;
                    }
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public void updateMap(Places[][] newPlacesArray) {
        this.placesArray = newPlacesArray;
        repaint();
    }

}