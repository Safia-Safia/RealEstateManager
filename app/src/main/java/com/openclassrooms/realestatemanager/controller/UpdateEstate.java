package com.openclassrooms.realestatemanager.controller;

import static com.openclassrooms.realestatemanager.controller.databinding.EstateDetailFragment.KEY_ESTATE_EDIT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateAdapter;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UpdateEstate extends AppCompatActivity {
    Uri imageUri;
    Estate estate;
    Spinner spinner;
    ImageButton addPictureBtn, cancelBtn;
    Button searchLocationBtn, saveBtn;
    private static final int PICK_IMAGE_REQUEST = 2;
    EditText textDescription, pictureDescription, price, surface, nbrOfRoom;
    private EstateAdapter propertyAdapter;
    private List<Picture> property_picture;
    EstateViewModel estateViewModel;

    UserViewModel userViewModel;
    CheckBox schoolCheckBox, parkCheckBox, parkingCheckBox, storeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_estate);
        setUpView();

        estate = (Estate) getIntent().getSerializableExtra(KEY_ESTATE_EDIT);
        getInfo();

        setUpSpinner();
        setSearchLocation();
        saveBtn.setOnClickListener(view -> {
            setInfo();
            EstateRepository.getInstance().updateEstate(estate, estate.getId())
                    .addOnSuccessListener(aVoid -> {
                        finish();
                    });
        });
    }

    private void getInfo() {
        estate.setPictures(property_picture);
        textDescription.setText(estate.getDescription());
        searchLocationBtn.setText(estate.getAddress());
        estate.setEstateType(estate.getEstateType());
        price.setText(Long.toString(estate.getPrice()));
        surface.setText(Long.toString(estate.getSurface()));
        nbrOfRoom.setText(Long.toString(estate.getSurface()));
        if (estate.getSchool()) {
            estate.setSchool(true);
            schoolCheckBox.setChecked(true);
        }
        if (estate.getStore()) {
            estate.setStore(true);
            storeCheckBox.setChecked(true);
        }
        if (estate.getPark()) {
            estate.setPark(true);
            parkCheckBox.setChecked(true);
        }
        if (estate.getPark()) {
            estate.setParking(true);
            parkingCheckBox.setChecked(true);
        }
    }
    private void setInfo() {
        estate.setDescription(textDescription.getText().toString());
        estate.setPictures(property_picture);
        //estate.setEstateType(spinner.getSelectedItem().toString());
        estate.setPrice(Integer.parseInt(price.getText().toString()));
        estate.setNumberOfRoom(Integer.parseInt(nbrOfRoom.getText().toString()));
        estate.setSurface(Integer.parseInt(surface.getText().toString()));
        estate.setDescription(textDescription.getText().toString());
        if (schoolCheckBox.isChecked()) {
            estate.setSchool(true);
        }
        if (storeCheckBox.isChecked()) {
            estate.setStore(true);
        }
        if (parkCheckBox.isChecked()) {
            estate.setPark(true);
        }
        if (parkingCheckBox.isChecked()) {
            estate.setParking(true);
        }
    }


    private void setUpView() {
        property_picture = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView_edit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        propertyAdapter = new EstateAdapter(property_picture);
        recyclerView.setAdapter(propertyAdapter);
        addPictureBtn = findViewById(R.id.addPictureBtn_edit);
        searchLocationBtn = findViewById(R.id.search_location_btn_edit);
        price = findViewById(R.id.edittext_house_price_edit);
        surface = findViewById(R.id.edittext_surface_edit);
        textDescription = findViewById(R.id.edit_description_edit);
        nbrOfRoom = findViewById(R.id.edittext_nbr_of_piece_edit);
        saveBtn = findViewById(R.id.save_btn_edit);
        schoolCheckBox = findViewById(R.id.school_checkBox_edit);
        storeCheckBox = findViewById(R.id.store_checkBox_edit);
        parkCheckBox = findViewById(R.id.park_checkBox_edit);
        parkingCheckBox = findViewById(R.id.parking_checkBox_edit);
        cancelBtn = findViewById(R.id.cancel_btn_edit);
        estate = new Estate();
    }

    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void setUpUserViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
    }

    private void setUpSpinner() {
        spinner = findViewById(R.id.type_of_property_spinner_edit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_property_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setSearchLocation() {
        searchLocationBtn.setOnClickListener(view -> {
            Places.initialize(getApplicationContext(), getText(R.string.maps_api_key).toString());
            Places.createClient(this);

            List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);

            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(this);
            startAutocomplete.launch(intent);

        });
    }

    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                        searchLocationBtn.setText(place.getName());
                        estate.setAddress(place.getAddress());
                        estate.setLatitude(place.getLatLng().latitude);
                        estate.setLongitude(place.getLatLng().longitude);

                        try {
                            List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                            Address address = addresses.get(0);
                            String city = address.getLocality();
                            estate.setCity(city);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );

}