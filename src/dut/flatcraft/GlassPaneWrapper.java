package dut.flatcraft;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import dut.flatcraft.player.Coordinate;
import dut.flatcraft.player.Player;
import dut.flatcraft.ui.MyGrid;

public class GlassPaneWrapper extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	private JPanel glassPanel = new JPanel();
	MouseEvent mouseEvent;

	public GlassPaneWrapper(MyGrid grid) {
		glassPanel.setOpaque(false);
		glassPanel.setVisible(false);
		glassPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				updateDirection(e, grid);
				mouseEvent = e;
			}
		});

		glassPanel.addMouseWheelListener(e -> {
			if (e.getWheelRotation() == 1) {
				Player.instance().previousInHand();
			} else {
				Player.instance().nextInHand();
			}
			grid.paintPlayer(false);
		});

		glassPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				Coordinate old = Player.instance().getPosition();
				Coordinate oldNext = Player.instance().getDirection().getNext();
				grid.digOrFill();
				grid.checkPhysics();
				grid.paint(old);
				grid.paint(oldNext);
				grid.paintPlayer(true);
			}
		});

		glassPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				grid.keyPressed(e);
				if (mouseEvent != null) {
					updateDirection(mouseEvent, grid);
				}
			}
		});

		glassPanel.setFocusable(true);

		grid.setSize(grid.getPreferredSize());
		add(grid, JLayeredPane.DEFAULT_LAYER);
		add(glassPanel, JLayeredPane.PALETTE_LAYER);

		glassPanel.setPreferredSize(grid.getPreferredSize());
		glassPanel.setSize(grid.getPreferredSize());
		setPreferredSize(grid.getPreferredSize());
	}

	private void updateDirection(MouseEvent e, MyGrid grid) {
		Coordinate old = grid.getPlayer().getPosition();
		Coordinate oldNext = Player.instance().getDirection().getNext();
		int px = old.getX() * MineUtils.DEFAULT_IMAGE_SIZE + 20;
		int py = old.getY() * MineUtils.DEFAULT_IMAGE_SIZE + 20;
		int mx = e.getX();
		int my = e.getY();

		Point p = new Point(px, py);
		Point m = new Point(mx, my);
		Point a = new Point(mx, py);

		double pm = p.distance(m);
		double pa = p.distance(a);

		double angle = Math.acos(pa / pm);

		if (py > my) { // vers le haut
			if (px > mx) { // vers le haut gauche
				hautGauche(grid, angle);
			} else { // vers le haut droite
				hautDroite(grid, angle);
			}
		} else { // vers le bas
			if (px > mx) { // vers le bas gauche
				basGauche(grid, angle);
			} else { // vers le bas droite
				basDroite(grid, angle);
			}
		}
		grid.paint(old);
		grid.paint(oldNext);
		grid.paintPlayer(true);
	}

	private void basDroite(MyGrid grid, double angle) {
		if (angle > 3 * Math.PI / 8) {
			grid.getPlayer().down();
		} else if (angle < Math.PI / 8) {
			grid.getPlayer().right();
		} else {
			grid.getPlayer().downRight();
		}
	}

	private void basGauche(MyGrid grid, double angle) {
		if (angle > 3 * Math.PI / 8) {
			grid.getPlayer().down();
		} else if (angle < Math.PI / 8) {
			grid.getPlayer().left();
		} else {
			grid.getPlayer().downLeft();
		}
	}

	private void hautDroite(MyGrid grid, double angle) {
		if (angle > 3 * Math.PI / 8) {
			grid.getPlayer().up();
		} else if (angle < Math.PI / 8) {
			grid.getPlayer().right();
		} else {
			grid.getPlayer().upRight();
		}
	}

	private void hautGauche(MyGrid grid, double angle) {
		if (angle > 3 * Math.PI / 8) {
			grid.getPlayer().up();
		} else if (angle < Math.PI / 8) {
			grid.getPlayer().left();
		} else {
			grid.getPlayer().upLeft();
		}
	}

	public void activateGlassPane(boolean activate) {
		glassPanel.setVisible(activate);
		if (activate) {
			glassPanel.requestFocusInWindow();
			glassPanel.setFocusTraversalKeysEnabled(false);
		}
	}
}
