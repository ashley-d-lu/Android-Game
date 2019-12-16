package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.MovementInfo;

/** A class for passing information for tapping game items to process update. */
public class TappingMovementInfo extends MovementInfo {
  /** Napping Speed */
  int tappingSpeed;
  /** Number of seconds left */
  int secondsLeft;
  /** Number of taps */
  int numTaps;

  /**
   * Construct TappingMovementInfo to store informaton needed for tapping game item to perform
   * update
   *
   * @param screenHeight height of the screen
   * @param screenWidth width of the screen
   * @param tappingSpeed tapping speed
   * @param secondsLeft number of seconds left
   * @param numTaps number of taps
   * @param numSeconds time period for updating coordinates
   */
  public TappingMovementInfo(
      int screenHeight,
      int screenWidth,
      int tappingSpeed,
      int secondsLeft,
      int numTaps,
      double numSeconds) {
    super(numSeconds, screenHeight, screenWidth);
    this.tappingSpeed = tappingSpeed;
    this.secondsLeft = secondsLeft;
    this.numTaps = numTaps;
  }

  /**
   * Return the tapping speed
   *
   * @return the tapping speed
   */
  public int getTappingSpeed() {
    return tappingSpeed;
  }

  /**
   * Return the number of taps
   *
   * @return
   */
  public int getNumTaps() {
    return numTaps;
  }

  /**
   * Return the number of seconds left
   *
   * @return
   */
  public int getSecondsLeft() {
    return secondsLeft;
  }
}
