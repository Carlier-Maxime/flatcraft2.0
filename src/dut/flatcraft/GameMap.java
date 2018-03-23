package dut.flatcraft;

import java.io.Serializable;

public interface GameMap extends Serializable {

	int getHeight();

	int getWidth();

	Cell getAt(int i, int j);

	void setAt(int i, int j, Cell cell);

	Coordinate findCell(Cell cell);
}
