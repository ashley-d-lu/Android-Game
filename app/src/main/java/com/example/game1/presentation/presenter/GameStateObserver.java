package com.example.game1.presentation.presenter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.presenter.common.GameManager;

import java.util.Observable;
import java.util.Observer;

/**
 * An observer class that responds to game state changes.
 */
public class GameStateObserver implements Observer {

    private UserManager userManager;

    /**
     * This method is called when the state of a game has changed. It notifies the rest of the system
     * of this state change.
     *
     * @param o   the object notifying this method of a game state change
     * @param arg an object passed in by o. It may contain data needed by this method
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameManager) {
            GameManager gameManager = (GameManager) o;
            // Currently, this method only takes action if a game has stopped.
            if (GameManager.State.STOP.equals(gameManager.getState())) {
                finishGame(gameManager.getGame(), gameManager.getActivity());
            }
        }
    }

    /**
     * Injects an instance of UserManager into this GameStateObserver.
     *
     * @param userManager an instance of UserManager
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Notifies the system that a game has finished.
     *
     * @param game     the Game class corresponding to the game that was just finished
     * @param activity the Activity class corresponding to the game that just finished
     */
    private void finishGame(Game game, AppCompatActivity activity) {
        // Currently, this method only notifies userManager.
        if (userManager != null) {
            userManager.updateCurrentUsersGame(game);
            userManager.goToStatsPage(activity);
        }
    }
}
