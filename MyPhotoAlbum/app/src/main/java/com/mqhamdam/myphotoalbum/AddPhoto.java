package com.mqhamdam.myphotoalbum;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPhoto extends AppCompatActivity {

   private ImageButton imageButton;
   private Button btn;
   private EditText editTextTitle, editTextDescription;

   ActivityResultLauncher<Intent> activityResultLauncher;
   private Bitmap selectedImage;
   private Bitmap reducedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        imageButton = findViewById(R.id.addImageBtn);
        btn = findViewById(R.id.btnAdd);
        editTextTitle = findViewById(R.id.addTitle);
        editTextDescription = findViewById(R.id.addDescr);

        registerActivityForSelectImage();

        imageButton.setOnClickListener((v)->{
            Snackbar.make(v, "Select an image", Snackbar.LENGTH_LONG).show();
            if(ContextCompat.checkSelfPermission(AddPhoto.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                Snackbar.make(v, "Permission not allowed", Snackbar.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            } else {
                Snackbar.make(v, "Permission allowed", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //
                activityResultLauncher.launch(intent);

            }
        });

        btn.setOnClickListener((v)->{
            if(selectedImage == null){
                Snackbar.make(v, "Select an image", Snackbar.LENGTH_LONG).show();
                return;
            }else {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                reducedImage = makeSmall(selectedImage, 300);
                reducedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                byte[] byteArray = outputStream.toByteArray();

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("image", byteArray);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void registerActivityForSelectImage() {
    activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK){
            Intent intent = result.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                imageButton.setImageBitmap(selectedImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //
            activityResultLauncher.launch(intent);
        }
    }

    public Bitmap makeSmall(Bitmap bitmap,int maxSize){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float ratio = (float) width / (float) height;
        if(ratio > 1){
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}