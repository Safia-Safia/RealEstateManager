package com.openclassrooms.realestatemanager.controller.placeholder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Picture;

import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateAdapter.PropertyViewHolder> {
    private final List<Picture> imageUris;

    public EstateAdapter(List<Picture> imageUris) {
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_property_picture_recyclerview_content, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Picture picture = imageUris.get(position);
        if (picture.getImageUri() != null){
            Glide.with(holder.imageView.getContext())
                    .load(picture.getImageUri())
                    .centerCrop()
                    .into(holder.imageView);
        }else {
            Glide.with(holder.imageView.getContext())
                    .load(picture.getImageUrl())
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.pictureDescriptionView.setText(picture.getDescription());
        holder.deleteButton.setOnClickListener(v -> {
            int position1 =holder.getBindingAdapterPosition();
            imageUris.remove(position1);
            notifyItemRemoved(position1);
        });
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton deleteButton;
        TextView pictureDescriptionView;

        public PropertyViewHolder(@NonNull View view) {
            super(view);
            deleteButton = view.findViewById(R.id.deleteButton);
            imageView = view.findViewById(R.id.recyclerviewcontent_property_picture);
            pictureDescriptionView = view.findViewById(R.id.cardview_picture_description);
        }


    }
}
