package dut.flatcraft;

import dut.flatcraft.ui.MyGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GlassPaneWrapper extends JLayeredPane {
    private JPanel glassPanel = new JPanel();
    MouseEvent mouseEvent;

    public GlassPaneWrapper(MyGrid grid) {
        glassPanel.setOpaque(false);
        glassPanel.setVisible(false);
        glassPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                updateDirection(e, grid);
                mouseEvent = e;
            }
        });

        glassPanel.addMouseWheelListener(e -> {
            if(e.getWheelRotation()==1) {
                grid.getPlayer().previousInHand();
            } else {
                grid.getPlayer().nextInHand();
            }
            grid.repaint();
        });

        glassPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                grid.digOrFill();
                grid.checkPhysics();
                grid.repaint();
            }
        });

        glassPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                grid.keyPressed(e);
                if (mouseEvent != null) {
                    updateDirection(mouseEvent, grid);
                }
            }
        });

        glassPanel.setFocusable(true);

        grid.setSize(grid.getPreferredSize());
        add(grid, JLayeredPane.DEFAULT_LAYER);
        add(glassPanel, JLayeredPane.PALETTE_LAYER);

        glassPanel.setPreferredSize(grid.getPreferredSize());
        glassPanel.setSize(grid.getPreferredSize());
        setPreferredSize(grid.getPreferredSize());
    }

    private void updateDirection(MouseEvent e, MyGrid grid) {
        int px = grid.getPlayer().getPosition().getX()* MineUtils.DEFAULT_IMAGE_SIZE+20;
        int py = grid.getPlayer().getPosition().getY()*MineUtils.DEFAULT_IMAGE_SIZE+20;
        int mx = e.getX();
        int my = e.getY();

        Point p = new Point(px, py);
        Point m = new Point(mx, my);
        Point a = new Point(mx, py);

        double pm = p.distance(m);
        double pa = p.distance(a);

        double angle = Math.acos(pa/pm);

//                System.out.println(angle);

//                System.out.println(mx + " " + my);
//                System.out.println(px + " " + py);

//                if (Math.abs(px-mx)<Math.abs(py-my)) {
//                    if (py>my) grid.getPlayer().up();
//                    else grid.getPlayer().down();
//                } else {
//                    if (px>mx) grid.getPlayer().left();
//                    else grid.getPlayer().right();
//                }

        if (py>my) { // vers le haut
            if (px>mx) { // vers le haut gauche
                if (angle>3*Math.PI/8) {
                    grid.getPlayer().up();
                } else if (angle<Math.PI/8) {
                    grid.getPlayer().left();
                } else grid.getPlayer().upLeft()
                ;
            } else { // vers le haut droite
                if (angle>3*Math.PI/8) {
                    grid.getPlayer().up();
                } else if (angle<Math.PI/8) {
                    grid.getPlayer().right();
                } else grid.getPlayer().upRight()
                ;
            }
        } else { // vers le bas
            if (px>mx) { // vers le bas gauche
                if (angle>3*Math.PI/8) {
                    grid.getPlayer().down();
                } else if (angle<Math.PI/8) {
                    grid.getPlayer().left();
                } else grid.getPlayer().downLeft()
                    ;
            } else { // vers le bas droite
                if (angle>3*Math.PI/8) {
                    grid.getPlayer().down();
                } else if (angle<Math.PI/8) {
                    grid.getPlayer().right();
                } else grid.getPlayer().downRight()
                    ;
            }
        }
        grid.repaint();
    }

    public void activateGlassPane(boolean activate) {
        glassPanel.setVisible(activate);
        if (activate) {
            glassPanel.requestFocusInWindow();
            glassPanel.setFocusTraversalKeysEnabled(false);
        }
    }
}
