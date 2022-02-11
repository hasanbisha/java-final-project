import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
/**
 * Implementation of Dijkstra Algorithm and its UI
 * @author Xhuljo Balli, Arlind Idrizi
 * */
public class Dijkstra extends JFrame {
	int cells = 20;
	int delay = 30;
	double dense = .5;
	double density = (cells*cells)*.5;
	int start_x = -1;
	int start_y = -1;
	int finish_x = -1;
	int finish_y = -1;
	int tool = 0;
	int checks = 0;
	int length = 0;
	int curAlg = 0;
	final int m_size = 600;
	int c_size = m_size /cells;

	//arrays
	String[] algorithms = {"Dijkstra"};
	String[] tools = {"Start","Finish","Wall"};

	boolean solving = false;
	Node[][] map;
	Algorithm Alg = new Algorithm();
	Random random = new Random();

	//Sliders
	JSlider size = new JSlider(1,5,2);
	JSlider speed = new JSlider(0,500,delay);
	JSlider obstacles = new JSlider(1,50,25);

	//Labels
	JLabel algorithm = new JLabel();
	JLabel toolBox = new JLabel("Toolbox");
	JLabel sizeL = new JLabel("Size:");
	JLabel cellsL = new JLabel(cells+"x"+cells);
	JLabel delayL = new JLabel("Delay:");
	JLabel msL = new JLabel(delay+"ms");
	JLabel obstacleL = new JLabel("Dens:");
	JLabel densityL = new JLabel(obstacles.getValue()+"%");
	JLabel boxesCheck = new JLabel("Boxes checked: "+checks);
	JLabel lengthL = new JLabel("Path Length: "+length);
	//BUTTONS
	JButton searchButton = new JButton("Start Search");
	JButton resetButton = new JButton("Reset");
	JButton genMapButton = new JButton("Generate Map");
	JButton deleteMapButton = new JButton("Delete Map");

