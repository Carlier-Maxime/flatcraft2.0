package flatcraft.ui;

/**
 * Drag and drop of combustible for the furnace.
 */
final class AcceptCombustibleTransfert extends AbstractResourceTransfert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Furnace furnace;

	public AcceptCombustibleTransfert(Furnace furnace) {
		this.furnace = furnace;
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