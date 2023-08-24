package com.openclassrooms.realestatemanager.controller.placeholder;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {
    private final List<Uri> imageUris;

    public PropertyAdapter(List<Uri> imageUris) {
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
        Uri imageUri = imageUris.get(position);
        holder.imageView.setImageURI(imageUri);
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
        public static TextView pictureDescriptionView;

        public PropertyViewHolder(@NonNull View view) {
            super(view);
            deleteButton = view.findViewById(R.id.deleteButton);
            imageView = view.findViewById(R.id.property_picture);
            pictureDescriptionView = view.findViewById(R.id.cardview_picture_description);
        }


    }
}
