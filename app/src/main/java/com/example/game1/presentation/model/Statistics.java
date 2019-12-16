package com.example.game1.presentation.model;

/**
 * The model object for the statistics of a game
 */
public class Statistics {

    private int points;
    private int stars;
    private int taps;

    /**
     * Constructs a new Statistics object
     */
    Statistics() {
        points = 0;
        stars = 0;
        taps = 0;
    }

    /**
     * Returns the number of points stored in this Statistics object
     *
     * @return the number of points in this Statistics object
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the number of points stored in this Statistics object
     *
     * @param points the new number of points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the number of stars stored in this Statistics object
     *
     * @return the number of stars in this Statistics object
     */
    public int getStars() {
        return stars;
    }

    /**
     * Sets the number of stars stored in this Statistics object
     *
     * @param stars the new number of stars
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * Returns the number of taps stored in this Statistics object
     *
     * @return the number of taps in this Statistics object
     */
    public int getTaps() {
        return taps;
    }

    /**
     * Sets the number of taps stored in this Statistics object
     *
     * @param taps the new number of taps
     */
    public void setTaps(int taps) {
        this.taps = taps;
    }
}
