package com.barmej.guesstheanswerre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    private TextView mTextViewQuestion;

    private String[] QUESTIONS;

    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true
    };

    private String[] ANSWERS_DETAILS;


    private String mCurrentQuestion, mCurrentAnswerDetails;
    private boolean mCurrentAnswer;
    private ImageButton changeQuestionButton;
    private Button buttonTrue;
    private Button buttonFalse;
    private ImageButton shareQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref",MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang", "");
        if (!appLang.equals("")) {
            LocaleHelper.setLocale(this, appLang);
        }
        mTextViewQuestion = findViewById(R.id.text_view_question);
        changeQuestionButton = findViewById(R.id.button_change_question);
        buttonTrue = findViewById(R.id.button_true);
        buttonFalse = findViewById(R.id.button_false);
        shareQuestionButton = findViewById(R.id.button_share_question);

        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);
        showNewQuestion();

        changeQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewQuestion();
            }
        });

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrueClicked();

            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFalseClicked();
            }
        });

        shareQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuestionClicked();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuChangeLang){
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);

        }
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }

                        saveLanguage(language);
                        LocaleHelper.setLocale(QuestionActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }).create();
        alertDialog.show();


    }

    private void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang", lang);
        editor.apply();


    }

    private void showNewQuestion() {
        Random random = new Random();
        int randomQuestionIndex = random.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomQuestionIndex];
        mCurrentAnswer = ANSWERS[randomQuestionIndex];
        mCurrentAnswerDetails = ANSWERS_DETAILS[randomQuestionIndex];
        mTextViewQuestion.setText(mCurrentQuestion);

    }

    private void onTrueClicked() {
        if (mCurrentAnswer == true) {
            Toast.makeText(this,"اجابة صحيحة",Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this,"اجابة خاطئة",Toast.LENGTH_SHORT).show();
            intentAnswer();

        }
    }

    private void onFalseClicked() {
        if (mCurrentAnswer == false) {
            Toast.makeText(this,"اجابة صحيحة",Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this,"اجابة خاطئة",Toast.LENGTH_SHORT).show();
            intentAnswer();
        }
    }

    private void intentAnswer() {
        Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
        intent.putExtra("question_answer", mCurrentAnswerDetails);
        startActivity(intent);
    }

    private void shareQuestionClicked() {
        Intent intent = new Intent(QuestionActivity.this, ShareActivity.class);
        intent.putExtra("question_text_extra", mCurrentQuestion);
        startActivity(intent);
    }


}