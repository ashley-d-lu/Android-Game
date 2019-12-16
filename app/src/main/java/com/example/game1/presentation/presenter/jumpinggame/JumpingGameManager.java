package com.example.game1.presentation.presenter.jumpinggame;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.jumpinggame.Jumper;
import com.example.game1.presentation.model.jumpinggame.JumpingMovementInfo;
import com.example.game1.presentation.model.jumpinggame.JumpingResult;
import com.example.game1.presentation.model.jumpinggame.JumpingStar;
import com.example.game1.presentation.model.jumpinggame.Obstacle;
import com.example.game1.presentation.model.jumpinggame.Terrain;
import com.example.game1.presentation.presenter.common.GameManager;
import com.example.game1.presentation.model.common.Result;
import java.util.ArrayList;
import java.util.List;

/** A JumpingGameManager */
public class JumpingGameManager extends GameManager {
  /** The width of jumper */
  private int jumperWidth;
  /** The height of jumper */
  private int jumperHeight;
  /** The width of obstacle */
  private int obstacleWidth;
  /** The height of obstacle */
  private int obstacleHeight;
  /** The width of star in this jumping game */
  private int starWidth;
  /** The height of star in this jumping game */
  private int starHeight;
  /** The terrain */
  private Terrain terrain;
  /** The jumper */
  private Jumper jumper;
  /** the velocity at x axis direction */
  private double cameraVelocityX = 450;
  /** number of obstacle jumped */
  private int numJumped = 0;
  /** number of star collected */
  private int numStars = 0;

  /**
   * Constructs a JumpingGameManager with the specified height, width, game, and activity.
   *
   * @param height the height of the screen
   * @param width the width of the screen
   * @param game the game the jumping manager is for
   * @param activity the activity
   */
  public JumpingGameManager(int height, int width, Game game, AppCompatActivity activity) {
    super(height, width, game, activity);
  }

  /**
   * returns the number of stars collected by the player in this game
   *
   * @return the number of stars collected by the player in this game
   */
  public int getNumStars() {
    return numStars;
  }

  /**
   * sets the number of stars collected by this player
   *
   * @param numStars the new number to set
   */
  public void setNumStars(int numStars) {
    this.numStars = numStars;
  }

  /** Creates GameItems required at the beginning of the minigame. */
  public void createGameItems() {
    // Create terrain
    terrain = new Terrain(getScreenWidth(), getScreenHeight() / 2);
    setTerrainPosition(terrain);
    place(terrain);

    // Create jumper
    jumper = new Jumper(jumperHeight, jumperWidth);
    setJumperPosition(jumper);
    place(jumper);

    // Create obstacles
    Obstacle obstacle1 = new Obstacle(obstacleHeight, obstacleWidth);
    setObstaclePosition(obstacle1, getScreenWidth() * 8 / 5);
    place(obstacle1);
    Obstacle obstacle2 = new Obstacle(obstacleHeight, obstacleWidth);
    setObstaclePosition(obstacle2, getScreenWidth() * 3 / 5);
    place(obstacle2);
    Obstacle obstacle3 = new Obstacle(obstacleHeight, obstacleWidth);
    setObstaclePosition(obstacle3, getScreenWidth() * 6 / 5);
    place(obstacle3);

    // Create star
    JumpingStar jumpingStar = new JumpingStar(getStarWidth(), getStarHeight());
    setStarPosition(jumpingStar, getScreenWidth() * 3 / 5);
    place(jumpingStar);

    setRunning(true);
  }

  /**
   * @param jumpingStar the jumping star
   * @param xCoordinate the position at which to add the jumpingStar
   */
  private void setStarPosition(JumpingStar jumpingStar, int xCoordinate) {
    jumpingStar.setYCoordinate(terrain.getYCoordinate() - 4 * obstacleHeight);
    jumpingStar.setXCoordinate(xCoordinate);
    jumpingStar.setXVelocity(-cameraVelocityX);
  }

  /** updates all items in this game */
  @Override
  public boolean update() {
    // newItems list stores GameItem to be added to gameItems
    List<GameItem> newItems = new ArrayList<>();
    // oldItems list stores GameItem to be removed from gameItems
    List<GameItem> oldItems = new ArrayList<>();
    boolean gameConinue = true;
    // Creates jumping movement info to store all information needed for game items to execute
    // update
    JumpingMovementInfo jumpingMovementInfo =
        new JumpingMovementInfo(
            getScreenHeight(), getScreenWidth(), this.jumper, this.terrain, getNumSeconds());
    // Iterate through the gameItems and execute update for each of the game item
    for (GameItem item : getGameItems()) {
      Result result = item.update(jumpingMovementInfo);
      // Extract items to be removed from result and add them to the oldItems list so that they can
      // be removed later
      if (result.getOldItems() != null) {
        for (GameItem oldItem : result.getOldItems()) {
          oldItems.add(oldItem);
        }
      }
      if (result instanceof JumpingResult) {
        JumpingResult jumpingResult = (JumpingResult) result;
        // Update Statistics data according to the update result
        gameConinue = updateStatistics(jumpingResult);
        // generate new star if needed
        if (jumpingResult.isNeedNewStar()) {
          newItems.add(generateNewStar());
        }
      }
    }
    // process olditems list and new items list to adding or removing game items for this game
    processLists(oldItems, newItems);
    return gameConinue;
  }

