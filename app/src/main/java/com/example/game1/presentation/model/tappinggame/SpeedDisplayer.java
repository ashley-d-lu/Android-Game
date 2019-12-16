package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.model.common.Result;

/** A SpeedDisplayer */
public class SpeedDisplayer extends GameItem {
  /** the tapping speed */
  private int tappingSpeed;

  /**
   * Construct a SpeedDisplayer with the specified x and y coordinates
   *
   * @param xCoordinate xCoordinate of this SpeedDisplayer
   * @param yCoordinate yCoordinate of this SpeedDisplayer
   */
  public SpeedDisplayer(double xCoordinate, double yCoordinate) {
    super(xCoordinate, yCoordinate, "Your average tapping speed: ");
    this.tappingSpeed = 0;
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
      // Extract tapping speed from tapping movement info
      this.tappingSpeed = ((TappingMovementInfo) tappingMovementInfo).getTappingSpeed();
      // Update description
      setDescription("Your average tapping speed: " + this.tappingSpeed);
    }
    return (new Result());
  }
}
