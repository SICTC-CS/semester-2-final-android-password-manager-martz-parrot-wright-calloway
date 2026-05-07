package com.example.passwordmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.passwordmanager.data.model.Acount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private TextView service, name, password,cost,email,type,madeOn;
    private Button showPassword, link;
    private Boolean passwordShown=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        Acount a= (Acount) getIntent().getSerializableExtra("account");
        service=findViewById(R.id.dService);
        name=findViewById(R.id.dName);
        password=findViewById(R.id.dPassword);
        cost=findViewById(R.id.dCost);
        showPassword=findViewById(R.id.dShowPassword);
        email=findViewById(R.id.dEmail);
        type=findViewById(R.id.dCatagory);
        madeOn=findViewById(R.id.dBirth);
        link=findViewById(R.id.dURL);
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShown){
                    password.setText("Password:\n"+a.getPassword());
                    passwordShown=false;
                }
                else{
                    passwordShown=true;
                    password.setText("Password:\n"+("*".repeat(a.getPassword().length())));
                }
            }
        });

        if(a.getLink() != null && Patterns.WEB_URL.matcher(a.getLink()).matches()){
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("link button",a.getLink());
                    Intent toWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getLink()));
                    v.getContext().startActivity(toWebsite);

                }
            });
        }
        service.setText("Service:\n"+a.getService());
        name.setText("Name:\n"+a.getName());
        email.setText("Email:\n"+a.getEmail());
        type.setText("Account Catagory:\n"+a.getCategory());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        madeOn.setText("Account Made on:\n"+sdf.format(new Date(a.getMadeOn())));
        cost.setText("Cost:\n"+String.format("$%.2f", a.getCost())+" to be paid every "+a.getPaymentInterval());
        password.setText("Password:\n"+("*".repeat(a.getPassword().length())));


    }
}