  /** Handles the jumper's jump when the screen is tapped */
  public void onTouchEvent() {
    if (jumper.getYVelocity() == 0) {
      jumper.setYVelocity(-2000);
      jumper.setYAcceleration(5000);
    }
  }

  /** Ends this minigame. */
  public void gameOver() {
    setRunning(false);
    // Send statistics
    game.getStatistics().setPoints(numJumped);
    game.getStatistics().setStars(numStars);
    game.getStatistics().setTaps(getNumTaps());
    System.out.println(numJumped + "  " + numStars + "  " + getNumTaps());
    super.gameOver();
  }

  /**
   * Set terrain position
   *
   * @param terrain
   */
  public void setTerrainPosition(Terrain terrain) {
    terrain.setXCoordinate(0);
    terrain.setYCoordinate(getScreenHeight() / 2);
  }

  /**
   * Set obstacle position according to the given xCoordinate
   *
   * @param obstacle a obstacle
   * @param xCoordinate x coordinate
   */
  public void setObstaclePosition(Obstacle obstacle, double xCoordinate) {
    obstacle.setYCoordinate(terrain.getYCoordinate() - obstacle.getHeight());
    obstacle.setXCoordinate(xCoordinate);
    obstacle.setXVelocity(-cameraVelocityX);
  }

  /**
   * Automatically set position for a star
   *
   * @param jumpingStar a jumping star
   */
  public void autoSetStarPosition(JumpingStar jumpingStar) {
    double xCoordinate = getScreenWidth() * 4 / 3 + jumpingStar.getWidth() / 2 - 80 / 2;
    jumpingStar.setXCoordinate(xCoordinate);
    double yCoordinate = terrain.getYCoordinate() - 4 * obstacleHeight;
    jumpingStar.setYCoordinate(yCoordinate);
    jumpingStar.setXVelocity(-cameraVelocityX);
  }

  /**
   * Set position for a jumper
   *
   * @param jumper a jumper
   */
  public void setJumperPosition(Jumper jumper) {
    jumper.setYCoordinate(terrain.getYCoordinate() - jumperHeight);
    jumper.setXVelocity(-cameraVelocityX + cameraVelocityX);
  }

  /**
   * update statistics according to game item's update result
   *
   * @param result
   * @return
   */
  public boolean updateStatistics(JumpingResult result) {
    if (result.isObstacleJumped()) {
      numJumped += 1;
    }
    if (result.isStarCollected()) {
      numStars += 1;
    }
    // call gameOver if a obstacle is touched
    if (result.isObstacleTouched()) {
      gameOver();
      return false;
    }
    return true;
  }

  /** generate a new star and set position for it */
  public JumpingStar generateNewStar() {
    JumpingStar newJumpingStar = new JumpingStar(80, 80);
    autoSetStarPosition(newJumpingStar);
    return newJumpingStar;
  }

  /**
   * Update game items list for this game manager by removing all old items in old lists and adding
   * new items in newItem list
   *
   * @param oldItems item to be removed from this game item list
   * @param newItems items to be added to this game item list
   */
  public void processLists(List<GameItem> oldItems, List<GameItem> newItems) {
    for (GameItem newItem : newItems) {
      place(newItem);
    }
    for (GameItem oldItem : oldItems) {
      removeItem(oldItem);
    }
  }

  /**
   * Get the width of the star
   *
   * @return the width of the star
   */
  public int getStarWidth() {
    return starWidth;
  }

  /**
   * get the height of the star
   *
   * @return the height of the star
   */
  public int getStarHeight() {
    return starHeight;
  }

  /**
   * Set size of the game items
   *
   * @param jumperWidth width of the jumper
   * @param jumperHeight height of the jumper
   * @param obstacleWidth width of the obstacle
   * @param obstacleHeight height of the obstacle
   * @param starWidth width of the star
   * @param starHeight height of the star
   */
  public void setItemSize(
      int jumperWidth,
      int jumperHeight,
      int obstacleWidth,
      int obstacleHeight,
      int starWidth,
      int starHeight) {
    this.jumperWidth = jumperWidth;
    this.jumperHeight = jumperHeight;
    this.obstacleWidth = obstacleWidth;
    this.obstacleHeight = obstacleHeight;
    this.starWidth = starWidth;
    this.starHeight = starHeight;
  }
}
