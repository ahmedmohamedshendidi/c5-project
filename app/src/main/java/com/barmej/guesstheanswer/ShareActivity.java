package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {
    private String mQuestionText;
    public EditText mEditTextShareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        mQuestionText = getIntent().getStringExtra("question text extra");
        SharedPreferences sharedPreferences = getSharedPreferences("app pref", MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("share_title","");

        mEditTextShareTitle.setText(questionTitle);

    }
    public void onShareQuestionClicked(View view){
        String questionTitle = mEditTextShareTitle.getText().toString();
        Intent ShareIntent = new Intent();
        ShareIntent.setAction(Intent.ACTION_SEND);
        ShareIntent.putExtra(Intent.EXTRA_TEXT,questionTitle + "\n" + mQuestionText);
        ShareIntent.setType("text/plain");
        startActivity(ShareIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("app pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share_title", questionTitle);
        editor.apply();

    }
}