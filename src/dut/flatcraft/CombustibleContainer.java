package dut.flatcraft;

import java.awt.datatransfer.DataFlavor;

public class CombustibleContainer extends ResourceContainer {

	public static final DataFlavor COMBUSTIBLE_FLAVOR = new DataFlavor(ResourceContainer.class, "resourcecontainer");

	public CombustibleContainer(Resource block, int quantity) {
		super(block, quantity);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {COMBUSTIBLE_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return COMBUSTIBLE_FLAVOR.equals(flavor);
	}
	
	@Override
	public CombustibleContainer clone() {
		return new CombustibleContainer(getResource(), getQuantity());
	}
}
