package flatcraft.player;

public class Left extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Left(Coordinate c) {
		super(c, Math.toRadians(180));
	}

	@Override
	public boolean next() {
		return c.decX();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.decX();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() > 0) {
			return new Coordinate(c.getX() - 1, c.getY(), c.width, c.height);
		}
		return new Coordinate(c);
	}
}
