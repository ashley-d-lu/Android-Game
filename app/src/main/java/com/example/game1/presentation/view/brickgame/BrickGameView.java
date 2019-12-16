package com.example.game1.presentation.view.brickgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.brickgame.Ball;
import com.example.game1.presentation.model.brickgame.Brick;
import com.example.game1.presentation.model.brickgame.Paddle;
import com.example.game1.presentation.model.brickgame.BrickStar;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.presenter.brickgame.BrickGameManager;
import com.example.game1.presentation.presenter.jumpinggame.JumpingGameManager;
import com.example.game1.presentation.view.common.GameThread;
import com.example.game1.presentation.view.common.GameView;

import java.util.ArrayList;
import java.util.List;

/** The view of the brick minigame presented to the user. */
public class BrickGameView extends GameView implements View.OnClickListener {
  private GameThread thread;
  private OnClickListener listener;
  private int numTaps = 0;
  private List<Bitmap> ballBmps;
  private List<Bitmap> starBmps;
  private Bitmap brickBmp;
  private Bitmap brickDamagedBmp;
  private List<Bitmap> paddleBlueBmps;
  private List<Bitmap> paddleRedBmps;
  private List<Bitmap> paddleYellowBmps;

  // background colours
  private final int BACKGROUND_COLOR_DARK = Color.rgb(83, 92, 104);
  private final int BACKGROUND_COLOR_LIGHT = Color.rgb(223, 249, 251);

  // images for game objects
  private final int[] BALL_FILES = {R.drawable.ball_blue};
  private final int[] STAR_FILES = {R.drawable.star_6};
  private final int[] PADDLE_BLUE_FILES = {R.drawable.paddle_blue};
  private final int[] PADDLE_RED_FILES = {R.drawable.paddle_red};
  private final int[] PADDLE_YELLOW_FILES = {R.drawable.paddle_yellow};

