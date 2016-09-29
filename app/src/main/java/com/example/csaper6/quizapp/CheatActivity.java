package com.example.csaper6.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean hasCheated;
    public static final String EXTRA_CHEATED = "they cheated";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hasCheated = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        TextView questionText = (TextView) findViewById(R.id.cheat_question);
        final TextView questionAnswerText= (TextView) findViewById(R.id.cheat_answer);
        Button cheatButton = (Button) findViewById(R.id.are_you_sure);

        Intent i = getIntent();
        //extracting the extra that was put there
        int questionId = i.getIntExtra(MainActivity.EXTRA_CURRENT_QUESTION, -500);
        final boolean answer = i.getBooleanExtra(MainActivity.EXTRA_CURRENT_ANSWER, false);
        //using that data that was passed to the new activity
        questionText.setText(questionId);

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAnswerText.setText("" + answer);
                hasCheated = true;
                //just using this to send info back, so no parameters
                Intent i = new Intent();
                i.putExtra(EXTRA_CHEATED, hasCheated);
                setResult(RESULT_OK, i);
            }
        });

     }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
