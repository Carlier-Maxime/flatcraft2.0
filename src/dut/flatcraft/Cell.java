package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface Cell {

	ImageIcon getImage();
	
	JLabel getUI();
	
	String getName();
	
	MineElement getType();
	
	boolean manage(Player p);
	
	boolean dig(Player p);
	
	boolean canBeReplacedBy(Cell c, Player p);
}
