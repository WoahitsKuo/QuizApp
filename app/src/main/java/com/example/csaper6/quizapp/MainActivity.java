package com.example.csaper6.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int currentIndex;
    private int score;
    //FOR THE EXTRAS SET IN INTENT
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CURRENT_QUESTION = "current question";
    public static final String EXTRA_CURRENT_ANSWER = "current answer";
    //FOR THE ACTIVITY RESULT
    public static final int REQUEST_CHEATED = 0;
    private boolean hasCheated = false;
    private boolean cheatingEnabled;
    private Button nextButton;
    private Button previousButton;
    private Button cheatButton;
    private Button trueButton;
    private Button falseButton;
    private TextView questionTextView;
    private TextView scoreTextView;
    private Question [] questionBank = {
            new Question(R.string.question_text, true),
            new Question(R.string.question_type, false),
            new Question(R.string.question_space, true),
            new Question(R.string.question_segway, true),
            new Question(R.string.question_worm, false),
            new Question(R.string.question_end, true)


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. wire the buttons and the TextView
        trueButton = (Button) findViewById(R.id.button_true);
        nextButton = (Button) findViewById(R.id.button_next);
        falseButton = (Button) findViewById(R.id.button_false);
        previousButton = (Button) findViewById(R.id.button_previous);
        questionTextView = (TextView) findViewById(R.id.textView_question);
        scoreTextView = (TextView) findViewById(R.id.textView_score);
        cheatButton = (Button) findViewById(R.id.button_cheat);
        //2. create a new question object from
        //the string resources
        //make a question object & pass the string resource
        //& answer in the constructor
        Question q1 = new Question(R.string.question_text, true);
        Question q2 = new Question(R.string.question_type, false);

        //3. Set the textView's text to the question's text
        currentIndex = 0;
        score = 0;
        questionTextView.setText(questionBank[currentIndex].getQuestionId());
        scoreTextView.setText("" + score);

        //4. on clicks
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer(true);
                lockButtons();

            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to go from one Activity to another
                Intent openCheatActivity = new Intent(MainActivity.this, CheatActivity.class);
                //load up the intent with extra information to take to the new activity
                openCheatActivity.putExtra(EXTRA_MESSAGE, "Ahoy from MainActivity");
                openCheatActivity.putExtra(EXTRA_CURRENT_QUESTION, questionBank[currentIndex].getQuestionId());
                openCheatActivity.putExtra(EXTRA_CURRENT_ANSWER, questionBank[currentIndex].isAnswerTrue());

                //startActivity(openCheatActivity);
                //make constant for request code to identify what result we're trying to get
                startActivityForResult(openCheatActivity, REQUEST_CHEATED);
            }
        });
        cheatingEnabled = false;
        cheatButton.setVisibility(View.GONE);

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                lockButtons();
            }
        });
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQ();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQ();

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unupdateQ();
            }
        });
    }


    private void checkAnswer(boolean userResponse) {
        if(questionBank[currentIndex].checkAnswer(userResponse)){
            Toast.makeText(MainActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT).show();
            score++;
            scoreTextView.setText("" + score);

        } else {
            Toast.makeText(MainActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
        }


    }



    private void updateQ() {
        currentIndex++;
        currentIndex = currentIndex % questionBank.length;
        if (currentIndex == (0)){
            score = 0;
            scoreTextView.setText("" + score);
        }
        questionTextView.setText(questionBank[currentIndex].getQuestionId());
        unlockButtons();
    }

    private void unupdateQ() {
        currentIndex--;
        if (currentIndex < 0)
        {
            currentIndex = questionBank.length-1;
        }
        questionTextView.setText(questionBank[currentIndex].getQuestionId());
        unlockButtons();
    }

    public void lockButtons(){
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    public void unlockButtons(){
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cheat:
                toggleCheating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleCheating() {
        if(cheatingEnabled) {
            cheatingEnabled = false;
            cheatButton.setVisibility(View.GONE);
        } else
        {
            cheatingEnabled = true;
            cheatButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CHEATED) {

            hasCheated = data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);
            Log.d("hi", "onActivityResult: "+ hasCheated);


        }
        if(hasCheated){
            Toast.makeText(MainActivity.this, R.string.toast_cheated, Toast.LENGTH_SHORT).show();
            lockButtons();
        }
    }
}
