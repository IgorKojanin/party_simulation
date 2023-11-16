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

import com.simulation.enums.Shape;
import com.simulation.partypeople.Catherine2;
import com.simulation.partypeople.JoeMama;

import java.awt.*;

public class MainTest {
	public static void main(String[] args) {
		//(Shape shape, Colors color, int borderWidth, int avatarAge, String avatarName)
		
		JoeMama avatarJoe = new JoeMama(Shape.CIRCLE, Color.RED, 1, 20, "Almudena");
		Catherine2 partyGoerC = new Catherine2(Shape.CIRCLE, Color.RED, 1, 20, "Catherine");

		Bouncer bouncerBob = new Bouncer(Shape.CIRCLE, Color.BLUE, 5);
		
		bouncerBob.checkEntry(avatarJoe);
		bouncerBob.checkEntry(partyGoerC);

		bouncerBob.breakUpFight(avatarJoe, partyGoerC, 10, 20);

		DJ dj = new DJ(Shape.CIRCLE, Color.BLUE, 5, 1);
		dj.playMusic(); // Start playing all tracks in sequence

		try {
			Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// Bartender bartenderSue = new Bartender(null, null, 0, 0);
		// System.out.println("Color: " + bartenderSue.getColor());
	}
}