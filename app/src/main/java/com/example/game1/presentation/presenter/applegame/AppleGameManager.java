package com.example.game1.presentation.presenter.applegame;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.Statistics;
import com.example.game1.presentation.model.applegame.Apple;
import com.example.game1.presentation.model.applegame.AppleMovementInfo;
import com.example.game1.presentation.model.applegame.AppleResult;
import com.example.game1.presentation.model.applegame.AppleStar;
import com.example.game1.presentation.model.applegame.Basket;
import com.example.game1.presentation.model.applegame.LivesCounter;
import com.example.game1.presentation.model.applegame.PointsCounter;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.Result;
import com.example.game1.presentation.presenter.common.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** An AppleGameManager */
public class AppleGameManager extends GameManager {
  /** Width of the apple */
  private int appleWidth;
  /** Height of the apple */
  private int appleHeight;
  /** Width of the apple star */
  private int starWidth;
  /** Height of the apple star */
  private int starHeight;
  /** Width of the basket */
  private int basketWidth;
  /** Height of the basket */
  private int basketHeight;
  /** A basket in this apple game */
  private Basket basket;
  /** A pointsCounter in this apple game */
  private PointsCounter points;
  /** A lives counter in this apple game */
  private LivesCounter livesCounter;
  /** Number of stars caught in this apple game */
  private int numCaughtStars = 0;

  /**
   * Constructs an AppleGameManager with the specified height, width, game, and activity.
   *
   * @param height screen height
   * @param width screen width
   * @param game the game
   * @param activity the activity
   */
  public AppleGameManager(int height, int width, Game game, AppCompatActivity activity) {
    super(height, width, game, activity);
  }

  /**
   * Sets the basket for this apple game.
   *
   * @param basket basket
   */
  void setBasket(Basket basket) {
    this.basket = basket;
  }

  /**
   * Sets the points counter for this apple game.
   *
   * @param pointsCounter points counter
   */
  void setPointsCounter(PointsCounter pointsCounter) {
    this.points = pointsCounter;
  }

  /**
   * Sets the lives counter for this apple game.
   *
   * @param livesCounter lives counter
   */
  void setLivesCounter(LivesCounter livesCounter) {
    this.livesCounter = livesCounter;
  }

  /** Creates GameItems required at the beginning of the minigame. */
  @Override
  public void createGameItems() {
    AppleItemsBuilder builder = new AppleItemsBuilder();
    builder.setBasketSize(basketWidth, basketHeight);
    builder.createPointsCounter();
    builder.createLivesCounter();
    builder.createBasket();
    builder.placeItems(this);
  }

  /**
   * Moves this AppleGameManager's Basket to the specified x coordinate.
   *
   * @param xCoordinate the x coordinate to move this Basket to
   */
  public void moveBasket(int xCoordinate) {
    basket.move(xCoordinate);
  }

  /**
   * Updates the game items in this apple game by executing method update for each game item. Then
   * based on the result update the statistics information of this game.
   *
   * @return if there is still lives remaining in this game
   */
  public boolean update() {
    // Check if there is still lives in this game.
    boolean hasLives = checkLivesRemaining();
    // OldItems list stores GameItem to be removed from gameItems
    List<GameItem> oldItems = new ArrayList<>();
    // Creates apple movement info to store all information needed for game items to execute update
    AppleMovementInfo appleMovementInfo =
        new AppleMovementInfo(getScreenWidth(), getScreenHeight(), basket, getNumSeconds());
    // Iterate through the gameItems
    for (GameItem item : getGameItems()) {
      Result result = item.update(appleMovementInfo);
      // Extract items to be removed from result and add them to the oldItems list so that they can
      // be removed later
      if (result.getOldItems() != null) {
        for (GameItem oldItem : result.getOldItems()) {
          oldItems.add(oldItem);
        }
      }
      // Update Statistics data according to the update result
      if (result instanceof AppleResult) {
        updateStatistics((AppleResult) result);
      }
    }
    // remove all items in the oldItems list
    removeOldItems(oldItems);
    // generates new game items
    spawnNew();
    // return if there are lives in this game
    return hasLives;
  }

