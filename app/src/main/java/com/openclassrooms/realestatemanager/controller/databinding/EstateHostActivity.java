package com.openclassrooms.realestatemanager.controller.databinding;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityEstateDetailBinding;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

public class EstateHostActivity extends AppCompatActivity {

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityEstateDetailBinding binding = ActivityEstateDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_property_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        setUpView();
        setUserViewModel();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setUpView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
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