	//Combo boxes
	JComboBox algorithmsBx = new JComboBox(algorithms);
	JComboBox toolBx = new JComboBox(tools);
	//Panels
	JPanel toolP = new JPanel();
	//Canvas
	Map canvas;
	//BORDER
	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	public void generateMap() {   //Map generate
		clearMap();
		for (int i = 0; i < density; i++) {
			Node current;
			do {
				int x = random.nextInt(cells);
				int y = random.nextInt(cells);
				current = map[x][y];
			} while (current.getType() == 2);
			current.setType(2);
		}
	}
	public void clearMap() {	//clear map
		finish_x = -1;
		finish_y = -1;
		start_x = -1;
		start_y = -1;
		map = new Node[cells][cells];
		for(int x = 0; x < cells; x++) {
			for(int y = 0; y < cells; y++) {
				map[x][y] = new Node(3,x,y);
			}
		}
		reset();
	}
	public void deleteMap() {//delete map
		for(int x = 0; x < cells; x++) {
			for(int y = 0; y < cells; y++) {
				Node current = map[x][y];
				if(current.getType() == 4 || current.getType() == 5)
					map[x][y] = new Node(3,x,y);
			}
		}
		if(start_x > -1 && start_y > -1) {
			map[start_x][start_y] = new Node(0, start_x, start_y);
			map[start_x][start_y].setHops(0);
		}
		if(finish_x > -1 && finish_y > -1)
			map[finish_x][finish_y] = new Node(1, finish_x, finish_y);
		reset();
	}
	 Dijkstra() {
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(850,650);
		this.setTitle("Dijkstra Algorithm");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("src/logo/logo.png");
		this.setIconImage(icon.getImage());

		toolP.setBorder(BorderFactory.createTitledBorder(loweredetched,"Control Panel"));
		int space = 25;
		int buff = 45;

		toolP.setLayout(null);
		toolP.setBounds(10,10,210,600);
		toolP.setBackground(Color.gray);

		searchButton.setBounds(40,space, 120, 25);
		searchButton.setFocusable(false);
		searchButton.setBackground(new Color(192,192,192));
		toolP.add(searchButton);
		space+=buff;

		resetButton.setBounds(40,space,120,25);
		resetButton.setFocusable(false);
		resetButton.setBackground(new Color(192,192,192));
		toolP.add(resetButton);
		space+=buff;

		genMapButton.setBounds(40,space, 120, 25);
		genMapButton.setFocusable(false);
		genMapButton.setBackground(new Color(192,192,192));
		toolP.add(genMapButton);
		space+=buff;

		deleteMapButton.setBounds(40,space, 120, 25);
		deleteMapButton.setFocusable(false);
		deleteMapButton.setBackground(new Color(192,192,192));
		toolP.add(deleteMapButton);
		space+=40;

		algorithm.setBounds(40,space,120,25);
		algorithm.setBackground(new Color(192,192,192));
		toolP.add(algorithm);
		space+=25;

		toolBox.setBounds(40,space,120,25);
		toolBox.setBackground(new Color(192,192,192));
		toolP.add(toolBox);
		space+=25;

		toolBx.setBounds(40,space,120,25);
		toolBx.setBackground(new Color(192,192,192));
		toolP.add(toolBx);
		space+=buff;

		sizeL.setBounds(15,space,40,25);
		toolP.add(sizeL);
		size.setMajorTickSpacing(10);
		size.setBounds(50,space,100,25);
		toolP.add(size);
		cellsL.setBounds(160,space,40,25);
		toolP.add(cellsL);
		space+=buff;

		delayL.setBounds(15,space,50,25);
		toolP.add(delayL);
		speed.setMajorTickSpacing(5);
		speed.setBounds(50,space,100,25);
		toolP.add(speed);
		msL.setBounds(160,space,40,25);
		toolP.add(msL);
		space+=buff;

		obstacleL.setBounds(15,space,100,25);
		toolP.add(obstacleL);
		obstacles.setMajorTickSpacing(5);
		obstacles.setBounds(50,space,100,25);
		toolP.add(obstacles);
		densityL.setBounds(160,space,100,25);
		toolP.add(densityL);
		space+=buff;

		boxesCheck.setBounds(15,space,100,25);
		toolP.add(boxesCheck);
		space+=buff;

		lengthL.setBounds(15,space,100,25);
		toolP.add(lengthL);

		canvas = new Map();
		canvas.setBounds(230, 10, m_size +1, m_size +1);

		this.add(canvas);
		this.add(toolP);

		 //Action Listeners
		 searchButton.addActionListener(e -> {
			 reset();
			 if((start_x > -1 && start_y > -1) && (finish_x > -1 && finish_y > -1))
				 solving = true;
		 });
		resetButton.addActionListener(e -> {
			deleteMap();
			Update();
		});
		genMapButton.addActionListener(e -> {
			generateMap();
			Update();
		});
		deleteMapButton.addActionListener(e -> {
			clearMap();
			Update();
		});
		algorithmsBx.addItemListener(e -> {
			curAlg = algorithmsBx.getSelectedIndex();
			Update();
		});
		toolBx.addItemListener(e -> tool = toolBx.getSelectedIndex());
		size.addChangeListener(e -> {
			cells = size.getValue()*10;
			clearMap();
			reset();
			Update();
		});
		speed.addChangeListener(e -> {
			delay = speed.getValue();
			Update();
		});
		obstacles.addChangeListener(e -> {
			dense = (double)obstacles.getValue()/100;
			Update();
		});

		clearMap();
		startSearch();
	}
	public void startSearch() {
		if(solving) {
			switch(curAlg) {
				case 0:
					Alg.Dijkstra();
					break;
			}
		}
		pause();
	}
	public void pause() {
		int i = 0;
		while(!solving) {
			i++;
			if(i > 500)
				i = 0;
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
		}
		startSearch();
	}

	public void Update() {
		density = (cells*cells)*dense;
		c_size = m_size /cells;
		canvas.repaint();
		cellsL.setText(cells+"x"+cells);
		msL.setText(delay+"ms");
		lengthL.setText("Path Length: "+length);
		densityL.setText(obstacles.getValue()+"%");
		boxesCheck.setText("Checks: "+checks);
	}
	public void reset() {
		solving = false;
		length = 0;
		checks = 0;
	}
	public void delay() {
		try {
			Thread.sleep(delay);
		} catch(Exception e) {}
	}
	class Map extends JPanel implements MouseListener, MouseMotionListener{	//MAP CLASS

