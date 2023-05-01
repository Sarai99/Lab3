package com.example.devoir1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class AddCardActivity2 extends AppCompatActivity {
    private EditText editTextQuest;
    private EditText editTextRep;
    private EditText editTextRep1;
    private EditText editTextRep2;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_card2);
        ImageView btn = findViewById(R.id.myBtnCancel);
        ImageView btnSave = findViewById(R.id.myBtnSave);

        editTextQuest = findViewById(R.id.textEditQuestion);
        editTextRep = findViewById(R.id.editTextReponse);
        editTextRep1 = findViewById(R.id.editTextReponse2);
        editTextRep2 = findViewById(R.id.editTextReponse3);
        coordinatorLayout= findViewById(R.id.coordinator);


        btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(AddCardActivity2.this,MainActivity.class);
                        //startActivity(i);

                        editTextQuest.setText("");
                        editTextRep.setText("");
                        editTextRep1.setText("");
                        editTextRep2.setText("");
                    }
                }
        );



        btnSave.setOnClickListener(new  View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Pass in the m ime type you want to let the user select
                    // as the input
                    String quest1 = editTextQuest.getText().toString();
                    String rep1 = editTextRep.getText().toString();
                    String rep2 = editTextRep1.getText().toString();
                    String rep3 = editTextRep2.getText().toString();

                    Intent i = new Intent(AddCardActivity2.this,MainActivity.class);

                    i.putExtra("question",quest1);
                    i.putExtra("reponse1",rep1);
                    i.putExtra("reponse2",rep2);
                    i.putExtra("reponse3",rep3);

                    setResult(78,i);
                    finish();
                    //showsnackbar();
                   // AddCardActivity2.super.onBackPressed();
                }
            });



        String Quest1 =getIntent().getStringExtra("Question");
        String Rep1 =getIntent().getStringExtra("Reponse1");
        String Rep2 =getIntent().getStringExtra("Reponse2");
        String Rep3 =getIntent().getStringExtra("Reponse3");


        editTextQuest.setText(Quest1);
        editTextRep.setText(Rep1);
        editTextRep1.setText(Rep2);
        editTextRep2.setText(Rep3);

    }



    void showsnackbar()
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"This is a snackbar",Snackbar.LENGTH_INDEFINITE)
                .setAction("UNDO",new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View view) {
                               Snackbar snackbar1 = Snackbar.make(coordinatorLayout,"Undo successful",Snackbar.LENGTH_SHORT);
                               snackbar1.show();
                            }
                        }
                );
    }

}