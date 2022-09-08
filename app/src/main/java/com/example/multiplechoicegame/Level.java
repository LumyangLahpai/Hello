package com.example.multiplechoicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends AppCompatActivity {
    TextView txtLevel,txtQuestionNo,question,txtTimer, txtScore;
    RadioGroup radioGroup;
    RadioButton rb1, rb2,rb3,rb4;
    Button btnNext,btnCheck;


    int questionIndex=1;
    int num1=0;
    int num2=0;
    int opeNum=0;
    String operator="+";
    int answer=0;
    int score=0;
    String my_level;
    ArrayList<Integer> optionList = new ArrayList<Integer>();


    CountDownTimer countDownTimer;
   long miliSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        my_level=getIntent().getStringExtra("Level");
        txtLevel=(TextView) findViewById(R.id.idlevel);
        txtLevel.setText("Level: "+my_level);
        txtTimer=(TextView) findViewById(R.id.idtimer);
        txtQuestionNo=(TextView) findViewById(R.id.idquestionno);
        btnNext=(Button) findViewById(R.id.idnext);
        btnCheck = (Button) findViewById(R.id.idcheck);
        rb1 = (RadioButton) findViewById(R.id.idrb1);
        rb2 = (RadioButton) findViewById(R.id.idrb2);
        rb3 = (RadioButton) findViewById(R.id.idrb3);
        rb4 = (RadioButton) findViewById(R.id.idrb4);
        radioGroup = (RadioGroup) findViewById(R.id.idrg);
        question = (TextView) findViewById(R.id.idquestion);
        txtScore = (TextView) findViewById(R.id.idscore);
        btnCheck.setVisibility(View.INVISIBLE);
        if(my_level.equals("0")){
            txtTimer.setVisibility(View.INVISIBLE);
        }
        else if(my_level.equals("1")){
            txtTimer.setVisibility(View.VISIBLE);
        }
        else if(my_level.equals("2")){
            txtTimer.setVisibility(View.VISIBLE);
        }
        setUpQuestion();
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                questionIndex++;
                btnCheck.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { if(questionIndex>10){
                Toast.makeText(Level.this,"Congrats",Toast.LENGTH_LONG).show();
            }else {
                setUpQuestion();
            }

            }
        });
    }
    public void setUpQuestion(){
        btnCheck.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        if(!my_level.equals("0")){
            startTimer();
        }
        radioGroup.clearCheck();
        txtQuestionNo.setText("Question:" + questionIndex+"/10");
        txtScore.setText("Score: "+score);
        num1=randomNumber(12,1);
        num2=randomNumber(12,1);
        opeNum=randomNumber(4,1);
        switch (opeNum){
            case 1:
                answer=num1+num2;
                operator="+";
                break;
            case 2:
                if(num1>num2){
                    answer=num1-num2;
                }else{
                    setUpQuestion();
                }
                operator="-";
                break;
            case 3:
                answer=num1*num2;
                operator="*";
                break;
            case 4:
                answer=num1/num2;
                operator="/";
                break;
        }
        txtQuestionNo.setText("Question:"+questionIndex+"/10");
        question.setText(String.valueOf(num1) +operator+ String.valueOf(num2) +" will be ");
        setUpOptions();
    }
    public void setUpOptions(){
        optionList=new ArrayList<Integer>();
        optionList.add(answer);
        txtScore.setText("Score: "+score);
        for(int i= 0; i<3;i++){
            int randNumber = randomNumber(12,1);
            while (optionList.contains(randNumber)){
                randNumber=randomNumber(12,1);
            }
            optionList.add(randNumber);
        }

        Collections.shuffle(optionList);
        rb1.setText(optionList.get(0).toString());
        rb2.setText(optionList.get(1).toString());
        rb3.setText(optionList.get(2).toString());
        rb4.setText(optionList.get(3).toString());

    }
    public void checkAnswer(){
        if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
            RadioButton checkedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if(Integer.parseInt(checkedRadioButton.getText().toString())==answer){
                score++;
                txtScore.setText("Score: "+score);
                MediaPlayer ring= MediaPlayer.create(Level.this,R.raw.correct);
                ring.start();
            }
            else{
                Toast.makeText(Level.this, "Wrong!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
                MediaPlayer ring= MediaPlayer.create(Level.this,R.raw.wrong);
                ring.start();
            }
            if(!my_level.equals("0")){
                countDownTimer.cancel();
            }

        }
        else {
            Toast.makeText(Level.this,"Please choose one",Toast.LENGTH_SHORT).show();
        }


    }
    public int randomNumber(int max, int min){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
    private  void startTimer(){
        if(my_level.equals("1")){
            miliSecond = 21000;
        }
        else if(my_level.equals("2")){
            miliSecond = 11000;
        }
        countDownTimer = new CountDownTimer(miliSecond,1000) {
            @Override
            public void onTick(long l) {
                miliSecond = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                setUpQuestion();
            }
        }.start();
    }
    private void updateCountDownText(){
        txtTimer.setText("Timer: 00: "+miliSecond/1000);
    }

}