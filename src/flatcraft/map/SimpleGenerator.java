package flatcraft.map;

import flatcraft.CellFactory;
import flatcraft.GameMap;
import flatcraft.MapRegistry;

/**
 * Generates a map in three parts:
 * 
 * <ul>
 * <li>first half of the map is the sky</li>
 * <li>then we have one line of grass</li>
 * <li>then the remaining of the map is for the soil</li>
 * </ul>
 * 
 * @author leberre
 *
 */
public class SimpleGenerator implements MapGenerator {

	@Override
	public GameMap generate(int width, int heigh, CellFactory factory) {
		SimpleGameMap map = MapRegistry.makeMap(width, heigh);
		int floorHeight = getGrassHeightAt(heigh,0);
		for (int i = 0; i < floorHeight; i++) {
			for (int j = 0; j < width; j++) {
				map.setBgAt(i, j, factory.createSky());
				map.setAt(i, j, factory.createEmpty());
			}
		}
		for (int j = 0; j < width; j++) {
			map.setAt(floorHeight-1, j, factory.createTallGrass());
			map.setBgAt(floorHeight, j, factory.createGrass(true));
			map.setAt(floorHeight, j, factory.createGrass(false));
		}
		for (int i = floorHeight + 1; i <= floorHeight+3; i++) {
			for (int j = 0; j < width; j++) {
				map.setBgAt(i, j, factory.createDirt(true));
				map.setAt(i, j, factory.createDirt(false));
			}
		}
		for (int i = floorHeight + 3; i < heigh; i++) {
			for (int j = 0; j < width; j++) {
				map.setBgAt(i, j, factory.createUnderground(true));
				map.setAt(i, j, factory.createUnderground(false));
			}
		}
		return map;
	}

}
