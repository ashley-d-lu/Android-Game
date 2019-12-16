package com.example.game1.data;

import com.example.game1.presentation.model.User;

import java.util.List;

/**
 * This is the interface that defines the responsibilities/functions of the data manager. The data
 * manager is responsible for storing, retrieving, and loading persistence data. The implementation
 * of this data manager interface can be database based, file based or cloud based.
 */
public interface DataManagerIntf {

    /**
     * Adds the given user.
     *
     * @param user the User object to be added
     */
    void createUser(User user);

    /**
     * Updates the given user's information.
     *
     * @param user the User object to be updated
     */
    void updateUser(User user);

    /**
     * Returns the user with the given username. If no such user exists, return null.
     *
     * @param username the username of a User
     * @return the User object with the given username. If no such User object exists, return null
     */
    User getUser(String username);

    /**
     * Returns the given's user's top score.
     *
     * @param user a User object
     * @return the top score of the User
     */
    int getTopScore(User user);

    /**
     * Returns the given's user's current score.
     *
     * @param user a User object
     * @return the current score of the User
     */
    int getCurrentScore(User user);

    /**
     * Returns a list of users sorted (in non-decreasing order) by score
     *
     * @return a list of User objects sorted (in non-decreasing order) by score
     */
    List sortUsersByScore();

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of points
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of points
     */
    List sortUsersByPoints();

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of stars
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of stars
     */
    List sortUsersByStars();

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of taps
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of taps
     */
    List sortUsersByTaps();
}
