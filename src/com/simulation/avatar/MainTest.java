package com.simulation.avatar;
import com.simulation.enviroment.MyFrame;

import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class MainTest {

	public static void main(String[] args) {
		PartyGoer avatarJoe = new PartyGoer(Shape.CIRCLE, Color.RED, 1, 2, 3, 4, null, null); 
		DJ dj_party = new DJ(null, null, 0, 0); 
		System.out.println("Color: " + avatarJoe.getColor());
		System.out.println("Shape: " + avatarJoe.getShape());
		System.out.println("Size: " + avatarJoe.getBorderWidth());
		System.out.println("Id: " + avatarJoe.getId());
		System.out.println("Age: " + avatarJoe.getAge());
		System.out.println("Drinks consumed: " + avatarJoe.getDrinksConsumed());
		System.out.println("My Place priorities: " + avatarJoe.getPlacePriorities());
		System.out.println("My questions and answers: " + avatarJoe.getQuestionsAnswersList());
		System.out.println("My randomely chosen direction: " + avatarJoe.moveAvatar());
		
		Bartender bartenderSue = new Bartender(null, null, 0, 0);  
		System.out.println("Color: " + bartenderSue.getColor());
		new MyFrame();
	} 
}
