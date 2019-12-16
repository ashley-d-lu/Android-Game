package com.example.game1.presentation.model.jumpinggame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/** An obstacle */
public class Obstacle extends AnimatedGameItem {

  /**
   * Constructs a Obstacle with the specified height and width.
   *
   * @param height the height of this Obstacle
   * @param width the width of this Obstacle
   */
  public Obstacle(int height, int width) {
    super(height, width);
  }

  @Override
  /**
   * Perform update based on the information given by the movement info
   *
   * @param jumpingMovementInfo information needed for the obstacle to execute update
   * @return update result information
   */
  public JumpingResult update(MovementInfo jumpingMovementInfo) {
    // Update x and y coordinates of this apple based on specified time period
    updatePositionAndVelocity(jumpingMovementInfo.getNumSeconds());

    JumpingResult jumpingResult = new JumpingResult();
    if (jumpingMovementInfo instanceof JumpingMovementInfo) {
      Jumper jumper = ((JumpingMovementInfo) jumpingMovementInfo).getJumper();
      // If jumper touches the obstacle, set obstacleTouched to be true in the jumping result
      if (this.isOverlapping(jumper)) {
        jumpingResult.setObstacleTouched(true);

        // reset obstacle's xCoordinate if it is out of the screen
      } else if (this.getXCoordinate() + this.getWidth() < 0) {
        this.setXCoordinate((jumpingMovementInfo).getScreenWidth() * 4 / 3);
        jumpingResult.setObstacleJumped(true);

        // randomly add new star
        if (Math.random() > 0.7) {
          jumpingResult.setNeedNewStar(true);
        }
      }
    }
    return jumpingResult;
  }
}
