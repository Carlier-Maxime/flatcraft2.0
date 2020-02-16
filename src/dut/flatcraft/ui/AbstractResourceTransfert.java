package dut.flatcraft.ui;

import java.awt.datatransfer.DataFlavor;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import dut.flatcraft.resources.ResourceContainer;

abstract class AbstractResourceTransfert extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(getFlavor());
	}

	public abstract void onNewContainer(ResourceContainerUI comp);

	public abstract void afterReceivingResource();

	public DataFlavor getFlavor() {
		return ResourceContainer.RESOURCE_FLAVOR;
	}

	@Override
	public boolean importData(TransferSupport support) {
		if (support.isDrop()) {
			JPanel source = (JPanel) support.getComponent();
			try {
				ResourceContainerUI comp;
				ResourceContainer rc = (ResourceContainer) support.getTransferable().getTransferData(getFlavor());
				if (support.getDropAction() == MOVE || rc.getQuantity() == 1) {
					comp = new ResourceContainerUI(rc);
				} else {
					comp = new ResourceContainerUI(rc.getBlock().getType(), rc.getQuantity() / 2);
				}
				if (source.getComponentCount() > 0) {
					ResourceContainerUI existing = (ResourceContainerUI) source.getComponent(0);
					if (existing.getResourceContainer().getResource() == rc.getResource()) {
						existing.getResourceContainer().inc(rc.getQuantity());
					} else if (existing.getResourceContainer().getQuantity() == 0) {
						source.removeAll();
						onNewContainer(comp);
						source.add(comp);
					} else {
						return false;
					}
				} else {
					onNewContainer(comp);
					source.add(comp);
				}
				afterReceivingResource();
				source.revalidate();
				source.repaint();
				return true;
			} catch (Exception e) {
				Logger.getAnonymousLogger().warning(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
}