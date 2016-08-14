package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;
class GUI extends JPanel {
    public char[][] fileMaze = null;
    public char[][] randomMaze = null;
    public char[][] selectedMaze = null;
	private BufferedImage leftArr = null;
	private BufferedImage rightArr = null;
	private BufferedImage upArr2 = null;
	private BufferedImage downArr2 = null;
	private BufferedImage leftArr2 = null;
	private BufferedImage rightArr2 = null;
	private BufferedImage leftArr3 = null;
	private BufferedImage rightArr3 = null;
	private BufferedImage upArr = null;
	private BufferedImage downArr = null;
	private BufferedImage boy = null;
	private BufferedImage wall = null;
	private BufferedImage coin = null;
	private BufferedImage door = null;
	private BufferedImage current = null;
	private Point startPos;
	private Point SwapPosX = null;
	private Point SwapPosY = null;
    private int iconSize = 45;
    private int MaxRandHeight = 7;
    private int MaxRandwidth = 13;
    private JPanel gui;
    private JFrame guiFrame;
    private JFrame tempFrame;
    private JLabel timer;
    private Stopwatch stopwatch;
    private boolean isLive;
    public GUI(String[] inMaze) {
    	if( inMaze == null ){
			JOptionPane.showMessageDialog(null, "File Currupted or not found");
			System.exit(0);
    	}
    	fileMaze = new char[inMaze.length][inMaze[0].length()];
    	for(int i=0;i<inMaze.length;i++)
    	this.fileMaze[i] = inMaze[i].toCharArray();
    	selectedMaze = new char[fileMaze.length][fileMaze[0].length];
		for(int i=0;i<fileMaze.length;i++){
			for(int j=0;j<fileMaze[i].length;j++){
				selectedMaze[i][j] = fileMaze[i][j];
			}
		}
    	try {
    	    leftArr = ImageIO.read(new File("left.png"));
    	    rightArr = ImageIO.read(new File("right.png"));
    	    upArr = ImageIO.read(new File("up.png"));
    	    downArr = ImageIO.read(new File("down.png"));
    	    leftArr2 = ImageIO.read(new File("left2.png"));
    	    rightArr2 = ImageIO.read(new File("right2.png"));
    	    leftArr3 = ImageIO.read(new File("left3.png"));
    	    rightArr3 = ImageIO.read(new File("right3.png"));
    	    upArr2 = ImageIO.read(new File("up2.png"));
    	    downArr2 = ImageIO.read(new File("down2.png"));
    	    door = ImageIO.read(new File("door.png"));
    	    coin = ImageIO.read(new File("coin.png"));
    	    boy = ImageIO.read(new File("sonic.png"));
    	    wall = ImageIO.read(new File("wall2.png"));
    	} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Images Currupted or not found");
			System.exit(0);
    	}
    	
    }
    public void paintIt(Graphics g2d,int fX ,int fY){
//    	fX = fY = 0;
        for(int i=0;i<selectedMaze.length;i++){
        	for(int j=0;j<selectedMaze[0].length;j++){
		        g2d.setColor(Color.BLACK);
				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize, iconSize);
    			if(selectedMaze[i][j] == '#'){
    		        g2d.setColor(Color.DARK_GRAY);
    				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2);
    				g2d.drawImage(wall, fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2,null);
        		}else if(selectedMaze[i][j] == 'S'){
    				g2d.setColor(Color.orange);
    				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2);
    				g2d.drawImage(current, fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2,null);
    				startPos = new Point(i,j);
    			}else if(selectedMaze[i][j] == 'E'){
    				g2d.setColor(Color.white);
    				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2);
    				g2d.drawImage(coin, fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2,null);
    			}else if(selectedMaze[i][j] == '*'){
    				g2d.setColor(Color.white);
    				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2);
    				g2d.drawImage(door, fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2,null);
    				
    			}else{
    				g2d.setColor(Color.WHITE);
    				g2d.fillRect(fX+j*iconSize, fY+i*iconSize, iconSize-2, iconSize-2);
    			}
        	}
        }
    }
    public void markPath( Graphics g2d ,int fX,int fY, Point[] path){
    	Point temp;
    	Point prev = path[0];
    	int i=1;
    	temp = path[i++];
    	while(path[i].x != -2){
    		prev = temp;
			temp = path[i++];
			Point hamada = temp;
			temp = prev;
			prev = hamada;
	        g2d.setColor(Color.BLACK);
			g2d.fillRect(fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize, iconSize);
	    	g2d.setColor(Color.orange);
			g2d.fillRect(fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize-2, iconSize-2);
			if( prev.y+1 == temp.y && prev.x == temp.x )
				g2d.drawImage(leftArr, fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize-2, iconSize-2,null);
			else if( prev.y-1 == temp.y && prev.x == temp.x )
				g2d.drawImage(rightArr, fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize-2, iconSize-2,null);
			else if( prev.y == temp.y && prev.x+1 == temp.x )
				g2d.drawImage(upArr, fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize-2, iconSize-2,null);
			else
				g2d.drawImage(downArr, fX+temp.y*iconSize, fY+temp.x*iconSize, iconSize-2, iconSize-2,null);
			prev = temp;
			temp = hamada;
    	}
    }
    public void generateRandomMaze(){
    	try{
			int h,w;
			Random rand = new Random();
			h = rand.nextInt(MaxRandHeight-5) + 6;
			w = rand.nextInt(MaxRandwidth-10) +11;
			MazeGenerator generator = new MazeGenerator(h,w);
			String[] temp = generator.generate();
			this.randomMaze = new char[temp.length][temp[0].length()];
			for(int i=0;i<temp.length;i++)randomMaze[i] = temp[i].toCharArray();
			this.selectedMaze = randomMaze;
			guiFrame.setSize(iconSize*(selectedMaze[0].length), iconSize*(selectedMaze.length));
			SwapPosX = null;
			SwapPosY = null;
			for(int i=0;i<selectedMaze.length;i++){
				for(int j=0;j<selectedMaze[i].length;j++){
					if(selectedMaze[i][j] == '*'){
						if(SwapPosX == null)SwapPosX = new Point(i,j);
						else SwapPosY = new Point(i,j);
					}
				}
			}
			current = boy;
		}catch(Exception ex){
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Error Occured");
		}
    	stopTime();
		gui.repaint();
    }
    public void Start(){
    	guiFrame = new JFrame();
		JFrame mini = new JFrame();
		gui = new JPanel(){
		    public void paintComponent(Graphics g2d) {	 
				super.paintComponent(g2d);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				guiFrame.setLocation(dim.width/2-guiFrame.getSize().width/2, dim.height/2-guiFrame.getSize().height/2);
				guiFrame.setSize(iconSize*(selectedMaze[0].length), iconSize*(selectedMaze.length));
		    	if(selectedMaze == null)return;
		        int fX = 0;
		        int fY = 0;
		        paintIt(g2d,fX,fY);
		    }
		};

		JLabel x1 = new JLabel("  F5       => Generate New Maze");
    	JLabel x2 = new JLabel("  F6       => Read Maze from File");
    	JLabel x3 = new JLabel("  CTRL + S => Solve the Maze");
    	JLabel x4 = new JLabel("  ESC      => Exit");
    	timer = new JLabel("0");
    	x1.setBounds(0,5,240,15);
    	x2.setBounds(0,20,240,15);
    	x3.setBounds(0,35,240,15);
    	x4.setBounds(0,50,240,15);
    	timer.setBounds(7,7,100,25);
    	timer.setOpaque(true);
    	timer.setForeground(Color.red);
    	timer.setBackground(new Color(25,50,74));
    	
    	mini.setLayout(null);
    	mini.add(x1);
    	mini.add(x2);
    	mini.add(x3);
    	mini.add(x4);
    	gui.add(timer);

        mini.pack();
    	mini.setSize(240, 70);
    	mini.setResizable(false);
    	mini.setAlwaysOnTop(true);
    	mini.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mini.setVisible(true);
        mini.addKeyListener(listener);
        
        
        gui.setLayout(new BorderLayout());

		guiFrame.setSize(iconSize*(selectedMaze[0].length), iconSize*(selectedMaze.length));
		guiFrame.setTitle("Maze Solver");
        guiFrame.setLocationRelativeTo(null);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setResizable(false);
		guiFrame.add(gui);
		guiFrame.addKeyListener(listener);
        guiFrame.setVisible(true);
        current = boy;
        isLive = false;
    }
    public void resetTime(){
    	stopwatch = new Stopwatch();
    	timer.setText(Double.toString(stopwatch.elapsedTime()));
       Timer SimpleTimer = new Timer(100, new ActionListener(){
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	try{
    	    		timer.setText("  Time : "+Double.toString(stopwatch.elapsedTime()));
    	    	}catch(Exception ex){
    	    		return;
    	    	}
    	    }
    	});
    	SimpleTimer.start();
    }
    public void stopTime(){
    	timer.setText("0");
    	stopwatch = null;
    	isLive = false;
    }
    public void move(int k){
    	if(!isLive){
    		isLive = true;
    		resetTime();
    	}
    	int di[] = {0,0,1,-1};
    	int dj[] = {1,-1,0,0};
    	Point temp = new Point(startPos.x,startPos.y);
    	if(temp.x + di[k] >= selectedMaze.length || temp.y + dj[k] >= selectedMaze[0].length  || temp.x+di[k] < 0 || temp.y+dj[k] < 0)return;
    	
    	if(selectedMaze[temp.x+di[k]][temp.y+dj[k]] == '.'){
	    	startPos = new Point(temp.x+di[k],temp.y+dj[k]);
	    	selectedMaze[temp.x][temp.y] = '.';
	    	selectedMaze[startPos.x][startPos.y] = 'S';
    	}else if(selectedMaze[temp.x+di[k]][temp.y+dj[k]] == 'E'){
    		Double tempTime = stopwatch.elapsedTime();
    		stopTime();
    		JOptionPane.showMessageDialog(this, "You Have Reached the required distination! in "+tempTime + " Seconds , Can you do better =D ! ?");
	    	startPos = new Point(temp.x+di[k],temp.y+dj[k]);
	    	selectedMaze[temp.x][temp.y] = '.';
	    	selectedMaze[startPos.x][startPos.y] = 'S';
	    	gui.repaint();
	    	generateRandomMaze();
    	}else if(selectedMaze[temp.x+di[k]][temp.y+dj[k]] == '*'){
    		if(new Point(temp.x+di[k],temp.y+dj[k]).equals(SwapPosX)){
    			startPos.x = SwapPosY.x;
    			startPos.y = SwapPosY.y;
    		}else{
    			startPos.x = SwapPosX.x;
    			startPos.y = SwapPosX.y;
    		}
	    	selectedMaze[temp.x][temp.y] = '.';
	    	selectedMaze[startPos.x][startPos.y] = 'S';
    	}else return;
    	if(temp.equals(SwapPosX) || temp.equals(SwapPosY)){
	    	selectedMaze[temp.x][temp.y] = '*';
    	}
    	gui.repaint();
    }
	KeyListener tempListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ESCAPE) {
	        	tempFrame.dispose();
	        }
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	};
		KeyListener listener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

		        if (key == KeyEvent.VK_LEFT) {
		        	if(current == leftArr)
		        		current = leftArr2;
		        	else if(current == leftArr2)
		        		current = leftArr3;
		        	else
		        		current = leftArr;
		        	move(1);
		        }

		        else if (key == KeyEvent.VK_RIGHT) {
		        	if(current == rightArr)
		        		current = rightArr2;
		        	else if(current == rightArr2)
		        		current = rightArr3;
		        	else
		        		current = rightArr;
		        	move(0);
		        }

		        else if (key == KeyEvent.VK_UP) {
		        	if(current == upArr)
		        		current = upArr2;
		        	else
		        		current = upArr;
		        	move(3);
		        }

	        	else if (key == KeyEvent.VK_DOWN) {
	        		if(current == downArr)
	        			current = downArr2;
	        		else
	        			current = downArr;
		        	move(2);
		        }else if (key == KeyEvent.VK_ESCAPE) {
		        	try{
		        		if(!tempFrame.isVisible())System.exit(0);
		        		tempFrame.dispose();
		        	}catch(Exception Ex){
		        		System.exit(0);
		        	}
		        }
		        else if (key == KeyEvent.VK_F5) {
		        	generateRandomMaze();
		        }
		        else if (key == KeyEvent.VK_F6) {

		        	stopTime();
					selectedMaze = new char[fileMaze.length][fileMaze[0].length];
					for(int i=0;i<fileMaze.length;i++){
						for(int j=0;j<fileMaze[i].length;j++){
							selectedMaze[i][j] = fileMaze[i][j];
						}
					}
					guiFrame.setSize(iconSize*(selectedMaze[0].length), iconSize*(selectedMaze.length));
					current = boy;
					gui.repaint();
		        }else if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    String temp = JOptionPane.showInputDialog("Algorithm Name : ");
                    if(temp != null && temp.length() > 2){
	                    String word;
        				word = "eg.edu.alexu.csd.ds.maze.cs45_65."+temp;
	        			try {
	        				int targety=0, targetx=0;
	        				for (int i=0; i<selectedMaze.length ;i++){
	        					for (int j=0; j<selectedMaze[i].length ;j++)
	        						if (selectedMaze[i][j]=='S'){
	        							targety=i;
	        							targetx=j;
	        							i = selectedMaze.length;
	        							break;
	        						}
	        				}
	        				Class theClass = Class.forName(word);
	        				MazeSolver algo = (MazeSolver) theClass.newInstance();
	        				try{
	        					String[] tempStr = new String[selectedMaze.length];
	        					for(int i=0;i<selectedMaze.length;i++)tempStr[i] = new String(selectedMaze[i]);
	        					algo.getData(tempStr, targety, targetx); 
	        					if(algo.solve()){
	        						final Point [] path = algo.getPath();
	        						JPanel msg = new JPanel(){
	        							public void paint(Graphics g2d) {	
	        					    		super.paint(g2d);
	        						    	if(selectedMaze == null)return;
	        						    	int fX = getWidth()/2 - iconSize*(selectedMaze[0].length)/2;
	        						        int fY = getHeight()/2 -  iconSize*(selectedMaze.length )/2;
	        						    	paintIt(g2d,fX,fY);
	        						    	try{
	        					    			markPath(g2d,fX,fY, path);
	        						    	}catch(Exception ex){
	        									
	        						    	}
	        							}
	        						};
	        						tempFrame = new JFrame("Path");
	        						tempFrame.addKeyListener(tempListener);
	        						tempFrame.setSize(iconSize*(selectedMaze[0].length), iconSize*(selectedMaze.length));
	        						tempFrame.setVisible(true);
	        						Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        						tempFrame.setLocation(dim.width/2-tempFrame.getSize().width/2, dim.height/2-tempFrame.getSize().height/2);
	        						tempFrame.add(msg);
	        					}else{
	        						JOptionPane.showMessageDialog(null, "Maze can't be Solved !");
	        					}
	        					
	        				}catch(Exception ex){
	        					JOptionPane.showMessageDialog(null, "Class Error !!");
	        				}
	        			} catch (Exception ex) {
	        				JOptionPane.showMessageDialog(null, "No Algorithm available with this name");
	        			}
                    }else{
            			JOptionPane.showMessageDialog(null, "Input is too short  !");
                    }
                    
                }
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
	};
}
