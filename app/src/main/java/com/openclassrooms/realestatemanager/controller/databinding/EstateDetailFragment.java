package com.openclassrooms.realestatemanager.controller.databinding;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.placeholder.EstateHolderContent;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateDetailBinding;
import com.openclassrooms.realestatemanager.model.Estate;

/**
 * A fragment representing a single Estate detail screen.
 * This fragment is either contained in a {@link EstateListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class EstateDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String KEY_ESTATE = "ESTATE";

    /**
     * The placeholder content this fragment is presenting.
     */
    private Estate estate;
    private CollapsingToolbarLayout mToolbarLayout;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            estate = EstateHolderContent.ESTATE_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentEstateDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EstateDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(KEY_ESTATE)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            estate = EstateHolderContent.ESTATE_MAP.get(getArguments().getString(KEY_ESTATE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEstateDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (estate != null) {
        //    mTextView.setText(mItem.details);
            if (mToolbarLayout != null) {
        //        mToolbarLayout.setTitle(mItem.content);
            }
        }
    }
}