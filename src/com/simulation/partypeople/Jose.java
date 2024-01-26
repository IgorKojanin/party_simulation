///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: Jose.java
// Description: Avatar class for Jose Luis Roldan Rodriguez
//
///////////////////////////////////////////////////////////////////////////////

package com.simulation.partypeople;

import com.simulation.avatar.Avatar;

import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Iterator;

import com.simulation.enums.*;

public class Jose extends Avatar {

	final static boolean FUNMODE = false; // Set to true to display output commands
	final static boolean LIVEMAP = true; // Set to true to save asic representation + csv file

	final static String script = "./misc/Shrek-Script_Jose.txt";
	final static String mentalFilePath = "./misc/JoseMap.csv";
	final static String mentalRepresentationPath = "./misc/JoseMapMatrix.txt";

	File mentalFile = new File(mentalFilePath);
	BufferedWriter bf = null;
	File file = new File(script);
	BufferedReader br = null;

	private int WAIT_TOILET = 2; // 10 loops * 30 ms
	private int WAIT_DANCE = 5; // 5 loops * 30 ms

	private List<String> shrek = new ArrayList<>();
	private BeverageType myBeverageType;
	private int waitTime_toilet = 0;
	private int waitTime_talk = 0;
	private int waitTime_dance = 0;

	private boolean is_turning = false;
	private boolean is_dancing = false;
	private boolean is_orderning = false;
	private boolean is_bingchilling = false;
	private boolean is_returning = false;
	private boolean std_infront = false;
	private boolean is_watiting = false;

	private int priorityList = 0;

	private Places[] priority = {
			Places.DANCEFLOOR,
			Places.BAR,
			Places.LOUNGE_BIG,
			Places.OUTSIDE
	};

	private Map<Integer, Places> mentalMap_pl_infront = new HashMap<>(); // Do class with objs
	private Map<Integer, Places> mentalMap_pl_std = new HashMap<>();
	private Map<Integer, Direction> mentalMap_dir = new HashMap<>();
	private int steps_done = 0;
	private int steps_done_return = 0;
	private int steps_done_return_tmp = -1;
	private int index_return = 0;

