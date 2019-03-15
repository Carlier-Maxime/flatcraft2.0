package dut.flatcraft;

import dut.flatcraft.map.CompleteGameMap;
import fr.univartois.migl.utils.DesignPattern;

/**
 * Simple access point to the map of the game.
 * 
 * The method type inference is used to allow the use of
 * any subclass of GameMap without changing the signature of the methods.
 * 
 * @author leberre
 */
@DesignPattern(name = "registry")
public class MapRegistry {
	private static ExtendedGameMap map;

	@DesignPattern(name = "factory method")
	public static <T extends GameMap> T makeMap(int width, int height) {
		map = new CompleteGameMap(width, height);
		return (T) map;
	}

	public static <T extends GameMap> T getMap() {
		if (map==null) {
			throw new IllegalStateException("call the factory method makeMap() before");
		}
		return (T) map;
	}
}
