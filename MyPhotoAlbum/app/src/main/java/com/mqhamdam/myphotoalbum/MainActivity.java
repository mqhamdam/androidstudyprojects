package com.mqhamdam.myphotoalbum;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImagesViewModel imagesViewModel;

    private RecyclerView rv;
    private FloatingActionButton fab;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerActivityForSelectImage();

        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.addImage);

        rv.setLayoutManager(new LinearLayoutManager(this));

        ImagesAdapter imagesAdapter = new ImagesAdapter();
        rv.setAdapter(imagesAdapter);

        imagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ImagesViewModel.class);

        imagesViewModel.getAllImages().observe(MainActivity.this, new Observer<List<EImages>>() {
            @Override
            public void onChanged(List<EImages> eImages) {
                imagesAdapter.setImages(eImages);
                imagesAdapter.notifyDataSetChanged();
            }
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPhoto.class);
            activityResultLauncher.launch(intent);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                imagesViewModel.delete(imagesAdapter.getImageAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(rv);
    }


    // method to register result
    public void registerActivityForSelectImage() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int resultCode = result.getResultCode();
            Intent data = result.getData();
            if (resultCode == RESULT_OK && data != null) {

                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                byte[] image = data.getByteArrayExtra("image");

                EImages newImage = new EImages(title, description, image);
                Snackbar.make(rv, "Image added", Snackbar.LENGTH_LONG).show();
                imagesViewModel.insert(newImage);
            }

        });
    }
}