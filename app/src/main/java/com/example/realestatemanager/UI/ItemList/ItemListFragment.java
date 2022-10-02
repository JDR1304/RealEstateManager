package com.example.realestatemanager.UI.ItemList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.RetrieveIdPropertyId;
import com.example.realestatemanager.UI.ItemDetailFragment;
import com.example.realestatemanager.databinding.FragmentItemListBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

public class ItemListFragment extends Fragment {

    private FragmentItemListBinding binding;
    private RecyclerView recyclerView;
    private PropertyViewModel propertyViewModel;
    private ListViewRecyclerViewAdapter listViewRecyclerViewAdapter;
    private List<PropertyWithPhoto> propertyWithPhotoList = new ArrayList<>();
    private ItemListFragmentDirections.ShowItemDetail action;

    private String PROPERTY_ID = "property_id";
    private View itemDetailFragmentContainer;


    public ItemListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        recyclerView = binding.itemList;
        View view = binding.getRoot();
        configureViewModel();
        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getProperties();

    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);

    }

    public void displayManagment() {

        listViewRecyclerViewAdapter = new ListViewRecyclerViewAdapter(propertyWithPhotoList, propertyViewModel, new RetrieveIdPropertyId() {
            @Override
            public void onClickItem(String propertyId) {
                Bundle arguments = new Bundle();
                arguments.putString(PROPERTY_ID, propertyId);

                if (itemDetailFragmentContainer != null) {
                    Navigation.findNavController(itemDetailFragmentContainer)
                            .navigate(R.id.fragment_item_detail, arguments);
                } else {
                    //Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
                    action = ItemListFragmentDirections.showItemDetail();
                    action.setPropertyId(propertyId);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail).navigate(action);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listViewRecyclerViewAdapter);

    }

    public void getProperties() {
        propertyWithPhotoList.clear();
        Observer<List<PropertyWithPhoto>> results = new Observer<List<PropertyWithPhoto>>() {

            @Override
            public void onChanged(List<PropertyWithPhoto> propertyWithPhotos) {
                for (int i = 0; i < propertyWithPhotos.size(); i++) {
                    propertyWithPhotoList.add(propertyWithPhotos.get(i));
                }
                listViewRecyclerViewAdapter = new ListViewRecyclerViewAdapter(propertyWithPhotoList, propertyViewModel, new RetrieveIdPropertyId() {
                    @Override
                    public void onClickItem(String propertyId) {
                        Bundle arguments = new Bundle();
                        arguments.putString(PROPERTY_ID, propertyId);
                        if (itemDetailFragmentContainer != null) {
                            Navigation.findNavController(itemDetailFragmentContainer)
                                    .navigate(R.id.fragment_item_detail, arguments);
                        } else {
                            //Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
                            action = ItemListFragmentDirections.showItemDetail();
                            action.setPropertyId(propertyId);
                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail).navigate(action);
                        }
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listViewRecyclerViewAdapter);
            }
        };
        propertyViewModel.getProperties().observe(this, results);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
