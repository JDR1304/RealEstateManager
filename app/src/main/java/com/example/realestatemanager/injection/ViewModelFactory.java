package com.example.realestatemanager.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.repository.PropertyRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private PropertyRepository dataSourceProperty;
    private Executor executor;

    public ViewModelFactory(PropertyRepository dataSourceProperty, Executor executor) {
        this.dataSourceProperty = dataSourceProperty;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(dataSourceProperty, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
