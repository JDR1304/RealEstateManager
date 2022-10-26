package com.example.realestatemanager.UI;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.UI.ItemDetail.ItemDetailCreateUpdateRecyclerViewAdapter;
import com.example.realestatemanager.databinding.FragmentCreateAndUpdatePropertyBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.Address;
import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateAndUpdatePropertyFragment extends Fragment {

    private FragmentCreateAndUpdatePropertyBinding binding;
    private PropertyWithPhoto propertyWithPhoto;
    private PropertyViewModel propertyViewModel;
    private ItemDetailCreateUpdateRecyclerViewAdapter itemDetailRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private final String PROPERTY_ID_CREATE_UPDATE = "property_id_create_update";
    private long propertyId;
    private Spinner spinnerType;
    private NumberPicker numberPickerRooms;
    private NumberPicker numberPickerBathrooms;
    private NumberPicker numberPickerBedrooms;
    private final String[] types = {"House", "Apartment", "Penthouse", "Duplex"};

    private Button createOrUpdateBtn;
    private Button addMedia;
    private Button takePicture;
    private Uri photoURI;

    private final int GALLERY_IMAGE_CAPTURE = 100;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;

    private List<Photo> pictures = new ArrayList<>();

    private String propertyTypeListenerValue;
    private double priceInDollarsListenerValue;
    private float surfaceListenerValue;
    private int numberOfRoomsListenerValue;
    private int numberOfBathRoomsListenerValue;
    private int numberOfBedRoomsListenerValue;
    private String descriptionListenerValue;
    private String streetListenerValue;
    private int postCodeListenerValue;
    private String cityListenerValue;
    private boolean pointsOfInterestSchoolListenerValue;
    private boolean pointsOfInterestParkListenerValue;
    private boolean pointsOfInterestStoreListenerValue;
    private boolean propertyStatusListenerValue;
    private String realEstateAgentNameListenerValue;


    public CreateAndUpdatePropertyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getArguments != null when you want to update
        if (getArguments() != null && getArguments().getString(PROPERTY_ID_CREATE_UPDATE) != null) {
            propertyId = Long.parseLong(getArguments().getString(PROPERTY_ID_CREATE_UPDATE));
        }

        configureViewModel();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateAndUpdatePropertyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initPickers(view);
        initSpinner(view);
        recyclerView = binding.itemPhoto;
        createOrUpdateBtn = view.findViewById(R.id.create_or_update_button);
        setTextOnCreateOrUpdateBtn(propertyId);
        addMedia = view.findViewById(R.id.add_media_button);
        takePicture = view.findViewById(R.id.take_picture);
        if (propertyId != 0) {
            //propertyId == 0 when we want to create a new property
            getProperty();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        priceListener();
        descriptionListener();
        spinnerListener();
        pickersListeners();
        surfaceValueListener();
        addressListener();
        addNewPicture();
        onClickListenerTake();
        checkBoxListener();
        agentNameListener();
        createOrUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
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
                itemDetailRecyclerViewAdapter = new ItemDetailCreateUpdateRecyclerViewAdapter(propertyWithPhoto.photos, propertyViewModel, true);
                initView();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(itemDetailRecyclerViewAdapter);
            }

        };
        propertyViewModel.getProperty(propertyId).observe(getActivity(), propertyWithPhotoObserver);
    }

    public void initView() {
        for (int i = 0; i < types.length; i++) {
            if (propertyWithPhoto.property.getPropertyType().equals(types[i])) {
                binding.spinnerPropertyTypeValue.setSelection(i);
            }
        }
        binding.propertyPriceValue.setText(Double.toString(propertyWithPhoto.property.getPriceInDollars()));
        binding.propertyDescription.setText(propertyWithPhoto.property.getDescription());
        binding.surfaceValue.setText(Float.toString(propertyWithPhoto.property.getSurface()));
        binding.numberOfRoomsValue.setValue(propertyWithPhoto.property.getNumberOfRooms());
        binding.numberOfBathroomsValue.setValue(propertyWithPhoto.property.getNumberOfBathrooms());
        binding.numberOfBedroomsValue.setValue(propertyWithPhoto.property.getNumberOfBedrooms());
        binding.street.setText(propertyWithPhoto.property.getAddress().getStreet());
        binding.postCode.setText(Integer.toString(propertyWithPhoto.property.getAddress().getPostCode()));
        binding.city.setText(propertyWithPhoto.property.getAddress().getCity());
        binding.chekBoxPark.setChecked(propertyWithPhoto.property.isPointsOfInterestPark());
        binding.chekBoxSchool.setChecked(propertyWithPhoto.property.isPointsOfInterestSchool());
        binding.chekBoxStore.setChecked(propertyWithPhoto.property.isPointsOfInterestStore());
        binding.agentNameValue.setText(propertyWithPhoto.property.getRealEstateAgentName());
        binding.checkBoxStatus.setChecked(propertyWithPhoto.property.isPropertyStatus());

        propertyTypeListenerValue = propertyWithPhoto.property.getPropertyType();
        priceInDollarsListenerValue = propertyWithPhoto.property.getPriceInDollars();
        surfaceListenerValue = propertyWithPhoto.property.getSurface();
        numberOfRoomsListenerValue = propertyWithPhoto.property.getNumberOfRooms();
        numberOfBathRoomsListenerValue = propertyWithPhoto.property.getNumberOfBathrooms();
        numberOfBedRoomsListenerValue = propertyWithPhoto.property.getNumberOfBedrooms();
        descriptionListenerValue = propertyWithPhoto.property.getDescription();
        streetListenerValue = propertyWithPhoto.property.getAddress().getStreet();
        postCodeListenerValue = propertyWithPhoto.property.getAddress().getPostCode();
        cityListenerValue = propertyWithPhoto.property.getAddress().getCity();
        pointsOfInterestSchoolListenerValue = propertyWithPhoto.property.isPointsOfInterestSchool();
        pointsOfInterestParkListenerValue = propertyWithPhoto.property.isPointsOfInterestPark();
        pointsOfInterestStoreListenerValue = propertyWithPhoto.property.isPointsOfInterestStore();
        propertyStatusListenerValue = propertyWithPhoto.property.isPropertyStatus();
        realEstateAgentNameListenerValue = propertyWithPhoto.property.getRealEstateAgentName();

    }

    private void initSpinner(View view) {
        spinnerType = view.findViewById(R.id.spinner_property_type_value);
        // Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, types);
        spinnerType.setAdapter(adapter);
    }


    public void initPickers(View view) {
        numberPickerRooms = view.findViewById(R.id.number_of_rooms_value);
        numberPickerBedrooms = view.findViewById(R.id.number_of_bedrooms_value);
        numberPickerBathrooms = view.findViewById(R.id.number_of_bathrooms_value);
        numberPickerRooms.setMaxValue(30);
        numberPickerRooms.setMinValue(0);
        numberPickerBathrooms.setMaxValue(10);
        numberPickerBathrooms.setMinValue(0);
        numberPickerBedrooms.setMaxValue(15);
        numberPickerBedrooms.setMinValue(0);
    }

    public void priceListener() {
        binding.propertyPriceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    /*if (propertyId != 0) {
                       propertyWithPhoto.property.setPriceInDollars(Double.valueOf(String.valueOf(s)));
                    } else {*/
                    priceInDollarsListenerValue = Double.valueOf(String.valueOf(s));
                    //}
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void spinnerListener() {
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* if (propertyId != 0) {
                    propertyWithPhoto.property.setPropertyType(types[position]);
                } else {*/
                propertyTypeListenerValue = types[position];
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void pickersListeners() {
        numberPickerRooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               /* if (propertyId != 0) {
                    propertyWithPhoto.property.setNumberOfRooms(newVal);
                } else {*/
                numberOfRoomsListenerValue = newVal;
            }
            // }
        });

        numberPickerBathrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                /*if (propertyId != 0) {
                    propertyWithPhoto.property.setNumberOfBathrooms(newVal);
                } else {*/
                numberOfBathRoomsListenerValue = newVal;
            }
            // }
        });

        numberPickerBedrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                /*if (propertyId != 0) {
                    propertyWithPhoto.property.setNumberOfBedrooms(newVal);
                } else {*/
                numberOfBedRoomsListenerValue = newVal;
                // }
            }
        });

    }

    public void surfaceValueListener() {
        binding.surfaceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                   /* if (propertyId != 0) {
                        propertyWithPhoto.property.setSurface(Float.valueOf(String.valueOf(s)));
                    } else {*/
                    surfaceListenerValue = Float.valueOf(String.valueOf(s));
                    //  }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addressListener() {
        binding.street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                   /* if (propertyId != 0) {
                        propertyWithPhoto.property.getAddress().setStreet(String.valueOf(s));
                    } else {*/
                    streetListenerValue = String.valueOf(s);
                    // }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.postCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    /*if (propertyId != 0) {
                        propertyWithPhoto.property.getAddress().setPostCode(Integer.valueOf(String.valueOf(s)));
                    } else {*/
                    postCodeListenerValue = Integer.valueOf(String.valueOf(s));
                    // }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                   /* if (propertyId != 0) {
                        propertyWithPhoto.property.getAddress().setCity(String.valueOf(s));
                    } else {*/
                    cityListenerValue = String.valueOf(s);
                    // }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void descriptionListener() {
        binding.propertyDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    /*if (propertyId != 0) {
                        propertyWithPhoto.property.setDescription(String.valueOf(s));
                    } else {*/
                    descriptionListenerValue = String.valueOf(s);
                    // }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void agentNameListener() {
        binding.agentNameValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                   /* if (propertyId != 0) {
                        propertyWithPhoto.property.setRealEstateAgentName(String.valueOf(s));
                    } else {*/
                    realEstateAgentNameListenerValue = String.valueOf(s);
                    //}
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
               /* if (propertyId != 0) {
                    propertyWithPhoto.property.setPointsOfInterestSchool(isChecked);
                } else {*/
                pointsOfInterestSchoolListenerValue = isChecked;
                //}
            }
        });

        binding.chekBoxPark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               /* if (propertyId != 0) {
                    propertyWithPhoto.property.setPointsOfInterestPark(isChecked);
                } else {*/
                pointsOfInterestParkListenerValue = isChecked;
                // }
            }
        });

        binding.chekBoxStore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               /* if (propertyId != 0) {
                    propertyWithPhoto.property.setPointsOfInterestStore(isChecked);
                } else {*/
                pointsOfInterestStoreListenerValue = isChecked;
                // }
            }
        });

        binding.checkBoxStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              /*  if (propertyId != 0) {
                    propertyWithPhoto.property.setPropertyStatus(isChecked);
                    if (isChecked == true) {
                        long currentTime = System.currentTimeMillis();
                        propertyWithPhoto.property.setPropertySaleDate(currentTime);
                    } else {
                        propertyWithPhoto.property.setPropertySaleDate(0);
                    }
                } else {*/
                propertyStatusListenerValue = isChecked;
                // }
            }
        });

    }

    public void addNewPicture() {
        addMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add Picture ...", Toast.LENGTH_LONG).show();
                Intent galleryPhotoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                galleryPhotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(galleryPhotoIntent, GALLERY_IMAGE_CAPTURE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get image from data
            Uri selectedImage = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String uriToStringPhoto = selectedImage.toString();
            pictures.add(new Photo(propertyId, uriToStringPhoto, uriToStringPhoto));
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            pictures.add(new Photo(propertyId, photoURI.toString(), photoURI.toString()));
        } else {
            Toast.makeText(getActivity(), "Picture not shot", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickListenerTake() {
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void createOrUpdate() {
        createOrUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (propertyId == 0) {
                    //propertyViewModel.createPropertyWithPhotos(propertyWithPhoto.property, propertyWithPhoto.photos);
                    Address addressCreated = new Address(streetListenerValue, postCodeListenerValue, cityListenerValue);
                    Property propertyCreated = new Property(propertyTypeListenerValue, priceInDollarsListenerValue, surfaceListenerValue,
                            numberOfRoomsListenerValue, numberOfBathRoomsListenerValue, numberOfBedRoomsListenerValue,
                            descriptionListenerValue, addressCreated, pointsOfInterestSchoolListenerValue, pointsOfInterestParkListenerValue,
                            pointsOfInterestStoreListenerValue, propertyStatusListenerValue, System.currentTimeMillis(), 0,
                            realEstateAgentNameListenerValue);

                    propertyViewModel.createPropertyWithPhotos(propertyCreated, pictures);

                    Toast.makeText(getActivity(), "Property Created", Toast.LENGTH_LONG).show();
                } else {
                    // je recupère les photos de la propriété que j'ajoute a la liste dans laquelle j'ai deja ajouté des photos
                    for (int i = 0; i < propertyWithPhoto.photos.size(); i++) {
                        pictures.add(propertyWithPhoto.photos.get(i));
                    }
                    Address addressUpdated = new Address(streetListenerValue, postCodeListenerValue, cityListenerValue);
                    Property propertyUpdated = new Property(propertyTypeListenerValue, priceInDollarsListenerValue, surfaceListenerValue,
                            numberOfRoomsListenerValue, numberOfBathRoomsListenerValue, numberOfBedRoomsListenerValue,
                            descriptionListenerValue, addressUpdated, pointsOfInterestSchoolListenerValue, pointsOfInterestParkListenerValue,
                            pointsOfInterestStoreListenerValue, propertyStatusListenerValue, System.currentTimeMillis(), 0,
                            realEstateAgentNameListenerValue);

                    if (propertyStatusListenerValue == true) {
                        long currentTime = System.currentTimeMillis();
                        propertyUpdated.setPropertySaleDate(currentTime);
                    } else {
                        propertyUpdated.setPropertySaleDate(0);
                    }

                    if (propertyUpdated.equals(propertyWithPhoto.property)) {
                        propertyViewModel.updatePropertyWithPhotos(propertyWithPhoto.property, pictures);
                    } else {
                        propertyUpdated.setId(propertyId);
                        propertyViewModel.updatePropertyWithPhotos(propertyUpdated, pictures);
                    }
                    Toast.makeText(getActivity(), "Property Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setTextOnCreateOrUpdateBtn(long propertyId) {
        if (propertyId == 0) {
            createOrUpdateBtn.setText("Create property");
        } else {
            createOrUpdateBtn.setText("Update property");
        }
    }
}