package com.example.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.passwordmanager.data.CRUDManager;
import com.example.passwordmanager.ui.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    private AuthManager authManager;
    private CRUDManager crudManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        authManager = new AuthManager();
        crudManager = new CRUDManager();
        progressBar = findViewById(R.id.pbSettings);

        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        toolbar.setNavigationOnClickListener(v -> finish());

        Button btnAbout = findViewById(R.id.btnAbout);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnAbout.setOnClickListener(v -> showAboutDialog());

        btnLogout.setOnClickListener(v -> {
            authManager.logout();
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnDeleteAccount.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About SecurePass");
        builder.setMessage("SecurePass v1.0\nDeveloped by: Martz, Parrot, Wright, Calloway\n\nA secure way to manage your passwords.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to permanently delete your account and all stored data? This action is irreversible.")
                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteAccount() {
        progressBar.setVisibility(View.VISIBLE);
        crudManager.deleteAllUserData((success, errorMessage) -> {
            if (success) {
                authManager.deleteAccount((authSuccess, authError) -> {
                    progressBar.setVisibility(View.GONE);
                    if (authSuccess) {
                        Toast.makeText(SettingsActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SettingsActivity.this, "Error deleting auth record: " + authError, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SettingsActivity.this, "Error deleting data: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}