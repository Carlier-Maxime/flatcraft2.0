package dut.flatcraft.resources;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dut.flatcraft.tools.ToolType;

/**
 * Example of ExecutableResource, i.e. a resource which reacts to an action.
 * 
 * @author leberre
 *
 */
public class ChestResource extends ExecutableResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChestResource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
		super(name, appearance, hardness, toolType);
	}

	@Override
	public ExecutableResourceInstance newInstance() {
		ExecutableResourceInstance instance = super.newInstance();
		instance.setRunnable(() -> JOptionPane.showMessageDialog(null, new JLabel("Le beau coffre"), "Attention",
				JOptionPane.PLAIN_MESSAGE));
		return instance;
	}

	@Override
	public ExecutableResourceInstance newInstance(JLabel label) {
		ExecutableResourceInstance instance = super.newInstance(label);
		instance.setRunnable(() -> JOptionPane.showMessageDialog(null, new JLabel("Le beau coffre"), "Attention",
				JOptionPane.PLAIN_MESSAGE));
		return instance;
	}

}
