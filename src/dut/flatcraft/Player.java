package dut.flatcraft;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JComponent;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Coordinate position;

	final Direction up, down, left, right;

	Direction direction;

	private Inventory inventory = new Inventory();

	public Player(GameMap map) {
		this.position = new Coordinate(0, 0, map.getWidth(), map.getHeight());
		left = new Left(position);
		right = new Right(position);
		up = new Up(position);
		down = new Down(position);
		direction = right;
	}

	public Handable getHand() {
		return inventory.getElementInTheHand();
	}

	public void nextInHand() {
		do {
			inventory.next();
		} while (inventory.getElementInTheHand().mustBeChanged());
	}

	public void previousInHand() {
		do {
			inventory.previous();
		} while (inventory.getElementInTheHand().mustBeChanged());
	}

	public void addToInventory(Cell cell) {
		inventory.add(cell);
	}

	public void addToInventory(Handable handable) {
		inventory.add(handable);
	}

	void up() {
		direction = up;
	}

	void down() {
		direction = down;
	}

	void left() {
		direction = left;
	}

	void right() {
		direction = right;
	}

	Direction opposite() {
		if (direction == up)
			return down;
		if (direction == down)
			return up;
		if (direction == left)
			return right;
		assert direction == right;
		return left;
	}

	boolean next() {
		return direction.next();
	}

	Direction getDirection() {
		return direction;
	}

	Coordinate toDig() {
		return inventory.getElementInTheHand().toDig(direction);
	}

	public void paint(Graphics g) {
		g.setColor(Color.PINK);
		g.drawImage(inventory.getElementInTheHand().getImage().getImage(), position.x * DEFAULT_IMAGE_SIZE,
				position.y * DEFAULT_IMAGE_SIZE, null);
		direction.paint(g);
	}

	public Coordinate getPosition() {
		return new Coordinate(position);
	}

	public JComponent getInventoryUI() {
		return inventory.getUI();
	}
}
