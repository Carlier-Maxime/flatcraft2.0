package flatcraft;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import flatcraft.resources.*;
import flatcraft.tools.Tool;
import flatcraft.tools.ToolType;

/**
 * Utility class to easily access the images to use in the game.
 * 
 * @author leberre
 *
 */
public class MineUtils {

	private MineUtils() {
		// to prevent the creation of an instance of that class
	}

	public static final int DEFAULT_IMAGE_SIZE = 40;

	private static final Map<String, CustomImageIcon> cachedImages = new TreeMap<>();
	private static final Map<String, Resource> cachedResources = new TreeMap<>();
	private static final Map<String, Tool> cachedTools = new TreeMap<>();

	public static final CustomImageIcon AIR = scaled("/textures/air.png");
	public static final CustomImageIcon PLAYER = scaled("/textures/player.png");

	static {
		cachedImages.put("air", AIR);
		cachedImages.put("player", PLAYER);
	}

	/**
	 * Create a scaled up version of the original icon, to have a MineCraft effect.
	 * 
	 * @param localName the local name of the texture file (from which we can deduce
	 *                  the complete file name).
	 * @return an ImageIcon scaled up to 40x40.
	 */

	public static CustomImageIcon getImage(String localName, boolean bg) {
		CustomImageIcon cached;
		if (bg) cached = cachedImages.get(localName+"_bg");
		else cached = cachedImages.get(localName);
		if (cached == null) {
			String absoluteName = "/textures/default_" + localName + ".png";
			cached = scaled(absoluteName, bg);
			if (bg) cachedImages.put(localName+"_bg", cached);
			else cachedImages.put(localName, cached);
		}
		return cached;
	}

	public static CustomImageIcon getImage(String localName) {return getImage(localName, false);}

