package dut.flatcraft.player;

import java.io.Serializable;
import java.util.Optional;

import javax.swing.ImageIcon;

import dut.flatcraft.Cell;
import dut.flatcraft.ui.Inventoriable;

/**
 * An abstraction for something that the player can have in its hand.
 * 
 * @author leberre
 *
 */
public interface Handable extends Serializable, Inventoriable {

	String getName();

	ImageIcon getImage();

	Optional<Cell> action(Player p, Cell c);

	Coordinate toDig(Direction direction);

	boolean mustBeChanged();
}
