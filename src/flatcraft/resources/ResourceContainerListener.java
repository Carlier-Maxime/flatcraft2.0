package dut.flatcraft.resources;

import java.io.Serializable;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Observer")
public interface ResourceContainerListener extends Serializable {

	void update(ResourceContainer source);
}
