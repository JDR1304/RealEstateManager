package com.example.realestatemanager.UI;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.UI.ItemList.ItemListFragmentDirections;
import com.example.realestatemanager.databinding.FragmentItemDetailBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ItemDetailFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private FragmentItemDetailBinding binding;
    private PropertyViewModel propertyViewModel;
    private View itemDetailFragmentContainer;

    private ItemDetailFragmentDirections.ActionItemDetailFragmentToCreateUpdateContainer action;

    private PropertyWithPhoto propertyWithPhoto;
    private final String PROPERTY_ID_DETAILS = "property_id_details";
    private long propertyId;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            propertyId = Long.parseLong(getArguments().getString(PROPERTY_ID_DETAILS));
        }
        configureViewModel();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        itemDetailFragmentContainer = rootView.findViewById(R.id.item_detail_nav_container);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getProperty();
    }

    public void initView() {
        if (binding != null) {
            binding.propertyDescription.setText(propertyWithPhoto.property.getDescription());
            binding.surfaceValue.setText(Float.toString(propertyWithPhoto.property.getSurface()));
            binding.numberOfRoomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfRooms()));
            binding.numberOfBathroomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfBathrooms()));
            binding.numberOfBedroomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfBedrooms()));
            binding.street.setText(propertyWithPhoto.property.getAddress().getStreet());
            binding.postCode.setText(Integer.toString(propertyWithPhoto.property.getAddress().getPostCode()));
            binding.city.setText(propertyWithPhoto.property.getAddress().getCity());
        }
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getActivity());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);
    }

    public void getProperty() {
        Observer<PropertyWithPhoto> propertyWithPhotoObserver = new Observer<PropertyWithPhoto>() {
            @Override
            public void onChanged(PropertyWithPhoto property) {
                propertyWithPhoto = property;
                initView();
            }
        };
        propertyViewModel.getProperty(propertyId).observe(getActivity(), propertyWithPhotoObserver);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (itemDetailFragmentContainer == null) {
            inflater.inflate(R.menu.menu_details, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (itemDetailFragmentContainer == null) {
            switch (item.getItemId()) {
                case R.id.update:
                    Toast.makeText(getActivity(), "Update mobile...", Toast.LENGTH_LONG).show();

                    Bundle arguments = new Bundle();
                    arguments.putString(PROPERTY_ID_DETAILS, Long.toString(propertyId));

                    if (itemDetailFragmentContainer != null) {
                    /*action = ItemDetailFragmentDirections.actionItemDetailFragmentToCreateUpdateContainer();
                    action.setPropertyId(Long.toString(propertyId));
                    Navigation.findNavController(getActivity(), R.id.fragment_item_detail).navigate(action);*/
                        Navigation.findNavController(itemDetailFragmentContainer)
                                .navigate(R.id.fragment_item_detail, arguments);
                    } else {
                        action = ItemDetailFragmentDirections.actionItemDetailFragmentToCreateUpdateContainer();
                        action.setPropertyIdCreateUpdate(Long.toString(propertyId));
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail).navigate(action);
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return false;
    }
}