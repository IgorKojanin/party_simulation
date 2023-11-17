///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: MainTest.java
// Description: Main class for testing the avatar keep disabled outside of
//              avatar development
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import java.awt.Color;
import com.simulation.enums.Shape;
import com.simulation.partypeople.Catherine2;
import com.simulation.partypeople.JoeMama;

public class MainTest {
	public static void main(String[] args) {
		
		JoeMama avatarJoe = new JoeMama(Shape.CIRCLE, Color.RED, 1, 20, "Almudena", 0);
		Catherine2 partyGoerY = new Catherine2(Shape.CIRCLE, Color.RED, 1, 17, "Catherine-too-young", 0);
		Catherine2 partyGoerC1 = new Catherine2(Shape.CIRCLE, Color.RED, 1, 30, "CatherineC1", 0);
		Catherine2 partyGoerC2 = new Catherine2(Shape.CIRCLE, Color.RED, 1, 20, "CatherineC2", 0);
		Bouncer bouncerBob = new Bouncer(Shape.CIRCLE, Color.BLUE, 5);

		// The Bouncer checks if all 4 people can be let in
		bouncerBob.checkEntry(avatarJoe);
		bouncerBob.checkEntry(partyGoerC1);
		bouncerBob.checkEntry(partyGoerC2);
		bouncerBob.checkEntry(partyGoerY);

		System.out.println(
				"The Bouncer lets Almudena, CatherineC1, and CatherineC2 into the party because they are overage:");
		System.out.println(bouncerBob.peopleInParty.toString());

		// The Bouncer kicks out Almudena and partyGoerC
		bouncerBob.breakUpFight(partyGoerC1, partyGoerC2, 10, 20);
		System.out.println(
				"The Bouncer breaks up a fight between CatherineC1 and CatherineC2 and both get kicked out. Only Almudena remains in the party:");
		System.out.println(bouncerBob.peopleInParty.toString());

		// After 10 min go by, Set the timeout time of the people who were kicked out
		partyGoerC1.setTimeoutTimeRemaining(0);
		partyGoerC2.setTimeoutTimeRemaining(10);

		// The Bouncer checks if all 4 people can be let in
		bouncerBob.checkEntry(avatarJoe);
		bouncerBob.checkEntry(partyGoerC1);
		bouncerBob.checkEntry(partyGoerC2);
		bouncerBob.checkEntry(partyGoerY);

		System.out.println(
				"The Bouncer only lets CatherineC1 back into the party because they have 0 time in timeout remaining:");
		System.out.println(bouncerBob.peopleInParty.toString());
		System.out.println(bouncerBob.getListOfPeopleInParty());

		DJ dj = new DJ(Shape.CIRCLE, Color.BLUE, 5, 1);
		dj.playMusic(); // Start playing all tracks in sequence

		try {
			Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}
}