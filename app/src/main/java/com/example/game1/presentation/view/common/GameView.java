package com.example.game1.presentation.view.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.presentation.model.Customization;
import com.example.game1.presentation.model.common.GameItem;
import com.example.game1.presentation.presenter.common.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** The game view. */
public abstract class GameView extends SurfaceView implements SurfaceHolder.Callback {
  /** The width of a character. */
  public static float charWidth;
  /** The height of a character. */
  public static float charHeight;
  /** The manager. */
  public GameManager gameManager;
  /** The part of the program that manages time. */
  public GameThread thread;
  /** The activity class corresponding this view */
  public AppCompatActivity activity;
  /** This item's Paint. */
  public Paint paintText = new Paint();
  /** Hash Map that map the GameItems with its appearance */
  protected HashMap<String, List<Bitmap>> itemAppearances;
  /** Screen width. */
  private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
  /** Screen height. */
  private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
  /** character Color Scheme */
  private String characterColorScheme;
  /** Background color scheme */
  private String backgroundColorScheme;
  /** Background color */
  private int backgroundColor;
  /** Dark background color */
  private int backgroundColorDark;
  /** Light background color */
  private int backgroundColorLight;

  /**
   * Constructs a GameView with the given context.
   *
   * @param context the environment.
   */
  public GameView(Context context) {
    super(context);
    this.activity = (AppCompatActivity) context;
    getHolder().addCallback(this);
    setFocusable(true);
    itemAppearances = new HashMap<>();
  }

  /**
   * Get screen width
   *
   * @return screen width
   */
  public int getScreenWidth() {
    return screenWidth;
  }

  /**
   * Get screen height
   *
   * @return screen height
   */
  public int getScreenHeight() {
    return screenHeight;
  }

  @Override
  /**
   * Execute tasks when a surface is created. To be overriden by the game view for each of the game
   */
  public abstract void surfaceCreated(SurfaceHolder holder);

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

  /**
   * Stops the game when the game view is closed
   *
   * @param holder the SurfaceHolder holding this game view
   */
  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry = true;
    while (retry) {
      try {
        thread.setRunning(false);
        thread.join();
        gameManager.gameOver();

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      retry = false;
    }
  }

  /** Update this GameView's GameManager. */
  public void update() {
    boolean updated = gameManager.update();
    // stop thread if update fails
    if (!updated) {
      thread.setRunning(false);
    }
  }

  /**
   * draws all the items in this game
   *
   * @param canvas the canvas on which to draw
   */
  public void drawItems(Canvas canvas) {
    List<GameItem> items = gameManager.getGameItems();
    for (GameItem item : items) {
      drawItem(canvas, item);
    }
  }

  /**
   * Draws this entire game view
   *
   * @param canvas the canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    if (canvas != null) {
      canvas.drawColor(getBackgroundColor());
      drawItems(canvas);
    }
  }

  /**
   * Draw this GameItem.
   *
   * @param canvas the canvas on which to draw this item.
   * @param item the item to draw
   */
  public void drawItem(Canvas canvas, GameItem item) {

    paintText = new Paint();
    paintText.setTypeface(Typeface.DEFAULT_BOLD);
    paintText.setTextSize(36);
    Object appearance = item.getDescription();
    double xCoordinate = item.getXCoordinate();
    double yCoordinate = item.getYCoordinate();
    if (appearance.getClass() == String.class) {

      // TODO NEW: remove * charWidth?
      canvas.drawText(
              (String) appearance,
              (float) xCoordinate * GameView.charWidth,
              (float) yCoordinate * GameView.charHeight,
              paintText);

    } else if (appearance.getClass() == Bitmap.class) {
      canvas.drawBitmap(
              (Bitmap) appearance,
              (int) Math.round(xCoordinate),
              (int) Math.round(yCoordinate),
              paintText);
    }
  }

  /**
   * Draw this GameItem.
   *
   * @param canvas the canvas on which to draw this item.
   */
  public void drawItem(Canvas canvas, GameItem item, Bitmap appearance) {
    double xCoordinate = item.getXCoordinate();
    double yCoordinate = item.getYCoordinate();

    canvas.drawBitmap(
            appearance, (int) Math.round(xCoordinate), (int) Math.round(yCoordinate), paintText);
  }

