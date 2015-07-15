import javax.swing.JFrame;


public class Space {
	public boolean open= true;
	public boolean safe = false;
	public int value=0;
	public boolean drawcard= false;
	public int x;
	public int y;
	public int number;
	public Player owner;
	
	public Space(){
		
	}
	public void upgradeSpace(){
		value = value+200;
	}
	public boolean isOwned(){
		return !open;
	}
}
