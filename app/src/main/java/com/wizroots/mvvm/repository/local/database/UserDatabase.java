package com.wizroots.mvvm.repository.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wizroots.mvvm.model.User;
import com.wizroots.mvvm.repository.local.dao.UserDao;


@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "sample_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
