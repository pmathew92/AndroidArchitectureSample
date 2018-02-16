package com.wizroots.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wizroots.mvvm.UserDataSource;
import com.wizroots.mvvm.model.User;

import io.reactivex.Single;


public class MainActivityViewModel extends AndroidViewModel {

    private final UserDataSource mDataSource;
    private final MutableLiveData<Boolean> setProgress;

    public MainActivityViewModel(@NonNull Application application){
        super(application);
        mDataSource=UserDataSource.getInstance(application);
        setProgress=new MutableLiveData<>();
        setProgress.setValue(false);
    }

    public LiveData<Boolean> getProgressStatus(){
        return setProgress;
    }

    public void setProgressStatus(boolean status){
        setProgress.setValue(status);
    }

    public Single<Long> insertUser(User user){
        setProgressStatus(true);
       return  Single.fromCallable(()-> mDataSource.insertUser(user));
    }

    public Single<Long> checkUser(String email, String password){
        setProgressStatus(true);
        return mDataSource.checkUser(email,password);
    }

}
