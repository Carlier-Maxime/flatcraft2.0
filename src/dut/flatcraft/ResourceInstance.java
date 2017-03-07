package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ResourceInstance implements MineElementInstance, Cell {

	private final Resource resourceType;

	private int hardness;
	private JLabel label;

	public ResourceInstance(Resource type) {
		this(type, new JLabel());
	}

	public ResourceInstance(Resource type, JLabel label) {
		this.resourceType = type;
		this.hardness = type.getHardness();
		this.label = label;
		label.setIcon(type.getImage());
	}

	public Resource getType() {
		return resourceType;
	}

	@Override
	public ImageIcon getImage() {
		return resourceType.getImage();
	}

	public boolean dig(ToolInstance tool) {
		hardness -= tool.getImpactWithBlock();
		return hardness <= 0;
	}

	@Override
	public JLabel getUI() {
		return label;
	}

	public void setUI(JLabel label) {
		this.label = label;
		label.setIcon(resourceType.getImage());
	}
	
	@Override
	public boolean manage(Player p) {
		return false;
	}

	@Override
	public String getName() {
		return resourceType.getName();
	}

	@Override
	public boolean dig(Player p) {
		if (dig((ToolInstance) p.getHand())) {
			p.addToInventory(resourceType.digBlock().newInstance());
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeReplacedBy(Cell c, Player p) {
		return false;
	}

}
