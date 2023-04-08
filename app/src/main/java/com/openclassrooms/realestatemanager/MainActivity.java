package com.openclassrooms.realestatemanager;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private Button emailButton, googleButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void signInBuilder(List<AuthUI.IdpConfig> providers) {
        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    // Show Toast with a message
    private void showToast(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }

    // Method that handles response after SignIn Activity close
  /*  private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                viewModel.createUser();
                showToast(String.valueOf(getString(R.string.connected)));
                startHomeActivity();
                progressBar.setVisibility(View.INVISIBLE);
            } else if (response == null) {
                showToast(String.valueOf(getString(R.string.notConnected)));
            }
        }
    }*/


}

