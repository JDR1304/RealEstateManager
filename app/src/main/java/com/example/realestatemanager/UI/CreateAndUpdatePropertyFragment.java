package com.example.realestatemanager.UI;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.databinding.FragmentCreateAndUpdatePropertyBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.Address;
import com.example.realestatemanager.models.Photo;
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
    private View itemDetailFragmentContainer;
    private String PROPERTY_ID = "property_id";
    private long propertyId;
    private Spinner spinnerType;
    private NumberPicker numberPickerRooms;
    private NumberPicker numberPickerBathrooms;
    private NumberPicker numberPickerBedrooms;
    private String[] types = {"House", "Apartment", "Penthouse", "Duplex"};

    private Button addMedia;
    private Button takePicture;
    private ImageView photo;
    private Uri photoURI;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;

    private List<Photo> photos = new ArrayList<>();


    public CreateAndUpdatePropertyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            propertyId = Long.parseLong(getArguments().getString(PROPERTY_ID));
        }
        configureViewModel();
        getProperty();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateAndUpdatePropertyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initPickers(view);
        initSpinner(view);
        addMedia = view.findViewById(R.id.add_media_button);
        takePicture = view.findViewById(R.id.take_picture);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        descriptionListener();
        spinnerListener();
        pickersListeners();
        surfaceValueListener();
        addressListener();
        addNewPicture();
        onClickListenerTake();

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

    public void initView() {
        for (int i = 0; i < types.length; i++) {
            if (propertyWithPhoto.property.getPropertyType().equals(types[i])) {
                binding.spinnerPropertyTypeValue.setSelection(i);
            }
        }
        binding.propertyDescription.setText(propertyWithPhoto.property.getDescription());
        binding.surfaceValue.setText(Float.toString(propertyWithPhoto.property.getSurface()));
        binding.numberOfRoomsValue.setValue(propertyWithPhoto.property.getNumberOfRooms());
        binding.numberOfBathroomsValue.setValue(propertyWithPhoto.property.getNumberOfBathrooms());
        binding.numberOfBedroomsValue.setValue(propertyWithPhoto.property.getNumberOfBedrooms());
        binding.street.setText(propertyWithPhoto.property.getAddress().getStreet());
        binding.postCode.setText(Integer.toString(propertyWithPhoto.property.getAddress().getPostCode()));
        binding.city.setText(propertyWithPhoto.property.getAddress().getCity());
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

    public void spinnerListener(){
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyWithPhoto.property.setPropertyType(types[position]);
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
                propertyWithPhoto.property.setNumberOfRooms(newVal);
            }
        });

        numberPickerBathrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                propertyWithPhoto.property.setNumberOfBathrooms(newVal);
            }
        });

        numberPickerBedrooms.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                propertyWithPhoto.property.setNumberOfBedrooms(newVal);
            }
        });

    }

    public void surfaceValueListener(){
        binding.surfaceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0) {
                    propertyWithPhoto.property.setSurface(Float.valueOf(String.valueOf(s)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addressListener(){
        binding.street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0) {
                    propertyWithPhoto.property.getAddress().setStreet(String.valueOf(s));
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
                if (s.length()!=0) {
                    propertyWithPhoto.property.getAddress().setPostCode(Integer.valueOf(String.valueOf(s)));
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
                if (s.length()!=0) {
                    propertyWithPhoto.property.getAddress().setCity(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void descriptionListener(){
        binding.propertyDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0) {
                    propertyWithPhoto.property.setDescription(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addNewPicture() {
        addMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add Picture ...", Toast.LENGTH_LONG).show();
                Intent galleryPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryPhotoIntent, 100);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Get image from data
            Uri selectedImage = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String uriToStringPhoto = selectedImage.toString();
            photos.add(new Photo(propertyId, uriToStringPhoto, uriToStringPhoto));


            //photo.setImageBitmap(image);


        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            photo.setImageURI(photoURI);

        } else {
            Toast.makeText(getActivity(), "Picture not selected", Toast.LENGTH_LONG).show();
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

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "On Detach", Toast.LENGTH_LONG).show();

        propertyViewModel.updatePropertyWithPhotos(propertyWithPhoto.property, photos);
    }


}