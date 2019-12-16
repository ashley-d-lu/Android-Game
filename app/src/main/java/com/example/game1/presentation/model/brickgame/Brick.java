package com.example.game1.presentation.model.brickgame;

import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.common.MovementInfo;

/**
 * Represents a brick in the brick minigame
 */
public class Brick extends GameItem {

  private final int STARTING_DAMAGE_LEVEL = 2;
  private final int APPEARANCE_CHANGE_CUTOFF = 1;
  private final int DESTROYED_CUTOFF = 0;

  // how much damage the brick has taken
  private int damageLevel;


  /**
   * Constructs a brick with the specified width and height.
   *
   * @param width the width of this GameItem
   * @param height the height of this GameItem
   */
  public Brick(int width, int height) {
    super(width, height);
    damageLevel = STARTING_DAMAGE_LEVEL;
  }
  /**
   * Causes this brick to take damage.
   *
   * @return True if an only if this brick is now broken
   */
  public boolean damageBrick() {
    damageLevel -= 1;
    return damageLevel == DESTROYED_CUTOFF;
  }

  /**
   * Returns whether the appearance of this brick needs to be changed.
   *
   * @return whether the appearance of this brick needs to be changed.
   */
  public boolean needChangeAppearance() {
    return damageLevel == APPEARANCE_CHANGE_CUTOFF;
  }

  /**
   * Return true iff this brick has passed the cutoff to be considered damaged.
   *
   * @return true iff this brick has passed the cutoff to be considered damaged.
   */
  public boolean isDamaged() {
    return damageLevel <= APPEARANCE_CHANGE_CUTOFF;
  }
}
