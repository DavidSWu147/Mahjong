package bigFourWinds;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * The DisplayPanel is the panel used to display the state of the game.
 * It is located to the left and is 800x800. 
 * 
 * @author David Wu
 * @version 1/16/18
 * 
 * Update 7/31/18 Made the colors in the center correspond to seat positions,
 * East = GREEN etc.
 */
public class DisplayPanel extends JPanel implements MouseListener {
	
	private MahjongPanel mahjongP;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public static final String PATHWAY = "C:/Users/Bdavi/eclipse-workspace/DavidSWu/Mahjong/bigFourWinds/";
	public static final String JPEG_EXTENSION = ".jpg";
	public static final String PNG_EXTENSION = ".png";
	private static final String[] PICTURE_NAMES = {"MahjongTiles","SymbolAvatars"};
	private static final String[] ROTATION_NAMES = {"","Rotate90CC","Rotate180","Rotate90CW"};
	private final int VS = -16;
	private final boolean USE_PNG = true;
	
	private Image[] mahjongTileImages;
	private Image[] symbolAvatarImages;
	
	private boolean drawBorders;
	private Slot selectedSlot;
	
	public DisplayPanel(MahjongPanel mP){
		mahjongP = mP;
		
		setLocation(0,0);
		setSize(800,800);
		setBackground(new Color(192,255,192));
		
		drawBorders = true;
		selectedSlot = null;
		
		readImages();
	}
	
	public final void activateMouseListener(){
		addMouseListener(this); 
	}
	
