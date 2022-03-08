package flatcraft.ui;

import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import flatcraft.MineUtils;
import flatcraft.player.Handable;
import flatcraft.resources.CombustibleContainer;
import flatcraft.resources.OreContainer;
import flatcraft.resources.Resource;
import flatcraft.resources.ResourceContainer;
import flatcraft.resources.ResourceInstance;
import flatcraft.tools.ToolInstance;
import fr.univartois.migl.utils.DesignPattern;

/**
 * The inventory of all things that can be retrieved
 * from the game (resources, tools, etc.)
 * 
 */
public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, ResourceContainer> containers = new HashMap<>();

	private JPanel ui = new JPanel();

	private int current = 0;

	private List<Handable> handables = new ArrayList<>();

	private TransferHandler handler;
	private transient MouseListener mouselistener = new MyMouseAdapterForInventory();

	public Inventory() {
		ui.setBorder(BorderFactory.createEmptyBorder());
		handler = new InventoryTransfert(this);
		createOreContainer(MineUtils.getResourceByName("iron_lump"));
		createCombustibleContainer(MineUtils.getResourceByName("wood"));
		createCombustibleContainer(MineUtils.getResourceByName("leaves"));
		createCombustibleContainer(MineUtils.getResourceByName("coal_lump"));
		add(MineUtils.createToolByName("woodaxe").newInstance());
		add(MineUtils.createToolByName("woodpick").newInstance());
		current = handables.size() - 2;
	}

	public Handable getElementInTheHand() {
		return handables.get(current);
	}

	public void next() {
		current++;
		if (current == handables.size()) {
			current = 0;
		}
	}

	public void previous() {
		if (current == 0) {
			current = handables.size() - 1;
		} else {
			current--;
		}
	}

	public void add(ResourceInstance instance) {
		ResourceContainer container = containers.get(instance.getName());
		if (container == null) {
			// create container
			container = createResourceContainer(instance.getType());
		}
		container.inc();
	}

	public void add(ResourceContainer rcontainer) {
		ResourceContainer container = containers.get(rcontainer.getResource().getName());
		if (container == null) {
			// create container
			container = createResourceContainer(rcontainer.getResource());
		}
		container.inc(rcontainer.getQuantity());
	}

	public void add(ToolInstance tool) {
		handables.add(tool);
		ToolInstanceUI tui = new ToolInstanceUI(tool);
		ui.add(tui);
		tui.setTransferHandler(handler);
		tui.addMouseListener(mouselistener);
		ui.revalidate();
		ui.repaint();
	}

    @DesignPattern(name = "Double Dispatch")
	public void add(Inventoriable inventoriable) {
		inventoriable.addTo(this);
	}

	public JComponent getUI() {
		return ui;
	}

	public List<Handable> getHandables() {
		return handables;
	}

	private ResourceContainer createResourceContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(resource);
		register(resource, cui);
		return cui.getResourceContainer();
	}

	private void createOreContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(new OreContainer(resource, 5));
		register(resource, cui);
	}

	private void createCombustibleContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(new CombustibleContainer(resource, 5));
		register(resource, cui);
	}

	private void register(Resource resource, ResourceContainerUI cui) {
		ui.add(cui);
		ui.revalidate();
		cui.setTransferHandler(handler);
		cui.addMouseListener(mouselistener);
		ResourceContainer container = cui.getResourceContainer();
		containers.put(resource.getName(), container);
		handables.add(container);
	}

	void addResource(ResourceContainer container) {
		ResourceContainer mcontainer = containers.get(container.getResource().getName());
		if (mcontainer == null) {
			// create container
			mcontainer = createResourceContainer(container.getResource());
		}
		mcontainer.inc(container.getQuantity());
		container.consumeAll();
	}
}
