package com.wizroots.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private final long id;
    public DetailViewModelFactory(Application application,long id) {
        this.application=application;
        this.id=id;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(application,id);
    }
}
