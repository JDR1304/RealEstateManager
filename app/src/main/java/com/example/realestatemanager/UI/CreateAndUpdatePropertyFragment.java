package com.example.realestatemanager.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.databinding.FragmentCreateAndUpdatePropertyBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;

public class CreateAndUpdatePropertyFragment extends Fragment {


    private FragmentCreateAndUpdatePropertyBinding binding;
    private PropertyWithPhoto propertyWithPhoto;
    private PropertyViewModel propertyViewModel;
    private View itemDetailFragmentContainer;
    private String PROPERTY_ID = "property_id";
    private long propertyId;
    private Spinner spinnerType;
    private NumberPicker numberPickerRooms;
    private NumberPicker numberPickerBathrooms;
    private NumberPicker numberPickerBedrooms;




    public CreateAndUpdatePropertyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            propertyId = getArguments().getLong(PROPERTY_ID);
        }
        configureViewModel();
        getProperty();

    }

    private void spinnerManagement() {
        String[] types = {"House", "apartment", "Penthouse", "Duplex"};

        // Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1, types);

        // Setting Adapter to the Spinner
        spinnerType.setAdapter(adapter);

        // Setting OnItemClickListener to the Spinner
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateAndUpdatePropertyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
       // spinnerManagement();
        return view;
    }

    public void initView() {
        binding.type.setText(propertyWithPhoto.property.getPropertyType());
        binding.propertyDescription.setText(propertyWithPhoto.property.getDescription());
        binding.surfaceValue.setText(Float.toString(propertyWithPhoto.property.getSurface()));
        //binding.numberOfRoomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfRooms()));
        //binding.numberOfBathroomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfBathrooms()));
        //binding.numberOfBedroomsValue.setText(Float.toString(propertyWithPhoto.property.getNumberOfBedrooms()));
        binding.street.setText(propertyWithPhoto.property.getAddress().getStreet());
        binding.postCode.setText(Integer.toString(propertyWithPhoto.property.getAddress().getPostCode()));
        binding.city.setText(propertyWithPhoto.property.getAddress().getCity());
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_update, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
            ADD WHAT YOU WANT TO DO WHEN ARROW IS PRESSED
        */
       /* switch (item.getItemId()) {
            case R.id.home:
                Bundle bundle = new Bundle();
                bundle.putString(PROPERTY_ID, Long.toString(propertyId));
                Fragment newFragment = new CreateAndUpdatePropertyFragment();
                newFragment.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (itemDetailFragmentContainer != null){
                    fragmentTransaction.replace(R.id.item_detail_nav_container, newFragment);
                }
                fragmentTransaction.replace(R.id.nav_host_fragment_item_detail, newFragment);
                fragmentTransaction.addToBackStack("ItemDetailFragment");
                fragmentTransaction.commit();
                return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


}