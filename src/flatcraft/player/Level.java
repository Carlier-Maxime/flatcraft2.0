package flatcraft.player;

import java.util.ArrayList;
import java.util.List;

/**
 * The experience level of the player
 *
 * @author Tristan Leroy
 */
public class Level {
    /**
     * The current xp.
     */
    private long xp = 0;
    /**
     * The limit of xp for the next level.
     */
    private long bound = 10;
    /**
     * The current level.
     */
    private int level = 0;

    private final List<LevelListener> listeners = new ArrayList();

    /**
     * Get the bound of this level
     */
    public long getBound() {
        return bound;
    }

    /**
     * Get the current experience
     */
    public long getXp() {
        return xp;
    }

    /**
     * Increment the current xp
     *
     * @param point Number of experience point gain
     */
    public void incrementXp(int point) {
        this.xp += point;
        while (this.xp >= bound) {
            this.xp -= bound;
            this.incrementLevel(1);
        }
        for (LevelListener listener: listeners) {
            listener.onXpChange(this);
        }
    }

    /**
     * Get the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increment the current level
     *
     * @param point Number of level gain
     */
    public void incrementLevel(int point) {
        this.bound = (level + point) * 10L;
        this.level += point;

        for (LevelListener listener: listeners) {
            listener.onLevelChange(this);
        }
    }

    /**
     * Decrease the current level
     *
     * @param point Number of level gain
     */
    public void decreaseLevel(int point) {
        if (level - point > 0) {
            this.bound = (level - point) * 10L;
            this.incrementLevel(0);
            this.level -= point;
            for (LevelListener listener: listeners) {
                listener.onLevelChange(this);
            }
        }
    }

    /**
     * Add a new level listener to the Level.
     *
     * @param listener an level listener
     */
    public void addListener(LevelListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove a level listener to the Level.
     *
     * @param listener an level listener
     */
    public void removeListener(LevelListener listener) {
        this.listeners.remove(listener);
    }
}
