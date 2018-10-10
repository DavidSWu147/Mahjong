package bigFourWinds;

import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * The Tabletop class is the class connecting the game to the GUI.
 * It contains instances of SlotArea and MeldSlotArea which 
 * represent the state of the game.
 * 
 * @author David Wu
 * @version 2/20/18
 */
public class Tabletop {
	private MahjongSim masterClass_MS;
	//private Big4WindsZungJung masterClass_B4WZJ;
	
	private SlotArea playerWall, lowerWall, oppositeWall, upperWall;
	private SlotArea playerDiscard, lowerDiscard, oppositeDiscard, upperDiscard;
	private SlotArea playerHand, lowerHand, oppositeHand, upperHand;
	
	private MeldSlotArea playerMeld1, playerMeld2, playerMeld3, playerMeld4;
	private MeldSlotArea lowerMeld1, lowerMeld2, lowerMeld3, lowerMeld4;
	private MeldSlotArea oppositeMeld1, oppositeMeld2, oppositeMeld3, oppositeMeld4;
	private MeldSlotArea upperMeld1, upperMeld2, upperMeld3, upperMeld4;
	
	private boolean canDeal;
	
	private static final int[] PLAYER_WALL_N_ARR = {2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,
				1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33};
	private static final int[] LOWER_WALL_N_ARR = {34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,
			17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
	private static final int[] OPPOSITE_WALL_N_ARR = {33,31,29,27,25,23,21,19,17,15,13,11,9,7,5,3,1,
				34,32,30,28,26,24,22,20,18,16,14,12,10,8,6,4,2};
	private static final int[] UPPER_WALL_N_ARR = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
				18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34};
	private static final int[] PLAYER_DISCARD_N_ARR = {25,1,2,3,4,5,6,29,26,7,8,9,10,11,12,30,
				27,13,14,15,16,17,18,31,28,19,20,21,22,23,24,32};
	private static final int[] LOWER_DISCARD_N_ARR = {29,30,31,32,6,12,18,24,5,11,17,23,4,10,16,22,
				3,9,15,21,2,8,14,20,1,7,13,19,25,26,27,28};
	private static final int[] OPPOSITE_DISCARD_N_ARR = {32,24,23,22,21,20,19,28,31,18,17,16,15,14,13,27,
				30,12,11,10,9,8,7,26,29,6,5,4,3,2,1,25};
	private static final int[] UPPER_DISCARD_N_ARR = {28,27,26,25,19,13,7,1,20,14,8,2,21,15,9,3,
				22,16,10,4,23,17,11,5,24,18,12,6,32,31,30,29};
	private static final int[] PLAYER_HAND_N_ARR = {12,10,8,6,4,2,1,3,5,7,9,11,13,0};
	private static final int[] LOWER_HAND_N_ARR = {0,13,11,9,7,5,3,1,2,4,6,8,10,12};
	private static final int[] OPPOSITE_HAND_N_ARR = {0,13,11,9,7,5,3,1,2,4,6,8,10,12};
	private static final int[] UPPER_HAND_N_ARR = {12,10,8,6,4,2,1,3,5,7,9,11,13,0};
	private static final int[] PLAYER_MELD_N_ARR = {-1,7,6,5,4,3,2,1};
	private static final int[] LOWER_MELD_N_ARR = {5,1,6,2,7,3,-1,4};
	private static final int[] OPPOSITE_MELD_N_ARR = {1,2,3,4,5,6,7,-1};
	private static final int[] UPPER_MELD_N_ARR = {4,-1,3,7,2,6,1,5};
	
	public Tabletop(Object mC){
		if (mC instanceof MahjongSim){
			masterClass_MS = (MahjongSim)mC;
		}
		
		setupWalls();
		setupDiscards();
		setupHands();
		setupMeldSpaces();
		
		canDeal = false;
	}
	
