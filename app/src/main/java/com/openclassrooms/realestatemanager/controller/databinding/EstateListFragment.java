package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.AddEstate;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateListAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateListBinding;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Properties. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link EstateDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EstateListFragment extends Fragment {
    FragmentEstateListBinding binding;
    List<String> selectedFilters = new ArrayList<>();

    EstateViewModel estateViewModel;
    RecyclerView recyclerView;
    EstateListAdapter adapter;
    View v;
    SeekBar seekBar;
    TextView minPriceTextView, maxPriceTextView;
    ConstraintLayout filterOptionsLayout;
    FloatingActionButton fabAddEstates;
    ImageButton filterBtn, signOutBtn;
    Button noFilterBtn, schoolBtn, storeBtn, parkingBtn, parkBtn, moreThan3PictureBtn, sinceAWeekBtn, soldBtn;
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEstateListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        v = view.findViewById(R.id.property_detail_nav_container);
        setUpEstateViewModel();
        getAllEstates();

        setUpView();
        setUpFilterButton();
        setUpSeekBar();
        setLogOutBtn();
        setUpAddEstate();
        initListOnButtonClick(noFilterBtn, "noFilter");
        initListOnButtonClick(schoolBtn, "school");
        initListOnButtonClick(storeBtn, "store");
        initListOnButtonClick(soldBtn, "sold");
        initListOnButtonClick(sinceAWeekBtn, "sinceAWeek");
        initListOnButtonClick(parkingBtn, "parking");
        initListOnButtonClick(parkBtn, "park");
        initListOnButtonClick(moreThan3PictureBtn, "picture");


    }

    private void setUpView() {
        recyclerView = binding.estateListRecyclerview;
        seekBar = binding.getRoot().findViewById(R.id.seekBar);
        minPriceTextView = binding.getRoot().findViewById(R.id.minPriceTextView);
        maxPriceTextView = binding.getRoot().findViewById(R.id.maxPriceTextView);
        fabAddEstates = binding.getRoot().findViewById(R.id.button_create_property);
        signOutBtn = binding.getRoot().findViewById(R.id.sign_out_btn);
        filterBtn = binding.getRoot().findViewById(R.id.filter_button);
        filterOptionsLayout = binding.includeFilter.getRoot();
        filterOptionsLayout.setVisibility(View.GONE);
        //FILTER'S BUTTON
        noFilterBtn = binding.getRoot().findViewById(R.id.noFilter);
        schoolBtn = binding.getRoot().findViewById(R.id.school);
        storeBtn = binding.getRoot().findViewById(R.id.store);
        parkBtn = binding.getRoot().findViewById(R.id.park);
        parkingBtn = binding.getRoot().findViewById(R.id.parking);
        sinceAWeekBtn = binding.getRoot().findViewById(R.id.sinceAweek);
        moreThan3PictureBtn = binding.getRoot().findViewById(R.id.plus3pictures);
        soldBtn = binding.getRoot().findViewById(R.id.sold);


    }

    public void changeButtonColor(Button button) {
        boolean clicked = button.isSelected();
        if (button.getId() == R.id.noFilter) {
            resetOtherButtonsState(button);
        } else {
            resetButtonState(noFilterBtn);
        }

        if (clicked) {
            button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            button.setTextColor(getResources().getColor(R.color.quantum_white_text));
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.filter_boder_blue));
            button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void resetButtonState(Button button) {
        button.setSelected(false);
        button.setBackground(getResources().getDrawable(R.drawable.filter_boder_blue));
        button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void resetOtherButtonsState(Button clickedButton) {
        Button[] allButtons = {noFilterBtn, schoolBtn, storeBtn, parkBtn, parkingBtn, sinceAWeekBtn, moreThan3PictureBtn, soldBtn};
        for (Button button : allButtons) {
            if (button != clickedButton) {
                button.setSelected(false);
                button.setBackground(getResources().getDrawable(R.drawable.filter_boder_blue));
                button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }

    public void initListOnButtonClick(Button button, String filterCriteria) {
        button.setOnClickListener(view -> {
            boolean isButtonClicked = !button.isSelected();
            button.setSelected(isButtonClicked);
            if (isButtonClicked) {
                selectedFilters.add(filterCriteria);
            } else {
                selectedFilters.remove(filterCriteria);
            }
            applyFilters();
            changeButtonColor(button);
        });

    }

    private void applyFilters() {
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), estates -> {
            List<Estate> filteredEstates = new ArrayList<>();

            for (Estate estate : estates) {
                boolean isFiltered = true;

                for (String filterCriteria : selectedFilters) {
                    switch (filterCriteria) {
                        case "noFilter":
                            getAllEstates();
                            break;
                        case "school":
                            isFiltered = estate.getSchool();
                            break;
                        case "store":
                            isFiltered = estate.getStore();
                            break;
                        case "park":
                            isFiltered = estate.getPark();
                            break;
                        case "parking":
                            isFiltered = estate.getParking();
                            break;
                        case "picture":
                            isFiltered = estate.getPictures().size() >= 3;
                            break;
                    }
                }

                if (isFiltered) {
                    filteredEstates.add(estate);
                }
            }
            setupRecyclerView(filteredEstates);
        });
    }

    public void setUpFilterButton() {
        filterBtn.setOnClickListener(new View.OnClickListener() {
            boolean visibility = false;

            @Override
            public void onClick(View view) {
                visibility = !visibility;
                if (visibility) {
                    filterOptionsLayout.setVisibility(View.VISIBLE);
                } else {
                    filterOptionsLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    public void setUpSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minPrice = 200000;
                int maxPrice = progress;
                minPriceTextView.setText("Min: " + minPrice);
                maxPriceTextView.setText("Max: " + maxPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int maxPrice = seekBar.getProgress();
                filterByPrice(maxPrice);
            }
        });
    }


    public void filterByPrice(int maxPrice) {
        List<Estate> filteredEstates = new ArrayList<>();
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), estates -> {

            for (Estate estate : estates) {
                boolean isFiltered = maxPrice >= estate.getPrice();
                Log.e("maxPrice", maxPrice+"");
                Log.e("Price", estate.getPrice()+"");
                if (isFiltered) {
                    filteredEstates.add(estate);
                }
                Log.e("isfiltered", isFiltered+"");

            }
            Log.e("size", filteredEstates.size()+"");
            setupRecyclerView(filteredEstates);
        });
    }

    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this.requireActivity());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getAllEstates() {
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), this::setupRecyclerView);
    }

    void setupRecyclerView(List<Estate> estates) {
        adapter = new EstateListAdapter(estates, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpAddEstate() {
        fabAddEstates.setOnClickListener(view -> {
            Intent addEstateIntent = new Intent(this.requireContext(), AddEstate.class);
            startActivity(addEstateIntent);
        });
    }

    private void setLogOutBtn() {
        signOutBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(this.requireContext())
                    .setMessage("Souhaitez vous déconnecté ?")
                    .setCancelable(true)
                    .setPositiveButton("Oui", (dialog, which) -> {
                        ((EstateHostActivity) EstateListFragment.this.requireActivity()).userViewModel.signOut(EstateListFragment.this.requireContext());
                        EstateListFragment.this.requireActivity().finish();
                    })
                    .create()
                    .show();


        });
    }

    //Update list when an estate is added
    @Override
    public void onResume() {
        super.onResume();
        getAllEstates();
    }
}