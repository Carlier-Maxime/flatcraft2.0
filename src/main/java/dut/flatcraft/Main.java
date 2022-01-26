package dut.flatcraft;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.map.SimpleGenerator;
import dut.flatcraft.map.TerrilDecorator;
import dut.flatcraft.map.TreeDecorator;
import dut.flatcraft.player.Level;
import dut.flatcraft.player.LevelListener;
import dut.flatcraft.player.Player;
import dut.flatcraft.ui.CraftTable;
import dut.flatcraft.ui.Furnace;
import dut.flatcraft.ui.MyGrid;

public class Main {

	private static final JFrame frame = new JFrame("FLATCRAFT 2022 - Student project - F1 to get help");

	private static int hourOfTheDay = 12;

	private static final JLabel hourLabel = new JLabel(hourString());

    private static MyGrid grid;

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
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		DisplayMode displayMode = ge.getDefaultScreenDevice().getDisplayMode();
		Dimension screenSize = new Dimension(displayMode.getWidth(), displayMode.getHeight());

		frame.add(BorderLayout.CENTER, createMap(screenSize, createMapGenerator()));
		frame.add(BorderLayout.SOUTH, createStatusBar());
		frame.pack();

		if (frame.getWidth() > screenSize.width) {
			frame.setSize(screenSize.width, frame.getHeight());
		}

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setExtendedState(JFrame.MAXIMIZED_HORIZ);

		frame.setVisible(true);
        grid.displayHelp();
        VaryingImageIcon.startSimulation(5, Main::updateHour);
	}

	private static JScrollPane createMap(Dimension screenSize, MapGenerator generator) {
		grid = new MyGrid((screenSize.height * 80 / 100) / DEFAULT_IMAGE_SIZE, 120, new ResourceCellFactory(), generator);
		GlassPaneWrapper glassPaneWrapper = new GlassPaneWrapper(grid);
		glassPaneWrapper.activateGlassPane(true);
		return MineUtils.scrollPane(glassPaneWrapper);
	}

	private static JPanel createStatusBar() {
		JLabel healthui = new JLabel("Health: 100");
		Player.instance().addListener(p -> healthui.setText("Health: " + p.getHealth()));

		JLabel experienceui = new JLabel("xp: 0/10");
		JLabel levelui = new JLabel("level: 0");

		LevelListener listener = new LevelListener(){
			@Override
			public void onXpChange(Level level) {
				experienceui.setText("xp: " + level.getXp() + "/" + level.getBound());
			}

			@Override
			public void onLevelChange(Level level) {
				levelui.setText("level: " + level.getLevel());
				experienceui.setText("xp: " + level.getXp() + "/" + level.getBound());
			}
		};

		Player.instance().getLevel().addListener(listener);

		JPanel south = new JPanel();
		south.add(healthui);
		south.add(experienceui);
		south.add(levelui);
		south.add(hourLabel);


		JDialog craft = new JDialog(frame, "Craft Table");
		craft.add(new CraftTable(Player.instance()));
		craft.pack();

		JDialog cook = new JDialog(frame, "Furnace");
		cook.add(new Furnace(Player.instance()));
		cook.pack();

		JButton cookButton = new JButton(MineUtils.getImage("furnace_front"));
		cookButton.addActionListener(e -> positionFurnace(cookButton, cook));
		cookButton.setFocusable(false);
		cookButton.setToolTipText("Furnace");
		south.add(cookButton);

		south.add(Player.instance().getInventoryUI());

		JButton craftButton = new JButton("Craft");
		craftButton.addActionListener(e -> positionCraftTable(craftButton, craft));
		craftButton.setFocusable(false);
		craftButton.setToolTipText("Craft Table");
		south.add(craftButton);
		return south;
	}

	private static MapGenerator createMapGenerator() {
		return new TerrilDecorator(new TreeDecorator(new SimpleGenerator(), 10, 5), 5);
	}
}
