package com.simulation.enviroment;

import com.simulation.enums.Places;

import javax.swing.*;
import java.awt.*;

import com.simulation.avatar.Avatar;

import java.awt.Color;
import java.awt.Dimension;

public class MyFrame extends JFrame {
	GridPanel panel;
	private final int rows = 23;
	private final int cols = 38;
	private int squareSize = 30; // Adjust the size of each square as needed
	private final int panelLength = squareSize * (cols + 2); // + 2 for padding
	private final int panelHeight = squareSize * (rows + 2); // + 2 for padding
	private Square[][] squares;


	private void createSquares() {
		squares = new Square[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int x = squareSize + j * squareSize;
				int y = squareSize + i * squareSize;

				// TODO: DELETE THE FIRST IF, IT'S FOR TESTING
				if(i == 7 && j == 34) {
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.BLUE, Places.DJ);
				} else if (i <= 1 && j >= 14 && j <= 18) {
					// DJ BOOTH
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.DJ);
				} else if (j >= 30 && j <= 32 && i <= 2) {
					// BOUNCER
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.BOUNCER);
				} else if (j <= 4 && i >= 8 && i <= 14) {
					// BAR
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.BAR);
				} else if (j >= 26 && j <= 28 && i >= 9 && i <= 13) {
					// FUSSBALL
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.FUSSBALL);
				} else if (j >= 14 && j <= 18 && i >= 19 && i <= 21) {
					// POOL
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.POOL);
				} else if (i >= 20 && j >= 27 && j <= 32) {
					// TOILET
					if (i == 20 && (j == 28 || j == 31)) {
						// ENTRANCE
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
					} else if (i == 21 && (j == 28 || j == 31)) {
						// USE
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.TOILET);
					} else {
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.PINK, Places.TOILET);
					}
				} else if (i == 1) {
					// SEATS TOP
					if (j == 3 || j == 5 || j == 7 || j == 9) {
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_BIG);
					} else if (j == 23 || j == 25) {
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMALL);
					} else {
						squares[i][j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
					}
				} else if (i == 3 && j == 16) {
					// SEAT DJ
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.DJ);
				} else if (i == 6 && (j == 1 || j == 3)) {
					// SEATS BAR TOP
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.BAR);
				} else if (j == 6 && (i == 9 || i == 11 || i == 13)) {
					// SEATS BAR RIGHT
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.BAR);
				} else if (i >= 7 && i <= 15 && j >= 12 && j <= 20) {
					// DANCEFLOOR
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.DANCEFLOOR);
				} else if (i == 11 && (j == 24 || j == 30)) {
					// FUSSBALL SEATS
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.FUSSBALL);
				} else if (i == 20 && (j == 12 || j == 20)) {
					// POOL SEATS
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.POOL);
				} else if (j == 1 && (i == 19 || i == 21)) {
					// SMOKING 1
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMOKING);
				} else if (i == 21 && (j == 3 || j == 5)) {
					// SMOKING 2
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.GREEN, Places.LOUNGE_SMOKING);
				} else {
					squares[i][j] = new Square(x, y, squareSize, squareSize, Color.WHITE, Places.PATH);
				}
			}
		}
	}

	public void moveTo(int fromRow, int fromCol, int toRow, int toCol) {
		if (squares[fromRow][fromCol].getColor() == Color.BLUE) {
			squares[fromRow][fromCol].setColor(Color.WHITE); // Clear the from-square
			repaint();
			squares[toRow][toCol].setColor(Color.BLUE); // Set the to-square to blue
			repaint();
		}
	}

	public boolean isUsable(int x, int y){
		return squares[x][y].getIsUsable();
	}

	public MyFrame(){
		createSquares();
		panel = new GridPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	class GridPanel extends JPanel {
		GridPanel() {
			this.setPreferredSize(new Dimension(panelLength, panelHeight));
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					Square square = squares[i][j];
					g2d.setColor(Color.BLACK);
					g2d.drawRect(square.x, square.y, square.width, square.height);
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
	


	public class AvatarLabel extends JLabel {
	    
	    private Avatar avatar;

	    public AvatarLabel(Avatar avatar) {
	        this.avatar = avatar;
	        
	        // Initialize the label with avatar properties
	        this.setText("Avatar " + avatar.getId()); 
	        this.setOpaque(true);
	        this.setBackground(convertColor(avatar.getColor()));
	        this.setPreferredSize(new Dimension(50, 50)); /
	        this.setToolTipText("Avatar Name: " + avatar.getShape().toString());
	        
	        
	        if (avatar.getBorderWidth() > 0) {
	            setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, avatar.getBorderWidth()));
	        }
	    }
	    

	    private Color convertColor(com.simulation.enums.Color colorEnum) {
	        switch (colorEnum) {
	            case RED:
	                return java.awt.Color.RED;
	            case GREEN:
	                return java.awt.Color.GREEN;
	            case BLUE:
	                return java.awt.Color.BLUE;
	           
	            default:
	                return java.awt.Color.BLACK; // Default color
	        }
	    }
	    
	    
	}

}