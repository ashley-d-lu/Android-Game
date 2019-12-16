package com.example.game1.presentation.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.presenter.UserManager;

public class CustomizationActivity extends AppCompatActivity {

  /* The customization choices that appear in the spinners */
  private String[] characterColours = {"Blue", "Red", "Yellow"};
  private String[] colourSchemes = {"Dark", "Light"};
  private String[] musicChoices = {"Chibi Ninja", "Arpanauts", "A Night Of Dizzy Spells"};
  private UserManager userManager = AppManager.getInstance().getUserManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customization);

    // Set up the spinners to have the current user's customization choices selected
    setCharacterColourSpinnerSelection();
    setColourSchemeSpinnerSelection();
    setMusicChoiceSpinnerSelection();
  }

  /** Set up the character colour spinner to have the current user's choice selected */
  private void setCharacterColourSpinnerSelection() {
    Spinner characterColoursSpinner = findViewById(R.id.characterColourSpinner);
    ArrayAdapter characterColoursAdapter =
        new ArrayAdapter(this, android.R.layout.simple_spinner_item, characterColours);
    characterColoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    characterColoursSpinner.setAdapter(characterColoursAdapter);

    String userCharacColour =
        userManager.getCurrentUser().getCustomization().getCharacterColour().toString();
    if (userCharacColour.equals("RED")) {
      characterColoursSpinner.setSelection(1);
    } else if (userCharacColour.equals("YELLOW")) {
      characterColoursSpinner.setSelection(2);
    } else {
      characterColoursSpinner.setSelection(0);
    }
  }

  /** Set up the colour scheme spinner to have the current user's choice selected */
  private void setColourSchemeSpinnerSelection() {
    Spinner colourSchemeSpinner = findViewById(R.id.colourSchemeSpinner);
    ArrayAdapter colourSchemeAdapter =
        new ArrayAdapter(this, android.R.layout.simple_spinner_item, colourSchemes);
    colourSchemeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    colourSchemeSpinner.setAdapter(colourSchemeAdapter);

    String userColourScheme =
        userManager.getCurrentUser().getCustomization().getColourScheme().toString();
    if (userColourScheme.equals("LIGHT")) {
      colourSchemeSpinner.setSelection(1);
    } else {
      colourSchemeSpinner.setSelection(0);
    }
  }

  /** Set up the music choice spinner to have the current user's choice selected */
  private void setMusicChoiceSpinnerSelection() {
    Spinner musicSpinner = findViewById(R.id.musicSpinner);
    ArrayAdapter musicAdapter =
        new ArrayAdapter(this, android.R.layout.simple_spinner_item, musicChoices);
    musicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    musicSpinner.setAdapter(musicAdapter);

    String userMusic = userManager.getCurrentUser().getCustomization().getMusicPath().toString();
    if (userMusic.equals("SONG2")) {
      musicSpinner.setSelection(1);
    } else if (userMusic.equals("SONG3")) {
      musicSpinner.setSelection(2);
    } else {
      musicSpinner.setSelection(0);
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

  /** Called when the user taps the Done button */
  public void sendCustomizationChoices(View view) {
    Intent intent = new Intent(this, UserMenuActivity.class);

    // Get the customization choices from the spinners
    Spinner characterColourSpinner = findViewById(R.id.characterColourSpinner);
    String characterColourChoice = characterColourSpinner.getSelectedItem().toString();
    Spinner colourSchemeSpinner = findViewById(R.id.colourSchemeSpinner);
    String colourSchemeChoice = colourSchemeSpinner.getSelectedItem().toString();
    Spinner musicSpinner = findViewById(R.id.musicSpinner);
    String musicChoice = musicSpinner.getSelectedItem().toString();

    // Update the current user's customization choices
    userManager.updateCurrentUsersCustomization(
        characterColourChoice, colourSchemeChoice, musicChoice);

    // Go back to the main menu
    startActivity(intent);
  }
}
