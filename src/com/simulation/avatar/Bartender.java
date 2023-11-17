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

import java.awt.Color;

import com.simulation.enums.BeverageType;
import java.awt.Color;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

import java.util.Iterator;
import java.util.LinkedList;

//Class to hold Avatar and BeverageType together for serving each Avatar with its drink
class AvatarOrder {
 private Avatar avatar;
 private BeverageType beverageType;

 public AvatarOrder(Avatar avatar, BeverageType beverageType) {
     this.avatar = avatar;
     this.beverageType = beverageType;
 }

 public Avatar getAvatar() {
     return avatar;
 }

 public BeverageType getBeverageType() {
     return beverageType;
 }
}

public class Bartender extends Avatar {
	private LinkedList<AvatarOrder> orderQueue; // a queue for serving Avatars in order of arrival
	
	public void addOrderToQueue(Avatar avatar, BeverageType beverageType) {
        AvatarOrder avatarOrder = new AvatarOrder(avatar, beverageType);
        orderQueue.add(avatarOrder);
        System.out.println("Added avatar and drink");
    }
	
	public void removeOrderFromQueue() {
        AvatarOrder removedOrder = orderQueue.poll();

        if (removedOrder != null) {
            Avatar removedAvatar = removedOrder.getAvatar();
            BeverageType beverageType = removedOrder.getBeverageType();

            System.out.println("Removed order from the queue: Avatar: " + removedAvatar.toString() + ", BeverageType: " + beverageType);
        } else {
            System.out.println("Order queue is empty.");
        }
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
		this.orderQueue = new LinkedList<>();
	}
	
	
	
	public Direction moveAvatar() {
		return Direction.IDLE;
	}

}