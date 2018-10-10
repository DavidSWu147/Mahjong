package bigFourWinds;

import java.util.NoSuchElementException;

/**
 * The SlotArea class contains a 2D array of Slots and is used to represent
 * the discard piles, walls, and players' hands for the GUI.
 * 
 * @author David Wu
 * @version 2/4/18
 */
public class SlotArea {
	protected String name;		//a letter
	protected int leftBound;
	protected int topBound;
	protected int rightBound;
	protected int bottomBound;
	protected String primaryOrientation;
	protected int slotWidth;
	protected int slotHeight;
	
	protected Slot[][] slotArray;
	
	private static final int DRAW_ADJUST = 15;
	
	public SlotArea(String n, int x1, int y1, int x2, int y2, String pO, String sO, int sW, int sH, int[] nArr){
		name = n;
		leftBound = x1;
		topBound = y1;
		rightBound = x2;
		bottomBound = y2;
		primaryOrientation = pO;
		slotWidth = sW;
		slotHeight = sH;
		
		populateSlotArray(nArr, sO);
	}
	
	/**
	 * Helper method for constructor, MAY NOT BE CALLED ELSEWHERE
	 * Fills up slotArray with the correct slots.
	 * @param nArr	array with numbers for the names of the individual Slots
	 * 				must have length rows*cols
	 * @param sO	secondary orientation: only needed for MeldSlots, = null for regular Slots
	 */
	protected void populateSlotArray(int[] nArr, String sO){
		int rows = (bottomBound - topBound + 1) / slotHeight;
		int cols = (rightBound - leftBound + 1) / slotWidth;
		slotArray = new Slot[rows][cols];
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				String sN = name;
				int ind = i*cols + j;
				if (nArr[ind] < 10)
					sN += "0";
				sN += nArr[ind];
				//if SlotArea is a Hand, if facing up or right adjust the drawn tile, if facing down or left adjust all hand tiles
				int adjX = 0, adjY = 0;
				switch (name){
					case "I":
						if (nArr[ind] == 0)
							adjX = DRAW_ADJUST;
						break;
					case "L":
						if (nArr[ind] == 0)
							adjY = DRAW_ADJUST;
						break;
					case "J":
						if (nArr[ind] != 0)
							adjY = DRAW_ADJUST;
						break;
					case "K":
						if (nArr[ind] != 0)
							adjX = DRAW_ADJUST;
						break;
					default:
						break;
				}
				
				slotArray[i][j] = new Slot(sN,leftBound+adjX+j*slotWidth,topBound+adjY+i*slotHeight,primaryOrientation,
					slotWidth,slotHeight);				
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public int getLeftBound() {
		return leftBound;
	}
	public int getTopBound() {
		return topBound;
	}
	public int getRightBound() {
		return rightBound;
	}
	public int getBottomBound() {
		return bottomBound;
	}
	public String getPrimaryOrientation() {
		return primaryOrientation;
	}
	public int getWidth(){
		return rightBound - leftBound + 1; 
	}
	public int getHeight(){
		return bottomBound - topBound + 1;
	}			
	public int getNumRows(){
		return slotArray.length;
	}
	public int getNumCols(){
		return slotArray[0].length;
	}
	
	public int getSlotWidth(){
		return slotWidth;
	}
	public int getSlotHeight(){
		return slotHeight;
	}
	public int[] getSlotCoordinates(String n){
		for (int r = 0; r < slotArray.length; r++){
			for (int c = 0; c < slotArray[0].length; c++){
				if (slotArray[r][c].getName().equals(n)){
					int[] coords = {r,c};
					return coords;
				}
			}
		}
		throw new NoSuchElementException(n + " is not in SlotArea " + name);
	}
	public String getSlotName(int r, int c){
		return slotArray[r][c].getName();
	}
	
	public Slot getSlot(int r, int c){
		return slotArray[r][c];
	}
	public Slot getSlot(String n){
		int[] coords = getSlotCoordinates(n);
		return getSlot(coords[0],coords[1]);
	}
	
	public Tile getTile(int r, int c){
		return slotArray[r][c].getCurrentTile();
	}
	public Tile getTile(String n){
		int[] coords = getSlotCoordinates(n);
		return getTile(coords[0],coords[1]);
	}
	
	public Tile setTile(int r, int c, Tile cT, boolean fD){
		return slotArray[r][c].setCurrentTile(cT,fD);
	}
	public Tile setTile(String n, Tile cT, boolean fD){
		int[] coords = getSlotCoordinates(n);
		return setTile(coords[0],coords[1],cT,fD);
	}
	
	public Tile removeTile(int r, int c){
		return slotArray[r][c].removeCurrentTile();
	}
	public Tile removeTile(String n){
		int[] coords = getSlotCoordinates(n);
		return removeTile(coords[0],coords[1]);
	}
	
	public boolean getTileFaceDown(int r, int c){
		return slotArray[r][c].getFaceDown();
	}
	public boolean getTileFaceDown(String n){
		int[] coords = getSlotCoordinates(n);
		return getTileFaceDown(coords[0],coords[1]);
	}
	
	public void setTileFaceDown(int r, int c, boolean fD){
		slotArray[r][c].setFaceDown(fD);
	}
	public void setTileFaceDown(String n, boolean fD){
		int[] coords = getSlotCoordinates(n);
		setTileFaceDown(coords[0],coords[1],fD);
	}
	
}
