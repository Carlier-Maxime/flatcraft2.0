package dut.flatcraft.player;

import java.io.Serializable;

public class Coordinate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;
	public final int width;
	public final int height;

	public Coordinate(Coordinate c) {
		this(c.x, c.y, c.width, c.height);
	}

	public Coordinate(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/*
	 * calculate the hashcode of the current coordinate
	 * 
	 * @return the hash code of the position
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * Check if the object is the same as the current
	 * 
	 * @return true if the object in parameter equals the current object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		return (y == other.y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean incX() {
		if (x < width - 1) {
			x++;
			return true;
		}
		return false;
	}

	public boolean decX() {
		if (x > 0) {
			x--;
			return true;
		}
		return false;
	}

	public boolean incY() {
		if (y < height - 1) {
			y++;
			return true;
		}
		return false;
	}

	public boolean decY() {
		if (y > 0) {
			y--;
			return true;
		}
		return false;
	}

	public boolean incXincY() {
		if (x < width - 1 && y < height - 1) {
			x++;
			y++;
			return true;
		}
		return false;
	}

	public boolean incXdecY() {
		if (x < width - 1 && y > 0) {
			x++;
			y--;
			return true;
		}
		return false;
	}

	public boolean decXincY() {
		if (x > 0 && y < height - 1) {
			x--;
			y++;
			return true;
		}
		return false;
	}

	public boolean decXdecY() {
		if (x > 0 && y > 0) {
			x--;
			y--;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Coordinate{" + "x=" + x + ", y=" + y + '}';
	}
}