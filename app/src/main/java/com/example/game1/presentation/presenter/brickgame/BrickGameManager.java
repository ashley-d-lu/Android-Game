package com.example.game1.presentation.presenter.brickgame;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.brickgame.Ball;
import com.example.game1.presentation.model.brickgame.Brick;
import com.example.game1.presentation.model.brickgame.BrickMovementInfo;
import com.example.game1.presentation.model.brickgame.Paddle;
import com.example.game1.presentation.model.brickgame.BrickStar;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.presenter.common.GameManager;

import java.util.List;

/**
 * A GameManager for a Brick Breaking minigame. Includes extra variables and methods for handling
 * game-specific features.
 */
public class BrickGameManager extends GameManager {
  // constants
  private final int NUM_BRICK_LAYERS = 3;
  private final int NUM_BRICKS_HORIZONTAL = 6;
  private final int BRICK_HEIGHT = 80;
  private final int STAR_WIDTH = 80;
  private final int STAR_HEIGHT = 80;
  private final int PADDLE_WIDTH = 300; // 1550
  private final int PADDLE_HEIGHT = 60;
  private final int BALL_WIDTH = 80;
  private final int BALL_HEIGHT = 80;
  private final double BALL_VELOCITY_X = 900;
  private final double BALL_VELOCITY_Y = 900;

  private Paddle paddle;
  private Ball ball;
  private List<Brick> bricks;
  private List<BrickStar> stars;
  private int numBroken = 0;
  private List<Bitmap> paddleBlueBmps;
  private List<Bitmap> paddleRedBmps;
  private List<Bitmap> paddleYellowBmps;

  /**
   * Constructs a BrickGameManager with the specified height and width.
   *
   * @param height the height of the BrickGameManager
   * @param width the width of the BrickGameManager
   * @param game the game being played
   * @param activity the activity containing the game
   */
  public BrickGameManager(int height, int width, Game game, AppCompatActivity activity) {
    super(height, width, game, activity);
  }

  /**
   * Sets the ball in this game.
   *
   * @param ball the ball to set.
   */
  void setBall(Ball ball) {
    this.ball = ball;
  }

  /**
   * Returns the ball in this game
   *
   * @return the ball in this game
   */
  public Ball getBall() {
    return ball;
  }

  /**
   * Sets the bricks in this game
   *
   * @param bricks the bricks in this game
   */
  void setBricks(List<Brick> bricks) {
    this.bricks = bricks;
  }

  /**
   * Returns the bricks in this game
   *
   * @return the bricks in this game
   */
  public List<Brick> getBricks() {
    return bricks;
  }

  /**
   * Sets the stars in this game
   *
   * @param stars the stars in this game
   */
  public void setStars(List<BrickStar> stars) {
    this.stars = stars;
  }

  /**
   * Returns the stars in this game
   *
   * @return the stars in this game
   */
  public List<BrickStar> getStars() {
    return stars;
  }

  /**
   * Returns the Paddle in this brick game
   *
   * @return the Paddle in this brick game
   */
  public Paddle getPaddle() {
    return paddle;
  }

  /**
   * Sets the paddle in this game
   *
   * @param paddle the paddle in this game
   */
  void setPaddle(Paddle paddle) {
    this.paddle = paddle;
  }

  /**
   * returns the number of successful jumps performed by the player.
   *
   * @return the number of obstacles jumped by the player
   */
  public int getNumBroken() {
    return numBroken;
  }

  /**
   * Sets the number of successful jumps made by this user.
   *
   * @param numBroken the new number of jumps to set
   */
  public void setNumBroken(int numBroken) {
    this.numBroken = numBroken;
  }



  /** Creates GameItems required at the beginning of the minigame. */
  public void createGameItems() {
    BrickItemsBuilder builder = new BrickItemsBuilder();

    builder.createPaddle(PADDLE_HEIGHT, PADDLE_WIDTH);
    builder.createBricks(
        getScreenWidth(), NUM_BRICKS_HORIZONTAL, NUM_BRICK_LAYERS, BRICK_HEIGHT);
    builder.createBall(
        BALL_WIDTH,
        BALL_HEIGHT,
        getScreenWidth(),
        BRICK_HEIGHT,
        NUM_BRICK_LAYERS,
        BALL_VELOCITY_X,
        BALL_VELOCITY_Y);
    builder.placeItems(this);

    setRunning(true);
  }



