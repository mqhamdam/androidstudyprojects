package com.mqhamdam.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button buttonStart;
    private RadioButton r1,r2,r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.btnStart);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                if(!r1.isChecked() && !r2.isChecked() && !r3.isChecked()){
                    // Show a snack-bar if no difficulty level is selected
                    Snackbar.make(v, "Please select a difficulty level", Snackbar.LENGTH_LONG).show();
                } else {
                    // Pass the difficulty level to the GameActivity
                    if(r1.isChecked()){
                        intent.putExtra("difficulty", 1);
                    } else if(r2.isChecked()){
                        intent.putExtra("difficulty", 2);
                    } else if(r3.isChecked()){
                        intent.putExtra("difficulty", 3);
                    }
                    startActivity(intent);
                }
            }
        });
    }
}