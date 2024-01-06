package com.openclassrooms.realestatemanager.controller;

import static com.openclassrooms.realestatemanager.controller.databinding.EstateDetailFragment.KEY_ESTATE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.databinding.EstateDetailFragment;
import com.openclassrooms.realestatemanager.model.Estate;

public class DetailActivity extends AppCompatActivity {
    private Estate estate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        EstateDetailFragment fragment = new EstateDetailFragment();

        estate = (Estate) getIntent().getSerializableExtra(KEY_ESTATE);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EstateDetailFragment.KEY_ESTATE, estate);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}