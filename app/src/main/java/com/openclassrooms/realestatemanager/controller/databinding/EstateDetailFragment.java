package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.UpdateEstate;
import com.openclassrooms.realestatemanager.controller.placeholder.ViewPagerAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateDetailBinding;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.repository.UserRepository;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

/**
 * A fragment representing a single Estate detail screen.
 * This fragment is either contained in a {@link EstateListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class EstateDetailFragment extends Fragment {
    public static final String KEY_ESTATE = "ESTATE";
    public static final String KEY_ESTATE_EDIT = "ESTATE_EDIT";

    private Estate estate;
    ImageButton edit_estate;
    EstateViewModel estateViewModel;
    ImageView coverPicture, mapImage, userPicture, mailButton;
    TextView detail, address, price, nbrOfPiece, surface, type, school, store, park, parking,
            pointStore, pointSchool, pointParking, pointPark, sellerName, entryDate, soldDate;
    private FragmentEstateDetailBinding binding;

    SwitchCompat sold;
    ViewPager mViewPager;

    List<Picture> pictureList;
    ViewPagerAdapter mViewPagerAdapter;

    public EstateDetailFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEstateDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        rootView.findViewById(R.id.toolbar_layout);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_ESTATE)) {
            estate = (Estate) bundle.getSerializable(KEY_ESTATE);
        }
        setUpView();
        setUpEstateViewModel();
        Log.e(" estateDetailFragment", "1 " + edit_estate);
        if (estate != null) {
            Log.e(" estateDetailFragment", "2 " + estate.getEstateType());
            setUpEditButton();
            editEstateLayout();
            imageViewPager();
            setUpEntryDate();
            updateContent();

        } else {
            Toast.makeText(requireContext(),R.string.large_text,Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    public void imageViewPager() {
        mViewPager = (ViewPager) binding.viewPagerMain;
        pictureList = estate.getPictures();
        mViewPagerAdapter = new ViewPagerAdapter(this.requireContext(), pictureList);
        mViewPager.setAdapter(mViewPagerAdapter);
        CircleIndicator indicator = binding.getRoot().findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    public void setUpEditButton() {
        edit_estate.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), UpdateEstate.class);
            intent.putExtra(KEY_ESTATE_EDIT, estate);
            startActivity(intent);
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setUpView() {
        edit_estate = binding.getRoot().findViewById(R.id.updateEstate);
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
        soldDate = binding.getRoot().findViewById(R.id.soldDate_detail);
        sold = binding.getRoot().findViewById(R.id.switchButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (estate!= null){
            updateContent();
            Log.e(" estateDetailFragment", "3 ");

        }
    }

    private void updateContent() {
        String apiKey = getString(R.string.maps_api_key);

        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", customSymbols);
        String formattedNumber = decimalFormat.format(estate.getPrice());

        isFieldChecked(estate.getSchool(), school, pointSchool);
        isFieldChecked(estate.getPark(), park, pointPark);
        isFieldChecked(estate.getParking(), parking, pointParking);
        isFieldChecked(estate.getStore(), store, pointStore);

        surface.setText(String.format("%s m²", estate.getSurface()));
        price.setText(String.format("%s $", formattedNumber));
        nbrOfPiece.setText(String.valueOf(estate.getNumberOfRoom()));
        detail.setText(estate.getDescription());
        address.setText(estate.getAddress());
        type.setText(estate.getEstateType());
        entryDate.setText(estate.getEntryDate());
        sellerName.setText(estate.getUser().getUsername());

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
        soldDate.setText(estate.getSoldDate());
    }

    public void setUpMailButton() {
        mailButton.setOnClickListener(view -> {
            String userEmail = estate.getUser().getEmail();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + userEmail));
            startActivity(emailIntent);
        });
    }

    private void isFieldChecked(boolean checkbox, TextView point, TextView textView) {
        if (!checkbox) {
            textView.setTextColor(getResources().getColor(R.color.grey));
            point.setTextColor(getResources().getColor(R.color.grey));
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
        sold.setChecked(estate.getSoldDate() != null);
        ConstraintLayout editOptionsLayout = binding.getRoot().findViewById(R.id.soldDateLayout_detail);
        if (estate.getSoldDate() != null){
            editOptionsLayout.setVisibility(View.VISIBLE);
        }else {
            editOptionsLayout.setVisibility(View.GONE);
        }
        sold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                estate.setSoldDate(format);
            } else {
                estate.setSoldDate(null);
            }
            estateViewModel.updateEstate(estate, estate.getId()).observe(this.requireActivity(), aBoolean -> {
                Intent intent = new Intent(this.requireContext(), EstateHostActivity.class);
                startActivity(intent);
                getActivity().finish();
            });
        });
    }


    public void editEstateLayout() {
        ConstraintLayout editOptionsLayout;
        editOptionsLayout = binding.getRoot().findViewById(R.id.layout_switch);
        if (Objects.equals(estate.getUser().getUid(),
                Objects.requireNonNull(UserRepository.getInstance().getCurrentUser()).getUid())) {
            editOptionsLayout.setVisibility(View.VISIBLE);
        } else {
            editOptionsLayout.setVisibility(View.GONE);
        }
    }

    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this.requireContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

}
