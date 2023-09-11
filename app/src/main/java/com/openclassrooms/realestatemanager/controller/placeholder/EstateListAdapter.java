package com.openclassrooms.realestatemanager.controller.placeholder;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controller.databinding.PropertyDetailFragment;
import com.openclassrooms.realestatemanager.databinding.PropertyListContentBinding;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.ViewHolder> {

    private final List<Estate> estateList;
    Estate estate;


    public EstateListAdapter(List<Estate> estates) {
        estateList = estates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyListContentBinding binding =
                PropertyListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        estate = estateList.get(position);
        holder.price.setText(estate.getPrice());
        holder.description.setText(estate.getDescription());
        holder.typeOfEstate.setText(estate.getEstateType());
        holder.city.setText(estate.getCity());
        Glide.with(holder.coverPicture.getContext())
                .load(estate.getCoverPictureUrl())
                .centerCrop()
                .into(holder.coverPicture);
    }

    @Override
    public int getItemCount() {
        return estateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, typeOfEstate, description, city;
        ImageView coverPicture;

        ViewHolder(PropertyListContentBinding binding) {
            super(binding.getRoot());
            price = binding.recyclerviewContentEstatePrice;
            coverPicture = binding.recyclerviewcontentPropertyPicture;
            typeOfEstate = binding.recyclerviewContentEstateType;
            description = binding.recyclerviewContentDescription;
            city = binding.cityTextView;
        }
    }

}