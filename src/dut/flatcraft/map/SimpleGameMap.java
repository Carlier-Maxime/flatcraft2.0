package dut.flatcraft.map;

import java.util.NoSuchElementException;

import dut.flatcraft.Cell;
import dut.flatcraft.GameMap;
import dut.flatcraft.player.Coordinate;

public class SimpleGameMap implements GameMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cell[][] elements;
	private final int width;
	private final int height;

	public SimpleGameMap(int width, int height) {
		elements = new Cell[height][width];
		this.width = width;
		this.height = height;
	}

	public void setAt(int i, int j, Cell c) {
		elements[i][j] = c;
	}

	public Cell getAt(int i, int j) {
		return elements[i][j];
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
				if (elements[i][j] == cell) {
					return new Coordinate(i, j, width, height);
				}
			}
		}
		throw new NoSuchElementException();
	}

}
