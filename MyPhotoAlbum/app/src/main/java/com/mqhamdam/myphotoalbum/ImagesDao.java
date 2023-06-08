package com.mqhamdam.myphotoalbum;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ImagesDao {

    @Insert
    void insert(EImages eImages);

    @Delete
    void delete(EImages eImages);

    @Update
    void update(EImages eImages);

    @Query("SELECT * FROM images Order By image_uid ASC")
    LiveData<List<EImages>> getAllImages();

    @Query("SELECT * FROM images WHERE image_uid = :image_uid")
    EImages getImage(int image_uid);

}