	private final void setupWalls(){
		playerWall = new SlotArea("A",129,593,587,670,"up",null,Tile.WIDTH,Tile.HEIGHT,PLAYER_WALL_N_ARR);
		lowerWall = new SlotArea("B",593,212,670,670,"left",null,Tile.HEIGHT,Tile.WIDTH,LOWER_WALL_N_ARR);
		oppositeWall = new SlotArea("C",212,129,670,206,"down",null,Tile.WIDTH,Tile.HEIGHT,OPPOSITE_WALL_N_ARR);
		upperWall = new SlotArea("D",129,129,206,587,"right",null,Tile.HEIGHT,Tile.WIDTH,UPPER_WALL_N_ARR);
	}
	
	private final void setupDiscards(){
		playerDiscard = new SlotArea("E",214,430,429,585,"up",null,Tile.WIDTH,Tile.HEIGHT,PLAYER_DISCARD_N_ARR);
		lowerDiscard = new SlotArea("F",430,370,585,585,"left",null,Tile.HEIGHT,Tile.WIDTH,LOWER_DISCARD_N_ARR);
		oppositeDiscard = new SlotArea("G",370,214,585,369,"down",null,Tile.WIDTH,Tile.HEIGHT,OPPOSITE_DISCARD_N_ARR);
		upperDiscard = new SlotArea("H",214,214,369,429,"right",null,Tile.HEIGHT,Tile.WIDTH,UPPER_DISCARD_N_ARR);
	}
	
	private final void setupHands(){
		playerHand = new SlotArea("I",187,740,579,778,"up",null,Tile.WIDTH,Tile.HEIGHT,PLAYER_HAND_N_ARR);
		lowerHand = new SlotArea("J",740,220,778,612,"left",null,Tile.HEIGHT,Tile.WIDTH,LOWER_HAND_N_ARR);
		oppositeHand = new SlotArea("K",220,21,612,59,"down",null,Tile.WIDTH,Tile.HEIGHT,OPPOSITE_HAND_N_ARR);
		upperHand = new SlotArea("L",21,187,59,579,"right",null,Tile.HEIGHT,Tile.WIDTH,UPPER_HAND_N_ARR);
	}
	
	private final void setupMeldSpaces(){
		playerMeld1 = new MeldSlotArea("IW",124,678,243,731,"up","left",Tile.WIDTH,Tile.HEIGHT,PLAYER_MELD_N_ARR);
		playerMeld2 = new MeldSlotArea("IX",252,678,371,731,"up","left",Tile.WIDTH,Tile.HEIGHT,PLAYER_MELD_N_ARR);
		playerMeld3 = new MeldSlotArea("IY",380,678,499,731,"up","left",Tile.WIDTH,Tile.HEIGHT,PLAYER_MELD_N_ARR);
		playerMeld4 = new MeldSlotArea("IZ",508,678,627,731,"up","left",Tile.WIDTH,Tile.HEIGHT,PLAYER_MELD_N_ARR);
		lowerMeld1 = new MeldSlotArea("JW",678,556,731,675,"left","down",Tile.HEIGHT,Tile.WIDTH,LOWER_MELD_N_ARR);
		lowerMeld2 = new MeldSlotArea("JX",678,428,731,547,"left","down",Tile.HEIGHT,Tile.WIDTH,LOWER_MELD_N_ARR);
		lowerMeld3 = new MeldSlotArea("JY",678,300,731,419,"left","down",Tile.HEIGHT,Tile.WIDTH,LOWER_MELD_N_ARR);
		lowerMeld4 = new MeldSlotArea("JZ",678,172,731,291,"left","down",Tile.HEIGHT,Tile.WIDTH,LOWER_MELD_N_ARR);
		oppositeMeld1 = new MeldSlotArea("KW",556,68,675,121,"down","right",Tile.WIDTH,Tile.HEIGHT,OPPOSITE_MELD_N_ARR);
		oppositeMeld2 = new MeldSlotArea("KX",428,68,547,121,"down","right",Tile.WIDTH,Tile.HEIGHT,OPPOSITE_MELD_N_ARR);
		oppositeMeld3 = new MeldSlotArea("KY",300,68,419,121,"down","right",Tile.WIDTH,Tile.HEIGHT,OPPOSITE_MELD_N_ARR);
		oppositeMeld4 = new MeldSlotArea("KZ",172,68,291,121,"down","right",Tile.WIDTH,Tile.HEIGHT,OPPOSITE_MELD_N_ARR);
		upperMeld1 = new MeldSlotArea("LW",68,124,121,243,"right","up",Tile.HEIGHT,Tile.WIDTH,UPPER_MELD_N_ARR);
		upperMeld2 = new MeldSlotArea("LX",68,252,121,371,"right","up",Tile.HEIGHT,Tile.WIDTH,UPPER_MELD_N_ARR);
		upperMeld3 = new MeldSlotArea("LY",68,380,121,499,"right","up",Tile.HEIGHT,Tile.WIDTH,UPPER_MELD_N_ARR);
		upperMeld4 = new MeldSlotArea("LZ",68,508,121,627,"right","up",Tile.HEIGHT,Tile.WIDTH,UPPER_MELD_N_ARR);
	}
	
