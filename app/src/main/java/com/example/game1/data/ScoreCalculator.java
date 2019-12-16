package com.example.game1.data;

/**
 * The class containing the algorithm used to calculate score.
 */
class ScoreCalculator {

    /**
     * Returns the score calculated from the given points, stars, and taps
     *
     * @param points the number of points
     * @param stars  the number of stars
     * @param taps   the number of taps
     * @return the score calculated from the given number of points, stars, and taps
     */
    static int calculateScore(int points, int stars, int taps) {
        return points + stars + taps;
    }
}
