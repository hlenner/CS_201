import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class Player extends JComponent{
	Vector<Space> ownedspaces = new Vector<Space>();
	int money=1200;
	int spacenum=0;
	Space sp;
	String animal;
	ImageIcon img;
	int x;
	int y;
	public Player(String piece, ImageIcon imge, int xx, int yv, Space s){
		sp=s;
		img=imge;
		animal=piece;
		x=xx;
		y=yv;
	}
	public void setSpaceNum(int num){
		spacenum=num;
	}
}