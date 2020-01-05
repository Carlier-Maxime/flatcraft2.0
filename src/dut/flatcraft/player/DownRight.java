package dut.flatcraft.player;

public class DownRight extends AbstractDirection {
	private static final long serialVersionUID = 1L;

	public DownRight(Coordinate c) {
		super(c);
		super.angle = Math.toRadians(45);
	}

	@Override
	public boolean next() {
		return c.incXincY();
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() < c.width - 1 && c.getY() < c.height - 1) {
			return new Coordinate(c.getX() + 1, c.getY() + 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.incXincY();
		return c2;
	}

	@Override
	public String toString() {
		return "DownRight{" + "c=" + c + '}';
	}
}
