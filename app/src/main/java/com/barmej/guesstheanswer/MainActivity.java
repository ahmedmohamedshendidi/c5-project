package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewQuestion;
    Random random = new Random() ;
    int randomQuestionIndex;
    private  String[] QUESTIONS ;
    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false};

    private  String[] ANSWERS_DETAILS;
    private String mCurrentQuestion, mCurrentAnswerDetail;
    private boolean mCurrentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("app pref",MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang","");
        if(!appLang.equals(""))
            LocaleHelper.setLocale(this,appLang);
        setContentView(R.layout.activity_main);
        mTextViewQuestion = findViewById(R.id.text_view_question);
        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);
        showNewQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuChangeLang){
            showLanguageDialog();
            return true ;
        }else {return super.onOptionsItemSelected(item);}
    }

    private void showLanguageDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
        .setTitle(R.string.change_lang_text)
        .setItems(R.array.languages, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface , int which){
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
                LocaleHelper.setLocale(MainActivity.this, language);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).create();
        alertDialog.show();
    }
    private void saveLanguage(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.APP_LANG,lang);
        editor.apply();
    }

    private void showNewQuestion(){
        randomQuestionIndex = random.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomQuestionIndex];
        mCurrentAnswer = ANSWERS[randomQuestionIndex];
        mCurrentAnswerDetail = ANSWERS_DETAILS[randomQuestionIndex];
        mTextViewQuestion.setText(mCurrentQuestion);
    }
    public void onChangeQuestionClicked(View view){
        showNewQuestion();
    }
    public void onTrueClicked(View view){
        if(mCurrentAnswer == true) {
            Toast.makeText(this, "إجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "إجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Answer_activity.class);
            intent.putExtra("question_answer",mCurrentAnswerDetail);
            startActivity(intent);
        }
    }
    public void onFalseClicked(View view){
        if(mCurrentAnswer == false) {
            Toast.makeText(this, "إجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "إجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Answer_activity.class);
            intent.putExtra("question_answer",mCurrentAnswerDetail);
            startActivity(intent);
        }
    }
    public void onShareQuestionClicked(View view){
        Intent intent = new Intent(MainActivity.this,ShareActivity.class);
        intent.putExtra("question text extra",mCurrentQuestion);
        startActivity(intent);
    }
}