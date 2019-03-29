package dut.flatcraft;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * ImageIcon whose
 * 
 * @author leberre
 *
 */
public class VaryingImageIcon extends ImageIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static float factor = 1.0f;

	private final BufferedImage modified = new BufferedImage(MineUtils.DEFAULT_IMAGE_SIZE, MineUtils.DEFAULT_IMAGE_SIZE,
			BufferedImage.TYPE_INT_ARGB);

	public static final void startSimulation(int delayInSeconds, ActionListener listener) {
		Timer timer = new Timer(delayInSeconds * 1000, listener);
		timer.start();
	}

	public static void setFactor(float f) {
		factor = f;
	}

	public static float getFactor() {
		return factor;
	}

	public VaryingImageIcon() {
		super();
	}

	public VaryingImageIcon(byte[] imageData, String description) {
		super(imageData, description);
	}

	public VaryingImageIcon(byte[] imageData) {
		super(imageData);
	}

	public VaryingImageIcon(Image image, String description) {
		super(image, description);
	}

	public VaryingImageIcon(Image image) {
		super(image);
	}

	public VaryingImageIcon(String filename, String description) {
		super(filename, description);
	}

	public VaryingImageIcon(String filename) {
		super(filename);
	}

	public VaryingImageIcon(URL location, String description) {
		super(location, description);
	}

	public VaryingImageIcon(URL location) {
		super(location);
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		if (getImageObserver() == null) {
			g.drawImage(getImage(), x, y, c);
		} else {
			g.drawImage(getImage(), x, y, getImageObserver());
		}
	}

	@Override
	public Image getImage() {
		Image original = super.getImage();
		RescaleOp op = new RescaleOp(factor, 0, null);
		modified.createGraphics().drawImage(original, 0, 0, null);
		op.filter(modified, modified);
		return modified;
	}

	public Image getOriginalImage() {
		return super.getImage();
	}
}
