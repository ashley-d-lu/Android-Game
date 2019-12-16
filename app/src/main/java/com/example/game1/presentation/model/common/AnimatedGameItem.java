package com.example.game1.presentation.model.common;

/** An animated game item which can be in a GameManager. */
public abstract class AnimatedGameItem extends GameItem {
  /** This item's velocity for x coordinate. */
  private double xVelocity;
  /** This item's velocity for y coordinate. */
  private double yVelocity;
  /** This item's acceleration for x coordinate. */
  private double xAcceleration;
  /** This item's acceleration for y coordinate. */
  private double yAcceleration;

  /**
   * Constructs a AnimatedGameItem with the specified height and width.
   *
   * @param description the appearance of this AnimatedGameItem
   */
  protected AnimatedGameItem(String description) {
    super(description);
  }

  /**
   * Constructs a AnimatedGameItem with the specified coordinates.
   *
   * @param xCoordinate xCoordinate of this AnimatedGameItem
   * @param yCoordinate yCoordinate of this AnimatedGameItem
   */
  protected AnimatedGameItem(double xCoordinate, double yCoordinate) {
    super(xCoordinate, yCoordinate);
  }

  /**
   * Construct an animated game item with the specified coordinates and description
   *
   * @param xCoordinate xCoordinate of this AnimatedGameItem
   * @param yCoordinate yCoordinate of this AnimatedGameItem
   * @param description Description of this AnimatedGameItem
   */
  public AnimatedGameItem(double xCoordinate, double yCoordinate, String description) {
    super(xCoordinate, yCoordinate, description);
  }

  /**
   * Constructs a AnimatedGameItem with the specified coordinates, width and height.
   *
   * @param xCoordinate xCoordinate of this AnimatedGameItem
   * @param yCoordinate yCoordinate of this AnimatedGameItem
   * @param width the width of this AnimatedGameItem
   * @param height the height of this AnimatedGameItem
   */
  protected AnimatedGameItem(double xCoordinate, double yCoordinate, int width, int height) {
    super(xCoordinate, yCoordinate, width, height);
  }

  /**
   * Constructs a AnimatedGameItem with the specified height and width.
   *
   * @param height the height of this AnimatedGameItem
   * @param width the width of this AnimatedGameItem
   */
  protected AnimatedGameItem(int height, int width) {
    super(height, width);
  }

  /**
   * Constructs a AnimatedGameItem with the specified height, width, description.
   *
   * @param height the height of this AnimatedGameItem
   * @param width the width of this AnimatedGameItem
   * @param description the description of this AnimatedGameItem
   */
  public AnimatedGameItem(int height, int width, String description) {
    super(height, width, description);
  }

  /**
   * Set the velocity at x axis direction
   *
   * @param xVelocity
   */
  public void setXVelocity(double xVelocity) {

    this.xVelocity = xVelocity;
  }

  /**
   * Return velocity of this GameItem at x axis direction
   *
   * @return
   */
  public double getXVelocity() {
    return this.xVelocity;
  }

  /**
   * Set velocity at y axis direction
   *
   * @param yVelocity
   */
  public void setYVelocity(double yVelocity) {
    this.yVelocity = yVelocity;
  }

  /**
   * Return velocity of this GameItem at y axis direction
   *
   * @return
   */
  public double getYVelocity() {
    return yVelocity;
  }

  /**
   * Set acceleration at x axis direction
   *
   * @param xAcceleration
   */
  public void setXAcceleration(double xAcceleration) {
    this.xAcceleration = xAcceleration;
  }

  /**
   * Return acceleration of this GameItem at x axis direction
   *
   * @return
   */
  public double getXAcceleration() {
    return xAcceleration;
  }

  /**
   * Set acceleration at y axis direction
   *
   * @param yAcceleration
   */
  public void setYAcceleration(double yAcceleration) {
    this.yAcceleration = yAcceleration;
  }

  /**
   * Return acceleration of this GameItem at y axis direction
   *
   * @return
   */
  public double getYAcceleration() {
    return yAcceleration;
  }

  /**
   * Update the Coordinates and Velocity at x and y direction based on specified time period
   *
   * @param numOfSeconds number of seconds used to refresh new xCoordinate, yCoordinate, xVelocity,
   *     yVelocity based on current xAcceleration and yAcceleration. Currently numOfSeconds =
   *     GameThread.FRAME_DURATION_NS / 1000000000.
   */
  public void updatePositionAndVelocity(double numOfSeconds) {
    // Calculate new x coordinates and y coordinates
    double newXCoordinate =
        getXCoordinate()
            + (0.5 * getXAcceleration() * numOfSeconds * numOfSeconds
                + getXVelocity() * numOfSeconds);
    setXCoordinate(newXCoordinate);

    double newYCoordinate =
        getYCoordinate()
            + (0.5 * getYAcceleration() * numOfSeconds * numOfSeconds
                + getYVelocity() * numOfSeconds);
    setYCoordinate(newYCoordinate);

    // Calculate new x velocity and y velocity
    double newXVelocity = getXVelocity() + getXAcceleration() * numOfSeconds;
    setXVelocity(newXVelocity);

    double newYVelocity = getYVelocity() + getYAcceleration() * numOfSeconds;
    setYVelocity(newYVelocity);
  }

  @Override
  /**
   * A default method for animated game item to update its coordinates based on specified time
   * period given in the movement info
   */
  public Result update(MovementInfo movementInfo) {
    updatePositionAndVelocity(movementInfo.getNumSeconds());
    return (new Result());
  }
}
