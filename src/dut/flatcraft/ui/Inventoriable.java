package dut.flatcraft.ui;

import fr.univartois.migl.utils.DesignPattern;

/**
 * Something that can be added to an Inventory.
 */
public interface Inventoriable {
	@DesignPattern(name = "Double Dispatch")
	void addTo(Inventory inventory);
}
