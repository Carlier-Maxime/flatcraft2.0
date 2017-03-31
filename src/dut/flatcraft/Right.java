package dut.flatcraft;

public class Right extends AbstractDirection {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Right(Coordinate c) {
		super(c);
	}
	
	@Override
	public int dx(int dx) {
		return dx+20;
	}
	
	@Override
	public int dy(int dy) {
		return dy+16;
	}
	
	@Override
	public int dh(int dh) {
		return 8;
	}
	
	@Override
	public boolean next() {
		if (c.x < c.width - 1) {
			c.x++;
			return true;
		}
		return false;
	}

	@Override
	public Coordinate toDig() {
		if (c.x < c.width - 1) {
			return new Coordinate(c.x+1,c.y,c.width,c.height);		
		}
		return new Coordinate(c);
	}
}
