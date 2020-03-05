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
	 * Compute the hashcode of the current coordinate
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

	/**
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Increment the x coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean incX() {
		if (x < width - 1) {
			x++;
			return true;
		}
		return false;
	}

	/**
	 * Decrement the x coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean decX() {
		if (x > 0) {
			x--;
			return true;
		}
		return false;
	}

	/**
	 * Increment the y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean incY() {
		if (y < height - 1) {
			y++;
			return true;
		}
		return false;
	}

	/**
	 * Decrement the y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean decY() {
		if (y > 0) {
			y--;
			return true;
		}
		return false;
	}

	/**
	 * Increment the x and y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean incXincY() {
		if (x < width - 1 && y < height - 1) {
			x++;
			y++;
			return true;
		}
		return false;
	}

	/**
	 * Increment the x and decrement the y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean incXdecY() {
		if (x < width - 1 && y > 0) {
			x++;
			y--;
			return true;
		}
		return false;
	}

	/**
	 * Decrement the x and increment the y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean decXincY() {
		if (x > 0 && y < height - 1) {
			x--;
			y++;
			return true;
		}
		return false;
	}

	/**
	 * Decrement the X and Y coordinate
	 *
	 * @return true if success, false otherwise
	 */
	public boolean decXdecY() {
		if (x > 0 && y > 0) {
			x--;
			y--;
			return true;
		}
		return false;
	}

	/**
	 * @return the coordinate as a string
	 */
	@Override
	public String toString() {
		return "Coordinate{" + "x=" + x + ", y=" + y + '}';
	}
}