	private final void readImages(){
		mahjongTileImages = new Image[4];
		symbolAvatarImages = new Image[4];
		
		final String EXT = USE_PNG ? PNG_EXTENSION : JPEG_EXTENSION;
		
		for (int i = 0; i < 4; i++){
			File imageFile = new File(PATHWAY + PICTURE_NAMES[0] + ROTATION_NAMES[i] + EXT);
			try{
				mahjongTileImages[i] = ImageIO.read(imageFile);
			}catch (IOException e){
				System.err.println("ERROR: Could not locate image: " + 
					PATHWAY + PICTURE_NAMES[0] + ROTATION_NAMES[i] + EXT);
				System.exit(1);
			}
		}
		
		for (int i = 0; i < 4; i++){
			File imageFile = new File(PATHWAY + PICTURE_NAMES[1] + ROTATION_NAMES[i] + JPEG_EXTENSION);
			try{
				symbolAvatarImages[i] = ImageIO.read(imageFile);
			}catch (IOException e){
				System.err.println("ERROR: Could not locate image: " + 
					PATHWAY + PICTURE_NAMES[1] + ROTATION_NAMES[i] + JPEG_EXTENSION);
				System.exit(1);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Tabletop table = getTabletop();
		
		SlotArea[] allAreas = table.getAllAreas();
		
		if (drawBorders){
			for (SlotArea area : allAreas){
				drawSlotArea(g,area);
			}
		}		
		
		for (SlotArea area : allAreas){
			drawSlotTiles(g,area);
		}
		
		if (selectedSlot != null){
			drawSelected(g,selectedSlot);
		}
		
		/*
		for (int x = 129; x < 587; x += Tile.WIDTH){
			for (int y = 593; y < 670; y += Tile.HEIGHT)
				g.drawImage(mahjongTileImages[0],x,y+VS,x+Tile.WIDTH,y+Tile.HEIGHT+VS,
						8*Tile.WIDTH,3*Tile.HEIGHT,(8+1)*Tile.WIDTH,(3+1)*Tile.HEIGHT,null);
		}
		for (int z = 593; z < 670; z += Tile.HEIGHT){
			for (int w = 212; w < 670; w += Tile.WIDTH){
				g.drawImage(mahjongTileImages[1], z, w+VS, z+Tile.HEIGHT, w+Tile.WIDTH+VS,
					3*Tile.HEIGHT, 8*Tile.WIDTH, (3+1)*Tile.HEIGHT, (8+1)*Tile.WIDTH, null);
			}
		}
		for (int x = 212; x < 670; x += Tile.WIDTH){
			for (int y = 129; y < 206; y += Tile.HEIGHT){
				g.drawImage(mahjongTileImages[2],x,y+VS,x+Tile.WIDTH,y+Tile.HEIGHT+VS,
						8*Tile.WIDTH,3*Tile.HEIGHT,(8+1)*Tile.WIDTH,(3+1)*Tile.HEIGHT,null);
			}
		}
		for (int z = 129; z < 206; z += Tile.HEIGHT){
			for (int w = 129; w < 587; w += Tile.WIDTH){
				g.drawImage(mahjongTileImages[3],z,w+VS,z+Tile.HEIGHT,w+Tile.WIDTH+VS,
					3*Tile.HEIGHT, 8*Tile.WIDTH, (3+1)*Tile.HEIGHT, (8+1)*Tile.WIDTH, null);
			}
		}
		g.drawImage(symbolAvatarImages[0],30,690+VS,110,770+VS,0,0,80,80,null);
		g.drawImage(symbolAvatarImages[1],690,689+VS,770,769+VS,80,0,160,80,null);
		g.drawImage(symbolAvatarImages[2],689,29+VS,769,109+VS,160,0,240,80,null);
		g.drawImage(symbolAvatarImages[3],29,30+VS,109,110+VS,240,0,320,80,null);
		*/
		
		String currentDealer = mahjongP.getSScreenPanel().getCurrentDealer();
		Color[] colors = {Color.BLACK,null,null,null,null};
		switch (currentDealer) {
			case "A":
				colors[1] = Color.GREEN;
				colors[2] = Color.RED;
				colors[3] = Color.YELLOW;
				colors[4] = Color.BLUE;
				break;
			case "B":
				colors[1] = Color.BLUE;
				colors[2] = Color.GREEN;
				colors[3] = Color.RED;
				colors[4] = Color.YELLOW;
				break;
			case "C":
				colors[1] = Color.YELLOW;
				colors[2] = Color.BLUE;
				colors[3] = Color.GREEN;
				colors[4] = Color.RED;
				break;
			case "D":
				colors[1] = Color.RED;
				colors[2] = Color.YELLOW;
				colors[3] = Color.BLUE;
				colors[4] = Color.GREEN;
				break;
		}
		
		int n = 4;
		g.setColor(colors[1]);
		int[] xCoord3 = {375,395,404,424};
		int[] yCoord3 = {424+VS,404+VS,404+VS,424+VS};
		g.fillPolygon(xCoord3,yCoord3,n);
		g.setColor(colors[2]);
		int[] xCoord4 = {424,404,404,424};
		int[] yCoord4 = {424+VS,404+VS,395+VS,375+VS};
		g.fillPolygon(xCoord4,yCoord4,n);
		g.setColor(colors[3]);
		int[] xCoord1 = {424,404,395,375};
		int[] yCoord1 = {375+VS,395+VS,395+VS,375+VS};		
		g.fillPolygon(xCoord1,yCoord1,n);
		g.setColor(colors[4]);
		int[] xCoord2 = {375,395,395,375};
		int[] yCoord2 = {375+VS,395+VS,404+VS,424+VS};
		g.fillPolygon(xCoord2,yCoord2,n);
		
		g.setColor(colors[0]);
		g.drawRect(374,374+VS,50,50);
		g.fillRect(395,395+VS,9,9);
		
	}
	
	private void drawSlotArea(Graphics g, SlotArea area){
		g.setColor(new Color(128,255,128));
		int[] xArr = {area.getLeftBound(),area.getLeftBound(),area.getRightBound(),area.getRightBound()};
		int[] yArr = {area.getTopBound()+VS,area.getBottomBound()+VS,area.getBottomBound()+VS,area.getTopBound()+VS};
		g.drawPolygon(xArr,yArr,4);
	}
	
	private void drawSlotTiles(Graphics g, SlotArea area){
		if (area instanceof MeldSlotArea){
			MeldSlotArea areaM = (MeldSlotArea)area;
			for (int r = 0; r < areaM.getNumRows(); r++){
				for (int c = 0; c < areaM.getNumCols(); c++){
					Slot cS = areaM.getSlot(r,c);
					if (cS != null && cS.containsTile()){
						Tile cT = cS.getCurrentTile();
						drawTile(g,cS,cT);
					}
				}
			}
			return;
		}
		
		for (int r = 0; r < area.getNumRows(); r++){
			for (int c = 0; c < area.getNumCols(); c++){
				Slot cS = area.getSlot(r,c);
				if (cS.containsTile()){
					Tile cT = cS.getCurrentTile();
					drawTile(g,cS,cT);
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void drawSlot(Graphics g, Slot cS){
		g.setColor(new Color(160,255,160));
		
		if (cS instanceof MeldSlot){
			MeldSlot cMS = (MeldSlot)cS;
			switch (cMS.getCurrentPosition()){
				case 1:
					int[] xArr = {cMS.getLeftBound(),cMS.getLeftBound(),cMS.getRightBound(),cMS.getRightBound()};
					int[] yArr = {cMS.getTopBound()+VS,cMS.getBottomBound()+VS,cMS.getBottomBound()+VS,cMS.getTopBound()+VS};
					g.drawPolygon(xArr,yArr,4);
					break;
				case 2:
					int[] xArr2 = {cMS.getLeftBound2(),cMS.getLeftBound2(),cMS.getRightBound2(),cMS.getRightBound2()};
					int[] yArr2 = {cMS.getTopBound2()+VS,cMS.getBottomBound2()+VS,cMS.getBottomBound2()+VS,cMS.getTopBound2()+VS};
					g.drawPolygon(xArr2,yArr2,4);
					break;
				case 3:
					int[] xArr3 = {cMS.getLeftBound3(),cMS.getLeftBound3(),cMS.getRightBound3(),cMS.getRightBound3()};
					int[] yArr3 = {cMS.getTopBound3()+VS,cMS.getBottomBound3()+VS,cMS.getBottomBound3()+VS,cMS.getTopBound3()+VS};
					g.drawPolygon(xArr3,yArr3,4);
					break;
				default: //0
					break;
			}
			return;
		}	
		
		int[] xArr = {cS.getLeftBound(),cS.getLeftBound(),cS.getRightBound(),cS.getRightBound()};
		int[] yArr = {cS.getTopBound()+VS,cS.getBottomBound()+VS,cS.getBottomBound()+VS,cS.getTopBound()+VS};
		g.drawPolygon(xArr,yArr,4);
	}
	
	private void drawSelected(Graphics g, Slot cS){
		g.setColor(Color.RED);
		
		if (cS instanceof MeldSlot){
			MeldSlot cMS = (MeldSlot)cS;
			switch (cMS.getCurrentPosition()){
				case 1:
					int[] xArr = {cMS.getLeftBound(),cMS.getLeftBound(),cMS.getRightBound(),cMS.getRightBound()};
					int[] yArr = {cMS.getTopBound()+VS,cMS.getBottomBound()+VS,cMS.getBottomBound()+VS,cMS.getTopBound()+VS};
					g.drawPolygon(xArr,yArr,4);
					break;
				case 2:
					int[] xArr2 = {cMS.getLeftBound2(),cMS.getLeftBound2(),cMS.getRightBound2(),cMS.getRightBound2()};
					int[] yArr2 = {cMS.getTopBound2()+VS,cMS.getBottomBound2()+VS,cMS.getBottomBound2()+VS,cMS.getTopBound2()+VS};
					g.drawPolygon(xArr2,yArr2,4);
					break;
				case 3:
					int[] xArr3 = {cMS.getLeftBound3(),cMS.getLeftBound3(),cMS.getRightBound3(),cMS.getRightBound3()};
					int[] yArr3 = {cMS.getTopBound3()+VS,cMS.getBottomBound3()+VS,cMS.getBottomBound3()+VS,cMS.getTopBound3()+VS};
					g.drawPolygon(xArr3,yArr3,4);
					break;
				default: //0
					break;
			}
			return;
		}		
		
		int[] xArr = {cS.getLeftBound(),cS.getLeftBound(),cS.getRightBound(),cS.getRightBound()};
		int[] yArr = {cS.getTopBound()+VS,cS.getBottomBound()+VS,cS.getBottomBound()+VS,cS.getTopBound()+VS};
		g.drawPolygon(xArr,yArr,4);
	}
	
	private void drawTile(Graphics g, Slot cS, Tile cT){
		int pX = cS.getFaceDown() ? Tile.FD_PICX : cT.getPicX();
		int pY = cS.getFaceDown() ? Tile.FD_PICY : cT.getPicY();
		
		if (cS instanceof MeldSlot){
			MeldSlot cMS = (MeldSlot)cS;
			if (cMS.getCurrentPosition() == 1){
				switch (cMS.getPrimaryOrientation()){
					case "up":
						g.drawImage(mahjongTileImages[0], cMS.getLeftBound(), cMS.getTopBound()+VS, 
						cMS.getRightBound()+1, cMS.getBottomBound()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "left":
						g.drawImage(mahjongTileImages[1], cMS.getLeftBound(), cMS.getTopBound()+VS, 
						cMS.getRightBound()+1, cMS.getBottomBound()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
					case "down":
						g.drawImage(mahjongTileImages[2], cMS.getLeftBound(), cMS.getTopBound()+VS, 
						cMS.getRightBound()+1, cMS.getBottomBound()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "right":
						g.drawImage(mahjongTileImages[3], cMS.getLeftBound(), cMS.getTopBound()+VS, 
						cMS.getRightBound()+1, cMS.getBottomBound()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
				}
			}else if (cMS.getCurrentPosition() == 2){
				switch (cMS.getSecondaryOrientation()){
					case "up":
						g.drawImage(mahjongTileImages[0], cMS.getLeftBound2(), cMS.getTopBound2()+VS, 
						cMS.getRightBound2()+1, cMS.getBottomBound2()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "left":
						g.drawImage(mahjongTileImages[1], cMS.getLeftBound2(), cMS.getTopBound2()+VS, 
						cMS.getRightBound2()+1, cMS.getBottomBound2()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
					case "down":
						g.drawImage(mahjongTileImages[2], cMS.getLeftBound2(), cMS.getTopBound2()+VS, 
						cMS.getRightBound2()+1, cMS.getBottomBound2()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "right":
						g.drawImage(mahjongTileImages[3], cMS.getLeftBound2(), cMS.getTopBound2()+VS, 
						cMS.getRightBound2()+1, cMS.getBottomBound2()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
				}
			}else if (cMS.getCurrentPosition() == 3){
				switch (cMS.getPrimaryOrientation()){
					case "up":
						g.drawImage(mahjongTileImages[0], cMS.getLeftBound3(), cMS.getTopBound3()+VS, 
						cMS.getRightBound3()+1, cMS.getBottomBound3()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "left":
						g.drawImage(mahjongTileImages[1], cMS.getLeftBound3(), cMS.getTopBound3()+VS, 
						cMS.getRightBound3()+1, cMS.getBottomBound3()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
					case "down":
						g.drawImage(mahjongTileImages[2], cMS.getLeftBound3(), cMS.getTopBound3()+VS, 
						cMS.getRightBound3()+1, cMS.getBottomBound3()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
						(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
						break;
					case "right":
						g.drawImage(mahjongTileImages[3], cMS.getLeftBound3(), cMS.getTopBound3()+VS, 
						cMS.getRightBound3()+1, cMS.getBottomBound3()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
						(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
						break;
				}
			}
			return;
		}
		
		switch (cS.getPrimaryOrientation()){
			case "up":
				g.drawImage(mahjongTileImages[0], cS.getLeftBound(), cS.getTopBound()+VS, 
				cS.getRightBound()+1, cS.getBottomBound()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
				(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
				break;
			case "left":
				g.drawImage(mahjongTileImages[1], cS.getLeftBound(), cS.getTopBound()+VS, 
				cS.getRightBound()+1, cS.getBottomBound()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
				(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
				break;
			case "down":
				g.drawImage(mahjongTileImages[2], cS.getLeftBound(), cS.getTopBound()+VS, 
				cS.getRightBound()+1, cS.getBottomBound()+1+VS, pX*Tile.WIDTH, pY*Tile.HEIGHT, 
				(pX+1)*Tile.WIDTH, (pY+1)*Tile.HEIGHT, null);
				break;
			case "right":
				g.drawImage(mahjongTileImages[3], cS.getLeftBound(), cS.getTopBound()+VS, 
				cS.getRightBound()+1, cS.getBottomBound()+1+VS, pY*Tile.HEIGHT, pX*Tile.WIDTH, 
				(pY+1)*Tile.HEIGHT, (pX+1)*Tile.WIDTH, null);
				break;
		}
	}
	
	public MahjongPanel getMahjongPanel() {
		return mahjongP;
	}
	
	public Tabletop getTabletop(){
		return mahjongP.getMahjongFrame().getMasterClass_MS().getTabletop();
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		int trueX = evt.getX();
		int trueY = evt.getY() - VS;
		SlotArea area = locateSlotArea(trueX,trueY);
		if (area == null){		//if click outside a Slot resets selected status
			if (evt.getButton() == MouseEvent.BUTTON1){
				selectedSlot = null;
				repaint();
			}
			return;
		}
			
		Slot cS;				
		if (area instanceof MeldSlotArea){
			cS = locateMeldSlot((MeldSlotArea)(area),trueX,trueY);
			if (cS == null){
				if (evt.getButton() == MouseEvent.BUTTON1){
					selectedSlot = null;
					repaint();
				}
				return;
			}
		}else{
			cS = locateNormalSlot(area,trueX,trueY);
			if (cS == null){
				if (evt.getButton() == MouseEvent.BUTTON1){
					selectedSlot = null;
					repaint();
				}
				return;
			}
		}
		
		if (evt.getButton() == MouseEvent.BUTTON1){		//left click means move/select
			if (selectedSlot != null){
				if (cS.equals(selectedSlot)){
					selectedSlot = null;
				}else{
					if (! cS.containsTile()){
						getTabletop().move(selectedSlot,cS);
						
						if (selectedSlot instanceof MeldSlot){	//must organize if moved in/out of MeldSlotArea
							MeldSlotArea mArea = (MeldSlotArea)(getTabletop().
									findSlotArea(selectedSlot.getName().substring(0, 2)));
							mArea.organize();
						}
						if (cS instanceof MeldSlot){
							((MeldSlotArea)(area)).organize();
						}
						
						selectedSlot = null;
					}else
						selectedSlot = cS;				
				}
			}else{
				if (cS.containsTile())	
					selectedSlot = cS;
			}
		}else if (evt.getButton() == MouseEvent.BUTTON3){	//right click means toggle
			if (cS.containsTile()){
				getTabletop().toggle(cS);
				if (cS instanceof MeldSlot){
					((MeldSlotArea)(area)).organize();
				}
				selectedSlot = null;
			}
		}
		repaint();
	}

	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	
	/**
	 * Locates the SlotArea containing the coordinates trueX and trueY
	 * and returns it. If trueX and trueY are outside all possible SlotAreas
	 * returns null.
	 * 
	 * @param trueX		the actual X-coord clicked (not due to (unimplemented) HS)
	 * @param trueY		the actual Y-coord clicked (not the one shown on display due to VS)
	 * @return		the SlotArea containing given coordinates, or null if none exists
	 */
	public SlotArea locateSlotArea(int trueX, int trueY){
		SlotArea[] allSlotAreas = getTabletop().getAllAreas();
		for (SlotArea area : allSlotAreas){
			if (area.getLeftBound() <= trueX && trueX <= area.getRightBound()
					&& area.getTopBound() <= trueY && trueY <= area.getBottomBound()){
				return area;
			}
		}
		return null;
	}
	
	/**
	 * Locates the normal Slot containing the coordinates trueX and trueY
	 * and returns it. 
	 * If unable to find a Slot means user probably clicked in the "null" region 
	 * of a Hand SlotArea and will return null.
	 * 
	 * @param area		SlotArea containing given coordinates, must be a normal SlotArea
	 * @param trueX		the actual X-coord clicked (not due to (unimplemented) HS)
	 * @param trueY		the actual Y-coord clicked (not the one shown on display due to VS)
	 * @return			the normal Slot containing given coordinates
	 */
	public Slot locateNormalSlot(SlotArea area, int trueX, int trueY){
		for (int r = 0; r < area.getNumRows(); r++){
			for (int c = 0; c < area.getNumCols(); c++){
				Slot cS = area.getSlot(r,c);
				if (cS.getLeftBound() <= trueX && trueX <= cS.getRightBound()
						&& cS.getTopBound() <= trueY && trueY <= cS.getBottomBound()){
					return cS;
				}
			}
		}
		return null;
	}
	
	/**
	 * Locates the MeldSlot containing the coordinates trueX and trueY
	 * and returns it. 
	 * If the coordinates fall into a shared region between two or more MeldSlots
	 * returns the MeldSlot that has its unique region closest to the coordinates.
	 * If unable to find a MeldSlot, user probably clicked in the "null" region
	 * and will return null.
	 * 
	 * @param meldArea		MeldSlotArea containing given coordinates
	 * @param trueX		the actual X-coord clicked (not due to (unimplemented) HS)
	 * @param trueY		the actual Y-coord clicked (not the one shown on display due to VS)
	 * @return			the MeldSlot containing given coordinates, or if multiple possible,
	 * 					MeldSlot whose unique region is closest to coordinates
	 */
	public MeldSlot locateMeldSlot(MeldSlotArea meldArea, int trueX, int trueY){
		//always searches in order null Slot, 07, 06, ... 01
		
		//iterate until find first Slot containing coordinates
		//check if Slot in next col also contains coordinates
		//check if Slot in next row also contains coordinates
		//if yes take average of two Slot borders to decide final Slot 
		switch (meldArea.getPrimaryOrientation()){
			case "up":
				for (int r = 0; r < meldArea.getNumRows(); r++){	//2,4
					for (int c = 0; c < meldArea.getNumCols(); c++){
						MeldSlot cMS = (MeldSlot)(meldArea.getSlot(r,c));
						if (cMS == null) continue;
						
						if (cMS.getLeftBound2() <= trueX && trueX <= cMS.getRightBound2()		//biggest possible region
								&& cMS.getTopBound() <= trueY && trueY <= cMS.getBottomBound()){
							boolean closerNextRow = false, closerNextCol = false;
							
							if (r < meldArea.getNumRows()-1){	//make sure has next row
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r+1,c));
								if (nMS.getTopBound() <= trueY && trueY <= nMS.getBottomBound()){	//Left/RightBounds are same
									int avgRndDown = (nMS.getTopBound() + cMS.getBottomBound()) / 2;
									closerNextRow = trueY > avgRndDown;
								}
							}
							
							if (c < meldArea.getNumCols()-1){	//make sure has next col
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r,c+1));
								if (nMS.getLeftBound2() <= trueX && trueX <= nMS.getRightBound2()){	//Top/BottomBounds are same
									int avgRndDown = (nMS.getLeftBound2() + cMS.getRightBound2()) / 2;
									closerNextCol = trueX > avgRndDown;
								}
							}
							
							if (closerNextRow){
								if (closerNextCol)
									return (MeldSlot)(meldArea.getSlot(r+1,c+1));
								return (MeldSlot)(meldArea.getSlot(r+1,c));
							}else if (closerNextCol)
								return (MeldSlot)(meldArea.getSlot(r,c+1));
							else
								return cMS;
						}
						
					}
				}
				break;
			case "left":
				for (int c = 0; c < meldArea.getNumCols(); c++){
					for (int r = meldArea.getNumRows()-1; r >= 0; r--){
						MeldSlot cMS = (MeldSlot)(meldArea.getSlot(r,c));
						if (cMS == null) continue;
						
						if (cMS.getLeftBound() <= trueX && trueX <= cMS.getRightBound()		//biggest possible region
								&& cMS.getTopBound2() <= trueY && trueY <= cMS.getBottomBound2()){
							boolean closerNextRow = false, closerNextCol = false;
							
							if (r > 0){	//make sure has next row, also 1st, 2nd position flipped for L/R orientation
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r-1,c));
								if (nMS.getTopBound2() <= trueY && trueY <= nMS.getBottomBound2()){	//Left/RightBounds are same
									int avgRndDown = (nMS.getBottomBound2() + cMS.getTopBound2()) / 2;
									closerNextRow = trueY <= avgRndDown;
								}
							}
							
							if (c < meldArea.getNumCols()-1){	//make sure has next col
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r,c+1));
								if (nMS.getLeftBound() <= trueX && trueX <= nMS.getRightBound()){	//Top/BottomBounds are same
									int avgRndDown = (nMS.getLeftBound() + cMS.getRightBound()) / 2;
									closerNextCol = trueX > avgRndDown;
								}
							}
							
							if (closerNextRow){
								if (closerNextCol)
									return (MeldSlot)(meldArea.getSlot(r-1,c+1));
								return (MeldSlot)(meldArea.getSlot(r-1,c));
							}else if (closerNextCol)
								return (MeldSlot)(meldArea.getSlot(r,c+1));
							else
								return cMS;
						}
					}
				}
				break;
			case "down":
				for (int r = meldArea.getNumRows()-1; r >= 0; r--){	//2,4
					for (int c = meldArea.getNumCols()-1; c >= 0; c--){
						MeldSlot cMS = (MeldSlot)(meldArea.getSlot(r,c));
						if (cMS == null) continue;
						
						if (cMS.getLeftBound2() <= trueX && trueX <= cMS.getRightBound2()		//biggest possible region
								&& cMS.getTopBound() <= trueY && trueY <= cMS.getBottomBound()){
							boolean closerNextRow = false, closerNextCol = false;
							
							if (r > 0){	//make sure has next row
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r-1,c));
								if (nMS.getTopBound() <= trueY && trueY <= nMS.getBottomBound()){	//Left/RightBounds are same
									int avgRndDown = (nMS.getBottomBound() + cMS.getTopBound()) / 2;
									closerNextRow = trueY <= avgRndDown;
								}
							}
							
							if (c > 0){	//make sure has next col
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r,c-1));
								if (nMS.getLeftBound2() <= trueX && trueX <= nMS.getRightBound2()){	//Top/BottomBounds are same
									int avgRndDown = (nMS.getRightBound2() + cMS.getLeftBound2()) / 2;
									closerNextCol = trueX <= avgRndDown;
								}
							}
							
							if (closerNextRow){
								if (closerNextCol)
									return (MeldSlot)(meldArea.getSlot(r-1,c-1));
								return (MeldSlot)(meldArea.getSlot(r-1,c));
							}else if (closerNextCol)
								return (MeldSlot)(meldArea.getSlot(r,c-1));
							else
								return cMS;
						}
					}
				}
				break;
			case "right":
				for (int c = meldArea.getNumCols()-1; c >= 0; c--){
					for (int r = 0; r < meldArea.getNumRows(); r++){
						MeldSlot cMS = (MeldSlot)(meldArea.getSlot(r,c));
						if (cMS == null) continue;
						
						if (cMS.getLeftBound() <= trueX && trueX <= cMS.getRightBound()		//biggest possible region
								&& cMS.getTopBound2() <= trueY && trueY <= cMS.getBottomBound2()){
							boolean closerNextRow = false, closerNextCol = false;
							
							if (r < meldArea.getNumRows()-1){	//make sure has next row, also 1st, 2nd position flipped for L/R orientation
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r+1,c));
								if (nMS.getTopBound2() <= trueY && trueY <= nMS.getBottomBound2()){	//Left/RightBounds are same
									int avgRndDown = (nMS.getTopBound2() + cMS.getBottomBound2()) / 2;
									closerNextRow = trueY > avgRndDown;
								}
							}
							
							if (c > 0){	//make sure has next col
								MeldSlot nMS = (MeldSlot)(meldArea.getSlot(r,c-1));
								if (nMS.getLeftBound() <= trueX && trueX <= nMS.getRightBound()){	//Top/BottomBounds are same
									int avgRndDown = (nMS.getRightBound() + cMS.getLeftBound()) / 2;
									closerNextCol = trueX <= avgRndDown;
								}
							}
							
							if (closerNextRow){
								if (closerNextCol)
									return (MeldSlot)(meldArea.getSlot(r+1,c-1));
								return (MeldSlot)(meldArea.getSlot(r+1,c));
							}else if (closerNextCol)
								return (MeldSlot)(meldArea.getSlot(r,c-1));
							else
								return cMS;
						}
					}
				}
				break;
		}
		return null;
	}
	
	public Image getMahjongTileImage() {
		return mahjongTileImages[0];
	}
}

