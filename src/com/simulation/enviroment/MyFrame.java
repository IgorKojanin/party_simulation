package com.simulation.enviroment;

import javax.swing.*;

import com.simulation.avatar.Avatar;

import java.awt.Color;
import java.awt.Dimension;

public class MyFrame extends JFrame {
	MyPanel panel;

	public MyFrame(){
		panel = new MyPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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