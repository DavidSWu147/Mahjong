package bigFourWinds;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * The ControlPanel is the panel with all of the controls for playing the game.
 * User interaction should be mainly through this panel.
 * The only exceptions are allowing the user to click on the tiles directly 
 * in the display panel or allowing the user to click directly on certain
 * objects in one of the screens.
 * 
 * @author David Wu
 * @version 1/16/18
 */
public class ControlPanel extends JPanel implements MouseListener, KeyListener, FocusListener{
	
	private MahjongPanel mahjongP;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	public ControlPanel(MahjongPanel mP){
		mahjongP = mP;
		
		setLocation(800,600);
		setSize(480,200);
		setBackground(Color.WHITE);
		
		addMouseListener(this);
		addKeyListener(this);
		addFocusListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draw borders
		g.drawLine(0,0,0,200);
		g.drawLine(0,0,480,0);
	}

	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}

	
	public void focusGained(FocusEvent evt) {}	
	public void focusLost(FocusEvent evt) {}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyReleased(KeyEvent evt) {}	
	public void keyTyped(KeyEvent evt) {}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		requestFocus();
	}
	public void mouseExited(MouseEvent evt) {
		// TODO Auto-generated method stub
	}
	
	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
}
