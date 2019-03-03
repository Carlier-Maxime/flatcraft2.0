package dut.flatcraft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {

	private static void positionCraftTable(Dimension screenSize, JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x, screenSize.height - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	private static void positionFurnace(Dimension screenSize, JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x + button.getWidth() - dialog.getWidth(),
					screenSize.height - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		MyGrid grid = new MyGrid((screenSize.height - 100) / 40, 120, new ResourceCellFactory(),
				new TerrilDecorator(new TreeDecorator(new SimpleGenerator(), 10, 5), 5));
		JScrollPane scrollpane = new JScrollPane(grid, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(40);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(40);
		scrollpane.setDoubleBuffered(true);
		frame.add(BorderLayout.CENTER, scrollpane);
		JPanel south = new JPanel();

		JDialog craft = new JDialog(frame, "Craft Table");
		craft.add(new CraftTable(grid.getPlayer()));
		craft.pack();
		craft.setLocation(screenSize.width / 2 - craft.getWidth() / 2, screenSize.height / 2 - craft.getHeight() / 2);

		JButton craftButton = new JButton("Craft");
		craftButton.addActionListener(e -> positionCraftTable(screenSize, craftButton, craft));
		craftButton.setFocusable(false);

		south.add(BorderLayout.WEST, craftButton);
		south.add(BorderLayout.CENTER, grid.getPlayer().getInventoryUI());

		JDialog cook = new JDialog(frame, "Furnace");
		cook.add(new Furnace(grid.getPlayer()));
		cook.pack();

		JButton cookButton = new JButton("Cook");
		cookButton.addActionListener(e -> positionFurnace(screenSize, cookButton, cook));
		cookButton.setFocusable(false);

		south.add(BorderLayout.EAST, cookButton);
		frame.add(BorderLayout.SOUTH, south);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
