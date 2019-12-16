package com.example.game1.presentation.model.jumpinggame;

import com.example.game1.presentation.model.common.MovementInfo;

/** A class for passing information for jumping game items to process update. */
public class JumpingMovementInfo extends MovementInfo {
  /** A Jumper */
  private Jumper jumper;
  /** A Terrain */
  private Terrain terrain;

  /**
   * Construct JumpingMovementInfo to store informaton needed for jumping game item to perform
   * update
   *
   * @param screenHeight height of the screen
   * @param screenWidth width of the screen
   * @param jumper jumper
   * @param terrain terrain
   * @param numSeconds time period for updating coordinates
   */
  public JumpingMovementInfo(
      int screenHeight, int screenWidth, Jumper jumper, Terrain terrain, double numSeconds) {
    super(numSeconds, screenHeight, screenWidth);
    this.jumper = jumper;
    this.terrain = terrain;
  }

  /**
   * Get the jumper in this jumping movement info
   *
   * @return the jumper
   */
  public Jumper getJumper() {
    return jumper;
  }

  /**
   * Set the jumper in this jumping movement info
   *
   * @param jumper
   */
  public void setJumper(Jumper jumper) {
    this.jumper = jumper;
  }

  /**
   * Get the terrain in this jumping movement info
   *
   * @return terrain
   */
  public Terrain getTerrain() {
    return terrain;
  }

  /**
   * Set the terrain in this jumping movement info
   *
   * @param terrain
   */
  public void setTerrain(Terrain terrain) {
    this.terrain = terrain;
  }
}
