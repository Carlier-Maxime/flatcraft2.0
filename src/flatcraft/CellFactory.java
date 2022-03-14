package flatcraft;

import fr.univartois.migl.utils.DesignPattern;

/**
 * Abstraction to create the different kinf of cell found in the map.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Abstract Factory")
public interface CellFactory {

	/**
	 * Those cells do not contain anything: there is nothing to dig, you cannot stay
	 * on that cell.
	 * 
	 * @return
	 */
	Cell createSky();

	/**
	 * The cell use for fill foreground.
	 *
	 * @return
	 */
	Cell createEmpty();

	/**
	 * The separation between the sky and the soil.
	 * 
	 * @return
	 */
	Cell createGrass(boolean empty);

	/**
	 * The place to dig.
	 * 
	 * @return
	 */
	Cell createSoil(boolean empty);

	/**
	 * The wood found in the map. Useful to craft. Useful to heat the furnace.
	 * 
	 * @return
	 */
	Cell createTree();

	/**
	 * The leaves of the tree, useful for the furnace.
	 * 
	 * @return
	 */
	Cell createLeaves();

	Cell createTallGrass();

	Cell createDirt(boolean empty);

	Cell createUnderground(boolean empty);

	/**
	 * Create a cell object using its name.
	 * 
	 * @param name a resource name
	 * @return an instance of that resource
	 * @see MineUtils
	 */
	default Cell createCell(String name) {
		return MineUtils.getResourceByName(name).newInstance();
	}
}
