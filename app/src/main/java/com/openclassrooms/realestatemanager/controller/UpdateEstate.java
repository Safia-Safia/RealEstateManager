package com.openclassrooms.realestatemanager.controller;

import static com.openclassrooms.realestatemanager.controller.databinding.EstateDetailFragment.KEY_ESTATE_EDIT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.openclassrooms.realestatemanager.controller.databinding.EstateHostActivity;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateAdapter;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;

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
    CheckBox schoolCheckBox, parkCheckBox, parkingCheckBox, storeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_estate);
        setUpView();
        estate = (Estate) getIntent().getSerializableExtra(KEY_ESTATE_EDIT);
        getInfo();
        setUpSpinner();
        openPicturePicker();
        setSearchLocation();
        setUpEstateViewModel();
        setCancelBtn();

        saveBtn.setOnClickListener(view -> {
            setInfo();
            estateViewModel.updateEstate(estate, estate.getId())
                    .observe(this,aBoolean -> {
                        Intent intent = new Intent(this,  EstateHostActivity.class);
                        startActivity(intent);
                        finish();
                    });
        });
    }

    private void getInfo() {
        property_picture.addAll(estate.getPictures());
        textDescription.setText(estate.getDescription());
        searchLocationBtn.setText(estate.getAddress());
        estate.setEstateType(estate.getEstateType());
        price.setText(Long.toString(estate.getPrice()));
        surface.setText(Long.toString(estate.getSurface()));
        nbrOfRoom.setText(Long.toString(estate.getSurface()));
        if (estate.getSchool()) {
            schoolCheckBox.setChecked(true);
        }
        if (estate.getStore()) {
            storeCheckBox.setChecked(true);
        }
        if (estate.getPark()) {
            parkCheckBox.setChecked(true);
        }
        if (estate.getParking()) {
            parkingCheckBox.setChecked(true);
        }
    }

    private void setInfo() {

        estate.setDescription(textDescription.getText().toString());
        estate.setPictures(property_picture);
        estate.setEstateType(spinner.getSelectedItem().toString());
        estate.setPrice(Integer.parseInt(price.getText().toString()));
        estate.setNumberOfRoom(Integer.parseInt(nbrOfRoom.getText().toString()));
        estate.setSurface(Integer.parseInt(surface.getText().toString()));
        estate.setDescription(textDescription.getText().toString());
        estate.setEstateType(spinner.getSelectedItem().toString());

        estate.setSchool(schoolCheckBox.isChecked());
        estate.setStore(storeCheckBox.isChecked());
        estate.setParking(parkingCheckBox.isChecked());
        estate.setPark(parkCheckBox.isChecked());
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
        this.estateViewModel = new ViewModelProvider(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void setUpSpinner() {
        spinner = findViewById(R.id.type_of_property_spinner_edit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_property_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for (int i=0; i<adapter.getCount(); i++){
            if (adapter.getItem(i).toString().equals(estate.getEstateType())){
                spinner.setSelection(i);
            }
        }
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

    private void openPicturePicker() {
        addPictureBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpg");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            showAlertDialog();

        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        pictureDescription = customLayout.findViewById(R.id.edittext_alert_dialog_picture);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(getString(R.string.describe_picture));
        alertDialog.setPositiveButton("OK", (dialog, which) -> {
            Picture picture = new Picture();
            picture.setImageUri(imageUri);
            picture.setDescription(pictureDescription.getText().toString());
            property_picture.add(picture);
            propertyAdapter.notifyDataSetChanged();
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void setCancelBtn() {
        cancelBtn.setOnClickListener(view -> finish());
    }


}