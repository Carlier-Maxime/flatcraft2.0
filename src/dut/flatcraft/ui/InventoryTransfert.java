package dut.flatcraft.ui;

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import dut.flatcraft.player.Handable;
import dut.flatcraft.resources.ResourceContainer;
import dut.flatcraft.tools.Tool;
import dut.flatcraft.tools.ToolInstance;

final class InventoryTransfert extends TransferHandler {
	/**
	 * 
	 */
	private final Inventory inventory;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryTransfert(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if (c instanceof ResourceContainerUI) {
			ResourceContainer rc = ((ResourceContainerUI) c).getResourceContainer();
			return rc.duplicate();
		} else {
			return ((ToolInstanceUI) c).getMineTool();
		}
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		if (source instanceof ResourceContainerUI) {
			ResourceContainer container = ((ResourceContainerUI) source).getResourceContainer();
			if (action == MOVE) {
				container.consumeAll();
			} else if (action == COPY) {
				if (container.getQuantity() == 1) {
					container.consume(1);
				} else {
					container.consume(container.getQuantity() / 2);
				}
			}
		} else if ((source instanceof ToolInstanceUI) && (action == MOVE || action == COPY)) {
			ToolInstanceUI toolUI = (ToolInstanceUI) source;
			inventory.getUI().remove(toolUI);
			inventory.getHandables().remove(toolUI.getMineTool());
			inventory.getUI().revalidate();
			inventory.getUI().repaint();
		}
	}

	// partie to
	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(ResourceContainer.RESOURCE_FLAVOR)
				|| support.isDataFlavorSupported(Tool.TOOL_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
		if (support.isDrop()) {
			JComponent source = (JComponent) support.getComponent();
			for (Component comp : inventory.getUI().getComponents()) {
				if (comp == source) {
					return false;
				}
			}
			try {
				Handable transferedHandable = (Handable) support.getTransferable()
						.getTransferData(ResourceContainer.RESOURCE_FLAVOR);
				if (transferedHandable instanceof ToolInstance) {
					inventory.add((ToolInstance) transferedHandable);
				} else {
					ResourceContainer sourceContainer = (ResourceContainer) transferedHandable;
					inventory.addResource(sourceContainer);
					source.revalidate();
					source.repaint();
				}
				return true;
			} catch (Exception e) {
				Logger.getAnonymousLogger().info(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
}