package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.model.common.Result;

/** A Time Displayer */
public class TimerDisplayer extends GameItem {
  /** Number of seconds left */
  private int secondsLeft;

  /**
   * Construct a Time Displayer according to specified xCoordinate, yCoordinate
   *
   * @param xCoordinate xCoordinate of this TimeDisplayer
   * @param yCoordinate yCoordinate of this TimeDisplayer
   */
  public TimerDisplayer(double xCoordinate, double yCoordinate) {
    super(xCoordinate, yCoordinate, "Your seconds left: 10");
    // set initial value to 10 seconds
    this.secondsLeft = 10;
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
      // Extract seconds Left from tapping movement info and update the secodnsLeft and description
      this.secondsLeft = ((TappingMovementInfo) tappingMovementInfo).getSecondsLeft();
      setDescription("Your seconds left: " + this.secondsLeft);
    }
    return new Result();
  }
}
