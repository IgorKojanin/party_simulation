///////////////////////////////////////////////////////////////////////////////
//                   Party Simulator
// Date:         27/10/2023
//
// Class: PartyGoer.java
// Description: Interface providing the basics functions to be implemented
//
///////////////////////////////////////////////////////////////////////////////
package com.simulation.avatar;
import com.simulation.enums.BeverageType;
import com.simulation.enums.Direction;
public interface PartyGoer {
	
	public void dancingAlgo();
	public void drink(BeverageType type);	
	public void fight(PartyGoer opponent);	
	public void talk();	//PartyGoer person
	public void smoke();
	public void toilet(int timeInToilet);
	public void playPool();	
	public void playFussball();
}
