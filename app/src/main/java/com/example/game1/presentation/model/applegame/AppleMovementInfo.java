package com.example.game1.presentation.model.applegame;

import com.example.game1.presentation.model.common.MovementInfo;

/** A class for passing information for use in apple game's items' update methods. */
public class AppleMovementInfo extends MovementInfo {
  /** the basket */
  private Basket basket;

  /**
   * Constructs an AppleMovementInfo.
   *
   * @param screenWidth width of the screen
   * @param screenHeight height of the screen
   * @param basket basket
   * @param numSeconds time period for updating coordinates
   */
  public AppleMovementInfo(int screenWidth, int screenHeight, Basket basket, double numSeconds) {
    super(numSeconds, screenHeight, screenWidth);
    this.basket = basket;
  }

  /**
   * Return the basket
   *
   * @return the basket
   */
  Basket getBasket() {
    return basket;
  }
}
