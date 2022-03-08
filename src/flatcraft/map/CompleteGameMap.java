package dut.flatcraft.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import dut.flatcraft.Cell;
import dut.flatcraft.ExtendedGameMap;
import dut.flatcraft.player.Coordinate;

/**
 * A map with easy access to the neighbor of a cell.
 * 
 * @author leberre
 */
public class CompleteGameMap extends SimpleGameMap implements ExtendedGameMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Cell, Coordinate> coordinates = new HashMap<>();

	public CompleteGameMap(int width, int height) {
		super(width, height);
	}

	@Override
	public void setAt(int i, int j, Cell c) {
		super.setAt(i, j, c);
		coordinates.put(c, new Coordinate(j, i, getWidth(), getHeight()));
	}

	private Optional<Cell> getNearCell(Cell cell, Predicate<Coordinate> p) {
		Coordinate c = coordinates.get(cell);
		if (c == null) {
			return Optional.empty();
		}
		Coordinate c2 = new Coordinate(c);
		if (p.test(c2)) {
			return Optional.of(getAt(c2.getY(), c2.getX()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Cell> west(Cell cell) {
		return getNearCell(cell, Coordinate::decX);
	}

	@Override
	public Optional<Cell> east(Cell cell) {
		return getNearCell(cell, Coordinate::incX);
	}

	@Override
	public Optional<Cell> north(Cell cell) {
		return getNearCell(cell, Coordinate::decY);
	}

	@Override
	public Optional<Cell> south(Cell cell) {
		return getNearCell(cell, Coordinate::incY);
	}

	@Override
	public Optional<Cell> northWest(Cell cell) {
		return getNearCell(cell, c -> c.decX() && c.decY());
	}

	@Override
	public Optional<Cell> southWest(Cell cell) {
		return getNearCell(cell, c -> c.decX() && c.incY());
	}

	@Override
	public Optional<Cell> northEast(Cell cell) {
		return getNearCell(cell, c -> c.incX() && c.decY());
	}

	@Override
	public Optional<Cell> southEast(Cell cell) {
		return getNearCell(cell, c -> c.incX() && c.incY());
	}

	@Override
	public Iterator<Optional<Cell>> neighbors(Cell cell) {
		return new Iterator<Optional<Cell>>() {
			private Coordinate origin = coordinates.get(cell);
			private List<Predicate<Coordinate>> p = new ArrayList<>();
			private Iterator<Predicate<Coordinate>> it;

			{
				p.add(Coordinate::decX);
				p.add(c -> c.decX() && c.decY());
				p.add(Coordinate::decY);
				p.add(c -> c.incX() && c.decY());
				p.add(Coordinate::incX);
				p.add(c -> c.incX() && c.incY());
				p.add(Coordinate::incY);
				p.add(c -> c.decX() && c.incY());
				it = p.iterator();
			}

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Optional<Cell> next() {
				Coordinate current = new Coordinate(origin);
				if (it.next().test(current)) {
					return Optional.of(getAt(current.getY(), current.getX()));
				}
				return Optional.empty();
			}
		};
	}

	@Override
	public Coordinate findCell(Cell cell) {
		Coordinate c = coordinates.get(cell);
		if (c == null) {
			return null;
		}
		return new Coordinate(c);
	}
}
