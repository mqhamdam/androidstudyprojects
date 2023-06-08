package com.mqhamdam.myphotoalbum;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImagesRepo {
    private final ImagesDao imagesDao;
    private LiveData<List<EImages>> allImages;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ImagesRepo(Application application) {
        ImagesDB imagesDB = ImagesDB.getInstance(application);
        imagesDao = imagesDB.imagesDao();
        allImages = imagesDao.getAllImages();
    }



    public void insert(EImages eImages) {
    //  new InsertImageAsyncTask(imagesDao).execute(eImages); // execute is deprecated
        executorService.execute(() -> imagesDao.insert(eImages));
    }

    public void delete(EImages eImages) {
//        new DeleteImageAsyncTask(imagesDao).execute(eImages);
        executorService.execute(() -> imagesDao.delete(eImages));
    }

    public void update(EImages eImages) {
//        new UpdateImageAsyncTask(imagesDao).execute(eImages);
        executorService.execute(() -> imagesDao.update(eImages));
    }

    public LiveData<List<EImages>> getAllImages() {
        return allImages;
    }

    private static class InsertImageAsyncTask extends AsyncTask<EImages,Void,Void>{
        private ImagesDao imagesDao;

        public InsertImageAsyncTask(ImagesDao imagesDao) {
            this.imagesDao = imagesDao;
        }

        @Override
        protected Void doInBackground(EImages... eImages) {
            imagesDao.insert(eImages[0]);
            return null;
        }
    }

    private static class UpdateImageAsyncTask extends AsyncTask<EImages,Void,Void>{
        private ImagesDao imagesDao;

        public UpdateImageAsyncTask(ImagesDao imagesDao) {
            this.imagesDao = imagesDao;
        }

        @Override
        protected Void doInBackground(EImages... eImages) {
            imagesDao.update(eImages[0]);
            return null;
        }
    }

    private static class DeleteImageAsyncTask extends AsyncTask<EImages,Void,Void>{
        private ImagesDao imagesDao;

        public DeleteImageAsyncTask(ImagesDao imagesDao) {
            this.imagesDao = imagesDao;
        }

        @Override
        protected Void doInBackground(EImages... eImages) {
            imagesDao.delete(eImages[0]);
            return null;
        }
    }
}
