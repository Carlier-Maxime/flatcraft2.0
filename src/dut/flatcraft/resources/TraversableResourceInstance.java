package dut.flatcraft.resources;

import java.util.Optional;

import javax.swing.JLabel;

import dut.flatcraft.Cell;
import dut.flatcraft.ExtendedGameMap;
import dut.flatcraft.MapRegistry;

/**
 * Specific implementation of a cell allowing to
 * propagate the call to the execute() method from left to right.
 * 
 * This class is given as a simple example of what can be done with
 * the new {@link ExtendedGameMap} and {@link MapRegistry}.
 */
public class TraversableResourceInstance extends ResourceInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TraversableResourceInstance(Resource type) {
		super(type);
	}

	public TraversableResourceInstance(Resource type, JLabel label) {
		super(type, label);
	}

	@Override
	public boolean execute() {
		ExtendedGameMap map = MapRegistry.getMap();
		Optional<Cell> optional = map.east(this);
		if (optional.isPresent()) {
			return optional.get().execute();
		}
		return false;
	}

}
