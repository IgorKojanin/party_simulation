///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: DJ.java
// Description: Class extending avatar.java for the DJ in charge of music
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.avatar;

import com.simulation.enums.Colors;
import com.simulation.enums.Shape;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class DJ extends Avatar {
	ArrayList<String> musicList;
	private Clip currentClip;

	public DJ(Shape shape, Colors color, int borderWidth, int avatarId) {
		super(shape, color, borderWidth, avatarId);
		musicList = new ArrayList<>();
		// Consider adding full paths to the music files
		musicList.add("Music\\Latina.wav");
		musicList.add("Music\\Spice.wav");
	}

	public void playMusic() {
		System.out.println("Music starts");
		playNextTrack(0); // Start with the first track
	}

	private void playNextTrack(int trackIndex) {
		if (trackIndex >= musicList.size()) {
			System.out.println("Finished playing all tracks. Restarting playlist.");
			playNextTrack(0); // Loop back to the first track
			return;
		}
		String musicFile = musicList.get(trackIndex);
		playWav(musicFile, () -> playNextTrack(trackIndex + 1));
	}

	private void playSpecificMusic(String musicName) {
		String musicFile = musicName + ".wav";
		if (musicList.contains(musicFile)) {
			stopMusic(); // Stop the current music if any
			playWav(musicFile, this::playMusic); // Play the specific music and set the callback to playMusic
		} else {
			System.out.println("Music not found");
		}
	}

	private void playWav(String filename, Runnable onEnd) {
		try {
			stopMusic();

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename));
			currentClip = AudioSystem.getClip();
			currentClip.open(audioStream);
			currentClip.addLineListener(event -> {
				if (event.getType() == LineEvent.Type.STOP) {
					event.getLine().close();
					if (onEnd != null) {
						onEnd.run(); // Run the callback after the track has finished playing
					}
				}
			});
			currentClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void stopMusic() {
		if (currentClip != null) {
			if (currentClip.isRunning()) {
				currentClip.stop();
			}
			currentClip.close();
		}
	}


}
