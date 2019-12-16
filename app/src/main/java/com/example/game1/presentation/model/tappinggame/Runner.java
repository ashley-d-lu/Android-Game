package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.model.common.Result;

/** Runner of the tapping game */
public class Runner extends AnimatedGameItem {
  /**
   * Constructs a Jumper with the specified x and y coordinates and its height, width.
   *
   * @param xCoordinate xCoordinate of this Runner
   * @param yCoordinate yCoordinate of this Runner
   * @param width the width of this Runner
   * @param height the height of this Runner
   */
  public Runner(double xCoordinate, double yCoordinate, int width, int height) {
    super(xCoordinate, yCoordinate, width, height);
  }

  @Override
  /**
   * Perform update based on the information given by the movement info
   *
   * @param tappingMovementInfo
   * @return result needed by the tapping game manager.
   */
  public Result update(MovementInfo tappingMovementInfo) {
    Result result = new Result();
    // Perform check
    if (tappingMovementInfo instanceof TappingMovementInfo) {
      // Get tapping Speed from tappingMovementInfo
      setXVelocity(((TappingMovementInfo) tappingMovementInfo).getTappingSpeed());
      // If runner is still in the screen, update it's velocity at x axis direction and its
      // xCoordinate
      if ((getXCoordinate() + getXVelocity())
          < (tappingMovementInfo.getScreenWidth() - getWidth())) {
        double newXCoordinate = getXCoordinate() + getXVelocity();
        setXCoordinate(newXCoordinate);
      }
    }
    return result;
  }
}
