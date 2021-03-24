package dut.flatcraft.player;

import fr.univartois.migl.utils.DesignPattern;

/**
 * A listener to get notified of the player's experience's and leve's changes.
 */
@DesignPattern(name = "Observer/Listener")
public interface LevelListener {

    void onXpChange(Level level);
    void onLevelChange(Level level);
}
