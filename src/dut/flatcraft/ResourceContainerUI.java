package dut.flatcraft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ResourceContainerUI extends JButton implements ResourceContainerListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final ResourceContainer container;

    public ResourceContainerUI(Resource block) {
        this(block, 0);
    }

    public ResourceContainerUI(Resource block, int quantity) {
        this(new ResourceContainer(block, quantity));
    }

    public ResourceContainerUI(ResourceContainer container) {
        this.container = container;
        container.addListener(this);
        this.setIcon(container.getImage());
        setPreferredSize(new Dimension(MineUtils.DEFAULT_IMAGE_SIZE, MineUtils.DEFAULT_IMAGE_SIZE));
        setBorder(BorderFactory.createEmptyBorder());
        setToolTipText(container.getResource().getName());
    }

    public ResourceContainer getResourceContainer() {
        return container;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int qty = container.getQuantity();
        if (qty > 0) {
            super.paintComponent(g);
            Rectangle rect = g.getClipBounds();
            g.setColor(Color.YELLOW);
            g.fillOval(rect.x +  20, rect.y + 20, 20, 20);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize()));
            if (qty < 10) {
                g.drawString(String.valueOf(qty), rect.x + 27, rect.y + 34);
            } else {
                g.drawString(String.valueOf(qty), rect.x + 23, rect.y + 34);
            }
        }
    }

    @Override
    public void update(ResourceContainer source) {
        repaint();
    }
}
