package dut.flatcraft.resources;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dut.flatcraft.tools.ToolType;

/**
 * Resource allowing to propagate a call to the execute() method
 * to neighboring cells.
 */
public class TraversableResource extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TraversableResource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
		super(name, appearance, hardness, toolType);
	}

	@Override
	public TraversableResourceInstance newInstance() {
		return new TraversableResourceInstance(this);
	}

	@Override
	public TraversableResourceInstance newInstance(JLabel label) {
		return new TraversableResourceInstance(this, label);
	}
}
