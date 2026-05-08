package com.example.passwordmanager.ui.login;

import static android.view.View.VISIBLE;
import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.AuthManager;
import com.example.passwordmanager.MainActivity;
import com.example.passwordmanager.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private Button loginBT;
    private TextView registerTXT, errorText;
    private EditText username, passwordInput;
    private ProgressBar loadingBar;

    boolean registerMode = false;
    private int tries = 3;
    private AuthManager authManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBT = findViewById(R.id.loginBTN);
        registerTXT = findViewById(R.id.textView);
        username = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        errorText = findViewById(R.id.textView4);
        loadingBar = findViewById(R.id.loading);

        authManager = new AuthManager();

        // Ensure user is not already logged in when starting the app
        authManager.logout();

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (user.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "Please enter email and password", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                loadingBar.setVisibility(VISIBLE);
                loginBT.setEnabled(false);

                if (!registerMode) {
                    authManager.signIn(user, password, new AuthManager.AuthCallback() {
                        @Override
                        public void onComplete(boolean success, String errorMessage) {
                            loadingBar.setVisibility(GONE);
                            loginBT.setEnabled(true);
                            if (success) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                errorText.setVisibility(VISIBLE);
                                errorText.setText("Login Failed: " + errorMessage);
                                tries -= 1;
                                if (tries <= 0) {
                                    finishAffinity();
                                }
                            }
                        }
                    });
                } else {
                    if (authManager.passwordCheck(password)) {
                        authManager.signUP(user, password, new AuthManager.AuthCallback() {
                            @Override
                            public void onComplete(boolean success, String errorMessage) {
                                loadingBar.setVisibility(GONE);
                                loginBT.setEnabled(true);
                                if (success) {
                                    Snackbar.make(v, "Account created! Please login.", Snackbar.LENGTH_SHORT).show();
                                    toggleRegisterMode(false);
                                } else {
                                    Snackbar.make(v, errorMessage, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        loadingBar.setVisibility(GONE);
                        loginBT.setEnabled(true);
                        errorText.setVisibility(VISIBLE);
                        errorText.setText(getString(R.string.invalid_password));
                    }
                }
            }
        });

        registerTXT.setOnClickListener(v -> toggleRegisterMode(!registerMode));
    }

    private void toggleRegisterMode(boolean mode) {
        registerMode = mode;
        if (registerMode) {
            loginBT.setText("Register User");
            registerTXT.setText("Back to Login");
        } else {
            loginBT.setText(getString(R.string.action_Login));
            registerTXT.setText("Sign up");
        }
        errorText.setVisibility(GONE);
    }
}