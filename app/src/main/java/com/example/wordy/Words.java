package com.example.wordy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class Words extends AppCompatActivity {
    FirebaseDatabase database;
    EditText newWordText;
    Button addButton, clearButton, backButton;
    DatabaseReference wordsRef;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //initialize firebase database
        database = FirebaseDatabase.getInstance();
        wordsRef = FirebaseDatabase.getInstance().getReference("words");

        //initialize stuff from xml
        newWordText = findViewById(R.id.addWordet);
        addButton = findViewById(R.id.addWordButton);
        clearButton = findViewById(R.id.clearDBButton);
        backButton = findViewById(R.id.returnButton);

        //button listeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWord(newWordText);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordsRef.removeValue();
            }

        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Words.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addWord(View view) {
        String newWord = newWordText.getText().toString().toLowerCase(); //convert to lowercase for case-insensitive comparison

        // check if the word is empty or not exactly 5 characters
        if (newWord.length() != 5) {
            Toast.makeText(this, "Word must be exactly 5 characters long.", Toast.LENGTH_LONG).show();
            newWordText.setText("");
            return;
        }

        // check if the word contains only letters
        if (!newWord.matches("[a-z]+")) {
            Toast.makeText(this, "Word must contain only letters.", Toast.LENGTH_LONG).show();
            newWordText.setText("");
            return;
        }

        // query to check if the word already exists in the database
        wordsRef.orderByValue().equalTo(newWord).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // word already exists in the database, don't add
                    Toast.makeText(Words.this, "Word already exists in the database.", Toast.LENGTH_LONG).show();
                    newWordText.setText("");
                } else {
                    // word doesn't already exist so add
                    String key = wordsRef.push().getKey();
                    if (key != null) {
                        wordsRef.child(key).setValue(newWord);
                        Toast.makeText(Words.this, "Word Added", Toast.LENGTH_LONG).show();
                        newWordText.setText("");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle errors, if any
            }
        });
    }


}
