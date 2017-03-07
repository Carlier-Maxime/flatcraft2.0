package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface MineElement {

    ImageIcon getImage();

    MineElementInstance newInstance();
    
    MineElementInstance newInstance(JLabel label);
}
