package bigFourWinds;

import javax.swing.JPanel;
import java.awt.*;

/**
 * The ScreenContainerPanel is the panel that holds the various screens.
 * It is in the upper-right, is 480x600, and uses CardLayout.
 * 
 * @author David Wu
 * @version 1/16/18
 */
public class SimScreenPanel extends JPanel {
	
	private MahjongPanel mahjongP;
	
	private boolean showRoll1;
	private boolean showRoll2;
	private int[] diceRoll1;
	private int[] diceRoll2;
	
	private boolean showDealer;
	private String currentDealer;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	
	public SimScreenPanel(MahjongPanel mP){
		mahjongP = mP;
		
		setLocation(800,0);
		setSize(480,600);
		setBackground(Color.WHITE);		
		
		showRoll1 = false;
		showRoll2 = false;
		diceRoll1 = new int[2];
		diceRoll2 = new int[2];
		
		showDealer = true;
		currentDealer = "A";
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);	
		
		//draw borders
		g.drawLine(0,0,0,600);
		g.drawLine(479,0,479,600);
		
		//show dice rolls
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,36));
		g.setColor(Color.BLUE);
		if (showRoll1){
			g.drawString("Roll #1: " + diceRoll1[0] + " - " + diceRoll1[1], 80, 80);
		}
		if (showRoll2){
			g.drawString("Roll #2: " + diceRoll2[0] + " - " + diceRoll2[1], 80, 160);
		}
		if (showDealer){
			g.drawString("Current Dealer: " + currentDealer, 80, 240);
		}
		
		//Display the Jiang
		Image mahjongTileImage = mahjongP.getDisplayPanel().getMahjongTileImage();
		Tile[] jiang = determineJiang();
		if (jiang == null) return;
		
		if (showRoll2) {			
			Tile t0 = jiang[0];
			g.drawImage(mahjongTileImage, 80, 300, 107, 339, t0.getPicX()*Tile.WIDTH, t0.getPicY()*Tile.HEIGHT, 
					(t0.getPicX()+1)*Tile.WIDTH, (t0.getPicY()+1)*Tile.HEIGHT, null);
			Tile t1 = jiang[1];
			g.drawImage(mahjongTileImage, 110, 300, 137, 339, t1.getPicX()*Tile.WIDTH, t1.getPicY()*Tile.HEIGHT, 
					(t1.getPicX()+1)*Tile.WIDTH, (t1.getPicY()+1)*Tile.HEIGHT, null);
			Tile t2 = jiang[2];
			g.drawImage(mahjongTileImage, 140, 300, 167, 339, t2.getPicX()*Tile.WIDTH, t2.getPicY()*Tile.HEIGHT, 
					(t2.getPicX()+1)*Tile.WIDTH, (t2.getPicY()+1)*Tile.HEIGHT, null);
			Tile t3 = jiang[3];
			g.drawImage(mahjongTileImage, 170, 300, 197, 339, t3.getPicX()*Tile.WIDTH, t3.getPicY()*Tile.HEIGHT, 
					(t3.getPicX()+1)*Tile.WIDTH, (t3.getPicY()+1)*Tile.HEIGHT, null);
		}
	}

	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}
	
	public int[] rollDice1(){
		diceRoll1[0] = (int)(Math.random()*6 + 1);
		diceRoll1[1] = (int)(Math.random()*6 + 1);
		showRoll1 = true;
		repaint();		
		return diceRoll1;
	}
	public int[] rollDice2(){
		diceRoll2[0] = (int)(Math.random()*6 + 1);
		diceRoll2[1] = (int)(Math.random()*6 + 1);
		showRoll2 = true;
		repaint();
		return diceRoll2;
	}
	
	public void showhideRolls(){
		showRoll1 = showRoll2 = ! (showRoll1 || showRoll2);
		repaint();
	}
	
	public String getCurrentDealer(){
		return currentDealer;
	}
	
	public void showhideDealer(){
		showDealer = ! showDealer;
		repaint();
	}
	
	public void passDealer(){
		switch (currentDealer){
			case "A":
				currentDealer = "B";
				break;
			case "B":
				currentDealer = "C";
				break;
			case "C":
				currentDealer = "D";
				break;
			case "D":
				currentDealer = "A";
				break;					
		}
		repaint();
	}
	
	public Tile[] determineJiang() {
		if (diceRoll2[0] == 0 || diceRoll2[1] == 0) return null;
		
		Tile[] jiang = new Tile[4];
		
		int[] roll = new int[2];
		if (diceRoll2[0] < diceRoll2[1]) {
			roll[0] = diceRoll2[1];
			roll[1] = diceRoll2[0];
		}else {
			roll[0] = diceRoll2[0];
			roll[1] = diceRoll2[1];
		}
		
		if (roll[0] == 6 && roll[1] == 5) {
			jiang[0] = new Tile("BlueDragon",0); //not an actual Tile, copyNo = 0
			jiang[1] = new Tile("2Dot",0);
			jiang[2] = new Tile("8Dot",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 2 && roll[1] == 1) {
			jiang[0] = new Tile("GreenDragon",0); //not an actual Tile, copyNo = 0
			jiang[1] = new Tile("2Bamboo",0);
			jiang[2] = new Tile("8Bamboo",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 4 && roll[1] == 3) {
			jiang[0] = new Tile("RedDragon",0); //not an actual Tile, copyNo = 0
			jiang[1] = new Tile("2Character",0);
			jiang[2] = new Tile("8Character",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 6 && roll[1] == 6) {
			jiang[0] = new Tile("5Dot",0);
			jiang[1] = new Tile("2Bamboo",0);
			jiang[2] = new Tile("8Character",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 1 && roll[1] == 1) {
			jiang[0] = new Tile("5Dot",0);
			jiang[1] = new Tile("2Character",0);
			jiang[2] = new Tile("8Bamboo",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 5 && roll[1] == 5) {
			jiang[0] = new Tile("5Bamboo",0);
			jiang[1] = new Tile("2Dot",0);
			jiang[2] = new Tile("8Character",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 2 && roll[1] == 2) {
			jiang[0] = new Tile("5Bamboo",0);
			jiang[1] = new Tile("2Character",0);
			jiang[2] = new Tile("8Dot",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 4 && roll[1] == 4) {
			jiang[0] = new Tile("5Character",0);
			jiang[1] = new Tile("2Dot",0);
			jiang[2] = new Tile("8Bamboo",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 3 && roll[1] == 3) {
			jiang[0] = new Tile("5Character",0);
			jiang[1] = new Tile("2Bamboo",0);
			jiang[2] = new Tile("8Dot",0);
			jiang[3] = new Tile("EastWind",0);
			return jiang;
		}
		
		if (roll[0] == 6) {
			jiang[0] = new Tile("BlueDragon",0);
			if (roll[1] % 2 == 1) {
				jiang[1] = new Tile("2Character",0);
				jiang[2] = new Tile("8Bamboo",0);
			}else {
				jiang[1] = new Tile("2Bamboo",0);
				jiang[2] = new Tile("8Character",0);
			}
			if (roll[0] + roll[1] == 9 || roll[0] + roll[1] == 10) {
				jiang[3] = new Tile("EastWind",0);
			}else {
				jiang[3] = new Tile("5Dot",0);
			}
		}else if (roll[0] == 5) {
			jiang[0] = new Tile("GreenDragon",0);
			if (roll[1] % 2 == 1) {
				jiang[1] = new Tile("2Dot",0);
				jiang[2] = new Tile("8Character",0);
			}else {
				jiang[1] = new Tile("2Character",0);
				jiang[2] = new Tile("8Dot",0);
			}
			if (roll[0] + roll[1] == 6 || roll[0] + roll[1] == 9) {
				jiang[3] = new Tile("EastWind",0);
			}else {
				jiang[3] = new Tile("5Bamboo",0);
			}
		}else if (roll[0] == 4 || roll[0] == 3) {
			jiang[0] = new Tile("RedDragon",0);
			if (roll[1] % 2 == 1) {
				jiang[1] = new Tile("2Bamboo",0);
				jiang[2] = new Tile("8Dot",0);
			}else {
				jiang[1] = new Tile("2Dot",0);
				jiang[2] = new Tile("8Bamboo",0);
			}
			if (roll[0] + roll[1] == 4 || roll[0] + roll[1] == 6) {
				jiang[3] = new Tile("EastWind",0);
			}else {
				jiang[3] = new Tile("5Character",0);
			}
		}
		return jiang;
	}
}