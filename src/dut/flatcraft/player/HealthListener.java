package dut.flatcraft.player;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Observer/Listener")
public interface HealthListener {

	void onHealthChange(Player p);
}
