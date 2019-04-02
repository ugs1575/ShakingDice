package com.example.woogyeongseo.shakingdice;


import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Collections;



public class Game extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    public int getx,gety;

    private ImageView dice1;
    private ImageView dice2;
    private TextView questionView;
    private TextView sumView;
    private EditText guessField;
    private int diceSum;
    private int questionNum = 0;
    private int answer;
    private int totalScore = 0;
    private String userAnswer;


    SharedPreferences Preferences;


    Dice d1 = new Dice(R.drawable.d1,1);
    Dice d2 = new Dice(R.drawable.d2,2);
    Dice d3 = new Dice(R.drawable.d3,3);
    Dice d4 = new Dice(R.drawable.d4,4);
    Dice d5 = new Dice(R.drawable.d5,5);
    Dice d6 = new Dice(R.drawable.d6,6);


    Dice [] diceArray = new Dice[]{
            d1, d2, d3, d4, d5, d6
    };

    Question q1 = new Question(1,"There are 600 pupils in a school.\nThe ratio of boys to girls in this school is 3:X.\nHow many girls are in this school?");
    Question q2 = new Question(2,"There are 800 animals in the park.\nThe ratio of cats to dogs in the park is 2:X.\nHow many dogs are in the park?");
    Question q3 = new Question(3,"At the start of the week a bookshop had 500 art books.\nBy the end of the week, X% of art books were sold.\nHow many books of art books were sold?");
    Question q4 = new Question(4,"If 30% of a number is 90, what is X% of the number?");
    Question q5 = new Question(5,"The original price of a toy was $100.\nIf the price is reduced by X%, what is the new price of the toy?");


    Question [] questionArray = new Question []{
      q1, q2, q3, q4, q5
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game_layout);

        Preferences = getSharedPreferences("data",MODE_PRIVATE);


        dice1 = (ImageView) findViewById(R.id.diceView1);
        dice2 = (ImageView) findViewById(R.id.diceView2);
        sumView = (TextView) findViewById(R.id.sumView);
        questionView = (TextView) findViewById(R.id.questionView);
        guessField = (EditText) findViewById(R.id.guessField);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        guessField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence string, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                userAnswer = guessField.getText().toString();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Game","on resume called");
        sumView.setText("");
        questionView.setText("");
        guessField.setText("");
        questionNum = questionNum + 1;
        if (questionNum == 6){
            Preferences.edit()
                    .putInt("userScore",totalScore)
                    .commit();
            Intent intent = new Intent(this, showScore.class);
            startActivity(intent);
        }
        Collections.shuffle(Arrays.asList(diceArray));
        sensorManager.registerListener(this,accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Game","on pause called");
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getx = (int) event.values[0];
            gety = (int) event.values[1];
            if (getx >0 && gety==0 || getx<0 && gety==0 ){
                setDiceImage();
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void setDiceImage(){
        dice1.setImageResource(diceArray[0].getDiceImage());
        dice2.setImageResource(diceArray[1].getDiceImage());
        cacDiceSum(diceArray[0].getDiceNum(),diceArray[1].getDiceNum());
    }

    //included in setDiceImage
    public void cacDiceSum(int dice1, int dice2){
        diceSum = dice1 + dice2;
        System.out.println(diceSum);
        sumView.setText(String.valueOf(diceSum));
        setQuestionView(questionNum);
    }

    public void setQuestionView(int questionNum){
       switch (questionNum){
           case 1:
              questionView.setText(questionArray[0].getQuestion());
              break;
           case 2:
               questionView.setText(questionArray[1].getQuestion());
               break;
           case 3:
               questionView.setText(questionArray[2].getQuestion());
               break;
           case 4:
               questionView.setText(questionArray[3].getQuestion());
               break;
           case 5:
               questionView.setText(questionArray[4].getQuestion());
               break;
       }
        setAnswer(questionNum);
    }

    public void setAnswer(int questionNum){
        switch (questionNum){
            case 1:
                answer = (600*diceSum)/(3+diceSum);
                System.out.println(answer);
                break;
            case 2:
                answer = (800*diceSum)/(2+diceSum);
                System.out.println(answer);
                break;
            case 3:
                answer = 15*diceSum;
                System.out.println(answer);
                break;
            case 4:
                answer = 3*diceSum;
                System.out.println(answer);
                break;
            case 5:
                answer = 100-diceSum;
                System.out.println(answer);
                break;
        }

    }

    public void nextQuestion(View view) {
        switch(view.getId()) {
            case R.id.nextQuestionBtn:
                if (userAnswer.equals(String.valueOf(answer))) {
                    totalScore = totalScore + questionNum ;
                }
                onResume();
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Game","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Game","on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Game","on destroy called");}


}
