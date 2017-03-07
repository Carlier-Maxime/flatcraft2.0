package dut.flatcraft;

import java.util.Optional;

import javax.swing.ImageIcon;

public interface Handable {

	ImageIcon getImage();
	
	Optional<Cell> action(Player p, Cell c);

	Coordinate toDig(Direction direction);
	
	boolean mustBeChanged();
}
