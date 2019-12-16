package com.example.game1.presentation.model.applegame;

import com.example.game1.presentation.model.common.GameItem;

/** A counter for lives remaining. */
public class LivesCounter extends GameItem {

  /** Lives remaining. At the begining there are 10 lives remaining. */
  private int livesRemaining;

  /**
   * Constructs a LivesCounter with the specified number of lives to start.
   *
   * @param startingLives the number of lives to start
   */
  public LivesCounter(int startingLives) {
    super("lives: " + startingLives);
    livesRemaining = startingLives;
  }

  /**
   * Gets livesRemaining.
   *
   * @return number of lives remaining
   */
  public int getLivesRemaining() {
    return livesRemaining;
  }

  /** Decrements liveRemaining by 1. */
  public void subtractLife() {
    livesRemaining -= 1;
    setDescription("lives: " + livesRemaining);
  }
}
