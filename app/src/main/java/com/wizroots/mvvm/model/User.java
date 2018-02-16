package com.wizroots.mvvm.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uId;

    @NonNull
    private final String name;

    @NonNull
    @ColumnInfo(name = "user_email")
    private final String email;

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    @ColumnInfo(name = "user_password")
    private final String password;

    public User(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }


    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }
}
