package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class NormalCell implements Cell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon image;
	private JLabel label;

	public NormalCell(ImageIcon image) {
		this.image = image;
		this.label = new JLabel(image);
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public boolean manage(Player p) {
		// do nothing
		return false;
	}

	@Override
	public JLabel getUI() {
		return label;
	}

	@Override
	public String getName() {
		return "noname";
	}

	@Override
	public Resource getType() {
		return null;
	}

	@Override
	public boolean dig(Player p) {
		p.next();
		return false;
	}

	@Override
	public boolean canBeReplacedBy(Cell c, Player p) {
		return false;
	}

}
