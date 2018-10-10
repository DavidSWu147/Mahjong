package bigFourWinds;

import javax.swing.*;
import java.awt.*;

/**
 * The MahjongPanel class is the container panel.
 * It contains DisplayPanel, ScreenContainerPanel, and a panel with controls.
 * 
 * @author David Wu
 * @version 1/16/18
 */
public class MahjongPanel extends JPanel {
	
	private MahjongFrame mahjongF;
	
	private DisplayPanel displayP;
	
	/*
	private ScreenContainerPanel containerP;
	private ControlPanel controlP;
	*/
	private SimScreenPanel sScreenP;
	private SimControlPanel sControlP;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MahjongPanel(MahjongFrame mF){
		mahjongF = mF;
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		displayP = new DisplayPanel(this);
		/*
		containerP = new ScreenContainerPanel(this);
		controlP = new ControlPanel(this);
		*/
		sScreenP = new SimScreenPanel(this);
		sControlP = new SimControlPanel(this);
		
		add(displayP);
		/*
		add(containerP);
		add(controlP);
		*/
		add(sScreenP);
		add(sControlP);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	public MahjongFrame getMahjongFrame() {
		return mahjongF;
	}
	public DisplayPanel getDisplayPanel() {
		return displayP;
	}
	/*
	public ScreenContainerPanel getContainerPanel() {
		return containerP;
	}
	public ControlPanel getControlPanel() {
		return controlP;
	}
	*/
	public SimScreenPanel getSScreenPanel(){
		return sScreenP;
	}
	public SimControlPanel getSControlPanel(){
		return sControlP;
	}
}
