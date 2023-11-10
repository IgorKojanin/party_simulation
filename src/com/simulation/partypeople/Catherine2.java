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
import com.simulation.enums.Colors;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Catherine2 extends Avatar implements PartyGoer {
	
	// store locally where u are
	// check with that exactly what can u do?
	

	// ************** Constructor **************
	public Catherine2(Shape shape, Colors color, int borderWidth, int avatarAge, String avatarName) {
		super(shape, color, borderWidth, avatarAge, avatarName);
		// TODO Auto-generated constructor stub
	}

	public void dancingAlgo() {
		// TODO Auto-generated method stub
		
		
	}

	
	public void drink() { // Ask bartender to drink and update alcohol levels
		// TODO Auto-generated method stub
		
	}


	public void fight() { // Call this function if other avatar starts a fight
		// TODO Auto-generated method stub
		
	}


	public void talk() {
		// TODO Auto-generated method stub
		
	}


	public void smoke() {
		// TODO Auto-generated method stub
		
	}


	public void toilet() {
		// TODO Auto-generated method stub
		
	}


	public void playPool() {
		// TODO Auto-generated method stub
		
	}


	public void playFußt() {
		// TODO Auto-generated method stub
		
	}


	public void getWhatISee() {
		// get function from simulation, returns array of Places enums. 2 places ahead
		// TODO Auto-generated method stub
		
	}


	public void asdfg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drink(BeverageType type) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'drink'");
	}

	@Override
	public void fight(PartyGoer opponent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'fight'");
	}

	@Override
	public void toilet(int timeInToilet) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'toilet'");
	}

	@Override
	public void playFussball() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'playFussball'");
	}

	@Override
	public Direction moveAvatar() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'moveAvatar'");
	}
}
