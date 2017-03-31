package dut.flatcraft;

import javax.swing.ImageIcon;

public class TransformableResource extends Resource {

    private final Resource digBlock;

    public TransformableResource(String name, ImageIcon blockAppearance, Resource digBlock, int hardness,
            ToolType toolType) {
        super(name, blockAppearance, hardness, toolType);
        this.digBlock = digBlock;
    }

    @Override
    public Resource digBlock() {
        return this.digBlock;
    }
}