package com.example.woogyeongseo.shakingdice;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Ranking extends AppCompatActivity{

    ScoresDAOHelper myDb;
    private TextView rankingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_layout);
        myDb = new ScoresDAOHelper(this);

        rankingView = (TextView) findViewById(R.id.rankingView);
        viewAll();

    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if(res.getCount() ==0){
            Toast.makeText(Ranking.this,"Nothing Found",Toast.LENGTH_LONG).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append(("Id : "+res.getString(0)+"   "
                    +"Name : "+res.getString(1)+"   "
                    +"Score : "+res.getString(2)+"\n\n"));
        }
        rankingView.setText(buffer.toString());

    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getMenuInflater().inflate(R.menu.menu_main,menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_ranking) {
            Intent intent = new Intent(this, Ranking.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Ranking","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ranking","on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ranking","on destroy called");}


}


