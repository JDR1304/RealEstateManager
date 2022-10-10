package com.example.realestatemanager.UI.ItemList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
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
import com.example.realestatemanager.UI.CreateAndUpdatePropertyFragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("your title");
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (itemDetailFragmentContainer == null) {
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (itemDetailFragmentContainer == null) {
            switch (item.getItemId()) {
                case R.id.map:
                    Toast.makeText(getActivity(), "COUCOU List ...", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.add:
                    Toast.makeText(getActivity(), "Add List...", Toast.LENGTH_LONG).show();
                    Fragment newFragment = new CreateAndUpdatePropertyFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_item_detail, newFragment);
                    fragmentTransaction.addToBackStack("ItemListFragment");
                    fragmentTransaction.commit();
                    return true;
                /*case R.id.update:
                    Toast.makeText(getActivity(), "Update List...", Toast.LENGTH_LONG).show();
                    return true;*/
                case R.id.search:
                    Toast.makeText(getActivity(), "Search List...", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return false;
    }
}
