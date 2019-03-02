package dut.flatcraft;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JComponent;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Player player;

	private Coordinate position;

	/*
	 * The four possible directions.
	 */
	final Direction lookingUp;
	final Direction lookingDown;
	final Direction lookingLeft;
	final Direction lookingRight;

	/**
	 * The current direction.
	 */
	Direction currentDirection;

	private Inventory inventory = new Inventory();

	private GameMap map;

	private Player(GameMap map) {
		this.position = new Coordinate(0, 0, map.getWidth(), map.getHeight());
		lookingLeft = new Left(position);
		lookingRight = new Right(position);
		lookingUp = new Up(position);
		lookingDown = new Down(position);
		currentDirection = lookingRight;
		this.map = map;
	}

	public GameMap getMap() {
		return map;
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
		currentDirection = lookingUp;
	}

	void down() {
		currentDirection = lookingDown;
	}

	void left() {
		currentDirection = lookingLeft;
	}

	void right() {
		currentDirection = lookingRight;
	}

	Direction opposite() {
		if (currentDirection == lookingUp)
			return lookingDown;
		if (currentDirection == lookingDown)
			return lookingUp;
		if (currentDirection == lookingLeft)
			return lookingRight;
		assert currentDirection == lookingRight;
		return lookingLeft;
	}

	boolean next() {
		return currentDirection.next();
	}

	Direction getDirection() {
		return currentDirection;
	}

	Coordinate toDig() {
		return inventory.getElementInTheHand().toDig(currentDirection);
	}

	public void paint(Graphics g) {
		g.drawImage(inventory.getElementInTheHand().getImage().getImage(), position.x * DEFAULT_IMAGE_SIZE,
				position.y * DEFAULT_IMAGE_SIZE, null);
		// application of state design pattern: the arrow is displayed depending of the
		// internal state
		currentDirection.paint(g);
	}

	public Coordinate getPosition() {
		return new Coordinate(position);
	}

	public JComponent getInventoryUI() {
		return inventory.getUI();
	}

	public static void createPlayer(GameMap map) {
		player = new Player(map);
	}

	public static Player instance() {
		if (player == null) {
			throw new IllegalStateException("The instance of player has not been created yet!");
		}
		return player;
	}
}
