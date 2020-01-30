package dut.flatcraft.player;

public class DownLeft extends AbstractDirection {
	private static final long serialVersionUID = 1L;

	public DownLeft(Coordinate c) {
		super(c, Math.toRadians(135));
	}

	@Override
	public boolean next() {
		return c.decXincY();
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() > 0 && c.getY() < c.height - 1) {
			return new Coordinate(c.getX() - 1, c.getY() + 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.decXincY();
		return c2;
	}

	@Override
	public String toString() {
		return "UpRight{" + "c=" + c + '}';
	}
}
