package dut.flatcraft;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JComponent;

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
		player = new Player(map);
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
			needsUpdate = digOrFill();
			needsToCheckVisible = true;
			break;
		default:
			// do nothing
		}
		if (needsUpdate) {
			e.consume();

			checkPhysics();

			Coordinate current = player.getPosition();

			map.getAt(old.y, old.x).getUI().repaint();
			map.getAt(current.y, current.x).getUI().repaint();

			repaint();

			if (needsToCheckVisible) {
				Rectangle visible = getVisibleRect();
				if (current.x * CELL_SIZE > visible.x + visible.width) {
					Logger.getAnonymousLogger().info(current.x * CELL_SIZE + "/" + (visible.x + visible.width));
					scrollRectToVisible(new Rectangle(visible.x + CELL_SIZE, visible.y, visible.width, visible.height));
				}
				if (current.x * CELL_SIZE < visible.x) {
					Logger.getAnonymousLogger().info(current.x * CELL_SIZE + "/" + (visible.x + visible.width));
					scrollRectToVisible(new Rectangle(visible.x - CELL_SIZE, visible.y, visible.width, visible.height));
				}
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private boolean digOrFill() {
		Coordinate toDig = player.toDig();
		Cell cellToDig = map.getAt(toDig.y, toDig.x);
		Optional<Cell> result = player.getHand().action(player, cellToDig);
		if (result.isPresent()) {
			map.setAt(toDig.y, toDig.x,result.get());
			if (player.getHand().mustBeChanged()) {
				player.nextInHand();
			}
			return true;
		}
		return false;
	}

	private void checkPhysics() {
		Coordinate down;
		do {
			down = player.down.toDig();
		} while (map.getAt(down.y, down.x).manage(player));

	}
}
