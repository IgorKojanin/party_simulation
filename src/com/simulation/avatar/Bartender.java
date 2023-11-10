///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Bartender.java
// Description: Class extending avatar.java for the bartender serving people
//              drinks
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import com.simulation.enums.Colors;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Bartender extends Avatar {
	
	private void serveDrink() {
		
	}
	private void checkID() {
		
	}
	
	// ************** Constructor **************
	public Bartender(Shape shape, Colors color, int borderWidth) {
		super(shape, color, borderWidth);
	}

	public Direction moveAvatar() {
		return Direction.BACK;
	}

}
