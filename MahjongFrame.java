package bigFourWinds;

import javax.swing.*;

import java.awt.*;

/**
 * The MahjongFrame class is the frame for a mahjong game.
 * 
 * @author David Wu
 * @version 1/16/18
 */
public class MahjongFrame extends JFrame {
	
	private MahjongSim masterClass_MS;
	//private Big4WindsZungJung masterClass_B4WZJ;
	private MahjongPanel mahjongP;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	
	public MahjongFrame(Object mC){
		super("Mahjong");
		
		if (mC instanceof MahjongSim){
			masterClass_MS = (MahjongSim)mC;
		}
		
		setLocation(0,0);
		setSize(1280,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setUndecorated(true);
		setResizable(false);
		setBackground(Color.BLACK);
		mahjongP = new MahjongPanel(this);
		setContentPane(mahjongP);
		setVisible(true);
	}

	public MahjongSim getMasterClass_MS() {
		return masterClass_MS;
	}
	
	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}
}
