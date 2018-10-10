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
public class ScreenContainerPanel extends JPanel {
	
	private MahjongPanel mahjongP;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	private CardLayout layman;
	//TODO declare the screens
	
	public ScreenContainerPanel(MahjongPanel mP){
		mahjongP = mP;
		
		setLocation(800,0);
		setSize(480,600);
		setBackground(Color.WHITE);
		
		layman = new CardLayout();
		setLayout(layman);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draw borders
		g.drawLine(0,0,0,600);
	}

	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}
}
