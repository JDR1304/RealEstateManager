package com.example.realestatemanager.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFilterFragment extends Fragment {

    private Boolean isTablet;
    private RecyclerView recyclerView;
    private PropertyViewModel propertyViewModel;
    private List<PropertyWithPhoto> propertyWithPhotoList = new ArrayList<>();
    private ListViewRecyclerViewAdapter listViewRecyclerViewAdapter;
    private final String PROPERTY_ID_DETAILS = "property_id_details";
    private String propertyUId;
    private LiveData<List<PropertyWithPhoto>> listLiveDataFiltered;


    public RecyclerViewFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_filter, container, false);
        recyclerView = view.findViewById(R.id.filter_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isTablet = getContext().getResources().getBoolean(R.bool.isTablet);
        getListFiltered();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);

    }

    public void getListFiltered(){
        propertyWithPhotoList.clear();
        Observer<List<PropertyWithPhoto>> results = new Observer<List<PropertyWithPhoto>>() {
            @Override
            public void onChanged(List<PropertyWithPhoto> propertyWithPhotos) {
                for (int i = 0; i<propertyWithPhotos.size(); i++){
                    propertyWithPhotoList.add(propertyWithPhotos.get(i));
                }
                listViewRecyclerViewAdapter = new ListViewRecyclerViewAdapter(propertyWithPhotoList, propertyViewModel, new RetrieveIdPropertyId() {
                    @Override
                    public void onClickItem(String propertyId) {
                        propertyUId = propertyId;
                        Bundle arguments = new Bundle();
                        arguments.putString(PROPERTY_ID_DETAILS, propertyId);
                        if (isTablet) {
                            Navigation.findNavController(getActivity(), R.id.item_detail_nav_container)
                                    .navigate(R.id.fragment_item_detail, arguments);
                        } else {
                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail).navigate(R.id.filter_list_container);
                        }
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listViewRecyclerViewAdapter);
            }
        };
        propertyViewModel.getPropertyFiltered().observe(getActivity(), results);
    }
}