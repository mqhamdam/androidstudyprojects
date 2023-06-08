package com.mqhamdam.myphotoalbum;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EImages.class}, version = 1)
public abstract class ImagesDB extends RoomDatabase {
    public abstract ImagesDao imagesDao();

    private static ImagesDB INSTANCE;

    public static synchronized ImagesDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ImagesDB.class, "images_db")
                    .fallbackToDestructiveMigration()
                  //  .allowMainThreadQueries() // --> not recommended
                    .build();
        }
        return INSTANCE;
    }
}
