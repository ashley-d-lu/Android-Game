package com.example.game1.presentation.model.applegame;

import com.example.game1.presentation.model.common.Result;

/** A class for storing the results of the call of an apple GameItem's update method. */
public class AppleResult extends Result {

  /** Indicate if apple is collected */
  private boolean appleCollected = false;
  /** Indicate if apple is dropped */
  private boolean appleDropped = false;
  /** Indicate if star is collected */
  private boolean starCollected = false;

  /**
   * Sets whether an Apple is collected
   *
   * @param appleCollected true if an apple was collected
   */
  void setAppleCollected(boolean appleCollected) {
    this.appleCollected = appleCollected;
  }

  /**
   * Returns whether an apple is collected
   *
   * @return true if apple is collected
   */
  public boolean isAppleCollected() {
    return appleCollected;
  }

  /**
   * Sets whether an Apple is dropped
   *
   * @param appleDropped true if an Apple is dropped
   */
  void setAppleDropped(boolean appleDropped) {
    this.appleDropped = appleDropped;
  }

  /**
   * Returns whether an Apple is dropped
   *
   * @return true if Apple is dropped
   */
  public boolean isAppleDropped() {
    return appleDropped;
  }

  /**
   * Sets whether a Star is collected
   *
   * @param starCollected whether a Star is collected
   */
  void setStarCollected(boolean starCollected) {
    this.starCollected = starCollected;
  }

  /**
   * Returns whether a Star is collected
   *
   * @return true if a Star is collected
   */
  public boolean isStarCollected() {
    return starCollected;
  }
}
