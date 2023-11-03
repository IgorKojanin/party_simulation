///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Bouncer.java
// Description: Class extending avatar.java for the bouncer checking people
//              ages and also stopping fights
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.avatar;

import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class Bouncer extends Avatar {
	private static final int AGELIMIT = 18;

	public boolean checkAge(int avatarAge) {
		boolean isOverAge = false;
		if(avatarAge >= AGELIMIT) {
			isOverAge = true;
		}
		return isOverAge;
	}
	
	// Check entrance
	
	// let people in (check age)
	
	public void hitPerson(int id, int x, int y) {
		// kick from bar, we should get a signal from envi. or simulation a fight is taking place with
		// and where exactly
		// Send move out an avatar to simulation team
	}

	// ************** Constructor **************
	public Bouncer(Shape shape, Color color, int borderWidth, int avatarId) {
		super(shape, color, borderWidth, avatarId);
	}
	
	 public void kickout(Avatar person){
		 
	 }

}
