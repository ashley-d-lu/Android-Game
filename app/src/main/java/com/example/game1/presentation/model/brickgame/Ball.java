package com.example.game1.presentation.model.brickgame;

import com.example.game1.presentation.model.common.AnimatedGameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/**
 * Represents a ball in the brick minigame
 */
public class Ball extends AnimatedGameItem {


  /**
   * Constructs a Ball with the specified height and width.
   *
   * @param height the height of this GameItem
   * @param width the width of this GameItem
   */
  public Ball(int width, int height) {

    super(width, height);
  }
}
