package dut.flatcraft;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * ImageIcon whose intensity can be programmatically changed.
 * 
 * @author leberre
 *
 */
public class VaryingImageIcon extends ImageIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All created instances of those objects
	 */
	private static List<VaryingImageIcon> instances = new ArrayList<>();

	/**
	 * The light factor. 1.0 means normal light, 0.5 means night.
	 */
	private static float lightFactor = 1.0f;

	/**
	 * A modifiable version of the image. Reused to prevent creating too many
	 * objects.
	 */
	private final BufferedImage modified = new BufferedImage(MineUtils.DEFAULT_IMAGE_SIZE, MineUtils.DEFAULT_IMAGE_SIZE,
			BufferedImage.TYPE_INT_ARGB);

	/**
	 * Start the process for changing the display of the images.
	 * 
	 * @param delayInSeconds the delay between each call to the listener
	 * @param listener       the method called at each simulation step
	 */
	public static final void startSimulation(int delayInSeconds, ActionListener listener) {
		Timer timer = new Timer(delayInSeconds * 1000, listener);
		timer.start();
	}

	public static void setFactor(float f) {
		lightFactor = f;
		instances.forEach(VaryingImageIcon::updateImage);
	}

	public static float getFactor() {
		return lightFactor;
	}

	public VaryingImageIcon() {
		super();
	}

	public VaryingImageIcon(Image image) {
		super(image);
		instances.add(this);
		modified.createGraphics().drawImage(image, 0, 0, null);
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		if (getImageObserver() == null) {
			g.drawImage(modified, x, y, c);
		} else {
			g.drawImage(modified, x, y, getImageObserver());
		}
	}

	@Override
	public Image getImage() {
		return modified;
	}

    private void updateImage() {
		Image original = super.getImage();
		RescaleOp op = new RescaleOp(lightFactor, 0, null);
		modified.createGraphics().drawImage(original, 0, 0, null);
		op.filter(modified, modified);
    }
    
	public Image getOriginalImage() {
		return super.getImage();
	}
}
