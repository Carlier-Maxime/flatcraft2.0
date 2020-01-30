package dut.flatcraft;

import javax.swing.ImageIcon;

import fr.univartois.migl.utils.DesignPattern;

/**
 * An instance of a type defined by a MineElement.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Type Object")
public interface MineElementInstance {

	ImageIcon getImage();

	MineElement getType();
}
