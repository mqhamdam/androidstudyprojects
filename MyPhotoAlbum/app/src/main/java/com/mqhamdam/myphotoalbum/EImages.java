package com.mqhamdam.myphotoalbum;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class EImages {
    @PrimaryKey(autoGenerate = true)
    public int image_uid; // Image UID
    public String image_title; // Image Title
    public String image_description; // Image Description

    /**
     * Blob is a binary large object that can hold a variable amount of data.
     * The term refers to a data type and is used as such in SQL statements
     * that insert or manipulate a blob data type.
     */
    public byte[] image; // Blob

    /**
     * Constructor
     * @param image_title - Image Title type of {@link String}
     * @param image_description - Image Description type of {@link String}
     * @param image - Image type of {@link byte[]}
     */
    public EImages(String image_title, String image_description, byte[] image) {
        this.image_title = image_title;
        this.image_description = image_description;
        this.image = image;
    }

    public int getImage_uid() {
        return image_uid;
    }

    // get title
    public String getImage_title() {
        return image_title;
    }

    // get descr
    public String getImage_description() {
        return image_description;
    }

    // get image
    public byte[] getImage() {
        return image;
    }
}
