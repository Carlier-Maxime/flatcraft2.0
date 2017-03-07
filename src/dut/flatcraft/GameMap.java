package dut.flatcraft;

import java.util.Iterator;

public interface GameMap extends Iterable<Cell>{

	int getHeight();
	
	int getWidth();
	
	Cell getAt(int i, int j);
	
	void setAt(int i, int j, Cell cell);
	
	Iterator<Cell> iterator();
}
