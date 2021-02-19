package dut.flatcraft.ui;

import fr.univartois.migl.utils.DesignPattern;

public interface Inventoriable {
	@DesignPattern(name = "Double Dispatch")
	void addTo(Inventory inventory);
}
