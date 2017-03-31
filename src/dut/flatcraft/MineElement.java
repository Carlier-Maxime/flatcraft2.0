package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public interface MineElement extends Serializable {

    ImageIcon getImage();

    MineElementInstance newInstance();
    
    MineElementInstance newInstance(JLabel label);
}
