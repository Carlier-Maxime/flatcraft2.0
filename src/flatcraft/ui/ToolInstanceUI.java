package flatcraft.ui;

import static flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import flatcraft.tools.ToolInstance;
import flatcraft.tools.ToolInstanceListener;

/**
 * A graphical representation of a tool instance.
 * It displays the life of the tool on top of the tool itself.
 */
public class ToolInstanceUI extends JComponent implements ToolInstanceListener, InventoriableUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ToolInstance tool;

	public ToolInstanceUI(ToolInstance tool) {
		this.tool = tool;
		this.tool.addListener(this);
		setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE));
		setBorder(BorderFactory.createEmptyBorder());
		setToolTipText(tool.getType().getName());
		setFocusable(false);
	}

	public ToolInstance getMineTool() {
		return tool;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tool.getImage().getImage(), 0, 0, null);
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
			if (parent != null) {
				parent.remove(this);
				this.setTransferHandler(null);
				parent.invalidate();
				parent.repaint();
			}
		} else {
			repaint();
		}
	}

	@Override
	public Inventoriable getHandable() {
		return getMineTool();
	}

    @Override
    public JComponent getGraphicalComponent() {
        return this;
    }
}
