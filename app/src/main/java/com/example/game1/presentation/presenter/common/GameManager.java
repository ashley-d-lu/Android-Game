package com.example.game1.presentation.presenter.common;

import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.common.GameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * A game manager. It acts as a mediator between the view and the model of a game. It is observed by
 * GameStateObserver.
 */
public abstract class GameManager extends Observable {

    /* The Game that this GameManager manages */
    public Game game;
    /* The possible states of a GameManager. */
    public enum State {START, PAUSE, STOP, RESUME}
    /* The state of this GameManager. */
    private State state;
    /* The list of GameItems in this GameManager. */
    protected List<GameItem> gameItems = new ArrayList<>();
    /* The width of the game display. */
    private int width;
    /* The height of the game display. */
    private int height;
    /* The Activity class of the game this GameManager manages.*/
    private AppCompatActivity activity;
    /* The music player of the game that this GameManager manages. */
    private MediaPlayer musicPlayer;
    /* The number of taps tracked */
    private int numTaps;
    /* The number of star tracked */
    private int numStars;
    /** The number of seconds used to refresh new xCoordinate, yCoordinate, xVelocity, * yVelocity
     * based on current xAcceleration and yAcceleration. Currently numOfSeconds = *
     * GameThread.FRAME_DURATION_NS / 1000000000.
     */
    private double numSeconds;
    /* If the game is running */
    private boolean isRunning;

    /** Constructs a GameManager with the specified height, width, game, and activity.
     *
     * @param game     the Game class corresponding to the game that the GameManager manages
     * @param height   the height of the game display
     * @param width    the width of the game display
     * @param activity the Activity class corresponding to the game that the GameManager manages
     */
    public GameManager(int height, int width, Game game, AppCompatActivity activity) {
        this.height = height;
        this.width = width;
        this.game = game;
        this.activity = activity;
    }

    /** Returns gameItems.
     *
     * @return a list of GameItem objects managed by this GameManager
     */
    public List<GameItem> getGameItems() {
        return gameItems;
    }

    /** Adds the specified item to gameItems.
     *
     * @param item the GameItem object to be added to gameItems
     */
    public void place(GameItem item) {
        gameItems.add(item);
    }

    /**
     * Returns the Game objects of this GameManager
     *
     * @return the Game object of this GameManager
     */
    public Game getGame() {
        return game;
    }

    /** Updates this GameManager by moving all GameItems in it. */
    public abstract boolean update();

    /** Removes the specified item from gameItems.
     *
     * @param item the GameItem object to be removed from gameItmes
     */
    protected void removeItem(GameItem item) {
        gameItems.remove(item);
    }

    /** Creates some GameItems and adds them to this GameManager. */
    public abstract void createGameItems();

    /**
     * Sets the game being played
     *
     * @param game the game being played
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * returns the current state of the game
     *
     * @return the current state of the game
     */
    public State getState() {
        return state;
    }

    /**
     * Returns the activity associated with this game.
     *
     * @return the activity associated with this game.
     */
    public AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Sets the activity associated with this game.
     *
     * @param activity the activity to associate with this game.
     */
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Returns the width of the screen the game is being played on
     *
     * @return the width of the screen the game is being played on
     */
    public int getScreenWidth() {
        return width;
    }

    /**
     * Sets the width of the screen the game is being played on
     *
     * @param width the screen width to set for this game
     */
    public void setScreenWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the screen the game is being played on
     *
     * @return the height of the screen the game is being played on
     */
    public int getScreenHeight() {
        return height;
    }

    /**
     * Sets the height of the screen the game is being played on
     *
     * @param height the screen height to set for this game
     */
    public void setScreenHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the grid width of the screen the game is being played on
     *
     * @return the grid width of the screen the game is being played on
     */
    public int getGridWidth() {
        return width;
    }

    /**
     * Returns the grid height of the screen the game is being played on
     *
     * @return the grid height of the screen the game is being played on
     */
    public int getGridHeight() {
        return height;
    }

    /**
     * Sets the music player for this game
     *
     * @param musicPlayer the music player to set for this game
     */
    public void setMusicPlayer(MediaPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    /** Starts playing the music for this game. */
    public void startMusic() {
        musicPlayer.start();
    }

    /** Stops playing the music for this game. */
    private void stopMusic() {
        musicPlayer.stop();
        musicPlayer.release();
    }

    /** Ends this game */
    public void gameOver() {
        stopMusic();
        state = State.STOP;
        setChanged();
        notifyObservers(this);
    }

    /**
     * Returns the duration in seconds of a single game frame
     *
     * @return the duration in seconds of a single game frame
     */
    public double getNumSeconds() {
        return numSeconds;
    }

    /**
     * Sets the duration in seconds of a single game frame
     *
     * @param numSeconds the duration in seconds of a single game frame
     */
    public void setNumSeconds(double numSeconds) {
        this.numSeconds = numSeconds;
    }

    /**
     * Returns the number of screen taps so far in this game.
     *
     * @return the number of screen taps so far in this game.
     */
    public int getNumTaps() {
        return numTaps;
    }

    /**
     * Sets the number of screen taps so far in this game.
     *
     * @param numTaps the number of screen taps to set so far in this game.
     */
    public void setNumTaps(int numTaps) {
        this.numTaps = numTaps;
    }

    /**
     * returns the number of stars collected by the player in this game
     *
     * @return the number of stars collected by the player in this game
     */
    public int getNumStars() {
        return numStars;
    }

    /**
     * sets the number of stars collected by this player
     *
     * @param numStars the new number to set
     */
    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    /** Increments the number of taps so far in this game by 1 */
    public void incrementNumTaps() {
        numTaps += 1;
    }

    /**
     * Sets whether or not this game is running
     *
     * @param isRunning whether or not this game is running
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
