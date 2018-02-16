package com.wizroots.mvvm;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.wizroots.mvvm.model.User;
import com.wizroots.mvvm.repository.local.database.UserDatabase;

import io.reactivex.Single;


public class UserDataSource implements DataSource {
    private static UserDataSource mInstance;
    private final UserDatabase mUserDatabase;

    private UserDataSource(Application application){
        mUserDatabase=UserDatabase.getInstance(application);
    }

    public static  UserDataSource getInstance(Application application){
        if (mInstance == null) {
            synchronized (UserDataSource.class){
                if(mInstance==null)
                    mInstance= new UserDataSource(application);
            }
        }
        return mInstance;
    }

    @Override
    public long insertUser(User user) {
        return mUserDatabase.userDao().insertUser(user);
    }

    @Override
    public LiveData<User> getUser(long id) {
        return mUserDatabase.userDao().getUser(id);
    }

    @Override
    public Single<Long> checkUser(String email, String password) {
        return mUserDatabase.userDao().checkUser(email,password);
    }

    @Override
    public void updateName(String name, long id) {
        mUserDatabase.userDao().updateName(name,id);
    }

}
