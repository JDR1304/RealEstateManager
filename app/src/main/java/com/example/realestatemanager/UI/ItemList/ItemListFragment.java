package com.example.realestatemanager.UI.ItemList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.realestatemanager.RetrieveIdPropertyId;
import com.example.realestatemanager.databinding.FragmentItemListBinding;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

public class ItemListFragment extends Fragment {

    private Boolean isTablet;
    private FragmentItemListBinding binding;
    private RecyclerView recyclerView;
    private PropertyViewModel propertyViewModel;
    private ListViewRecyclerViewAdapter listViewRecyclerViewAdapter;
    private List<PropertyWithPhoto> propertyWithPhotoList = new ArrayList<>();
    private ItemListFragmentDirections.ShowItemDetail action;
    private ItemListFragmentDirections.ActionItemListFragmentToCreateUpdateContainer actionCreate;


    private final String PROPERTY_ID_DETAILS = "property_id_details";
    private final String PROPERTY_ID_CREATE_UPDATE = "property_id_create_update";
    private String propertyUId;
    private View itemDetailFragmentContainer;

    NavHostFragment navHostFragment;


    public ItemListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("your title");
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isTablet = getContext().getResources().getBoolean(R.bool.isTablet);
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        recyclerView = binding.itemList;
        View view = binding.getRoot();
        configureViewModel();
        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);
        //setTabletOrMobile();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.item_detail_nav_container);
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


    public void getProperties() {

        Observer<List<PropertyWithPhoto>> results = new Observer<List<PropertyWithPhoto>>() {

            @Override
            public void onChanged(List<PropertyWithPhoto> propertyWithPhotos) {
                propertyWithPhotoList.clear();
                for (int i = 0; i < propertyWithPhotos.size(); i++) {
                    propertyWithPhotoList.add(propertyWithPhotos.get(i));
                }
                listViewRecyclerViewAdapter = new ListViewRecyclerViewAdapter(propertyWithPhotoList, propertyViewModel, new RetrieveIdPropertyId() {
                    @Override
                    public void onClickItem(String propertyId) {
                        propertyUId = propertyId;
                        Bundle arguments = new Bundle();
                        arguments.putString(PROPERTY_ID_DETAILS, propertyId);
                        if (isTablet) {
                            Navigation.findNavController(itemDetailFragmentContainer)
                                    .navigate(R.id.fragment_item_detail, arguments);
                        } else {
                            action = ItemListFragmentDirections.showItemDetail();
                            action.setPropertyIdDetails(propertyId);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (isTablet) {
            inflater.inflate(R.menu.whole_menu, menu);
        } else {
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isTablet) {
            switch (item.getItemId()) {
                case R.id.map:
                    Toast.makeText(getActivity(), "COUCOU List tablet ...", Toast.LENGTH_LONG).show();
                    navHostFragment.getNavController().navigate(R.id.mapsFragment);
                    return true;
                case R.id.add:
                    Toast.makeText(getActivity(), "Add List tablet...", Toast.LENGTH_LONG).show();
                    navHostFragment.getNavController().navigate(R.id.create_update_container);
                    return true;
               case R.id.update:
                    Bundle arguments = new Bundle();
                    arguments.putString(PROPERTY_ID_CREATE_UPDATE, propertyUId);
                    Toast.makeText(getActivity(), "Update 1 List tablet...", Toast.LENGTH_LONG).show();
                    navHostFragment.getNavController().navigate(R.id.create_update_container, arguments);
                    return true;
                case R.id.search:
                    Toast.makeText(getActivity(), "Search List tablet...", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        } else {
            switch (item.getItemId()) {
                case R.id.map:
                    Toast.makeText(getActivity(), "COUCOU List ...", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail)
                            .navigate(R.id.action_item_list_fragment_to_mapsFragment);
                    return true;
                case R.id.add:
                    Toast.makeText(getActivity(), "Add List...", Toast.LENGTH_LONG).show();
                    actionCreate = ItemListFragmentDirections.actionItemListFragmentToCreateUpdateContainer();
                    actionCreate.setPropertyIdCreateUpdate(null);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_item_detail)
                            .navigate(actionCreate);
                    return true;
                case R.id.search:
                    Toast.makeText(getActivity(), "Search List...", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

}
