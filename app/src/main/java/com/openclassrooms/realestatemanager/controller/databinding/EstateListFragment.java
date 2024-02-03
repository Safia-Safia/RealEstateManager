package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jem.rubberpicker.RubberRangePicker;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.AddEstate;
import com.openclassrooms.realestatemanager.controller.AuthenticationActivity;
import com.openclassrooms.realestatemanager.controller.MapsActivity;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateListAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateListBinding;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
    private TextView priceMaxValue, priceMinValue, surfaceMaxValue, surfaceMinValue;
    SearchView searchView;
    EstateViewModel estateViewModel;
    RecyclerView recyclerView;
    EstateListAdapter adapter;
    View v;
    ConstraintLayout filterOptionsLayout;
    FloatingActionButton fabAddEstates;
    ImageButton filterBtn, signOutBtn, mapsButton;
    Button noFilterBtn, schoolBtn, storeBtn, parkingBtn, parkBtn, moreThan3PictureBtn, lastWeekBtn, soldBtn;
    private RubberRangePicker priceRangeBar, surfaceRangeBar;

    Spinner spinner;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEstateListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        v = view.findViewById(R.id.property_detail_nav_container);
        setUpView();
        setUpEstateViewModel();
        setUpFilterButton();
        setLogOutBtn();
        setUpAddEstate();
        setUpMaps();
        initListOnButtonClick(noFilterBtn, "noFilter");
        initListOnButtonClick(schoolBtn, "school");
        initListOnButtonClick(storeBtn, "store");
        initListOnButtonClick(soldBtn, "sold");
        initListOnButtonClick(parkingBtn, "parking");
        initListOnButtonClick(parkBtn, "park");
        initListOnButtonClick(moreThan3PictureBtn, "picture");
        initListOnButtonClick(lastWeekBtn, "lastWeek");
        initListOnButtonClick(soldBtn, "sold");

        initSearchBar();
        getAllEstates();
    }


    public static EstateListFragment newInstance() {
        return (new EstateListFragment());
    }

    private void initSearchBar() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText.toLowerCase();
                List<Estate> filteredEstates = new ArrayList<>();
                estateViewModel.getEstates().observe(getViewLifecycleOwner(), estates -> {

                    for (Estate estate : estates) {
                        if (estate.getAddress().toLowerCase().contains(searchStr)) {
                            filteredEstates.add(estate);
                        }
                    }
                    setupRecyclerView(filteredEstates);
                });
                return true;
            }

        });
    }

    private void setUpView() {
        recyclerView = binding.estateListRecyclerview;
        fabAddEstates = binding.getRoot().findViewById(R.id.button_create_property);
        mapsButton = binding.getRoot().findViewById(R.id.button_maps);

        signOutBtn = binding.getRoot().findViewById(R.id.sign_out_btn);
        filterBtn = binding.getRoot().findViewById(R.id.filter_button);
        filterOptionsLayout = binding.includeFilter.getRoot();
        filterOptionsLayout.setVisibility(View.GONE);
        searchView = binding.getRoot().findViewById(R.id.search_bar);
        //FILTER'S BUTTON
        noFilterBtn = binding.getRoot().findViewById(R.id.noFilter);
        schoolBtn = binding.getRoot().findViewById(R.id.school);
        storeBtn = binding.getRoot().findViewById(R.id.store);
        parkBtn = binding.getRoot().findViewById(R.id.park);
        parkingBtn = binding.getRoot().findViewById(R.id.parking);
        lastWeekBtn = binding.getRoot().findViewById(R.id.sinceAweek);
        moreThan3PictureBtn = binding.getRoot().findViewById(R.id.plus3pictures);
        soldBtn = binding.getRoot().findViewById(R.id.sold);
        //RANGEBAR
        //          --PRICE
        priceRangeBar = new RubberRangePicker(requireContext());
        priceRangeBar = binding.getRoot().findViewById(R.id.rangeBarPrice);
        priceMaxValue = binding.getRoot().findViewById(R.id.maxValue_price);
        priceMinValue = binding.getRoot().findViewById(R.id.minValue_price);
        //          --SURFACE
        surfaceRangeBar = new RubberRangePicker(requireContext());
        surfaceRangeBar = binding.getRoot().findViewById(R.id.rangeBarSurface);
        surfaceMaxValue = binding.getRoot().findViewById(R.id.maxValue_surface);
        surfaceMinValue = binding.getRoot().findViewById(R.id.minValue_surface);

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
        Button[] allButtons = {noFilterBtn, schoolBtn, storeBtn, parkBtn, parkingBtn, lastWeekBtn, moreThan3PictureBtn, soldBtn};
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
                if ("noFilter".equals(filterCriteria)) {
                    selectedFilters.clear();
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                    priceRangeBar.setCurrentStartValue(priceRangeBar.getMin());
                    priceRangeBar.setCurrentEndValue(priceRangeBar.getMax());
                    surfaceRangeBar.setCurrentStartValue(surfaceRangeBar.getMin());
                    surfaceRangeBar.setCurrentEndValue(surfaceRangeBar.getMax());
                    spinner.setSelection(0);
                }
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
                        case "school":
                            isFiltered = isFiltered && estate.getSchool();
                            break;
                        case "store":
                            isFiltered = isFiltered && estate.getStore();
                            break;
                        case "park":
                            isFiltered = isFiltered && estate.getPark();
                            break;
                        case "parking":
                            isFiltered = isFiltered && estate.getParking();
                            break;
                        case "picture":
                            isFiltered = isFiltered && (estate.getPictures().size() >= 3);
                            break;
                        case "sold":
                            isFiltered = isFiltered && (estate.getSoldDate() != null && !estate.getSoldDate().isEmpty());
                            break;
                        case "lastWeek":
                            Calendar lastWeek = Calendar.getInstance();
                            Date referenceDate = new Date();
                            lastWeek.setTime(referenceDate);
                            lastWeek.add(Calendar.DAY_OF_MONTH, -7);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
                            try {
                                Date date = formatter.parse(estate.getEntryDate());
                                isFiltered = date.after(lastWeek.getTime());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                }

                int startThumbValue = priceRangeBar.getCurrentStartValue();
                int endThumbValue = priceRangeBar.getCurrentEndValue();
                if (isFiltered) {
                    isFiltered = (estate.getPrice() >= Long.parseLong(String.valueOf(startThumbValue))
                            && estate.getPrice() <= Long.parseLong(String.valueOf(endThumbValue)));
                }

                int startThumbValue2 = surfaceRangeBar.getCurrentStartValue();
                int endThumbValue2 = surfaceRangeBar.getCurrentEndValue();

                if (isFiltered) {
                    isFiltered = (estate.getSurface() >= Long.parseLong(String.valueOf(startThumbValue2))
                            && estate.getSurface() <= Long.parseLong(String.valueOf(endThumbValue2)));
                }
                if (spinner.getSelectedItemPosition() != 0) {
                    if (isFiltered) {
                        isFiltered = Objects.equals(estate.getEstateType(), spinner.getSelectedItem().toString());
                    }
                }
                if (isFiltered) {
                    filteredEstates.add(estate);
                }
            }
            setupRecyclerView(filteredEstates);
        });
    }


    private void setUpSpinner() {
        spinner = binding.getRoot().findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.requireContext(),
                R.array.type_of_property_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
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

    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this.requireActivity());
        this.estateViewModel = new ViewModelProvider(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getAllEstates() {
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), estates -> {
            setupRecyclerView(estates);
            initPriceRangeBar(estates);
            initSurfaceRangeBar(estates);
            ConstraintLayout constraintLayout = getActivity().findViewById(R.id.list_item_color);
            setUpSpinner();
        });
    }

    void setupRecyclerView(List<Estate> estates) {
        adapter = new EstateListAdapter(estates, v, this.requireContext());
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

    private void setUpMaps() {
        mapsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this.requireContext(), MapsActivity.class);
            startActivity(intent);
        });
    }

    private void setLogOutBtn() {
        signOutBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(this.requireContext())
                    .setMessage(R.string.logout_message)
                    .setCancelable(true)
                    .setPositiveButton("Oui", (dialog, which) -> {
                        ((EstateHostActivity) EstateListFragment.this.requireActivity())
                                .userViewModel.signOut(EstateListFragment.this.requireContext()).observe(getViewLifecycleOwner(), isSuccessful -> {

                                    if (isSuccessful) {
                                        Intent intent = new Intent(EstateListFragment.this.requireActivity(), AuthenticationActivity.class);
                                        startActivity(intent);
                                        EstateListFragment.this.requireActivity().finish();
                                    }
                                });

                    })
                    .setNegativeButton("Non", (dialogInterface, i) -> {

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

    private void initPriceRangeBar(List<Estate> estates) {
        Collections.sort(estates, (estate1, estate2) -> {
            return Long.compare(estate1.getPrice(), estate2.getPrice());
        });
        long minValue = Long.MAX_VALUE;
        long maxValue = Long.MIN_VALUE;
        for (Estate estate : estates) {
            long price = estate.getPrice(); //Each estate price
            if (price < minValue) { //if the current price is smaller than the minPrice
                minValue = price; // then we update the min price with the current price
            }
            if (price > maxValue) {
                maxValue = price;
            }
        }

        if (minValue == maxValue) {
            minValue = 0;
        }

        if (!estates.isEmpty()) {
            priceRangeBar.setMax((int) maxValue);
            priceRangeBar.setMin((int) minValue);
            priceRangeBar.setCurrentStartValue((int) minValue);
            priceRangeBar.setCurrentEndValue((int) maxValue);
        }
        priceRangeBar.setHighlightThumbOnTouchColor(Color.CYAN);


        //COMBIEN DE BIEN ONT LE MEME PRIX
        Map<Long, Integer> countMap = new TreeMap<>();
        for (Estate estate : estates) {
            if (countMap.containsKey(estate.getPrice())) {
                countMap.put(estate.getPrice(), countMap.get(estate.getPrice()) + 1);
            } else {
                countMap.put(estate.getPrice(), 1);
            }
        }
        setUpPriceRangeBar();
    }

    private void initSurfaceRangeBar(List<Estate> estates) {
        Collections.sort(estates, (estate1, estate2) -> {
            return Long.compare(estate1.getSurface(), estate2.getSurface());
        });
        long minValue = Long.MAX_VALUE;
        long maxValue = Long.MIN_VALUE;
        for (Estate estate : estates) {
            long surface = estate.getSurface();
            if (surface < minValue) {
                minValue = surface;
            }
            if (surface > maxValue) {
                maxValue = surface;
            }
        }

        if (minValue == maxValue) {
            minValue = 0;
        }

        if (!estates.isEmpty()) {
            surfaceRangeBar.setMax((int) maxValue);
            surfaceRangeBar.setMin((int) minValue);
            surfaceRangeBar.setCurrentStartValue((int) minValue);
            surfaceRangeBar.setCurrentEndValue((int) maxValue);
        }
        surfaceRangeBar.setHighlightThumbOnTouchColor(Color.CYAN);

        Map<Long, Integer> countMap = new TreeMap<>();
        for (Estate estate : estates) {
            if (countMap.containsKey(estate.getSurface())) {
                countMap.put(estate.getSurface(), countMap.get(estate.getSurface()) + 1);
            } else {
                countMap.put(estate.getSurface(), 1);
            }
        }
        setUpSurfaceRangeBar();
    }

    private void setUpPriceRangeBar() {
        priceRangeBar.setOnRubberRangePickerChangeListener(new RubberRangePicker.OnRubberRangePickerChangeListener() {
            @Override
            public void onProgressChanged(@NonNull RubberRangePicker rangePicker, int startValue, int endValue, boolean fromUser) {
                DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
                customSymbols.setGroupingSeparator(' ');
                DecimalFormat decimalFormat = new DecimalFormat("#,###", customSymbols);
                String formattedStartValue = decimalFormat.format(startValue);
                String formattedEndValue = decimalFormat.format(endValue);
                priceMinValue.setText((formattedStartValue));
                priceMaxValue.setText((formattedEndValue));
                applyFilters();
            }

            @Override
            public void onStartTrackingTouch(RubberRangePicker rangePicker, boolean isStartThumb) {
            }

            @Override
            public void onStopTrackingTouch(RubberRangePicker rangePicker, boolean isStartThumb) {

            }
        });

    }

    private void setUpSurfaceRangeBar() {
        surfaceRangeBar.setOnRubberRangePickerChangeListener(new RubberRangePicker.OnRubberRangePickerChangeListener() {
            @Override
            public void onProgressChanged(@NonNull RubberRangePicker rangePicker, int startValue, int endValue, boolean fromUser) {
                surfaceMinValue.setText(String.valueOf(startValue));
                surfaceMaxValue.setText(String.valueOf(endValue));
                applyFilters();
            }

            @Override
            public void onStartTrackingTouch(@NonNull RubberRangePicker rangePicker, boolean isStartThumb) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RubberRangePicker rangePicker, boolean isStartThumb) {

            }
        });

    }

}