		public Map() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		public void paintComponent(Graphics g) {//repaint
			super.paintComponent(g);
			for(int x = 0; x < cells; x++) {
				for(int y = 0; y < cells; y++) {
					switch (map[x][y].getType()) {
						case 0 -> g.setColor(Color.GREEN);
						case 1 -> g.setColor(Color.RED);
						case 2 -> g.setColor(Color.BLACK);
						case 3 -> g.setColor(Color.WHITE);
						case 4 -> g.setColor(Color.CYAN);
						case 5 -> g.setColor(Color.YELLOW);
					}
					g.fillRect(x* c_size,y* c_size, c_size, c_size);
					g.setColor(Color.BLACK);
					g.drawRect(x* c_size,y* c_size, c_size, c_size);
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				int x = e.getX()/ c_size;
				int y = e.getY()/ c_size;
				Node current = map[x][y];
				if((tool == 2 || tool == 3) && (current.getType() != 0 && current.getType() != 1))
					current.setType(tool);
				Update();
			} catch(Exception z) {}
		}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			deleteMap();
			try {
				int x = e.getX()/ c_size;
				int y = e.getY()/ c_size;
				Node current = map[x][y];
				switch(tool ) {
					case 0: {
						if(current.getType()!=2) {
							if(start_x > -1 && start_y > -1) {
								map[start_x][start_y].setType(3);
								map[start_x][start_y].setHops(-1);
							}
							current.setHops(0);
							start_x = x;
							start_y = y;
							current.setType(0);
						}
						break;
					}
					case 1: {
						if(current.getType()!=2) {
							if(finish_x > -1 && finish_y > -1)
								map[finish_x][finish_y].setType(3);
							finish_x = x;
							finish_y = y;
							current.setType(1);
						}
						break;
					}
					default:
						if(current.getType() != 0 && current.getType() != 1)
							current.setType(tool);
						break;
				}
				Update();
			} catch(Exception z) {}
		}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	class Algorithm {	//dijkstra class
		public void Dijkstra() {
			ArrayList<Node> priority = new ArrayList<>();
			priority.add(map[start_x][start_y]);
			while(solving) {
				if(priority.size() <= 0) {
					solving = false;
					break;
				}
				int hops = priority.get(0).getHops()+1;
				ArrayList<Node> explored = exploreNeighbors(priority.get(0), hops);
				if(explored.size() > 0) {
					priority.remove(0);
					priority.addAll(explored);
					Update();
					delay();
				} else {
					priority.remove(0);
				}
			}
		}

		public ArrayList<Node> exploreNeighbors(Node current, int hops) {
			ArrayList<Node> explored = new ArrayList<>();
			for(int a = -1; a <= 1; a++) {
				for(int b = -1; b <= 1; b++) {
					int xbound = current.getX()+a;
					int ybound = current.getY()+b;
					if((xbound > -1 && xbound < cells) && (ybound > -1 && ybound < cells)) {
						Node neighbor = map[xbound][ybound];
						if((neighbor.getHops()==-1 || neighbor.getHops() > hops) && neighbor.getType()!=2) {
							explore(neighbor, current.getX(), current.getY(), hops);
							explored.add(neighbor);
						}
					}
				}
			}
			return explored;
		}
		public void explore(Node current, int lastx, int lasty, int hops) {
			if(current.getType()!=0 && current.getType() != 1)
				current.setType(4);
			current.setLastNode(lastx, lasty);
			current.setHops(hops);
			checks++;
			if(current.getType() == 1) {
				backtrack(current.getLastX(), current.getLastY(),hops);
			}
		}
		public void backtrack(int lx, int ly, int hops) {
			length = hops;
			while(hops > 1) {
				Node current = map[lx][ly];
				current.setType(5);
				lx = current.getLastX();
				ly = current.getLastY();
				hops--;
			}
			solving = false;
		}
	}
	class Node {
		public int cellType;
		public int hops;
		public int x;
		public int y;
		public int lastX;
		public int lastY;

		public Node(int type, int x, int y) {
			cellType = type;
			this.x = x;
			this.y = y;
			hops = -1;
		}
		public int getX() {return x;}
		public int getY() {return y;}
		public int getLastX() {return lastX;}
		public int getLastY() {return lastY;}
		public int getType() {return cellType;}
		public int getHops() {return hops;}
		public void setType(int type) {cellType = type;}
		public void setLastNode(int x, int y) {lastX = x; lastY = y;}
		public void setHops(int hops) {this.hops = hops;}
	}
}
