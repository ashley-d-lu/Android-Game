package com.example.game1.data;

import android.util.Log;

import com.example.game1.AppManager;
import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the file based data manager that implements the DataManagerIntf interface in the
 * persistence layer. It is responsible for storing, retrieving, and loading data in the persistence
 * file.
 */
public class FileDataManager implements DataManagerIntf {

    /* Keys for writing to and reading from the file */
    private static final String USERNAME = "UserName";
    private static final String PASSWORD = "Password";
    private static final String CHARAC_COLOUR = "CharacterColour";
    private static final String COLOUR_SCHEME = "ColourScheme";
    private static final String MUSIC = "Music";
    private static final String TOP_GAME_POINTS = "PointsOfTopGame";
    private static final String TOP_GAME_STARS = "StarsOfTopGame";
    private static final String TOP_GAME_TAPS = "TapsOfTopGame";
    private static final String CURR_GAME_POINTS = "PointsOfCurrentGame";
    private static final String CURR_GAME_STARS = "StarsOfCurrentGame";
    private static final String CURR_GAME_TAPS = "TapsOfCurrentGame";
    private static final String TOP_IND_POINTS = "TopPoints";
    private static final String TOP_IND_STARS = "TopStars";
    private static final String TOP_IND_TAPS = "TopTaps";
    private static final String LAST_COMP_LVL = "LastCompletedLevel";
    private static final String TAG = "Data Manager";

    /* The file to write and read */
    private static final String DATA_FILE = "game_data.txt";

    /* The map that stores all the users */
    private Map<String, User> userMap = new HashMap<>();

    /**
     * Constructs a FileDataManager
     */
    public FileDataManager() {
        readFromFile();
    }

