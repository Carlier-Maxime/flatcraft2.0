package flatcraft.tools;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import flatcraft.Cell;
import flatcraft.EmptyCell;
import flatcraft.MineUtils;
import flatcraft.player.Coordinate;
import flatcraft.player.Direction;
import flatcraft.player.Handable;
import flatcraft.player.Player;
import flatcraft.ui.Inventory;

/**
 * A particular instance of a tool, i.e. one that will be manipulated in the
 * game.
 * 
 * @author leberre
 *
 */
public class ToolInstance implements Transferable, Handable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Tool toolType;

	private int currentLife;

	private int impact;

	private List<ToolInstanceListener> listeners = new ArrayList<>();

	public ToolInstance(Tool toolType) {
		this.toolType = toolType;
		this.currentLife = toolType.getInitialLife();
		impact = toolType.getDecrement();
	}

	public ImageIcon getImage() {
		return toolType.getImage();
	}

	public int getCurrentLife() {
		return currentLife;
	}

	public Tool getType() {
		return toolType;
	}

	public int getImpactWithBlock() {
		currentLife--;
		notifyListeners();
		return impact;
	}

	public void addListener(ToolInstanceListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		for (ToolInstanceListener listener : listeners) {
			listener.update(this);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { Tool.TOOL_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return Tool.TOOL_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public Optional<Cell> action(Player p, Cell c) {
		if (c.getType().getToolType()==this.toolType.getTooltype() || c.getType().getToolType()==ToolType.NONE) {
			impact = toolType.getDecrement();
		} else impact=1;
		boolean result = c.dig(p);
		if (result) {
			return Optional.of(new EmptyCell(MineUtils.getImage("air"), c.getUI()));
		}
		return Optional.empty();
	}

	@Override
	public Coordinate toDig(Direction direction) {
		return direction.toDig();
	}

	@Override
	public boolean mustBeChanged() {
		return currentLife <= 0;
	}

	@Override
	public void addTo(Inventory inventory) {
		inventory.add(this);
	}

	@Override
	public String getName() {
		return toolType.getName();
	}
}
