package com.mqhamdam.myphotoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class UpdatePhoto extends AppCompatActivity {

    ImageButton imageButton;
    EditText editTextTitle, editTextDescription;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo);

        imageButton = findViewById(R.id.addImageBtn);
        editTextTitle = findViewById(R.id.updTitle);
        editTextDescription = findViewById(R.id.updDescr);
        btn = findViewById(R.id.btnUpd);
    }
}