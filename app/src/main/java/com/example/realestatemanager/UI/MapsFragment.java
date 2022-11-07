package com.example.realestatemanager.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.Utils;
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

public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback{

    private Boolean isTablet;
    private Context mContext;
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 14;
    private PropertyViewModel propertyViewModel;
    private long property_id;
    private final String PROPERTY_ID_DETAILS = "property_id_details";
    private List<PropertyWithPhoto> propertyWithPhotoList = new ArrayList<>();
    private Location location;


   /* private OnMapReadyCallback callback = new OnMapReadyCallback() {


       @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
        }
    };*/

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
        isTablet = getContext().getResources().getBoolean(R.bool.isTablet);
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        configureViewModel();
        propertyViewModel.getLocation().observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                MapsFragment.this.location = location;
                mapFragment.getMapAsync(MapsFragment.this);

            }
        });
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
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyPosition, DEFAULT_ZOOM));
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
                        if (isTablet) {
                            Navigation.findNavController(getActivity(), R.id.item_detail_nav_container)
                                    .navigate(R.id.item_detail_fragment, arguments);
                        } else {
                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail)
                                    .navigate(R.id.action_mapsFragment_to_item_detail_fragment, arguments);
                        }
                        return false;
                    }

                });
            }
        };
        propertyViewModel.getProperties().observe(getActivity(), results);

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        if (Utils.isInternetAvailable(context)) {
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
        }
        Toast.makeText(context.getApplicationContext(), "Not connected", Toast.LENGTH_LONG).show();
        return null;
    }

    public String getAddress(PropertyWithPhoto propertyWithPhoto) {
        String address = propertyWithPhoto.property.getAddress().getStreet() + " " +
                propertyWithPhoto.property.getAddress().getPostCode() + " " +
                propertyWithPhoto.property.getAddress().getCity();

        return address;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        // Add a marker at current place and move the camera
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentPosition).title("Current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, DEFAULT_ZOOM));
        getProperties();
    }
}