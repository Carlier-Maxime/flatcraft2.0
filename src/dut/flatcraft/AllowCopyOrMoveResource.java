package dut.flatcraft;

import java.awt.Container;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

final class AllowCopyOrMoveResource extends TransferHandler {

	/**
	 * 
	 */
	private final CraftTable craftTable;

	/**
	 * @param craftTable
	 */
	AllowCopyOrMoveResource(CraftTable craftTable) {
		this.craftTable = craftTable;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getSourceActions(JComponent c) {
	    return MOVE | COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
	    return ((ResourceContainerUI) c).getResourceContainer();
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
	    ResourceContainer rc = ((ResourceContainerUI) source).getResourceContainer();
	    if (action == MOVE || rc.getQuantity() == 0) {
	        Container container = source.getParent();
	        container.remove(source);
	        container.revalidate();
	        container.repaint();
	    } else {
	        rc.consume(rc.getQuantity() / 2);
	    }
	    this.craftTable.processCrafting();
	}
}