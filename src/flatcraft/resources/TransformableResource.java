package flatcraft.resources;

import javax.swing.ImageIcon;

import flatcraft.tools.ToolType;

/**
 * A transformable resource is a resource found on the map which is transformed
 * once taken from the map (think about coal for instance).
 * 
 * In this case, we do not have this.equals(this.digBlock()).
 * 
 * @author leberre
 *
 */
public class TransformableResource extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Resource digBlock;

	public TransformableResource(String name, ImageIcon appearance, Resource digBlock, int hardness, int hardnessLevel, ToolType toolType, boolean needToolType) {
		super(name, appearance, hardness, (byte) hardnessLevel, toolType, needToolType);
		this.digBlock = digBlock;
	}

	public TransformableResource(String name, ImageIcon blockAppearance, Resource digBlock, int hardness, ToolType toolType) {
		this(name, blockAppearance, digBlock, hardness, 0, toolType, false);
	}

	public TransformableResource(String name, ImageIcon appearance, Resource digBlock, int hardness, int hardnessLevel, ToolType toolType) {
		this(name, appearance, digBlock, hardness, hardnessLevel, toolType, false);
	}

	public TransformableResource(String name, ImageIcon appearance,  Resource digBlock, int hardness, ToolType toolType, boolean needToolType) {
		this(name, appearance, digBlock, hardness, 0, toolType, needToolType);
	}

	@Override
	public Resource digBlock() {
		return this.digBlock;
	}

    @Override
	public boolean equals(Object o) {
		if (o instanceof TransformableResource) {
			return super.equals(o)&& digBlock.equals(((TransformableResource)o).digBlock);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode()/2+ this.digBlock.hashCode()/2;
	}
}