 	public SlotArea getPlayerWall() {
		return playerWall;
	}
	public SlotArea getLowerWall() {
		return lowerWall;
	}
	public SlotArea getOppositeWall() {
		return oppositeWall;
	}
	public SlotArea getUpperWall() {
		return upperWall;
	}
	public SlotArea getPlayerDiscard() {
		return playerDiscard;
	}
	public SlotArea getLowerDiscard() {
		return lowerDiscard;
	}
	public SlotArea getOppositeDiscard() {
		return oppositeDiscard;
	}
	public SlotArea getUpperDiscard() {
		return upperDiscard;
	}
	public SlotArea getPlayerHand() {
		return playerHand;
	}
	public SlotArea getLowerHand() {
		return lowerHand;
	}
	public SlotArea getOppositeHand() {
		return oppositeHand;
	}
	public SlotArea getUpperHand() {
		return upperHand;
	}
	public MeldSlotArea getPlayerMeld1() {
		return playerMeld1;
	}
	public MeldSlotArea getPlayerMeld2() {
		return playerMeld2;
	}
	public MeldSlotArea getPlayerMeld3() {
		return playerMeld3;
	}
	public MeldSlotArea getPlayerMeld4() {
		return playerMeld4;
	}
	public MeldSlotArea getLowerMeld1() {
		return lowerMeld1;
	}
	public MeldSlotArea getLowerMeld2() {
		return lowerMeld2;
	}
	public MeldSlotArea getLowerMeld3() {
		return lowerMeld3;
	}
	public MeldSlotArea getLowerMeld4() {
		return lowerMeld4;
	}
	public MeldSlotArea getOppositeMeld1() {
		return oppositeMeld1;
	}
	public MeldSlotArea getOppositeMeld2() {
		return oppositeMeld2;
	}
	public MeldSlotArea getOppositeMeld3() {
		return oppositeMeld3;
	}
	public MeldSlotArea getOppositeMeld4() {
		return oppositeMeld4;
	}
	public MeldSlotArea getUpperMeld1() {
		return upperMeld1;
	}
	public MeldSlotArea getUpperMeld2() {
		return upperMeld2;
	}
	public MeldSlotArea getUpperMeld3() {
		return upperMeld3;
	}
	public MeldSlotArea getUpperMeld4() {
		return upperMeld4;
	}


	public MahjongSim getMasterClass_MS() {
		return masterClass_MS;
	}
	
