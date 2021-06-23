package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.TerminalConnect4.*;
public class GUI extends JFrame implements ActionListener{
	private JPanel menu;
	private GUIGame game; 
	private CardLayout card = new CardLayout();
	private JButton b;
	
	public GUI() {
		//setLayout(card);
		
		game = new GUIGame();
		/*
		menu = new JPanel();
		menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
		
		menu.add(new JLabel("Welcome To Connect 4"));
		b = new JButton("Start Game");
		b.addActionListener(this);
		menu.add(b);
		
		
		
		add(menu, "menu");
		*/
		//add(game, "game");
		add(game);
		
	    //setSize(475,475);
		setMinimumSize(new Dimension(400,400));
		setSize(600,600);
	    //setResizable(false);
	    setVisible(true);
	    setTitle("Connect 4");
	    setIconImage(new ImageIcon("Connect4Assets/icon.png").getImage());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void actionPerformed(ActionEvent e) {
		card.show(this,"game");
	}
	
	
}

class GUIGame extends JPanel{
	private GUIGameDetails d;
	private GUIGameGrid g;
	private Board board;
	
	
	GUIGame(){
		super(new BorderLayout());
		
		
		this.board = new Board();
		d = new GUIGameDetails();
		g = new GUIGameGrid(board);
		
		add(d, BorderLayout.NORTH);
		add(g, BorderLayout.CENTER);
		

		
	}
	
}

class GUIGameDetails extends JPanel{
	private static JLabel l;
	
	GUIGameDetails(){
		l = new JLabel("Yellow's Turn");
		add(l);
	}
	
	public static void updateLabel(boolean isRunning, boolean turn) {
		if (!isRunning) {
			l.setText((!turn ? "Red" : "Yellow") + " Won!");
		}
		else {
			l.setText((turn ? "Red" : "Yellow") + "'s Turn" );
		}
	}
}

class GUIGameGrid extends JPanel{
	private JButton[] buttons;
	private JLabel[][] grid;
	private int lastClicked = -1;
	private int turnCount = 0;
	private int turnCountLast = 0;
	private Board board;
	private boolean player;

	  GUIGameGrid(Board board){
		this.board = board;
	    buttons = new JButton[7];
	    for (int i = 0; i < buttons.length; i++){
	      ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("arrow.png")); // load the image to a imageIcon
	        Image image = imageIcon.getImage(); // transform it 
	        Image newimg = image.getScaledInstance(55,55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	      ImageIcon newImageIcon = new ImageIcon(newimg);
	      buttons[i] = new JButton(new StretchIcon(newimg));
	      //buttons[i].setPreferredSize(new Dimension(newImageIcon.getIconWidth(),newImageIcon.getIconHeight()));
	      buttons[i].addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	          int index = 0;
	          for (int whatEver = 0; whatEver < buttons.length; whatEver++){
	            if(e.getSource() == buttons[whatEver]){
	              index = whatEver;
	              break;
	            }
	          }
	          if(board.isRunning()){
	            board.dropthechild((player?1:2),index);
	            board.theySayChildrenAreTheChickenOfTheOrphanarium();
	            player = !player;
	            updateBoard();
	            GUIGameDetails.updateLabel(board.isRunning(), player);
	          }

	        }
	      }
	      );
	    }



	    for (JButton b : buttons){
	      add(b);
	    }

	    grid = new JLabel[6][7];

	    for (int r = 0; r < grid.length; r ++){
	      for (int c = 0; c < grid[r].length; c++){
	        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("empty.png")); // load the image to a imageIcon
	        Image image = imageIcon.getImage(); // transform it 
	        Image newimg = image.getScaledInstance(55,55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	        grid[r][c] = new JLabel(new StretchIcon(newimg));
	        add(grid[r][c]);
	      }
	    }

	    
	    setLayout(new GridLayout(7,7));
	    //setSize(245,266);


	    /*
	    DisplayGraphics g = new DisplayGraphics();
	    this.add(g);
	    */
	  }

	  public void updateBoard(){
	    String[] conversions = {"empty.png", "red.png", "yellow.png"};
	    int[][] boardBoard = board.getBoard();
	    for (int r = 0; r < grid.length; r++){
	      for(int c = 0; c < grid[r].length; c++){
	        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(conversions[boardBoard[r][c]])); // load the image to a imageIcon
	        Image image = imageIcon.getImage(); // transform it 
	        Image newimg = image.getScaledInstance(55,55,  java.awt.Image.SCALE_SMOOTH);
	        grid[r][c].setIcon(new StretchIcon(newimg));
	      }
	    }
	  }
	  public void end(){
	      //The use of !player is because the check win function runs at the very end of the turn and so pressing the button has already changed it to the next player (the loser) 's turn'
	      board.setIsRunning(false);
	  }
}