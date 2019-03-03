package dut.flatcraft.map;

import java.util.Random;

import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;

public interface MapGenerator {
	Random RAND = new Random();
	

	GameMap generate(int width, int heigh,CellFactory factory);
}
