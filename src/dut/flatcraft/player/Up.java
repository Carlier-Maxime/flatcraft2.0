package dut.flatcraft.player;

public class Up extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Up(Coordinate c) {
		super(c);
		super.angle = Math.toRadians(270);
	}

	@Override
	public boolean next() {
		return c.decY();
	}

	@Override
	public Coordinate getNext() {
		Coordinate c2 = new Coordinate(c);
		c2.decY();
		return c2;
	}

	@Override
	public Coordinate toDig() {
		System.out.println("Diging Up");
		if (c.getY() > 0) {
			return new Coordinate(c.getX(), c.getY() - 1, c.width, c.height);
		}
		return new Coordinate(c);
	}
}
