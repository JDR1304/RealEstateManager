package com.example.realestatemanager.UI.ItemDetail;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.models.Photo;

import java.util.List;


public class ItemDetailCreateUpdateRecyclerViewAdapter extends RecyclerView.Adapter<ItemDetailCreateUpdateRecyclerViewAdapter.ViewHolder> {

    private List <Photo> photos;
    private ImageView DeletePicture;
    private PropertyViewModel propertyViewModel;
    private boolean buttonDeletePictureVisible;

    public ItemDetailCreateUpdateRecyclerViewAdapter(List<Photo> photos, PropertyViewModel propertyViewModel, boolean buttonDeletePictureVisible) {
        this.photos = photos;
        this.propertyViewModel = propertyViewModel;
        this.buttonDeletePictureVisible = buttonDeletePictureVisible;
    }

    @NonNull
    @Override
    public ItemDetailCreateUpdateRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDetailCreateUpdateRecyclerViewAdapter.ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.image.setImageURI(Uri.parse(photo.getStringPhoto()));
        if (buttonDeletePictureVisible) {
            DeletePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Delete Picture", Toast.LENGTH_LONG).show();
                    propertyViewModel.deleteById(photo.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;



        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            DeletePicture = view.findViewById(R.id.image_delete);
            if (buttonDeletePictureVisible) {
                DeletePicture.setVisibility(View.VISIBLE);
            } else {
                DeletePicture.setVisibility(View.GONE);
            }

        }
    }

}

