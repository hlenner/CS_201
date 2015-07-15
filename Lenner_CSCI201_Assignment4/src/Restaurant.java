import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



public class Restaurant extends JPanel implements ActionListener{
	final Vector<Table> tables = new Vector<Table>();
	final Vector<Wall> walls = new Vector<Wall>();
	final Vector<Header> headers = new Vector<Header>();
	final Vector<Podium>podiums = new Vector<Podium>();
	JFrame frame;
	JPanel topPanel;
	String title;
	int titlex;
	int titley;
	int col1;
	int col2;
	int wallx;
	int wally;
	
	    public Restaurant() {
	    	frame = new JFrame("CSCI201 Restaurant");
	    	JMenuBar menuBar;
	    	JMenu menu;
	    	setLayout(null);
	    	final JMenuItem open;
	    	menuBar = new JMenuBar();
	    	menu = new JMenu("File");
	    	Dimension menuDimension = new Dimension(150, 20);
	    	menuBar.add(menu);
	    	menuBar.setSize(menuDimension);
	    	open = new JMenuItem("Open",
	    			KeyEvent.VK_C);
	    	open.setEnabled(true);
	    	open.setPreferredSize(menuDimension);
	    	open.setAccelerator(KeyStroke.getKeyStroke(
	    			KeyEvent.VK_1, ActionEvent.ALT_MASK));
	    	menu.add(open);
	    	menuBar.add(menu);
	    	frame.setJMenuBar(menuBar);
	    	
	    	final JPanel mainPanel = new JPanel();
	    	//mainPanel.setBackground(Color.gray);
	    	mainPanel.setPreferredSize(new Dimension(650, 600));
	    	topPanel = new JPanel();
	    	topPanel.setBackground(Color.gray);
	    	topPanel.setPreferredSize(new Dimension(650, 30));
	    	
	        final JPanel gamePanel = new JPanel();
	        gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
	        gamePanel.setPreferredSize(new Dimension(650, 550));

	        final JPanel infoPanel = new JPanel();
	        infoPanel.setBackground(Color.WHITE);
	        
	        final JPanel previewPanel = new JPanel();
	        previewPanel.setBackground(Color.WHITE);
	        
	        
	        final JPanel checkboxes = new JPanel();
	        checkboxes.setPreferredSize(new Dimension(100, 100));
	        checkboxes.setLocation(500, 500);
	        checkboxes.add(new JCheckBox("open tables"));
	        checkboxes.add(new JCheckBox("occupied tables"));
	        
	        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	        infoPanel.setPreferredSize(new Dimension(150, 600));
	        
	        //infoPanel.add(previewPanel);
	        //infoPanel.add(checkboxes);
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent me) {
	                super.mouseClicked(me);
	                System.out.println("X POINT"+me.getPoint().x);
                    System.out.println("Y POINT"+me.getPoint().y);
	                for (Table t : tables) {
	                	if (t.shape.compareTo("rectangle")==0){
	                	 int x = t.locationX + t.height;
		                 int y = t.locationY + t.width;
	                	System.out.println("Table X POINT Start FIRST"+ t.locationX);
	                	System.out.println("Table X POINT Start SECOND"+ x);
	                    System.out.println("Table Y POINT END FIRST"+ t.locationY);
	                    System.out.println("Table Y POINT END SECOND"+y);
	                   
	                	if ((t.locationX < (me.getPoint().x)) && (x > (me.getPoint().x))){
	                		System.out.println("inside X!!!");
	                		if ((t.locationY < (me.getPoint().y)) && (y > (me.getPoint().y))){
	                    	System.out.println("inside Y!!!");
	                    	toggleColor(t);
	                		}
	                	}
	                    }
	                	else if (t.shape.compareTo("round")==0 || t.shape.compareTo("square")==0){
		                	 int x = t.locationX + t.radius;
			                 int y = t.locationY + t.radius;
		                	System.out.println("Table X POINT Start FIRST"+ t.locationX);
		                	System.out.println("Table X POINT Start SECOND"+ x);
		                    System.out.println("Table Y POINT END FIRST"+ t.locationY);
		                    System.out.println("Table Y POINT END SECOND"+y);
		                   
		                	if ((t.locationX < (me.getPoint().x)) && (x > (me.getPoint().x))){
		                		if ((t.locationY < (me.getPoint().y)) && (y > (me.getPoint().y))){
		                    	toggleColor(t);
		                		}
		                	}
		                    }
	                }
	            }
	        });
	        open.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent e) {
	    	    	//Create a file chooser
	    	    	final JFileChooser fc = new JFileChooser();
	    	    	//In response to a button click:
	    	    	int returnVal = fc.showOpenDialog(Restaurant.this);
	    	    	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	    	    File myFile = fc.getSelectedFile();
	    	    	    try{
	    	    	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	    	    dbFactory.setIgnoringElementContentWhitespace(true);
	    	    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	    		Document doc = dBuilder.parse(myFile);
	    	    		doc.getDocumentElement().normalize();
	    	    		System.out.println(doc);
	    	    	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    	    	    NodeList preview = doc.getElementsByTagName("title");
	    	    	     for (int t = 0; t < preview.getLength(); t++) {
	    	    	        Node tt = preview.item(t);
	    	    	        if (tt.getNodeType() == Node.ELEMENT_NODE) {
	    	    	        Element titleT = (Element) tt;
	    	    	        System.out.println("Name : " + titleT.getElementsByTagName("name").item(0).getTextContent());
	    	    	        title = titleT.getElementsByTagName("name").item(0).getTextContent();
	    	    	        titlex= Integer.parseInt(titleT.getElementsByTagName("x").item(0).getTextContent());
	    	    	        titley= Integer.parseInt(titleT.getElementsByTagName("y").item(0).getTextContent());
	    	    	        System.out.println("XLOC : " + titleT.getElementsByTagName("x").item(0).getTextContent());
	    	    	        System.out.println("YLOC : " + titleT.getElementsByTagName("y").item(0).getTextContent());
	    	    	        System.out.println("Location : " + titleT.getElementsByTagName("location").item(0).getTextContent());
	    	    	        }
	    	    	     }
	    	    	     
	    	    	     
	    	    	     previewPanel.setPreferredSize(new Dimension(100, 400));
			    	     previewPanel.setLayout(new GridLayout(20, 1));
			    	    NodeList tablestatus = doc.getElementsByTagName("column");   
			    	    
			    	    System.out.println(tablestatus.getLength());
			    	    for (int u = 0; u < tablestatus.getLength(); u++) {
	    	    	        Node uu = tablestatus.item(u);
	    	    	        if (uu.getNodeType() == Node.ELEMENT_NODE) {
	    	    	        Element table = (Element) uu;
	    	    	        Header H= new Header();
	    	    	        String title= table.getAttribute("name");
	    	    	        System.out.println("Name : " + title);
	    	    	        System.out.println("XLOC : " + table.getElementsByTagName("x").item(0).getTextContent());
	    	    	        System.out.println("YLOC : " + table.getElementsByTagName("y").item(0).getTextContent());
	    	    	        H.xloc=Integer.parseInt(table.getElementsByTagName("x").item(0).getTextContent());
	    	    	        H.yloc=Integer.parseInt(table.getElementsByTagName("y").item(0).getTextContent());
	    	    	        H.name=title;
	    	    	        headers.addElement(H);
	    	    	        //System.out.println("Location : " + titleT.getElementsByTagName("font").item(0).getTextContent());
	    	    	        
	    	    	        }
	    	    	     }
			    	    
			    	    NodeList gg = doc.getElementsByTagName("wall");   
			    	    
			    	    System.out.println(gg.getLength());
			    	    for (int u = 0; u < gg.getLength(); u++) {
	    	    	        Node uu = gg.item(u);
	    	    	        if (uu.getNodeType() == Node.ELEMENT_NODE) {
	    	    	        Element test = (Element) uu;
	    	    	        Wall W= new Wall();
	    	    	        W.startlocx=Integer.parseInt(test.getElementsByTagName("x").item(0).getTextContent());
	    	    	        W.startlocy=Integer.parseInt(test.getElementsByTagName("y").item(0).getTextContent());
	    	    	        W.endlocx=Integer.parseInt(test.getElementsByTagName("x").item(1).getTextContent());
	    	    	        W.endlocy=Integer.parseInt(test.getElementsByTagName("y").item(1).getTextContent());
	    	    	        walls.addElement(W);
	    	    	        //System.out.println("Location : " + titleT.getElementsByTagName("font").item(0).getTextContent());
	    	    	        
	    	    	        }
	    	    	     }
			    	    
			    	    NodeList pp = doc.getElementsByTagName("podium"); 
			    	    for (int u = 0; u < pp.getLength(); u++) {
	    	    	        Node uu = pp.item(u);
	    	    	        if (uu.getNodeType() == Node.ELEMENT_NODE) {
	    	    	        Element test = (Element) uu;
	    	    	       Podium P = new Podium();
	    	    	        P.xloc=Integer.parseInt(test.getElementsByTagName("x").item(0).getTextContent());
	    	    	        P.yloc=Integer.parseInt(test.getElementsByTagName("y").item(0).getTextContent());
	    	    	        P.width=Integer.parseInt(test.getElementsByTagName("width").item(0).getTextContent());
	    	    	        P.height=Integer.parseInt(test.getElementsByTagName("height").item(0).getTextContent());
	    	    	        podiums.addElement(P);
	    	    	        //System.out.println("Location : " + titleT.getElementsByTagName("font").item(0).getTextContent());
	    	    	        
	    	    	        }
	    	    	     }
			    	    
			    	    
			    	    
			    	    infoPanel.add(previewPanel);
	    	    	    NodeList nList = doc.getElementsByTagName("table");
	    	    	    
	    	    	    System.out.println("length: " + nList.getLength());
	    	    	    for (int i=0; i<nList.getLength(); i++){
	    	    	        Node nNode = nList.item(i);
	    	    	        System.out.println("nNode: "+nNode);

	    	    	       System.out.println("\nCurrent Element :" + nNode.getNodeName());
	    	    	       System.out.println("SIZE: " + nList.getLength());
	    	    	        
	    	    	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    	    	        	System.out.println("LENGTH: " + nNode.getChildNodes().getLength());
	    	    	        	
	    	    	            Element eElement = (Element) nNode;
	    	    	            Table T = new Table();
	    	    	            System.out.println("NUMBER:" + eElement.getAttribute("number"));
	    	    	            System.out.println("Shape : " + eElement.getElementsByTagName("shape").item(0).getTextContent());
	    	    	            T.shape= eElement.getElementsByTagName("shape").item(0).getTextContent();
	    	    	            T.tableNum=eElement.getAttribute("number");
	    	    	            System.out.println("table number: "+T.tableNum);
	    	            		T.locationX=Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
	    	            		T.locationY= Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
	    	            		System.out.println(T.locationX);
	    	            		System.out.println(T.locationY);
	    	    	            System.out.println("Seats : " + eElement.getElementsByTagName("numseats").item(0).getTextContent());
	    	    	            T.seats= Integer.parseInt(eElement.getElementsByTagName("numseats").item(0).getTextContent());
	    	    	            System.out.println("Size : " + eElement.getElementsByTagName("size").item(0).getTextContent());
	    	    	            if (T.shape.compareTo("round")==0){
	    	    	            	T.radius= Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
	    	    	            	System.out.println(T.radius);
	    	    	            }
	    	    	            else if (T.shape.compareTo("rectangle")==0){
	    	    	            	NodeList nn = eElement.getElementsByTagName("size").item(0).getChildNodes();
	    	    	            		String x = nn.item(1).getTextContent().trim();
	    	    	            		String y = nn.item(3).getTextContent().trim();
	    	    	            		System.out.println(x+","+y);
	    	    	            	T.height=Integer.parseInt(x);
	    	    	            	T.width= Integer.parseInt(y);
	    	    	            	//T.size.setSize(T.height, T.width);
	    	    	            	System.out.println(T.height);
	    	    	            	System.out.println(T.width);
	    	    	            	}
	    	    	            else if (T.shape.compareTo("square")==0){
	    	    	            	T.width= Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
	    	    	            	T.height= Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
	    	    	            	System.out.println(T.width);
	    	    	            	}
	    	    	            System.out.println("Status : " + eElement.getElementsByTagName("status").item(0).getTextContent());
	    	    	            T.status= eElement.getElementsByTagName("status").item(0).getTextContent();
	    	    	            if (T.status.compareTo("occupied")==0){
	    	    	            	T.color=Color.red;
	    	    	            }
	    	    	            else{
	    	    	            	T.color=Color.green;
	    	    	            }
	    	    	            System.out.println();
	    	    	            System.out.println();
	    	    	            System.out.println();
	    	    	            tables.addElement(T);
	    	    	        	}
	    	    	        	
	    	    	        	System.out.println("Size: "+tables.size());
	    	    	        	//frame.add(infoPanel, BorderLayout.EAST);
	    		    	    	for(int j=0; j<tables.size(); j++){
	    		    	    		gamePanel.add(tables.get(j));
	    		    	    		//repaint();
	    		    	    		}
	    		    	    	add(checkboxes);
	    		    	    	repaint();
	    	    	    		}
	    	    	        }
	    	    	  //  }
	    	    	    catch (Exception y) {
	    	    	    	y.printStackTrace();
							JOptionPane.showMessageDialog(null,
	    	    	    	        "The selected file is invalid",
	    	    	    	        "ERROR", JOptionPane.ERROR_MESSAGE);
	    	    	        }
	    	    	    }
	    	    }
	        });
	        mainPanel.add(topPanel);
	        mainPanel.add(gamePanel);
	        
	        
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        frame.setContentPane(this);
	        //frame.setResizable(false);
	        frame.pack();
	        frame.setSize(800,600);
	        frame.setVisible(true);
	    }
	    public void printTitle(String text){
	    	JLabel L = new JLabel();
	    	L.setText(text);
	    	System.out.println("adding");
	    	topPanel.add(L);
	    }
	    public void toggleColor(Table t){
	    	if (t.color==Color.red){
	    		t.color=Color.green;
	    		t.status="open";
	    	}
	    	else if (t.color==Color.green){
	    		t.color=Color.red;
	    		t.status="occupied";
	    	}
	    	removeAll();
	    	repaint();
	    }
	    public static void main(String s[]) {
	    	Restaurant r = new Restaurant();
	    }
	    
	    
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		public void paintComponent(Graphics g) {
			//System.out.println("Inside paint component, size=" + tables.size());
		    Graphics2D g2d = (Graphics2D) g;
		    for(int x=0; x<walls.size(); x++){
		    	g2d.drawLine(walls.get(x).startlocx,walls.get(x).startlocy,walls.get(x).endlocx,walls.get(x).endlocy );
		    }
		    for(int x=0; x<headers.size(); x++){
		    	
		    	g2d.drawString(headers.get(x).name, headers.get(x).xloc, headers.get(x).yloc);
		    }
		    for (int z=0; z<tables.size(); z++){
		    		g2d.drawString("Table "+(tables.get(z).tableNum).toString(), headers.get(0).xloc, headers.get(0).yloc+(20*(z+1)));
		    }
		    for (int z=0; z<tables.size(); z++){
	    		g2d.drawString(Integer.toString(tables.get(z).seats), headers.get(1).xloc, headers.get(0).yloc+(20*(z+1)));
		    }
		    for (int z=0; z<podiums.size(); z++){
		    	g2d.setColor(Color.CYAN);
		    	g2d.fillRect(podiums.get(z).xloc, podiums.get(z).yloc, podiums.get(z).height, podiums.get(z).width);
		    	g2d.setColor(Color.black);
	    		g2d.drawString("P",podiums.get(z).xloc+((podiums.get(z).height)/2),podiums.get(z).yloc+((podiums.get(z).width)/2));
		    }
		    
		    for(int i=0; i<tables.size(); i++){
		    	//System.out.println("height: "+tables.get(i).height);
		    	//System.out.println("width: "+tables.get(i).width);
		    	//System.out.println("radius: "+tables.get(i).radius);
		    	System.out.println("MOUSE CLICK COLOR: "+tables.get(i).color);
		    	g2d.setColor(Color.black);
		    	g2d.drawString(title, titlex, titley);
		    	g2d.setColor(tables.get(i).color);
		    	if (tables.get(i).height != -1 && tables.get(i).height != -1 ){
		    		System.out.println("rectangle");
		    		g2d.fillRect(tables.get(i).locationX, tables.get(i).locationY, tables.get(i).height, tables.get(i).width);
		    		g2d.setColor(Color.black);
		    		g2d.drawString(tables.get(i).tableNum,tables.get(i).locationX+((tables.get(i).height)/2),tables.get(i).locationY+((tables.get(i).width)/2));
		    	}
		    	else if(tables.get(i).radius!=-1){
		    		System.out.println("circle");
		    		g2d.fillOval(tables.get(i).locationX, tables.get(i).locationY, tables.get(i).radius, tables.get(i).radius);
		    		g2d.setColor(Color.black);
		    		g2d.drawString(tables.get(i).tableNum,tables.get(i).locationX+((tables.get(i).radius)/2),tables.get(i).locationY+((tables.get(i).radius)/2));
		    		}
		    	}
		}
		
		private class Table extends JComponent{
			protected int locationX;
			protected int locationY;
			protected String status;
			protected String tableNum;
			protected int seats;
			protected String shape;
			protected int height=-1;
			protected Dimension size;
			protected int width=-1;
			protected int radius=-1;
			protected Color color;
			public Table(){
			}
			
			
		ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                //repaint();
           }
		};
		}
		
		private class Wall extends JPanel{
			protected int startlocx;
			protected int startlocy;
			protected int endlocx;
			protected int endlocy;
			public Wall(){
				
			}
			
			
		ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                //repaint();
           }
		};
		}
		private class Header extends JComponent{
			protected int xloc;
			protected int yloc;
			protected String name;
		}
		private class Podium extends JComponent{
			protected int height;
			protected int width;
			protected int xloc;
			protected int yloc;
		}
		}
		
		


