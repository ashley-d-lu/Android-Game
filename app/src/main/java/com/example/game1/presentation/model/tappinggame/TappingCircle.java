package com.example.game1.presentation.model.tappinggame;

import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;
import com.example.game1.presentation.model.common.Result;

/** A tapping circle */
public class TappingCircle extends GameItem {

  /**
   * Construct a tapping circle with the specified x and y coordinates
   *
   * @param xCoordinate xCoordinate of this TappingCircle
   * @param yCoordinate yCoordinate of this TappingCircle
   */
  public TappingCircle(double xCoordinate, double yCoordinate) {
    super(xCoordinate, yCoordinate);
  }
}
