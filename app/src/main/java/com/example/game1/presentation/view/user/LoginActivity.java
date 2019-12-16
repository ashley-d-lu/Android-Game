package com.example.game1.presentation.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game1.AppManager;
import com.example.game1.R;
import com.example.game1.presentation.presenter.UserManager;

public class LoginActivity extends AppCompatActivity {

  private UserManager userManager = AppManager.getInstance().getUserManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  /** Called when the user taps the Login button */
  public void sendLoginCredentials(View view) {
    Intent intent = new Intent(this, UserMenuActivity.class);

    EditText messageText = findViewById(R.id.messageText);
    messageText.setText("");

    EditText userNameText = findViewById(R.id.userNameText);
    String userName = userNameText.getText().toString();

    EditText passwordText = findViewById(R.id.passwordText);
    String password = passwordText.getText().toString();

    if (userManager.loginUser(userName.toLowerCase(), password)) {
      // The username and password were correct. Go to the user menu.
      startActivity(intent);
    } else {
      messageText.setText(R.string.login_incorrect_username_or_pass);
    }
  }
}
