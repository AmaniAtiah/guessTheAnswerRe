package com.barmej.guesstheanswerre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    private TextView mTextViewAnswer;
    private Button mButtonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mTextViewAnswer = findViewById(R.id.text_view_answer);
        mButtonReturn = findViewById(R.id.button_return);

        String answer = getIntent().getStringExtra("question_answer");
        if (answer != null) {
            mTextViewAnswer.setText(answer);
        }

        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}