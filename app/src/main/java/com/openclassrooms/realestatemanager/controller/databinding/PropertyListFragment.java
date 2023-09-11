package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateListAdapter;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateViewHolderContent;
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding;
import com.openclassrooms.realestatemanager.databinding.PropertyListContentBinding;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.Injection.Injection;
import com.openclassrooms.realestatemanager.utils.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;

import java.util.List;

/**
 * A fragment representing a list of Properties. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link PropertyDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PropertyListFragment extends Fragment {
    FragmentPropertyListBinding binding;
    EstateViewModel estateViewModel;
    RecyclerView recyclerView;
    EstateListAdapter adapter;


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
        binding = FragmentPropertyListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        recyclerView = binding.propertyList;
        View v = view.findViewById(R.id.property_detail_nav_container);
        setUpEstateViewModel();
        getAllEstates();
    }


    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this.requireActivity());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getAllEstates() {
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), estates -> {
            setupRecyclerView(estates);
            adapter.notifyDataSetChanged();
        });
    }

    void setupRecyclerView(List<Estate> estates) {
        adapter = new EstateListAdapter(estates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}