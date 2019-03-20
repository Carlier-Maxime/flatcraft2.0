package dut.flatcraft.player;

public class Down extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Down(Coordinate c) {
		super(c);
		super.angle = Math.toRadians(90);
	}

	@Override
	public boolean next() {
		return c.incY();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.incY();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		System.out.println("dinging down");
		if (c.getY() < c.height - 1) {
			return new Coordinate(c.getX(), c.getY() + 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public Coordinate nextForResource() {
		return new Coordinate(c);
	}
}