  /** updates all items in this game */
  @Override
  public boolean update() {

    BrickMovementInfo brickMovementInfo =
        new BrickMovementInfo(
            ball,
            bricks,
            stars,
            paddle,
            gameItems,
            getScreenHeight(),
            getScreenWidth(),
            getNumSeconds());
    brickMovementInfo.animate();

    numBroken += brickMovementInfo.getNumBroken();
    setNumStars(getNumStars() + brickMovementInfo.getNumStars());
    for (GameItem item : gameItems) {
      item.update(brickMovementInfo);
    }

    for (double[] coordinates : brickMovementInfo.getStarsToAdd()) {
      addStar(coordinates);
    }

    if (!brickMovementInfo.continueGame()) {
      gameOver();
    }
    return brickMovementInfo.continueGame();
  }

  /**
   * Adds the stars at the specified coordinates.
   *
   * @param coordinates stores the coordinates at which to add the stars
   */
  private void addStar(double[] coordinates) {
    BrickStar star = new BrickStar(STAR_WIDTH, STAR_HEIGHT);
    star.setPosition(
        coordinates[0] + getScreenWidth() / NUM_BRICKS_HORIZONTAL / 2 - STAR_WIDTH / 2,
        coordinates[1]);
    gameItems.add(star);
    stars.add(star);
  }

  /**
   * Moves the paddle when the screen is tapped
   *
   * @param xCoordinate the x coordinate at which to move the paddle's centre
   */
  public void onTouchEvent(double xCoordinate) {
    paddle.setXCoordinate(xCoordinate - paddle.getWidth() / 2);
  }

  /** Ends this minigame. */
  public void gameOver() {
    setRunning(false);
    // set points here
    game.getStatistics().setPoints(numBroken);
    game.getStatistics().setStars(getNumStars());
    game.getStatistics().setTaps(getNumTaps());

    super.gameOver();
  }

  /**
   * Sets the paddle to it's default starting position
   *
   * @param paddle the paddle to set
   */
  public void setPaddlePosition(Paddle paddle) {
    paddle.setXCoordinate((getScreenWidth() - PADDLE_WIDTH) / 2);
    paddle.setYCoordinate(getScreenHeight() - PADDLE_HEIGHT * 6);
  }

  /**
   * sets the bmp files to use for this game's items
   *
   * @param paddleBlueBmps bmp files for the blue paddle
   * @param paddleRedBmps bmp files for the red paddle
   * @param paddleYellowBmps bmp files for the yellow paddle
   */
  public void setBmpfiles(
      List<Bitmap> paddleBlueBmps,
      List<Bitmap> paddleRedBmps,
      List<Bitmap> paddleYellowBmps) {
    this.paddleBlueBmps = paddleBlueBmps;
    this.paddleYellowBmps = paddleYellowBmps;
    this.paddleRedBmps = paddleRedBmps;
  }

  /**
   * Returns the number of brick layers in this game.
   *
   * @return the number of brick layers in this game.
   */
  public int getNumBrickLayers() {
    return NUM_BRICK_LAYERS;
  }

  /**
   * Returns the number of bricks horizontally in this game
   *
   * @return the number of bricks horizontally in this game
   */
  public int getNumBricksHorizontal() {
    return NUM_BRICKS_HORIZONTAL;
  }

  /**
   * Returns the brick height in this game
   *
   * @return the brick height in this game
   */
  public int getBrickHeight() {
    return BRICK_HEIGHT;
  }

  /**
   * Returns the star width in this game
   *
   * @return the star width in this game
   */
  public int getStarWidth() {
    return STAR_WIDTH;
  }

  /**
   * Returns the star height in this game
   *
   * @return the star height in this game
   */
  public int getStarHeight() {
    return STAR_HEIGHT;
  }

  /**
   * Returns the paddle width in this game
   *
   * @return the paddle width in this game
   */
  public int getPaddleWidth() {
    return PADDLE_WIDTH;
  }

  /**
   * Returns the paddle height in this game
   *
   * @return the paddle height in this game
   */
  public int getPaddleHeight() {
    return PADDLE_HEIGHT;
  }

  /**
   * Returns the ball width in this game
   *
   * @return the ball width in this game
   */
  public int getBallWidth() {
    return BALL_WIDTH;
  }

  /**
   * Returns the ball height in this game
   *
   * @return the ball height in this game
   */
  public int getBallHeight() {
    return BALL_HEIGHT;
  }
}
