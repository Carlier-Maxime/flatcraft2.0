package dut.flatcraft;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		float factor = 1.0f;
		hourOfTheDay = (hourOfTheDay + 1) % 24;
		switch (hourOfTheDay) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			factor = 0.5f;
			break;
		case 7:
			factor = 0.55f;
			break;
		case 8:
			factor = 0.6f;
			break;
		case 9:
			factor = 0.7f;
			break;
		case 10:
			factor = 0.8f;
			break;
		case 11:
			factor = 0.9f;
			break;
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
			factor = 1.0f;
			break;
		case 18:
			factor = 0.9f;
			break;
		case 19:
			factor = 0.8f;
			break;
		case 20:
			factor = 0.7f;
			break;
		case 21:
			factor = 0.6f;
			break;
		case 22:
			factor = 0.55f;
			break;
		case 23:
			factor = 0.5f;
			break;
		default:
			throw new IllegalStateException("A day does not last more than 24 hours");
		}
		VaryingImageIcon.setFactor(factor);
		hourLabel.setText(hourString());
		frame.repaint();
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static void positionCraftTable(Dimension screenSize, JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x + button.getWidth() - dialog.getWidth(),
					screenSize.height - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	private static void positionFurnace(Dimension screenSize, JButton button, JDialog dialog) {
		if (dialog.isVisible()) {
			dialog.setVisible(false);
		} else {
			Point pos = button.getLocation();
			dialog.setLocation(pos.x, screenSize.height - 70 - dialog.getHeight());
			dialog.setVisible(true);
		}
	}

	public static void main(String[] args) {
		MapGenerator generator = new TerrilDecorator(new TreeDecorator(new SimpleGenerator(), 10, 5), 5);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		MyGrid grid = new MyGrid((screenSize.height - 150) / 40, 120, new ResourceCellFactory(), generator);
		GlassPaneWrapper glassPaneWrapper = new GlassPaneWrapper(grid);
		glassPaneWrapper.activateGlassPane(true);
		JScrollPane scrollpane = new JScrollPane(glassPaneWrapper, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(40);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(40);
		scrollpane.setDoubleBuffered(true);
		frame.add(BorderLayout.CENTER, scrollpane);

		JPanel south = new JPanel();
		south.add(hourLabel);

		JDialog craft = new JDialog(frame, "Craft Table");
		craft.add(new CraftTable(grid.getPlayer()));
		craft.pack();
		craft.setLocation(screenSize.width / 2 - craft.getWidth() / 2, screenSize.height / 2 - craft.getHeight() / 2);

		JDialog cook = new JDialog(frame, "Furnace");
		cook.add(new Furnace(grid.getPlayer()));
		cook.pack();

		JButton cookButton = new JButton(MineUtils.getImage("furnace_front"));
		cookButton.addActionListener(e -> positionFurnace(screenSize, cookButton, cook));
		cookButton.setFocusable(false);
		cookButton.setToolTipText("Furnace");
		south.add(cookButton);

		south.add(grid.getPlayer().getInventoryUI());

		JButton craftButton = new JButton("Craft");
		craftButton.addActionListener(e -> positionCraftTable(screenSize, craftButton, craft));
		craftButton.setFocusable(false);
		craftButton.setToolTipText("Craft Table");
		south.add(craftButton);

		frame.add(BorderLayout.SOUTH, south);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		VaryingImageIcon.startSimulation(5, Main::updateHour);
	}
}
