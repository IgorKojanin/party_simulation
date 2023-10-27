package com.simulation.avatar;

import java.util.HashMap;

import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class Bartender extends Avatar {
	
	private void serveDrink() {
		
	}
	private void checkID() {
		
	}

	public Bartender(Shape shape, Color color, int borderWidth, int avatarId, int avatarAge, 
			int drinksConsumed, HashMap<String, Integer> placePriorities, 
			HashMap<String[], String[]> questionsAnswersList) {
		super(shape, color, avatarAge, avatarAge, avatarAge, avatarAge, placePriorities, questionsAnswersList);
	}

}
