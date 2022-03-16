package flatcraft.resources;

import javax.imageio.ImageIO;
import javax.swing.*;

import flatcraft.Cell;
import flatcraft.player.Player;
import flatcraft.tools.ToolInstance;
import flatcraft.ui.Inventoriable;
import flatcraft.ui.Inventory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ResourceInstance implements Cell, Inventoriable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final BufferedImage[] crackAnyLength = initCrackAnyLength();

	private final Resource resourceType;

	private int hardness;
	private int hardnessIndex;
	private JLabel label;

	public ResourceInstance(Resource type) {
		this(type, new JLabel());
	}

	public ResourceInstance(Resource type, JLabel label) {
		this.resourceType = type;
		this.hardness = type.getHardness();
		this.label = label;
		label.setIcon(type.getImage());
		label.setToolTipText(type.getName());
		label.setOpaque(false);
	}

	public Resource getType() {
		return resourceType;
	}

	@Override
	public ImageIcon getImage() {
		return resourceType.getImage();
	}

	public boolean dig(ToolInstance tool) {
		hardness -= tool.getImpactWithBlock();
		return hardness <= 0;
	}

	@Override
	public JLabel getUI() {
		return getLabelHardnessMode();
	}

	public void setUI(JLabel label) {
		this.label = label;
		label.setIcon(resourceType.getImage());
		label.setToolTipText(resourceType.getName());
		label.setOpaque(false);
	}

	@Override
	public boolean manage(Player p) {
		return false;
	}

	@Override
	public String getName() {
		return resourceType.getName();
	}

	@Override
	public boolean dig(Player p) {
		ToolInstance tool = (ToolInstance) p.getHand();
		if (dig(tool)) {
			if (resourceType.isNeedToolType()){
				if (resourceType.getToolType()==tool.getType().getTooltype() || resourceType.getHardnessLevel()<=tool.getType().getLevel()) p.addToInventory(resourceType.digBlock().newInstance());
			} else p.addToInventory(resourceType.digBlock().newInstance());
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeReplacedBy(Cell c, Player p) {
		return false;
	}

	@Override
	public void addTo(Inventory inventory) {
		inventory.add(this);
	}

	private static BufferedImage[] initCrackAnyLength(){
		URL url = ResourceInstance.class.getResource("/textures/crack_anylength.png");
		try {
			if (url==null) throw new Exception();
			BufferedImage img = ImageIO.read(url);
			BufferedImage[] t = new BufferedImage[(img.getHeight()/ img.getWidth())+1];
			t[0] = new BufferedImage(img.getWidth(),img.getWidth(),BufferedImage.TYPE_INT_ARGB);
			for (int i=1; i< t.length; i++){
				t[i] = new BufferedImage(img.getWidth(),img.getWidth(),BufferedImage.TYPE_INT_ARGB);
				Graphics g = t[i].getGraphics();
				BufferedImage crack = img.getSubimage(0,(i-1)* img.getWidth(),img.getWidth(),img.getWidth());
				g.drawImage(crack,0,0,img.getWidth(),img.getWidth(),null);
				g.dispose();
			}
			return t;
		} catch (IOException e){
			System.err.println("ERROR : when retrieving the texture for block mining !");
		} catch (RasterFormatException e){
			System.err.println("ERROR : when retrieving the block mining texture subregion.");
		} catch (Exception e){
			System.err.println("ERROR : unknown while initializing mining textures.");
		}
		return null;
	}

	private int calculHardnessIndex(){
		if (crackAnyLength==null) return 0;
		if (hardness<0) return crackAnyLength.length-1;
		return (crackAnyLength.length-1)-((hardness*(crackAnyLength.length-1))/resourceType.getHardness());
	}

	private BufferedImage getHardnessImg(){
		if (crackAnyLength==null) return null;
		hardnessIndex = calculHardnessIndex();
		return crackAnyLength[hardnessIndex];
	}

	private JLabel getLabelHardnessMode(){
		if (!isDigBrokenUpdate() || getHardnessImg()==null) return label;
		Icon icon = label.getIcon();
		Image h = getHardnessImg().getScaledInstance(icon.getIconWidth(),icon.getIconHeight(),Image.SCALE_SMOOTH);
		BufferedImage b = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics gb = b.getGraphics();
		icon.paintIcon(null,gb,0,0);
		gb.dispose();
		BufferedImage f = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics gf = f.getGraphics();
		gf.drawImage(b,0,0,null);
		gf.drawImage(h,0,0,null);
		gf.dispose();
		label.setIcon(new ImageIcon(f));
		return label;
	}

	private boolean isDigBrokenUpdate(){
		if (crackAnyLength==null) return false;
		return calculHardnessIndex() != hardnessIndex;
	}
}
