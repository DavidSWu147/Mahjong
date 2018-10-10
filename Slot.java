package bigFourWinds;

/**
 * The Slot class represents a specific space on the Tabletop.
 * It can contain a Mahjong Tile, or stay empty.
 * A SlotArea contains a group of Slots next to each other and is
 * in charge of managing them.
 * 
 * @author David Wu
 * @version 1/17/18
 */
public class Slot {
	protected String name;		//a letter + 2 or 1 digit number with leading zero
	protected int leftBound;
	protected int topBound;
	protected int rightBound;
	protected int bottomBound;
	protected String primaryOrientation;		//either "up", "left", "down", or "right"
	
	protected Tile currentTile;
	protected boolean faceDown;
	
	/**
	 * Creates a standard Slot. Note the order of the parameters.
	 * The orientation comes after so as to group the coordinates together.
	 * 
	 * @param n		the name
	 * @param x1	the top left x-coord
	 * @param y1	the top left y-coord
	 * @param x2	the bottom right x-coord
	 * @param y2	the bottom right y-coord
	 * @param pO	the primary orientation
	 */
	public Slot(String n, int x1, int y1, int x2, int y2, String pO){
		name = n;
		leftBound = x1;
		topBound = y1;
		rightBound = x2;
		bottomBound = y2;
		primaryOrientation = pO;
		currentTile = null;
		faceDown = false;
	}
	
	/**
	 * Creates a standard Slot. Note the order of the parameters.
	 * The orientation comes in the middle so as to separate coordinates 
	 * from dimensions.
	 * 
	 * @param n		the name
	 * @param x1	the top left x-coord
	 * @param y1	the top left y-coord
	 * @param pO	the primary orientation
	 * @param w		the width 
	 * @param h		the height
	 */
	public Slot(String n, int x1, int y1, String pO, int w, int h){
		name = n;
		leftBound = x1;
		topBound = y1;
		rightBound = x1 + w - 1;
		bottomBound = y1 + h - 1;
		primaryOrientation = pO;
		currentTile = null;
		faceDown = false;
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
	public int getWidth() {
		return rightBound - leftBound + 1;
	}
	public int getHeight() {
		return bottomBound - topBound + 1;
	}
	public String getPrimaryOrientation() {
		return primaryOrientation;
	}
	
	public boolean containsTile(){ 
		return currentTile != null;
	}
	public Tile getCurrentTile() {
		return currentTile;
	}
	
	/**
	 * First removes the current Tile, changes to the new Tile,
	 * then returns the old Tile.
	 * Different from an ordinary setter method.
	 * 
	 * @param cT	the Tile that will be the current Tile
	 * @param fD	if the new tile will be face down
	 * @return		the old Tile, or null if there was none
	 */
	public Tile setCurrentTile(Tile cT, boolean fD) {
		if (cT == null)
			throw new IllegalArgumentException("Use removeCurrentTile() instead of null parameter!");
		Tile rT = removeCurrentTile();
		currentTile = cT;
		setFaceDown(fD);
		return rT;
	}
	
	/**
	 * Removes the current Tile and returns it.
	 * Will return null if there was no tile to be removed.
	 * 
	 * @return	the Tile that was removed
	 */
	public Tile removeCurrentTile(){
		Tile rT = currentTile;
		currentTile = null;
		return rT;
	}
	
	public boolean getFaceDown(){
		return faceDown;
	}	
	public void setFaceDown(boolean fD){
		faceDown = fD;
	}
}
