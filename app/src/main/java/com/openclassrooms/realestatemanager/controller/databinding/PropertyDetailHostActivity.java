package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.CreateProperty;
import com.openclassrooms.realestatemanager.databinding.ActivityPropertyDetailBinding;

public class PropertyDetailHostActivity extends AppCompatActivity {
    FloatingActionButton mCreatePropertyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPropertyDetailBinding binding = ActivityPropertyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_property_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        mCreatePropertyBtn = findViewById(R.id.button_create_property);
        mCreatePropertyBtn.setOnClickListener(view -> {
            Intent createPropertyIntent = new Intent(PropertyDetailHostActivity.this, CreateProperty.class);
            startActivity(createPropertyIntent);
            finish();
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_property_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}