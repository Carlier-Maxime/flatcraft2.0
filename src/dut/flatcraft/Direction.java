package dut.flatcraft;

import java.awt.Graphics;

public interface Direction {
	boolean next();
	Coordinate toDig();
	void paint(Graphics g);
	Coordinate nextForResource();
}
