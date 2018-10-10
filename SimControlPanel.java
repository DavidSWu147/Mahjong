package bigFourWinds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

public class SimControlPanel extends JPanel implements ActionListener{
	
	private MahjongPanel mahjongP;
	
	private JButton resetButton, shuffleButton, dealButton, arrangeHandsButton, startGameButton;
	private JButton passDealerButton, showhideDealerButton, showhideRollButton;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	public SimControlPanel(MahjongPanel mP){
		mahjongP = mP;
		
		setLocation(800,600);
		setSize(480,200);
		setBackground(Color.WHITE);
		
		resetButton = new JButton("Reset");
		shuffleButton = new JButton("Shuffle");
		dealButton = new JButton("Deal");
		arrangeHandsButton = new JButton("Arrange");
		startGameButton = new JButton("Start Game");
		
		passDealerButton = new JButton("Pass Dealer");
		showhideDealerButton = new JButton("Show/Hide Dealer");
		showhideRollButton = new JButton("Show/Hide Roll");
		
		add(resetButton);
		add(shuffleButton);
		add(dealButton);
		add(arrangeHandsButton);
		add(startGameButton);
		
		add(passDealerButton);
		add(showhideDealerButton);
		add(showhideRollButton);
		
		resetButton.addActionListener(this);
		shuffleButton.addActionListener(this);
		dealButton.addActionListener(this);
		arrangeHandsButton.addActionListener(this);
		startGameButton.addActionListener(this);
		
		passDealerButton.addActionListener(this);
		showhideDealerButton.addActionListener(this);
		showhideRollButton.addActionListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draw borders
		g.drawLine(0,0,0,200);
		g.drawLine(0,0,480,0);
		g.drawLine(479,0,479,200);
	}

	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}
	public Tabletop getTabletop(){
		return mahjongP.getMahjongFrame().getMasterClass_MS().getTabletop();
	}
	
	public void actionPerformed(ActionEvent evt){
		Tabletop table = getTabletop();
		
		if (evt.getSource() == resetButton)
			table.resetToOrder();
		else if (evt.getSource() == shuffleButton)
			table.shuffleWalls();
		else if (evt.getSource() == dealButton)
			table.deal();
		else if (evt.getSource() == arrangeHandsButton)
			table.arrangeAllHands();
		else if (evt.getSource() == startGameButton){
			table.resetToOrder();
			table.shuffleWalls();
			table.deal();
			table.arrangeAllHands();			
		}else if (evt.getSource() == passDealerButton)
			getMahjongPanel().getSScreenPanel().passDealer();
		else if (evt.getSource() == showhideDealerButton)
			getMahjongPanel().getSScreenPanel().showhideDealer();
		else if (evt.getSource() == showhideRollButton)
			getMahjongPanel().getSScreenPanel().showhideRolls();
		
		
		
		getMahjongPanel().getDisplayPanel().repaint();
	}
}