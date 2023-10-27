package partySimulation;

import com.simulation.avatar.Avatar;

public class LocatedAvatar {
	private Avatar avatar;
	private int x;
	private int y;
	private Heading heading;
	
	public LocatedAvatar(Avatar avatar,int x,int y,Heading heading) {
		this.avatar = avatar;
		this.x = x;
		this.y = y;
		this.heading = Heading.WEST;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Avatar getAvatar() {
		return avatar;
	}

	public Heading getHeading() {
		
		return heading;
	}
}
