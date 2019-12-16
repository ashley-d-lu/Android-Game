package com.example.game1.presentation.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.presenter.UserManager;

public class StatisticsActivity extends AppCompatActivity {

  private UserManager userManager = AppManager.getInstance().getUserManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_statistics);

    // Display information about score
    EditText infoText = findViewById(R.id.infoText);
    infoText.setText(R.string.stats_score_description);

    // Display the user's statistics
    displayCurrentGameStats();
    displayTopGameStats();
    displayTopIndividualStats();
  }

  /**
   * Displays the statistics of the user's current game
   */
  private void displayCurrentGameStats(){
    EditText currPointsText = findViewById(R.id.currPointsText);
    currPointsText.setText(
            String.valueOf(userManager.getCurrentUser().getStatsOfCurrentGame().getPoints()));

    EditText currStarsText = findViewById(R.id.currStarsText);
    currStarsText.setText(
            String.valueOf(userManager.getCurrentUser().getStatsOfCurrentGame().getStars()));

    EditText currTapsText = findViewById(R.id.currTapsText);
    currTapsText.setText(
            String.valueOf(userManager.getCurrentUser().getStatsOfCurrentGame().getTaps()));

    EditText currScoreText = findViewById(R.id.currScoreText);
    String usernameOfCurrUser = userManager.getCurrentUser().getUserName();
    currScoreText.setText(String.valueOf(userManager.getCurrentScore(usernameOfCurrUser)));
  }

  /**
   * Displays the statistics of the user's top game
   */
  private void displayTopGameStats(){
    EditText topPointsText = findViewById(R.id.topPointsText);
    topPointsText.setText(
            String.valueOf(userManager.getCurrentUser().getStatsOfTopGame().getPoints()));

    EditText topStarsText = findViewById(R.id.topStarsText);
    topStarsText.setText(
            String.valueOf(userManager.getCurrentUser().getStatsOfTopGame().getStars()));

    EditText topTapsText = findViewById(R.id.topTapsText);
    topTapsText.setText(String.valueOf(userManager.getCurrentUser().getStatsOfTopGame().getTaps()));

    EditText topScoreText = findViewById(R.id.topScoreText);
    String usernameOfCurrUser = userManager.getCurrentUser().getUserName();
    topScoreText.setText(String.valueOf(userManager.getTopScore(usernameOfCurrUser)));
  }

  /**
   * Displays the user's top individual statistics
   */
  private void displayTopIndividualStats(){
    EditText topIndPointsText = findViewById(R.id.topIndPointsText);
    topIndPointsText.setText(
            String.valueOf(userManager.getCurrentUser().getTopIndividualStats().getPoints()));

    EditText topIndStarsText = findViewById(R.id.topIndStarsText);
    topIndStarsText.setText(
            String.valueOf(userManager.getCurrentUser().getTopIndividualStats().getStars()));

    EditText topIndTapsText = findViewById(R.id.topIndTapsText);
    topIndTapsText.setText(
            String.valueOf(userManager.getCurrentUser().getTopIndividualStats().getTaps()));
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

  /** Called when the user taps the Android device's Back button */
  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, UserMenuActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the Back button */
  public void goBack(View view) {
    Intent intent = new Intent(this, UserMenuActivity.class);
    startActivity(intent);
  }
}
