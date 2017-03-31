package dut.flatcraft;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

final class AcceptCombustibleTransfert extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Furnace furnace;
	
	public AcceptCombustibleTransfert(Furnace furnace) {
		this.furnace = furnace;
	}

	@Override
	public boolean canImport(TransferSupport support) {
	    return support.isDataFlavorSupported(CombustibleContainer.COMBUSTIBLE_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
	    System.err.println("Importing data");
	    if (support.isDrop()) {
	        JPanel source = (JPanel) support.getComponent();
	        try {
	            ResourceContainerUI comp;
	            ResourceContainer rc = (ResourceContainer) support.getTransferable()
	                    .getTransferData(CombustibleContainer.COMBUSTIBLE_FLAVOR);
	            if (support.getDropAction() == MOVE || rc.getQuantity() == 1) {
	                comp = new ResourceContainerUI(rc);
	            } else {
	                comp = new ResourceContainerUI(rc.getBlock().getType(), rc.getQuantity() / 2);
	            }
	            source.removeAll();
	            source.add(comp);
	            furnace.processCooking();
	            source.revalidate();
	            source.repaint();
	            return true;
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
}