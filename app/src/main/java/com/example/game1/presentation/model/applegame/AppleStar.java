package com.example.game1.presentation.model.applegame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/** A Star that is used in the apple game. */
public class AppleStar extends AnimatedGameItem {

  /**
   * Constructs an AppleStar with the specified width and height.
   *
   * @param height the height of this AppleStar
   * @param width the width of this AppleStar
   */
  public AppleStar(int height, int width) {

    super(height, width);
  }

  /**
   * Updates this AppleStar based on the information given by the specified appleMovementInfo.
   *
   * @param appleMovementInfo the AppleMovementInfo
   * @return the result needed by the apple game manager
   */
  @Override
  public AppleResult update(MovementInfo appleMovementInfo) {
    // Update x and y coordinates of this apple based on specified time period
    updatePositionAndVelocity(((appleMovementInfo).getNumSeconds()));
    AppleResult result = new AppleResult();

    if (appleMovementInfo instanceof AppleMovementInfo) {
      // get the starCollector from appleMovementInfo. In this game, it's the basket
      GameItem starCollector = ((AppleMovementInfo) appleMovementInfo).getBasket();
      // If this star is collected by the star collector, update the result and put this star to the
      // list that will be removed from gameItems in this game later.
      if (this.isOverlapping(starCollector)) {
        result.setStarCollected(true);
        result.addOldItem(this);
      }
    }
    return result;
  }
}