	// ************** Constructor **************
	public Jose(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitingTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitingTime);
		this.myBeverageType = BeverageType.MOJITO;
		if (FUNMODE) { // Store Shrek facts to array
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
				String line;
				while ((line = br.readLine()) != null) {
					shrek.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Methods:
	// ******************* Activities *******************
	public Direction dancingAlgo(Places placeAhead) {
		Direction dancingDir = Direction.IDLE;
		if (is_dancing == false) { // Step inside dancing floor
			dancingDir = Direction.FORWARD;
			is_dancing = true;
			if (FUNMODE) {
				System.out.printf("Jose: Yeah dancing is fun! \n"); // Store Shreck facts to array
			}
		} else {
			if (waitTime_dance < WAIT_DANCE) {
				if (placeAhead != Places.DANCEFLOOR) {
					dancingDir = Direction.TURN_LEFT_ON_SPOT;
				} else {
					dancingDir = Direction.FORWARD; // Do Naruto run, 佐!!!!!
				}
				waitTime_dance++;
			} else { // Go out of dancing floor, dont reset waitTime_dance, no more dancing
				dancingDir = Direction.FORWARD;
			}
		}
		return dancingDir;
	}

	public Direction chillAlgo(Places place_standing) {
		Direction chillDir = Direction.IDLE;
		if (priority[priorityList] == Places.LOUNGE_BIG) { // Move in and rest for ever
			if (place_standing != Places.LOUNGE_BIG && place_standing != Places.LOUNGE_SMALL
					&& place_standing != Places.LOUNGE_SMOKING) { // Check for
				chillDir = Direction.FORWARD;
				is_bingchilling = true;
				if (FUNMODE) {
					System.out.printf("Jose: Im gonna chill in here %n");
				}
			}
		} else {
			chillDir = turnRand();
		}
		return chillDir;
	}

	public void fight(Avatar opponent) {
		if ("Anatoly Cartman".equals(opponent.getName())) {
			if (FUNMODE) {
				System.out.printf("Jose kicks %s %n", opponent.getName());
			}
		}
	}

	public Direction talk() { // My avatar only speaks about shrek movie
		Direction dir = Direction.IDLE;
		if (FUNMODE) { // Talk to person only if fun mode
			if (waitTime_talk == 0) {
				System.out.printf("Jose: Hi person, do you want to hear Shrek silly facts? %n");
			} else if (waitTime_talk == 1) {
				Random rand = new Random();
				int i = rand.nextInt(shrek.size()) + 1;
				System.out.printf("Jose:  %s %n", shrek.get(i));
			} else {
				System.out.printf("Jose: Sayonara, baby %n");
				dir = Direction.TURN_LEFT_ON_SPOT;
				waitTime_talk = 0;
			}
			waitTime_talk++;
		} else {
			dir = turnRand(); // Avoid person
		}
		return dir;
	}

	public Direction toilet() { // Do only toilet things in the toilet
		Direction dir = Direction.IDLE;
		if (FUNMODE) {
			if (waitTime_toilet == 0) {
				dir = Direction.FORWARD;
				System.out.printf("Jose: Im sitting on the toilet playing my recorder turururu tururururu \n");
			} else if (waitTime_toilet < WAIT_TOILET) {
				dir = Direction.IDLE;
				if (waitTime_toilet == 1) {
					System.out.printf("Jose: Sitting on the toilet playing my recorder turururu tururururu \n");
				}
			} else {
				if (waitTime_toilet == (WAIT_TOILET + 2)) {
					dir = Direction.FORWARD;
					waitTime_toilet = 0;
				} else {
					dir = Direction.TURN_LEFT_ON_SPOT;
				}
			}
			waitTime_toilet++;
		} else {
			dir = turn_180(); // Avoid toilter
		}
		return dir;
	}

	public void playPool() { // Play pool only on the designate spot
		// Needs to check if someone is on the other chair
	}

	public void playFussball() { // Play Fussball only on the designate spot
		// Needs to check if someone is on the other chair
	}

	public Direction drinkBar() { // Ask for drink
		if (FUNMODE) {
			System.out.printf("Jose: bartender give me your strongest potion \n");
			if (is_orderning == false) { // Wait till its in the chair
				return Direction.FORWARD;

			} else {
				drink(myBeverageType);
				return Direction.IDLE;
			}
		} else {
			return turnRand();
		}
	}

	// ******************* Movement *******************
	public Direction moveAvatar() { // First implementation random movement
		Direction dir = Direction.FORWARD; // Default
		Places place_ahead = getWhatISee()[1];
		Places place_standing = getWhatISee()[0];

		if (place_ahead != Places.PERSON && waitTime_talk > 0) { // Reset if someon is rude
			waitTime_talk = 0;
		}
		if (is_dancing == true && priority[priorityList] == Places.DANCEFLOOR) {
			dir = this.dancingAlgo(place_ahead);
		} else if (is_bingchilling == true || longcheck(place_standing, place_ahead)) {
			dir = chillAlgo(place_standing);
		} else if (is_turning == true) {
			dir = turn_180();
		} else if (is_watiting == true) {
			if (place_ahead != Places.PERSON) {
				dir = Direction.FORWARD;
				is_watiting = false;
			} else {
				dir = Direction.IDLE;
			}
		} else {
			if (priority[priorityList] == Places.LOUNGE_BIG && is_inside() == true) {
				dir = undoPath();
				if (dir == Direction.FORWARD && place_ahead == Places.PERSON) {

				}
			} else {
				switch (place_ahead) {
					case BAR:
						dir = turnRand(); // Near bar, search for a spot free
						break;
					case BAR_CHAIR:
						dir = this.drinkBar(); // Drink
						break;
					case POOL:
						dir = turnRand();
						break;
					case POOL_CHAIR:
						dir = turnRand(); // Dont play for now
						// this.playPool(); // Not implemented
						break;
					case TOILET_CHAIR: // Do toilet things
						dir = toilet(); // Stay x time and then leave
						break;
					case TOILET:
						dir = turnRand();
						break;
					case DANCEFLOOR:
						dir = this.dancingAlgo(place_ahead);
						break;
					case FUSSBALL:
						dir = turnRand();
						break;
					case FUSSBALL_CHAIR:
						dir = turnRand();
						// this.playFussball(); // Play for x time
						break;
					case LOUNGE_BIG: // Same as the small one
					case LOUNGE_SMALL: // stay for x time
					case LOUNGE_SMOKING:
						dir = chillAlgo(place_standing);
						break;
					case DJ:
						dir = turnRand();
						if (FUNMODE) {
							System.out.println("Jose: Hi Dj bomboclap!");
						}
						break;
					case BOUNCER:
						dir = turnRand();
						if (FUNMODE) {
							System.out.println("Jose: Hi bouncer!");
						}
						break;
					case PATH:
						Random ran = new Random();
						if (ran.nextInt(5) >= 2) { // Higher chance to go forward than turn
							dir = moveRand();
						} else {
							dir = Direction.FORWARD;
						}
						break;
					case WALL:
						dir = turn_180(); // Turn around
						break;
					case OUTSIDE: // Turn around, dont want to leave
						dir = turn_180();
						break;
					case PERSON:
						dir = talk();
						break;
					case QUEUE:
						break;
					default:
						dir = moveRand();
						break;
				}
			}
		}

		if (dir != Direction.IDLE && is_returning != true) { // Dont store when idle or undoing the path
			mentalMap_dir.put(steps_done, dir);
			mentalMap_pl_infront.put(steps_done, place_ahead);
			mentalMap_pl_std.put(steps_done, place_standing);

			steps_done++;
		}

		if (priority[priorityList] == place_standing) { // Move the priority list if alread
			priorityList++;
		} else if (priority[priorityList] == Places.BAR || priority[priorityList] == Places.BAR_CHAIR) {
			if (place_ahead == Places.BAR || place_standing == Places.BAR_CHAIR) {
				priorityList++;
			}
		}

		if (LIVEMAP) {
			updateAndSaveMatrix();
			saveMentalMapToCSV();
		}
		return dir;
	}

	public Direction turn_180() {
		Direction dir = Direction.IDLE;
		if (is_turning == false) {
			dir = Direction.TURN_LEFT_ON_SPOT;
			is_turning = true;
		} else {
			dir = Direction.TURN_LEFT_ON_SPOT;
			is_turning = false;
		}
		return dir;
	}

	public Direction turnRand() { // Prevent being stuck
		Direction dir = Direction.IDLE;
		switch (random()) {
			case 0:
			case 1:
				dir = Direction.TURN_RIGHT_ON_SPOT;
				break;
			case 2:
			case 3:
				dir = Direction.TURN_LEFT_ON_SPOT;
				break;
		}
		return dir;
	}

	public Direction moveRand() {
		Direction dir = Direction.IDLE;
		switch (random()) {
			case 0:
				dir = Direction.FORWARD;
				break;
			case 1:
				dir = Direction.TURN_RIGHT_ON_SPOT;
				break;
			case 2:
				dir = Direction.FORWARD;
				break;
			case 3:
				dir = Direction.TURN_LEFT_ON_SPOT;
				break;
		}
		return dir;
	}

	public int random() {
		Random rand = new Random();
		return rand.nextInt(4);
	}

	public boolean longcheck(Places standing, Places heading) {
		boolean temp = false;
		if (priority[priorityList] == Places.LOUNGE_BIG)
			if (standing == Places.LOUNGE_SMALL || heading == Places.LOUNGE_SMALL
					|| standing == Places.LOUNGE_SMOKING || heading == Places.LOUNGE_SMOKING
					|| standing == Places.LOUNGE_BIG || heading == Places.LOUNGE_BIG) {
				temp = true;
			}
		return temp;
	}

	// ******************* Movement Return *******************
	public boolean is_inside() {
		boolean is_prio = false;
		index_return = -1; // Initialize with a value that will be less than any valid index

		switch (priority[priorityList]) {
			case BAR:
			case BAR_CHAIR:
				// Find the indices for BAR and BAR_CHAIR and compare them
				Integer barIndex = findVisitedPlaceIndex(Places.BAR);
				Integer barChairIndex = findVisitedPlaceIndex(Places.BAR_CHAIR);
				index_return = getMaxIndex(barIndex, barChairIndex);
				break;
			case LOUNGE_SMALL:
			case LOUNGE_BIG:
			case LOUNGE_SMOKING:
				// Similarly for LOUNGE_BIG and related places
				Integer loungeSmallIndex = findVisitedPlaceIndex(Places.LOUNGE_SMALL);
				Integer loungeBigIndex = findVisitedPlaceIndex(Places.LOUNGE_BIG);
				Integer loungeSmokingIndex = findVisitedPlaceIndex(Places.LOUNGE_SMOKING);
				index_return = getMaxIndex(getMaxIndex(loungeSmallIndex, loungeBigIndex), loungeSmokingIndex);
				break;
			default:
				Integer defaultIndex = findVisitedPlaceIndex(priority[priorityList]);
				if (defaultIndex != null) {
					index_return = defaultIndex;
				}
				break;
		}

		is_prio = index_return >= 0; // If maxIndex is not the initial -1, it means one of the places was found
		return is_prio;
	}

	private Integer getMaxIndex(Integer index1, Integer index2) {
		if (index1 == null && index2 == null) {
			return -1; // Both indices are null, indicating neither place has been visited
		} else if (index1 == null) {
			return index2;
		} else if (index2 == null) {
			return index1;
		} else {
			return Math.max(index1, index2);
		}
	}

	public Integer findVisitedPlaceIndex(Places place) {
		// Iterate through the mentalMap_pl_infront to find the place
		for (Map.Entry<Integer, Places> entry : mentalMap_pl_infront.entrySet()) {
			if (entry.getValue().equals(place)) {
				std_infront = true;
				return entry.getKey();
			}
		}
		// If not found in mentalMap_pl_infront, check in mentalMap_pl_std
		for (Map.Entry<Integer, Places> entry : mentalMap_pl_std.entrySet()) {
			if (entry.getValue().equals(place)) {
				std_infront = false;
				return entry.getKey();
			}
		}
		return null; // Return null if the place is not found in both maps
	}

	public Direction return_Path(Direction dir) {
		Direction dir_new = Direction.IDLE;
		switch (dir) {
			case FORWARD:
				dir_new = Direction.FORWARD;
				break;
			case TURN_RIGHT_ON_SPOT:
				dir_new = Direction.TURN_LEFT_ON_SPOT;
				break;
			case TURN_LEFT_ON_SPOT:
				dir_new = Direction.TURN_RIGHT_ON_SPOT;
				break;
			default:
				break;
		}
		return dir_new;
	}

	public Direction undoPath() {
		if (steps_done_return > index_return) {
			is_returning = true;
			steps_done_return--;
			Direction lastDirection = mentalMap_dir.get(steps_done_return);
			return return_Path(lastDirection);
		} else if (steps_done_return == index_return) { // At desired place
			is_returning = false;
			steps_done_return_tmp = steps_done_return;
			steps_done_return = -1;

			if (std_infront == true) { // Was infront not on top
				return Direction.FORWARD;
			} else {
				return Direction.IDLE;
			}
		} else {
			steps_done_return = steps_done;
			return turn_180();
		}
	}

	// ******************* Save mental map *******************

	public void saveMentalMapToCSV() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(mentalFilePath))) {
			// Write headers
			bw.write("Step,Place_Standing,Place_In_Front,Direction\n");

			// Iterate through the steps
			for (int step = 0; step < steps_done; step++) {
				String placeStanding = mentalMap_pl_std.getOrDefault(step, Places.QUEUE).toString();
				String placeInFront = mentalMap_pl_infront.getOrDefault(step, Places.QUEUE).toString();
				String direction = mentalMap_dir.getOrDefault(step, Direction.IDLE).toString();

				// Write to CSV
				bw.write(step + "," + placeStanding + "," + placeInFront + "," + direction + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateAndSaveMatrix() {
		// Variables for matrix representation, respect to Avatar
		final int MATRIX_SIZE = 80; // Assuming a 80x80 matrix
		char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];
		// Initial position of center of the matrix
		int posX = MATRIX_SIZE / 2;
		int posY = MATRIX_SIZE / 2;

		// Position infront of the avatar temp
		int posXF = posX - 1;
		int posYF = posY;

		// Previous Position for avatar
		int posXpre = posX;
		int posYpre = posY;
		char placeStandingX = getCharForPlace(mentalMap_pl_std.getOrDefault(0, Places.PATH));
		Heading currentDirection = Heading.WEST; // Initial direction

		// Initialize the matrix with default values
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				matrix[i][j] = '.'; // Dot for an unknown place
			}
		}

		// Iterate through each step
		for (int step = 0; step <= steps_done; step++) {
			// Update the matrix based on Jose's movement
			Direction direction = mentalMap_dir.getOrDefault(step, Direction.IDLE);
			char placeInFront = getCharForPlace(mentalMap_pl_infront.getOrDefault(step, Places.PATH));
			char placeStanding = getCharForPlace(mentalMap_pl_std.getOrDefault(step, Places.PATH));

			matrix[posX][posY] = placeStanding;
			if (placeInFront != '+') {
				matrix[posXF][posYF] = placeInFront;
			}
			// Place 'X' at last position
			if (step == (steps_done - 1) && !(step == steps_done_return_tmp && steps_done_return_tmp != -1)) {
				matrix[posXpre][posYpre] = placeStandingX; // Update previous place
				placeStandingX = matrix[posY][posX]; // Safe place
				posXpre = posX; // Update new avatar position
				posYpre = posY;
				matrix[posX][posY] = 'X'; // Add x for avatar
			}

			// Update direction and movement if Forward
			if (direction == Direction.FORWARD) {
				// Move based on currentDirection and update posX, posY
				switch (currentDirection) {
					case WEST:
						posY = posY - 1; // Move left
						break;
					case EAST:
						posY = posY + 1; // Move right
						break;
					case NORTH:
						posX = posX - 1; // Move up
						break;
					case SOUTH:
						posX = posX + 1; // Move down
						break;
				}
			} else if (direction == Direction.TURN_LEFT_ON_SPOT) {
				// Rotate left from his current direction
				switch (currentDirection) {
					case WEST:
						currentDirection = Heading.SOUTH;
						break;
					case EAST:
						currentDirection = Heading.NORTH;
						break;
					case NORTH:
						currentDirection = Heading.WEST;
						break;
					case SOUTH:
						currentDirection = Heading.EAST;
						break;
				}
			} else if (direction == Direction.TURN_RIGHT_ON_SPOT) {
				// Rotate right from his current direction
				switch (currentDirection) {
					case WEST:
						currentDirection = Heading.NORTH;
						break;
					case EAST:
						currentDirection = Heading.SOUTH;
						break;
					case NORTH:
						currentDirection = Heading.EAST;
						break;
					case SOUTH:
						currentDirection = Heading.WEST;
						break;
				}
			}

			// Update Infront position
			if (placeInFront != '+') { // Person or default option
				switch (currentDirection) {
					case WEST:
						posYF = posY - 1; // Move left
						posXF = posX;
						break;
					case EAST:
						posYF = posY + 1; // Move right
						posXF = posX;
						break;
					case NORTH:
						posXF = posX - 1; // Move up
						posYF = posY;
						break;
					case SOUTH:
						posXF = posX + 1; // Move down
						posYF = posY;
						break;
				}
			}

			// Wrap around logic for moving through matrix bounds
			posX = Math.floorMod(posX, MATRIX_SIZE);
			posY = Math.floorMod(posY, MATRIX_SIZE);
			posXF = Math.floorMod(posXF, MATRIX_SIZE);
			posYF = Math.floorMod(posYF, MATRIX_SIZE);
		}

		// Save the matrix to a file
		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(mentalRepresentationPath))) {
			for (int i = 0; i < MATRIX_SIZE; i++) {
				for (int j = 0; j < MATRIX_SIZE; j++) {
					bw.write(" " + matrix[i][j] + " ");
				}
				bw.write(" \n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private char getCharForPlace(Places place) {
		switch (place) {
			case BAR:
				return 'b';
			case BAR_CHAIR:
				return 'B';
			case POOL:
				return 'p';
			case POOL_CHAIR:
				return 'P';
			case TOILET_CHAIR:
				return 'T';
			case TOILET:
				return 't';
			case DANCEFLOOR:
				return 'D';
			case FUSSBALL:
				return 'f';
			case FUSSBALL_CHAIR:
				return 'F';
			case LOUNGE_BIG:
				return 'L';
			case LOUNGE_SMALL:
				return 'L';
			case LOUNGE_SMOKING:
				return 'L';
			case DJ:
				return 'j';
			case BOUNCER:
				return 'u';
			case PATH:
				return 'O';
			case WALL:
				return 'W';
			case OUTSIDE:
				return 'O';
			case PERSON:
				return '+';
			case QUEUE:
				return 'Q';
			default:
				return '+';
		}
	}

}
