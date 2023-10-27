package com.simulation.enviroment;

import java.awt.*;
import javax.swing.*;

import com.simulation.enums.Places;

 

public class MyPanel extends JPanel{
	private int rows = 23;
    private int cols = 38;
    private int squareSize = 30; // Adjust the size of each square as needed
    private int panelLength = squareSize * (cols + 2); // + 2 for padding
    private int panelHeight = squareSize * (rows + 2); // + 2 for padding
    private Square[] squares;


	MyPanel() {

		this.setPreferredSize(new Dimension(panelLength, panelHeight));
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		squares = new Square[rows * cols];

 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = squareSize + j * squareSize;
                int y = squareSize + i * squareSize;

                if (i >= 0 && i <= 1 && j >= 14 && j <= 18) {
                	// DJ BOOTH
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.DJ);
                } else if (j >= 30 && j <= 32 && i >= 0 && i <= 2) {
                	// BOUNCER
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.BOUNCER);
                } else if (j >= 0 && j <= 4 && i >= 8 && i <= 14) {
                	// BAR
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.BAR);
                } else if (j >= 26 && j <= 28 && i >= 9 && i <= 13) {
                	// FUSSBALL 
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.FUSSBALL);
                } else if (j >= 14 && j <= 18 && i >= 19 && i <= 21) {
                	// POOL
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.POOL);
                } else if (i >= 20 && j >= 27 && j <= 32) {
                	// TOILET
                	if (i == 20 && (j == 28 || j == 31)) {
                		// ENTRANCE 
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
                	} else if (i == 21 && (j == 28 || j == 31)) {
                		// USE 
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.TOILET);
                	} else {
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.TOILET);
                	}
                } else if (i == 1) {
                	// SEATS TOP
                	if (j == 3 || j == 5 || j == 7 || j == 9) {
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_BIG);
                	} else if (j == 23 || j == 25) {
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMALL);
                	} else {
                		squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
                	}
                } else if (i == 3 && j == 16) {
                	// SEAT DJ
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.DJ);
                } else if (i == 6 && (j == 1 || j == 3)) {
                	// SEATS BAR TOP
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.BAR);
                } else if (j == 6 && (i == 9 || i == 11 || i == 13)) {
                	// SEATS BAR RIGHT
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.BAR);
                } else if (i >= 7 && i <= 15 && j >= 12 && j <= 20) {
                	// DANCEFLOOR 
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.DANCEFLOOR);
                } else if (i == 11 && (j == 24 || j == 30)) {
                	// FUSSBALL SEATS
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.FUSSBALL);
                } else if (i == 20 && (j == 12 || j == 20)) {
                	// POOL SEATS
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.POOL);
                } else if (j == 1 && (i == 19 || i == 21)) {
                	// SMOKING 1 
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMOKING);
                } else if (i == 21 && (j == 3 || j == 5)) {
                	// SMOKING 2
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMOKING);
                } else {
                	squares[i * cols + j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
                }

            }
        }


        // DRAW RECTANGLES
        for (Square square : squares) {
        	g2D.setColor(Color.BLACK);
        	g2D.drawRect(square.x, square.y, square.width, square.height);
        	if (square.getColor() != Color.WHITE) {
        		g2D.setColor(square.getColor());
        		g2D.fillRect(square.x + 1, square.y + 1, square.width, square.height);
        	}
        }


        // DRAW BORDERS
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(3));
        // TOP BORDER 
        g2D.drawLine(squareSize, squareSize, panelLength - squareSize, squareSize);
        // RIGHT BORDER
        g2D.drawLine(panelLength - squareSize, squareSize, panelLength - squareSize, panelHeight - squareSize);
        // LEFT BORDER
        g2D.drawLine(squareSize, squareSize, squareSize, panelHeight - squareSize);
        // BOTTOM BORDER
        g2D.drawLine(squareSize, panelHeight - squareSize, panelLength - squareSize, panelHeight - squareSize);

        // ENTRANCE BORDER
        g2D.drawLine(squareSize * 34, squareSize, squareSize * 34, squareSize * 5);
        g2D.drawLine(squareSize * 34, squareSize * 8, squareSize * 34, panelHeight - squareSize);
    }
}