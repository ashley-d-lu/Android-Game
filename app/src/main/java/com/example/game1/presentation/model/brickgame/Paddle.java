package com.example.game1.presentation.model.brickgame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/**
 * Represents a paddle in the brick minigame
 */
public class Paddle extends AnimatedGameItem {

  /**
   * Constructs a paddle with the specified height and width.
   *
   * @param height the height of this GameItem
   * @param width the width of this GameItem
   */
  public Paddle(int height, int width) {
    super(height, width);
  }
}
