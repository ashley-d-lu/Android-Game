package com.example.game1.presentation.presenter.applegame;

import com.example.game1.presentation.model.applegame.Basket;
import com.example.game1.presentation.model.applegame.LivesCounter;
import com.example.game1.presentation.model.applegame.PointsCounter;

/** A Builder for constructing items required for the apple minigame. */
class AppleItemsBuilder {
  /** The Basket. */
  private Basket basket;
  /** The PointsCounter */
  private PointsCounter pointsCounter;
  /** The LivesCounter. */
  private LivesCounter livesCounter;
  /** The width of the Basket. */
  private int basketWidth;
  /** The height of the Basket. */
  private int basketHeight;

  /** Creates a Basket. */
  void createBasket() {
    this.basket = new Basket(basketHeight, basketWidth);
  }

  /** Creates a PointsCounter. */
  void createPointsCounter() {
    this.pointsCounter = new PointsCounter();
  }

  /** Creates a LivesCounter. */
  void createLivesCounter() {
    this.livesCounter = new LivesCounter(10);
  }

  /**
   * Places the items in the specified AppleGameManager.
   *
   * @param appleGameManager the AppleGameManager
   */
  void placeItems(AppleGameManager appleGameManager) {

    // basket
    appleGameManager.setBasket(basket);
    appleGameManager.place(basket);
    basket.setPosition(appleGameManager.getGridWidth() / 2, appleGameManager.getGridHeight() - 300);

    // pointsCounter
    appleGameManager.setPointsCounter(pointsCounter);
    appleGameManager.place(pointsCounter);
    pointsCounter.setPosition(appleGameManager.getGridWidth() - 250, 100);

    // livesCounter
    appleGameManager.setLivesCounter(livesCounter);
    appleGameManager.place(livesCounter);
    livesCounter.setPosition(appleGameManager.getGridWidth() - 250, 150);
  }

  /**
   * @param basketWidth the width of the basket
   * @param basketHeight the height of the basket
   */
  void setBasketSize(int basketWidth, int basketHeight) {
    this.basketWidth = basketWidth;
    this.basketHeight = basketHeight;
  }
}
