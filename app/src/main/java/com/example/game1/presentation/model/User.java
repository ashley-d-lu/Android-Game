package com.example.game1.presentation.model;

/**
 * The model object for a user. This object is also used by the domain and data layer.
 */
public class User {

    private String userName;
    private String password;

    /* This user's customization choices */
    private Customization customization;

    /* The highest individual statistics in this user's history */
    private Statistics topIndividualStats;

    /* The statistics of the top game in this user's history (i.e. the game with the highest score */
    private Statistics statsOfTopGame;

    /* The statistics of this user's current game */
    private Statistics statsOfCurrentGame;

    /* The last completed level in this user's current game */
    private int lastCompletedLevel;

    /**
     * Constructs a new User with the given username and password.
     *
     * @param username this user's username
     * @param password this user's password
     */
    public User(String username, String password) {
        this.userName = username;
        this.password = password;
        // Set default values for the user's customization choices, statistics, and last completed level
        customization = new Customization();
        statsOfCurrentGame = new Statistics();
        statsOfTopGame = new Statistics();
        topIndividualStats = new Statistics();
        lastCompletedLevel = 0;
    }

    /**
     * Returns this user's username
     *
     * @return this user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns this user's password
     *
     * @return this user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets this user's password
     *
     * @param password this user's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns this user's customization choices
     *
     * @return a Customization object storing this user's customization choices
     */
    public Customization getCustomization() {
        return customization;
    }

    /**
     * Sets this user's customization choices
     *
     * @param customization a Customization object storing this user's new customization choices
     */
    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

    /**
     * Returns the statistics of this user's current game
     *
     * @return a Statistics object corresponding to this user's current game
     */
    public Statistics getStatsOfCurrentGame() {
        return statsOfCurrentGame;
    }

    /**
     * Returns the statistics of this user's top game
     *
     * @return a Statistics object corresponding to this user's top game
     */
    public Statistics getStatsOfTopGame() {
        return statsOfTopGame;
    }

    /**
     * Returns this user's top individual statistics
     *
     * @return a Statistics object storing this user's top individual statistics (e.g. top number of
     * points, stars, and taps)
     */
    public Statistics getTopIndividualStats() {
        return topIndividualStats;
    }

    /**
     * Returns this user's last completed level
     *
     * @return an integer representing this user's last completed level
     */
    public int getLastCompletedLevel() {
        return lastCompletedLevel;
    }

    /**
     * Sets this user's last completed level
     *
     * @param lastCompletedLevel the level this user just completed
     */
    public void setLastCompletedLevel(int lastCompletedLevel) {
        this.lastCompletedLevel = lastCompletedLevel;
    }
}
