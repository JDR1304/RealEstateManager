package com.example.realestatemanager.injection;

import android.content.Context;

import com.example.realestatemanager.database.PropertyDAO;
import com.example.realestatemanager.database.RealEstateManagerDataBase;
import com.example.realestatemanager.repository.PropertyRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static PropertyRepository providePropertyDataSource(Context context) {
        RealEstateManagerDataBase database = RealEstateManagerDataBase.getInstance(context);
        return new PropertyRepository(database.propertyDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        PropertyRepository dataSourceProperty = providePropertyDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProperty, executor);
    }

}
