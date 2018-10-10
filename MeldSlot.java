package bigFourWinds;

/**
 * The MeldSlot class is a subclass of Slot that is used in melds,
 * since they require more than 1 orientation.
 * The MeldArea container class keeps track of which position and
 * orientation a MeldSlot and its current Tile are in. 
 * 
 * @author David Wu
 * @version 1/17/18
 */
public class MeldSlot extends Slot {
	//name (inherited from Slot) has 2 letters instead of 1
	
	/**
	 * pixels difference between the two possible places 
	 * that share the same orientation
	 * direction depends on orientation of tile
	 */
	public static final int DIFFERENCE = Tile.HEIGHT - Tile.WIDTH;
	
	private String secondaryOrientation;
	//always 90` CCW from primaryOrientation
	private boolean disablePrimaryOrientation;		
	//true for top tile of a small exposed kong	(#567)
	private boolean disableThirdPosition;
	//true for top tile of a small exposed kong
	//AND rightmost tile of a meld (#1)
	
	//Bounds for 2nd position
	private int leftBound2;
	private int topBound2;
	private int rightBound2;
	private int bottomBound2;
	//Bounds for 3rd position
	private int leftBound3;
	private int topBound3;
	private int rightBound3;
	private int bottomBound3;
	
	private int currentPosition;
	
	/**
	 * Creates a standard MeldSlot. Note the order of the parameters.
	 * The orientation comes after so as to group the coordinates together.
	 * 
	 * @param n		the name
	 * @param x1	the top left x-coord
	 * @param y1	the top left y-coord
	 * @param x2	the bottom right x-coord
	 * @param y2	the bottom right y-coord
	 * @param pO	the primary orientation
	 * @param sO	the seconday orientation (always 90` CCW from primary)
	 * @param dPO	true if the primary orientation is not a valid orientation
	 * @param dTP	true if third position is not a valid position
	 */
	public MeldSlot(String n, int x1, int y1, int x2, int y2, String pO, 
			String sO, boolean dPO, boolean dTP) {
		super(n, x1, y1, x2, y2, pO);
		secondaryOrientation = sO;
		disablePrimaryOrientation = dPO;
		disableThirdPosition = dTP;
		find2ndPositionBounds();
		find3rdPositionBounds();
		currentPosition = 0;
	}
	
	/**
	 * Creates a standard MeldSlot. Note the order of the parameters.
	 * The orientation comes in the middle so as to separate coordinates 
	 * from dimensions.
	 * 
	 * @param n		the name
	 * @param x1	the top left x-coord
	 * @param y1	the top left y-coord
	 * @param pO	the primary orientation
	 * @param w		the width
	 * @param h		the height
	 * @param sO	the seconday orientation (always 90` CCW from primary)
	 * @param dPO	true if the primary orientation is not a valid orientation
	 * @param dTP	true if third position is not a valid position
	 */
	public MeldSlot(String n, int x1, int y1, String pO, int w, int h, 
			String sO, boolean dPO, boolean dTP){
		super(n, x1, y1, pO, w, h);
		secondaryOrientation = sO;
		disablePrimaryOrientation = dPO;
		disableThirdPosition = dTP;
		find2ndPositionBounds();
		find3rdPositionBounds();
		currentPosition = 0;
	}
	
	private final void find2ndPositionBounds(){
		switch (primaryOrientation){
			case "up":
				leftBound2 = leftBound - DIFFERENCE;
				topBound2 = topBound + DIFFERENCE;
				rightBound2 = rightBound;
				bottomBound2 = bottomBound;
				break;
			case "left":
				leftBound2 = leftBound + DIFFERENCE;
				topBound2 = topBound;
				rightBound2 = rightBound;
				bottomBound2 = bottomBound + DIFFERENCE;
				break;
			case "down":
				leftBound2 = leftBound;
				topBound2 = topBound;
				rightBound2 = rightBound + DIFFERENCE;
				bottomBound2 = bottomBound - DIFFERENCE;
				break;
			case "right":
				leftBound2 = leftBound;
				topBound2 = topBound - DIFFERENCE;
				rightBound2 = rightBound - DIFFERENCE;
				bottomBound2 = bottomBound;
				break;
		}
	}
	
	private final void find3rdPositionBounds(){
		switch (primaryOrientation){
			case "up":
				leftBound3 = leftBound - DIFFERENCE;
				topBound3 = topBound;
				rightBound3 = rightBound - DIFFERENCE;
				bottomBound3 = bottomBound;
				break;
			case "left":
				leftBound3 = leftBound;
				topBound3 = topBound + DIFFERENCE;
				rightBound3 = rightBound;
				bottomBound3 = bottomBound + DIFFERENCE;
				break;
			case "down":
				leftBound3 = leftBound + DIFFERENCE;
				topBound3 = topBound;
				rightBound3 = rightBound + DIFFERENCE;
				bottomBound3 = bottomBound;
				break;
			case "right":
				leftBound3 = leftBound;
				topBound3 = topBound - DIFFERENCE;
				rightBound3 = rightBound;
				bottomBound3 = bottomBound - DIFFERENCE;
				break;
		}
	}
	
	public String getSecondaryOrientation(){
		return secondaryOrientation;
	}
	public boolean getDisablePrimaryOrientation() {
		return disablePrimaryOrientation;
	}
	public boolean getDisableThirdPosition() {
		return disableThirdPosition;
	}
	public int getLeftBound2() {
		return leftBound2;
	}
	public int getTopBound2() {
		return topBound2;
	}
	public int getRightBound2() {
		return rightBound2;
	}
	public int getBottomBound2() {
		return bottomBound2;
	}
	public int getLeftBound3() {
		return leftBound3;
	}
	public int getTopBound3() {
		return topBound3;
	}
	public int getRightBound3() {
		return rightBound3;
	}
	public int getBottomBound3() {
		return bottomBound3;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public void setCurrentPosition(int cP) {
		if (cP < 0 || cP > 3)
			throw new IllegalArgumentException("Position " + cP + " is not allowed.");
		
		if (disablePrimaryOrientation && cP != 0 && cP != 2){
			throw new IllegalArgumentException("Primary Orientation disabled, so position "
				+ cP + " is not allowed.");			
		}else if (disableThirdPosition && cP == 3){
			throw new IllegalArgumentException("Third position disabled.");
		}
		
		currentPosition = cP;
	}	
	
	@Override
	//By default puts Tile in second position (since all Tiles have second position)
	public Tile setCurrentTile(Tile cT, boolean fD){
		return setCurrentTile(cT,fD,2);
	}
	
	public Tile setCurrentTile(Tile cT, boolean fD, int cP) {		
		setCurrentPosition(cP);		
		
		if (cT == null)
			throw new IllegalArgumentException("Use removeCurrentTile() instead of null parameter!");
		Tile rT = super.removeCurrentTile();
		currentTile = cT;
		setFaceDown(fD);
		return rT;
	}
	
	public Tile removeCurrentTile(){
		setCurrentPosition(0);
		return super.removeCurrentTile();
	}
	
	
	//TODO add javadoc, test out Slot and MeldSlot
}
