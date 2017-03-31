package dut.flatcraft;

public class Left extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Left(Coordinate c) {
		super(c);
	}
	
	@Override
	public boolean next() {
		if (c.x > 0) {
			c.x--;
			return true;
		}
		return false;
	}
	
	@Override
	public Coordinate toDig() {
		if (c.x > 0) {
			return new Coordinate(c.x-1,c.y,c.width,c.height);
		}
		return new Coordinate(c);
	}	
	
	@Override
	public int dy(int dy) {
		return dy+16;
	}
	
	@Override
	public int dh(int dh) {
		return 8;
	}
}
