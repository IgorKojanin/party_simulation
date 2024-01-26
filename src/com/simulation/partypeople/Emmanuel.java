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

	private boolean barFound =false;
	private boolean movedForwardForDivert =false;
	private boolean initComplete = false;
	private boolean hPlaneInitComplete = false;
	private boolean mapInitialized = false;
	private boolean stayPut =false;
	private boolean loungeFound =false;
	private boolean mapFilled =false;

	private Direction prevRL;

	private boolean roundOver =true;

	private Block[][] mentalMap;
	private final DanceMove dancer;

	private boolean divert =false;
	private Heading heading;

	public Emmanuel(Shape shape, Color color, int borderWidth, int avatarAge, String avatarName, int waitTime) {
		super(shape, color, borderWidth, avatarAge, avatarName, waitTime);
		this.heading =Heading.WEST;
		dancer = new DanceMove(3);
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
		}else if(!mapFilled && !stayPut){
			dir = fillMap();
		}else if(stayPut && loungeFound){
			dir = Direction.IDLE;
		}

		return dir;
	
	}
	private Direction initHPlane(){
		Direction dir = Direction.IDLE;
		if(this.getWhatISee()[1]!= Places.WALL){
			if(!this.getWhatISee()[1].equals(Places.PERSON)){
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
			if(!getWhatISee()[1].equals(Places.PERSON)){
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
		return getWhatISee()[1] != Places.WALL && getWhatISee()[1] != Places.PERSON && getWhatISee()[1] != Places.PATH && getWhatISee()[1] != Places.DANCEFLOOR;
	}
	private Direction divert(Heading destHeading){
		Direction dir =Direction.IDLE;
		boolean divertIsDone=false;

		switch (destHeading) {
			case SOUTH -> {
				if (roundOver && !turnedLeftForDivert) {
					dir = Direction.TURN_LEFT_ON_SPOT;
					turnedLeftForDivert = true;
					roundOver= false;
				} else if (getWhatISee()[1] == Places.PATH && turnedLeftForDivert) {
					dir = Direction.FORWARD;
					turnedLeftForDivert = false;
					movedForwardForDivert =true;
					hPos--;
				} else if (getWhatISee()[1] == Places.PATH && movedForwardForDivert) {
					dir = Direction.TURN_RIGHT_ON_SPOT;
					roundOver = true;
					divert = false;
					movedForwardForDivert =false;
				}

			}
			case NORTH -> {
				if (roundOver && !turnedRightForDivert) {
					dir = Direction.TURN_RIGHT_ON_SPOT;
					turnedRightForDivert = true;
					roundOver= false;
				} else if (getWhatISee()[1] == Places.PATH && turnedRightForDivert) {
					dir = Direction.FORWARD;
					turnedRightForDivert = false;
					movedForwardForDivert =true;
					if(hPos!=0)hPos--;
				} else if (getWhatISee()[1] == Places.PATH && movedForwardForDivert) {
					dir = Direction.TURN_LEFT_ON_SPOT;
					roundOver = true;
					divert = false;
					movedForwardForDivert =false;
				}

			}
		}

		return dir;
	}

	private Direction fillMap(){
		Direction dir = Direction.IDLE;
		if ((getWhatISee()[0] !=Places.DANCEFLOOR && !dancer.danceComplete && !dancer.dancingStarted) ||dancer.danceComplete){
			if(dancer.danceComplete && !barFound){
				if(getWhatISee()[1]==Places.BAR){
					barFound = true;
				}
			}
			if(dancer.danceComplete && barFound && (getWhatISee()[0]==Places.LOUNGE_BIG ||getWhatISee()[0]==Places.LOUNGE_SMALL)){
				stayPut =true;
				loungeFound =true;
			}
			mentalMap[vPos][hPos].isVisited = true;
			System.out.println("---filling map");
			if (changedLevel(prevLevel)) {
				if (prevRL == Direction.RIGHT) {
					heading = Heading.WEST;
					dir = Direction.TURN_LEFT_ON_SPOT;
				} else {
					heading = Heading.EAST;
					dir = Direction.TURN_RIGHT_ON_SPOT;
				}
				prevLevel = vPos;
			} else if (getWhatISee()[1] != Places.PERSON) {
				if (((getWhatISee()[1] != Places.WALL && !isOtherWall()) || (getWhatISee()[1] == Places.PATH || getWhatISee()[1].name().matches(".*CHAIR$"))) && !divert) {
					dir = Direction.FORWARD;
					if (heading != Heading.EAST && heading != Heading.WEST) {
						prevLevel = vPos;
						if (heading == Heading.NORTH && vPos != 0) {
							vPos--;
						} else if (heading == Heading.SOUTH && vPos != vPlane - 1) {
							vPos++;
						}
					}
					if (heading != Heading.NORTH && heading != Heading.SOUTH) {
						if (heading == Heading.EAST && hPos != 0) {
							hPos--;
						} else if (heading == Heading.WEST && hPos != hPlane - 1) {
							hPos++;
						}
					}
				} else if ((isOtherWall() || getWhatISee()[1] == Places.WALL) && (heading == Heading.EAST || heading == Heading.WEST)) {
					Block b = new Block(getWhatISee()[1]);
					if (heading == Heading.EAST) {
						if (hPos - 1 != hPlane) mentalMap[vPos][hPos + 1] = b;
						if (vPos - 1 >= 0)
							dir = !mentalMap[vPos - 1][hPos].isVisited ? Direction.TURN_LEFT_ON_SPOT : Direction.TURN_RIGHT_ON_SPOT;
						prevRL = Direction.RIGHT;
					} else if (heading == Heading.WEST) {
						if (hPos + 1 < hPlane) mentalMap[vPos][hPos - 1] = b;
						if (vPos - 1 >= 0)
							dir = !mentalMap[vPos - 1][hPos].isVisited ? Direction.TURN_RIGHT_ON_SPOT : Direction.TURN_LEFT_ON_SPOT;
						prevRL = Direction.LEFT;
					}
					heading = Heading.NORTH;
				} else if ((isOtherWall() || getWhatISee()[1] == Places.WALL) && (heading == Heading.NORTH || heading == Heading.SOUTH) && !divert) {
					divert = true;
				}
				if (divert) {
					dir = divert(heading);
				}
			} else {

			}
		}else {
			dancer.initialDancingHeading = dancer.dancingStarted ? dancer.initialDancingHeading : heading;
			if(getWhatISee()[0]==Places.DANCEFLOOR && dancer.danceRound<4){
				dir =dance();
			}else{
				dancer.danceComplete =true;
			}
		}

		return dir;
	}
	private boolean changedLevel(int vPosition){
		return vPos!=vPosition && isHasMoved();
	}


	private Direction dance(){
		Direction dir = Direction.IDLE;
		if(dancer.danceStep !=dancer.danceSpace){
			dir =Direction.FORWARD;
			dancer.danceStep++;
		}else{
			dancer.danceStep =0;
			dancer.danceRound++;
			dir =dancer.initialDancingHeading == Heading.EAST ? Direction.TURN_LEFT_ON_SPOT :Direction.TURN_RIGHT_ON_SPOT;
		}
		return dir;
	}
	private class Block {
		Places place;
		boolean isVisited;

		private Block(Places p){
			place = p;
		}
	}

	private class DanceMove{
		 boolean danceComplete =false;
		boolean dancingStarted =false;
		 int danceStep=0;
		 int danceSpace;
		 int danceRound =0;

		 Heading initialDancingHeading;
		 private DanceMove(int spaceSize){
			 danceSpace =spaceSize;
		 }
	}

}
