package com.simulation.avatar;
import com.simulation.enviroment.MyFrame;

import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class MainTest {
	public static void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();

		}
	}

	public static void main(String[] args) {
		PartyGoer avatarJoe = new PartyGoer(Shape.CIRCLE, Color.RED, 1, 2, 3, 4, null, null); 
		System.out.println("Color: " + avatarJoe.getColor());
		System.out.println("Shape: " + avatarJoe.getShape());
		System.out.println("Size: " + avatarJoe.getBorderWidth());
		System.out.println("Id: " + avatarJoe.getId());
		System.out.println("Age: " + avatarJoe.getAge());
		System.out.println("Drinks consumed: " + avatarJoe.getDrinksConsumed());
		System.out.println("My Place priorities: " + avatarJoe.getPlacePriorities());
		System.out.println("My questions and answers: " + avatarJoe.getQuestionsAnswersList());
		System.out.println("My randomely chosen direction: " + avatarJoe.moveAvatar());

		Bartender bartenderSue = new Bartender(Shape.SQUARE, Color.BLUE, 10, 11, 12, 13, null, null);
		System.out.println("Color: " + bartenderSue.getColor());

		// DRAWING THE FLOORPLAN
		MyFrame frame = new MyFrame();
		frame.setVisible(true);

		// EXAMPLE MOVING AVATAR BY CHANGING SQUARE COLOR
		wait(1000);
		frame.moveObject(7,34, 6, 34);
		wait(1000);
		frame.moveObject(6,34, 5, 34);
		wait(1000);
		frame.moveObject(5,34, 5, 33);
		wait(1000);
		frame.moveObject(5,33, 5, 32);
		wait(1000);
		frame.moveObject(5,32, 5, 31);
		wait(1000);
		frame.moveObject(5,31, 5, 30);
	}
}
