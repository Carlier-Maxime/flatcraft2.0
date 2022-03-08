package flatcraft.player;

import java.io.Serializable;

import fr.univartois.migl.utils.DesignPattern;

/**
 * A listener to get notified of the player's health's changes.
 */
@DesignPattern(name = "Observer/Listener")
public interface HealthListener extends Serializable {

	void onHealthChange(Player p);
}
