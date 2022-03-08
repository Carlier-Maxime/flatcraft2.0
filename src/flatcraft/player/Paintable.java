package flatcraft.player;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * Something that must be displayed on the map.
 * 
 * @author leberre
 *
 */
public interface Paintable extends Serializable {

	void paint(Graphics g);
}
