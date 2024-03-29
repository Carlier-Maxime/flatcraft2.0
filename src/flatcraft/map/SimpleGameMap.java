package flatcraft.map;

import java.util.NoSuchElementException;

import flatcraft.Cell;
import flatcraft.GameMap;
import flatcraft.player.Coordinate;

/**
 * Utility class to store the cells in a two-dimensional array.
 * 
 * @author leberre
 *
 */
public class SimpleGameMap implements GameMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cell[][][] elements;
	private final int width;
	private final int height;

	/**
	 * Create a storage of the given size
	 * 
	 * @param width  the width (a positive integer)
	 * @param height the height (a positive integer)
	 */
	public SimpleGameMap(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The dimensions should be strictly positive !");
		}
		elements = new Cell[height][width][2];
		this.width = width;
		this.height = height;
	}

	@Override
	public void setAt(int i, int j, Cell c) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		elements[i][j][1] = c;
		c.getUI().repaint();
	}

	@Override
	public Cell getBgAt(int i, int j) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		return elements[i][j][0];
	}

	@Override
	public void setBgAt(int i, int j, Cell c) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		elements[i][j][0] = c;
		c.getUI().repaint();
	}

	@Override
	public Cell getAt(int i, int j) {
		if (j >= width || i >= height) {
			throw new IllegalArgumentException("Incorrect cell location");
		}
		return elements[i][j][1];
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Coordinate findCell(Cell cell) {
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[i].length; j++) {
				if (elements[i][j][1] == cell) {
					return new Coordinate(j, i, width, height);
				}
			}
		}
		throw new NoSuchElementException();
	}

}
