package com.example.game1.presentation.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.MainActivity;
import com.example.game1.presentation.presenter.UserManager;
import com.example.game1.presentation.view.applegame.AppleActivity;
import com.example.game1.presentation.view.brickgame.BrickActivity;
import com.example.game1.presentation.view.jumpinggame.JumpingActivity;
import com.example.game1.presentation.view.tappinggame.TappingActivity;

public class UserMenuActivity extends AppCompatActivity {

  private UserManager userManager = AppManager.getInstance().getUserManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_menu);

    EditText userNameText = findViewById(R.id.userNameText);
    userNameText.setText(userManager.getCurrentUser().getUserName());

    // Disable all Play buttons. Then enable the Play button corresponding to the user's next
    // level.
    Button playButton1 = findViewById(R.id.playButton1);
    playButton1.setEnabled(false);
    Button playButton2 = findViewById(R.id.playButton2);
    playButton2.setEnabled(false);
    Button playButton3 = findViewById(R.id.playButton3);
    playButton3.setEnabled(false);
    Button playButton4 = findViewById(R.id.playButton4);
    playButton4.setEnabled(false);

    switch (userManager.getCurrentUser().getLastCompletedLevel()) {
      case 0:
        playButton1.setEnabled(true);
        break;
      case 1:
        playButton2.setEnabled(true);
        break;
      case 2:
        playButton3.setEnabled(true);
        break;
      case 3:
        playButton4.setEnabled(true);
        break;
      default:
        break;
    }
  }

  /** Changes the theme of the page based on the user's customization choice */
  private void setTheme() {
    if ("LIGHT"
        .equals(userManager.getCurrentUser().getCustomization().getColourScheme().toString())) {
      setTheme(android.R.style.Theme_Material_Light_NoActionBar);
      getSupportActionBar().setTitle(Html.fromHtml(
              "<font color='#ffffff'>" + getString(R.string.app_name) + "</font>"));
    }
  }

  /** Called when the user taps the Restart button */
  public void restartGame(View view) {
    Button playButton1 = findViewById(R.id.playButton1);
    playButton1.setEnabled(true);
    Button playButton2 = findViewById(R.id.playButton2);
    playButton2.setEnabled(false);
    Button playButton3 = findViewById(R.id.playButton3);
    playButton3.setEnabled(false);
    Button playButton4 = findViewById(R.id.playButton4);
    playButton4.setEnabled(false);
    userManager.restartCurrentUsersGame();
  }

  /** Called when the user taps the Logout button */
  public void logout(View view) {
    userManager.setCurrentUser(null);
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the 1st Play button */
  public void goToLevel1(View view) {
    Intent intent = new Intent(this, AppleActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the 2nd Play button */
  public void goToLevel2(View view) {
    Intent intent = new Intent(this, TappingActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the 3rd Play button */
  public void goToLevel3(View view) {
    Intent intent = new Intent(this, JumpingActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the 4th Play button */
  public void goToLevel4(View view) {
    Intent intent = new Intent(this, BrickActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the Customize button */
  public void goToCustomization(View view) {
    Intent intent = new Intent(this, CustomizationActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the View Statistics button */
  public void goToStatistics(View view) {
    Intent intent = new Intent(this, StatisticsActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the Scoreboard button */
  public void goToScoreboard(View view) {
    Intent intent = new Intent(this, ScoreboardActivity.class);
    startActivity(intent);
  }
}
