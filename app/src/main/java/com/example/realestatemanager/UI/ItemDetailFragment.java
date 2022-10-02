package com.example.realestatemanager.UI;



import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.databinding.FragmentItemDetailBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;



public class ItemDetailFragment extends Fragment {

    private FragmentItemDetailBinding binding;
    private PropertyViewModel propertyViewModel;

    PropertyWithPhoto propertyWithPhoto;
    String PROPERTY_ID = "property_id";
    long propertyId;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ) {
            propertyId = Long.parseLong(getArguments().getString(PROPERTY_ID));
        }
        configureViewModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        //getProperty(propertyId);
       // initView();
        return rootView;
    }

    public void initView(){
        binding.propertyDescription.setText(propertyWithPhoto.property.getDescription());
        binding.surfaceValue.setText(Float.toString(propertyWithPhoto.property.getSurface()));
        binding.numberOfRoomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfRooms()));
        binding.numberOfBathroomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfRooms()));
        binding.numberOfBedrooms.setText(Float.toString(propertyWithPhoto.property.getNumberOfRooms()));
        binding.street.setText(propertyWithPhoto.property.getAddress().getStreet());
        binding.postCode.setText(propertyWithPhoto.property.getAddress().getPostCode());
        binding.city.setText(propertyWithPhoto.property.getAddress().getCity());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getActivity());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);
    }

    public void getProperty(Long propertyId) {
        propertyWithPhoto = propertyViewModel.getProperty(propertyId).getValue();
        Observer<PropertyWithPhoto> propertyWithPhotoObserver = new Observer<PropertyWithPhoto>() {

            @Override
            public void onChanged(PropertyWithPhoto propertyWithPhoto) {
                initView();
            }
        };
        propertyViewModel.getProperty(propertyId).observe(getActivity(),propertyWithPhotoObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}