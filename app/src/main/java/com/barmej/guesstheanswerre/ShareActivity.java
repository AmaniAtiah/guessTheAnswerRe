package com.barmej.guesstheanswerre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {
    private Button shareQuestionButton;
    private String mQuestionText;
    private TextView mEditTextShareTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        shareQuestionButton = findViewById(R.id.button_share_question);
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        mQuestionText = getIntent().getStringExtra("question_text_extra");

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("share_title", "");
        mEditTextShareTitle.setText(questionTitle);


        shareQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuestion();
            }
        });
    }

    private void shareQuestion() {
        String questionTitle = mEditTextShareTitle.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, questionTitle + "\n" + mQuestionText);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share_title", questionTitle);
        editor.apply();


    }
}