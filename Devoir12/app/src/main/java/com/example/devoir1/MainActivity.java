package com.example.devoir1;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView ch_1;
    private TextView ch_2 ;
    private TextView ch_3;
    private TextView rep1;
    private TextView ques1;

    @NotNull
    private FlashcardDatabase flashcardDatabase;

    List<Flashcard> allFlashcards = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ques1 = findViewById(R.id.question1) ;
        rep1 = findViewById(R.id.reponse) ;
        rep1.setVisibility(INVISIBLE);

        ch_1 = findViewById(R.id.choix_1);
        ch_2 = findViewById(R.id.choix_2);
        ch_3 = findViewById(R.id.choix_3);

        flashcardDatabase = new FlashcardDatabase(this);
        flashcardDatabase.initFirstCard();
        allFlashcards = new ArrayList<>(flashcardDatabase.getAllCards());

        if (allFlashcards.size() > 0) {
            TextView questionTextView = findViewById(R.id.question1);
            TextView reponseTextView = findViewById(R.id.reponse) ;
            TextView answerTextView1 = findViewById(R.id.choix_1);
            TextView answerTextView2 = findViewById(R.id.choix_2);
            TextView answerTextView3 = findViewById(R.id.choix_3);


            questionTextView.setText(allFlashcards.get(0).getQuestion());
            reponseTextView.setText(allFlashcards.get(0).getAnswer());
            answerTextView1.setText(allFlashcards.get(0).getAnswer());
            answerTextView2.setText(allFlashcards.get(0).getWrongAnswer1());
            answerTextView3.setText(allFlashcards.get(0).getWrongAnswer2());
        }

        ques1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something here
                ques1.setVisibility(INVISIBLE);
                rep1.setVisibility(View.VISIBLE);

            }
        });
        rep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something here
                ques1.setVisibility(View.VISIBLE);
                rep1.setVisibility(INVISIBLE);

            }
        });

        ch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something here

                rep1.setVisibility(INVISIBLE);
                ch_1.setBackgroundColor(Color.parseColor("#00FF0C"));


            }
        });
        ch_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something here

                rep1.setVisibility(INVISIBLE);
                ch_2.setBackgroundColor(Color.parseColor("#F40303"));

            }
        });
        ch_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something here

                rep1.setVisibility(INVISIBLE);
                ch_3.setBackgroundColor(Color.parseColor("#F40303"));

            }
        });


       /* String quest =getIntent().getStringExtra("question");
        String Rep1 =getIntent().getStringExtra("reponse1");
        String Rep2 =getIntent().getStringExtra("reponse2");
        String Rep3 =getIntent().getStringExtra("reponse3");


        ques1.setText(quest );
        rep1.setText(Rep1);
        ch_1.setText(Rep1);
        ch_2.setText(Rep2);
        ch_3.setText(Rep3);

        if(ques1.getText().toString()=="" || rep1.getText().toString()=="" || ch_1.getText().toString()==""
                || ch_2.getText().toString()=="" || ch_3.getText().toString()==""
        )
                {
                    ques1.setText(" Who is the 44th president of the United State ");
                    rep1.setText("Barack Obama");
                    ch_1.setText("Barack Obama");
                    ch_2.setText("George H W.Bush");
                    ch_3.setText("Bill Clinton");

                }
*/
        // Define a new ActivityResultLauncher object
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d("data ", "onActivityResult:  from addactivity");

                    if(result.getResultCode()== 78) {
                        Intent data = result.getData();
                        if (data != null) { // Check that we have data returned
                            String quest =data.getStringExtra("question");
                            String Rep1 =data.getStringExtra("reponse1");
                            String Rep2 =data.getStringExtra("reponse2");
                            String Rep3 =data.getStringExtra("reponse3");

                            // Log the value of the strings for easier debugging
                            Log.i("MainActivity", "Question: " + quest);
                            Log.i("MainActivity", "Reponse1: " + Rep1);
                            Log.i("MainActivity", "Reponse2: " + Rep2);
                            Log.i("MainActivity", "Reponse3: " + Rep3);

                            // Display newly created flashcard
                            ques1.setText(quest);
                            rep1.setText(Rep1);
                            ch_1.setText(Rep1);
                            ch_2.setText(Rep2);
                            ch_3.setText(Rep3);

                            if (quest != null && Rep1 != null && Rep2 != null && Rep3 != null) {

                                flashcardDatabase.insertCard(new Flashcard(quest, Rep1, Rep2, Rep3));

                                // Update set of flashcards to include new card
                                allFlashcards = new ArrayList<>(flashcardDatabase.getAllCards());
                            } else {
                                Log.e("TAG", "Missing question or answer to input into database. Question is " + quest + " and answer is " + Rep1 + " and answer is " + Rep2 + " and answer is " + Rep3);
                            }
                        } else {
                            Log.i("MainActivity", "Returned null data from AddCardActivity");
                        }
                    }

                });

        ImageView btn = findViewById(R.id.myBtn);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(MainActivity.this,AddCardActivity2.class);
                        //startActivity(i);
                        Intent intent = new Intent(MainActivity.this, AddCardActivity2.class);
                        resultLauncher.launch(intent);

                    }
                }
        );

        int currentCardDisplayedIndex;
        findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            int currentCardDisplayedIndex = 0;
            @Override
            public void onClick(View v) {
                // don't try to go to next card if you have no cards to begin with
                if (allFlashcards.size() == 0) {
                    // return here, so that the rest of the code in this onClickListener doesn't execute
                    return;
                }


                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(
                                    findViewById(R.id.question1), // This should be the TextView for displaying your flashcard question
                                    "You've reached the end of the cards, going back to start.",
                                    Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = -1;
                }
                else{
                    // set the question and answer TextViews with data from the database
                    allFlashcards = flashcardDatabase.getAllCards();
                    String question = allFlashcards.get(currentCardDisplayedIndex).getQuestion();
                    String answer = allFlashcards.get(currentCardDisplayedIndex).getAnswer();
                    String answer1 = allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1();
                    String answer2 = allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2();

                    ((TextView) findViewById(R.id.reponse)).setText(answer);
                    ((TextView) findViewById(R.id.question1)).setText(question);
                    ((TextView) findViewById(R.id.choix_1)).setText(answer);
                    ((TextView) findViewById(R.id.choix_2)).setText(answer1);
                    ((TextView) findViewById(R.id.choix_3)).setText(answer2);
                }
                // advance our pointer index so we can show the next card

                currentCardDisplayedIndex ++;
            }
        });
        ImageView btnEdit = findViewById(R.id.myBtnEdit);
        btnEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(MainActivity.this,AddCardActivity2.class);
                        //startActivity(i);
                        Intent intent = new Intent(MainActivity.this, AddCardActivity2.class);

                        String Quest1 = ques1.getText().toString();
                        String Rep1 = ch_1.getText().toString();
                        String Rep2 = ch_2.getText().toString();
                        String Rep3 = ch_3.getText().toString();


                        intent.putExtra("Question",Quest1);
                        intent.putExtra("Reponse1",Rep1);
                        intent.putExtra("Reponse2",Rep2);
                        intent.putExtra("Reponse3",Rep3);
                        resultLauncher.launch(intent);

                    }
                }
        );

    }


}
