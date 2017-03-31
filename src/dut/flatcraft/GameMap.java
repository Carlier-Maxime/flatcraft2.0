package dut.flatcraft;

public interface GameMap {

	int getHeight();
	
	int getWidth();
	
	Cell getAt(int i, int j);
	
	void setAt(int i, int j, Cell cell);
	
}
