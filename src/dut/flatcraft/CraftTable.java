package dut.flatcraft;

import static dut.flatcraft.MineUtils.DEFAULT_IMAGE_SIZE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

/**
 * A crafting table.
 * 
 * 
 * @author leberre
 *
 */
public class CraftTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<String, String> RULES = new HashMap<>();

	/*
	 * The few crafting rules supported by the craftable. Ideally, the rules
	 * should be read from a property file, that way the rules could be changed
	 * without recompiling the program.
	 */
	static {
		RULES.put("tree_empty_empty_empty_empty_empty_empty_empty_empty", "wood 4");
		RULES.put("wood_empty_empty_empty_empty_empty_empty_empty_empty", "stick 4");
		RULES.put("wood_wood_wood_empty_stick_empty_empty_stick_empty", "woodpick");
		RULES.put("wood_wood_empty_wood_stick_empty_empty_stick_empty", "woodaxe");
		RULES.put("cobble_cobble_cobble_empty_stick_empty_empty_stick_empty", "stonepick");
		RULES.put("cobble_cobble_empty_cobble_stick_empty_empty_stick_empty", "stoneaxe");
		RULES.put("steel_lingot_steel_lingot_steel_lingot_empty_stick_empty_empty_stick_empty", "steelpick");
	}

	private JPanel craftPanel;
	private JPanel result;

	private TransferHandler from;
	private TransferHandler fromResult;
	private MouseListener dndMouseListener = new MyMouseAdapter();

	private Player player;

	/**
	 * Build an empty craft table.
	 */
	public CraftTable(Player player) {
		this.player = player;
		this.setLayout(new BorderLayout());
		craftPanel = new JPanel();
		craftPanel.setLayout(new GridLayout(3, 3));

		from = new AllowCopyOrMoveResource(this);

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
			processCrafting();
			result.revalidate();
			result.repaint();
		}
	}

	private void createGrid() {
		TransferHandler to = new AcceptResourceTransfert(this);
		JPanel tableCell;
		for (int i = 0; i < 9; i++) {
			craftPanel.add(tableCell = new JPanel());
			tableCell.setTransferHandler(to);
			tableCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			tableCell.setPreferredSize(new Dimension(DEFAULT_IMAGE_SIZE + 10, DEFAULT_IMAGE_SIZE + 10));
		}
		add(BorderLayout.CENTER, craftPanel);
	}

	void processCrafting() {
		String key = buildCraftingKey();
		String value = RULES.get(key);
		AbstractButton crafted = null;
		if (value != null) {
			String[] pieces = value.split(" ");
			if (pieces.length == 2) {
				// resource quantity
				Resource resource = MineUtils.getResourceByName(pieces[0]);
				int qty = Integer.parseInt(pieces[1].trim());
				crafted = new ResourceContainerUI(resource, qty);
			} else {
				assert pieces.length == 1;
				// tool
				Tool tool = MineUtils.createToolByName(pieces[0]);
				crafted = new ToolInstanceUI(tool.newInstance());
			}
			crafted.setTransferHandler(fromResult);
			crafted.addMouseListener(dndMouseListener);
		}
		result.removeAll();
		if (crafted == null) {
			System.err.println("Humm, cannot do anything for " + key);
		} else {
			result.add(crafted);
		}
		result.revalidate();
		result.repaint();
	}

	void consumeOneItem() {
		JPanel panel;
		Component[] comps = craftPanel.getComponents();
		assert comps.length == 9;
		for (int i = 0; i < 9; i++) {
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

	void register(ResourceContainerUI ui) {
		ui.setTransferHandler(from);
		ui.addMouseListener(dndMouseListener);
	}

	private String buildCraftingKey() {
		StringBuilder stb = new StringBuilder();
		JPanel panel;
		Component[] comps = craftPanel.getComponents();
		assert comps.length == 9;
		for (int i = 0; i < 9; i++) {
			panel = (JPanel) comps[i];
			if (panel.getComponentCount() == 0) {
				stb.append("empty");
			} else {
				assert panel.getComponentCount() == 1;
				ResourceContainerUI rcui = (ResourceContainerUI) panel.getComponents()[0];
				if (rcui.getResourceContainer().getQuantity() > 0) {
					stb.append(rcui.getResourceContainer().getResource().getName());
				} else {
					stb.append("empty");
				}
			}
			if (i < 8) {
				stb.append("_");
			}
		}
		return stb.toString();
	}
}
