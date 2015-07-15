/**
 * LENNER
 * ASSIGNMENT 5
 * 
 */


import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game_Board extends JPanel implements ActionListener {
	
	static Vector<Player> players = new Vector<Player>();
	Vector<Space> spaces = new Vector<Space>();
	Vector<JLabel> labels = new Vector<JLabel>();
	String [] pile1 = {"move back 1 space", "Pay $200", "move back 2 spaces", "move back 4 spaces"}; //bad pile
	String [] pile2 = {"Go to space 10- safe space", "Move forward 2 spaces", "Go back to start and get $100", "Go back to start and get $100"}; //good pile
	boolean started;
	boolean ended;
	int pile=0;
	int player = 0;
	JFrame frame;
	final JButton b = new JButton();
	final JButton quitting = new JButton();
	final JButton startb = new JButton();
	JLabel label;
	Random generator = new Random();
	int random=0;
	public Game_Board() {
		frame = new JFrame("Monopoly");
		System.out.println("SIZE "+ players.size());
		for (int i = 0; i<players.size(); i++){
			add(players.get(i));
		}
		 for (int i=0; i<40; i++){
			 Space s = new Space();
			 s.value=generator.nextInt(200)+50;
			 s.number=i;
			 spaces.add(s);
		 }
		 for (int i=0; i<players.size(); i++){
			 players.get(i).sp=spaces.get(1);
		 }
			 for (int d = 0; d < 14; d++) {
						int ee = 7 + d;
						//spaces.get(d+7).number=ee;
						spaces.get(d+7).x=(100 * (d + 1)) - 40;
						spaces.get(d+7).y=750;
						if (d + 27 < 40) {
							//spaces.get(d+27).number=d+27;
							spaces.get(d+27).x=(100 * (14 - d)) - 40;
							spaces.get(d+27).y=50;
							}
					
					}
				for (int e = 0; e < 8; e++) {
					spaces.get(e).x=40;
					spaces.get(e).y=(100 * (e + 1)) - 40;
				}
				for (int e = 0; e < 7; e++) {
					spaces.get(e+21).x=1360;
					spaces.get(e+21).y=(100 * (8 - (e + 1)) - 40);
				}
		
		for (int i = 0; i < players.size(); i++) {
			ImageIcon icon = new ImageIcon("images/" + players.get(i).animal
					+ ".png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			players.get(i).img = newIcon;
		}
		ImageIcon quit = new ImageIcon("images/quit.png");
		Image quitimg = quit.getImage();
		Image Q = quitimg.getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon qq = new ImageIcon(Q);
		quitting.setIcon(qq);
		quitting.setBounds(700, 200, 150, 50);
		quitting.setEnabled(false);
		add(quitting);
		ImageIcon start = new ImageIcon("images/start.png");
		Image st = start.getImage();
		Image S = st.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ss = new ImageIcon(S);
		startb.setIcon(ss);
		startb.setBounds(700, 350, 100, 100);
		add(startb);
		quitting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quitting.hasFocus())
					try {
						Thread.sleep(200);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				System.exit(0);
			}
		});
		startb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (startb.hasFocus()){
				started=true;
				b.setEnabled(true);
				quitting.setEnabled(true);
				startb.setEnabled(false);
				for (int i = 0; i < players.size(); i++) {
					label = new JLabel(players.get(i).img);
					label.setBounds((25 * i), 25, 100, 100);
					labels.add(label);
				}
				//started=true;
				repaint();
				}
			}
		});
		started = false;
		ended = false;
		ImageIcon icon = new ImageIcon("images/dice.png");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);
		b.setIcon(newIcon);
		//b.setBounds(550, 350, 100, 100);
		
		b.setLocation(550, 350);
		b.setSize(100, 100);
		b.setEnabled(false);
		add(b);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				random=0;
				if (player==players.size()-1){
					player=0;
				}
				else{
					player++;
				}
				checkMoney(players.get(player));
				random= generator.nextInt(6)+1;
				int p=player+1;
				if (b.hasFocus()){
					JOptionPane.showMessageDialog(
							null,
							"Player "+ p +", the " + players.get(player).animal +" rolled a "
									+ Integer.toString(random));
					players.get(player).spacenum=(players.get(player).spacenum+random)%40;	
					
				}
				System.out.println("SPACE #: "+players.get(player).spacenum);
				labels.get(player).setBounds(spaces.get(players.get(player).spacenum).x-40, spaces.get(players.get(player).spacenum).y-40, 100, 100);
				repaint();
				checkSpace(players.get(player));
				labels.get(player).setBounds(spaces.get(players.get(player).spacenum).x-40, spaces.get(players.get(player).spacenum).y-40, 100, 100);
				
				repaint();		
			}
		});
		for (int i=0; i<spaces.size(); i++){
		System.out.println("VALUE "+spaces.get(i).value);
		}
		frame.setContentPane(this);
		frame.pack();
		frame.setSize(1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void checkMoney(Player p){
		if (p.money<=0){
			JOptionPane.showMessageDialog(
					null,
					"You ran out of money! Sorry you lose.");
			//players.remove(p);
		}
	}
		
	public void checkSpace(Player p){
		pile= generator.nextInt(4);
		
		if (p.spacenum%10==0){
			JOptionPane.showMessageDialog(
					null,
					"You landed on a safe space!");
		}
		else if (p.spacenum==6 || p.spacenum==19){
			JOptionPane.showMessageDialog(
					null,
					"You get a card from Pile 1 saying: "+pile1[pile]);
			if (pile==0){
				p.spacenum=p.spacenum-1;
			}if (pile==1){
				p.money=p.money-200;
			}
			if (pile==2){
				p.spacenum=p.spacenum-2;
			}if (pile==3){
				p.spacenum=p.spacenum-4;
			}
			
		}
		else if (p.spacenum==32|| p.spacenum==14){
			
			JOptionPane.showMessageDialog(
					null,
					"You get a card from Pile 2 saying: "+pile2[pile]);
			if (pile==0){
				p.spacenum=10;
			}if (pile==1){
				p.spacenum=p.spacenum+2;
			}
			if (pile==2){
				p.spacenum=0;
				p.money=p.money+100;
			}if (pile==3){
				p.spacenum=0;
				p.money=p.money+100;
			}
			
		}
		else{
			if (spaces.get(p.spacenum).isOwned()==false){
				int reply = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like to buy this space for "+ spaces.get(p.spacenum).value+"?",
					    "Purchase Space",
					    JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					p.money=p.money-spaces.get(p.spacenum).value;
					//set isowned to true
					p.ownedspaces.add(spaces.get(p.spacenum));
					spaces.get(p.spacenum).owner=p;
					spaces.get(p.spacenum).open=false;
				}
				if (reply == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(
							null,
							"Ok.");
				}
			}
			else if (spaces.get(p.spacenum).owner==p){
				int upv=spaces.get(p.spacenum).value+100;
				int reply = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like to upgrade this space to " + upv +" for 100?",
					    "Upgrade Space",
					    JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					p.money=p.money-100;
					//set isowned to true
					spaces.get(p.spacenum).value+=100;
				}
				if (reply == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(
							null,
							"Ok.");
				}
			}
			else{
				JOptionPane.showMessageDialog(
						null,
						"You have to pay "+ spaces.get(p.spacenum).value +" to " +spaces.get(p.spacenum).owner.animal +" for landing on this space.");
				p.money=p.money-spaces.get(p.spacenum).value;
				spaces.get(p.spacenum).owner.money+=spaces.get(p.spacenum).value;
			}
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("LABELS SIZE "+ labels.size());
		for(int i=0; i<labels.size(); i++){
			add(labels.get(i));
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillRect(300, 300, 150, 100);
		g2d.setColor(Color.BLACK);
		g2d.drawString("PILE 1", 340, 350);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(500, 500, 150, 100);
		g2d.setColor(Color.BLACK);
		g2d.drawString("PILE 2", 540, 550);
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < players.size(); i++) {
			String xx = players.get(i).animal;
			int money = players.get(i).money;
			String display = Integer.toString(money);
			int j = i + 1;
			String yy = Integer.toString(j);
			
			g2d.drawString("Player " + yy, 120, 150 + (30 * i));
			g2d.drawString(xx, 200, 150 + (30 * i));
			g2d.drawString("$ " + display, 280, 150 + (30 * i));
			g2d.drawString("Spaces/Value: ", 350, 150 + (30 * i));
			for(int k=0; k<players.get(i).ownedspaces.size(); k++){
				g2d.drawString(players.get(i).ownedspaces.get(k).number+"/ $ "+players.get(i).ownedspaces.get(k).value + ", ", 450+60*k, 150 + (30 * i));
			}
			
		}
		g2d.drawLine(100, 100, 1300, 100);
		g2d.drawLine(100, 700, 1300, 700);
		g2d.drawLine(100, 100, 100, 700);
		g2d.drawLine(1300, 100, 1300, 700);

		for (int i = 0; i < 14; i++) {
			g2d.drawLine(100 * (i + 1), 0, 100 * (i + 1), 100);
			g2d.drawLine(0, 100 * (i + 1), 100, 100 * (i + 1));
			g2d.drawLine(100 * (i + 1), 700, 100 * (i + 1), 800);
			g2d.drawLine(1300, 100 * (i + 1), 1400, 100 * (i + 1));
			}
		for (int i=0; i<40; i++){
			if (i %10==0){
				g2d.drawString("SAFE", spaces.get(i).x, spaces.get(i).y );
			}
			else{
			g2d.drawString(Integer.toString(i), spaces.get(i).x, spaces.get(i).y );
			if (i==6 || i==19){
				g2d.setColor(Color.RED);
				g2d.fillRect(spaces.get(i).x-50, spaces.get(i).y-50, 80, 80);
				g2d.setColor(Color.BLACK);
			}
			if (i==32 || i==14){
				g2d.setColor(Color.GREEN);
				g2d.fillRect(spaces.get(i).x-50, spaces.get(i).y-50, 80, 80);
				g2d.setColor(Color.BLACK);
			}
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	public static void main (String[] args){
		String[] choices = { "2", "3", "4"};
	    String input = (String) JOptionPane.showInputDialog(null, "Choose how many players",
	        "Players", JOptionPane.QUESTION_MESSAGE, null,
	        choices, // Array of choices
	        choices[0]); // Initial choice
	    System.out.println(input);
	    
	    String[] animals= { "cat", "dog", "pig", "horse", "butterfly", "lion", "bear", "giraffe", "cheetah", "lizard"};
	    for (int i=0; i<Integer.parseInt(input); i++){
	    	String input2 = (String) JOptionPane.showInputDialog(null, "Choose animal: ",
	    	        "Animals", JOptionPane.QUESTION_MESSAGE, null,
	    	        animals, // Array of choices
	    	        animals[0]); // Initial choice
		   Player p = new Player(input2, null, 25, 25, null);
    	players.add(p);
	    	for (int j=0; j<animals.length; j++){
	    		if (animals[j].compareTo(input2)==0){
	    			for (int k=j; k<animals.length-(i+1); k++){
	    				animals[k]=animals[k+1];
	    			}
	    		}
	    	}
	   }
	   //System.out.println("PLAYER SIZE: "+players.size());
	   Game_Board g = new Game_Board();
	   }
}