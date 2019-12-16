package com.example.game1.presentation.view.tappinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.model.Game;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.model.tappinggame.Runner;
import com.example.game1.presentation.model.tappinggame.TappingCircle;
import com.example.game1.presentation.presenter.tappinggame.TappingGameManager;
import com.example.game1.presentation.view.common.GameThread;
import com.example.game1.presentation.view.common.GameView;

import java.util.ArrayList;
import java.util.List;

/** A Tapping Game View */
public class TappingGameView extends GameView implements View.OnClickListener {
  /** Dark background color */
  private final int BACKGROUND_COLOR_DARK = Color.BLACK;
  /** Light background color */
  private final int BACKGROUND_COLOR_LIGHT = Color.GRAY;
  /** A multiplier used to generate width of the runner */
  private final double RUNNER_WIDTH_MULTIPLIER = 0.2;
  /** A multiplier used to generate height of the runner */
  private final double RUNNER_HEIGHT_MULTIPLIER = 0.2;
  /**
   * Number of taps used for tapping game. it is set in the tapping game view for better performance
   * purpose as tapping game needs process tap quickly
   */
  protected int numOfTaps;
  /** Number of stars */
  protected int numStars;
  /** If the game starts */
  protected boolean gameStarted;
  /** Number of seconds left */
  protected int secondLeft;
  /** The tapping speed */
  protected int speed;
  /** Bitmap file for tapping circle */
  private Bitmap tappingCircleBmp;
  /** A list of bitmap files for runner with yellow color scheme */
  private List<Bitmap> yellowPugs;
  /** A list of bitmap files for runner with blue color scheme */
  private List<Bitmap> blueBirds;
  /** A list of bitmap files for runner with red color scheme */
  private List<Bitmap> redFishes;
  /** A OnClickListener that catch the number of clicks */
  private OnClickListener listener;
  /** A CountDown timer */
  private CountDownTimer myTimer;
  /**
   * the index of the current bitmap file in the bitmap file list. This index is used to iterate
   * throught the bitmap file list and extract the bitmap to generate animation
   */
  private int currentFrameRunner = 0;

  // images used for game objects
  /** Runner bitmaps for yellow scheme */
  private int[] YELLOW_PUG_FILES = {
          R.drawable.dog_yellow_1,
          R.drawable.dog_yellow_2,
          R.drawable.dog_yellow_3,
          R.drawable.dog_yellow_4,
          R.drawable.dog_yellow_5,
          R.drawable.dog_yellow_6,
          R.drawable.dog_yellow_7,
          R.drawable.dog_yellow_8
  };
  /** Runner bitmaps for blue scheme */
  private int[] BLUE_BIRD_FILES = {
          R.drawable.bird_blue_1, R.drawable.bird_blue_2, R.drawable.bird_blue_3, R.drawable.bird_blue_4
  };
  /** Runner bitmaps for red scheme */
  private int[] RED_FISHES_FILES = {
          R.drawable.fish_red_1,
          R.drawable.fish_red_2,
          R.drawable.fish_red_3,
          R.drawable.fish_red_4,
          R.drawable.fish_red_5,
          R.drawable.fish_red_6
  };

  /**
   * Create a new Tapping Game View in the context environment.
   *
   * @param context the environment.
   */
  public TappingGameView(Context context) {
    super(context);
    getHolder().addCallback(this);
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
    numOfTaps = 0;
    numStars = 0;
    gameStarted = false;
    secondLeft = 10;
    speed = 0;
    // set up onClickListener to catch the number of clicks
    this.listener =
            new OnClickListener() {
              @Override
              public void onClick(View v) {
                if (gameStarted) {
                  numOfTaps++;
                  gameManager.setNumTaps(numOfTaps);
                }
              }
            };
  }

