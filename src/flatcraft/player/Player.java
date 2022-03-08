package flatcraft.player;

import static flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import flatcraft.GameMap;
import flatcraft.MapRegistry;
import flatcraft.ui.Inventoriable;
import flatcraft.ui.Inventory;
import fr.univartois.migl.utils.DesignPattern;

/**
 * The player controlled on the GameMap.
 * 
 * @author leberre
 *
 */
public class Player implements Paintable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * The player.
     */

    private static Player player;

    /**
     * The actual position of the player.
     * 
     * @author gregoire delacroix
     *
     */

    private Coordinate position;

    /*
     * The four cardinal directions.
     */
    public final Direction lookingUp;
    public final Direction lookingDown;
    public final Direction lookingLeft;
    public final Direction lookingRight;

    /*
     * The four diagonal directions (provided by 2019's students)
     */
    public final Direction lookingUpRight;
    public final Direction lookingDownRight;
    public final Direction lookingUpLeft;
    public final Direction lookingDownLeft;

    /**
     * The current direction.
     */
    private Direction currentDirection;

    /**
     * The player health (from 0 to 100)
     */
    private byte health = 100;

    /**
     * The player experience level
     */
    private Level level = new Level();

    /**
     * The creation of inventory.
     */

    private Inventory inventory = new Inventory();

    /**
     * The health listeners.
     */
    private List<HealthListener> listeners = new ArrayList<>();

    /**
     * Constructor of the player. Set the player at the initial coordinate which is
     * the top left corner: the physics will put it in the first non empty cell.
     */

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

    /**
     * Add a new health listener to the player.
     * 
     * @param hl an health listener
     */
    public void addListener(HealthListener hl) {
        this.listeners.add(hl);
    }

    /**
     * Remove an health listener to the player.
     * 
     * @param hl an health listener
     */
    public void removeListener(HealthListener hl) {
        this.listeners.remove(hl);
    }

    /**
     * Get the health of the player.
     */
    public byte getHealth() {
        return health;
    }

    /**
     * Get the experience level of the player.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * increase the health of the player.
     */
    public void incHealth() {
        health += 5;
        if (health > 100) {
            health = 100;
        }
        fireHealthChange();
    }

    /**
     * decreases the health of the player.
     */
    public void decHealth() {
        health -= 5;
        if (health < 0) {
            health = 0;
        }
        fireHealthChange();
    }

    /**
     * set the health of the player.
     * 
     * @param health the new health, between O and 100
     */
    public void setHealth(byte health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException();
        }
        this.health = health;
        fireHealthChange();
    }

    private void fireHealthChange() {
        for (HealthListener hl : listeners) {
            hl.onHealthChange(this);
        }
    }

    /**
     * @return the actual object in hand.
     */

    public Handable getHand() {
        return inventory.getElementInTheHand();
    }

    /**
     * @return the next element in hand.
     */

    public void nextInHand() {
        do {
            inventory.next();
        } while (inventory.getElementInTheHand().mustBeChanged());
    }

    /**
     * @return the previous element in hand.
     */

    public void previousInHand() {
        do {
            inventory.previous();
        } while (inventory.getElementInTheHand().mustBeChanged());
    }

    /**
     * Add object in inventory.
     */

    public void addToInventory(Inventoriable inventoriable) {
        inventoriable.addTo(inventory);
    }

    /**
     * Set current direction to up.
     */

    public void up() {
        currentDirection = lookingUp;
    }

    /**
     * Set current direction to down.
     */

    public void down() {
        currentDirection = lookingDown;
    }

    /**
     * Set current direction to left.
     */

    public void left() {
        currentDirection = lookingLeft;
    }

    /**
     * Set current direction to rigth.
     */

    public void right() {
        currentDirection = lookingRight;
    }

    /**
     * Set current direction to up right.
     */

    public void upRight() {
        currentDirection = lookingUpRight;
    }

    /**
     * Set current direction to down right.
     */

    public void downRight() {
        currentDirection = lookingDownRight;
    }

    /**
     * Set current direction to up left.
     */

    public void upLeft() {
        currentDirection = lookingUpLeft;
    }

    /**
     * Set current direction to down left.
     */

    public void downLeft() {
        currentDirection = lookingDownLeft;
    }

    // Start - QD Implementation

    /**
     * @return block coordinate at the right to dig
     */

    public Coordinate getRight() {
        return lookingRight.toDig();
    }

    /**
     * @return block coordinate at the left to dig
     */

    public Coordinate getLeft() {
        return lookingLeft.toDig();
    }

    /**
     * @return block coordinate at the top to dig
     */

    public Coordinate getTop() {
        return lookingUp.toDig();
    }

    /**
     * @return block coordinate at the top right to dig
     */

    public Coordinate getTopRight() {
        return lookingUpRight.toDig();
    }

    /**
     * @return block coordinate at the top left to dig
     */

    public Coordinate getTopLeft() {
        return lookingUpLeft.toDig();
    }

    /**
     * move the player right if possible or top right or not
     */

    public void moveRight() {
        if (isEmptyCell(getRight())) {
            lookingRight.next();
        } else if (isEmptyCell(getTop()) && isEmptyCell(getTopRight())) {
            lookingUpRight.next();
        }
    }

    /**
     * move the player left if possible or top left or not
     */

    public void moveLeft() {
        if (isEmptyCell(getLeft())) {
            lookingLeft.next();
        } else if (isEmptyCell(getTop()) && isEmptyCell(getTopLeft())) {
            lookingUpLeft.next();
        }
    }

    /**
     * @return boolean if a cell is empty or not
     */

    private static boolean isEmptyCell(Coordinate c) {
        return (MapRegistry.getMap().getAt(c.getY(), c.getX()).getName().equals("empty"));
    }

    // End - QD Implementation

    /**
     * @return the opposite direction
     */

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

    /**
     * move the player if it's possible
     * 
     * @return boolean
     */

    public boolean next() {
        if (currentDirection == lookingUpRight) {
            if (!isEmptyCell(lookingUp.getNext())) {
                return false;
            }
        } else if (currentDirection == lookingDownRight) {
            if (!isEmptyCell(lookingRight.getNext())) {
                return false;
            }
        } else if (currentDirection == lookingDownLeft) {
            if (!isEmptyCell(lookingLeft.getNext())) {
                return false;
            }
        } else if (currentDirection == lookingUpLeft && !isEmptyCell(lookingUp.getNext())) {
            return false;
        }
        return currentDirection.next();
    }

    /**
     * @return the current direction
     */

    public Direction getDirection() {
        return currentDirection;
    }

    /**
     * @return coordinate of element to dig
     */

    public Coordinate toDig() {
        return inventory.getElementInTheHand().toDig(currentDirection);
    }

    /**
     * print the current player object in hand
     */

    @Override
    public void paint(Graphics g) {
        g.drawImage(inventory.getElementInTheHand().getImage().getImage(), position.getX() * DEFAULT_IMAGE_SIZE,
                position.getY() * DEFAULT_IMAGE_SIZE, null);
        // application of state design pattern: the arrow is displayed depending of the
        // internal state
        currentDirection.paint(g);
    }

    /**
     * @return the new position of the player
     */

    public Coordinate getPosition() {
        return new Coordinate(position);
    }

    /**
     * @return all the inventory
     */

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
     * @see {@link MapRegistry} for equivalent functionality for GameMap.
     */
    @DesignPattern(name = "registry", url = "https://martinfowler.com/eaaCatalog/registry.html")
    public static Player instance() {
        if (player == null) {
            throw new IllegalStateException("The instance of player has not been created yet!");
        }
        return player;
    }
}
