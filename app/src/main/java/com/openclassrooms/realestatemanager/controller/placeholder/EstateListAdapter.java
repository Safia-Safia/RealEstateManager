package com.openclassrooms.realestatemanager.controller.placeholder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.databinding.EstateDetailFragment;
import com.openclassrooms.realestatemanager.databinding.EstateListContentBinding;
import com.openclassrooms.realestatemanager.model.Estate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.EstateViewHolder> {
    private final View view;
    private final List<Estate> estateList;
    Estate estate;


    public EstateListAdapter(List<Estate> estates, View view) {
        estateList = estates;
        this.view = view;
    }

    @NonNull
    @Override
    public EstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EstateListContentBinding binding =
                EstateListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EstateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final EstateViewHolder holder, int position) {
        estate = estateList.get(position);
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", customSymbols);
        String formattedNumber = decimalFormat.format(estate.getPrice());
        holder.price.setText(formattedNumber);

        holder.description.setText(estate.getDescription());
        holder.typeOfEstate.setText(estate.getEstateType());
        holder.city.setText(estate.getCity());
        Glide.with(holder.coverPicture.getContext())
                .load(estate.getCoverPictureUrl())
                .centerCrop()
                .into(holder.coverPicture);

        holder.itemView.setOnClickListener(itemView -> {
            estate = estateList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(EstateDetailFragment.KEY_ESTATE, estate);
            Gson gson = new Gson();
            Log.e("estate", gson.toJson(estate));
            if (view != null) {
                Navigation.findNavController(view)
                        .navigate(R.id.fragment_estate_detail, bundle);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_estate_detail, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return estateList.size();
    }

    public static class EstateViewHolder extends RecyclerView.ViewHolder {
        TextView price, typeOfEstate, description, city;
        ImageView coverPicture;

        EstateViewHolder(EstateListContentBinding binding) {
            super(binding.getRoot());
            price = binding.recyclerviewContentEstatePrice;
            coverPicture = binding.recyclerviewcontentPropertyPicture;
            typeOfEstate = binding.recyclerviewContentEstateType;
            description = binding.recyclerviewContentDescription;
            city = binding.cityTextView;
        }
    }

}