  /** Increments points by 1. */
  private void catchApple() {
    points.addPoints(1);
  }

  /** Increments numStarsCaught by 1. */
  private void catchStar() {
    numCaughtStars += 1;
  }

  /** Spawns new game items. */
  private void spawnNew() {
    // get a random x-coordinate to spawn the new Apple/AppleStar
    Random randCoordinate = new Random();
    int spawnCoordinate = randCoordinate.nextInt(getGridWidth() - 80);

    // decide whether to spawn an Apple or a Star or nothing
    Random randItem = new Random();
    int randint = randItem.nextInt(200);
    if (randint < 2) {
      spawnStar(spawnCoordinate);
    } else if (randint < 9) {
      spawnApple(spawnCoordinate);
    }
    // else spawn nothing
  }

  /**
   * Spawns a new AppleStar.
   *
   * @param spawnCoordinate xCoodinate of the new AppleStar
   */
  private void spawnStar(int spawnCoordinate) {
    // generate a new star and set velocity for y coordinate
    AppleStar nextItem = new AppleStar(starWidth, starHeight);
    nextItem.setYVelocity(250);
    // place it in the gameItems list and set its position
    place(nextItem);
    nextItem.setPosition(spawnCoordinate, 0);
  }

  /**
   * Spawns a new Apple.
   *
   * @param spawnCoordinate xCoordinate of the new Apple
   */
  private void spawnApple(int spawnCoordinate) {
    Apple nextItem = new Apple(appleWidth, appleHeight);
    // Set velocity for y coordinate
    // and place it in the gameItems list and set its position
    nextItem.setYVelocity(350);
    place(nextItem);
    nextItem.setPosition(spawnCoordinate, 0);
  }

  /** Ends this minigame. */
  public void gameOver() {
    // Update statistics
    Statistics gameStatistics = game.getStatistics();
    gameStatistics.setPoints(gameStatistics.getPoints() + points.getNumPoints());
    gameStatistics.setStars(gameStatistics.getStars() + numCaughtStars);
    gameStatistics.setTaps(gameStatistics.getTaps() + getNumTaps());
    super.gameOver();
  }

  /**
   * Sets up width and height for each game item.
   *
   * @param appleWidth Width of apple
   * @param appleHeight Height of apple
   * @param starWidth Width of star
   * @param starHeight Height of star
   * @param basketWidth Width of basket
   * @param basketHeight Height of basket
   */
  public void setItemSize(
      int appleWidth,
      int appleHeight,
      int starWidth,
      int starHeight,
      int basketWidth,
      int basketHeight) {
    this.appleWidth = appleWidth;
    this.appleHeight = appleHeight;
    this.starWidth = starWidth;
    this.starHeight = starHeight;
    this.basketWidth = basketWidth;
    this.basketHeight = basketHeight;
  }

  /**
   * Returns whether or not there are any lives remaining in this game.
   *
   * @return whether there are any lives remaining in this game
   */
  private boolean checkLivesRemaining() {
    if (livesCounter.getLivesRemaining() <= 0) {
      gameOver();
      return false;
    }
    return true;
  }

  /**
   * Removes all items stored in oldItems from gameItems.
   *
   * @param oldItems the items to remove
   */
  private void removeOldItems(List<GameItem> oldItems) {
    for (GameItem oldItem : oldItems) {
      removeItem(oldItem);
    }
  }

  /**
   * Updates statistics according to gameItem's update result.
   *
   * @param result the result
   */
  private void updateStatistics(AppleResult result) {
    if (result.isAppleCollected()) {
      catchApple();
    }
    if (result.isAppleDropped()) {
      livesCounter.subtractLife();
    }
    if (result.isStarCollected()) {
      catchStar();
    }
  }
}
