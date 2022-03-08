package flatcraft.resources;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import flatcraft.tools.ToolType;

/**
 * An executable resource type, i.e. a resource which
 * reacts to the execute key.
 * 
 * @author leberre
 *
 */
public class ExecutableResource extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExecutableResource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
		super(name, appearance, hardness, toolType);
	}

	@Override
	public ExecutableResourceInstance newInstance() {
		return new ExecutableResourceInstance(this);
	}

	@Override
	public ExecutableResourceInstance newInstance(JLabel label) {
		return new ExecutableResourceInstance(this, label);
	}

}
