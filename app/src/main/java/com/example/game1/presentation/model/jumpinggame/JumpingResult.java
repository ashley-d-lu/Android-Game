package com.example.game1.presentation.model.jumpinggame;

import com.example.game1.presentation.model.common.Result;

/** Result after jumping game item perform update */
public class JumpingResult extends Result {

  /** if obstacle is jumped */
  boolean obstacleJumped = false;

  /** if star is collected */
  boolean starCollected = false;

  /** if new star is needed */
  boolean needNewStar = false;

  /** if obstacle is touched */
  boolean obstacleTouched = false;

  /**
   * Return if the obstacle is touched
   *
   * @return if the obstacle is touched
   */
  public boolean isObstacleTouched() {
    return obstacleTouched;
  }

  /**
   * Set if obstacle is touched
   *
   * @param obstacleTouched if obstacle is touched
   */
  public void setObstacleTouched(boolean obstacleTouched) {
    this.obstacleTouched = obstacleTouched;
  }

  /**
   * Return if obstacle is jumped
   *
   * @return if obstacle is jumped
   */
  public boolean isObstacleJumped() {
    return obstacleJumped;
  }

  /**
   * Set if obstacle is jumped
   *
   * @param obstacleJumped if obstacle is jumped
   */
  public void setObstacleJumped(boolean obstacleJumped) {
    this.obstacleJumped = obstacleJumped;
  }

  /**
   * Return if star is collected
   *
   * @return if star is collected
   */
  public boolean isStarCollected() {
    return starCollected;
  }

  /**
   * Set if star is collected
   *
   * @param starCollected if star is collected
   */
  public void setStarCollected(boolean starCollected) {
    this.starCollected = starCollected;
  }

  /**
   * Return if new star is needed
   *
   * @return if new star is needed
   */
  public boolean isNeedNewStar() {
    return needNewStar;
  }

  /**
   * Set if new star is needed
   *
   * @param needNewStar if new star is needed
   */
  public void setNeedNewStar(boolean needNewStar) {
    this.needNewStar = needNewStar;
  }
}
