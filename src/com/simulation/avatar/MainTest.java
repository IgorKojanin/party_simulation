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
import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class MainTest {
	public static void main(String[] args) {
//		PartyGoer avatarJoe = new PartyGoer(Shape.CIRCLE, Colors.RED, 1, 2, 3, 4, null, null);
//		DJ dj_party = new DJ(null, null, 0, 0);
//		System.out.println("Color: " + avatarJoe.getColor());
//		System.out.println("Shape: " + avatarJoe.getShape());
//		System.out.println("Size: " + avatarJoe.getBorderWidth());
//		System.out.println("Id: " + avatarJoe.getId());
//		System.out.println("Age: " + avatarJoe.getAge());
//		System.out.println("Drinks consumed: " + avatarJoe.getDrinksConsumed());
//		System.out.println("My Place priorities: " + avatarJoe.getPlacePriorities());
//		System.out.println("My questions and answers: " + avatarJoe.getQuestionsAnswersList());
//		System.out.println("My randomely chosen direction: " + avatarJoe.moveAvatar());
//
//		Bartender bartenderSue = new Bartender(null, null, 0, 0);
//
//		System.out.println("Color: " + bartenderSue.getColor());


		DJ dj = new DJ(Shape.CIRCLE, Color.BLUE, 5, 1);
		dj.playMusic(); // Start playing all tracks in sequence

		try {
			Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// To play a specific track:
		//dj.playSpecificMusic("Spice");

	}
}
