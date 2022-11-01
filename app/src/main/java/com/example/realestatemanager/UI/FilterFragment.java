package com.example.realestatemanager.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.databinding.FilterFragmentBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertiesFiltered;

public class FilterFragment extends Fragment {

    private FilterFragmentBinding binding;
    private Boolean isTablet;
    private String propertyTypeListenerValue;
    private double priceInDollarsListenerValueMin;
    private double priceInDollarsListenerValueMax;
    private float surfaceListenerValueMin;
    private float surfaceListenerValueMax;
    private int numberOfMediaValue;
    private int numberOfRoomsListenerValue;
    private int numberOfBathRoomsListenerValue;
    private int numberOfBedRoomsListenerValue;
    private String cityListenerValue;
    private boolean pointsOfInterestSchoolListenerValue;
    private boolean pointsOfInterestParkListenerValue;
    private boolean pointsOfInterestStoreListenerValue;
    private boolean propertyStatusListenerValue;

    private Spinner spinnerType;
    private NumberPicker numberPickerMedia;
    private NumberPicker numberPickerRooms;
    private NumberPicker numberPickerBathrooms;
    private NumberPicker numberPickerBedrooms;
    private final String[] types = {"", "House", "Apartment", "Penthouse", "Duplex"};
    private Button buttonSearch;

    private PropertiesFiltered propertiesFiltered;
    private PropertyViewModel propertyViewModel;

    public FilterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FilterFragmentBinding.inflate(inflater, container, false);
        isTablet = getContext().getResources().getBoolean(R.bool.isTablet);
        View view = binding.getRoot();
        initPickers(view);
        initSpinner(view);
        buttonSearch = view.findViewById(R.id.search_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        priceMinListener();
        priceMaxListener();
        spinnerTypeListener();
        pickersListeners();
        surfaceValueMinListener();
        surfaceValueMaxListener();
        cityListener();
        checkBoxListener();
        searchClickListener();

    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getActivity());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);
    }

    private void initSpinner(View view) {
        spinnerType = view.findViewById(R.id.spinner_property_type_value);
        // Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, types);
        spinnerType.setAdapter(adapter);
    }


    public void initPickers(View view) {
        numberPickerMedia = view.findViewById(R.id.numbers_media_value);
        numberPickerRooms = view.findViewById(R.id.number_of_rooms_value);
        numberPickerBedrooms = view.findViewById(R.id.number_of_bedrooms_value);
        numberPickerBathrooms = view.findViewById(R.id.number_of_bathrooms_value);
        numberPickerMedia.setMinValue(0);
        numberPickerMedia.setMaxValue(50);
        numberPickerRooms.setMaxValue(30);
        numberPickerRooms.setMinValue(0);
        numberPickerBathrooms.setMaxValue(10);
        numberPickerBathrooms.setMinValue(0);
        numberPickerBedrooms.setMaxValue(15);
        numberPickerBedrooms.setMinValue(0);
    }

    public void priceMinListener() {
        binding.propertyPriceValueMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    priceInDollarsListenerValueMin = Double.valueOf(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void priceMaxListener() {
        binding.propertyPriceValueMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    priceInDollarsListenerValueMax = Double.valueOf(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void spinnerTypeListener() {
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyTypeListenerValue = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void pickersListeners() {

        numberPickerMedia.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfMediaValue = newVal;
            }
        });

        numberPickerRooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfRoomsListenerValue = newVal;
            }
        });

        numberPickerBathrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfBathRoomsListenerValue = newVal;
            }
        });

        numberPickerBedrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfBedRoomsListenerValue = newVal;
            }
        });

    }

    public void surfaceValueMinListener() {
        binding.surfaceValueMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    surfaceListenerValueMin = Float.valueOf(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void surfaceValueMaxListener() {
        binding.surfaceValueMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    surfaceListenerValueMax = Float.valueOf(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void cityListener() {

        binding.city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != -1) {
                    cityListenerValue = String.valueOf(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void checkBoxListener() {

        binding.chekBoxSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pointsOfInterestSchoolListenerValue = isChecked;
            }
        });

        binding.chekBoxPark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pointsOfInterestParkListenerValue = isChecked;
            }
        });

        binding.chekBoxStore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pointsOfInterestStoreListenerValue = isChecked;
            }
        });

        binding.checkBoxStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                propertyStatusListenerValue = isChecked;
            }
        });

    }

    public void searchClickListener() {
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertiesFiltered = new PropertiesFiltered(propertyTypeListenerValue, priceInDollarsListenerValueMin,
                        priceInDollarsListenerValueMax, surfaceListenerValueMin, surfaceListenerValueMax,
                        numberOfMediaValue, numberOfRoomsListenerValue, numberOfBathRoomsListenerValue,
                        numberOfBedRoomsListenerValue, cityListenerValue, pointsOfInterestSchoolListenerValue,
                        pointsOfInterestParkListenerValue, pointsOfInterestStoreListenerValue, propertyStatusListenerValue);


                propertyViewModel.getListFiltered(propertiesFiltered);
                if (isTablet){
                    Navigation.findNavController(getActivity(), R.id.item_detail_nav_container)
                            .navigate(R.id.recyclerViewFilterFragment);

                } else {
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail)
                            .navigate(R.id.action_filterFragment_to_recyclerViewFilterFragment);
                }
            }
        });
    }
}
