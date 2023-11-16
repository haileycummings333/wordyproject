package com.example.wordy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText[] guessInputs;
    private String targetWord;
    private int attemptsLeft;
    EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8, edt9, edt10,
            edt11, edt12, edt13, edt14, edt15, edt16, edt17, edt18, edt19,
            edt20, edt21, edt22, edt23, edt24, edt25, edt26, edt27,
            edt28, edt29, edt30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all the edittext
        //row 1
        edt1 = findViewById(R.id.et_1);
        edt2 = findViewById(R.id.et_2);
        edt3 = findViewById(R.id.et_3);
        edt4 = findViewById(R.id.et_4);
        edt5 = findViewById(R.id.et_5);
        //row 2
        edt6 = findViewById(R.id.et_6);
        edt7 = findViewById(R.id.et_7);
        edt8 = findViewById(R.id.et_8);
        edt9 = findViewById(R.id.et_9);
        edt10 = findViewById(R.id.et_10);
        //row 3
        edt11 = findViewById(R.id.et_11);
        edt12 = findViewById(R.id.et_12);
        edt13 = findViewById(R.id.et_13);
        edt14 = findViewById(R.id.et_14);
        edt15 = findViewById(R.id.et_15);
        //row 4
        edt16 = findViewById(R.id.et_16);
        edt17 = findViewById(R.id.et_17);
        edt18 = findViewById(R.id.et_18);
        edt19 = findViewById(R.id.et_19);
        edt20 = findViewById(R.id.et_20);
        //row 5
        edt21 = findViewById(R.id.et_21);
        edt22 = findViewById(R.id.et_22);
        edt23 = findViewById(R.id.et_23);
        edt24 = findViewById(R.id.et_24);
        edt25 = findViewById(R.id.et_25);
        //row 6
        edt26 = findViewById(R.id.et_26);
        edt27 = findViewById(R.id.et_27);
        edt28 = findViewById(R.id.et_28);
        edt29 = findViewById(R.id.et_29);
        edt30 = findViewById(R.id.et_30);

        // Load a random word from Firebase as the target word, SHOULD BE RANDOM
        loadRandomWord();


        //initialize submit button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });


        //initialize restart button
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //row 1 blank
                edt1.setText("");
                edt2.setText("");
                edt3.setText("");
                edt4.setText("");
                edt5.setText("");
                //row 2 blank
                edt6.setText("");
                edt7.setText("");
                edt8.setText("");
                edt9.setText("");
                edt10.setText("");
                //row 3 blank
                edt11.setText("");
                edt12.setText("");
                edt13.setText("");
                edt14.setText("");
                edt15.setText("");
                //row 4 blank
                edt16.setText("");
                edt17.setText("");
                edt18.setText("");
                edt19.setText("");
                edt20.setText("");
                //row 5 blank
                edt21.setText("");
                edt22.setText("");
                edt23.setText("");
                edt24.setText("");
                edt25.setText("");
                //row 6 blank
                edt26.setText("");
                edt27.setText("");
                edt28.setText("");
                edt29.setText("");
                edt30.setText("");
            }
        });



    }


    private void loadRandomWord() {
        //
    }

    private void checkGuess() {
        //
    }

}
