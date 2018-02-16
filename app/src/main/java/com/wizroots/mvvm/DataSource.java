package com.wizroots.mvvm;

import android.arch.lifecycle.LiveData;

import com.wizroots.mvvm.model.User;

import io.reactivex.Single;


public interface DataSource {
    long insertUser(User user);

    LiveData<User> getUser(long id);

    Single<Long> checkUser(String email, String password);

    void updateName(String name,long id);
}
