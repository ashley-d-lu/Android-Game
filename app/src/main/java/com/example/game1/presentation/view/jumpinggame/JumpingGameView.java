package com.example.game1.presentation.view.jumpinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.jumpinggame.Jumper;
import com.example.game1.presentation.presenter.jumpinggame.JumpingGameManager;
import com.example.game1.presentation.view.common.GameThread;
import com.example.game1.presentation.view.common.GameView;

import java.util.ArrayList;
import java.util.List;

/** The view of the jumping game presented to the user. */
public class JumpingGameView extends GameView implements View.OnClickListener {
  /** Dark background color */
  private final int BACKGROUND_COLOR_DARK = Color.rgb(83, 92, 104);
  /** Light background color */
  private final int BACKGROUND_COLOR_LIGHT = Color.rgb(223, 249, 251);
  /** The width of the jumper */
  private final int JUMPER_WIDTH = 100;
  /** The height of the jumper */
  private final int JUMPER_HEIGHT = 200;
  /** The width of the obstacle */
  private final int OBSTACLE_WIDTH = 100;
  /** The height of the obstacle */
  private final int OBSTACLE_HEIGHT = 100;
  /** The width of the star */
  private final int STAR_WIDTH = 80;
  /** The height of the star */
  private final int STAR_HEIGHT = 80;
  /** A OnClickListener used to catch the number of clicks */
  private OnClickListener listener;
  /** A list of bitmap files for obstacle */
  private List<Bitmap> obstacleBmps;
  /** A list of bitmap files for jumping star */
  private List<Bitmap> starBmps;
  /** A list of bitmap files for terrain */
  private List<Bitmap> terrainBmps;
  /** A list of bitmap files for jumper animation for blue color scheme */
  private List<Bitmap> jumperBlueBmps;
  /** A list of bitmap files for jumper animation for red color scheme */
  private List<Bitmap> jumperRedBmps;
  /** A list of bitmap files for jumper animation for yellow color scheme */
  private List<Bitmap> jumperYellowBmps;
  /**
   * the index of the current bitmap file in the bitmap file list. This index is used to iterate
   * throught the bitmap file list and extract the bitmap to generate animation
   */
  private int currentFrameJumper = 0;

  // image files for game objects
  /** Terrain bitmap */
  private final int[] TERRAIN_FILES = {R.drawable.grass};
  /** Obstacle bitmap */
  private final int[] OBSTACLE_FILES = {R.drawable.wooden_blocks_1};
  /** Star bitmap */
  private final int[] STAR_FILES = {R.drawable.star_6};
  /** Jumper bitmaps for blue scheme */
  private final int[] JUMPER_BLUE_FILES = {
          R.drawable.jumper_blue_1,
          R.drawable.jumper_blue_2,
          R.drawable.jumper_blue_3,
          R.drawable.jumper_blue_4,
          R.drawable.jumper_blue_5,
          R.drawable.jumper_blue_6,
          R.drawable.jumper_blue_7,
          R.drawable.jumper_blue_8,
          R.drawable.jumper_blue_9,
          R.drawable.jumper_blue_10
  };
  /** Jumper bitmaps for red scheme */
  private final int[] JUMPER_RED_FILES = {
          R.drawable.jumper_red_1,
          R.drawable.jumper_red_2,
          R.drawable.jumper_red_3,
          R.drawable.jumper_red_4,
          R.drawable.jumper_red_5,
          R.drawable.jumper_red_6,
          R.drawable.jumper_red_7,
          R.drawable.jumper_red_8,
          R.drawable.jumper_red_9,
          R.drawable.jumper_red_10
  };
  /** Jumper bitmaps for yellow scheme */
  private final int[] JUMPER_YELLOW_FILES = {
          R.drawable.jumper_yellow_1,
          R.drawable.jumper_yellow_2,
          R.drawable.jumper_yellow_3,
          R.drawable.jumper_yellow_4,
          R.drawable.jumper_yellow_5,
          R.drawable.jumper_yellow_6,
          R.drawable.jumper_yellow_7,
          R.drawable.jumper_yellow_8
  };

  /**
   * creates a new JumpingView *
   *
   * @param context the context in which to create the view
   */
  public JumpingGameView(Context context) {
    super(context);
    getHolder().addCallback(this);
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
    // set up onClickListener to catch the number of clicks
    this.listener =
            new OnClickListener() {
              @Override
              /** increment number of click by 1 when there is a click */
              public void onClick(View v) {
                gameManager.incrementNumTaps();
              }
            };
  }

