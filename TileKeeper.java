package bigFourWinds;

import java.util.NoSuchElementException;

/**
 * The TileKeeper class holds all references to the 136 Tiles used in playing a game of Mahjong.
 * 
 * @author David Wu
 * @version 1/17/18
 */
public class TileKeeper {
	private MahjongSim masterClass_MS;
	//private Big4WindsZungJung masterClass_B4WZJ;
	
	public static final String[] ALL_RANKS = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static final String[] ALL_SUITS = {"Dot", "Bamboo", "Character"};
	public static final String[] ALL_WINDS = {"East", "South", "West", "North"};
	public static final String[] ALL_DRAGONS = {"Red", "Green", "Blue", "White"};
	public static final String[] TWO_HONORS = {"Wind", "Dragon"};
	
	private boolean hasAllFourDragons;
	private Tile[] fullTileList;
	private Slot[] correspondingSlotsOccupied;
	
	/**
	 * Creates a standard TileKeeper.
	 * 136 Tiles, 4 Blue Dragons.
	 * 
	 * @param mC	the Master Class
	 */
	public TileKeeper(Object mC){
		if (mC instanceof MahjongSim){
			masterClass_MS = (MahjongSim)mC;
		}
		
		
		fullTileList = new Tile[136];
		hasAllFourDragons = false;
		
		for (int j = 0; j < 3; j++){
			for (int i = 0; i < 9; i++){
				for (int n = 1; n <= 4; n++){
					fullTileList[j*36 + i*4 + n-1] = new Tile(ALL_RANKS[i] + ALL_SUITS[j], n);
				}
			}
		}
		
		for (int i = 0; i < 4; i++){
			for (int n = 1; n <= 4; n++){
				fullTileList[108 + i*4 + n-1] = new Tile(ALL_WINDS[i] + TWO_HONORS[0], n);
			}
		}
		for (int i = 0; i < 3; i++){		//don't make white dragons
			for (int n = 1; n <= 4; n++){
				fullTileList[124 + i*4 + n-1] = new Tile(ALL_DRAGONS[i] + TWO_HONORS[1], n);
			}
		}
		
		correspondingSlotsOccupied = new Slot[136];
	}
	
	/**
	 * Creates a TileKeeper with White Dragons.
	 * If fourDragons is true, the set will also include Blue Dragons,
	 * and will have 140 Tiles.
	 * Otherwise it will have 136 Tiles as usual.
	 * 
	 * @param mC	the Master Class
	 * @param fourDragons 	Decides whether to have all four dragons or exclude the Blue Dragon
	 */
	public TileKeeper(Object mC, boolean fourDragons){
		this(mC);
		for (int n = 1; n <= 4; n++){
			fullTileList[132 + n-1] = new Tile(ALL_DRAGONS[3] + TWO_HONORS[1], n);
		}
		if (fourDragons){
			hasAllFourDragons = true;
			throw new UnsupportedOperationException(
				"As of yet, a four dragon set has not been implemented.");
			//maybe implement later
		}
	}
	
	public Slot getSlot(Tile cT){
		for (int i = 0; i < fullTileList.length; i++){
			if (fullTileList[i].equalsCopiesDistinct(cT))
				return correspondingSlotsOccupied[i];
		}
		throw new NoSuchElementException(cT + " is not in the TileKeeper");
	}
	
	public void setSlot(Tile cT, Slot cS){
		for (int i = 0; i < fullTileList.length; i++){
			if (fullTileList[i].equalsCopiesDistinct(cT))
				correspondingSlotsOccupied[i] = cS;
		}
		throw new NoSuchElementException(cT + " is not in the TileKeeper");
	}
	
	public MahjongSim getMasterClass_MS() {
		return masterClass_MS;
	}
	
	public boolean getHasAllFourDragons() {
		return hasAllFourDragons;
	}
	
	public int getNumTiles(){
		return fullTileList.length;
	}
	
	public Tile getTile(int ind){
		return fullTileList[ind];
	}
	public Tile getTile(String n, int copyNo){
		for (int i = 0; i < fullTileList.length; i++){
			if (fullTileList[i].getName().equals(n) && fullTileList[i].getCopyNo() == copyNo)
				return fullTileList[i];
		}
		throw new NoSuchElementException("Tile with name " + n + " and copy No. " + copyNo + " is not in the TileKeeper");
	}
}
