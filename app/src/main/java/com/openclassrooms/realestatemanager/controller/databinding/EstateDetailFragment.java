package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.placeholder.ViewPagerAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateDetailBinding;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

/**
 * A fragment representing a single Estate detail screen.
 * This fragment is either contained in a {@link EstateListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class EstateDetailFragment extends Fragment {
    public static final String KEY_ESTATE = "ESTATE";
    private CircleIndicator indicator;
    private Estate estate;
    ImageView coverPicture, mapImage, userPicture, mailButton;
    TextView detail, address, price, nbrOfPiece, surface, type, school, store, park, parking,
            pointStore,pointSchool, pointParking, pointPark, sellerName, entryDate;
    private CollapsingToolbarLayout mToolbarLayout;
    private FragmentEstateDetailBinding binding;

    ViewPager mViewPager;

    // images array
    List<Picture> pictureList;

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    public EstateDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEstateDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_ESTATE)) {
            estate = (Estate) bundle.getSerializable(KEY_ESTATE);
        }

        mViewPager = (ViewPager)binding.viewPagerMain;

        pictureList = estate.getPictures();
        mViewPagerAdapter = new ViewPagerAdapter(this.requireContext(), pictureList);
        mViewPager.setAdapter(mViewPagerAdapter);

        setUpView();
        updateContent();

            indicator = binding.getRoot().findViewById(R.id.indicator);
            indicator.setViewPager(mViewPager);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setUpView(){
        coverPicture = binding.getRoot().findViewById(R.id.collapsing_image);
        mapImage = binding.getRoot().findViewById(R.id.map_snapshot);
        surface = binding.surfaceDetailText;
        price = binding.priceDetailTextView;
        nbrOfPiece = binding.houseNbrOfPieceTextDetail;
        detail = binding.getRoot().findViewById(R.id.description_detail);
        address = binding.getRoot().findViewById(R.id.address_detail);
        type = binding.getRoot().findViewById(R.id.type_detail_text);
        school = binding.getRoot().findViewById(R.id.textView_school);
        store = binding.getRoot().findViewById(R.id.textView_store);
        park = binding.getRoot().findViewById(R.id.textView_park);
        parking = binding.getRoot().findViewById(R.id.textView_parking);
        pointSchool = binding.getRoot().findViewById(R.id.point_school);
        pointStore = binding.getRoot().findViewById(R.id.point_store);
        pointPark = binding.getRoot().findViewById(R.id.point_park);
        pointParking = binding.getRoot().findViewById(R.id.point_parking);
        userPicture = binding.getRoot().findViewById(R.id.sellerPic);
        sellerName = binding.getRoot().findViewById(R.id.sellerName);
        mailButton = binding.getRoot().findViewById(R.id.mail_imageButton);
        entryDate = binding.getRoot().findViewById(R.id.entryDate_detail);
    }
    private void updateContent() {
        String apiKey = getString(R.string.maps_api_key);

        isFieldChecked(estate.getSchool(), school, pointSchool);
        isFieldChecked(estate.getPark(), park, pointPark);
        isFieldChecked(estate.getParking(), parking, pointParking);
        isFieldChecked(estate.getStore(), store, pointStore);

        surface.setText(estate.getSurface());
        price.setText(String.valueOf(estate.getPrice()));
        nbrOfPiece.setText(estate.getNumberOfRoom());
        detail.setText(estate.getDescription());
        address.setText(estate.getAddress());
        type.setText(estate.getEstateType());
        entryDate.setText(estate.getEntryDate());

        Glide.with(this).load(estate.getCoverPictureUrl()).into(coverPicture);
        double latitude = estate.getLatitude();
        double longitude = estate.getLongitude();
        int zoom = 17;

        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + latitude + "," + longitude
                + "&zoom=" + zoom
                + "&size=1400x200"
                + "&markers=color:red|" + latitude + "," + longitude
                + "&key=" + apiKey;

        Glide.with(this).load(imageUrl).into(mapImage);
        setUpMailButton();

        Glide.with(this).load(estate.getUser().getUrlPicture()).circleCrop().into(userPicture);
    }

    public void setUpMailButton() {
        mailButton.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(estate.getUser().email))));
    }

    private void isFieldChecked(boolean checkbox, TextView point, TextView textView) {
        if (!checkbox) {
            textView.setTextColor(getResources().getColor(R.color.grey));
            point.setTextColor(getResources().getColor(R.color.grey));
        }
    }
}
