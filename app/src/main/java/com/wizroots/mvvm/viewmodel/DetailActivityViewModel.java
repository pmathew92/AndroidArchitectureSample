package com.wizroots.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.wizroots.mvvm.UserDataSource;
import com.wizroots.mvvm.model.User;

public class DetailActivityViewModel extends AndroidViewModel {
    private final UserDataSource mDataSource;
    private final LiveData<User> mUserData;
    private final long userId;
    DetailActivityViewModel(@NonNull Application application,long id) {
        super(application);
        mDataSource=UserDataSource.getInstance(application);
        userId=id;
        mUserData=mDataSource.getUser(id);
    }

    public LiveData<User> getObservableUserData(){
        return mUserData;
    }

    public void updateName(String name){
        mDataSource.updateName(name,userId);
    }
}
