package com.example.realestatemanager.UI.ItemList;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.RetrieveIdPropertyId;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListViewRecyclerViewAdapter extends RecyclerView.Adapter<ListViewRecyclerViewAdapter.ViewHolder> {


    private List<PropertyWithPhoto> propertyWithPhotoList;
    private PropertyViewModel propertyViewModel;
    private RetrieveIdPropertyId listener;
    private List <View> listView = new ArrayList<>();


    public ListViewRecyclerViewAdapter(List<PropertyWithPhoto> propertyWithPhotoList, PropertyViewModel propertyViewModel, RetrieveIdPropertyId listener) {
        this.propertyWithPhotoList = propertyWithPhotoList;
        this.propertyViewModel = propertyViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        listView.add(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PropertyWithPhoto propertyWithPhoto = propertyWithPhotoList.get(position);
        holder.type.setText(propertyWithPhoto.property.getPropertyType());
        holder.town.setText(propertyWithPhoto.property.getAddress().getCity());
        holder.price.setText(displayPrice(propertyWithPhoto.property.getPriceInDollars()));
        if (propertyWithPhoto.photos.size()==0) {
            Glide.with(holder.propertyImage.getContext())
                    .load(R.drawable.ic_baseline_apartment_24)
                    .into(holder.propertyImage);
        } else{
            Glide.with(holder.propertyImage.getContext())
                    .load(Uri.parse(propertyWithPhoto.photos.get(0).getStringPhoto()))
                    .override(100,100)
                    .error(R.drawable.ic_baseline_apartment_24)
                    .into(holder.propertyImage);
        }
        holder.itemView.setOnClickListener(v -> {
            listener.onClickItem(Long.toString(propertyWithPhoto.property.getId()));
            for (View item: listView) {
                if (listView.get(holder.getAdapterPosition()) == item) {
                    item.setBackgroundColor(0xFF8CF1EC);
                } else {
                    item.setBackgroundColor(0xFFFFFFFF);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return propertyWithPhotoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView propertyImage;
        private TextView town;
        private TextView type;
        private TextView price;


        public ViewHolder(View view) {
            super(view);
            town = view.findViewById(R.id.town);
            type = view.findViewById(R.id.type);
            price = view.findViewById(R.id.price);
            propertyImage = view.findViewById(R.id.imageProperty);
            
        }
    }
    public String displayPrice(Double price){
        String convertedString = new DecimalFormat("#,###.##").format(price);
        String priceInDollars = "$"+convertedString;
        return priceInDollars;
    }

}
