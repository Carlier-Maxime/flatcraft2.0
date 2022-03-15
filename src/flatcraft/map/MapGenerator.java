package flatcraft.map;

import java.util.Random;

import flatcraft.CellFactory;
import flatcraft.GameMap;

/**
 * Generates a GameMap of a given size with a given CellFactory.
 * 
 * @author leberre
 *
 */
public interface MapGenerator {
	Random RAND = new Random();

	/**
	 * Generates a new map.
	 * 
	 * @param width   width in number of cells
	 * @param height  height in number of cells
	 * @param factory a CellFactory
	 * @return game map
	 */
	GameMap generate(int width, int height, CellFactory factory);

	/**
	 * Retrieve the grass height for a given x.
	 * 
	 * By default, the grass is on the middle of the map.
	 * 
	 * @param mapheight the height of the map
	 * @param x         the x coordinate
	 * @return a y coordinate
	 */
	default int getGrassHeightAt(int mapheight, int x) {
		return mapheight < 20 ? mapheight/2 : 12;
	}
}
