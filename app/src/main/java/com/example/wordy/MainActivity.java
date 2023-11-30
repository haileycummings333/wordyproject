package com.example.wordy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    EditText[][] editTextArrays = new EditText[6][5]; // 6 rows, 5 columns
    private String targetWord;
    String guessingWord;
    //private int attemptsLeft;
    //boolean exists;
    private List<Character> targetWordLetters;
    EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8, edt9, edt10,
            edt11, edt12, edt13, edt14, edt15, edt16, edt17, edt18, edt19,
            edt20, edt21, edt22, edt23, edt24, edt25, edt26, edt27,
            edt28, edt29, edt30;
    Button addWordButton, restartButton, clearButton, submitButton, hardModeButton, clearGameButton;
    DatabaseReference wordsRef;
    int currentRowIndex = 0;
    boolean hardModeStatus;
    TextView hardMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordsRef = FirebaseDatabase.getInstance().getReference("words");
        //exists = false;
        hardModeStatus = false; //by default hard mode is OFF
        hardMode = findViewById(R.id.hardModeTextView);


        //initialize all the edittexts and add to array for row
        //row 1
        edt1 = findViewById(R.id.et_1);
        edt2 = findViewById(R.id.et_2);
        edt3 = findViewById(R.id.et_3);
        edt4 = findViewById(R.id.et_4);
        edt5 = findViewById(R.id.et_5);
        editTextArrays[0] = new EditText[]{edt1, edt2, edt3, edt4, edt5};
        //row 2
        edt6 = findViewById(R.id.et_6);
        edt7 = findViewById(R.id.et_7);
        edt8 = findViewById(R.id.et_8);
        edt9 = findViewById(R.id.et_9);
        edt10 = findViewById(R.id.et_10);
        editTextArrays[1] = new EditText[]{edt6, edt7, edt8, edt9, edt10};
        //row 3
        edt11 = findViewById(R.id.et_11);
        edt12 = findViewById(R.id.et_12);
        edt13 = findViewById(R.id.et_13);
        edt14 = findViewById(R.id.et_14);
        edt15 = findViewById(R.id.et_15);
        editTextArrays[2] = new EditText[]{edt11, edt12, edt13, edt14, edt15};
        //row 4
        edt16 = findViewById(R.id.et_16);
        edt17 = findViewById(R.id.et_17);
        edt18 = findViewById(R.id.et_18);
        edt19 = findViewById(R.id.et_19);
        edt20 = findViewById(R.id.et_20);
        editTextArrays[3] = new EditText[]{edt16, edt17, edt18, edt19, edt20};
        //row 5
        edt21 = findViewById(R.id.et_21);
        edt22 = findViewById(R.id.et_22);
        edt23 = findViewById(R.id.et_23);
        edt24 = findViewById(R.id.et_24);
        edt25 = findViewById(R.id.et_25);
        editTextArrays[4] = new EditText[]{edt21, edt22, edt23, edt24, edt25};
        //row 6
        edt26 = findViewById(R.id.et_26);
        edt27 = findViewById(R.id.et_27);
        edt28 = findViewById(R.id.et_28);
        edt29 = findViewById(R.id.et_29);
        edt30 = findViewById(R.id.et_30);
        editTextArrays[5] = new EditText[]{edt26, edt27, edt28, edt29, edt30};

        restartGame();
        addWordButton = findViewById(R.id.addButton);
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Words.class);
                startActivity(intent);
            }
        });


        //initialize submit button
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hardModeStatus)
                    checkGuess(editTextArrays[currentRowIndex]);
                else
                    hardModeGuess(editTextArrays[currentRowIndex]);
            }
        });


        //initialize clear button
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRow(currentRowIndex);
            }
        });


        //initialize hard mode button
        hardModeButton = findViewById(R.id.hardModeButton);
        hardModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardModeStatus = !hardModeStatus;
                if (hardModeStatus)
                    hardMode.setText(R.string.hard_mode_on);
                else
                    hardMode.setText(R.string.hard_mode_off);
            }
        });

        //initialize clear game button (restart game with SAME word)
        clearGameButton =findViewById(R.id.clearGameButton);
        clearGameButton. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGame();
            }
        });


        //initialize restart button (restart game with DIFFERENT word)
        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    private void clearGame(){
        for (int i = 0; i < 6; i++) {
            clearRow(i);
        }
        startAgain();

    }


    private void clearRow(int rowIndex) {
        EditText[] currentRow = editTextArrays[rowIndex];
        for (EditText editText : currentRow) {
            editText.setText("");
            editText.setBackgroundColor(Color.WHITE);
            editText.setTextColor(Color.BLACK);
            //editText.setBackground(getDrawable(R.drawable.border));
        }
    }

    private void enableRow(int rowIndex) {
        EditText[] currentRow = editTextArrays[rowIndex];
        for (EditText editText : currentRow) {
            editText.setEnabled(true);
        }
    }

    private void disableRow(int rowIndex) {
        EditText[] currentRow = editTextArrays[rowIndex];
        for (EditText editText : currentRow) {
            editText.setEnabled(false);
        }
    }

    private void restartGame() {
        //exists = false;
        hardModeStatus = false;
        //get a new word
        loadRandomWord();
        //clear all the rows
        for (int i = 0; i < 6; i++) {
            clearRow(i);
        }
        startAgain();

    }

    private void startAgain(){
        //start back at line one, disable all other rows
        currentRowIndex = 0;
        enableRow(currentRowIndex);
        disableRow(1);
        disableRow(2);
        disableRow(3);
        disableRow(4);
        disableRow(5);
    }

    //load words from firebase into a list
    private void loadRandomWord() {
        wordsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> wordList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String word = snapshot.getValue(String.class);
                    if (word != null) {
                        wordList.add(word);
                    }
                }
                if (!wordList.isEmpty()) {
                    // select a random word from the list
                    targetWord = getRandomWord(wordList);
                    // for hard mode, store the letters of the target word in a list
                    //targetWordLetters = Arrays.asList(targetWord.split(""));
                    char[] charArray = targetWord.toCharArray();
                    targetWordLetters = new ArrayList<>();
                    for (char c : charArray) {
                        targetWordLetters.add(c);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //selects a random word from the world list
    private String getRandomWord(List<String> wordList) {
        if (wordList != null && !wordList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(wordList.size());
            return wordList.get(randomIndex);
        } else {
            //stops app if no words available
            throw new IllegalStateException("No words available");
        }
    }

    public void checkGuess(EditText[] editArray) {
            EditText et1 = editArray[0];
            EditText et2 = editArray[1];
            EditText et3 = editArray[2];
            EditText et4 = editArray[3];
            EditText et5 = editArray[4];

            guessingWord = et1.getText().toString() + et2.getText().toString() + et3.getText().toString()
                    + et4.getText().toString() + et5.getText().toString();

            //get char for each letter entered to compare to letters of target word
            char editText1 = et1.getText().toString().toLowerCase().charAt(0);
            char editText2 = et2.getText().toString().toLowerCase().charAt(0);
            char editText3 = et3.getText().toString().toLowerCase().charAt(0);
            char editText4 = et4.getText().toString().toLowerCase().charAt(0);
            char editText5 = et5.getText().toString().toLowerCase().charAt(0);
            //get char for each letter of target word to compare to guess
            char l1 = targetWord.charAt(0);
            char l2 = targetWord.charAt(1);
            char l3 = targetWord.charAt(2);
            char l4 = targetWord.charAt(3);
            char l5 = targetWord.charAt(4);


        //check if word guessed exists in database, if it doesn't tells user invalid guess word
        wordsRef.orderByValue().equalTo(guessingWord).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //exists = true;
                    // compares if letter a letter in word but in wrong spot, changes to yellow
                    if (editText1 == l2 || editText1 == l3 || editText1 == l4 || editText1 == l5) {
                        et1.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    // compares if later entered in edt is equal to any of the other letters
                    if (editText2 == l1 || editText2 == l3 || editText2 == l4 || editText2 == l5) {
                        et2.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    // compares if later entered in edt is equal to any of the other letters
                    if (editText3 == l1 || editText3 == l2 || editText3 == l4 || editText3 == l5) {
                        et3.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    // compares if later entered in edt is equal to any of the other letters
                    if (editText4 == l1 || editText4 == l2 || editText4 == l3 || editText4 == l5) {
                        et4.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    // compares if later entered in edt is equal to any of the other letters
                    if (editText5 == l1 || editText5 == l2 || editText5 == l3 || editText5 == l4) {
                        et5.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    // checks if letter is equal to letter in word, changes color to green
                    if (editText1 == l1) {
                        et1.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (editText2 == l2) {
                        et2.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (editText3 == l3) {
                        et3.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (editText4 == l4) {
                        et4.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (editText5 == l5) {
                        et5.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    //checks if none of the letters are in the word, changes colors to gray
                    if (editText1 != l1 && editText1 != l2 && editText1 != l3 && editText1 != l4 && editText1 != l5) {
                        et1.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    if (editText2 != l1 && editText2 != l2 && editText2 != l3 && editText2 != l4 && editText2 != l5) {
                        et2.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    if (editText3 != l1 && editText3 != l2 && editText3 != l3 && editText3 != l4 && editText3 != l5) {
                        et3.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    if (editText4 != l1 && editText4 != l2 && editText4 != l3 && editText4 != l4 && editText4 != l5) {
                        et4.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    if (editText5 != l1 && editText5 != l2 && editText5 != l3 && editText5 != l4 && editText5 != l5) {
                        et5.setBackgroundColor(getResources().getColor(R.color.gray));
                    }

                    //if on last row and not all are green, then user lost
                    if(currentRowIndex==5){
                        Toast.makeText(getApplicationContext(), "You lost! The correct word was: " + targetWord, Toast.LENGTH_LONG).show();
                        restartGame();
                    }

                    //go to next row
                    disableRow(currentRowIndex);
                    currentRowIndex++;
                    enableRow(currentRowIndex);

                    //if all letters are correct and in right spot then win
                    if (editText1 == l1 && editText2 == l2 && editText3 == l3 && editText4 == l4 && editText5 == l5) {
                        Toast.makeText(getApplicationContext(), "You have guessed the right word!", Toast.LENGTH_LONG).show();
                        restartGame();
                    }


                } else {
                    //exists = false;
                    clearRow(currentRowIndex);
                    Toast.makeText(getApplicationContext(), "Word Not In Database", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle errors, if any
            }
        });

    }

    private List<Character> guessedLetters = new ArrayList<>();

    private void hardModeGuess(EditText[] editArray) {
        wordsRef.orderByValue().equalTo(guessingWord).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    // check if all letters are guessed correctly
                    boolean allCorrect = true;

                    for (int i = 0; i < editArray.length; i++) {
                        EditText editText = editArray[i];
                        char guessedLetter = editText.getText().toString().toLowerCase().charAt(0);
                        char targetLetter = targetWordLetters.get(i);

                        if (guessedLetter == targetLetter) {
                            // correct letter in the correct spot (green)
                            editText.setBackgroundColor(getResources().getColor(R.color.green));
                            guessedLetters.add(guessedLetter);
                        } else if (guessedLetters.contains(guessedLetter)) {
                            // correct letter in the wrong spot (yellow)
                            editText.setBackgroundColor(getResources().getColor(R.color.yellow));
                        } else {
                            // incorrect letter
                            allCorrect = false;
                            guessedLetters.remove(guessedLetter); // Remove if it was previously guessed
                            editText.setBackgroundColor(getResources().getColor(R.color.gray));
                        }
                    }

                    if (allCorrect) {
                        Toast.makeText(getApplicationContext(), "You have guessed the right word!", Toast.LENGTH_LONG).show();
                        restartGame();
                    } else {
                        // Move to the next row
                        disableRow(currentRowIndex);
                        currentRowIndex++;
                        enableRow(currentRowIndex);

                        if (currentRowIndex == 5) {
                            Toast.makeText(getApplicationContext(), "You lost! The correct word was: " + targetWord, Toast.LENGTH_LONG).show();
                            restartGame();
                        }
                    }
                }
            }
        @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // handle errors, if any
                }
    });
}

















    //check if word guessed is available in the database
//    private void checkWordAvailability(String word) {
//        wordsRef.orderByValue().equalTo(word).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    exists = true;
//                } else {
//                    exists = false;
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // handle errors, if any
//            }
//        });
//    }
}