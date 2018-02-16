package com.wizroots.mvvm.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.wizroots.mvvm.model.User;

import io.reactivex.Single;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("SELECT * FROM user WHERE uId = :user_id")
    LiveData<User> getUser(long user_id);

    @Query("SELECT uId FROM user WHERE user_email = :email AND user_password = :password")
    Single<Long> checkUser(String email, String password);

    @Query("UPDATE user SET name = :name WHERE uId = :id")
    void updateName(String name,long id);
}
