package dut.flatcraft;

import java.util.Iterator;
import java.util.Optional;

import fr.univartois.migl.utils.DesignPattern;

/**
 * Game map with the possibility to access neighboring cells.
 * 
 * The idea is a hide the way the cells are stored. As such,
 * the current cell is given as parameter, and the height neighboring
 * cells are denoted by directions.
 * 
 * Note that in some cases, there is no neighbor, which result in the
 * choice to return an Optional object, not directly a Cell.
 * 
 * @author leberre
 *
 */
public interface ExtendedGameMap extends GameMap {

	Optional<Cell> west(Cell cell);

	Optional<Cell> east(Cell cell);

	Optional<Cell> north(Cell cell);

	Optional<Cell> south(Cell cell);

	Optional<Cell> northWest(Cell cell);

	Optional<Cell> southWest(Cell cell);

	Optional<Cell> northEast(Cell cell);

	Optional<Cell> southEast(Cell cell);

	@DesignPattern(name = "iterator")
	Iterator<Optional<Cell>> neighbors(Cell cell);

}
