package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * The most basic element found in the game.
 * 
 * One instance of MineElement represents a specific "Type" using the Type
 * Object design pattern.
 * 
 * @author leberre
 *
 */
public interface MineElement extends Serializable {

	/**
	 * Retrieve the image used to represent that element on the map.
	 * 
	 * @return
	 */
	ImageIcon getImage();
}
