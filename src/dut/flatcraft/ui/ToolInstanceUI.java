package dut.flatcraft.ui;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import dut.flatcraft.tools.ToolInstance;
import dut.flatcraft.tools.ToolInstanceListener;

public class ToolInstanceUI extends JButton implements ToolInstanceListener, InventoriableUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ToolInstance tool;

	public ToolInstanceUI(ToolInstance tool) {
		this.tool = tool;
		this.setIcon(tool.getImage());
		this.tool.addListener(this);
		setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE));
		setBorder(BorderFactory.createEmptyBorder());
		setFocusable(false);
	}

	public ToolInstance getMineTool() {
		return tool;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle rect = g.getClipBounds();
		g.setColor(Color.GRAY);
		g.fillRect(rect.x + 5, rect.y + DEFAULT_IMAGE_SIZE / 2, DEFAULT_IMAGE_SIZE - 10, 3);
		if (tool.getCurrentLife() > 0) {
			g.setColor(Color.YELLOW);
			g.fillRect(rect.x + 5, DEFAULT_IMAGE_SIZE / 2,
					(tool.getCurrentLife() * DEFAULT_IMAGE_SIZE - 10) / tool.getType().getInitialLife(), 3);
		}
	}

	@Override
	public void update(ToolInstance source) {
		if (tool.getCurrentLife() == 0) {
			Container parent = this.getParent();
			parent.remove(this);
			this.setTransferHandler(null);
			parent.invalidate();
			parent.repaint();
		} else {
			repaint();
		}
	}

	@Override
	public Inventoriable getHandable() {
		return getMineTool();
	}

}
