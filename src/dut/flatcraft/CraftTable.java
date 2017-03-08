package dut.flatcraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

/**
 * A crafting table.
 * 
 * 
 * @author leberre
 *
 */
public class CraftTable extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Map<String, String> RULES = new HashMap<>();

    /*
     * The few crafting rules supported by the craftable.
     * Ideally, the rules should be read from a property file,
     * that way the rules could be changed without recompiling the program.
     */
    static {
        RULES.put("tree_empty_empty_empty_empty_empty_empty_empty_empty", "wood 4");
        RULES.put("wood_empty_empty_empty_empty_empty_empty_empty_empty", "stick 4");
        RULES.put("wood_wood_wood_empty_stick_empty_empty_stick_empty", "woodpick");
        RULES.put("wood_wood_empty_wood_stick_empty_empty_stick_empty", "woodaxe");
        RULES.put("cobble_cobble_cobble_empty_stick_empty_empty_stick_empty", "stonepick");
        RULES.put("cobble_cobble_empty_cobble_stick_empty_empty_stick_empty", "stoneaxe");
    }

    private JPanel craftPanel;
    private JPanel result;

    TransferHandler from;
    private TransferHandler fromResult;
    MouseListener mouselistener;

    /**
     * Build an empty craft table.
     */
    public CraftTable() {
        this.setLayout(new BorderLayout());
        craftPanel = new JPanel();
        craftPanel.setLayout(new GridLayout(3, 3));

        from = new AllowCopyOrMoveResource(this);
        fromResult = new MoveResultingResourceFromCraftTable(this);

        mouselistener = new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JComponent comp = (JComponent) me.getSource();
                TransferHandler handler = comp.getTransferHandler();
                if (me.getButton() == MouseEvent.BUTTON1) {
                    handler.exportAsDrag(comp, me, TransferHandler.MOVE);
                } else {
                    handler.exportAsDrag(comp, me, TransferHandler.COPY);
                }
            }
        };

        createGrid();
        createResultSpace();
    }

    private void createResultSpace() {
        result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.PAGE_AXIS));
        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        result.setPreferredSize(new Dimension(80, 80));
        result.addMouseListener(mouselistener);
        add(BorderLayout.EAST, result);
    }

    private void createGrid() {
        TransferHandler to = new AcceptResourceTransfert(this);
        JPanel tableCell;
        for (int i = 0; i < 9; i++) {
            craftPanel.add(tableCell = new JPanel());
            tableCell.setTransferHandler(to);
            tableCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tableCell.setPreferredSize(new Dimension(80, 80));
        }
        add(BorderLayout.CENTER, craftPanel);
    }

    void processCrafting() {
        String key = buildCraftingKey();
        String value = RULES.get(key);
        AbstractButton crafted = null;
        if (value != null) {
            String[] pieces = value.split(" ");
            if (pieces.length == 2) {
                // resource quantity
                Resource resource = MineUtils.getResourceByName(pieces[0]);
                int qty = Integer.parseInt(pieces[1].trim());
                crafted = new ResourceContainerUI(resource, qty);
            } else {
                assert pieces.length == 1;
                // tool
                Tool tool = MineUtils.createToolByName(pieces[0]);
                crafted = new ToolInstanceUI(tool.newInstance());
            }
            crafted.setTransferHandler(fromResult);
            crafted.addMouseListener(mouselistener);
        }
        result.removeAll();
        if (crafted == null) {
            System.err.println("Humm, cannot do anything for " + key);
        } else {
            result.add(crafted);
        }
        result.revalidate();
        result.repaint();
    }

    void consumeOneItem() {
        JPanel panel;
        Component[] comps = craftPanel.getComponents();
        assert comps.length == 9;
        for (int i = 0; i < 9; i++) {
            panel = (JPanel) comps[i];
            if (panel.getComponentCount() == 1) {
                ResourceContainerUI rcui = (ResourceContainerUI) panel.getComponents()[0];
                rcui.getResourceContainer().consume();
                if (rcui.getResourceContainer().getQuantity() == 0) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                }
            }
        }
    }

    private String buildCraftingKey() {
        StringBuilder stb = new StringBuilder();
        JPanel panel;
        Component[] comps = craftPanel.getComponents();
        assert comps.length == 9;
        for (int i = 0; i < 9; i++) {
            panel = (JPanel) comps[i];
            if (panel.getComponentCount() == 0) {
                stb.append("empty");
            } else {
                assert panel.getComponentCount() == 1;
                ResourceContainerUI rcui = (ResourceContainerUI) panel.getComponents()[0];
                if (rcui.getResourceContainer().getQuantity() > 0) {
                    stb.append(rcui.getResourceContainer().getResource().getName());
                } else {
                    stb.append("empty");
                }
            }
            if (i < 8) {
                stb.append("_");
            }
        }
        return stb.toString();
    }
}