	/**
	 * Create a scaled up version of the original icon, to have a MineCraft effect.
	 * 
	 * @param imageName the name of the texture file.
	 * @return an ImageIcon scaled up to 40x40.
	 */
	public static VaryingImageIcon scaled(String imageName, boolean bg) {
		try {
            URL url = MineUtils.class.getResource(imageName);
            if (url == null) {
                throw new IllegalArgumentException("Le fichier "+imageName+" n'a pas été trouvé");
            }
            CustomImageIcon icon = new CustomImageIcon(url);
            Image img = icon.getImage();
			if (bg) img = darken(img);
            return new VaryingImageIcon(img.getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, Image.SCALE_DEFAULT));
		} catch (Exception e) {
			return new VaryingImageIcon();
		}
	}

	public static VaryingImageIcon scaled(String imageName) {return scaled(imageName, false);}

	/**
	 * Create a scaled up version of the original icon, to have a MineCraft effect.
	 * 
	 * This image will not be lighted according to a light factor.
	 * 
	 * @param imageName the name of the texture file.
	 * @return an image
	 */
	public static CustomImageIcon scaledNotVarying(String imageName) {
		try {
            URL url = MineUtils.class.getResource(imageName);
            if (url == null) {
                throw new IllegalArgumentException("Le fichier "+imageName+" n'a pas été trouvé");
            }
            CustomImageIcon icon = new CustomImageIcon(url);
			return new CustomImageIcon(icon.getImage()
					.getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, Image.SCALE_DEFAULT));
		} catch (Exception e) {
			return new CustomImageIcon();
		}
	}

	/**
	 * Create a new scaled up version of the original icon, over an already scaled
	 * image (e.g. STONE).
	 * 
	 * @param backgroundName a scaled up background image
	 * @param foregroundName  the new image to put on top of the background.
	 * @return an image consisting of imageName with the given background.
	 */
	public static CustomImageIcon overlay(String backgroundName, String foregroundName) {
		Image background = getImage(backgroundName).getOriginalImage();
		Image foreground = getImage(foregroundName).getOriginalImage();
		BufferedImage merged = new BufferedImage(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = merged.getGraphics();
		g.drawImage(background, 0, 0, null);
		g.drawImage(foreground, 0, 0, null);
		return new VaryingImageIcon(merged);
	}

	/**
	 * Create a JScrollPane which is incremented by 1/4 of a tile when scrolling
	 * once.
	 * 
	 * @param comp a component to be decorated with scrollbars.
	 * @return a JScrollPane with scroll speed adapted to tiles.
	 */
	public static JScrollPane scrollPane(JComponent comp) {
		JScrollPane scroller = new JScrollPane(comp);
		scroller.getVerticalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
		scroller.getHorizontalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
        scroller.setDoubleBuffered(true);
		return scroller;
	}

	/**
	 * Utility method to simplify the creation of a resource if the localName of the
	 * resource is also the local name of its image.
	 * 
	 * @param localName a local name
	 * @param hardness  the hardness of the resource
	 * @param toolType  the type of tool required to dig it
	 * @return
	 */
	private static Resource makeResource(String localName, int hardness, ToolType toolType) {
		return new Resource(localName, getImage(localName), hardness, toolType);
	}

	private static Resource makeResource(String localName, int hardness, ToolType toolType, boolean needToolType) {
		return new Resource(localName, getImage(localName), hardness, toolType, needToolType);
	}

	private static Resource makeResource(String localName, int hardness, int hardnessLevel, ToolType toolType) {
		return new Resource(localName, getImage(localName), hardness, (byte) hardnessLevel, toolType);
	}

	private static Resource makeResource(String localName, int hardness, int hardnessLevel, ToolType toolType, boolean needToolType) {
		return new Resource(localName, getImage(localName), hardness, (byte) hardnessLevel, toolType, needToolType);
	}

	public static synchronized Resource getResourceByName(String resourceName) {
		String key = resourceName.toLowerCase();
		Resource resource = cachedResources.get(key);
		if (resource != null) {
			return resource;
		}
		switch (key) {
		case "air":
			resource = new Resource("air", MineUtils.AIR, 10, ToolType.NONE);
			break;
		case "tree":
			resource = makeResource("tree", 10, ToolType.AXE);
			break;
		case "leaves":
			resource = new Resource("leaves", getImage("leaves_simple"), 1, ToolType.NONE);
			break;
		case "treetop":
			Resource digTree = getResourceByName("tree");
			resource = new TransformableResource("treetop", getImage("tree_top"), digTree, 10, ToolType.AXE);
			break;
		case "water":
			resource = makeResource("water", 1, ToolType.NONE);
			break;
		case "junglegrass":
			resource = makeResource("junglegrass", 1, ToolType.NONE);
			break;
		case "grass":
			resource = makeResource("grass", 1, ToolType.SHOVEL);
			break;
		case "dirt":
			resource = makeResource("dirt", 1, ToolType.SHOVEL);
			break;
		case "brick":
			resource = makeResource("brick", 3, ToolType.PICKAXE, true);
			break;
		case "wood":
			resource = new Resource("wood", getImage("pine_wood"), 1, ToolType.AXE);
			break;
		case "stick":
			resource = makeResource("stick", 1, ToolType.NONE);
			break;
		case "lava":
			resource = makeResource("lava", 100000, ToolType.NONE);
			break;
		case "coal_lump":
			resource = makeResource("coal_lump", 20, ToolType.NONE);
			break;
		case "coal":
			Resource lump = getResourceByName("coal_lump");
			resource = new TransformableResource("coal", overlay("stone", "mineral_coal"), lump, 20, 1, ToolType.PICKAXE, true);
			break;
		case "iron_lump":
			resource = makeResource("iron_lump", 30, ToolType.NONE);
			break;
		case "iron":
			lump = getResourceByName("iron_lump");
			resource = new TransformableResource("iron", overlay("stone", "mineral_iron"), lump, 30, 2, ToolType.PICKAXE, true);
			break;
		case "gold_lump":
			resource = makeResource("gold_lump", 40, ToolType.NONE);
			break;
		case "gold":
			lump = getResourceByName("gold_lump");
			resource = new TransformableResource("gold", overlay("stone", "mineral_gold"), lump, 40, 3, ToolType.PICKAXE, true);
			break;
		case "stone":
			Resource cobble = getResourceByName("cobble");
			resource = new TransformableResource("stone", getImage("stone"), cobble, 10, 1, ToolType.PICKAXE, true);
			break;
		case "cobble":
			resource = makeResource("cobble", 10, ToolType.PICKAXE, true);
			break;
		case "steel_lingot":
			resource = makeResource("steel_ingot", 10, ToolType.NONE);
			break;
		case "gold_lingot":
			resource = makeResource("gold_ingot", 10, ToolType.NONE);
			break;
		case "copper_lump":
			resource = makeResource("copper_lump", 30, ToolType.NONE);
			break;
		case "copper":
			lump = getResourceByName("copper_lump");
			resource = new TransformableResource("copper", overlay("stone", "mineral_copper"), lump, 30, 2, ToolType.PICKAXE, true);
			break;
		case "copper_lingot":
			resource = makeResource("copper_ingot", 10, ToolType.NONE);
			break;
		case "chest":
			resource = new ChestResource("chest", getImage("chest_front"), 100000, ToolType.AXE);
			break;
		case "ladder":
			resource = new TraversableResource("ladder", getImage("ladder"), 5, ToolType.AXE);
			break;
		case "furnace":
			resource = new FurnaceResource("furnace", getImage("furnace_front"), 100000, ToolType.PICKAXE);
			break;
		default:
			throw new IllegalArgumentException(resourceName + " is not a correct resource name");
		}
		cachedResources.put(key, resource);
		return resource;
	}

	/**
	 * Utility method to simplify the creation of a tool.
	 * 
	 * @param localName a local name
	 * @param life      the initial capacity of the tool
	 * @param toolType  the type of tool
	 * @param decrement how much the tool capacity is decremented each time it is
	 *                  used
	 * @return a new tool
	 */
	private static Tool makeTool(String localName, int life, ToolType toolType, int level, int decrement) {
		return new Tool(localName, getImage("tool_" + localName), life, toolType, (byte) level, decrement);
	}

	public static synchronized Tool createToolByName(String toolName) {
		String key = toolName.toLowerCase();
		Tool tool = cachedTools.get(key);
		if (tool != null) {
			return tool;
		}
		switch (key) {
		case "woodpick":
			tool = makeTool("woodpick", 50, ToolType.PICKAXE, 1,5);
			break;
		case "stonepick":
			tool = makeTool("stonepick", 100, ToolType.PICKAXE, 2, 10);
			break;
		case "steelpick":
			tool = makeTool("steelpick", 150, ToolType.PICKAXE, 3,20);
			break;
		case "woodaxe":
			tool = makeTool("woodaxe", 50, ToolType.AXE, 1, 2);
			break;
		case "stoneaxe":
			tool = makeTool("stoneaxe", 100, ToolType.AXE, 2,4);
			break;
		case "steelaxe":
			tool = makeTool("steelaxe", 150, ToolType.AXE, 3, 8);
			break;
		case "woodshovel":
			tool = makeTool("woodshovel", 50, ToolType.SHOVEL, 1, 2);
			break;
		case "stoneshovel":
			tool = makeTool("stoneshovel", 100, ToolType.SHOVEL, 2, 4);
			break;
		case "steelshovel":
			tool = makeTool("steelshovel", 150, ToolType.SHOVEL, 3, 8);
			break;
		default:
			throw new IllegalArgumentException(toolName + " is not a correct tool name");
		}
		cachedTools.put(key, tool);
		return tool;
	}

	public static void fillRulesFromFile(String filename, Map<String, String> rules) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(MineUtils.class.getResource(filename).openStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] pieces = line.split("=");
				rules.put(pieces[0], pieces[1]);
			}
		} catch (IOException ioe) {
			Logger.getAnonymousLogger().log(Level.INFO, "Rules file " + filename + " not found", ioe);
		}
	}

	private static Image darken(Image image){
		if (image.getWidth(null)<0 || image.getHeight(null)<0) return image;
		BufferedImage img = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics gr = img.createGraphics();
		gr.drawImage(image,0,0,null);
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
		try {
			ImageIO.write(img,"png",new File("test/test.png"));
		} catch (Exception ignored) {}
		return img;
	}
}
