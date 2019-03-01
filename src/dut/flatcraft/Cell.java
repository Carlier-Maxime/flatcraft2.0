package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A cell represents a unit space in the map. It can be filled with a resource
 * or empty (i.e. the sky, the air).
 * 
 * @author leberre
 *
 */
public interface Cell extends Serializable {

	/**
	 * Retrieve the graphical representation of the cell on the map.
	 * 
	 * @return
	 */
	ImageIcon getImage();

	/**
	 * Retrieve the graphical component for that cell.
	 * 
	 * @return
	 */
	JLabel getUI();

	String getName();

	MineElement getType();

	boolean manage(Player p);

	boolean dig(Player p);

	default boolean execute() {
		return false;
	}

	boolean canBeReplacedBy(Cell c, Player p);
}
