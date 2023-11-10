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
		JoeMama partyGoerJ = new JoeMama(Shape.SQUARE, Colors.RED, 1, 5678, 17, 1, false, 0, true);
		Catherine2 partyGoerC = new Catherine2(Shape.CIRCLE, Colors.BLUE, 1, 1234, 30, 1, false, 0, true);


		Bouncer bouncerBob = new Bouncer(Shape.CIRCLE, Colors.BLUE, 5, 1);
		
		bouncerBob.checkEntry(partyGoerJ);
		bouncerBob.checkEntry(partyGoerC);

		bouncerBob.breakUpFight(partyGoerJ, partyGoerC, 10, 20);

		DJ dj = new DJ(Shape.CIRCLE, Colors.BLUE, 5, 1);
		// dj.playMusic(); // Start playing all tracks in sequence

		try {
			Thread.sleep(Long.MAX_VALUE); // Keep the main thread asleep
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		// To play a specific track:
		//dj.playSpecificMusic("Spice");
	}
}