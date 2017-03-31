package dut.flatcraft;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		MyGrid grid = new MyGrid(19,120,new ResourceCellFactory(), new TerrilDecorator(new TreeDecorator(new SimpleGenerator(),10,5),5));
		JScrollPane scrollpane = new JScrollPane(grid,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.getVerticalScrollBar().setUnitIncrement(40);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(40);
	    scrollpane.setDoubleBuffered(true);
		frame.add(BorderLayout.CENTER,scrollpane);
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		JDialog craft = new JDialog(frame, "Craft Table");
		craft.add(new CraftTable(grid.getPlayer()));
		craft.pack();
        
		JButton craftButton = new JButton("Craft");
		craftButton.addActionListener(e -> craft.setVisible(true));
		
		south.add(BorderLayout.WEST, craftButton);
		south.add(BorderLayout.CENTER, grid.getPlayer().getInventoryUI());
		
		JDialog cook = new JDialog(frame, "Furnace");
		cook.add(new Furnace(grid.getPlayer()));
		cook.pack();
		
		JButton cookButton = new JButton("Cook");
		cookButton.addActionListener(e -> cook.setVisible(true));
		
		south.add(BorderLayout.EAST, cookButton);
		frame.add(BorderLayout.SOUTH,south);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
