package flatcraft.player;

public class Right extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Right(Coordinate c) {
		super(c, Math.toRadians(0));
	}

	@Override
	public boolean next() {
		return c.incX();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.incX();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() < c.width - 1) {
			return new Coordinate(c.getX() + 1, c.getY(), c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public String toString() {
		return "Right{" + "c=" + c + '}';
	}
}
