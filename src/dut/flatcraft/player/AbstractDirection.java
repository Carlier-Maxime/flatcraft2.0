package dut.flatcraft.player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public abstract class AbstractDirection implements Direction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Coordinate c;
	protected double angle;

	public AbstractDirection(Coordinate c) {
		this.c = c;
	}

	@Override
	public void paint(Graphics g) {
		int dx = c.getX() * 40 + 20;
		int dy = c.getY() * 40 + 17;
		int dw = 20;
		int dh = 6;
		Rectangle rect = new Rectangle(dx, dy, dw, dh);
		Path2D.Double path = new Path2D.Double();
		path.append(rect, false);

		AffineTransform t = new AffineTransform();
		t.rotate(angle, dx, dy + 3);
		path.transform(t);

		Rectangle nextRect = new Rectangle(getNext().getX() * 40, getNext().getY() * 40, 40, 40);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.PINK);
//		g.fill3DRect(dx, dy, dw, dh, true);
		g2d.fill(path);
		g2d.setColor(Color.YELLOW);
		g2d.draw(nextRect);
	}

	@Override
	public Coordinate nextForResource() {
		return new Coordinate(getNext());
	}
}
