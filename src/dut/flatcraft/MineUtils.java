package dut.flatcraft;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;


/**
 * Classe utilitaire permettant d'accéder facilement aux images de MineTest dans
 * un programme Java.
 * 
 * @author leberre
 *
 */
public class MineUtils {

    private MineUtils() {
        // pas possible de créer des instances de cette classe
    }

    public static final int DEFAULT_IMAGE_SIZE = 40;
    
    public static final ImageIcon AIR = scaled("/textures/air.png");
    
    public static final ImageIcon GRASS = scaled("/textures/default_grass.png");

    public static final ImageIcon JUNGLEGRASS = scaled("/textures/default_junglegrass.png");

    public static final ImageIcon DIRT = scaled("/textures/default_dirt.png");

    public static final ImageIcon STONE = scaled("/textures/default_stone.png");

    public static final ImageIcon BRICK = scaled("/textures/default_brick.png");

    public static final ImageIcon CLAY = scaled("/textures/default_clay.png");

    public static final ImageIcon CLOUD = scaled("/textures/default_cloud.png");

    public static final ImageIcon COBBLE = scaled("/textures/default_cobble.png");

    public static final ImageIcon ICE = scaled("/textures/default_ice.png");

    public static final ImageIcon LAVA = scaled("/textures/default_lava.png");

    public static final ImageIcon WOOD_TOP = scaled("/textures/default_pine_tree_top.png");
    public static final ImageIcon WOOD = scaled("/textures/default_pine_wood.png");
    public static final ImageIcon STICK = scaled("/textures/default_stick.png");

    public static final ImageIcon TREE_TOP = scaled("/textures/default_tree_top.png");
    public static final ImageIcon TREE = scaled("/textures/default_tree.png");

    public static final ImageIcon WATER = scaled("/textures/default_water.png");
    
    public static final ImageIcon PLAYER = scaled("/textures/player.png");

    public static final ImageIcon COAL = overlay(STONE, "/textures/default_mineral_coal.png");
    public static final ImageIcon COAL_LUMP = scaled("/textures/default_coal_lump.png");
    public static final ImageIcon GOLD = overlay(STONE, "/textures/default_mineral_gold.png");
    public static final ImageIcon GOLD_LUMP = scaled("/textures/default_gold_lump.png");
    public static final ImageIcon IRON = overlay(STONE, "/textures/default_mineral_iron.png");
    public static final ImageIcon IRON_LUMP = scaled("/textures/default_iron_lump.png");
    public static final ImageIcon STEEL_INGOT = scaled("/textures/default_steel_ingot.png");

    public static final ImageIcon TOOL_AXE_WOOD = scaled("/textures/default_tool_woodaxe.png");
    public static final ImageIcon TOOL_AXE_STONE = scaled("/textures/default_tool_stoneaxe.png");
    public static final ImageIcon TOOL_PICK_WOOD = scaled("/textures/default_tool_woodpick.png");
    public static final ImageIcon TOOL_PICK_STONE = scaled("/textures/default_tool_stonepick.png");

    private static final Map<String, Resource> cachedResources = new HashMap<>();
    private static final Map<String, Tool> cachedTools = new HashMap<>();

