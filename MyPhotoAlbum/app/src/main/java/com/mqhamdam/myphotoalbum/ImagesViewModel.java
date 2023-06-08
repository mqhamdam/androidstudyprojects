package com.mqhamdam.myphotoalbum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ImagesViewModel extends AndroidViewModel {

    private ImagesRepo imagesRepo;
    private LiveData<List<EImages>> allImages;

    public ImagesViewModel(@NonNull Application application) {
        super(application);

        imagesRepo = new ImagesRepo(application);
        allImages = imagesRepo.getAllImages();
    }

    public void insert(EImages eImages) {
        imagesRepo.insert(eImages);
    }

    public void delete(EImages eImages) {
        imagesRepo.delete(eImages);
    }

    public void update(EImages eImages) {
        imagesRepo.update(eImages);
    }

    public LiveData<List<EImages>> getAllImages() {
        return allImages;
    }
}
