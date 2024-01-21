package com.simulation.partypeople;

import java.awt.Color;
import java.util.Arrays;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;
import com.simulation.enums.Heading;
import com.simulation.enums.Places;
import com.simulation.enums.Shape;

public class Emmanuel extends Avatar {

	private int hPlane =1;
	private int vPlane =1;
	private int hStep =0;
	private int vStep =0;
	private int vPos =0;
	private int hPos =0;
	private int vDir =1;

	private int prevLevel =vPos;
	private Direction prev =Direction.IDLE;
	private boolean turnedRightForDivert =false;
	private boolean turnedLeftForDivert =false;

	private boolean movedForwardForDivert =false;
	private boolean initComplete = false;
	private boolean hPlaneInitComplete = false;
	private boolean mapInitialized = false;
	private boolean stayPut =false;

	private Direction prevRL;

	private boolean roundOver =true;

	private Block[][] mentalMap;

	private boolean divert =false;
	private Heading heading;

	public Emmanuel(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitTime);
		this.heading =Heading.WEST;
	}

	@Override
	public Direction moveAvatar() {
		Direction dir = Direction.FORWARD;
		if(!mapInitialized && initComplete) {
			dir = Direction.IDLE;
			mentalMap = new Block[vPlane][hPlane];
			for (Block[] row : mentalMap) {
				for (int i = 0; i < hPlane; i++) {
					row[i] = new Block(Places.PATH);
				}
			}
			mapInitialized =true;
		}
		if(!initComplete){
			if(!hPlaneInitComplete) {
				dir =initHPlane();
			}else{
				dir =initVPlane();
			}
		}else if(mapInitialized && !stayPut){
			dir = fillMap();
		}else if(stayPut){
			dir = Direction.IDLE;
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
			hPlaneInitComplete =true;
			hPos=hPlane-1;
			System.out.println(hPlane);
		}
		return dir;
	}

	private Direction initVPlane(){
		Direction dir = Direction.IDLE;
		if(getWhatISee()[1]!= Places.WALL && vDir == 1 &&prev!=Direction.TURN_LEFT_ON_SPOT){
			if(getWhatISee()[1].equals(Places.PERSON)){
				dir = Direction.IDLE;
			}else{
				dir =Direction.FORWARD;
			}
			prev = dir;
		}else if (getWhatISee()[1] == Places.WALL){
			if(vDir==1){
				dir =Direction.TURN_LEFT_ON_SPOT;
				prev = Direction.TURN_LEFT_ON_SPOT;
			}
		}
		if(getWhatISee()[1]!= Places.WALL && (prev == Direction.TURN_LEFT_ON_SPOT||vDir==2)){
			vDir=2;
			boolean isOtherWall = isOtherWall();
			if(isOtherWall){
				divert =true;
			}else if( !divert){
				dir= Direction.FORWARD;
				prev=dir;
				vPlane++;
				System.out.println(vPlane);
			}
			if (divert){
				System.out.println("diverrting");
				dir =divert(Heading.SOUTH);
			}
		} else if (getWhatISee()[1].equals(Places.WALL)&&vDir ==2) {
			dir = Direction.TURN_LEFT_ON_SPOT;
			initComplete=true;
			System.out.println(vPlane);
			vPos =vPlane-1;
			prevLevel =vPos;
			heading = Heading.EAST;
		}
		return dir;
	}
	private Boolean isOtherWall(){
		if(getWhatISee()[1]!= Places.WALL && getWhatISee()[1]!=Places.PERSON && getWhatISee()[1] != Places.PATH && getWhatISee()[1] !=Places.DANCEFLOOR){
			return true;
		}else{
			return false;
		}
	}
	private Direction divert(Heading destHeading){
		Direction dir =Direction.IDLE;
		boolean divertIsDone=false;

		switch (destHeading) {
			case SOUTH -> {
				/*if (hPos != hPlane) {
					if (!roundOver && !turnedRightForDivert) {
						dir = Direction.TURN_RIGHT_ON_SPOT;
						turnedRightForDivert = true;
					} else if (getWhatISee()[1] == Places.PATH && turnedRightForDivert) {
						dir = Direction.FORWARD;
						turnedRightForDivert = false;
						hPos++;
					} else if (getWhatISee()[1] == Places.PATH && !turnedRightForDivert) {
						dir = Direction.TURN_LEFT_ON_SPOT;
						roundOver = true;
						divert = false;
					}
				} else*/ {
					if (roundOver && !turnedLeftForDivert) {
						dir = Direction.TURN_LEFT_ON_SPOT;
						turnedLeftForDivert = true;
						roundOver= false;
						System.out.println("step1");
					} else if (getWhatISee()[1] == Places.PATH && turnedLeftForDivert) {
						dir = Direction.FORWARD;
						turnedLeftForDivert = false;
						movedForwardForDivert =true;
						hPos--;
						System.out.println("step2");
					} else if (getWhatISee()[1] == Places.PATH && movedForwardForDivert) {
						dir = Direction.TURN_RIGHT_ON_SPOT;
						roundOver = true;
						divert = false;
						movedForwardForDivert =false;
						System.out.println("step3");
					}
				}

			}
			case NORTH -> {
				if (roundOver && !turnedRightForDivert) {
					dir = Direction.TURN_RIGHT_ON_SPOT;
					turnedRightForDivert = true;
					roundOver= false;
					System.out.println("step1 North divert");
				} else if (getWhatISee()[1] == Places.PATH && turnedRightForDivert) {
					dir = Direction.FORWARD;
					turnedRightForDivert = false;
					movedForwardForDivert =true;
					if(hPos!=0)hPos--;
					System.out.println("step2 North divert");
				} else if (getWhatISee()[1] == Places.PATH && movedForwardForDivert) {
					dir = Direction.TURN_LEFT_ON_SPOT;
					roundOver = true;
					divert = false;
					movedForwardForDivert =false;
					System.out.println("step3 North Divert");
				}

			}
		}

		return dir;
	}

	private Direction fillMap(){
		mentalMap[vPos][hPos].isVisited=true;
		System.out.println("---filling map");
		System.out.println(hPos);
		Direction dir = Direction.IDLE;
		System.out.println(changedLevel(prevLevel));
		if(changedLevel(prevLevel)){
			System.out.println("visited : " + mentalMap[vPos][hPos].isVisited);
			if(prevRL==Direction.RIGHT){
				heading =Heading.WEST;
				dir = Direction.TURN_LEFT_ON_SPOT;
			}else{
				heading = Heading.EAST;
				dir = Direction.TURN_RIGHT_ON_SPOT;
			}
			prevLevel =vPos;
		}else if(getWhatISee()[1]!=Places.PERSON ){
			if(((getWhatISee()[1]!=Places.WALL && !isOtherWall()) || (getWhatISee()[1]==Places.PATH||getWhatISee()[1].name().matches(".*CHAIR$")))&&!divert){
				dir = Direction.FORWARD;
				if(heading !=Heading.EAST && heading !=Heading.WEST){
					prevLevel =vPos;
					if(heading==Heading.NORTH &&vPos!=0){
						vPos--;
					} else if (heading==Heading.SOUTH &&vPos!=vPlane-1) {
						vPos++;
					}
				}
				if(heading !=Heading.NORTH && heading != Heading.SOUTH){
					if(heading==Heading.EAST &&hPos!=0){
						hPos--;
					} else if (heading==Heading.WEST &&hPos!=hPlane-1) {
						hPos++;
					}
				}
			}else if((isOtherWall() ||getWhatISee()[1]==Places.WALL) &&(heading ==Heading.EAST||heading==Heading.WEST)){
				Block b = new Block(getWhatISee()[1]);
				if(heading == Heading.EAST){
					if(hPos-1 !=hPlane)mentalMap[vPos][hPos+1] =b;
					if(vPos-1 >=0)dir = !mentalMap[vPos-1][hPos].isVisited ? Direction.TURN_LEFT_ON_SPOT :Direction.TURN_RIGHT_ON_SPOT;
					prevRL =Direction.RIGHT;
				}else if(heading == Heading.WEST){
					if(hPos+1<hPlane)mentalMap[vPos][hPos-1] =b;
					if(vPos-1 >=0)dir = !mentalMap[vPos-1][hPos].isVisited ? Direction.TURN_RIGHT_ON_SPOT :Direction.TURN_LEFT_ON_SPOT;
					prevRL =Direction.LEFT;
				}
				heading =Heading.NORTH;
			} else if ((isOtherWall() ||getWhatISee()[1]==Places.WALL) &&(heading ==Heading.NORTH||heading==Heading.SOUTH) &&!divert) {
				divert=true;
				System.out.println("got here for divert");
			}
			if (divert) {
				dir =divert(heading);
			}
		}else  {

		}

		return dir;
	}
	private boolean changedLevel(int vPosition){
		System.out.println("vpos " +vPosition);
		return vPos!=vPosition && isHasMoved();
	}

	private class Block {
		Places place;
		boolean isVisited;

		private Block(Places p){
			place = p;
		}
	}

}
