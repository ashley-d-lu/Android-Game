package com.example.game1.domain;

import com.example.game1.presentation.model.User;

import java.util.List;

/**
 * This is the interface that defines the responsibilities/functions of the user service.
 */
public interface UserServiceIntf {

    /**
     * If the given user's username is not already taken, register the given user and return true.
     * Otherwise, return false
     *
     * @param user the User object to register
     * @return true if registration was successful, and false otherwise
     */
    boolean registerUser(User user);

    /**
     * If the given username and password correspond to an existing user, return the existing user.
     * Otherwise, return null
     *
     * @param username the username of the User object to return
     * @param password the password of the User object to return
     * @return the User object with the given username and password. If no such User exists,
     * return null
     */
    User getUser(String username, String password);

    /**
     * Updates the given user's information
     *
     * @param user the User object to update
     */
    void updateUser(User user);

    /**
     * Return the top score of the user with the given username. If the user does not exist,
     * return 0.
     *
     * @param username the username of a user
     * @return the top score of the user with the given username. If no such user exists, return 0.
     */
    int getTopScore(String username);

    /**
     * Return the current score of the user with the given username. If the user does not exist,
     * return 0.
     *
     * @param username the username of a user
     * @return the current score of the user with the given username. If no such user exists,
     * return 0.
     */
    int getCurrentScore(String username);

    /**
     * Returns the top x users (sorted in non-increasing order) based on the given criterion.
     *
     * @param x         the maximum number of users to return
     * @param criterion the criterion on which to sort the users
     * @return a list of x users (sorted in non-increasing order) based on the given criterion. If
     * less than x users exist, return a list whose length equals the number of existing users.
     */
    List<User> getTopUsers(int x, String criterion);
}
