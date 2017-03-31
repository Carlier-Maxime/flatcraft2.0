package dut.flatcraft;

import java.util.Iterator;

public class SimpleGameMap implements GameMap {
	private Cell[][] elements;
	private final int width,height;
	
	public SimpleGameMap(int width,int height) {
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

}
