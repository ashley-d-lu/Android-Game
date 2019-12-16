package com.example.game1.presentation.model.jumpinggame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/** A Star used in jumping game */
public class JumpingStar extends AnimatedGameItem {

  /**
   * Constructs a JumpingStar with the specified height, width and appearance.
   *
   * @param height the height of this JumpingStar
   * @param width the width of this JumpingStar
   */
  public JumpingStar(int height, int width) {

    super(height, width);
  }

  @Override
  /**
   * Perform update based on the information given by the movement info
   *
   * @param jumpingMovementInfo
   * @return result needed by the jumping game manager.
   */
  public JumpingResult update(MovementInfo jumpingMovementInfo) {
    // Update x and y coordinates of this apple based on specified time period
    updatePositionAndVelocity(jumpingMovementInfo.getNumSeconds());
    JumpingResult result = new JumpingResult();

    if (jumpingMovementInfo instanceof JumpingMovementInfo) {
      // get the starCollector from jumpingMovementInfo. In this game, it's the jumper
      GameItem starCollector = ((JumpingMovementInfo) jumpingMovementInfo).getJumper();
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
