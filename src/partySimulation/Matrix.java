package partySimulation;

import java.util.ArrayList;

import com.simulation.avatar.Avatar;
import com.simulation.enums.Direction;

public class Matrix {

	private MyFrame env;
	private ArrayList<LocatedAvatar> avatars;
	
	public Matrix() {
		env = new MyFrame();
		avatars = new ArrayList<LocatedAvatar>();
	}

	public void run() {
		while(true) {
			for(LocatedAvatar locAvatar : avatars) {
				
				Direction dir = locAvatar.getAvatar().moveAvatar();
				switch(dir) {
					case Direction.FORWARD:
						if(locAvatar.getHeading()==Heading.WEST) {
							if(env.isUsable(locAvatar.getX()+1,locAvatar.getY()))
							locAvatar.setX(locAvatar.getX()+1);
						}else if(locAvatar.getHeading()==Heading.EAST) {
							if(env.isUsable(locAvatar.getX()-1,locAvatar.getY()))
								locAvatar.setX(locAvatar.getX()-1);
					}else if(locAvatar.getHeading()==Heading.NORTH) {
						if(env.isUsable(locAvatar.getX(),locAvatar.getY()-1))
							locAvatar.setX(locAvatar.getY()-1);
					}else if(locAvatar.getHeading()==Heading.SOUTH) {
						if(env.isUsable(locAvatar.getX(),locAvatar.getY()+1))
							locAvatar.setX(locAvatar.getY()+1);
					}
				}
			}
		}
		
	}
}
