package com.example.game1.presentation.model.jumpinggame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/** A jumper. */
public class Jumper extends AnimatedGameItem {

  /**
   * Constructs a Jumper with the specified width and height.
   *
   * @param height the height f this Jumper
   * @param width the width of this Jumper
   */
  public Jumper(int height, int width) {
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
    JumpingResult jumpingResult = new JumpingResult();

    if (jumpingMovementInfo instanceof JumpingMovementInfo) {
      // Get terrain from the movement info
      Terrain terrain = ((JumpingMovementInfo) jumpingMovementInfo).getTerrain();
      // Jumper landed on the terrain
      if (this.isOverlapping(terrain)) {
        this.setYCoordinate(terrain.getYCoordinate() - this.getHeight());
        this.setYVelocity(0);
        this.setYAcceleration(0);
      }
    }
    return jumpingResult;
  }
}