    /**
     * Create a scaled up version of the original icon, to have a MineCraft
     * effect.
     * 
     * @param imageName
     *            the name of the texture file.
     * @return an ImageIcon scaled up to 40x40.
     */
    public static ImageIcon scaled(String imageName) {
        try {
            return new ImageIcon(ImageIO.read(MineUtils.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT));
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    /**
     * Create a new scaled up version of the original icon, over an already
     * scaled image (e.g. STONE).
     * 
     * @param background
     *            a scaled up background image
     * @param imageName
     *            the new image to put on top of the background.
     * @return an image consisting of imageName with the given background.
     */
    public static ImageIcon overlay(ImageIcon background, String imageName) {
        try {
            Image foreground = ImageIO.read(MineUtils.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT);
            BufferedImage merged = new BufferedImage(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = merged.getGraphics();
            g.drawImage(background.getImage(), 0, 0, null);
            g.drawImage(foreground, 0, 0, null);
            return new ImageIcon(merged);
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    /**
     * Create a JButton without borders, to be used typically in a
     * {@see GridLayout}.
     * 
     * @param icon
     *            the ImageIcon to be seen on the button.
     * @return a button displaying icon, with no borders.
     */
    public static JButton noBorderButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    /**
     * Create a JToggleButton without borders, to be used typically in a
     * {@see GridLayout}.
     * 
     * @param icon1
     *            the ImageIcon to be seen first on the button.
     * @param icon2
     *            the ImageIcon to be seen once the button is pushed.
     * @return a button displaying icon, with no borders.
     */
    public static JToggleButton toggleNoBorderButton(ImageIcon icon1, ImageIcon icon2) {
        JToggleButton button = new JToggleButton(icon1);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setSelectedIcon(icon2);
        return button;
    }

    /**
     * Create a JScrollPane which is incremented by 1/4 of a tile when scrolling
     * once.
     * 
     * @param comp
     *            a component to be decorated with scrollbars.
     * @return a JScrollPane with scroll speed adaptated to tiles.
     */
    public static JScrollPane scrollPane(JComponent comp) {
        JScrollPane scroller = new JScrollPane(comp);
        scroller.getVerticalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
        scroller.getHorizontalScrollBar().setUnitIncrement(DEFAULT_IMAGE_SIZE);
        return scroller;
    }
    
    public static final Resource getResourceByName(String resourceName) {
        String key = resourceName.toLowerCase();
        Resource resource = cachedResources.get(key);
        if (resource != null) {
            return resource;
        }
        switch (key) {
        case "air":
            resource = new Resource("air", MineUtils.AIR, 10, ToolType.NO_TOOL);
            break;
        case "tree":
            resource = new Resource("tree", MineUtils.TREE, 10, ToolType.NO_TOOL);
            break;
        case "treetop":
            Resource digTree = getResourceByName("tree");
            resource = new TransformableResource("treetop", MineUtils.TREE_TOP, digTree, 10, ToolType.NO_TOOL);
            break;
        case "water":
            resource = new Resource("water", MineUtils.WATER, 1, ToolType.NO_TOOL);
            break;
        case "junglegrass":
            resource = new Resource("junglegrass", MineUtils.JUNGLEGRASS, 1, ToolType.NO_TOOL);
            break;
        case "grass":
            resource = new Resource("grass", MineUtils.GRASS, 1, ToolType.NO_TOOL);
            break;
        case "dirt":
            resource = new Resource("dirt", MineUtils.DIRT, 1, ToolType.NO_TOOL);
            break;
        case "brick":
            resource = new Resource("brick", MineUtils.BRICK, 3, ToolType.MEDIUM_TOOL);
            break;
        case "wood":
            resource = new Resource("wood", MineUtils.WOOD, 1, ToolType.NO_TOOL);
            break;
        case "stick":
            resource = new Resource("stick", MineUtils.STICK, 1, ToolType.NO_TOOL);
            break;
        case "lava":
            resource = new Resource("lava", MineUtils.LAVA, 100000, ToolType.HARD_TOOL);
            break;
        case "coal_lump":
            resource = new Resource("coal_lump", MineUtils.COAL_LUMP, 20, ToolType.MEDIUM_TOOL);
            break;
        case "coal":
            Resource lump = getResourceByName("coal_lump");
            resource = new TransformableResource("coal", MineUtils.COAL, lump, 20, ToolType.MEDIUM_TOOL);
            break;
        case "iron_lump":
            resource = new Resource("iron_lump", MineUtils.IRON_LUMP, 30, ToolType.MEDIUM_TOOL);
            break;
        case "iron":
            lump = getResourceByName("iron_lump");
            resource = new TransformableResource("iron", MineUtils.IRON, lump, 30, ToolType.MEDIUM_TOOL);
            break;
        case "gold_lump":
            resource = new Resource("gold_lump", MineUtils.GOLD_LUMP, 40, ToolType.HARD_TOOL);
            break;
        case "gold":
            lump = getResourceByName("gold_lump");
            resource = new TransformableResource("gold", MineUtils.GOLD, lump, 40, ToolType.HARD_TOOL);
            break;
        case "stone":
            Resource cobble = getResourceByName("cobble");
            resource = new TransformableResource("stone", MineUtils.STONE, cobble, 10, ToolType.MEDIUM_TOOL);
            break;
        case "cobble":
            resource = new Resource("cobble", MineUtils.COBBLE, 10, ToolType.MEDIUM_TOOL);
            break;
        default:
            throw new IllegalArgumentException(resourceName + " is not a correct resource name");
        }
        cachedResources.put(key, resource);
        return resource;
    }

    public static final Tool createToolByName(String toolName) {
        String key = toolName.toLowerCase();
        Tool tool = cachedTools.get(key);
        if (tool != null) {
            return tool;
        }
        switch (key) {
        case "woodpick":
            tool = new Tool("woodpick", MineUtils.TOOL_PICK_WOOD, 100, ToolType.MEDIUM_TOOL, 5);
            break;
        case "stonepick":
            tool = new Tool("stonepick", MineUtils.TOOL_PICK_STONE, 100, ToolType.MEDIUM_TOOL, 10);
            break;
        case "woodaxe":
            tool = new Tool("woodaxe", MineUtils.TOOL_AXE_WOOD, 100, ToolType.NO_TOOL, 1);
            break;
        case "stoneaxe":
            tool = new Tool("stoneaxe", MineUtils.TOOL_AXE_STONE, 100, ToolType.NO_TOOL, 2);
            break;
        default:
            throw new IllegalArgumentException(toolName + " is not a correct tool name");
        }
        cachedTools.put(key, tool);
        return tool;
    }
}
