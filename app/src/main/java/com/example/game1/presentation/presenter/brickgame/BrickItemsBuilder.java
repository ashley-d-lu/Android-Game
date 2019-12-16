package com.example.game1.presentation.presenter.brickgame;

import android.graphics.Bitmap;

import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.brickgame.Ball;
import com.example.game1.presentation.model.brickgame.Brick;
import com.example.game1.presentation.model.brickgame.Paddle;
import com.example.game1.presentation.model.brickgame.BrickStar;

import java.util.ArrayList;
import java.util.List;

/** A builder that builds the items necessary for a Brick minigame. */
class BrickItemsBuilder {
  private Paddle paddle;
  private ArrayList<Brick> bricks;
  private Ball ball;
  private ArrayList<BrickStar> stars;

  /**
   * Creates a builder item based on the customization setting selected by the user
   */
  BrickItemsBuilder() {
    stars = new ArrayList<>();
  }

  /**
   * creates a paddle for the game
   *
   * @param paddleHeight the height of the paddle
   * @param paddleWidth the width of the paddle
   */
  void createPaddle(int paddleHeight, int paddleWidth) {
    paddle = new Paddle(paddleHeight, paddleWidth);
  }

  /**
   * Builds the bricks for this game
   *
   * @param screenWidth the width of the screen
   * @param numBricksHorizontal the number of bricks to fit horizontally
   * @param numBrickLayers the number of brick layers vertically
   * @param brickHeight the height of each brick
   */
  void createBricks(int screenWidth, int numBricksHorizontal, int numBrickLayers, int brickHeight) {
    bricks = new ArrayList<>();
    int brickWidth = screenWidth / numBricksHorizontal;
    for (int i = 0; i < numBrickLayers; i++) {
      for (int j = 0; j < numBricksHorizontal; j++) {
        Brick newBrick = new Brick(brickHeight, brickWidth);
        newBrick.setPosition(j * brickWidth, i * brickHeight);
        bricks.add(newBrick);
      }
    }
  }

  /**
   * Builds a ball for this game
   *
   * @param ballWidth the width of the ball
   * @param ballHeight the height of the ball
   * @param screenWidth the width of the screen
   * @param brickHeight the height of the brick
   * @param numBrickLayers the number of layers of bricks vertically
   * @param ballXVelocity the ball's initial x velocity
   * @param ballYVelocity the ball's initial y velocity
   */
  void createBall(
      int ballWidth,
      int ballHeight,
      int screenWidth,
      int brickHeight,
      int numBrickLayers,
      double ballXVelocity,
      double ballYVelocity) {
    ball = new Ball(ballWidth, ballHeight);
    ball.setPosition(screenWidth / 2, brickHeight * (numBrickLayers + 1));
    ball.setXVelocity(ballXVelocity);
    ball.setYVelocity(ballYVelocity);
  }

  /**
   * Places the items for this brick game into the manager.
   *
   * @param brickGameManager the game manager in which to place the items.
   */
  void placeItems(BrickGameManager brickGameManager) {
    // paddle
    brickGameManager.setPaddle(paddle);
    brickGameManager.setPaddlePosition(paddle);
    brickGameManager.place(paddle);

    // bricks
    brickGameManager.setBricks(bricks);
    for (Brick brick : bricks) {
      brickGameManager.place(brick);
    }

    // ball
    brickGameManager.setBall(ball);
    brickGameManager.place(ball);

    // stars
    brickGameManager.setStars(stars);
  }
}
