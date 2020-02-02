package dut.flatcraft.ui;

import java.awt.datatransfer.Transferable;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import dut.flatcraft.Cell;
import dut.flatcraft.MineUtils;
import dut.flatcraft.player.Handable;
import dut.flatcraft.resources.CombustibleContainer;
import dut.flatcraft.resources.OreContainer;
import dut.flatcraft.resources.Resource;
import dut.flatcraft.resources.ResourceContainer;
import dut.flatcraft.resources.ResourceInstance;
import dut.flatcraft.tools.Tool;
import dut.flatcraft.tools.ToolInstance;
import fr.univartois.migl.utils.DesignPattern;

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
	private MouseListener mouselistener = new MyMouseAdapterForInventory();

	public Inventory() {
		ui.setBorder(BorderFactory.createEmptyBorder());
		handler = createTransfertFrom();
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

	@DesignPattern(name = "Double Dispatch")
	public void add(Cell cell) {
		cell.addTo(this);
	}

	public void add(ResourceInstance instance) {
		ResourceContainer container = containers.get(instance.getName());
		if (container == null) {
			// create container
			container = createResourceContainer((Resource) instance.getType());
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
	}

	public void add(Handable handable) {
		handable.addTo(this);
		ui.revalidate();
		ui.repaint();
	}

	public JComponent getUI() {
		return ui;
	}

	private TransferHandler createTransfertFrom() {
		return new TransferHandler() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int getSourceActions(JComponent c) {
				return COPY;
			}

			@Override
			protected Transferable createTransferable(JComponent c) {
				if (c instanceof ResourceContainerUI) {
					ResourceContainer rc = ((ResourceContainerUI) c).getResourceContainer();
					return rc.clone();
				} else {
					return ((ToolInstanceUI) c).getMineTool();
				}
			}

			@Override
			protected void exportDone(JComponent source, Transferable data, int action) {
				if (source instanceof ResourceContainerUI) {
					ResourceContainer container = ((ResourceContainerUI) source).getResourceContainer();
					if (action == MOVE) {
						container.consumeAll();
					} else if (action == COPY) {
						if (container.getQuantity() == 1) {
							container.consume(1);
						} else {
							container.consume(container.getQuantity() / 2);
						}
					}
				} else if (source instanceof ToolInstanceUI) {
					if (action == MOVE || action == COPY) {
						ToolInstanceUI toolUI = (ToolInstanceUI) source;
						ui.remove(toolUI);
						handables.remove(toolUI.getMineTool());
						ui.revalidate();
						ui.repaint();
					}
				}
			}

			// partie to
			@Override
			public boolean canImport(TransferSupport support) {
				return support.isDataFlavorSupported(ResourceContainer.RESOURCE_FLAVOR)
						|| support.isDataFlavorSupported(Tool.TOOL_FLAVOR);
			}

			@Override
			public boolean importData(TransferSupport support) {
				if (support.isDrop()) {
					JComponent source = (JComponent) support.getComponent();
					try {
						Handable transferedHandable = (Handable) support.getTransferable()
								.getTransferData(ResourceContainer.RESOURCE_FLAVOR);
						if (transferedHandable instanceof ToolInstance) {
							if (!handables.contains(transferedHandable)) {
								add((ToolInstance) transferedHandable);
							}
						} else {
							ResourceContainer sourceContainer = (ResourceContainer) transferedHandable;
							addResource(sourceContainer);
							source.revalidate();
							source.repaint();
						}
						return true;
					} catch (Exception e) {
						Logger.getAnonymousLogger().info(e.getMessage());
						return false;
					}
				} else {
					return false;
				}
			}
		};
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

	private void addResource(ResourceContainer container) {
		ResourceContainer mcontainer = containers.get(container.getResource().getName());
		if (mcontainer == null) {
			// create container
			mcontainer = createResourceContainer(container.getResource());
		}
		mcontainer.inc(container.getQuantity());
		container.consumeAll();
	}
}
