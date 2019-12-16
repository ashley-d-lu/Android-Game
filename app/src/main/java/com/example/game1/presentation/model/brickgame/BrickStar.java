package com.example.game1.presentation.model.brickgame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/**
 * Represents a star in the brick minigame
 */
public class BrickStar extends AnimatedGameItem {

  /**
   * Constructs a Star with the specified height and width and appearance.
   *
   * @param height the height of this GameItem
   * @param width the width of this GameItem
   */
  public BrickStar(int width, int height) {

    super(width, height);
  }
}
