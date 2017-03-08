package dut.flatcraft;

import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

final class AcceptResourceTransfert extends TransferHandler {
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// partie to
	@Override
	public boolean canImport(TransferSupport support) {
	    return support.isDataFlavorSupported(ResourceContainer.RESOURCE_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
	    System.err.println("Importing data");
	    if (support.isDrop()) {
	        JPanel source = (JPanel) support.getComponent();
	        try {
	            ResourceContainerUI comp;
	            ResourceContainer rc = (ResourceContainer) support.getTransferable()
	                    .getTransferData(ResourceContainer.RESOURCE_FLAVOR);
	            if (support.getDropAction() == MOVE || rc.getQuantity() == 1) {
	                comp = new ResourceContainerUI(rc);
	            } else {
	                comp = new ResourceContainerUI(rc.getBlock().getType(), rc.getQuantity() / 2);
	            }
	            source.removeAll();
	            comp.setTransferHandler(this.craftTable.from);
	            comp.addMouseListener(this.craftTable.mouselistener);
	            source.add(comp);
	            this.craftTable.processCrafting();
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