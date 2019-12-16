package com.example.game1.presentation.view.applegame;

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
import com.example.game1.presentation.model.Statistics;
import com.example.game1.presentation.model.applegame.Apple;
import com.example.game1.presentation.model.applegame.AppleStar;
import com.example.game1.presentation.model.applegame.Basket;
import com.example.game1.presentation.model.applegame.LivesCounter;
import com.example.game1.presentation.model.applegame.PointsCounter;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.presenter.applegame.AppleGameManager;
import com.example.game1.presentation.view.common.GameThread;
import com.example.game1.presentation.view.common.GameView;

import java.util.ArrayList;
import java.util.List;

/** An apple game view. */
public class AppleGameView extends GameView implements View.OnClickListener {
  /** A list to store apple bitmap */
  private List<Bitmap> appleBmps;
  /** A list to store star bitmap */
  private List<Bitmap> starBmps;
  /** A list to store basket bitmap */
  private List<Bitmap> basketBmps;
  /** A list to store blue basket bitmap */
  private List<Bitmap> basketBlueBmps;
  /** A list to store yellow basket bitmap */
  private List<Bitmap> basketYellowBmps;
  /** A list to store red basket bitmap */
  private List<Bitmap> basketRedBmps;
  /** Dark background color */
  private final int BACKGROUND_COLOR_DARK = Color.BLACK;
  /** Light background color */
  private final int BACKGROUND_COLOR_LIGHT = Color.LTGRAY;
  /** The width of the apple */
  public static final int APPLE_WIDTH = 100;
  /** The height of the apple */
  public static final int APPLE_HEIGHT = 100;
  /** The width of the star */
  public static final int STAR_WIDTH = 80;
  /** The height of the star */
  public static final int STAR_HEIGHT = 80;
  /** The width of the basket */
  public static final int BASKET_WIDTH = 100;
  /** The height of the basket */
  public static final int BASKET_HEIGHT = 100;
  /** The OnClickListener used to collect the number of click */
  private OnClickListener listener;

  // image files for game objects
  private final int[] APPLE_FILES = {R.drawable.apple_red};
  private final int[] STAR_FILES = {R.drawable.star_6};
  private final int[] BASKET_FILES = {R.drawable.basket_red};
  private final int[] BASKET_BLUE_FILES = {R.drawable.basket_blue};
  private final int[] basketYellowFiles = {R.drawable.basket_yellow};
  private final int[] BASKET_RED_FILES = {R.drawable.basket_red};

  /**
   * Constructs an AppleGameView with the specified Context.
   *
   * @param context context
   */
  public AppleGameView(Context context) {
    super(context);
    thread = new GameThread(getHolder(), this);
    // Create a new OnClickListener and set listener to this one.
    this.listener =
        new OnClickListener() {
          @Override
          /** increment number of taps when there is a click */
          public void onClick(View v) {
            gameManager.incrementNumTaps();
          }
        };
  }

  /** Sets up the view when surface is created. */
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    setupPaintText();
    // use screen height and width and activity to generate the game manager the size of the
    // GameManager
    gameManager =
        AppManager.getInstance()
            .buildGameManager(Game.GameName.APPLE, getScreenHeight(), getScreenWidth(), activity);
    gameManager.startMusic();
    // extract bitmap files and added them to a hashmap
    extractBmpFiles();
    // generate the color for the character
    generateCharacterColor();
    // Create a apple game manager
    gameManager.setNumSeconds(GameThread.FRAME_DURATION_NS / 1000000000.);
    if (gameManager instanceof AppleGameManager) {
      ((AppleGameManager) gameManager)
          .setItemSize(
              APPLE_WIDTH, APPLE_HEIGHT, STAR_WIDTH, STAR_HEIGHT, BASKET_WIDTH, BASKET_HEIGHT);
    }

    // Setup background color according to customization
    setBackgroundColorDark(BACKGROUND_COLOR_DARK);
    setBackgroundColorLight(BACKGROUND_COLOR_LIGHT);
    generateBackgroundColor();
    // Create game items
    gameManager.createGameItems();

    thread.setRunning(true);
    thread.start();
    // Setup OnClickListener
    this.setOnClickListener(this.listener);
  }

  /** Sends statistics when surface is destroyed. */
  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    super.surfaceDestroyed(holder);
    Statistics gameStatistics = gameManager.getGame().getStatistics();
    gameStatistics.setTaps(gameStatistics.getTaps() + gameManager.getNumTaps());
  }

  /** Increments number of taps when there is a click. */
  @Override
  public void onClick(View v) {
    gameManager.incrementNumTaps();
  }

  /** Sets xCoordinate of the basket according to the position of the cursor. */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // move basket to location of tap
    double touchX = event.getX();
    ((AppleGameManager) gameManager).moveBasket((int) touchX);
    return super.onTouchEvent(event);
  }

  /** Draws a GameItem on the specified Canvas. */
  @Override
  public void drawItem(Canvas canvas, GameItem item) {
    double xCoordinate = item.getXCoordinate();
    double yCoordinate = item.getYCoordinate();
    // Draw text for Lives Counter and Points Counter
    if (item instanceof LivesCounter || item instanceof PointsCounter) { // String appearance
      setupPaintText();
      String appearance = item.getDescription();
      if (appearance instanceof String) {
        paintText.setColor(Color.WHITE);
      }
      canvas.drawText((String) appearance, (float) xCoordinate, (float) yCoordinate, paintText);
    }
    // Draw bitmap for Apple, AppleStar and Basket
    if (item instanceof Apple || item instanceof AppleStar || item instanceof Basket) {
      Bitmap appearance;
      String key;

      if (item instanceof Basket) {
        // Generate key according to color scheme selected
        key = "Basket" + getCharacterColorScheme();
      } else {
        key = item.getClass().getSimpleName();
      }
      // Extract bitmap from hashmap
      appearance = getAppearance(key);
      canvas.drawBitmap(
          appearance, (int) Math.round(xCoordinate), (int) Math.round(yCoordinate), paintText);
    }
  }

  /** Extract Bitmap Files and add them to hash map */
  public void extractBmpFiles() {
    // animated items require a list of bitmaps for each of their frames
    appleBmps = new ArrayList<>();
    starBmps = new ArrayList<>();
    basketBmps = new ArrayList<>();
    basketBlueBmps = new ArrayList<>();
    basketYellowBmps = new ArrayList<>();
    basketRedBmps = new ArrayList<>();

    generateAnimatedBmps(appleBmps, APPLE_FILES, APPLE_WIDTH, APPLE_HEIGHT);
    generateAnimatedBmps(starBmps, STAR_FILES, STAR_WIDTH, STAR_HEIGHT);
    generateAnimatedBmps(basketBmps, BASKET_FILES, BASKET_WIDTH, BASKET_HEIGHT);
    generateAnimatedBmps(basketBlueBmps, BASKET_BLUE_FILES, BASKET_WIDTH, BASKET_HEIGHT);
    generateAnimatedBmps(basketYellowBmps, basketYellowFiles, BASKET_WIDTH, BASKET_HEIGHT);
    generateAnimatedBmps(basketRedBmps, BASKET_RED_FILES, BASKET_WIDTH, BASKET_HEIGHT);
    // Add bitmaps to hashmap
    addGameItemAppearances("BasketYellow", basketYellowBmps);
    addGameItemAppearances("BasketBlue", basketBlueBmps);
    addGameItemAppearances("BasketRed", basketRedBmps);
    addGameItemAppearances("Apple", appleBmps);
    addGameItemAppearances("AppleStar", starBmps);
  }


}
