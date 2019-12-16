package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.model.common.Result;

/**
 * A TapCounter
 */
public class TapCounter extends GameItem {
  /**
   * Number of taps
   */
  private int numTaps;

  /**
   * Construct a Tap Counter with the specified x and y coordinates
   * @param xCoordinate xCoordinate of this TapCounter
   * @param yCoordinate yCoordinate of this TapCounter
   */
  public TapCounter(double xCoordinate, double yCoordinate) {
    super(xCoordinate, yCoordinate, "Number of Taps: ");
    this.numTaps = 0;
  }

  /**
   * Get number of Taps in this TapCounter
   * @return number of Taps in this TapCounter
   */
  public int getNumTaps() {
    return numTaps;
  }


  @Override
  /**
   * Perform update based on the information given by the movement info
   *
   * @param tappingMovementInfo
   * @return result needed by the tapping game manager.
   */
  public Result update(MovementInfo tappingMovementInfo) {
    if (tappingMovementInfo instanceof TappingMovementInfo) {
      // Extract number of taps from tapping
      this.numTaps = ((TappingMovementInfo) tappingMovementInfo).getNumTaps();
      // Update description
      setDescription("Number of Taps: " + this.numTaps);
    }
    return new Result();
  }

}
