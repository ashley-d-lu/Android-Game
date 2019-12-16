package com.example.game1.presentation.model.brickgame;

import android.graphics.Bitmap;

import com.example.game1.presentation.model.brickgame.Ball;
import com.example.game1.presentation.model.brickgame.Brick;
import com.example.game1.presentation.model.brickgame.Paddle;
import com.example.game1.presentation.model.brickgame.BrickStar;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.presenter.brickgame.BrickGameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles game item interactions and movement in the brick minigame.
 */
public class BrickMovementInfo extends MovementInfo {
  private Ball ball;
  private List<Brick> bricks;
  private List<BrickStar> stars;
  private Paddle paddle;
  private List<GameItem> gameItems;
  private List<double[]> starsToAdd;

  private boolean continueGame;
  private int numBroken;
  private int numStars;

  private final double STAR_PROBABILITY = 0.8;

  /**
   * Constructs the BrickMovementInfo instance needed to control the movement of items in the brick
   * minigame
   *
   * @param ball the ball in this game
   * @param bricks the bricks in this game
   * @param stars the stars in this game
   * @param paddle the paddle in this game
   * @param gameItems all the items in this game
   * @param screenHeight the height of the screen
   * @param screenWidth the width of the screen
   * @param numSeconds the number of seconds that each frame lasts
   */
  public BrickMovementInfo(
      Ball ball,
      List<Brick> bricks,
      List<BrickStar> stars,
      Paddle paddle,
      List<GameItem> gameItems,
      int screenHeight,
      int screenWidth,
      double numSeconds) {
    super(numSeconds, screenHeight, screenWidth);
    this.ball = ball;
    this.bricks = bricks;
    this.stars = stars;
    this.paddle = paddle;
    this.gameItems = gameItems;
    continueGame = true;
  }

  /** Causes the items in this game to move and interact with each other */
  public void animate() {
    // newItems list stores GameItem to be added to gameItems
    List<GameItem> newItems = new ArrayList<>();
    // oldItem list stores GameItem to be removed from gameItems
    List<GameItem> oldItems = new ArrayList<>();
    // note, right now, stars are the only object that get removed
    starsToAdd = new ArrayList<>();

    // update ball and check if game is over
    if (!updateBall()) {
      continueGame = false;
    }

    updateStars(oldItems);
    updateBricks(oldItems);

    for (GameItem newItem : newItems) {
      gameItems.add(newItem);
    }

    removeOldItems(oldItems);
  }

  /**
   * Returns the number of bricks broken in this game
   *
   * @return the number of bricks broken in this game
   */
  public int getNumBroken() {
    return numBroken;
  }

  /**
   * Returns the number of stars collected in this game
   *
   * @return the number of stars collected in this game
   */
  public int getNumStars() {
    return numStars;
  }

  /**
   * Returns whether or not the game should continue (true if yes)
   *
   * @return whether or not the game should continue (true if yes)
   */
  public boolean continueGame() {
    return continueGame;
  }

  /**
   * Updates and adjusts the movement of the ball in this game
   *
   * @return false iff the ball is in a state that causes the game to end
   */
  private boolean updateBall() {
    if (ball.getXCoordinate() + ball.getWidth() >= getScreenWidth() || ball.getXCoordinate() < 0) {
      ball.setXVelocity(-ball.getXVelocity());
    }
    // out of top boundary
    if (ball.getYCoordinate() < 0) {
      ball.setYVelocity(Math.abs(ball.getYVelocity()));
    }
    // overlapping and not more than halfway through
    if (ball.isOverlapping(paddle)
        && ball.getYCoordinate() + ball.getHeight()
            < paddle.getYCoordinate() + paddle.getHeight() / 2) {
      ball.setYVelocity((-Math.abs(ball.getYVelocity())));
    }
    if (ball.getYCoordinate() > getScreenHeight()) {
      return false;
    }
    return true;
  }

  /**
   * Updates all the stars in this game and keeps track of collected stars.
   *
   * @param oldItems the game items currently in the game (prior to star collection)
   */
  private void updateStars(List<GameItem> oldItems) {
    for (BrickStar star : stars) {
      if (ball.isOverlapping(star)) {
        oldItems.add(star);
        numStars += 1;
      }
    }
  }

  /**
   * Updates all the bricks in this game and handles collisions with bricks.
   *
   * @param oldItems the game items currently in this game
   */
  public void updateBricks(List<GameItem> oldItems) {
    for (Brick brick : bricks) {
      if (ball.isOverlapping(brick)) {
        boolean broken = brick.damageBrick();
        if (broken) {
          oldItems.add(brick);
          numBroken++;
          if (Math.random() > STAR_PROBABILITY) {
            double[] coordinates = {brick.getXCoordinate(), brick.getYCoordinate()};
            starsToAdd.add(coordinates);
          }
        }
        ball.setYVelocity(Math.abs(ball.getYVelocity()));
      }
    }
  }

  /**
   * Removes oldItems from the current list of game items (because they were destroyed or collected)
   *
   * @param oldItems the items to remove from the game
   */
  public void removeOldItems(List<GameItem> oldItems) {
    for (GameItem oldItem : oldItems) {
      gameItems.remove(oldItem);
      if (oldItem instanceof Brick) {
        bricks.remove(oldItem);
      } else if (oldItem instanceof BrickStar) {
        stars.remove(oldItem);
      }
    }
  }

  /**
   * Returns the coordinates where new stars need to be added in this game
   *
   * @return the coordinates where new stars need to be added in this game
   */
  public List<double[]> getStarsToAdd() {
    return starsToAdd;
  }
}
