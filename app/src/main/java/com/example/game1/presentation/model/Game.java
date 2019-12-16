package com.example.game1.presentation.model;

/**
 * The model object for a game.
 */
public class Game {

    /**
     * The possible game types
     */
    public enum GameName {APPLE, TAPPING, JUMPING, BRICK}

    /**
     * The levels that each game type corresponds to
     */
    private static final int APPLE_LEVEL = 1;
    private static final int TAPPING_LEVEL = 2;
    private static final int JUMPING_LEVEL = 3;
    private static final int BRICK_LEVEL = 4;

    private GameName name;
    private int level;
    private Statistics statistics;
    private Customization customization;

    /**
     * Constructs a new Game.
     *
     * @param name the GameName of this Game
     */
    public Game(GameName name) {
        this.name = name;
        setLevel();
        // Set default values for the game's statistics and customization
        statistics = new Statistics();
        customization = new Customization();
    }

    /**
     * Sets the game's level according to its type
     */
    private void setLevel() {
        switch (name) {
            case APPLE:
                setLevel(APPLE_LEVEL);
                break;
            case TAPPING:
                setLevel(TAPPING_LEVEL);
                break;
            case JUMPING:
                setLevel(JUMPING_LEVEL);
                break;
            case BRICK:
                setLevel(BRICK_LEVEL);
                break;
            default:
                setLevel(0);
        }
    }

    /**
     * Returns the level that this Game corresponds to
     *
     * @return an integer representing the level that this Game corresponds to
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level that this Game corresponds to
     *
     * @param level an integer representing the level that this Game corresponds to
     */
    private void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the statistics of this Game
     *
     * @return the Statistics object stored in this Game
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Set the statistics of this Game
     *
     * @param statistics the new Statistics object to be stored in this Game
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * The customization choices of this Game
     *
     * @return the Customization object stored in this Game
     */
    public Customization getCustomization() {
        return customization;
    }

    /**
     * The customization choices of this Game
     *
     * @param customization the new Customization object to be stored in this Game
     */
    public void setCustomization(Customization customization) {
        this.customization = customization;
    }
}
