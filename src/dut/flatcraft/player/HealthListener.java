package dut.flatcraft.player;

import java.io.Serializable;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Observer/Listener")
public interface HealthListener extends Serializable {

	void onHealthChange(Player p);
}
