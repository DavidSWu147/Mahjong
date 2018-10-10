package bigFourWinds;

import java.util.NoSuchElementException;

/**
 * The MeldSlotArea class contains a 2D array of Slots and is used to represent
 * the melds of players' hands for the GUI.
 * As of now, only supports the following dimensions:
 * For primary orientation "up" or "down", rows = 2, cols = 4
 * For primary orientation "left" or "right", rows = 4, cols = 2
 * Anything else will throw an IllegalArgumentException.
 * 
 * @author David Wu
 * @version 2/18/18
 */
public class MeldSlotArea extends SlotArea {
	private String secondaryOrientation;
	//always 90` CCW from primaryOrientation
	
	public MeldSlotArea(String n, int x1, int y1, int x2, int y2, 
			String pO, String sO, int sW, int sH, int[] nArr){
		super(n, x1, y1, x2, y2, pO, sO, sW, sH, nArr);
		
		secondaryOrientation = sO;
		
	}
	
	@Override
	/**
	 * Helper method for constructor, MAY NOT BE CALLED ELSEWHERE
	 * Fills up slotArray with the correct MeldSlots.
	 * @param nArr	array with numbers for the names of the individual Slots
	 * 				must have length rows*cols
	 */
	protected void populateSlotArray(int[] nArr, String sO){
		int rows = 0;
		int cols = 0;
		if (primaryOrientation.equals("up") || primaryOrientation.equals("down")){
			rows = (bottomBound - topBound + 1) / (slotHeight-MeldSlot.DIFFERENCE);
			cols = (rightBound - leftBound + 1) / slotWidth;
		}else if (primaryOrientation.equals("left") || primaryOrientation.equals("right")){
			rows = (bottomBound - topBound + 1) / slotHeight;
			cols = (rightBound - leftBound + 1) / (slotWidth-MeldSlot.DIFFERENCE);
		}
		
		if (primaryOrientation.equals("up") || primaryOrientation.equals("down")){
			if (rows != 2 || cols != 4)
				throw new IllegalArgumentException("Bad parameters: " +
						primaryOrientation + " " + rows + " " + cols);
		}else if (primaryOrientation.equals("left") || primaryOrientation.equals("right")){
			if (rows != 4 || cols != 2)
				throw new IllegalArgumentException("Bad parameters: " +
						primaryOrientation + " " + rows + " " + cols);
		}
		
		slotArray = new Slot[rows][cols];
		
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				String sN = name;
				int ind = i*cols + j;
				
				if (nArr[ind] == -1){
					slotArray[i][j] = null;
				}else{
					if (nArr[ind] < 10)
						sN += "0";
					sN += nArr[ind];
					
					boolean dPO;
					boolean dTP;
					switch (primaryOrientation){
						
						case "up":
							dPO = i == 0;
							dTP = j == 3 && i == 1;
							slotArray[i][j] = new MeldSlot(sN,leftBound+MeldSlot.DIFFERENCE+j*slotWidth,
									topBound-MeldSlot.DIFFERENCE+i*(slotHeight-MeldSlot.DIFFERENCE),
									primaryOrientation,slotWidth,slotHeight,sO,dPO,dTP);									
							break;
						case "down":
							dPO = i == 1;
							dTP = j == 0 && i == 0;
							slotArray[i][j] = new MeldSlot(sN,leftBound+j*slotWidth,
									topBound+i*(slotHeight-MeldSlot.DIFFERENCE), primaryOrientation,
									slotWidth,slotHeight,sO,dPO,dTP);
							break;
					//slotArray[i][j] = new Slot(sN,leftBound+j*slotWidth,topBound+i*slotHeight,primaryOrientation,
							//slotWidth,slotHeight);
						case "left":
							dPO = j == 0;
							dTP = i == 0 && j == 1;
							slotArray[i][j] = new MeldSlot(sN,
									leftBound-MeldSlot.DIFFERENCE+j*(slotWidth-MeldSlot.DIFFERENCE),
									topBound+i*slotHeight,primaryOrientation,slotWidth,slotHeight,
									sO,dPO,dTP);									
							break;
						case "right":
							dPO = j == 1;
							dTP = i == 3 && j == 0;
							slotArray[i][j] = new MeldSlot(sN,leftBound+j*(slotWidth-MeldSlot.DIFFERENCE),
									topBound+MeldSlot.DIFFERENCE+i*slotHeight,primaryOrientation,
									slotWidth,slotHeight,sO,dPO,dTP);
							break;
					}
				}
			}
		}
	}

	public String getSecondaryOrientation() {
		return secondaryOrientation;
	}
	
	@Override
	public int[] getSlotCoordinates(String n){
		for (int r = 0; r < slotArray.length; r++){
			for (int c = 0; c < slotArray[0].length; c++){
				if (slotArray[r][c] != null){
					if (slotArray[r][c].getName().equals(n)){
						int[] coords = {r,c};
						return coords;
					}
				}
			}
		}
		throw new NoSuchElementException(n + " is not in SlotArea " + name);
	}
	
	public int getPosition(int r, int c){
		return ((MeldSlot)slotArray[r][c]).getCurrentPosition();
	}
	public int getPosition(String n){
		int[] coords = getSlotCoordinates(n);
		return getPosition(coords[0],coords[1]);
	}
	
	public void setPosition(int r, int c, int cP){
		((MeldSlot)slotArray[r][c]).setCurrentPosition(cP);
	}
	
	public void setPosition(String n, int cP){
		int[] coords = getSlotCoordinates(n);
		setPosition(coords[0],coords[1],cP);
	}
	
	@Override
	public Tile setTile(int r, int c, Tile cT, boolean fD){
		if (containsTopRowTile()){
			//can't have two top row tiles
			if (((MeldSlot)slotArray[r][c]).getDisablePrimaryOrientation() && ! ((MeldSlot)slotArray[r][c]).containsTile()){
				System.err.println("Can't put tile in " + getSlotName(r,c) + ", already has top row tile.");
				return null;
			}
		}
		return ((MeldSlot)slotArray[r][c]).setCurrentTile(cT,fD);
	}
	
	public Tile setTile(int r, int c, Tile cT, boolean fD, int cP){
		if (containsTopRowTile()){
			//can't have two top row tiles
			if (((MeldSlot)slotArray[r][c]).getDisablePrimaryOrientation() && ! ((MeldSlot)slotArray[r][c]).containsTile()){
				System.err.println("Can't put tile in " + getSlotName(r,c) + ", already has top row tile.");
				return null;
			}
		}
		return ((MeldSlot)slotArray[r][c]).setCurrentTile(cT,fD,cP);
	}
	public Tile setTile(String n, Tile cT, boolean fD, int cP){
		int[] coords = getSlotCoordinates(n);
		return setTile(coords[0],coords[1],cT,fD,cP);
	}
	
	@Override
	public Tile removeTile(int r, int c){
		return ((MeldSlot)slotArray[r][c]).removeCurrentTile();
	}
	
	public boolean containsTopRowTile(){
		switch (primaryOrientation){
			case "up":
				for (int c = 1; c <= 3; c++){
					if (slotArray[0][c].containsTile())
						return true;
				}
				break;
			case "down":
				for (int c = 0; c <= 2; c++){
					if (slotArray[1][c].containsTile())
						return true;
				}
				break;
			case "left":
				for (int r = 0; r <= 2; r++){
					if (slotArray[r][0].containsTile())
						return true;
				}
				break;
			case "right":
				for (int r = 1; r <= 3; r++){
					if (slotArray[r][1].containsTile())
						return true;
				}
				break;
		}
		return false;
	}
	
	/**
	 * Organizes the tiles so that they are in valid positions.
	 * Always call this method after setting a tile or position.
	 */
	public void organize(){
		switch (primaryOrientation){
		case "up":
			if (containsTopRowTile()){
				int topTileCol = -1;
				if (slotArray[0][1].containsTile())
					topTileCol = 1;
				else if (slotArray[0][2].containsTile())
					topTileCol = 2;
				else if (slotArray[0][3].containsTile())
					topTileCol = 3;
				for (int c = 0; c <= 3; c++){
					if (slotArray[1][c].containsTile()){
						if (c < topTileCol)
							((MeldSlot)slotArray[1][c]).setCurrentPosition(3);
						else if (c == topTileCol)
							((MeldSlot)slotArray[1][c]).setCurrentPosition(2);
						else if (c > topTileCol){
							((MeldSlot)slotArray[1][c]).setCurrentPosition(1);
						}
					}
				}
			}else{
				int pos2Col = -1;
				for (int c = 0; c <= 3 && pos2Col == -1; c++){
					if (slotArray[1][c].containsTile())
						if (((MeldSlot)slotArray[1][c]).getCurrentPosition() == 2)
							pos2Col = c;
				}
				if (pos2Col == -1){
					for (int c = 0; c <= 3; c++){
						if (slotArray[1][c].containsTile())
							((MeldSlot)slotArray[1][c]).setCurrentPosition(1);
					}
				}else{
					for (int c = 0; c <= 3; c++){
						if (slotArray[1][c].containsTile()){
							if (c < pos2Col)
								((MeldSlot)slotArray[1][c]).setCurrentPosition(3);
							else if (c > pos2Col){
								((MeldSlot)slotArray[1][c]).setCurrentPosition(1);
							}
						}
					}
				}
			}
			break;
		case "down":
			if (containsTopRowTile()){
				int topTileCol = -1;
				if (slotArray[1][0].containsTile())
					topTileCol = 0;
				else if (slotArray[1][1].containsTile())
					topTileCol = 1;
				else if (slotArray[1][2].containsTile())
					topTileCol = 2;
				for (int c = 0; c <= 3; c++){
					if (slotArray[0][c].containsTile()){
						if (c < topTileCol)
							((MeldSlot)slotArray[0][c]).setCurrentPosition(1);
						else if (c == topTileCol)
							((MeldSlot)slotArray[0][c]).setCurrentPosition(2);
						else if (c > topTileCol){
							((MeldSlot)slotArray[0][c]).setCurrentPosition(3);
						}
					}
				}
			}else{
				int pos2Col = -1;
				for (int c = 0; c <= 3 && pos2Col == -1; c++){
					if (slotArray[0][c].containsTile())
						if (((MeldSlot)slotArray[0][c]).getCurrentPosition() == 2)
							pos2Col = c;
				}
				if (pos2Col == -1){
					for (int c = 0; c <= 3; c++){
						if (slotArray[0][c].containsTile())
							((MeldSlot)slotArray[0][c]).setCurrentPosition(1);
					}
				}else{
					for (int c = 0; c <= 3; c++){
						if (slotArray[0][c].containsTile()){
							if (c < pos2Col)
								((MeldSlot)slotArray[0][c]).setCurrentPosition(1);
							else if (c > pos2Col){
								((MeldSlot)slotArray[0][c]).setCurrentPosition(3);
							}
						}
					}
				}
			}
			break;
		case "left":
			if (containsTopRowTile()){
				int topTileRow = -1;
				if (slotArray[0][0].containsTile())
					topTileRow = 0;
				else if (slotArray[1][0].containsTile())
					topTileRow = 1;
				else if (slotArray[2][0].containsTile())
					topTileRow = 2;
				for (int r = 0; r <= 3; r++){
					if (slotArray[r][1].containsTile()){
						if (r < topTileRow)
							((MeldSlot)slotArray[r][1]).setCurrentPosition(1);
						else if (r == topTileRow)
							((MeldSlot)slotArray[r][1]).setCurrentPosition(2);
						else if (r > topTileRow){
							((MeldSlot)slotArray[r][1]).setCurrentPosition(3);
						}
					}
				}
			}else{
				int pos2Row = -1;
				for (int r = 0; r <= 3 && pos2Row == -1; r++){
					if (slotArray[r][1].containsTile())
						if (((MeldSlot)slotArray[r][1]).getCurrentPosition() == 2)
							pos2Row = r;
				}
				if (pos2Row == -1){
					for (int r = 0; r <= 3; r++){
						if (slotArray[r][1].containsTile())
							((MeldSlot)slotArray[r][1]).setCurrentPosition(1);
					}
				}else{
					for (int r = 0; r <= 3; r++){
						if (slotArray[r][1].containsTile()){
							if (r < pos2Row)
								((MeldSlot)slotArray[r][1]).setCurrentPosition(1);
							else if (r > pos2Row){
								((MeldSlot)slotArray[r][1]).setCurrentPosition(3);
							}
						}
					}
				}
			}
			break;
		case "right":
			if (containsTopRowTile()){
				int topTileRow = -1;
				if (slotArray[1][1].containsTile())
					topTileRow = 1;
				else if (slotArray[2][1].containsTile())
					topTileRow = 2;
				else if (slotArray[3][1].containsTile())
					topTileRow = 3;
				for (int r = 0; r <= 3; r++){
					if (slotArray[r][0].containsTile()){
						if (r < topTileRow)
							((MeldSlot)slotArray[r][0]).setCurrentPosition(3);
						else if (r == topTileRow)
							((MeldSlot)slotArray[r][0]).setCurrentPosition(2);
						else if (r > topTileRow){
							((MeldSlot)slotArray[r][0]).setCurrentPosition(1);
						}
					}
				}
			}else{
				int pos2Row = -1;
				for (int r = 0; r <= 3 && pos2Row == -1; r++){
					if (slotArray[r][0].containsTile())
						if (((MeldSlot)slotArray[r][0]).getCurrentPosition() == 2)
							pos2Row = r;
				}
				if (pos2Row == -1){
					for (int r = 0; r <= 3; r++){
						if (slotArray[r][0].containsTile())
							((MeldSlot)slotArray[r][0]).setCurrentPosition(1);
					}
				}else{
					for (int r = 0; r <= 3; r++){
						if (slotArray[r][0].containsTile()){
							if (r < pos2Row)
								((MeldSlot)slotArray[r][0]).setCurrentPosition(3);
							else if (r > pos2Row){
								((MeldSlot)slotArray[r][0]).setCurrentPosition(1);
							}
						}
					}
				}
			}
			break;
		}
	}
}