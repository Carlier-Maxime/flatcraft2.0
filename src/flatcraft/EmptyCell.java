package flatcraft;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import flatcraft.player.Player;
import flatcraft.resources.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A cell representing the absence of resource on the map.
 * 
 * @author leberre
 *
 */
public class EmptyCell implements Cell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int index=0;

	private ImageIcon image;
	private JLabel label;

	private static final Resource TYPE = MineUtils.getResourceByName("air");

	public EmptyCell(ImageIcon image, JLabel label) {
		this.image = image;
		assombrir();
		this.label = label;
		label.setIcon(this.image);
		label.setToolTipText("Empty");
		index+=1;
	}

	public EmptyCell(ImageIcon image) {
		this(image, new JLabel());
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public boolean manage(Player p) {
		return p.lookingDown.next();
	}

	@Override
	public JLabel getUI() {
		return label;
	}

	@Override
	public String getName() {
		return "empty";
	}

	@Override
	public Resource getType() {
		return TYPE;
	}

	@Override
	public boolean dig(Player p) {
		p.next();
		return false;
	}

	@Override
	public boolean canBeReplacedBy(Cell c, Player p) {
		return true;
	}

	private void assombrir(){
		if (image.getIconWidth()<0 || image.getIconHeight()<0) return;
		BufferedImage img = new BufferedImage(image.getIconWidth(),image.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics gr = img.getGraphics();
		gr.drawImage(image.getImage(),0,0,null);
		gr.dispose();
		for (int i=0; i<img.getWidth(); i++){
			for (int j=0; j<img.getHeight(); j++){
				Color c = new Color(img.getRGB(i,j),true);
				int r = c.getRed()/2;
				int g = c.getGreen()/2;
				int b = c.getBlue()/2;
				img.setRGB(i,j,new Color(r,g,b,c.getAlpha()).getRGB());
			}
		}
		image = new VaryingImageIcon(img);
	}
}