	public SlotArea[] getAllAreasNoMelds() {
		SlotArea[] areas = {playerWall,lowerWall,oppositeWall,upperWall,playerDiscard,lowerDiscard,
							oppositeDiscard,upperDiscard,playerHand,lowerHand,oppositeHand,upperHand};		
		return areas;
	}
	public MeldSlotArea[] getAllMeldAreas(){
		MeldSlotArea[] areas = {playerMeld1,playerMeld2,playerMeld3,playerMeld4,
				lowerMeld1,lowerMeld2,lowerMeld3,lowerMeld4,oppositeMeld1,oppositeMeld2,oppositeMeld3,oppositeMeld4,
				upperMeld1,upperMeld2,upperMeld3,upperMeld4};
		return areas;
	}
	public SlotArea[] getAllAreas(){
		SlotArea[] areas = new SlotArea[28];
		SlotArea[] a1 = getAllAreasNoMelds();
		for (int i = 0; i < 12; i++)
			areas[i] = a1[i];
		MeldSlotArea[] aM = getAllMeldAreas();
		for (int i = 0; i < 16; i++)
			areas[i+12] = aM[i];
		return areas;
	}
	
	/**
	 * Return the SlotArea with name n.
	 * 
	 * @param n		name of the desired SlotArea
	 * @return		the SlotArea
	 */
	public SlotArea findSlotArea(String n){
		if (n.length() == 1){	//normal SlotArea
			SlotArea[] allAreas = getAllAreasNoMelds();
			for (SlotArea area : allAreas){
				if (area.getName().equals(n))
					return area;
			}
		}else if (n.length() == 2){	//MeldSlotArea
			MeldSlotArea[] allMeldAreas = getAllMeldAreas();
			for (MeldSlotArea area : allMeldAreas){
				if (area.getName().equals(n))
					return area;
			}
		}
		throw new NoSuchElementException("No SlotArea exists with name " + n + ".");
	}
	
	/**
	 * Return the Slot with name n.
	 * 
	 * @param n		name of the desired Slot
	 * @return		the Slot
	 */
	public Slot findSlot(String n){
		if (n.length() == 3){	//normal Slot
			return findSlotArea(n.substring(0,1)).getSlot(n);
		}else if (n.length() == 4){ 	//MeldSlot
			return findSlotArea(n.substring(0,2)).getSlot(n);
		}
		throw new NoSuchElementException("No Slot exists with name " + n + ".");
	}
	
	public void clearAllTiles(){
		SlotArea[] allAreas = getAllAreasNoMelds();
		MeldSlotArea[] allMeldAreas = getAllMeldAreas();
		for (SlotArea area : allAreas){
			for (int r = 0; r < area.getNumRows(); r++){
				for (int c = 0; c < area.getNumCols(); c++){
					area.removeTile(r,c);
				}
			}
		}
		for (MeldSlotArea area : allMeldAreas){
			for (int r = 0; r < area.getNumRows(); r++){
				for (int c = 0; c < area.getNumCols(); c++){
					if (area.getSlot(r,c) != null)
						area.removeTile(r,c);
				}
			}
		}
		canDeal = false;
	}
	
	public void resetToOrder(){
		clearAllTiles();
		SlotArea[] allWalls = {playerWall,lowerWall,oppositeWall,upperWall};
		TileKeeper keeper = masterClass_MS.getTileKeeper();
		for (int i = 0; i < keeper.getNumTiles(); i++){
			int wall = i / (keeper.getNumTiles()/4);
			int ind = i % (keeper.getNumTiles()/4) + 1;
			String n = allWalls[wall].getName();
			if (ind < 10)
				n += "0";
			n += ind;
			allWalls[wall].setTile(n, keeper.getTile(i), true);
		}
		canDeal = true;
	}
	
	public void setAllToOneFace(boolean fD){
		SlotArea[] allAreas = getAllAreasNoMelds();
		MeldSlotArea[] allMeldAreas = getAllMeldAreas();
		for (SlotArea area : allAreas){
			for (int r = 0; r < area.getNumRows(); r++){
				for (int c = 0; c < area.getNumCols(); c++){
					area.setTileFaceDown(r,c,fD);
				}
			}
		}
		for (MeldSlotArea area : allMeldAreas){
			for (int r = 0; r < area.getNumRows(); r++){
				for (int c = 0; c < area.getNumCols(); c++){
					if (area.getSlot(r,c) != null)
						area.setTileFaceDown(r,c,fD);
				}
			}
		}
	}
	
