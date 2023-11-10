///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: PartyGoer.java
// Description: Interface providing the basics functions to be implemented
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.avatar;


import java.awt.Color;
import java.util.HashMap;


import com.simulation.enums.Shape;

public class PartyGoer extends Avatar {
	
	private void startDancing(){
		
	}
	private void stopDancing(){
		
	}
	private void setTimeout(){
		
	}
	private void drink(){
		
	}


	// ************** Constructor **************
	public PartyGoer(Shape shape, Color color, int borderWidth, int avatarId, int avatarAge, 
			int drinksConsumed, HashMap<String, Integer> placePriorities, 
			HashMap<String[], String[]> questionsAnswersList) {
		super(shape, color, borderWidth, avatarId, avatarAge, drinksConsumed, placePriorities, questionsAnswersList);
		// TODO Auto-generated constructor stub
	}

public interface PartyGoer {
	
	public void dancingAlgo();
	public void drink();	
	public void fight();	
	public void talk();	
	public void smoke();
	public void toilet();
	public void playPool();	
	public void playFußt();
	public void getWhatISee();
	public void asdfg();	

}
