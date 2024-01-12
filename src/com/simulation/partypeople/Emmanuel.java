package com.simulation.partypeople;

import java.awt.Color;
import java.util.Random;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Emmanuel extends Avatar {

	private int hPlane =0;
	private int vPlane =0;
	private int hStep =0;
	private int vStep =0;
	private int vDir =1;
	private Direction prev =Direction.IDLE;
	private boolean initComplete = false;
	private boolean hPlaneinitComplete = false;

	public Emmanuel(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitTime);
	}

	@Override
	public Direction moveAvatar() {
		Direction dir = Direction.FORWARD;
		if(!initComplete){
			if(!hPlaneinitComplete) {
				dir =initHPlane();
			}else{
				dir =initVPlane();
			}
			//System.out.println(hPlane);
			System.out.println(this.getWhatISee()[1]);
		}
		/*Random rand = new Random();
		int number = rand.nextInt(5);
		// direction is set externally --> check with the simulation environment

		if (number == 0) {
			dir = Direction.FORWARD;
		}
		else if (number == 1) {
			dir = Direction.RIGHT;
		}
		else if (number == 2) {
			dir = Direction.BACK;
		}
		else if (number == 3) {
			dir = Direction.LEFT;
		}
		else if (number == 4) {
			dir = Direction.TURN_LEFT_ON_SPOT;
		}
		else if (number == 5) {
			dir = Direction.TURN_RIGHT_ON_SPOT;
		}*/
		return dir;
	
	}
	private Direction initHPlane(){
		Direction dir = Direction.IDLE;
		if(this.getWhatISee()[1]!= Places.WALL){
			if(this.getWhatISee()[1].equals(Places.PERSON)){
				dir = Direction.IDLE;
			}else{
				dir= Direction.FORWARD;
				hPlane+=1;
			}
		}else{
			dir =Direction.TURN_RIGHT_ON_SPOT;
			hPlaneinitComplete =true;
		}
		return dir;
	}

	private Direction initVPlane(){
		Direction dir = Direction.IDLE;

		if(this.getWhatISee()[1]!= Places.WALL && vDir == 1){
			if(this.getWhatISee()[1].equals(Places.PERSON)){
				dir = Direction.IDLE;
				prev = dir;
			}else{
				dir= Direction.FORWARD;
				prev=dir;
			}
		}else if (this.getWhatISee()[1].equals(Places.WALL)&&vDir ==1){
			dir =Direction.TURN_LEFT_ON_SPOT;
			prev = Direction.TURN_LEFT_ON_SPOT;
		}
		if(this.getWhatISee()[1]!= Places.WALL && (prev.equals(Direction.TURN_LEFT_ON_SPOT)||vDir==2)){
			vDir=2;
			if(isOtherWall()){
				dir = divert();
			}else{
				dir= Direction.FORWARD;
				prev=dir;
				vPlane++;
			}
		}else if (this.getWhatISee()[1].equals(Places.WALL)&&vDir ==2) {
			dir = Direction.TURN_LEFT_ON_SPOT;
			initComplete=true;
		}
		return dir;
	}
	private Boolean isOtherWall(){
		if(!this.getWhatISee()[1].equals(Places.WALL)&&!this.getWhatISee()[1].equals(Places.PERSON)){
			return true;
		}else{
			return false;
		}
	}
	private Direction divert(){
		Direction dir =Direction.IDLE;
		if(!isOtherWall()){
			dir = Direction.RIGHT;
			hStep--;
		}
		return dir;
	}

}