	/**
	 * Shuffles all tiles in the walls of the four players.
	 * Does not move any tiles outside the walls.
	 */
	public void shuffleWalls(){
		int ind = 0;
		String[] occupiedNames = new String[136];
		Tile[] currentTiles = new Tile[136];
		
		SlotArea[] walls = {playerWall,lowerWall,oppositeWall,upperWall};
		for (int i = 0; i < 4; i++){
			for (int n2 = 1; n2 <= 34; n2++){
				String n = walls[i].getName();
				if (n2 < 10)
					n += "0";
				n += n2;
				
				if (walls[i].getTile(n) != null){
					occupiedNames[ind] = n;
					currentTiles[ind] = walls[i].removeTile(n);
					ind++;
				}
			}
		}
		
		for (int i = ind-1; i >= 0; i--){
			int rand = (int)(Math.random()*(i+1));
			Tile temp = currentTiles[i];
			currentTiles[i] = currentTiles[rand];
			currentTiles[rand] = temp;
		}
		
		for (int i = 0; i < ind; i++){
			if (occupiedNames[i].startsWith("A")){
				playerWall.setTile(occupiedNames[i], currentTiles[i], true);
			}else if (occupiedNames[i].startsWith("B")){
				lowerWall.setTile(occupiedNames[i], currentTiles[i], true);
			}else if (occupiedNames[i].startsWith("C")){
				oppositeWall.setTile(occupiedNames[i], currentTiles[i], true);
			}else if (occupiedNames[i].startsWith("D")){
				upperWall.setTile(occupiedNames[i], currentTiles[i], true);
			}
		}
	}
	
	/**
	 * Deals the starting hands to each of the players.
	 * Follows the proper dealing procedure:
	 * 1st, rolls two dice
	 * 2nd, determines whose wall to break and where the break point is
	 * 3rd, deals the Tiles in sets of 4 with the extra 13th Tile,
	 * 		going clockwise
	 */
	public void deal(){
		if (! canDeal) return;
		
		SlotArea[] walls = {playerWall,lowerWall,oppositeWall,upperWall};
		SlotArea[] hands = {playerHand,lowerHand,oppositeHand,upperHand};
		
		int[] roll1 = getMasterClass_MS().getMahjongFrame().getMahjongPanel()
				.getSScreenPanel().rollDice1();
		int[] roll2 = getMasterClass_MS().getMahjongFrame().getMahjongPanel()
				.getSScreenPanel().rollDice2();
		String currentDealer = getMasterClass_MS().getMahjongFrame().getMahjongPanel()
				.getSScreenPanel().getCurrentDealer();
		int cD_int = (currentDealer.charAt(0) - 1 + 4) % 4;
		
		int breakPlayer = ( (roll1[0] + roll1[1] - 1) + currentDealer.charAt(0) - 1 + 4) % 4;
		int breakPoint = (roll1[0] + roll1[1]) + (roll2[0] + roll2[1]);
		if (breakPoint >= 17){
			breakPoint -= 17;
			breakPlayer = (breakPlayer - 1 + 4) % 4;		
		}
		
		int firstSlot = 34 - 2*breakPoint;
		String fS_str = walls[breakPlayer].getName();
		if (firstSlot < 10)
			fS_str += "0";
		fS_str += firstSlot;
		
		int[][] order = {{12,10,8,6},{4,2,1,3},{5,7,9,11},{13}};
		
		String cS_str = fS_str;
		for (int[] order1 : order){
			for (int d = 0; d < 4; d++){
				SlotArea curHand = hands[(cD_int + d) % 4];
				for (int order2 : order1){
					//get name of hand Slot want to move to
					String n = curHand.getName();
					if (order2 < 10)
						n += "0";
					n += order2;
					Slot curSlot = curHand.getSlot(n);
					
					//move Tile
					move(findSlot(cS_str), curSlot);
					
					//update cS_str to point to next wall Slot with Tile want to move
					if (cS_str.equals("A01"))
						cS_str = "D34";
					else if (cS_str.equals("B01"))
						cS_str = "A34";
					else if (cS_str.equals("C01"))
						cS_str = "B34";
					else if (cS_str.equals("D01"))
						cS_str = "C34";
					else{
						int nextSlot = Integer.parseInt(cS_str.substring(1)) - 1;
						cS_str = cS_str.substring(0,1);
						if (nextSlot < 10)
							cS_str += "0";
						cS_str += nextSlot;
					}
				}
			}
		}
		canDeal = false;
	}
	
