package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    EstateViewModel estateViewModel;
    RecyclerView recyclerView;
    EstateListAdapter adapter;
    View v;

    FloatingActionButton fabAddEstates;
    ImageButton filterBtn, signOutBtn;
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
        recyclerView = binding.estateListRecyclerview;
        v = view.findViewById(R.id.property_detail_nav_container);
        setUpEstateViewModel();
        getAllEstates();

        fabAddEstates = view.findViewById(R.id.button_create_property);
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        filterBtn = view.findViewById(R.id.filter_button);

        setLogOutBtn();
        setUpAddEstate();
    }


    private void setUpEstateViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this.requireActivity());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getAllEstates() {
        estateViewModel.getEstates().observe(getViewLifecycleOwner(), this::setupRecyclerView);
    }

    void setupRecyclerView(List<Estate> estates) {;
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
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((EstateHostActivity)EstateListFragment.this.requireActivity()).userViewModel.signOut(EstateListFragment.this.requireContext());
                            EstateListFragment.this.requireActivity().finish();
                        }
                    })
                    .create()
                    .show();


        });
    }
    //onResume ou StartActivityResult
}