  // keys for Map from item string to it's bitmap image
  private final String BALL_KEY = "ball";
  private final String STAR_KEY = "star";
  private final String BRICK_KEY = "brick";
  private final String BRICK_DAMAGED_KEY = "brick_damaged";
  private final String PADDLE_RED_KEY = "paddle_red";
  private final String PADDLE_BLUE_KEY = "paddle_red";
  private final String PADDLE_YELLOW_KEY = "paddle_red";
  /**
   * creates a new BrickView *
   *
   * @param context the context in which to create the view
   */
  public BrickGameView(Context context) {
    super(context);

    getHolder().addCallback(this);
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
    this.listener =
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (true) {
              numTaps++;
              gameManager.setNumTaps(numTaps);
            }
          }
        };
  }

  /**
   * initialiezes the game view when a surface is created
   *
   * @param holder the surface holder holding the view
   */
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    gameManager =
        AppManager.getInstance()
            .buildGameManager(
                Game.GameName.BRICK,
                (int) (getScreenHeight() / charHeight),
                (int) (getScreenWidth() / charWidth),
                activity);
    gameManager.setScreenHeight(this.getScreenHeight());
    gameManager.setScreenWidth(this.getScreenWidth());
    gameManager.setNumSeconds(GameThread.FRAME_DURATION_NS / 1000000000.);

    extractBmpFiles();

    ((BrickGameManager) gameManager).setBmpfiles(paddleBlueBmps, paddleRedBmps, paddleYellowBmps);
    setBackgroundColorDark(BACKGROUND_COLOR_DARK);
    setBackgroundColorLight(BACKGROUND_COLOR_LIGHT);

    generateBackgroundColor();

    gameManager.createGameItems();
    gameManager.startMusic();

    thread.setRunning(true);
    thread.start();

    this.setOnClickListener(this.listener);
  }

    /**
     * Draws all of the items in this brick game
     * @param canvas
     */
  @Override
  public void drawItems(Canvas canvas) {
    List<GameItem> items = gameManager.getGameItems();
    for (GameItem item : items) {
      drawItem(canvas, item, getAppearance(item));
    }
  }

  /**
   * draws this game view on the canvas
   *
   * @param canvas the canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
  }

  /**
   * Returns the bitmap image for the appearance of the given game item
   *
   * @param item the item whose bitmap image appearance is desired
   * @return the bitmap image for the appearance of the given game item
   */
  private Bitmap getAppearance(GameItem item) {
    String key = null;
    if (item instanceof Paddle) {
      Customization cust = gameManager.getGame().getCustomization();
      if (cust.getCharacterColour().equals(Customization.CharacterColour.BLUE)) {
        key = PADDLE_BLUE_KEY;
      } else if (cust.getCharacterColour().equals(Customization.CharacterColour.RED)) {
        key = PADDLE_RED_KEY;
      } else if (cust.getCharacterColour().equals(Customization.CharacterColour.YELLOW)) {
        key = PADDLE_YELLOW_KEY;
      }
    } else if (item instanceof Ball) {
      key = BALL_KEY;
    } else if (item instanceof Brick) {
      if (((Brick) item).isDamaged()) {
        key = BRICK_DAMAGED_KEY;
      } else {
        key = BRICK_KEY;
      }
    } else if (item instanceof BrickStar) {
      key = STAR_KEY;
    }
    if (key == null) {
      return null;
    }
    return getAppearance(key);
  }
  /**
   * Causes the paddle to move based on the user's touch
   *
   * @param event the event for the screen tap
   * @return true or false depending on the implementation of the superclass - irrelevant to this
   *     method
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    ((BrickGameManager) gameManager).onTouchEvent(event.getX());
    return super.onTouchEvent(event);
  }

  /**
   * Increments the number of screen taps each time the screen is tapped,
   *
   * @param v the view
   */
  @Override
  public void onClick(View v) {
    if (true) {
      numTaps++;
      System.out.println(numTaps);
    }
  }

  /** Sets up the map of game items to bitmap images for game items */
  public void extractBmpFiles() {
    BrickGameManager brickGameManager = (BrickGameManager) gameManager;
    brickBmp =
        getNewBitmap(
            R.drawable.brick_blue,
            getScreenWidth() / brickGameManager.getNumBricksHorizontal(),
            brickGameManager.getBrickHeight());
    brickDamagedBmp =
        getNewBitmap(
            R.drawable.brick_blue_damaged,
            getScreenWidth() / brickGameManager.getNumBricksHorizontal(),
            brickGameManager.getBrickHeight());

    ballBmps = new ArrayList<>();
    starBmps = new ArrayList<>();
    paddleBlueBmps = new ArrayList<>();
    paddleRedBmps = new ArrayList<>();
    paddleYellowBmps = new ArrayList<>();

    generateAnimatedBmps(
        ballBmps, BALL_FILES, brickGameManager.getBallWidth(), brickGameManager.getBallHeight());
    generateAnimatedBmps(
        starBmps, STAR_FILES, brickGameManager.getStarWidth(), brickGameManager.getStarHeight());
    generateAnimatedBmps(
        paddleBlueBmps,
        PADDLE_BLUE_FILES,
        brickGameManager.getPaddleWidth(),
        brickGameManager.getPaddleHeight());
    generateAnimatedBmps(
        paddleRedBmps,
        PADDLE_RED_FILES,
        brickGameManager.getPaddleWidth(),
        brickGameManager.getPaddleHeight());
    generateAnimatedBmps(
        paddleYellowBmps,
        PADDLE_YELLOW_FILES,
        brickGameManager.getPaddleWidth(),
        brickGameManager.getPaddleHeight());

    addGameItemAppearance(BRICK_KEY, brickBmp);
    addGameItemAppearance(BRICK_DAMAGED_KEY, brickDamagedBmp);
    addGameItemAppearances(BALL_KEY, ballBmps);
    addGameItemAppearances(PADDLE_BLUE_KEY, paddleBlueBmps);
    addGameItemAppearances(PADDLE_RED_KEY, paddleRedBmps);
    addGameItemAppearances(PADDLE_YELLOW_KEY, paddleYellowBmps);
    addGameItemAppearances(STAR_KEY, starBmps);
  }
}
