package dut.flatcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Inventory {

	private Map<String,ResourceContainer> containers = new HashMap<>();
	
	private JPanel ui = new JPanel();
	
	private int current = 0;
	
	private List<Handable> handables = new ArrayList<>();
	
	public Inventory() {
		ui.setBorder(BorderFactory.createEmptyBorder());
		ToolInstance tool = MineUtils.createToolByName("woodaxe").newInstance();
		handables.add(tool);
		ui.add(new ToolInstanceUI(tool));
		tool = MineUtils.createToolByName("woodpick").newInstance();
		handables.add(tool);
		ui.add(new ToolInstanceUI(tool));
	}
	
	public Handable getElementInTheHand() {
		return handables.get(current);
	}
	
	public void next() {
		current++;
		if (current==handables.size()) {
			current = 0;
		};
	}
	
	public void previous() {
		if (current == 0) {
			current = handables.size()-1;
		} else {
			current--;
		}
	}
	
	public void add(Cell cell) {
		ResourceContainer container = containers.get(cell.getName());
		if (container == null) {
			// create container
			ResourceContainerUI cui = new ResourceContainerUI((Resource)cell.getType());
			ui.add(cui);
			ui.revalidate();
			container = cui.getResourceContainer();
			containers.put(cell.getName(), container);
			handables.add(container);
		}
		container.inc();
	}
	
	public JComponent getUI() {
		return ui;
	}
}