  /**
   * Initialize the game view when a surface is created
   *
   * @param holder the surface holder holding the view
   */
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    // use screen height and width and activity to generate the game manager the size of the
    // GameManager
    gameManager =
            AppManager.getInstance()
                    .buildGameManager(
                            Game.GameName.JUMPING,
                            (int) (getScreenHeight() / charHeight),
                            (int) (getScreenWidth() / charWidth),
                            activity);
    gameManager.setScreenHeight(this.getScreenHeight());
    gameManager.setScreenWidth(this.getScreenWidth());
    gameManager.setNumSeconds(GameThread.FRAME_DURATION_NS / 1000000000.);
    // set up size of the game item in the game manager
    if (gameManager instanceof JumpingGameManager) {
      ((JumpingGameManager) gameManager)
              .setItemSize(
                      JUMPER_WIDTH,
                      JUMPER_HEIGHT,
                      OBSTACLE_WIDTH,
                      OBSTACLE_HEIGHT,
                      STAR_WIDTH,
                      STAR_HEIGHT);
    }
    // extract bitmap files and added them to a hashmap
    extractBmpFiles();
    // generate character color according to customizatipon
    generateCharacterColor();
    // set Dark background color
    setBackgroundColorDark(BACKGROUND_COLOR_DARK);
    // set light background color
    setBackgroundColorLight(BACKGROUND_COLOR_LIGHT);
    // generate back ground color according to customization
    generateBackgroundColor();
    // create game items
    gameManager.createGameItems();
    // start music
    gameManager.startMusic();
    // set up thread
    thread.setRunning(true);
    thread.start();
    // Setup OnClickListener
    this.setOnClickListener(this.listener);
  }

  /**
   * Handles the jumper's jump when the user taps the screen
   *
   * @param event the event for the screen tap
   * @return true or false depending on the implementation of the superclass
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    ((JumpingGameManager) gameManager).onTouchEvent();
    return super.onTouchEvent(event);
  }

  @Override
  /**
   * Increment number of click by 1 when there is a click
   *
   * @param View a View
   */
  public void onClick(View v) {
    gameManager.incrementNumTaps();
  }

  @Override
  /**
   * Draw this GameItem.
   *
   * @param canvas the canvas on which to draw this item.
   */
  public void drawItem(Canvas canvas, GameItem item) {
    setupPaintText();
    Bitmap appearance;
    // key used to extract bitmaps from the hash map
    String key;
    // get coordinates of this game item
    double xCoordinate = item.getXCoordinate();
    double yCoordinate = item.getYCoordinate();
    // If the game item is a jumper
    if (item instanceof Jumper) {
      // Generate key according to class name and charactor color scheme according to the
      // customization
      key = "Jumper" + getCharacterColorScheme();
      // Extract the bitmap from hashmap
      appearance = getCurrentAppearance(key);
    } else {
      // If the game item is not a jumper,
      // set key to the class name and extract the bitmap from hash map
      key = item.getClass().getSimpleName();
      appearance = getAppearance(key);
    }
    // Draw bitmap
    canvas.drawBitmap(
            appearance, (int) Math.round(xCoordinate), (int) Math.round(yCoordinate), paintText);
  }

  /** Extract Bitmap Files and add them to hash map */
  public void extractBmpFiles() {
    // terrainBmp = getNewBitmap(R.drawable.grass, getScreenWidth(), getScreenHeight() / 2);
    terrainBmps = new ArrayList<>();
    obstacleBmps = new ArrayList<>();
    starBmps = new ArrayList<>();
    // @TODO don't need to load everything - only the customization that was chosen
    jumperBlueBmps = new ArrayList<>();
    jumperRedBmps = new ArrayList<>();
    jumperYellowBmps = new ArrayList<>();
    JumpingGameManager jumpingGameManager = (JumpingGameManager) gameManager;
    generateAnimatedBmps(terrainBmps, TERRAIN_FILES, getScreenWidth(), getScreenHeight() / 2);
    generateAnimatedBmps(obstacleBmps, OBSTACLE_FILES, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
    generateAnimatedBmps(
            starBmps,
            STAR_FILES,
            jumpingGameManager.getStarWidth(),
            jumpingGameManager.getStarHeight());
    generateAnimatedBmps(jumperBlueBmps, JUMPER_BLUE_FILES, JUMPER_WIDTH, JUMPER_HEIGHT);
    generateAnimatedBmps(jumperRedBmps, JUMPER_RED_FILES, JUMPER_WIDTH, JUMPER_HEIGHT);
    generateAnimatedBmps(jumperYellowBmps, JUMPER_YELLOW_FILES, JUMPER_WIDTH, JUMPER_HEIGHT);
    // Add bitmaps to hashmap
    addGameItemAppearances("JumperYellow", jumperYellowBmps);
    addGameItemAppearances("JumperBlue", jumperBlueBmps);
    addGameItemAppearances("JumperRed", jumperRedBmps);
    addGameItemAppearances("Obstacle", obstacleBmps);
    addGameItemAppearances("JumpingStar", starBmps);
    addGameItemAppearances("Terrain", terrainBmps);
  }

  /**
   * Extract bitmap for game item with animation from the has map.
   *
   * @param key key used to extract bitmap
   * @return
   */
  public Bitmap getCurrentAppearance(String key) {
    List<Bitmap> appearances = getAppearances(key);
    Bitmap appearance = appearances.get(currentFrameJumper);
    // helper method to extract correct index number for the bitmap file stored in the list of
    // bitmap
    currentFrameJumper = updateIndex(currentFrameJumper, appearances.size());
    return appearance;
  }
}
