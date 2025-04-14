package com.example.trivia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.data.getquestionserve;
import com.example.trivia.model.Questions;
import com.example.trivia.model.Score;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questiontxt;
    private CardView cardView;
    private Button truebutton;
    private Button falsebutton;
    TextView highscore;
    private ImageButton nextbutton;
    private ImageButton prevbutton;
    private TextView counterquestion;
    private TextView scoretxt;
    private ArrayList<Questions> arrayList;
    private int questionno=0;
    Score finalsocore;

    int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=getSharedPreferences("bala",MODE_PRIVATE);
        scoretxt=findViewById(R.id.txtscore);
        finalsocore=new Score(0);
        highscore=findViewById(R.id.txthighscore);
        highscore.setText("High score: "+sharedPreferences.getInt("bala",0));
        questiontxt=findViewById(R.id.txtquestion);
        cardView=findViewById(R.id.cardView);
        truebutton=findViewById(R.id.btntrue);
        falsebutton=findViewById(R.id.btnfalse);
        nextbutton=findViewById(R.id.btnnext);
        prevbutton=findViewById(R.id.btnprev);
        counterquestion=findViewById(R.id.countertext);
        nextbutton.setOnClickListener(this);
        prevbutton.setOnClickListener(this);
        truebutton.setOnClickListener(this);
        falsebutton.setOnClickListener(this);


    arrayList=new QuestionBank().getquestions(new getquestionserve() {
        @Override
        public void setquestion(ArrayList<Questions> as) {
            updatequestion();
            updatescore();
        }
    });


    }




    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btntrue:
                checkresult("true");
                questionno=(questionno+1)%arrayList.size();
                updatequestion();
                break;
            case R.id.btnfalse:
                checkresult("false");
                questionno=(questionno+1)%arrayList.size();
                updatequestion();
                break;
            case R.id.btnnext:
                questionno=(questionno+1)%arrayList.size();
                updatequestion();
                break;
            case R.id.btnprev:
                if(questionno>0)
                questionno--;
                else questionno=0;
                updatequestion();
                break;
        }
    }

    private void checkresult(String result) {
        if(result==arrayList.get(questionno).getAns())
        {
            score+=10;
            updatescore();
            fadeanim();
            Toast.makeText(this,"That's right",Toast.LENGTH_SHORT).show();
        }
        else
        {
            score=score-5;
            if(score<0)
                score=0;
            updatescore();
            shankeanim();
            Toast.makeText(this,"That's Wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void updatequestion() {
        counterquestion.setText(questionno+" / "+arrayList.size());
        questiontxt.setText(arrayList.get(questionno).getQuestion());
    }
    public void shankeanim()
    {
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                questiontxt.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                questiontxt.setTextColor(getResources().getColor(R.color.was));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        cardView.setAnimation(shake);
    }
    public void fadeanim()
    {
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.8f,0.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
                questiontxt.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                questiontxt.setTextColor(getResources().getColor(R.color.was));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void updatescore() {
        scoretxt.setText("Current Score: "+score);
    }

    @Override
    protected void onPause() {
        super.onPause();
        int ll=getSharedPreferences("bala",MODE_PRIVATE).getInt("bala",0);
        if(score>ll)
        {
            getSharedPreferences("bala",MODE_PRIVATE).edit().putInt("bala",score).apply();
        }
    }
}