	/**
	 * Puts all tiles in each player's Hand in order.
	 * Does include the drawn tile.
	 * The sorted tiles all appear face-up.
	 */
	public void arrangeAllHands(){
		SlotArea[] allHands = {playerHand,lowerHand,oppositeHand,upperHand};
		for (SlotArea hand : allHands)
			arrangeHand(hand);
	}
	
	/**
	 * Puts the hand tiles of a player's Hand in order.
	 * Does include the drawn tile.
	 * The sorted tiles all appear face-up.
	 * 
	 * @param hand		the player's Hand to be arranged
	 */
	public void arrangeHand(SlotArea hand){
		//Move tiles into tempArray
		
		int count = 0;
		for (int r = 0; r < hand.getNumRows(); r++){
			for (int c = 0; c < hand.getNumCols(); c++){
				Slot cS = hand.getSlot(r,c);
				
				if (cS.containsTile())
					count++;
			}
		}
		
		if (count == 0)
			return;
		
		Tile[] tempArray = new Tile[count];
		int ind = 0;
		for (int r = 0; r < hand.getNumRows(); r++){
			for (int c = 0; c < hand.getNumCols(); c++){
				Slot cS = hand.getSlot(r,c);
				
				if (cS.containsTile()){
					tempArray[ind] = cS.removeCurrentTile();
					ind++;
				}
			}
		}
		
		//Sort
		Arrays.sort(tempArray);
		
		//Move back to Hand
		int idNum = count;
		if (count == 14){		//full hand, must put "highest" Tile into drawn Tile Slot
			for (int i = count-1; i >= 0; i--){
				String n = hand.getName();
				if (idNum == 14){
					n += "00";
				}else{
					if (idNum < 10)
						n += "0";
					n += idNum;
				}
				hand.setTile(n, tempArray[i], false);
				
				if (idNum == 14)
					idNum = 13;
				else if (idNum == 1)
					idNum = 2;
				else if (idNum % 2 == 1)
					idNum -= 2;
				else if (idNum % 2 == 0)
					idNum += 2;
			}
		
		}else if (count % 2 == 0){
			for (int i = 0; i < count; i++){
				String n = hand.getName();
				if (idNum < 10)
					n += "0";
				n += idNum;
				hand.setTile(n, tempArray[i], false);
				
				if (idNum == 2)
					idNum = 1;
				else if (idNum % 2 == 0)
					idNum -= 2;
				else if (idNum % 2 == 1)
					idNum += 2;
			}
		}else{		//odd
			for (int i = count-1; i >= 0; i--){
				String n = hand.getName();
				if (idNum < 10)
					n += "0";
				n += idNum;
				hand.setTile(n, tempArray[i], false);
				
				if (idNum == 1)
					idNum = 2;
				else if (idNum % 2 == 1)
					idNum -= 2;
				else if (idNum % 2 == 0)
					idNum += 2;
			}
		}
	}
	
