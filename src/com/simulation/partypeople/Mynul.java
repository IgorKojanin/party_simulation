package com.simulation.partypeople;

import com.simulation.avatar.Avatar;

import java.awt.Color;
import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.simulation.enums.*;

public class Mynul extends Avatar {
    

///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Avatar class for Mynul
//
///////////////////////////////////////////////////////////////////////////////




	File file = new File("misc/Shrek_Script_Mynul.txt");
	BufferedReader br = null;

	private String shrek_movie;

	// ToDo individually:
	// - Store surroudings locally
	// - Develop an algorithm to determine your next destination
	// - Develop movement pattern
	// - Develop dancing movement pattern
	// - Develop fighting algorithm with certain fighting skills
	// - Develop prefered drinks list
	// - Develop default phrases to interact with other users of Club Penguin
	// - Develop spiels
	// - Develop smoke area behaviour
	// - Develop skibidi toilet

	// ************** Constructor **************
	public Mynul(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		
	
		

	}

	

	public Direction moveAvatar() {
		int r = ThreadLocalRandom.current().nextInt(0, 100); 
		switch ((00 <= r && r < 40 ) ? 0 :
				(40 <= r && r < 65) ? 1 :
				(65 <= r && r < 90) ? 2 : 3){
		case 0: return Direction.FORWARD;
		case 1: return Direction.RIGHT;
		case 2: return Direction.LEFT;
		case 3: return Direction.BACK;
		default: return Direction.IDLE;
		}
	}

	
}
