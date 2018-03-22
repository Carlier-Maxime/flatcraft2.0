package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface Cell extends Serializable {

	ImageIcon getImage();

	JLabel getUI();

	String getName();

	MineElement getType();

	boolean manage(Player p);

	boolean dig(Player p);

	boolean canBeReplacedBy(Cell c, Player p);
}