    /**
     * Writes all the users of this application to DATA_FILE.
     *
     * @param users the Collection of User objects to write to file
     */
    private void writeToFile(Collection<User> users) {
        PrintWriter out = null;

        try {
            String filePath = AppManager.getInstance().getContext().getFilesDir().getPath() + DATA_FILE;
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            out = new PrintWriter(outStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "Error encountered trying to open file for writing: " + DATA_FILE);
        }

        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            out.println(USERNAME + ":" + user.getUserName());
            out.println(PASSWORD + ":" + user.getPassword());
            out.println(CHARAC_COLOUR + ":" + user.getCustomization().getCharacterColour());
            out.println(COLOUR_SCHEME + ":" + user.getCustomization().getColourScheme());
            out.println(MUSIC + ":" + user.getCustomization().getMusicPath());
            out.println(TOP_IND_POINTS + ":" + user.getTopIndividualStats().getPoints());
            out.println(TOP_IND_STARS + ":" + user.getTopIndividualStats().getStars());
            out.println(TOP_IND_TAPS + ":" + user.getTopIndividualStats().getTaps());
            out.println(TOP_GAME_POINTS + ":" + user.getStatsOfTopGame().getPoints());
            out.println(TOP_GAME_STARS + ":" + user.getStatsOfTopGame().getStars());
            out.println(TOP_GAME_TAPS + ":" + user.getStatsOfTopGame().getTaps());
            out.println(CURR_GAME_POINTS + ":" + user.getStatsOfCurrentGame().getPoints());
            out.println(CURR_GAME_STARS + ":" + user.getStatsOfCurrentGame().getStars());
            out.println(CURR_GAME_TAPS + ":" + user.getStatsOfCurrentGame().getTaps());
            out.println(LAST_COMP_LVL + ":" + user.getLastCompletedLevel());
        }
        out.close();
    }

    /**
     * Reads the information in DATA_FILE and stores the information into userMap
     */
    private void readFromFile() {
        String filePath = AppManager.getInstance().getContext().getFilesDir().getPath() + DATA_FILE;
        userMap.clear();

        try (Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            User user = null;
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str != null && str.trim().length() > 0 && str.split(":").length > 1) {
                    String[] line = str.split(":");
                    String key = line[0];
                    String value;
                    if (USERNAME.equals(key)) {
                        if (line.length > 1) {
                            value = line[1];
                            user = new User(value, "");
                            userMap.put(value.toLowerCase(), user);
                        } else {
                            user = null;
                        }
                    } else if (user != null && line.length > 1) {
                        value = line[1];
                        if (PASSWORD.equals(key)) {
                            user.setPassword(value);
                        } else if (CHARAC_COLOUR.equals(key)) {
                            String characColour = value;
                            if (characColour.equals(Customization.CharacterColour.RED.toString())) {
                                user.getCustomization().setCharacterColour(Customization.CharacterColour.RED);
                            } else if (characColour.equals(Customization.CharacterColour.YELLOW.toString())) {
                                user.getCustomization().setCharacterColour(Customization.CharacterColour.YELLOW);
                            } else {
                                user.getCustomization().setCharacterColour(Customization.CharacterColour.BLUE);
                            }
                        } else if (COLOUR_SCHEME.equals(key)) {
                            String colourScheme = value;
                            if (colourScheme.equals(Customization.ColourScheme.LIGHT.toString())) {
                                user.getCustomization().setColourScheme(Customization.ColourScheme.LIGHT);
                            } else {
                                user.getCustomization().setColourScheme(Customization.ColourScheme.DARK);
                            }
                        } else if (MUSIC.equals(key)) {
                            String music = value;
                            if (music.equals(Customization.MusicPath.SONG2.toString())) {
                                user.getCustomization().setMusicPath(Customization.MusicPath.SONG2);
                            } else if (music.equals(Customization.MusicPath.SONG3.toString())) {
                                user.getCustomization().setMusicPath(Customization.MusicPath.SONG3);
                            } else {
                                user.getCustomization().setMusicPath(Customization.MusicPath.SONG1);
                            }
                        } else if (TOP_GAME_POINTS.equals(key)) {
                            user.getStatsOfTopGame().setPoints(Integer.parseInt(value));
                        } else if (TOP_GAME_STARS.equals(key)) {
                            user.getStatsOfTopGame().setStars(Integer.parseInt(value));
                        } else if (TOP_GAME_TAPS.equals(key)) {
                            user.getStatsOfTopGame().setTaps(Integer.parseInt(value));
                        } else if (CURR_GAME_POINTS.equals(key)) {
                            user.getStatsOfCurrentGame().setPoints(Integer.parseInt(value));
                        } else if (CURR_GAME_STARS.equals(key)) {
                            user.getStatsOfCurrentGame().setStars(Integer.parseInt(value));
                        } else if (CURR_GAME_TAPS.equals(key)) {
                            user.getStatsOfCurrentGame().setTaps(Integer.parseInt(value));
                        } else if (TOP_IND_POINTS.equals(key)) {
                            user.getTopIndividualStats().setPoints(Integer.parseInt(value));
                        } else if (TOP_IND_STARS.equals(key)) {
                            user.getTopIndividualStats().setStars(Integer.parseInt(value));
                        } else if (TOP_IND_TAPS.equals(key)) {
                            user.getTopIndividualStats().setTaps(Integer.parseInt(value));
                        } else if (LAST_COMP_LVL.equals(key)) {
                            user.setLastCompletedLevel(Integer.parseInt(value));
                        } else {
                            Log.e(TAG, "Invalid key: " + DATA_FILE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error encountered trying to open file for reading: " + DATA_FILE);
        }
    }

    /**
     * Adds the given user to userMap and write userMap to file.
     *
     * @param user the User object to be added to userMap
     */
    public void createUser(User user) {
        userMap.put(user.getUserName().toLowerCase(), user);
        Collection<User> users = userMap.values();
        writeToFile(users);
    }

    /**
     * Adds the given user's updated information to userMap and write userMap to file.
     *
     * @param user the updated User object to be added to userMap
     */
    public void updateUser(User user) {
        updateUsersTopGameStats(user);
        updateUsersTopIndStats(user);
        createUser(user);
    }

    /**
     * Updates the statistics of the given user's top game.
     */
    private void updateUsersTopGameStats(User user) {
        // If the user's current score is higher than their top score, update the statistics of
        // their top game
        if (getCurrentScore(user) > getTopScore(user)) {
            user.getStatsOfTopGame().setPoints(user.getStatsOfCurrentGame().getPoints());
            user.getStatsOfTopGame().setStars(user.getStatsOfCurrentGame().getStars());
            user.getStatsOfTopGame().setTaps(user.getStatsOfCurrentGame().getTaps());
        }
    }

    /**
     * Updates the given user's top individual statistics.
     */
    private void updateUsersTopIndStats(User user) {
        int currentPoints = user.getStatsOfCurrentGame().getPoints();
        int currentStars = user.getStatsOfCurrentGame().getStars();
        int currentTaps = user.getStatsOfCurrentGame().getTaps();

        // If the user's current individual statistic is higher than their top individual statistic,
        // update it
        if (currentPoints > user.getTopIndividualStats().getPoints()) {
            user.getTopIndividualStats().setPoints(currentPoints);
        }
        if (currentStars > user.getTopIndividualStats().getStars()) {
            user.getTopIndividualStats().setStars(currentStars);
        }
        if (currentTaps > user.getTopIndividualStats().getTaps()) {
            user.getTopIndividualStats().setTaps(currentTaps);
        }
    }

    /**
     * Returns the user in userMap with the given username. If no such user exists, return null.
     *
     * @param username the username of a User
     * @return the User object with the given username. If no such User object exists, return null
     */
    public User getUser(String username) {
        return userMap.get(username);
    }

    /**
     * Returns the given user's top score.
     *
     * @param user a User object
     * @return the top score of the User object
     */
    public int getTopScore(User user) {
        return ScoreCalculator.calculateScore(
                user.getStatsOfTopGame().getPoints(),
                user.getStatsOfTopGame().getStars(),
                user.getStatsOfTopGame().getTaps());
    }

    /**
     * Returns the given user's current score.
     *
     * @param user a User object
     * @return the current score of the User object
     */
    public int getCurrentScore(User user) {
        return ScoreCalculator.calculateScore(
                user.getStatsOfCurrentGame().getPoints(),
                user.getStatsOfCurrentGame().getStars(),
                user.getStatsOfCurrentGame().getTaps());
    }

    /**
     * Returns a list of users sorted (in non-decreasing order) by score
     *
     * @return a list of User objects sorted (in non-decreasing order) by score
     */
    public List sortUsersByScore() {
        return sortUsers(new ScoreComparator());
    }

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of points
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of points
     */
    public List sortUsersByPoints() {
        return sortUsers(new PointsComparator());
    }

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of stars
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of stars
     */
    public List sortUsersByStars() {
        return sortUsers(new StarsComparator());
    }

    /**
     * Returns a list of users sorted (in non-decreasing order) by number of taps
     *
     * @return a list of User objects sorted (in non-decreasing order) by number of taps
     */
    public List sortUsersByTaps() {
        return sortUsers(new TapsComparator());
    }

    /**
     * Returns a list of users sorted (in non-decreasing order) using the given comparator
     *
     * @param comparator a Comparator object
     * @return a list of User objects sorted (in non-decreasing order) using the Comparator object
     */
    private List sortUsers(Comparator comparator) {
        List<User> userList = new ArrayList(userMap.values());
        Collections.sort(userList, comparator);
        return userList;
    }
}
