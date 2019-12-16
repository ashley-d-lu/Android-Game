package com.example.game1.presentation.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.presenter.UserManager;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    private String[] spinnerChoices = {"Total Score", "Points", "Stars", "Taps"};
    private UserManager userManager = AppManager.getInstance().getUserManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Set up the spinner choices
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        spinnerChoices);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Create the spinner listener
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        // Update header 2
                        Object item = parent.getItemAtPosition(pos);
                        EditText header2 = findViewById(R.id.tableHeader2);
                        String headerText = item.toString() + ":";
                        header2.setText(headerText);
                        // Update the values displayed in the scoreboard
                        updateScoreboard(item.toString());
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // Select the spinner's default option
        spinner.setSelection(0);
    }

    /**
     * Updates the scoreboard based on the current spinner selection
     */
    private void updateScoreboard(String selection) {
        displayDefaultValues();

        // Get the user names and statistics
        List<Pair<String, String>> usernameToStat = new ArrayList<>();
        int numUsers = userManager.getTopUsers(5, selection).size();
        for (int i = 0; i < numUsers; i++) {
            String username = userManager.getTopUsers(5, selection).get(i).getUserName();
            int stat;
            if ("Points".equals(selection)) {
                stat = userManager.getTopUsers(
                        5, selection).get(i).getTopIndividualStats().getPoints();
            } else if ("Stars".equals(selection)) {
                stat = userManager.getTopUsers(
                        5, selection).get(i).getTopIndividualStats().getStars();
            } else if ("Taps".equals(selection)) {
                stat = userManager.getTopUsers(
                        5, selection).get(i).getTopIndividualStats().getTaps();
            } else {  // "Total Score".equals(selection)
                stat = userManager.getTopScore(username);
            }
            usernameToStat.add(Pair.create(username, String.valueOf(stat)));
        }

        // Display the user names and statistics on the scoreboard
        displayNewValues(usernameToStat);
    }

    /**
     * Displays the values in the given list on the scoreboard
     */
    private void displayNewValues(List<Pair<String, String>> usernameToStat){
        int numUsers = usernameToStat.size();
        if (numUsers >= 1) {
            ((EditText) findViewById(R.id.username1)).setText(usernameToStat.get(0).first);
            ((EditText) findViewById(R.id.stat1)).setText(usernameToStat.get(0).second);
            if (numUsers >= 2) {
                ((EditText) findViewById(R.id.username2)).setText(usernameToStat.get(1).first);
                ((EditText) findViewById(R.id.stat2)).setText(usernameToStat.get(1).second);
                if (numUsers >= 3) {
                    ((EditText) findViewById(R.id.username3)).setText(usernameToStat.get(2).first);
                    ((EditText) findViewById(R.id.stat3)).setText(usernameToStat.get(2).second);
                    if (numUsers >= 4) {
                        ((EditText) findViewById(R.id.username4)).setText(
                                usernameToStat.get(3).first);
                        ((EditText) findViewById(R.id.stat4)).setText(
                                usernameToStat.get(3).second);
                        if (numUsers >= 5) {
                            ((EditText) findViewById(R.id.username5)).setText(
                                    usernameToStat.get(4).first);
                            ((EditText) findViewById(R.id.stat5)).setText(
                                    usernameToStat.get(4).second);
                        }
                    }
                }
            }
        }
    }

    /**
     * Displays default values on the scoreboard
     */
    private void displayDefaultValues() {
        ((EditText) findViewById(R.id.username1)).setText("N/A");
        ((EditText) findViewById(R.id.username2)).setText("N/A");
        ((EditText) findViewById(R.id.username3)).setText("N/A");
        ((EditText) findViewById(R.id.username4)).setText("N/A");
        ((EditText) findViewById(R.id.username5)).setText("N/A");
        ((EditText) findViewById(R.id.stat1)).setText("N/A");
        ((EditText) findViewById(R.id.stat2)).setText("N/A");
        ((EditText) findViewById(R.id.stat3)).setText("N/A");
        ((EditText) findViewById(R.id.stat4)).setText("N/A");
        ((EditText) findViewById(R.id.stat5)).setText("N/A");
    }

    /**
     * Changes the theme of the page based on the user's customization choice
     */
    private void setTheme() {
        if ("LIGHT".equals(
                userManager.getCurrentUser().getCustomization().getColourScheme().toString())) {
            setTheme(android.R.style.Theme_Material_Light_NoActionBar);
            getSupportActionBar().setTitle(Html.fromHtml(
                    "<font color='#ffffff'>" + getString(R.string.app_name) + "</font>"));
        }
    }

    /**
     * Called when the user taps the Back button
     */
    public void goBack(View view) {
        Intent intent = new Intent(this, UserMenuActivity.class);
        startActivity(intent);
    }
}
