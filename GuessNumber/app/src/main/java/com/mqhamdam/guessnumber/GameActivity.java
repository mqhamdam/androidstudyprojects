package com.mqhamdam.guessnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    // 1 two digits, 2 three digits, 3 four digits
    private int difficulty;
    private int generatedNumber;

    private ArrayList<Integer> guessedNumbers = new ArrayList<>();
    private int guessedNumber;
    private int attempts;
    private int guessLimit;

    private TextView textLastGuess;
    private TextView textGuessAttempts;
    private TextView textHintIncreaseOrDecrease;

    private EditText editGuess;
    private Button btnSubmit;

    // random generator
    private int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // get difficulty level from MainActivity
        difficulty = getIntent().getIntExtra("difficulty", 1);

        View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            int screenHeight = rootView.getRootView().getHeight();
            int keyboardHeight = screenHeight - rect.bottom;

            boolean isKeyboardVisible = keyboardHeight > screenHeight * 0.15; // Adjust the threshold as needed

            adjustPaddingForKeyboard(isKeyboardVisible);
        });

        // get resources
        textLastGuess = findViewById(R.id.textLastGuess);
        textGuessAttempts = findViewById(R.id.textAttempts);
        textHintIncreaseOrDecrease = findViewById(R.id.textHint);
        editGuess = findViewById(R.id.editTextNumber);
        btnSubmit = findViewById(R.id.btnConfirm);

        textLastGuess.setVisibility(View.INVISIBLE);
        textGuessAttempts.setVisibility(View.INVISIBLE);
        textHintIncreaseOrDecrease.setVisibility(View.INVISIBLE);

        // set guess limit and generate number based on difficulty level
        switch (difficulty) {
            case 1:
                guessLimit = 5;
                generatedNumber = getRandomNumber(10, 100);
                break;
            case 2:
                guessLimit = 10;
                generatedNumber = getRandomNumber(100, 1000);
                break;
            case 3:
                guessLimit = 15;
                generatedNumber = getRandomNumber(1000, 10000);
                break;
        }

        editGuess.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN) {
                // Perform your desired action here
                // For example, you can call a method or handle the keyboard input
                onPerformAction(v);
                return true;
            }
            return false;
        });

        btnSubmit.setOnClickListener(this::onPerformAction);
    }

    // show Success dialog
    private void showSuccessDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("You won!");
        String message = "You guessed the number correctly!\n\n" +
                "Number: " + generatedNumber + "\n" +
                "Attempts: " + attempts + "\n" +
                "Guessed numbers: " + guessedNumbers.toString() + "\n\n"
                + "Play again?";
        builder.setMessage(message);
        builder.setPositiveButton("YES!", (dialog, which) -> {
            // restart activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        builder.setNegativeButton("NO!", (dialog, which) -> finish());
        builder.show();
    }

    /// show fail dialog
    private void showFailDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("You Lose!");
        String message = "You are out of attempts!\n\n" +
                "Attempts: " + attempts + "\n" +
                "Guessed numbers: " + guessedNumbers.toString() + "\n\n" +
                "Generated number: " + generatedNumber + "\n\n"
                + "Play again?";
        builder.setMessage(message);
        builder.setPositiveButton("YES!", (dialog, which) -> {
            // restart activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        builder.setNegativeButton("NO!", (dialog, which) -> finish());
        builder.show();
    }

    // method on action button click
    private void onPerformAction(View v){
        if(editGuess.getText().toString().isEmpty()){
            // show a snack-bar if no number is entered
            Snackbar.make(v, "Please enter a number", Snackbar.LENGTH_LONG).show();
            return;
        }

        guessedNumber = Integer.parseInt(editGuess.getText().toString());

        // push to guessed numbers array
        guessedNumbers.add(guessedNumber);

        if(guessedNumber == generatedNumber){
            // show a snack-bar if the number is correct
            Snackbar.make(v, "You guessed the number correctly!", Snackbar.LENGTH_LONG).show();
            showSuccessDialog(v);
        } else {
            attempts++;
            textLastGuess.setText("Last guess: " + guessedNumber);
            textGuessAttempts.setText("Guess attempts: " + attempts + "/" + guessLimit);
            textLastGuess.setVisibility(View.VISIBLE);
            textGuessAttempts.setVisibility(View.VISIBLE);

            // set hint
            if(guessedNumber > generatedNumber){
                textHintIncreaseOrDecrease.setText("Hint: Decrease");
                textHintIncreaseOrDecrease.setTextColor(getResources().getColor(R.color.red));
            } else {
                textHintIncreaseOrDecrease.setText("Hint: Increase");
                textHintIncreaseOrDecrease.setTextColor(getResources().getColor(R.color.green));
            }
            textHintIncreaseOrDecrease.setVisibility(View.VISIBLE);
            if(attempts == guessLimit){
                showFailDialog(v);
                // show a snack-bar if the number of attempts is equal to the guess limit
                Snackbar.make(v, "You have reached the guess limit", Snackbar.LENGTH_LONG).show();
            }
        }
    }
    private void adjustPaddingForKeyboard(boolean isVisible) {
        View rootView = findViewById(android.R.id.content);
        int bottomPadding = isVisible ? getNavigationBarHeight() : 0;
        rootView.setPadding(0, 0, 0, bottomPadding);
    }
    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}