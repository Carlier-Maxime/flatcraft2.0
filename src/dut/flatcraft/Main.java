package dut.flatcraft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.map.SimpleGenerator;
import dut.flatcraft.map.TerrilDecorator;
import dut.flatcraft.map.TreeDecorator;
import dut.flatcraft.ui.CraftTable;
import dut.flatcraft.ui.Furnace;
import dut.flatcraft.ui.MyGrid;

public class Main {

	private static JFrame frame = new JFrame("FLATCRAFT 2020 - Student project - F1 to get help");

	private static int hourOfTheDay = 12;

	private static JLabel hourLabel = new JLabel(hourString());

	private static String hourString() {
		return String.format("Time: %2d o'clock", hourOfTheDay);
	}

	public static final void updateHour(ActionEvent e) {
		float factor;
		hourOfTheDay = (hourOfTheDay + 1) % 24;
		if (hourOfTheDay <= 6 || hourOfTheDay >= 23) {
			factor = 0.5f;
		} else if (hourOfTheDay >= 12 && hourOfTheDay <= 17) {
			factor = 1.0f;
		} else if (hourOfTheDay < 12) {
			factor = 0.5f + 0.05f * (hourOfTheDay - 6);
		} else {
			factor = 1.0f - 0.05f * (hourOfTheDay - 12);
		}
		VaryingImageIcon.setFactor(factor);
		hourLabel.setText(hourString());
		frame.repaint();
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static void positionCraftTable(JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x + button.getWidth() - dialog.getWidth(),
					frame.getHeight() - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	private static void positionFurnace(JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x, frame.getHeight() - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MapGenerator generator = new TerrilDecorator(new TreeDecorator(new SimpleGenerator(), 10, 5), 5);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		DisplayMode displayMode = ge.getDefaultScreenDevice().getDisplayMode();
		Dimension screenSize = new Dimension(displayMode.getWidth(), displayMode.getHeight());
		MyGrid grid = new MyGrid((screenSize.height * 80 / 100) / 40, 120, new ResourceCellFactory(), generator);
		GlassPaneWrapper glassPaneWrapper = new GlassPaneWrapper(grid);
		glassPaneWrapper.activateGlassPane(true);
		JScrollPane scrollpane = new JScrollPane(glassPaneWrapper, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(40);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(40);
		scrollpane.setDoubleBuffered(true);
		frame.add(BorderLayout.CENTER, scrollpane);

		JPanel south = new JPanel();
		south.add(hourLabel);

		JDialog craft = new JDialog(frame, "Craft Table");
		craft.add(new CraftTable(grid.getPlayer()));
		craft.pack();

		JDialog cook = new JDialog(frame, "Furnace");
		cook.add(new Furnace(grid.getPlayer()));
		cook.pack();

		JButton cookButton = new JButton(MineUtils.getImage("furnace_front"));
		cookButton.addActionListener(e -> positionFurnace(cookButton, cook));
		cookButton.setFocusable(false);
		cookButton.setToolTipText("Furnace");
		south.add(cookButton);

		south.add(grid.getPlayer().getInventoryUI());

		JButton craftButton = new JButton("Craft");
		craftButton.addActionListener(e -> positionCraftTable(craftButton, craft));
		craftButton.setFocusable(false);
		craftButton.setToolTipText("Craft Table");
		south.add(craftButton);

		frame.add(BorderLayout.SOUTH, south);
		frame.pack();
		if (frame.getWidth() > screenSize.width) {
			frame.setSize(screenSize.width, frame.getHeight());
		}
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

		VaryingImageIcon.startSimulation(5, Main::updateHour);
	}
}
