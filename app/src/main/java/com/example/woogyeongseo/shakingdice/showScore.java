package com.example.woogyeongseo.shakingdice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class showScore extends AppCompatActivity {

    private ImageView resultUserImage;
    private TextView resultUserName;
    private TextView resultUserScore;

    public String userName;
    private String userImage;
    public int userScore;

    SharedPreferences Preferences;

    ScoresDAOHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        myDb = new ScoresDAOHelper(this);

        resultUserImage = (ImageView) findViewById(R.id.resultUserImage);
        resultUserName = (TextView) findViewById(R.id.resultUserName);
        resultUserScore = (TextView) findViewById(R.id.resultUserScore);

        Preferences = getSharedPreferences("data", MODE_PRIVATE);


        userImage = Preferences.getString("userImage","userImage false");
        userName = Preferences.getString("userName","userName false");
        userScore = Preferences.getInt("userScore",0);

        resultUserName.setText(userName);
        resultUserScore.setText("Total Score : " + String.valueOf(userScore));
        //decode string to image
        String base=userImage;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        resultUserImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length));

        AddData();


    }

    public void startTwitter(View view){
        Intent intent = new Intent(this, TwitterMain.class);
        startActivity(intent);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AddData() {
        boolean isInserted = myDb.insertData(userName,userScore);
        if (isInserted=true)
            Toast.makeText(showScore.this,"Your Data is inserted to Ranking sucessfully",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(showScore.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("showScore","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("showScore","on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("showScore","on destroy called");}

}
