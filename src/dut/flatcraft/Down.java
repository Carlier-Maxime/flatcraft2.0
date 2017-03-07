package dut.flatcraft;

public class Down extends AbstractDirection {

	public Down(Coordinate c) {
		super(c);
	}

	@Override
	public boolean next() {
		if (c.y < c.height - 1) {
			c.y++;
			return true;
		}
		return false;
	}

	@Override
	public Coordinate toDig() {
		if (c.y < c.height - 1) {
			return new Coordinate(c.x,c.y+1,c.width,c.height);
		}
		return new Coordinate(c);
	}
	
	@Override
	public int dx(int dx) {
		return dx + 16;
	}

	@Override
	public int dy(int dy) {
		return dy + 20;
	}

	@Override
	public int dw(int dw) {
		return 8;
	}
}
