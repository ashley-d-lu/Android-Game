package com.example.game1.presentation.presenter;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.domain.UserServiceIntf;
import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.Statistics;
import com.example.game1.presentation.model.User;
import com.example.game1.presentation.view.user.StatisticsActivity;
import com.example.game1.presentation.view.user.UserMenuActivity;

import java.util.List;

/**
 * A controller class for managing user activity. It is also a proxy to classes in the domain layer.
 */
public class UserManager {

    /* An instance of UserService from the domain layer. */
    private UserServiceIntf userService;

    /* The current user that is logged in. */
    private User currentUser;

    /**
     * Constructs a UserManager
     */
    public UserManager() {
    }

    /**
     * Injects an instance of an implementation of UserServiceIntf into this UserManager
     *
     * @param userService an instance of an implementation of UserServiceIntf
     */
    public void setUserService(UserServiceIntf userService) {
        this.userService = userService;
    }

    /**
     * Registers a new User instance with specified username and password. If registration is
     * successful return true. Otherwise, return false.
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if registration was successful, and false otherwise
     */
    public boolean registerUser(String username, String password) {
        User user = new User(username, password);

        // Send the user to userService to check for duplicate user name
        if (userService.registerUser(user)) {
            setCurrentUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Logs in a User with the specified username and password. If login is successful, return true.
     * Otherwise, return false.
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if login was successful, and false otherwise
     */
    public boolean loginUser(String username, String password) {
        // Send the user to userService for user validation
        User user = userService.getUser(username, password);
        if (user != null) {
            setCurrentUser(user);
        }
        return user != null;
    }

    /**
     * Returns the current user
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user
     *
     * @param currentUser the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Updates the customization choices of the current user
     *
     * @param characterColour the current user's choice of character colour
     * @param colourScheme    the current user's choice of colour scheme
     * @param music           the current user's choice of music
     */
    public void updateCurrentUsersCustomization(String characterColour, String colourScheme,
                                                String music) {
        Customization newCustomization = new Customization();

        if ("Red".equals(characterColour)) {
            newCustomization.setCharacterColour(Customization.CharacterColour.RED);
        } else if ("Yellow".equals(characterColour)) {
            newCustomization.setCharacterColour(Customization.CharacterColour.YELLOW);
        } else {
            newCustomization.setCharacterColour(Customization.CharacterColour.BLUE);
        }

        if ("Light".equals(colourScheme)) {
            newCustomization.setColourScheme(Customization.ColourScheme.LIGHT);
        } else {
            newCustomization.setColourScheme(Customization.ColourScheme.DARK);
        }

        if ("Arpanauts".equals(music)) {
            newCustomization.setMusicPath(Customization.MusicPath.SONG2);
        } else if ("A Night Of Dizzy Spells".equals(music)) {
            newCustomization.setMusicPath(Customization.MusicPath.SONG3);
        } else {
            newCustomization.setMusicPath(Customization.MusicPath.SONG1);
        }

        currentUser.setCustomization(newCustomization);
        updateUserInfo();
    }

    /**
     * Updates the current user's game statistics and level using the information in the given Game
     * object.
     *
     * @param game a Game object containing data to be saved to the current user's game
     */
    void updateCurrentUsersGame(Game game) {
        updateGameInfo(
                currentUser.getStatsOfCurrentGame().getPoints() + game.getStatistics().getPoints(),
                currentUser.getStatsOfCurrentGame().getStars() + game.getStatistics().getStars(),
                currentUser.getStatsOfCurrentGame().getTaps() + game.getStatistics().getTaps(),
                game.getLevel());
        updateUserInfo();
    }

    /**
     * Restarts the current user's game
     */
    public void restartCurrentUsersGame() {
        // Set all the current statistics and the current game level to 0
        updateGameInfo(0, 0, 0, 0);
        updateUserInfo();
    }

    /**
     * Sets the current user's game statistics and level to the given values.
     */
    private void updateGameInfo(int points, int stars, int taps, int level) {
        currentUser.getStatsOfCurrentGame().setPoints(points);
        currentUser.getStatsOfCurrentGame().setStars(stars);
        currentUser.getStatsOfCurrentGame().setTaps(taps);
        currentUser.setLastCompletedLevel(level);
    }

    /**
     * Sends any changes to the current user's info to the backend through UserService
     */
    private void updateUserInfo() {
        userService.updateUser(currentUser);
    }

    /**
     * Redirects the user to the statistics page
     *
     * @param activity the Activity class of the statistics page
     */
    void goToStatsPage(AppCompatActivity activity) {
        Intent intent = new Intent(activity, StatisticsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Returns the current score of the user with the given username. If the user does not exist, this
     * method returns 0.
     *
     * @param username the user's username
     * @return the current score of the user with the given username, or 0 if the user does not exist
     */
    public int getCurrentScore(String username) {
        return userService.getCurrentScore(username);
    }

    /**
     * Returns the top score of the user with the given username. If the user does not exist, this
     * method returns 0.
     *
     * @param username the user's username
     * @return the top score of the user with the given username, or 0 if the user does not exist
     */
    public int getTopScore(String username) {
        return userService.getTopScore(username);
    }

    /**
     * Returns the top x users (sorted in non-increasing order) based on the given criterion.
     *
     * @param x         the maximum number of users to return
     * @param criterion the criterion on which to judge the users
     * @return a list of x users (sorted in non-increasing order) based on the given criterion. If
     * less than x users exist, return a list whose length equals the number of existing users.
     */
    public List<User> getTopUsers(int x, String criterion) {
        return userService.getTopUsers(x, criterion);
    }
}
