package com.example.realestatemanager.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.RetrieveIdPropertyId;
import com.example.realestatemanager.UI.ItemList.ItemListFragmentDirections;
import com.example.realestatemanager.UI.ItemList.ListViewRecyclerViewAdapter;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private Context mContext;
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 16;
    private PropertyViewModel propertyViewModel;
    private long property_id;
    private final String PROPERTY_ID_DETAILS = "property_id_details";
    private List<PropertyWithPhoto> propertyWithPhotoList = new ArrayList<>();


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        configureViewModel();
        getProperties();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);

    }

    public void getProperties() {

        Observer<List<PropertyWithPhoto>> results = new Observer<List<PropertyWithPhoto>>() {

            @Override
            public void onChanged(List<PropertyWithPhoto> propertyWithPhotos) {
                propertyWithPhotoList.clear();
                for (int i = 0; i < propertyWithPhotos.size(); i++) {
                    propertyWithPhotoList.add(propertyWithPhotos.get(i));
                    LatLng propertyPosition = getLocationFromAddress(mContext, getAddress(propertyWithPhotos.get(i)));
                    if (propertyPosition != null) {
                        mMap.addMarker(new MarkerOptions().position(propertyPosition).title(Long.toString(propertyWithPhotos.get(i).property.getId())));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyPosition, DEFAULT_ZOOM));
                    }
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        for (int i = 0; i < propertyWithPhotos.size(); i++) {
                            if ((propertyWithPhotos.get(i).property.getId()) == (Long.parseLong(marker.getTitle()))) {
                                property_id = propertyWithPhotos.get(i).property.getId();
                            }
                        }
                        Bundle arguments = new Bundle();
                        arguments.putString(PROPERTY_ID_DETAILS, Long.toString(property_id));
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail)
                                .navigate(R.id.action_mapsFragment_to_item_detail_fragment, arguments);

                        return false;
                    }

                });
            }
        };
        propertyViewModel.getProperties().observe(getActivity(), results);

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        if (strAddress != "null 0 null") {
            Geocoder coder = new Geocoder(context);
            List<Address> address;
            LatLng p1 = null;

            try {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null) {
                    return null;
                }
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return p1;
        }
        return null;
    }

    public String getAddress(PropertyWithPhoto propertyWithPhoto) {
        String address = propertyWithPhoto.property.getAddress().getStreet() + " " +
                propertyWithPhoto.property.getAddress().getPostCode() + " " +
                propertyWithPhoto.property.getAddress().getCity();

        return address;
    }
}