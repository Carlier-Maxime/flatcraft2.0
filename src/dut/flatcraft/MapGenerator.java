package dut.flatcraft;

import java.util.Random;

public interface MapGenerator {
	Random RAND = new Random();
	

	GameMap generate(int width, int heigh,CellFactory factory);
}
