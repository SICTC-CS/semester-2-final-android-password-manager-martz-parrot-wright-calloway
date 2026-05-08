package com.example.passwordmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.passwordmanager.data.model.Acount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private TextView service, name, password, cost, email, type, madeOn;
    private ImageButton showPassword;
    private Button link;
    private boolean passwordShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        Acount a = (Acount) getIntent().getSerializableExtra("account");
        if (a == null) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.detailsToolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        service = findViewById(R.id.dService);
        name = findViewById(R.id.dName);
        password = findViewById(R.id.dPassword);
        cost = findViewById(R.id.dCost);
        showPassword = findViewById(R.id.dShowPassword);
        email = findViewById(R.id.dEmail);
        type = findViewById(R.id.dCatagory);
        madeOn = findViewById(R.id.dBirth);
        link = findViewById(R.id.dURL);

        password.setText("*".repeat(a.getPassword().length()));

        showPassword.setOnClickListener(v -> {
            if (passwordShown) {
                password.setText("*".repeat(a.getPassword().length()));
                passwordShown = false;
            } else {
                password.setText(a.getPassword());
                passwordShown = true;
            }
        });

        if (a.getLink() != null && Patterns.WEB_URL.matcher(a.getLink()).matches()) {
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(v -> {
                try {
                    Intent toWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getLink()));
                    startActivity(toWebsite);
                } catch (Exception e) {
                    Log.e("DetailsActivity", "Error opening link", e);
                }
            });
        }

        service.setText(a.getService());
        name.setText(a.getName());
        email.setText(a.getEmail());
        type.setText(a.getCategory());
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        madeOn.setText(sdf.format(new Date(a.getMadeOn())));
        
        if (a.isSubscription()) {
            cost.setVisibility(View.VISIBLE);
            cost.setText(String.format(Locale.US, "$%.2f every %s", a.getCost(), a.getPaymentInterval()));
        } else {
            findViewById(R.id.tvLabelCost).setVisibility(View.GONE);
            cost.setVisibility(View.GONE);
        }
    }
}