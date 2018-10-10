package bigFourWinds;

/**
 * The MahjongSim class is the main class used to run a Mahjong Simulation.
 * In a simulation, one is allowed to click on tiles and move them anywhere.
 * (Right-clicking toggles orientations of tiles)
 * 
 * @author David Wu
 * @version 1/16/18
 */
public class MahjongSim {
	private MahjongFrame mahjongF;
	
	private Tabletop table;
	private TileKeeper keeper;
	
	public MahjongSim(){
		mahjongF = new MahjongFrame(this);
		table = new Tabletop(this);
		keeper = new TileKeeper(this);
	}
	
	public static void main(String[] args){
		MahjongSim ms = new MahjongSim();
		ms.run();
	}
	
	public void run(){
		mahjongF.getMahjongPanel().getDisplayPanel().activateMouseListener();
		
	}
	
	public void test(){
		
	}
	
	public MahjongFrame getMahjongFrame() {
		return mahjongF;
	}

	public Tabletop getTabletop() {
		return table;
	}	
	
	public TileKeeper getTileKeeper(){
		return keeper;
	}
}
