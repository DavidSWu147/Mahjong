package bigFourWinds;

/**
 * The Tile class represents a single mahjong tile in the set of 136 tiles.
 * 
 * @author David Wu
 * @version 1/15/18
 */
public class Tile implements Comparable<Tile>{
	public static final String PATHWAY = "C:/Users/Bdavi/eclipse-workspace/DavidSWu/Mahjong/bigFourWinds/";
	public static final String JPEG_EXTENSION = ".jpeg";
	public static final String PNG_EXTENSION = ".png";
	
	public static final int WIDTH = 27;
	public static final int HEIGHT = 39;
	public static final int FD_PICX = 8;
	public static final int FD_PICY = 3;
	
	private String name;
	private String rank;
	private String suit;
	private int copyNo;		//int from 1 to 4 distinguishing identical tiles
	private boolean isFake;
	
	private int picX;
	private int picY;
	
	/**
	 * Creates a fake mahjong Tile. Use it as a placeholder.
	 * A fake mahjong Tile is blank on both sides.
	 */
	public Tile(){
		name = "";
		rank = "";
		suit = "";
		copyNo = 0;
		isFake = true;
		picX = 8;
		picY = 3;
	}
	
	/**
	 * Creates an actual mahjong Tile.
	 * 
	 * @param n		the Tile's name
	 * @param cN	the Tile's copy number
	 */
	public Tile(String n, int cN){
		name = n;
		
		if (n.charAt(0) >= '1' && n.charAt(0) <= '9'){
			rank = n.substring(0,1);
			suit = n.substring(1).toLowerCase();
			if (! (suit.equals("dot") || suit.equals("bamboo") || suit.equals("character")))
				throw new IllegalArgumentException("Not a valid tile name: " + n);
		}else{
			suit = "honor";
			if (n.endsWith("Wind")){
				rank = n.substring(0,n.length()-4).toLowerCase();
			}else if (n.endsWith("Dragon")){
				rank = n.substring(0,n.length()-6).toLowerCase();
			}else{
				throw new IllegalArgumentException("Not a valid tile name: " + n);
			}
			if (! (rank.equals("east") || rank.equals("south") || rank.equals("west") || 
				rank.equals("north") || rank.equals("red") || rank.equals("green") || 
				rank.equals("blue") || rank.equals("white"))){
				throw new IllegalArgumentException("Not a valid tile name: " + n);
			}				
		}
		
		copyNo = cN;
		isFake = false;
		
		if (suit.equals("honor")){
			picY = 3;
			switch(rank){
				case "east": picX = 0; break; case "south": picX = 1; break;
				case "west": picX = 2; break; case "north": picX = 3; break;
				case "red": picX = 4; break;  case "green": picX = 5; break;
				case "blue": picX = 6; break; case "white": picX = 7; break;
			}
		}else{
			picX = Integer.parseInt(rank) - 1;
			switch(suit){
				case "dot": picY = 0; break;
				case "bamboo": picY = 1; break;
				case "character": picY = 2; break;
			}
		}
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Tile){
			Tile other = (Tile)o;
			return name.equals(other.name);
		}else{
			return false;
		}			
	}
	
	public boolean equalsCopiesDistinct(Tile other){
		return equals(other) && getCopyNo() == other.getCopyNo();
	}
	
	public boolean equalsBlueWhiteSame(Tile other){
		if (name.equals("BlueDragon") && other.name.equals("WhiteDragon") ||
			name.equals("WhiteDragon") && other.name.equals("BlueDragon")){
			return true;
		}else{
			return equals(other);
		}			
	}
	
	@Override
	public int compareTo(Tile other){
		return intValue() - other.intValue();
	}
	
	public int intValue(){
		if (isFake)
			return -1;
		
		int value = 0;
		if (suit.equals("honor")){
			switch(rank){
				case "east": value = 60; break;  case "south": value = 70; break;
				case "west": value = 80; break;  case "north": value = 90; break;
				case "red": value = 120; break;  case "green": value = 210; break;
				case "blue": value = 300; break; case "white": value = 400; break;
			}
		}else{
			switch(suit){				
				case "dot": value = 0; break;
				case "bamboo": value = 10; break;
				case "character": value = 20; break;				
			}
			value += Integer.parseInt(rank);
		}
		return value;
	}
	
	@Override
	public String toString(){
		if (isFake)
			return "Fake";
		return name + ", #" + copyNo;
	}
	
	public String getName(){ return name; }
	public String getRank(){ return rank; }
	public String getSuit(){ return suit; }
	public int getCopyNo(){ return copyNo; }
	public boolean getIsFake(){ return isFake; }
	public int getPicX(){ return picX; }
	public int getPicY(){ return picY; }
	
}
