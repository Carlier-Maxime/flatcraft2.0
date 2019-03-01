package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Type Object")
public interface MineElement extends Serializable {

	ImageIcon getImage();

	MineElementInstance newInstance();

	MineElementInstance newInstance(JLabel label);
}
