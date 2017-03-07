package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Resource implements MineElement {

    private final ImageIcon blockAppearance;
    private int hardness;
    private final ToolType toolType;
    private final String name;
    
    public Resource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
        this.name = name;
        this.blockAppearance = appearance;
        this.setHardness(hardness);
        this.toolType = toolType;
    }

    public String getName() {
        return name;
    }

    @Override
    public ImageIcon getImage() {
        return blockAppearance;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public Resource digBlock() {
        return this;
    }

    public ToolType getToolType() {
        return toolType;
    }

    @Override
    public ResourceInstance newInstance() {
        return new ResourceInstance(this);
    }

	@Override
	public ResourceInstance newInstance(JLabel label) {
		return new ResourceInstance(this,label);
	}
}
