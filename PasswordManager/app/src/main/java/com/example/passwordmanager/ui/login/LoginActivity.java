package com.example.passwordmanager.ui.login;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        authManager = new AuthManager();

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickedBTN","ClickedBTN");
                if (!registerMode){
                    String user = username.getText().toString();
                    String password = passwordInput.getText().toString();
                    authManager.signIn(user, password, new AuthManager.AuthCallback() {
                        @Override
                        public void onComplete(boolean success, String errorMessage) {
                            if (success){
                                Snackbar.make(v,"Success",Snackbar.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else {
                                Snackbar.make(v,errorMessage,Snackbar.LENGTH_SHORT).show();
                                errorText.setVisibility(VISIBLE);
                                errorText.setText("Wrong Username or Password");
                                tries -=1;
                                if (tries == 0){
                                    finishAffinity();
                                }
                            }
                        }
                    });
                }
                else{
                    String user = username.getText().toString();
                    String password = passwordInput.getText().toString();
                    if(authManager.PasswordCheck(password)){
                        authManager.signUP(user, password, new AuthManager.AuthCallback() {
                            @Override
                            public void onComplete(boolean success, String errorMessage) {
                                if (success){
                                    Snackbar.make(v,"Success",Snackbar.LENGTH_SHORT).show();
                                    username.setText("");
                                    passwordInput.setText("");
                                    registerMode = false;
                                    loginBT.setText(getString(R.string.action_Login));
                                    registerTXT.setText("Sign up");
                                }else {
                                    Snackbar.make(v,errorMessage,Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        errorText.setVisibility(VISIBLE);
                        errorText.setText(getString(R.string.invalid_password));
                    }
                }
            }
        });
        Log.d("onCreate","ClickedBTN");

        registerTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registerMode){
                    registerMode = true;
                    loginBT.setText("Register User");
                    registerTXT.setText("Back to Login");
                }
                else{
                    registerMode = false;
                    loginBT.setText(getString(R.string.action_Login));
                    registerTXT.setText("Sign up");
                }
            }
        });





    }


}