package com.openclassrooms.realestatemanager.controller;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.placeholder.PropertyAdapter;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEstate extends AppCompatActivity {

    Estate estate;
    Spinner spinner;
    private Calendar currentDate;
    ImageButton addPictureBtn;
    Button searchLocationBtn;

    Boolean isDatePickerOn = false;
    ImageButton entryDateButton, soldDateButton;
    SimpleDateFormat formatter;
    Date date;
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText textDescription, pictureDescription, price, surface, nbrOfPiece;

    SwitchCompat availableSwitchBtn;
    TextView estateManagerName, entryDateText, soldDateText;
    private PropertyAdapter propertyAdapter;
    private List<Uri> property_picture;
     UserRepository userViewModel= UserRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_property);
        setUpView();
        spinner();
        openPicturePicker();
        setSearchLocation();
        setUpEntryDate();
        setUpSoldDate();
        setEstate();

    }


    private void setUpView() {
        property_picture = new ArrayList<>();
        currentDate = Calendar.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        propertyAdapter = new PropertyAdapter(property_picture);
        recyclerView.setAdapter(propertyAdapter);
        addPictureBtn = findViewById(R.id.addPictureBtn);
        availableSwitchBtn = findViewById(R.id.switch_btn);
        searchLocationBtn = findViewById(R.id.search_location_btn);
        estateManagerName = findViewById(R.id.texteView_estate_manager);
        price = findViewById(R.id.edittext_house_price);
        surface = findViewById(R.id.edittext_surface);
        textDescription = findViewById(R.id.edit_description);
        nbrOfPiece = findViewById(R.id.edittext_nbr_of_piece);
        entryDateText = findViewById(R.id.textview_date_entry);
        soldDateText = findViewById(R.id.textview_date_sold);
        entryDateButton = findViewById(R.id.datepicker_entry);
        soldDateButton = findViewById(R.id.datepicker_sold);
        estate = new Estate();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create your advert");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        availableSwitchBtn.setTextColor(getResources().getColor(R.color.quantum_white_text));
    }

    private void setEstate() {
        estate.setEstateType(spinner.getSelectedItem().toString());
        estate.setPrice(price.getText().toString());
        estate.setNumberOfRoom(nbrOfPiece.getText().toString());
        estate.setSurface(surface.getText().toString());
        estate.setDescription(textDescription.getText().toString());
        estate.setPicturesUri(property_picture.toString());
        estate.setAddress(searchLocationBtn.getText().toString());
        estate.setEntryDate(entryDateText.getText().toString());
        estateManagerName.setText(String.format("%s%s", getString(R.string.estateManagerName), userViewModel.getCurrentUser().getDisplayName()));
    }

    private void spinner() {
        spinner = findViewById(R.id.type_of_property_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_property_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Choose the type");
        spinner.setAdapter(adapter);
    }

    private void openPicturePicker() {
        addPictureBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"), PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            property_picture.add(imageUri);
            propertyAdapter.notifyDataSetChanged();
            showAlertDialog();
        }
    }

    private void setSearchLocation() {
        searchLocationBtn.setOnClickListener(view -> {
            Places.initialize(getApplicationContext(), getText(R.string.maps_api_key).toString());
            Places.createClient(this);

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

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
                        place.getLatLng(); //afficher sur une carte
                        searchLocationBtn.setText(place.getName());
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i(TAG, "User canceled autocomplete");
                }
            });

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        alertDialog.setView(customLayout);
        pictureDescription = customLayout.findViewById(R.id.edittext_alert_dialog_picture);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PropertyAdapter.PropertyViewHolder.pictureDescription.setText(pictureDescription.getText());
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void setUpEntryDate() {
        entryDateButton.setOnClickListener(view -> {
            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH);
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker;
            formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

            mDatePicker = new DatePickerDialog(AddEstate.this,
                    (DatePickerDialog.OnDateSetListener) (datePicker, year1, month1, dayOfMonth) -> {
                        currentDate.set(Calendar.YEAR, year1);
                        currentDate.set(Calendar.MONTH, month1);
                        currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        date = currentDate.getTime();
                        String format = formatter.format(date.getTime());
                        entryDateText.setText(format);
                    }, year, month, day);
            mDatePicker.show();
        });
    }

    public void setUpSoldDate() {
        soldDateButton.setOnClickListener(view -> {
            if (availableSwitchBtn.isChecked()) {
                soldDateButton.setClickable(true);
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
                mDatePicker = new DatePickerDialog(AddEstate.this,
                        (DatePickerDialog.OnDateSetListener) (datePicker, year1, month1, dayOfMonth) -> {
                            currentDate.set(Calendar.YEAR, year1);
                            currentDate.set(Calendar.MONTH, month1);
                            currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            date = currentDate.getTime();
                            String format = formatter.format(date.getTime());
                            soldDateText.setText(format);
                        }, year, month, day);
                mDatePicker.show();
            } else {
                View parentLayout = findViewById(R.id.add_property);
                Snackbar.make(parentLayout, "Maison encore disponible.", Snackbar.LENGTH_SHORT).show();
            }

        });
    }
}

