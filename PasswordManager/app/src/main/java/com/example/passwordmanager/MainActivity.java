package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.data.AcountAdapter;
import com.example.passwordmanager.data.CRUDManager;
import com.example.passwordmanager.data.model.Acount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvEvidenceStream;
    private static AcountAdapter adapter;
    private CRUDManager crud = new CRUDManager();
    private List<Acount> acountList;
    private FirebaseUser user= new AuthManager().getActiveUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        acountList = new ArrayList<>();

        rvEvidenceStream = findViewById(R.id.rvEvidenceStream);


        //Recycler and Adapter
//        acountList.add(new Acount("test","test","test","test","test","test","test",System.currentTimeMillis(),5.55, false));
        adapter = new AcountAdapter(acountList);
        adapter.importFirebaseData();
        rvEvidenceStream.setLayoutManager(new LinearLayoutManager(this));
        rvEvidenceStream.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddAcount);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randy = new Random();
                crud.writeNewAcount(new Acount("test","test","test","test","test","test","test",System.currentTimeMillis(), 1 + ((20 - 1) * randy.nextDouble()), false), new CRUDManager.CrudCallback() {
                    @Override
                    public void onComplete(boolean success, String errorMessage) {
                        if(success){
                            Log.d("writeNewAcount","succes");


                        } else {
                            Snackbar.make(MainActivity.this.getCurrentFocus(),errorMessage, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}