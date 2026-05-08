package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
    private FirebaseUser user = new AuthManager().getActiveUser();
    private SearchView searchView;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        acountList = new ArrayList<>();

        rvEvidenceStream = findViewById(R.id.rvEvidenceStream);
        searchView = findViewById(R.id.searchView);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        adapter = new AcountAdapter(acountList);
        adapter.importFirebaseData();
        rvEvidenceStream.setLayoutManager(new LinearLayoutManager(this));
        rvEvidenceStream.setAdapter(adapter);

        setupSearchAndFilter();

        FloatingActionButton fab = findViewById(R.id.fabAddAcount);
        fab.setOnClickListener(v -> {
            Random randy = new Random();
            crud.writeNewAcount(new Acount("test", "test", "test", "test", "test", "Work", "https://youtu.be/TFwXbp9bLlY?si=k6D5_94ssvVhp9lR", "test", System.currentTimeMillis(), 1 + ((20 - 1) * randy.nextDouble()), randy.nextBoolean()), new CRUDManager.CrudCallback() {
                @Override
                public void onComplete(boolean success, String errorMessage) {
                    if (success) {
                        Log.d("writeNewAcount", "success");
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void setupSearchAndFilter() {
        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query, spinnerCategory.getSelectedItem().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText, spinnerCategory.getSelectedItem().toString());
                return true;
            }
        });

        // Filter functionality
        String[] categories = {"All", "Work", "Personal", "Social", "Banking", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.filter(searchView.getQuery().toString(), categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}