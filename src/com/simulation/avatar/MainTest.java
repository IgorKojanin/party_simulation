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
import com.simulation.enums.Colors;
import com.simulation.enums.Shape;
import com.simulation.partypeople.Catherine2;
import com.simulation.partypeople.JoeMama;

public class MainTest {
	public static void main(String[] args) {
		//(Shape shape, Colors color, int borderWidth, int avatarAge, String avatarName)
		
		JoeMama avatarJoe = new JoeMama(Shape.CIRCLE, Colors.RED, 1, 20, "Almudena");
		Catherine2 partyGoerY = new Catherine2(Shape.CIRCLE, Colors.RED, 1, 17, "Catherine-too-young");
		Catherine2 partyGoerC1 = new Catherine2(Shape.CIRCLE, Colors.RED, 1, 30, "CatherineC1");
		Catherine2 partyGoerC2 = new Catherine2(Shape.CIRCLE, Colors.RED, 1, 20, "CatherineC2");

		Bouncer bouncerBob = new Bouncer(Shape.CIRCLE, Colors.BLUE, 5);

		// The Bouncer checks if all 4 people can be let in
		bouncerBob.checkEntry(avatarJoe);		
		bouncerBob.checkEntry(partyGoerC1);		
		bouncerBob.checkEntry(partyGoerC2);		
		bouncerBob.checkEntry(partyGoerY);

		System.out.println("The Bouncer lets Almudena, CatherineC1, and CatherineC2 into the party because they are overage:");
		System.out.println(bouncerBob.peopleInParty.toString());
		
		// The Bouncer kicks out Almudena and partyGoerC 
		bouncerBob.breakUpFight(partyGoerC1, partyGoerC2, 10, 20);
		System.out.println("The Bouncer breaks up a fight between CatherineC1 and CatherineC2 and both get kicked out. Only Almudena remains in the party:");
		System.out.println(bouncerBob.peopleInParty.toString());

		// After 10 min go by, Set the timeout time of the people who were kicked out
		partyGoerC1.setTimeoutTimeRemaining(0);
		partyGoerC2.setTimeoutTimeRemaining(10);

		
		// The Bouncer checks if all 4 people can be let in
		bouncerBob.checkEntry(avatarJoe);		
		bouncerBob.checkEntry(partyGoerC1);		
		bouncerBob.checkEntry(partyGoerC2);		
		bouncerBob.checkEntry(partyGoerY);
		
		System.out.println("The Bouncer only lets CatherineC1 back into the party because they have 0 time in timeout remaining:");
		System.out.println(bouncerBob.peopleInParty.toString());


		DJ dj = new DJ(Shape.CIRCLE, Colors.BLUE, 5, 1);
		// dj.playMusic(); // Start playing all tracks in sequence

		try {
			Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		// To play a specific track:

		avatarJoe.talk();

		wait(10000);

		// DJ dj_party = new DJ(null, null, 0, 0);
		// System.out.println("Color: " + avatarJoe.getColor());
		// System.out.println("Shape: " + avatarJoe.getShape());
		// System.out.println("Size: " + avatarJoe.getBorderWidth());
		// System.out.println("Id: " + avatarJoe.getId());
		// System.out.println("Age: " + avatarJoe.getAge());
		// System.out.println("Drinks consumed: " + avatarJoe.getDrinksConsumed());
		// System.out.println("My Place priorities: " + avatarJoe.getPlacePriorities());
		// System.out.println("My questions and answers: " + avatarJoe.getQuestionsAnswersList());
		// System.out.println("My randomely chosen direction: " + avatarJoe.moveAvatar());

		// Bartender bartenderSue = new Bartender(null, null, 0, 0);

		// System.out.println("Color: " + bartenderSue.getColor());


		// DJ dj = new DJ(Shape.CIRCLE, Colors.BLUE, 5, 1);
		// dj.playMusic(); // Start playing all tracks in sequence

		// try {
		// 	Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		// } catch (InterruptedException e) {
		// 	Thread.currentThread().interrupt();
		// }
		// // To play a specific track:
		// //dj.playSpecificMusic("Spice");

	}
	public static void wait(int ms) {
	try {
		Thread.sleep(ms);
	} catch (InterruptedException ex) {
		Thread.currentThread().interrupt();

	}
}
}