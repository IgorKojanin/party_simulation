///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;
import com.simulation.avatar.Avatar;
import com.simulation.avatar.PartyGoer;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

import java.awt.*;

public class JoeMama extends Avatar implements PartyGoer {
	
	// store locally where u are
	// check with that exactly what can u do?
	

	// ************** Constructor **************
	public JoeMama(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName) {
		super(shape, color, borderWidth, avatarAge, avatarName);
		// TODO
	}

	public void dancingAlgo() {
		// TODO
		
		
	}

	public void fight(PartyGoer opponent) { // Call this function if other avatar starts a fight
		// TODO
		
	}


	public void talk(PartyGoer person) {
		// TODO		
	}


	public void smoke() {
		// TODO
	}


	public void toilet(int timeInToilet) {
		// TODO
		
	}


	public void playPool() {
		// TODO
		
	}


	public void playFussball() {
		// TODO
		
	}


	public Direction moveAvatar() {
		// TODO
		Direction dir = Direction.FORWARD;
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink and update alcohol levels
		// TODO
	}
}
