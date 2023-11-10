///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Joe.java
// Description: Template for the people
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.partypeople;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

import com.simulation.avatar.Avatar;
import com.simulation.avatar.PartyGoer;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Colors;
import com.simulation.enums.Direction;
import com.simulation.enums.Shape;

public class JoeMama extends Avatar implements PartyGoer {
	
	// store locally where u are
	// check with that exactly what can u do?
	

	// ************** Constructor **************
	public JoeMama(Shape shape, Colors color, int borderWidth, int avatarAge, String avatarName) {
		super(shape, color, borderWidth, avatarAge, avatarName);
		// TODO
	}

	public void dancingAlgo() {
		// TODO
		
		
	}

	public void fight(PartyGoer opponent) { // Call this function if other avatar starts a fight
		// TODO
		
	}


	public void talk() { //PartyGoer person
		// TODO
        try {
            // Replace "mumble.wav" with the actual path to your "mumble.wav" file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("SFX\\Speaking.wav")
            );
 
            // Create a Clip object to play the audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
 
            // Optional: Add a listener to handle events (e.g., when the sound finishes)
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
 
            // Start playing the sound effect
            clip.start();
 
            // Optional: Wait for the sound to finish playing before exiting
            while (clip.isRunning()) {
                Thread.sleep(100);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}


	public void smoke() {
		// TODO
	}


	public void toilet(int timeInToilet) {
		// TODO
		
	}


	public void playPool() {
		// TODO
		
	}


	public void playFussball() {
		// TODO
		
	}


	public Direction moveAvatar() {
		// TODO
		Direction dir = Direction.FORWARD;
		return dir;
	}

	public void drink(BeverageType type) { // Ask bartender to drink and update alcohol levels
		// TODO
	}
}
