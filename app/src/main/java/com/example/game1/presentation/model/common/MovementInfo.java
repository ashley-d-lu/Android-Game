package com.example.game1.presentation.model.common;

/** A class for storing information needed by GameItems to perform update. */
public class MovementInfo {
  /**
   * The number of seconds used to refresh new xCoordinate, yCoordinate, xVelocity,
   * yVelocity based on current xAcceleration and yAcceleration. Currently,
   * numOfSeconds = * GameThread.FRAME_DURATION_NS / 1000000000.
   */
  private double numSeconds;

  /** the height of the screen */
  private int screenHeight;

  /** the width of the screen */
  private int screenWidth;

  /**
   * Construct the MovementInfo to store information needed for a game item to perform update()
   *
   * @param numSeconds number of seconds
   * @param screenHeight screen height
   * @param screenWidth screen width
   */
  public MovementInfo(double numSeconds, int screenHeight, int screenWidth) {
    this.numSeconds = numSeconds;
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
  }

  /**
   * Return the height of the current screen
   *
   * @return the height of the current screen
   */
  public int getScreenHeight() {
    return screenHeight;
  }

  /**
   * Return the width of the current screen
   *
   * @return the width of the current screen
   */
  public int getScreenWidth() {
    return screenWidth;
  }

  /**
   * Return the number of seconds needed for coordinates update
   *
   * @return the number of seconds needed for coordinates update
   */
  public double getNumSeconds() {
    return numSeconds;
  }
}
