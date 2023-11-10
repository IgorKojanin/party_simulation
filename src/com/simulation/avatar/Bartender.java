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

import com.simulation.enums.BeverageType;
import com.simulation.enums.Colors;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class Bartender extends Avatar {
private static final int LEGAL_STRONG_ALCOHOL_AGE = 18;
private static final int LEGAL_WEAK_ALCOHOL_AGE = 16;
private static final int ADD_BEER_PERCENTAGE = 10;
private static final int ADD_VODKA_PERCENTAGE = 40;

	public int checkAge(Avatar avatar) {
		return avatar.getAge();
	}

	private void serveDrink(Avatar avatar, BeverageType type) {
		switch (type) {
		case BEER:
			if (checkAge(avatar) > LEGAL_WEAK_ALCOHOL_AGE) { // avatars can drink beer if they are above 16 years old
				avatar.setAlcoholPercentage(avatar.getAlcoholPercentage()+ADD_BEER_PERCENTAGE);
			}
			else
				System.out.println("Bartender: Sorry your'e too young");
			break;
		case VODKA:
			if (checkAge(avatar) > LEGAL_STRONG_ALCOHOL_AGE) { // avatars can drink vodka if they are above 18 years old
				avatar.setAlcoholPercentage(avatar.getAlcoholPercentage()+ADD_VODKA_PERCENTAGE);
			}
			else
				System.out.println("Bartender: Sorry your'e too young");
			break;
		case WATER: // drinking water lowers the Alcohol percentage 
			avatar.setAlcoholPercentage(avatar.getAlcoholPercentage()-10);
			break;
		
		}
	}
	
	
	// ************** Constructor **************
	public Bartender(Shape shape, Colors color, int borderWidth) {
		super(shape, color, borderWidth);
	}

	public Direction moveAvatar() {
		return Direction.BACK;
	}

}