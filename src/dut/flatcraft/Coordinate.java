package dut.flatcraft;

class Coordinate {
	int x, y;
	final int width;
	final int height;
	
	Coordinate(Coordinate c) {
		this(c.x,c.y,c.width,c.height);
	}
	
	Coordinate(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	Coordinate area(Coordinate c) {
		int dx,dy;
		if (x<c.x) {
			dx=x;
		} else {
			dx = c.x;
		}
		if (y<c.y) {
			dy=y;
		} else {
			dy = c.y;
		}
		return new Coordinate(dx,dy,width,height);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}