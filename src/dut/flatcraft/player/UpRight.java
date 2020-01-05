package dut.flatcraft.player;

public class UpRight extends AbstractDirection {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UpRight(Coordinate c) {
		super(c);
		super.angle = Math.toRadians(315);
	}

	@Override
	public boolean next() {
		return c.incXdecY();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.incXdecY();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() < c.width - 1 && c.getY() > 0) {
			return new Coordinate(c.getX() + 1, c.getY() - 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public String toString() {
		return "UpRight{" + "c=" + c + '}';
	}
}
