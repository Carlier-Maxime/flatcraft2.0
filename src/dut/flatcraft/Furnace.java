package dut.flatcraft;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A crafting table.
 * 
 * 
 * @author leberre
 *
 */
public class Furnace extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<String, String> RULES = new HashMap<>();

	static {
		MineUtils.fillRulesFromFile("/furnacerules.txt",RULES);
	}
	private JPanel craftPanel;
	private JPanel result;

	private Player player;
	
	/**
	 * Build an empty furnace.
	 */
	public Furnace(Player player) {
		this.player = player;
		this.setLayout(new BorderLayout());
		craftPanel = new JPanel();
		craftPanel.setLayout(new GridLayout(2, 1));

		createGrid();
		createResultSpace();
	}

	private void createResultSpace() {
		result = new JPanel();
		result.setLayout(new FlowLayout());
		result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		result.setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE + 10, DEFAULT_IMAGE_SIZE + 10));
		JButton add = new JButton("Ajouter à l'inventaire");
		add.addActionListener(e -> addToInventory());
		add(BorderLayout.EAST, result);
		add(BorderLayout.SOUTH, add);
	}

	private void addToInventory() {
		Component[] components = result.getComponents();
		if (components.length == 1) {
			Component c = components[0];
			Handable handable;
			if (c instanceof ResourceContainerUI) {
				handable = (((ResourceContainerUI) c).getResourceContainer());
			} else {
				handable = ((ToolInstanceUI) c).getMineTool();
			}
			player.addToInventory(handable);
			consumeOneItem();
			result.remove(c);
			processCooking();
			result.revalidate();
			result.repaint();
		}
	}

	private void createGrid() {
		JPanel tableCell= new JPanel();
		craftPanel.add(tableCell);
		tableCell.setTransferHandler(new AcceptOreTransfert(this));
		tableCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tableCell.setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE + 10, DEFAULT_IMAGE_SIZE + 10));
		tableCell= new JPanel();
		craftPanel.add(tableCell);
		tableCell.setTransferHandler(new AcceptCombustibleTransfert(this));
		tableCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tableCell.setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE + 10, DEFAULT_IMAGE_SIZE + 10));
		add(BorderLayout.CENTER, craftPanel);
	}

	void processCooking() {
		JPanel panel = (JPanel) craftPanel.getComponent(0);
		if (panel.getComponentCount() == 0) {
			System.out.println("No ore");
			return;
		}
		
		ResourceContainer ore;
		ResourceContainerUI rcui = (ResourceContainerUI) panel.getComponents()[0];
		ore = rcui.getResourceContainer();
		if (ore.getQuantity() == 0) {
			System.out.println("No ore in container");
			return;
		}
		panel = (JPanel) craftPanel.getComponent(1);
		if (panel.getComponentCount() == 0) {
			System.out.println("No combustible");
			return;
		}
		ResourceContainer combustible;
		rcui = (ResourceContainerUI) panel.getComponents()[0];
		combustible = rcui.getResourceContainer();
		if (combustible.getQuantity() == 0) {
			System.out.println("No combustible in container");
			return;
		}
		String key = RULES.get(ore.getBlock().getName());
		System.out.println("Should produce "+key);
		AbstractButton crafted = new ResourceContainerUI(MineUtils.getResourceByName(key), 1);
		result.removeAll();
		result.add(crafted);
		result.revalidate();
		result.repaint();
	}

	void consumeOneItem() {
		JPanel panel;
		Component[] comps = craftPanel.getComponents();
		assert comps.length == 2;
		for (int i = 0; i < 2; i++) {
			panel = (JPanel) comps[i];
			if (panel.getComponentCount() == 1) {
				ResourceContainerUI rcui = (ResourceContainerUI) panel.getComponents()[0];
				rcui.getResourceContainer().consume();
				if (rcui.getResourceContainer().getQuantity() == 0) {
					panel.removeAll();
					panel.revalidate();
					panel.repaint();
				}
			}
		}
	}
}
