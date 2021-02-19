package dut.flatcraft;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * An ImageIcon which may be updated, with a way
 * to retrieve it's initial state.
 */
public class CustomImageIcon extends ImageIcon {

	private static final long serialVersionUID = 1L;

	public CustomImageIcon() {
		super();
	}

	public CustomImageIcon(Image image) {
		super(image);
	}

	public Image getOriginalImage() {
		return super.getImage();
	}
}
