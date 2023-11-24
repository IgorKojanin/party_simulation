package com.simulation.matrix;

import java.awt.Color;
import java.util.ArrayList;

import com.simulation.avatar.Avatar;
import com.simulation.avatar.Bouncer;
import com.simulation.avatar.Emmanuel;
import com.simulation.enums.ChangeInXY;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;
import com.simulation.enviroment.MyFrame;

import com.simulation.partypeople.*;
import com.simulation.partypeople.Bernhard;

public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;
	private ArrayList<LocatedAvatar> queue;  // Array list for tracking avatars in queue
	// Queue related variables
	private int queuelength;
	private int initialWaitingTime;
	private boolean avatarInQueue;
	
	public Matrix() {
		env = new MyFrame();
		env.setVisible(true);
		avatars = new ArrayList<LocatedAvatar>();
		

		Emmanuel emmanuel = new Emmanuel(Shape.CIRCLE, Color.RED, 0, 0, "Emmanuel",0);
		Emmanuel eliyas = new Emmanuel(Shape.CIRCLE, Color.MAGENTA, 0, 0, "Eliyas",0);
		Emmanuel celestine = new Emmanuel(Shape.CIRCLE, Color.BLUE, 0, 0, "Celestine",0);
		LocatedAvatar locEmmanuel = new LocatedAvatar(emmanuel, 0, 0);
		LocatedAvatar locCelestine = new LocatedAvatar(celestine, 0, 0);
		LocatedAvatar locEliyas = new LocatedAvatar(eliyas, 31, 5);
		//avatars.add(locEmmanuel);
		//avatars.add(locCelestine);
		avatars.add(locEliyas);
		Bernhard bernhard = new Bernhard(Shape.CIRCLE, Color.YELLOW, 0, 0, "Bernhard",0);
		LocatedAvatar locBernhard = new LocatedAvatar(bernhard, 0, 0);
		avatars.add(locBernhard);

		// Implementing a queue at the entrance
		queuelength = 14; // Defining the length of the queue, can increase to required number of avatars
		initialWaitingTime = 5; // Waiting time defined for queue e.g. 5 minutes to enter, could depend on position
        avatarInQueue = true; // True if avatar waiting in queue, for simple start we set yes, can use interrupt later
        for(int i = 1; i <= queuelength; i++) {
			Avatar uniqueAvatar = createUniqueAvatar(i);
			LocatedAvatar avatar = new LocatedAvatar(uniqueAvatar, 34, 5 + i); // Starting position in front of door
			//avatars.add(avatar);
			initialWaitingTime += 5;
		}
		printAvatars();
	}
    
	// Instantiating a new avatar to be added to the queue, currently basic functiosn for testing
	private Avatar createUniqueAvatar(int index) {
		String name = "Avatar" + index;
		Shape shape = Shape.SQUARE;
		Color color = Color.BLUE;

		return new AvatarTemplate(shape, color, 0, 21, name, 20);
	}

	private void printAvatars() {
		for(LocatedAvatar avatar : avatars) {
			System.out.println("Name: " + avatar.getAvatar().getName() + "\n Waiting Time: " + avatar.getAvatar().getWaitingTime());
		}
	}
	
	public void moveAvatars() {
		for (LocatedAvatar locAvatar : avatars) {
			wait(30);
			locAvatar.setWhatIsee(env);
			int oldX = locAvatar.getX();
			int oldY = locAvatar.getY();
			Direction dir = locAvatar.getAvatar().moveAvatar();
			switch (dir) {
				case FORWARD:
					switch (locAvatar.getHeading()) {
						case WEST:
							changeXY(locAvatar, ChangeInXY.DECX);
							break;
						case EAST:
							changeXY(locAvatar, ChangeInXY.INCX);
							break;
						case NORTH:
							changeXY(locAvatar, ChangeInXY.DECY);
							break;
						case SOUTH:
							changeXY(locAvatar, ChangeInXY.INCY);
							break;
					}
					break;
				case BACK:
					switch (locAvatar.getHeading()) {
						case WEST:
							changeXY(locAvatar, ChangeInXY.INCX);
							break;
						case EAST:
							changeXY(locAvatar, ChangeInXY.DECX);
							break;
						case NORTH:
							changeXY(locAvatar, ChangeInXY.INCY);
							break;
						case SOUTH:
							changeXY(locAvatar, ChangeInXY.DECY);
							break;
					}
					break;
				case RIGHT:
					switch (locAvatar.getHeading()) {
						case WEST:
							changeXY(locAvatar, ChangeInXY.DECY);
							break;
						case EAST:
							changeXY(locAvatar, ChangeInXY.INCY);
							break;
						case NORTH:
							changeXY(locAvatar, ChangeInXY.INCX);
							break;
						case SOUTH:
							changeXY(locAvatar, ChangeInXY.DECX);
							break;
					}
					break;
				case LEFT:
					switch (locAvatar.getHeading()) {
						case WEST:
							changeXY(locAvatar, ChangeInXY.INCY);
							break;
						case EAST:
							changeXY(locAvatar, ChangeInXY.DECY);
							break;
						case NORTH:
							changeXY(locAvatar, ChangeInXY.DECX);
							break;
						case SOUTH:
							changeXY(locAvatar, ChangeInXY.INCX);
							break;
					}
					break;
			}
			
			env.moveTo(oldX, oldY, locAvatar.getX(), locAvatar.getY(),locAvatar.getColor());
		}
	}

 	public void simulateQueue() {
		while(avatarInQueue == true){
            for(LocatedAvatar locAvatar : avatars) {
                Avatar avatar = locAvatar.getAvatar();
				avatar.setWaitngTime(-5);
			}

		    // Potential experess VIP queuing algorithm --> needs to be worked on
			for(int i =avatars.size() - 1; i > 0; i--) { // Start from back of queue to distinguish waiting times
				LocatedAvatar current = avatars.get(i);
				LocatedAvatar previous = avatars.get(i - 1);
				if(current.getAvatar().getWaitingTime() < previous.getAvatar().getWaitingTime()){ // Update avatar positions dependent on waiting time
					avatars.set(i, previous);  // If the current avatar has a shorter waiting time than the previous, swap positions
					avatars.set(i - 1, current);
				}
			}
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void wait(int ms) {
			try {
				Thread.sleep(ms);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();

			}
		}
	
	
	public void run() {
		while (true) {
			moveAvatars();
		}
	}

	private void changeXY(LocatedAvatar locAvatar, ChangeInXY c) {
		switch (c) {
			case INCX:
				if (env.isUsable(locAvatar.getX() + 1, locAvatar.getY())
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX() + 1, locAvatar.getY())) {
					locAvatar.incX();
					locAvatar.setHeading(Heading.EAST);
				}
				break;

			case DECX:
				if (env.isUsable(locAvatar.getX() - 1, locAvatar.getY())
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX() - 1, locAvatar.getY())) {
					locAvatar.decX();
					locAvatar.setHeading(Heading.WEST);
				}
				break;

			case INCY:
				if (env.isUsable(locAvatar.getX(), locAvatar.getY() + 1)
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX(), locAvatar.getY() + 1)) {
					locAvatar.incY();
					locAvatar.setHeading(Heading.SOUTH);
				}
				break;

			case DECY:
				if (env.isUsable(locAvatar.getX(), locAvatar.getY() - 1)
						&& !env.isWall(locAvatar.getX(), locAvatar.getY(), locAvatar.getX(), locAvatar.getY() - 1)) {
					locAvatar.decY();
					locAvatar.setHeading(Heading.N