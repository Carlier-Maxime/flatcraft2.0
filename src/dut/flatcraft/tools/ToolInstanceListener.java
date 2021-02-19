package dut.flatcraft.tools;

import java.io.Serializable;

import fr.univartois.migl.utils.DesignPattern;

/**
 * A listener of changes in a particular tool instance.
 */
@DesignPattern(name = "Observer")
public interface ToolInstanceListener extends Serializable {

	void update(ToolInstance source);
}
