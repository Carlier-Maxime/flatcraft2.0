package dut.flatcraft;

public class Up extends AbstractDirection {

	public Up(Coordinate c) {
		super(c);
	}
	
	@Override
	public boolean next() {
		if (c.y > 0) {
			c.y--;
			return true;
		}
		return false;
	}

	@Override
	public int dx(int dx) {
		return dx+16;
	}
	
	@Override
	public int dw(int dw) {
		return 8;
	}

	@Override
	public Coordinate toDig() {
		if (c.y > 0) {
			return new Coordinate(c.x,c.y-1,c.width,c.height);
		}
		return new Coordinate(c);
	}
}
