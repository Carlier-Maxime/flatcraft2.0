package dut.flatcraft;

import java.awt.Graphics;
import java.io.Serializable;

public interface Direction extends Serializable {
	boolean next();
	Coordinate toDig();
	void paint(Graphics g);
	Coordinate nextForResource();
}
