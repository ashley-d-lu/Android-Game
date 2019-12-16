package com.example.game1.presentation.presenter.tappinggame;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.tappinggame.Runner;
import com.example.game1.presentation.model.tappinggame.SpeedDisplayer;
import com.example.game1.presentation.model.tappinggame.StarDisplayer;
import com.example.game1.presentation.model.tappinggame.TapCounter;
import com.example.game1.presentation.model.tappinggame.TappingCircle;
import com.example.game1.presentation.model.tappinggame.TappingMovementInfo;
import com.example.game1.presentation.model.tappinggame.TimerDisplayer;
import com.example.game1.presentation.presenter.common.GameManager;
import java.util.List;

/** A Tapping Game Manager */
public class TappingGameManager extends GameManager {
  /** The width of the runner */
  private int runnerWidth;
  /** The height of the runner */
  private int runnerHeight;
  /** A tapping circle */
  public TappingCircle tappingCircle;
  /** A runner of this game */
  public Runner runner;
  /** A tap counter */
  public TapCounter tapCounter;
  /** A time Displayer */
  public TimerDisplayer timerDisplayer;
  /** A Star Displayer */
  public StarDisplayer starDisplayer;
  /** A Speed Displayer */
  public SpeedDisplayer speedDisplayer;
  /** Number of seconds left in this game */
  private int secondsLeft;
  /** The tapping speed */
  private int tappingSpeed;
  /** The grid Width of the screen */
  private int gridWidth;
  /** The grid Height of the screen */
  private int gridHeight;

  /**
   * Get grid width of the screen
   *
   * @return grid width of the screen
   */
  @Override
  public int getGridWidth() {
    return gridWidth;
  }

  /**
   * Get grid height of the screen
   *
   * @return grid height of the screen
   */
  @Override
  public int getGridHeight() {
    return gridHeight;
  }

  /**
   * Constructs a TappingGameManager with the specified height, width, game, and activity.
   *
   * @param height height of the screen
   * @param width width of the screen
   * @param game the game this game manager is for
   * @param activity the activity
   */
  public TappingGameManager(int height, int width, Game game, AppCompatActivity activity) {
    super(height, width, game, activity);
  }

  /** execute update for all items in the gameItems list of this game */
  public boolean update() {
    // get game items list
    List<GameItem> Items = getGameItems();
    // Creates tapping movement info to store all information needed for game items to execute
    // update
    TappingMovementInfo tappingMovementInfo =
        new TappingMovementInfo(
            getScreenHeight(),
            getScreenWidth(),
            tappingSpeed,
            secondsLeft,
            getNumTaps(),
            getNumSeconds());
    // Iterate through the gameItems and execute update for each of the game item
    for (GameItem item : Items) {
      item.update(tappingMovementInfo);
    }
    return true;
  }

  /** Create game items needed for this game */
  public void createGameItems() {
    // Create tapping circle
    this.tappingCircle = new TappingCircle(0.0, 0.0);
    place(tappingCircle);
    // Create runner
    this.runner = new Runner(0.0, getScreenHeight() * 3 / 4, runnerWidth, runnerHeight);
    place(runner);
    // Create tap counter
    this.tapCounter = new TapCounter(getGridWidth() * 1 / 3, getGridHeight() * 5 / 8);
    place(tapCounter);
    // Create time displayer
    this.timerDisplayer = new TimerDisplayer(getGridWidth() * 1 / 3, getGridHeight() * 5 / 8 + 1);
    place(timerDisplayer);
    // Create speed displayer
    this.speedDisplayer = new SpeedDisplayer(getGridWidth() * 1 / 3, getGridHeight() * 5 / 8 + 2);
    place(speedDisplayer);
    // Create star displayer
    this.starDisplayer = new StarDisplayer(getGridWidth() * 1 / 3, getGridHeight() * 5 / 8 + 3);
    place(starDisplayer);
  }

  /** Ends this minigame. */
  public void gameOver() {
    // Send statistics
    game.getStatistics().setPoints(tapCounter.getNumTaps());
    game.getStatistics().setStars(starDisplayer.getNumStar());
    game.getStatistics().setTaps(tapCounter.getNumTaps());
    super.gameOver();
  }

  /**
   * Set Number of seconds left
   *
   * @param secondsLeft
   */
  public void setSecondsLeft(int secondsLeft) {
    this.secondsLeft = secondsLeft;
  }

  /**
   * Set tapping speed
   *
   * @param tappingSpeed
   */
  public void setTappingSpeed(int tappingSpeed) {
    this.tappingSpeed = tappingSpeed;
  }

  /**
   * Set item size for game items
   *
   * @param runnerWidth the width of runner
   * @param runnerHeight the height of runner
   */
  public void setItemSize(int runnerWidth, int runnerHeight) {
    this.runnerWidth = runnerWidth;
    this.runnerHeight = runnerHeight;
  }

  /**
   * Set gridwidth and gridHeight for this game screen
   *
   * @param gridWidth gridWidth of the game screen
   * @param gridHeight gridHeight of the game screen
   */
  public void setGridWidthHeight(int gridWidth, int gridHeight) {
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
  }
}
