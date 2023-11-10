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
		Catherine2 partyGoerC = new Catherine2(Shape.CIRCLE, Colors.RED, 1, 20, "Catherine");

		Bouncer bouncerBob = new Bouncer(Shape.CIRCLE, Colors.BLUE, 5);
		
		bouncerBob.checkEntry(avatarJoe);
		bouncerBob.checkEntry(partyGoerC);

		bouncerBob.breakUpFight(avatarJoe, partyGoerC, 10, 20);

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