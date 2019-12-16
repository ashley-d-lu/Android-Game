package com.example.game1.presentation.model.common;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to store information generated after a GameItem's update method is called. This
 * information is sent to GameManager to be processed.
 */
public class Result {
  /** Items to be added. */
  private List<GameItem> newItems = new ArrayList<>();
  /** Items to be removed. */
  private List<GameItem> oldItems = new ArrayList<>();

  /**
   * Return the List of game items that are to be removed
   *
   * @return the List of game items that are to be removed
   */
  public List<GameItem> getOldItems() {
    return oldItems;
  }

  /**
   * Add a new game item to the oldItems list so that it will be removed later
   *
   * @param item add a new game item to the oldItems list so that it will be removed later
   */
  public void addOldItem(GameItem item) {
    this.oldItems.add(item);
  }
}
