package dut.flatcraft.ui;

final class AcceptResourceTransfert extends AbstractResourceTransfert {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final CraftTable craftTable;

	/**
	 * @param craftTable
	 */
	AcceptResourceTransfert(CraftTable craftTable) {
		this.craftTable = craftTable;
	}

	@Override
	public void onNewContainer(ResourceContainerUI comp) {
		this.craftTable.register(comp);
	}

	@Override
	public void afterReceivingResource() {
		this.craftTable.processCrafting();
	}
}