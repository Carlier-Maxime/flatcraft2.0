package dut.flatcraft.ui;

import java.awt.datatransfer.DataFlavor;

import dut.flatcraft.resources.OreContainer;

/**
 * Drag and drop of ore to the furnace.
 */
final class AcceptOreTransfert extends AbstractResourceTransfert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Furnace furnace;

	public AcceptOreTransfert(Furnace furnace) {
		this.furnace = furnace;
	}

	@Override
	public DataFlavor getFlavor() {
		return OreContainer.ORE_FLAVOR;
	}

	@Override
	public void onNewContainer(ResourceContainerUI comp) {
		// do nothing
	}

	@Override
	public void afterReceivingResource() {
		furnace.processCooking();
	}
}