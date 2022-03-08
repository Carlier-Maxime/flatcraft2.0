package flatcraft.player;

public class UpLeft extends AbstractDirection {
	private static final long serialVersionUID = 1L;

	public UpLeft(Coordinate c) {
		super(c, Math.toRadians(225));
	}

	@Override
	public boolean next() {
		return c.decXdecY();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.decXdecY();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() > 0 && c.getY() > 0) {
			return new Coordinate(c.getX() - 1, c.getY() - 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public String toString() {
		return "UpRight{" + "c=" + c + '}';
	}
}
