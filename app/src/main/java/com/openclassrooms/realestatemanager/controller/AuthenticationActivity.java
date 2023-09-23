package com.openclassrooms.realestatemanager.controller;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.databinding.EstateHostActivity;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

import java.util.Collections;
import java.util.List;

public class AuthenticationActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private Button emailButton, googleButton;
    ProgressBar progressBar;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setUpView();
        setupListeners();
        configureViewModel();
        isUserLogged();
    }

    private void setUpView() {
        googleButton = findViewById(R.id.login_button_google);
        emailButton = findViewById(R.id.login_button_email);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        progressBar.setVisibility(View.INVISIBLE);

        emailButton.setOnClickListener(view ->
                signInBuilder(Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build()))
        );
        googleButton.setOnClickListener(view ->
                signInBuilder(Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
        );
    }

    public void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
    }

    private void signInBuilder(List<AuthUI.IdpConfig> providers) {
        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                userViewModel.createUser();
                showToast("Connecté");
                startPropertyListFragment();
                progressBar.setVisibility(View.INVISIBLE);
            } else if (response == null) {
                showToast("Pas connecté");
            }
        }
    }

    public void isUserLogged() {
        if (userViewModel.isCurrentUserLogged()) {
            startPropertyListFragment();
        } else {
            startActivity(this.getIntent());
        }
    }

    private void startPropertyListFragment() {
        Intent homeActivityIntent = new Intent(this, EstateHostActivity.class);
        startActivity(homeActivityIntent);
        finish();
    }
}

