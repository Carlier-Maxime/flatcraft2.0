package dut.flatcraft.tools;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Define a particular kind of tool.
 * 
 * @author leberre
 *
 */
public class Tool implements Transferable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final DataFlavor TOOL_FLAVOR = new DataFlavor(Tool.class, "tool");

	private final ImageIcon image;

	private final int initialLife;

	private final ToolType tooltype;

	private final int decrement;

	private final String name;

	public Tool(String name, ImageIcon image, int life, ToolType toolType, int decrement) {
		this.name = name;
		this.image = image;
		this.initialLife = life;
		this.tooltype = toolType;
		this.decrement = decrement;
	}

	public String getName() {
		return name;
	}

	public int getInitialLife() {
		return initialLife;
	}

	public ImageIcon getImage() {
		return image;
	}

	public ToolType getTooltype() {
		return tooltype;
	}

	public int getDecrement() {
		return decrement;
	}

	public ToolInstance newInstance() {
		return new ToolInstance(this);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { TOOL_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return TOOL_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return this;
	}
}
