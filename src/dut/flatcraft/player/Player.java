package dut.flatcraft.player;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JComponent;

import dut.flatcraft.GameMap;
import dut.flatcraft.MapRegistry;
import dut.flatcraft.ui.Inventoriable;
import dut.flatcraft.ui.Inventory;
import fr.univartois.migl.utils.DesignPattern;

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
	public final Direction lookingUp;
	public final Direction lookingDown;
	public final Direction lookingLeft;
	public final Direction lookingRight;

	public final Direction lookingUpRight;
	public final Direction lookingDownRight;
	public final Direction lookingUpLeft;
	public final Direction lookingDownLeft;

	/**
	 * The current direction.
	 */
	private Direction currentDirection;

	private Inventory inventory = new Inventory();

	private Player(GameMap map) {
		this.position = new Coordinate(0, 0, map.getWidth(), map.getHeight());
		lookingLeft = new Left(position);
		lookingRight = new Right(position);
		lookingUp = new Up(position);
		lookingDown = new Down(position);
		lookingUpRight = new UpRight(position);
		lookingDownRight = new DownRight(position);
		lookingUpLeft = new UpLeft(position);
		lookingDownLeft = new DownLeft(position);
		currentDirection = lookingRight;
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

	public void addToInventory(Inventoriable inventoriable) {
		inventoriable.addTo(inventory);
	}

	public void up() {
		currentDirection = lookingUp;
		System.out.println("Up");
	}

	public void down() {
		currentDirection = lookingDown;
		System.out.println("Down");
	}

	public void left() {
		currentDirection = lookingLeft;
		System.out.println("Left");
	}

	public void right() {
		currentDirection = lookingRight;
		System.out.println("Right");
	}

	public void upRight() {
		currentDirection = lookingUpRight;
		System.out.println("UpRight");
	}

	public void downRight() {
		currentDirection = lookingDownRight;
		System.out.println("DownRight");
	}

	public void upLeft() {
		currentDirection = lookingUpLeft;
		System.out.println("UpLeft");
	}

	public void downLeft() {
		currentDirection = lookingDownLeft;
		System.out.println("DownLeft");
	}

	// Start - QD Implementation

	public Coordinate getRight(){
		return lookingRight.toDig();
	}

	public Coordinate getLeft(){
		return lookingLeft.toDig();
	}

	public Coordinate getTop(){
		return lookingUp.toDig();
	}

	public Coordinate getTopRight(){
		return new Coordinate(getRight().getX(),getRight().getY()-1,getRight().width,getRight().height);
	}

	public Coordinate getTopLeft(){
		return new Coordinate(getLeft().getX(),getLeft().getY()-1,getLeft().width,getLeft().height);
	}

	public void moveRight() {
		if(isEmptyCell(getRight())){
			lookingRight.next();
		}
		else if(isEmptyCell(getTop()) && isEmptyCell(getTopRight())){
			lookingUpRight.next();
		}
	}
	public void moveLeft() {
		if(isEmptyCell(getLeft())){
			lookingLeft.next();
		}
		else if(isEmptyCell(getTop()) && isEmptyCell(getTopLeft())){
			lookingUpLeft.next();
		}
	}

	private boolean isEmptyCell(Coordinate c){
		return (MapRegistry.getMap().getAt(c.getY(),c.getX()).getName().equals("empty"));
	}

	// End - QD Implementation

	public Direction opposite() {
		if (currentDirection == lookingUp)
			return lookingDown;
		if (currentDirection == lookingDown)
			return lookingUp;
		if (currentDirection == lookingLeft)
			return lookingRight;
		assert currentDirection == lookingRight;
		return lookingLeft;
	}

	public boolean next() {
		return currentDirection.next();
	}

	public Direction getDirection() {
		return currentDirection;
	}

	public Coordinate toDig() {
		return inventory.getElementInTheHand().toDig(currentDirection);
	}

	public void paint(Graphics g) {
		g.drawImage(inventory.getElementInTheHand().getImage().getImage(), position.getX() * DEFAULT_IMAGE_SIZE,
				position.getY() * DEFAULT_IMAGE_SIZE, null);
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

	/**
	 * Creates a player on a given map
	 * 
	 * @param map a GameMap
	 * @return the player interacting with that map
	 */
	@DesignPattern(name = "factory method")
	public static Player createPlayer(GameMap map) {
		player = new Player(map);
		return player;
	}

	/**
	 * Allow access to the player object anywhere in the code.
	 * 
	 * As such, it is really a registry design pattern, not a singleton design
	 * pattern: the method does not enforce that only one instance of a player will
	 * be created.
	 * 
	 * @return the last player created using the {@link #createPlayer(GameMap)}
	 * @see {@link MapRegistry} for equivalent functionaly for GameMap.
	 */
	@DesignPattern(name = "registry", url = "https://martinfowler.com/eaaCatalog/registry.html")
	public static Player instance() {
		if (player == null) {
			throw new IllegalStateException("The instance of player has not been created yet!");
		}
		return player;
	}
}
