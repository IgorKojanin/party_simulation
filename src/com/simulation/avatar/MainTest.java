package com.simulation.avatar;

import com.simulation.enums.Color;
import com.simulation.enums.Shape;

public class MainTest {

	public static void main(String[] args) {
		PartyGoer avatarJoe = new PartyGoer(Shape.CIRCLE, Color.RED, 3, 30, 2, 0, null, null); 
		System.out.println("Hello");
		avatarJoe.moveAvatar();
	} 
}