  @Override
  /**
   * Initialize the game view when a surface is created
   *
   * @param holder the surface holder holding the view
   */
  public void surfaceCreated(SurfaceHolder holder) {
    // Setup PaintText
    Paint paintText = new Paint();
    paintText.setTextSize(36);
    paintText.setTypeface(Typeface.DEFAULT_BOLD);
    charWidth = paintText.measureText("W");
    charHeight = -paintText.ascent() + paintText.descent();

    // use screen height and width and activity to generate the game manager the size of the
    // GameManager
    gameManager =
            AppManager.getInstance()
                    .buildGameManager(
                            Game.GameName.TAPPING,
                            (int) (getScreenHeight() / charHeight),
                            (int) (getScreenWidth() / charWidth),
                            activity);
    gameManager.setScreenHeight(this.getScreenHeight());
    gameManager.setScreenWidth(this.getScreenWidth());
    if (gameManager instanceof TappingGameManager) {
      ((TappingGameManager) gameManager)
              .setGridWidthHeight(
                      (int) (getScreenWidth() / charWidth), (int) (getScreenHeight() / charHeight));
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
    // Set up CountDownTimer
    CountDownTimer timer =
            new CountDownTimer(10000, 1000) {

              @Override
              public void onTick(long millisUntilFinished) {
                secondLeft--;
                if (gameManager instanceof TappingGameManager) {
                  // decrement seconds left in tapping game manager
                  ((TappingGameManager) gameManager).setSecondsLeft(secondLeft);
                }
                // display the remaining time
                long timeTillEnd = (millisUntilFinished / 1000) + 1;
                long secondsPassed = 10 - timeTillEnd;
                if (0 == secondsPassed) {// do nothing initally
                } else {
                  // generate average speed
                  speed = (int) (numOfTaps / secondsPassed);
                  // set speed in game manager
                  if (gameManager instanceof TappingGameManager) {
                    ((TappingGameManager) gameManager).setTappingSpeed(speed);
                  }
                }
              }

              @Override
              public void onFinish() {
                (gameManager).gameOver();
              }
            };
    myTimer = timer;
    timer.start();
    gameStarted = true;
  }

  @Override
  /**
   * Execute tasks when surface is destroyed
   */
  public void surfaceDestroyed(SurfaceHolder holder) {
    super.surfaceDestroyed(holder);
    // Cancel timer
    myTimer.cancel();
  }

  @Override
  /**
   * Increment number of taps and set the number in game manager
   */
  public void onClick(View v) {
    if (gameStarted) {
      numOfTaps++;
      gameManager.setNumTaps(numOfTaps);
    }
  }

  @Override
  /**
   * Draw this GameItem.
   *
   * @param canvas the canvas on which to draw this item.
   */
  public void drawItem(Canvas canvas, GameItem item) {
    setupPaintText();
    double xCoordinate = item.getXCoordinate();
    double yCoordinate = item.getYCoordinate();
    // key used to extract bitmaps from the hash map
    String key = "";
    // If item is a Runner or a Tapping Circle
    if (item instanceof Runner || item instanceof TappingCircle) {
      // Generate key
      try {
        key = generateKey(item);
      } catch (Exception e) {
        System.out.println("No Key");
      }
      //Extract the bitmap from hashmap
      Bitmap appearance;
      if (item instanceof Runner) {
        appearance = getCurrentAppearance(key);
      } else {
        appearance = getAppearance(key);
      }
      // Draw bitmap
      canvas.drawBitmap(
              appearance, (int) Math.round(xCoordinate), (int) Math.round(yCoordinate), paintText);
    } else {
      canvas.drawText(
              item.getDescription(),
              (float) xCoordinate * GameView.charWidth,
              (float) yCoordinate * GameView.charHeight,
              paintText);
    }
  }
  /** Extract Bitmap Files and add them to hash map */
  public void extractBmpFiles() {
    TappingGameManager tappingGameManager = (TappingGameManager) gameManager;
    tappingCircleBmp = getNewBitmap(R.drawable.circle, getScreenWidth(), getScreenHeight() / 2);
    //Generate Runner Size and set it in game Manager
    int runnerWidth = (int) (getScreenWidth() * RUNNER_WIDTH_MULTIPLIER);
    int runnerHeight = (int) (getScreenWidth() * RUNNER_HEIGHT_MULTIPLIER);
    if (gameManager instanceof TappingGameManager) {
      ((TappingGameManager) gameManager).setItemSize(runnerWidth, runnerHeight);
    }

    yellowPugs = new ArrayList<>();
    blueBirds = new ArrayList<>();
    redFishes = new ArrayList<>();
    // generate runner bitmaps lists
    generateAnimatedBmps(yellowPugs, YELLOW_PUG_FILES, runnerWidth, runnerHeight);
    generateAnimatedBmps(blueBirds, BLUE_BIRD_FILES, runnerWidth, runnerHeight);
    generateAnimatedBmps(redFishes, RED_FISHES_FILES, runnerWidth, runnerHeight);
    // Add bitmaps to hashmap
    addGameItemAppearance("TappingCircle", tappingCircleBmp);
    addGameItemAppearances("RunnerYellow", yellowPugs);
    addGameItemAppearances("RunnerBlue", blueBirds);
    addGameItemAppearances("RunnerRed", redFishes);
  }

  @Override
  /** Sets up PaintText */
  public void setupPaintText() {
    paintText = new Paint();
    paintText.setTypeface(Typeface.DEFAULT_BOLD);
    paintText.setTextSize(36);
    paintText.setColor(Color.CYAN);
  }

  /**
   * Generate key for the tapping game item
   * @param item game item
   * @return Key used to extract bitmap
   * @throws Exception if no key is generated
   */
  public String generateKey(GameItem item) throws Exception {
    if (item instanceof TappingCircle) {

      return "TappingCircle";

    } else if (item instanceof Runner) {
      return "Runner" + getCharacterColorScheme();

    } else {
      throw new Exception("No key generated");
    }
  }

  /**
   * Extract the appearance from a list of bitmaps for animation purpose
   * @param key key to extract a list of bitmap
   * @return the bitmap file in the list of bitmaps
   */
  public Bitmap getCurrentAppearance(String key) {
    // Extract the list of bitmaps from hashmap
    List<Bitmap> appearances = getAppearances(key);
    // Extract the bitmap according to the current index
    Bitmap appearance = appearances.get(currentFrameRunner);
    // helper method to process index so it points to the next bitmap in the bitmap list
    currentFrameRunner = updateIndex(currentFrameRunner, appearances.size());
    return appearance;
  }
}