	/**
	 * Moves a Tile from one Slot to another Slot.
	 * Tries to keep as same orientation as possible.
	 * Face-up/Face-down is always preserved.
	 * If moving from MeldSlot to Slot orientation is lost.
	 * If moving from Slot to MeldSlot will put in firstPosition if possible, else secondPosition
	 * If moving from MeldSlot to MeldSlot will put in same orientation if possible, else secondPosition
	 * 
	 * Does not organize Tiles in case of moving between MeldSlots, must manually organize
	 * 
	 * @param fromSlot 	original Slot of moved Tile
	 * @param toSlot	new Slot of moved Tile
	 */
	public void move(Slot fromSlot, Slot toSlot){
		//check if fromSlot containsTile
		if (! fromSlot.containsTile())
			return;
		//check if toSlot is empty
		if (toSlot.containsTile())
			return;
		
		if (toSlot instanceof MeldSlot){
			MeldSlot toSlot2 = (MeldSlot)(toSlot);
			if (fromSlot instanceof MeldSlot){
				MeldSlot fromSlot2 = (MeldSlot)(fromSlot);
				int cP = fromSlot2.getCurrentPosition();
				if (toSlot2.getDisablePrimaryOrientation()){
					toSlot2.setCurrentTile(fromSlot2.removeCurrentTile(),fromSlot2.getFaceDown(),2);
				}else{
					if (cP == 1 || cP == 3){
						toSlot2.setCurrentTile(fromSlot2.removeCurrentTile(),fromSlot2.getFaceDown(),1);
					}else{ 		// cP == 2
						toSlot2.setCurrentTile(fromSlot2.removeCurrentTile(),fromSlot2.getFaceDown(),2);
					}
				}
			}else{
				if (toSlot2.getDisablePrimaryOrientation()){
					toSlot2.setCurrentTile(fromSlot.removeCurrentTile(),fromSlot.getFaceDown(),2);
				}else{
					toSlot2.setCurrentTile(fromSlot.removeCurrentTile(),fromSlot.getFaceDown(),1);
				}
			}
		}else{
			toSlot.setCurrentTile(fromSlot.removeCurrentTile(), fromSlot.getFaceDown());
		}
		canDeal = false;
	}
	
	/**
	 * Same as above method, only parameters are String names of the Slots
	 * instead of actual Slots themselves
	 * 
	 * @param fromSlotName	name of original Slot of moved Tile
	 * @param toSlotName	name of new Slot of moved Tile
	 */
	public void move(String fromSlotName, String toSlotName){
		move(findSlot(fromSlotName),findSlot(toSlotName));
	}
	
	/**
	 * Toggles the orientation of a Tile within a Slot
	 * If normal Slot (or MeldSlot with no primaryOrientation)
	 * goes from {face-up <-> face-down}
	 * If MeldSlot goes from {face-up primary -> face-up secondary
	 * -> face-down primary -> face-down secondary} and back
	 * 
	 * Does not organize in case of MeldSlot, must manually organize
	 * 
	 * @param currentSlot		Slot of Tile to be toggled
	 */
	public void toggle(Slot currentSlot){
		//check if containsTile
		if (! currentSlot.containsTile())
			return;
		
		if (currentSlot instanceof MeldSlot){
			MeldSlot currentSlot2 = (MeldSlot)(currentSlot);
			if (currentSlot2.getDisablePrimaryOrientation()){
				currentSlot2.setFaceDown(! currentSlot2.getFaceDown());
			}else{
				int cP = currentSlot2.getCurrentPosition();
				if (cP == 1 || cP == 3){
					currentSlot2.setCurrentPosition(2);
				}else{	// cP == 2
					currentSlot2.setCurrentPosition(1);
					currentSlot2.setFaceDown(! currentSlot2.getFaceDown());
				}
			}
		}else{
			currentSlot.setFaceDown(! currentSlot.getFaceDown());
		}
	}
	
	/**
	 * Same as above method, only parameter is String name of the Slot
	 * instead of actual Slot itself
	 * 
	 * @param currentSlotName	name of Slot of Tile to be toggled
	 */
	public void toggle(String currentSlotName){
		toggle(findSlot(currentSlotName));
	}
}

//TODO Synchronize TileKeeper so it knows where each tile is, also add javadoc to SimScreenPanel and SimControlPanel