  /**
   * Resize a bitmap to the specified width and height. code taken from:
   * https://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
   *
   * @param bm the bitmap to be resized
   * @param newWidth the new width
   * @param newHeight the new height
   * @return the resized bitmap
   */
  public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
    int width = bm.getWidth();
    int height = bm.getHeight();
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // CREATE A MATRIX FOR THE MANIPULATION
    Matrix matrix = new Matrix();
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight);

    // "RECREATE" THE NEW BITMAP
    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    bm.recycle();
    return resizedBitmap;
  }

  /**
   * Returns a bitmap image from the given resource with width and height given by the parameters
   * width and height respectively
   *
   * @param resource the image resource to draw
   * @param width the desired width
   * @param height the desired height
   * @return a resized bitmap image using the given resource
   */
  public Bitmap getNewBitmap(int resource, int width, int height) {
    Bitmap newBmp = BitmapFactory.decodeResource(getResources(), resource);
    newBmp = getResizedBitmap(newBmp, width, height);
    return newBmp;
  }

  /**
   * Generates a list of resized bitmap image for an animated item (one image for each frame of its
   * animation) and stores them in dest
   *
   * @param dest the destination list to store all the bitmap image frames
   * @param files the files for each frame of the animation
   * @param width the width of the desired images
   * @param height the height of the desired images
   */
  public void generateAnimatedBmps(List<Bitmap> dest, int[] files, int width, int height) {
    for (int i : files) {
      dest.add(getNewBitmap(i, width, height));
    }
  }

  /** Maps each item in the game to its associated bitmap images */
  public abstract void extractBmpFiles();

  public HashMap<String, List<Bitmap>> getItemAppearances() {
    return itemAppearances;
  }

  /**
   * Adds a GameItem's image to itemAppearances.
   * @param gameItemName the GameItem's name
   * @param image the GameItem's image
   */
  public void addGameItemAppearance(String gameItemName, Bitmap image) {
    if (itemAppearances.containsKey(gameItemName)) {
      itemAppearances.get(gameItemName).add(image);
    } else {
      List<Bitmap> images = new ArrayList<>();
      images.add(image);
      itemAppearances.put(gameItemName, images);
    }
  }

  /**
   * Adds a GameItem's images to itemAppearances.
   * @param gameItemName the GameItem's name
   * @param images the GameItem's images
   */
  public void addGameItemAppearances(String gameItemName, List<Bitmap> images) {
    if (itemAppearances.containsKey(gameItemName)) {
      for (Bitmap image : images) {
        itemAppearances.get(gameItemName).add(image);
      }
    } else {
      itemAppearances.put(gameItemName, images);
    }
  }

  /**
   * Returns a GameItem's appearances.
   * @param key the key of the GameItem
   * @return the GameItem's appearances
   */
  public List<Bitmap> getAppearances(String key) {
    return itemAppearances.get(key);
  }

  /**
   * Returns a GameItem's appearance.
   * @param key the key of the GameItem
   * @return the GameItem's appearance
   */
  public Bitmap getAppearance(String key) {
    return itemAppearances.get(key).get(0);
  }

  /** Sets backgroundColourScheme based on gameManager's Customization. */
  public void generateBackgroundColorScheme() {

    Customization cust = gameManager.getGame().getCustomization();
    if (cust.getColourScheme().equals(Customization.ColourScheme.DARK)) {
      this.backgroundColorScheme = "Dark";
    } else if (cust.getColourScheme().equals(Customization.ColourScheme.LIGHT)) {
      this.backgroundColorScheme = "Light";
    }
  }

  /** Sets characterColourScheme based on gameManager's Customization. */
  public void generateCharacterColor() {
    Customization cust = gameManager.getGame().getCustomization();
    if (cust.getCharacterColour().equals(Customization.CharacterColour.BLUE)) {
      this.characterColorScheme = "Blue";
    } else if (cust.getCharacterColour().equals(Customization.CharacterColour.YELLOW)) {
      this.characterColorScheme = "Yellow";
    } else if (cust.getCharacterColour().equals(Customization.CharacterColour.RED)) {
      this.characterColorScheme = "Red";
    }
  }

  /** Sets backgroundColourScheme. */
  public void generateBackgroundColor() {
    generateBackgroundColorScheme();
    if (this.backgroundColorScheme.equals("Dark")) {
      setScreenBackgroundColor(backgroundColorDark);
    } else if (this.backgroundColorScheme.equals("Light")) {
      setScreenBackgroundColor(backgroundColorLight);
    }
  }
 /** Gets backgroundColor .*/
  public int getBackgroundColor() {
    return backgroundColor;
  }

  /** Sets backgroundColor to the specified color.
   * @param backgroundColor color
   */
  public void setScreenBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  /** Sets backgroundColorDark to the specified color.
   * @param backgroundColorDark color
   */
  public void setBackgroundColorDark(int backgroundColorDark) {
    this.backgroundColorDark = backgroundColorDark;
  }

  /** Sets backgroundColorLight to the specified color.
   * @param backgroundColorLight color
   */
  public void setBackgroundColorLight(int backgroundColorLight) {
    this.backgroundColorLight = backgroundColorLight;
  }

  /**
   * Returns characterColorScheme.
   * @return characterColorScheme
   */
  public String getCharacterColorScheme() {
    return characterColorScheme;
  }

  /** Updates index to the specified length.
   * @param index index
   * @param length length
   * @return updated index
   */
  public int updateIndex(int index, int length) {
    index += 1;
    if (index == length) {
      index = 0;
    }
    return index;
  }

  /** Sets up PaintText */
  public void setupPaintText() {
    paintText = new Paint();
    paintText.setTypeface(Typeface.DEFAULT_BOLD);
    paintText.setTextSize(36);
  }
}
