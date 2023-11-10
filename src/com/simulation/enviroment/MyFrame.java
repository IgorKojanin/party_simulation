/**
 * This class represents the main frame of the party simulation environment.
 */
package com.simulation.enviroment;

import com.simulation.enums.Places;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
	GridPanel panel;
	private final int maxY = 23;
	private final int maxX = 38;
	private final int squareSize = 30; // Adjust the size of each square as needed
	private final int panelLength = squareSize * (maxX + 2); // + 2 for padding
	private final int panelHeight = squareSize * (maxY + 2); // + 2 for padding
	private Square[][] squares;

	/**
	 * This method creates the squares of the grid and assigns them to their respective places.
	 */
	private void createSquares() {
		squares = new Square[maxX][maxY];

		for (int ySquare = 0; ySquare < maxY; ySquare++) {
			for (int xSquare = 0; xSquare < maxX; xSquare++) {
				int xPixels = squareSize + xSquare * squareSize;
				int yPixels = squareSize + ySquare * squareSize;

				// Assign each square to its respective place
				if (ySquare <= 1 && xSquare >= 14 && xSquare <= 18) {
					// DJ BOOTH
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.DJ, " ");
				} else if (xSquare >= 30 && xSquare <= 32 && ySquare <= 2) {
					// BOUNCER
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.BOUNCER, " ");
				} else if (xSquare <= 4 && ySquare >= 8 && ySquare <= 14) {
					// BAR
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.BAR, " ");
				} else if (xSquare >= 26 && xSquare <= 28 && ySquare >= 9 && ySquare <= 13) {
					// FUSSBALL
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.FUSSBALL, " ");
				} else if (xSquare >= 14 && xSquare <= 18 && ySquare >= 19 && ySquare <= 21) {
					// POOL
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.POOL, " ");
				} else if (ySquare >= 20 && xSquare >= 27 && xSquare <= 32) {
					// TOILET
					if (ySquare == 20 && (xSquare == 28 || xSquare == 31)) {
						// ENTRANCE
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.WHITE,true, Places.PATH, " ");
					} else if (ySquare == 21 && (xSquare == 28 || xSquare == 31)) {
						// USE
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.TOILET, " ");
					} else {
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.PINK,false, Places.TOILET, " ");
					}
				} else if (ySquare == 1) {
					// SEATS TOP
					if (xSquare == 3 || xSquare == 5 || xSquare == 7 || xSquare == 9) {
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.LOUNGE_BIG, " ");
					} else if (xSquare == 23 || xSquare == 25) {
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.LOUNGE_SMALL, " ");
					} else {
						squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.WHITE,true, Places.PATH, " ");
					}
				} else if (ySquare == 3 && xSquare == 16) {
					// SEAT DJ
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.DJ, " ");
				} else if (ySquare == 6 && (xSquare == 1 || xSquare == 3)) {
					// SEATS BAR TOP
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.BAR, " ");
				} else if (xSquare == 6 && (ySquare == 9 || ySquare == 11 || ySquare == 13)) {
					// SEATS BAR RIGHT
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.BAR, " ");
				} else if (ySquare >= 7 && ySquare <= 15 && xSquare >= 12 && xSquare <= 20) {
					// DANCEFLOOR
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.DANCEFLOOR, " ");
				} else if (ySquare == 11 && (xSquare == 24 || xSquare == 30)) {
					// FUSSBALL SEATS
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.FUSSBALL, " ");
				} else if (ySquare == 20 && (xSquare == 12 || xSquare == 20)) {
					// POOL SEATS
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true,Places.POOL, " ");
				} else if (xSquare == 1 && (ySquare == 19 || ySquare == 21)) {
					// SMOKING 1
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true,Places.LOUNGE_SMOKING, " ");
				} else if (ySquare == 21 && (xSquare == 3 || xSquare == 5)) {
					// SMOKING 2
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.GREEN,true, Places.LOUNGE_SMOKING, " ");
				} else {
					squares[xSquare][ySquare] = new Square(xPixels, yPixels, squareSize, squareSize, Color.WHITE,true, Places.PATH, " ");
				}
			}
		}
	}

	/**
	 * This method waits for a specified amount of time.
	 * @param ms The amount of time to wait in milliseconds.
	 */
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();

		}
	}

	public boolean isWall(int fromX, int fromY, int toX, int toY) {
		if ((fromX <= 32 && toX >= 33) || (fromX >= 33 && toX <= 32)) {
			if (fromY < 7 && fromY > 3 && toY < 7 && toY > 3) {
				return false;
			}
			return true;
		}
		return false;
	}

	public void moveTo(int fromX, int fromY, int toX, int toY, Color color) {
		wait(30);
		if (toX < maxX && toY < maxY && toX >= 0 && toY >= 0) {
			squares[fromX][fromY].setColor(squares[fromX][fromY].getBaseColor()); // Clear the from-square
			squares[fromX][fromY].setIsUsable(true);
			repaint();
			squares[toX][toY].setColor(color); // Set the to-square to avatar color
			squares[toX][toY].setIsUsable(false);
			repaint();
		}
	}

	/**
	 * This method checks if a square is usable.
	 * @param x The x-coordinate of the square.
	 * @param y The y-coordinate of the square.
	 * @return True if the square is usable, false otherwise.
	 */
	public boolean isUsable(int x, int y) {
		if (y < maxY && x < maxX && y >= 0 && x >= 0) {
			return squares[x][y].getIsUsable();
		} else
			return false;
	}

	/**
	 * This is the constructor of the MyFrame class.
	 * It creates the grid panel and sets up the frame.
	 */
	public MyFrame(){
		createSquares();
		panel = new GridPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		createText();
	}

	// Set the letters to be displayed in the individual grids here
	public void createText() {
		squares[1][0].setDisplayLetter("B");
		squares[1][1].setDisplayLetter("A");
		squares[1][2].setDisplayLetter("R");
	}
	/**
	 * This class represents the grid panel of the frame.
	 */
	class GridPanel extends JPanel {
		GridPanel() {
			this.setPreferredSize(new Dimension(panelLength, panelHeight));
		}

		/**
		 * This method paints the grid and its borders.
		 * @param g The graphics object.
		 */
		public void paint(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < maxY; i++) {
				for (int j = 0; j < maxX; j++) {
					Square square = squares[j][i];
					g2d.setColor(Color.BLACK);
					g2d.drawRect(square.x, square.y, square.width, square.height);
					// these numbers ensure that the letters are displayed in the middle of the square
					g2d.drawString(square.getDisplayLetter(), square.x + 12, square.y + 19);
					if (square.getColor() != Color.WHITE) {
						g2d.setColor(square.getColor());
						g2d.fillRect(square.x + 1, square.y + 1, square.width, square.height);
					}
				}
			}

			// DRAW BORDERS
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(3));
			// TOP BORDER
			g2d.drawLine(squareSize, squareSize, panelLength - squareSize, squareSize);
			// RIGHT BORDER
			g2d.drawLine(panelLength - squareSize, squareSize, panelLength - squareSize, panelHeight - squareSize);
			// LEFT BORDER
			g2d.drawLine(squareSize, squareSize, squareSize, panelHeight - squareSize);
			// BOTTOM BORDER
			g2d.drawLine(squareSize, panelHeight - squareSize, panelLength - squareSize, panelHeight - squareSize);

			// ENTRANCE BORDER
			g2d.drawLine(squareSize * 34, squareSize, squareSize * 34, squareSize * 5);
			g2d.drawLine(squareSize * 34, squareSize * 8, squareSize * 34, panelHeight - squareSize);
		}
	}
}