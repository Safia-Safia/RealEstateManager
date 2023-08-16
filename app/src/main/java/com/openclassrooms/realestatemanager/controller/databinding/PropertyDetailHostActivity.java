package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.AddEstate;
import com.openclassrooms.realestatemanager.databinding.ActivityPropertyDetailBinding;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

public class PropertyDetailHostActivity extends AppCompatActivity {
    FloatingActionButton mCreatePropertyBtn;

    UserViewModel userViewModel;
    ImageButton filterBtn, signOutBtn;

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


        setUpView();
        setUpAddProperty();
        setUserViewModel();
        setLogOutBtn();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setUpView() {
        mCreatePropertyBtn = findViewById(R.id.button_create_property);
        signOutBtn = findViewById(R.id.sign_out_btn);
        filterBtn = findViewById(R.id.filter_button);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void setUpAddProperty() {
        mCreatePropertyBtn.setOnClickListener(view -> {
            Intent addEstateIntent = new Intent(this, AddEstate.class);
            startActivity(addEstateIntent);
        });
    }

    private void setLogOutBtn() {
        signOutBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setMessage("Souhaitez vous déconnecté ?")
                    .setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userViewModel.signOut(PropertyDetailHostActivity.this);
                            finish();
                        }
                    })
                    .create()
                    .show();


        });
    }

    private void setUserViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_property_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}