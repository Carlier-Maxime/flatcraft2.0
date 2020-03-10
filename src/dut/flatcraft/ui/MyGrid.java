package dut.flatcraft.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import dut.flatcraft.Cell;
import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import dut.flatcraft.MineUtils;
import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.player.Coordinate;
import dut.flatcraft.player.Player;

public class MyGrid extends JComponent implements KeyListener {

	private static final int CELL_SIZE = MineUtils.DEFAULT_IMAGE_SIZE;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private GameMap map;
	private Player player;

	public MyGrid(int height, int width, CellFactory factory, MapGenerator generator) {

		map = generator.generate(width, height, factory);
		player = Player.createPlayer(map);
		setLayout(new GridLayout(height, width));
		for (int i = 0; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				add(map.getAt(i, j).getUI());
			}
		}
		checkPhysics();
		addKeyListener(this);
		setFocusable(true);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(map.getWidth() * CELL_SIZE, map.getHeight() * CELL_SIZE);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		player.paint(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// we are only interested in keyPressed()
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean needsUpdate = false;
		boolean needsToCheckVisible = false;
		Coordinate old = player.getPosition();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP:
			player.up();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN:
			player.down();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT:
			if (e.isShiftDown()) {
				player.previousInHand();
			} else {
				player.left();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT:
			if (e.isShiftDown()) {
				player.nextInHand();
			} else {
				player.right();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_SPACE:
			needsUpdate = player.next();
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_CONTROL:
			digOrFill();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_E:
			if (execute()) {
				needsUpdate = true;
				needsToCheckVisible = true;
			}
			break;
		// Start - QD Implementation
		case KeyEvent.VK_D:
			player.moveRight();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_Q:
			player.moveLeft();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		// End - QD Implementation
		case KeyEvent.VK_F1:
			displayHelp();
			break;
		case KeyEvent.VK_M:
			player.decHealth();
			break;
		case KeyEvent.VK_P:
			player.incHealth();
			break;
		default:
			// do nothing
		}
		if (needsUpdate) {
			e.consume();

			checkPhysics();

			Coordinate current = player.getPosition();

			map.getAt(old.getY(), old.getX()).getUI().repaint();
			map.getAt(current.getY(), current.getX()).getUI().repaint();

			if (needsToCheckVisible) {
				scrollMap(current);
			}

		}
	}

	private void scrollMap(Coordinate current) {
		Rectangle visible = getVisibleRect();
		if (current.getX() * CELL_SIZE > visible.x + visible.width) {
			Logger.getAnonymousLogger().fine(() -> current.getX() * CELL_SIZE + "/" + (visible.x + visible.width));
			scrollRectToVisible(new Rectangle(visible.x + CELL_SIZE, visible.y, visible.width, visible.height));
		}
		if (current.getX() * CELL_SIZE < visible.x) {
			Logger.getAnonymousLogger().fine(() -> current.getX() * CELL_SIZE + "/" + (visible.x + visible.width));
			scrollRectToVisible(new Rectangle(visible.x - CELL_SIZE, visible.y, visible.width, visible.height));
		}
	}

	private void displayHelp() {
		String help = "<html><h1>How to play?</h1>" + "It's simple:<ul>"
				+ "<li>Change direction using directional arrows</li>"
				+ "<li>Use a tool or put something on the map using the <pre>CTRL</pre> key</li>"
				+ "<li>Change the current tool or resource using <pre>SHIFT</pre>+left or right key</li>"
				+ "<li>Move resources from or to the inventory, craft table, furnace using drag'n'drop</li>"
				+ "<li>Move resources on the craft table with (unit) or without (half available) <pre>CTRL</pre> key</li>"
				+ "<li>Execute an action on the cell in front of you using the <pre>e</pre> key</li>" + "</ul>";
		JOptionPane.showMessageDialog(null, help);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// we are only interested in keyPressed()
	}

	public boolean digOrFill() {
		Coordinate toDig = player.toDig();
		Cell cellToDig = map.getAt(toDig.getY(), toDig.getX());
		Optional<Cell> result = player.getHand().action(player, cellToDig);
		if (result.isPresent()) {
			map.setAt(toDig.getY(), toDig.getX(), result.get());
			if (player.getHand().mustBeChanged()) {
				player.nextInHand();
			}
			player.next();
			return true;
		}
		return false;
	}

	private boolean execute() {
		Coordinate toExecute = player.toDig();
		Cell cellToExecute = map.getAt(toExecute.getY(), toExecute.getX());
		return cellToExecute.execute();
	}

	public void checkPhysics() {
		Coordinate down;
		do {
			down = player.lookingDown.toDig();
		} while (map.getAt(down.getY(), down.getX()).manage(player));

	}
}
