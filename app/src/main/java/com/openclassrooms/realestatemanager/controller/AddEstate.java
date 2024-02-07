package com.openclassrooms.realestatemanager.controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.databinding.EstateHostActivity;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateAdapter;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateDataViewModel;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddEstate extends AppCompatActivity {
    Uri imageUri;
    Estate estate;
    Spinner spinner;
    ImageButton addPictureBtn, cancelBtn;
    Button searchLocationBtn, saveBtn;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL";
    public static final String NOTIFICATION_TAG = "NOTIFICATION";
    public static final int NOTIFICATION_ID = 0;
    EditText textDescription, pictureDescription, price, surface, nbrOfRoom;
    private EstateAdapter propertyAdapter;
    private List<Picture> property_picture;
    EstateViewModel estateViewModel;
    UserViewModel userViewModel;

    EstateDataViewModel estateDataViewModel;
    CheckBox schoolCheckBox, parkCheckBox, parkingCheckBox, storeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_estate);
        setUpView();
        spinner();
        openPicturePicker();
        setSearchLocation();
        setUpEstateViewModel();
        setUpUserViewModel();
        configureViewModel();
        setUpEntryDate();
        saveEstate();
        setCancelBtn();

    }

    private void setUpView() {
        property_picture = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        propertyAdapter = new EstateAdapter(property_picture);
        recyclerView.setAdapter(propertyAdapter);
        addPictureBtn = findViewById(R.id.addPictureBtn);
        searchLocationBtn = findViewById(R.id.search_location_btn);
        price = findViewById(R.id.edittext_house_price);
        surface = findViewById(R.id.edittext_surface);
        textDescription = findViewById(R.id.edit_description);
        nbrOfRoom = findViewById(R.id.edittext_nbr_of_piece);
        saveBtn = findViewById(R.id.save_btn);
        schoolCheckBox = findViewById(R.id.school_checkBox);
        storeCheckBox = findViewById(R.id.store_checkBox);
        parkCheckBox = findViewById(R.id.park_checkBox);
        parkingCheckBox = findViewById(R.id.parking_checkBox);
        cancelBtn = findViewById(R.id.cancel_btn);
        estate = new Estate();

    }

    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = new ViewModelProvider(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void setUpUserViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }

    public void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateDataViewModel = new ViewModelProvider(this, viewModelFactory).get(EstateDataViewModel.class);
    }

    private void spinner() {
        spinner = findViewById(R.id.type_of_property_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_property_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void openPicturePicker() {
        addPictureBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpg");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
        });
    }

    public void setCancelBtn() {
        cancelBtn.setOnClickListener(view -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            showAlertDialog();

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


    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        pictureDescription = customLayout.findViewById(R.id.edittext_alert_dialog_picture);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(R.string.describe_picture);
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

    public boolean checkValid() {
        boolean isFieldEmpty =
                surface.getText().toString().isEmpty() ||
                        price.getText().toString().isEmpty() ||
                        nbrOfRoom.getText().toString().isEmpty() ||
                        textDescription.getText().toString().isEmpty() ||
                        property_picture.size() == 0 ||
                        (estate.getAddress() != null && estate.getAddress().isEmpty()) ||
                        spinner.getSelectedItemPosition() == 0;
        if (isFieldEmpty) {
            Snackbar.make(findViewById(android.R.id.content), R.string.check_field, Snackbar.LENGTH_LONG).show();
            return false;
        } else {
            return true;

        }
    }

    public void setUpEntryDate() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter;
        Date date;

        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, month);
        currentDate.set(Calendar.DAY_OF_MONTH, day);
        date = currentDate.getTime();
        String format = formatter.format(date.getTime());
        estate.setEntryDate(format);
    }

    public void saveEstate() {

        saveBtn.setOnClickListener(view -> {
            if (!checkValid()) {
                return;
            }
            estate.setPictures(property_picture);
            estate.setEstateType(spinner.getSelectedItem().toString());
            estate.setPrice(Integer.parseInt(price.getText().toString()));
            estate.setNumberOfRoom(Integer.parseInt(nbrOfRoom.getText().toString()));
            estate.setSurface(Integer.parseInt(surface.getText().toString()));
            estate.setDescription(textDescription.getText().toString());
            User newUser = new User();
            newUser.setUid(userViewModel.getCurrentUser().getUid());
            newUser.setEmail(userViewModel.getCurrentUser().getEmail());
            newUser.setUsername(userViewModel.getCurrentUser().getDisplayName());

            String urlPicture;
            if (userViewModel.getCurrentUser().getPhotoUrl() != null) {
                urlPicture = String.valueOf(userViewModel.getCurrentUser().getPhotoUrl());
            } else {
                urlPicture = getRandomImageUrl();
            }

            newUser.setUrlPicture(urlPicture);
            estate.setUser(newUser);
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

            createEstate(estate);
            estateViewModel.createEstate(estate).observe(this, aBoolean -> finish());

        });

    }

    private void createEstate(Estate estate) {
        estateDataViewModel.createEstate(estate);
        sendNotification();
        finish();
    }

    private String getRandomImageUrl() {
        String[] images = {
                "https://i.pravatar.cc/150?u=a042581f4e29026704a",
                "https://i.pravatar.cc/150?u=a042581f4e29026704b",
                "https://i.pravatar.cc/150?u=a042581f4e29026704c",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d"
        };

        int randomIndex = new Random().nextInt(images.length);
        return images[randomIndex];
    }

    private void sendNotification() {
        int smallIconResId = R.drawable.thumb_icon;
        String channelId = NOTIFICATION_CHANNEL;
        Intent intent = new Intent(this, EstateHostActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(getString(R.string.title_estate_saved))
                        .setContentText(getString(R.string.estate_saved_notification))
                        .setSmallIcon(smallIconResId)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Firebase Messages";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }


}

