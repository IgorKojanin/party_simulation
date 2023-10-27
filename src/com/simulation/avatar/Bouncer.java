package com.simulation.avatar;

import java.util.HashMap;

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
	private void hitPerson(int id) { // kick from bar
		
	}


	public Bouncer(Shape shape, Color color, int borderWidth, int avatarId) {
		super(shape, color, borderWidth, avatarId);
	}

}
