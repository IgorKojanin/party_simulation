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
import java.awt.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;
import java.util.LinkedList;

public class Bartender extends Avatar {
	private LinkedList<Avatar> servingQueue; // a queue for serving Avatars in order of arrival
	
	public void addAvatarToOrderQueue(Avatar avatar) {
		servingQueue.add(avatar);
    }
	
    public void removeAvatarFromOrderQueue(Avatar avatar) {
    	servingQueue.remove(avatar);
    }
    
	private int checkAge(Avatar avatar) {
		return avatar.getAge();
	}

	public void serveDrink(Avatar avatar, BeverageType type) {
		final int LEGAL_STRONG_ALCOHOL_AGE = 18;
		final int LEGAL_WEAK_ALCOHOL_AGE = 16;
		final int ADD_BEER_PERCENTAGE = 10;
		final int ADD_VODKA_PERCENTAGE = 40;
		final int ADD_MOJITO_PERCENTAGE = 20;
		final int ADD_RUM_AND_COKE_PERCENTAGE = 25;
		final int ADD_APEROL_SPRITZ_PERCENTAGE = 15;
	    int legalAge = LEGAL_WEAK_ALCOHOL_AGE; // Default age for weak alcohol
	    int addPercentage = 0;

	    switch (type) {
	        case BEER:
	            legalAge = LEGAL_WEAK_ALCOHOL_AGE;
	            addPercentage = ADD_BEER_PERCENTAGE;
	            break;
	        case VODKA:
	            legalAge = LEGAL_STRONG_ALCOHOL_AGE;
	            addPercentage = ADD_VODKA_PERCENTAGE;
	            break;
	        case MOJITO:
	            legalAge = LEGAL_STRONG_ALCOHOL_AGE;
	            addPercentage = ADD_MOJITO_PERCENTAGE;
	            break;
	        case RUM_AND_COKE:
	            legalAge = LEGAL_STRONG_ALCOHOL_AGE;
	            addPercentage = ADD_RUM_AND_COKE_PERCENTAGE;
	            break;
	        case GIN_TONIC:
	            legalAge = LEGAL_STRONG_ALCOHOL_AGE;
	            addPercentage = ADD_RUM_AND_COKE_PERCENTAGE;
	            break;
	        case APEROL_SPRITZ:
	            legalAge = LEGAL_STRONG_ALCOHOL_AGE;
	            addPercentage = ADD_APEROL_SPRITZ_PERCENTAGE;
	            break;
	        case WATER:
	            avatar.setAlcoholPercentage(avatar.getAlcoholPercentage() - 10);
	            return; // No need for age check for water
	    }

	    if (checkAge(avatar) > legalAge) {
	        avatar.setAlcoholPercentage(avatar.getAlcoholPercentage() + addPercentage);
	    } else {
	        System.out.println("Bartender: Sorry you're too young for " + type.toString());
	    }
	}
	
	public void chat() {
		System.out.println("Bartender: Welcome to the party! What can I get you?");
	}
	
	// ************** Constructor **************
	public Bartender(Shape shape, Color color, int borderWidth) {
		super(shape, color, borderWidth);
		this.servingQueue = new LinkedList<>();
	}

	public Direction moveAvatar() {
		return Direction.IDLE;
	}

}